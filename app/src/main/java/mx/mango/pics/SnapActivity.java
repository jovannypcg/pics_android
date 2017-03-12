package mx.mango.pics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;

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
        progressDialog.setMessage("Enviando");
        progressDialog.show();

        String cause = etCause.getText().toString();
        String description = etDescription.getText().toString();
        String location = spLocation.getSelectedItem().toString();
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
            etDescription.setError("Ingresa un email válido");
            valid = false;
        } else {
            etDescription.setError(null);
        }

        if (cause.isEmpty()) {
            etCause.setError("Debe ser entre 4 y 10 caracteres");
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
}
