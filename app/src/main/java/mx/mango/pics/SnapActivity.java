package mx.mango.pics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import mx.mango.pics.models.ApiSnap;
import mx.mango.pics.models.ApiUser;
import mx.mango.pics.models.User;
import mx.mango.pics.rest.ApiClient;
import mx.mango.pics.rest.ApiInterface;
import mx.mango.pics.utils.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnapActivity extends AppCompatActivity {
    @InjectView(R.id.ib_pic_one)
    ImageButton picOne;
    @InjectView(R.id.ib_pic_two)
    ImageButton picTwo;
    @InjectView(R.id.ib_pic_three)
    ImageButton picThree;
    @InjectView(R.id.ib_pic_four)
    ImageButton picFour;
    @InjectView(R.id.btn_send_snap)
    Button btnSendSnap;
    @InjectView(R.id.sp_location)
    Spinner spLocation;
    @InjectView(R.id.et_cause)
    EditText etCause;
    @InjectView(R.id.et_description)
    EditText etDescription;

    private User currentUser;

    private static final int CAMERA_REQUEST_PIC_ONE = 1;
    private static final int CAMERA_REQUEST_PIC_TWO = 2;
    private static final int CAMERA_REQUEST_PIC_THREE = 3;
    private static final int CAMERA_REQUEST_PIC_FOUR = 4;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);
        ButterKnife.inject(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        this.currentUser = getCurrentUser();
        Log.d("SNAP", this.currentUser.toString());

        picOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture(view);
            }
        });
        picTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture(view);
            }
        });
        picThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture(view);
            }
        });
        picFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture(view);
            }
        });
        btnSendSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSnap();
            }
        });
    }

    private void sendSnap() {
        Log.d("SNAP", "Sending ==========");

        if (!validate()) {
            onSendindFailed("Verificar datos");
            return;
        }

        btnSendSnap.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SnapActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Enviando...");
        progressDialog.show();

        Bitmap bmPicOne = ((BitmapDrawable)picOne.getDrawable()).getBitmap();
        Bitmap bmPicTwo = ((BitmapDrawable)picTwo.getDrawable()).getBitmap();
        Bitmap bmPicThree = ((BitmapDrawable)picThree.getDrawable()).getBitmap();
        Bitmap bmPicFour = ((BitmapDrawable)picFour.getDrawable()).getBitmap();

        String cause = etCause.getText().toString();
        String description = etDescription.getText().toString();
        String location = spLocation.getSelectedItem().toString();

        String strPicOne = StringUtils.bitmapToBase64(bmPicOne);
        String strPicTwo = StringUtils.bitmapToBase64(bmPicTwo);
        String strPicThree = StringUtils.bitmapToBase64(bmPicThree);
        String strPicFour = StringUtils.bitmapToBase64(bmPicFour);

        String phoneSerial = Build.SERIAL;
        String phoneModel = Build.MODEL;
        String phoneBrand = Build.BRAND;
        String phoneDevice = Build.DEVICE;
        String phoneManufacturer = Build.MANUFACTURER;
        String phoneOSVersion = Build.VERSION.INCREMENTAL;

        List<String> pics = new ArrayList<>();
        pics.add(strPicOne);
        pics.add(strPicTwo);
        pics.add(strPicThree);
        pics.add(strPicFour);

        ApiSnap apiSnap = new ApiSnap(null, currentUser.getId(), pics, location, cause,
                description, phoneOSVersion, phoneBrand, phoneManufacturer, phoneModel,
                phoneSerial, phoneDevice);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiSnap> call = apiService.createSnap(apiSnap);

        call.enqueue(new Callback<ApiSnap>() {
            @Override
            public void onResponse(Call<ApiSnap> call, Response<ApiSnap> response) {
                Log.d("LOGIN", " ============================== ");
                Log.d("LOGIN", "Status code: " + response.code());
                Log.d("LOGIN", "Is response body null?: " + (response.body() == null));
                Log.d("LOGIN", "Response: " + response.body());
                Log.d("LOGIN", " ============================== ");

                onSendingSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ApiSnap> call, Throwable t) {
                onSendindFailed("Error al enviar información");
            }
        });
    }

    public void onSendingSuccess() {
        btnSendSnap.setEnabled(true);
        Toast.makeText(getBaseContext(), "Información enviada", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onSendindFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        btnSendSnap.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String description = etDescription.getText().toString();
        String cause = etCause.getText().toString();

        if (description.isEmpty()) {
            etDescription.setError("Ingresa una descripción");
            valid = false;
        } else {
            etDescription.setError(null);
        }

        if (cause.isEmpty()) {
            etCause.setError("Ingresa un motivo");
            valid = false;
        } else {
            etCause.setError(null);
        }

        return valid;
    }

    public void capturePicture(View view) {
        int requestCode = 0;

        switch (view.getId()) {
            case R.id.ib_pic_one:
                Log.d("Snap", "Taking picture one");
                requestCode = CAMERA_REQUEST_PIC_ONE;
                break;
            case R.id.ib_pic_two:
                Log.d("Snap", "Taking picture two");
                requestCode = CAMERA_REQUEST_PIC_TWO;
                break;
            case R.id.ib_pic_three:
                Log.d("Snap", "Taking picture three");
                requestCode = CAMERA_REQUEST_PIC_THREE;
                break;
            case R.id.ib_pic_four:
                Log.d("Snap", "Taking picture four");
                requestCode = CAMERA_REQUEST_PIC_FOUR;
                break;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        switch (requestCode) {
            case CAMERA_REQUEST_PIC_ONE:
                picOne.setImageBitmap(imageBitmap);
                break;
            case CAMERA_REQUEST_PIC_TWO:
                picTwo.setImageBitmap(imageBitmap);
                break;
            case CAMERA_REQUEST_PIC_THREE:
                picThree.setImageBitmap(imageBitmap);
                break;
            case CAMERA_REQUEST_PIC_FOUR:
                picFour.setImageBitmap(imageBitmap);
                break;
        }
    }

    public User getCurrentUser() {
        return realm.where(User.class).findAll().first();
    }
}
