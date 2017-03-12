package mx.mango.pics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;
import mx.mango.pics.models.ApiSnap;
import mx.mango.pics.models.User;
import mx.mango.pics.rest.ApiClient;
import mx.mango.pics.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    @InjectView(R.id.tv_user_first_name)
    TextView tvFirstName;
    @InjectView(R.id.btn_add_snap)
    FloatingActionButton btnAddSnap;
    @InjectView(R.id.btn_refresh_snaps)
    FloatingActionButton btnRefreshSnaps;

    private Realm realm;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        this.currentUser = getCurrentUser();
        Log.d("HOME", this.currentUser.toString());

        this.tvFirstName.setText(this.currentUser.getFirstName());

        this.btnAddSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSnap();
            }
        });

        this.btnRefreshSnaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveSnaps();
            }
        });
    }

    private void retrieveSnaps() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ApiSnap>> call = apiService.getSnaps(currentUser.getId());

        call.enqueue(new Callback<List<ApiSnap>>() {
            @Override
            public void onResponse(Call<List<ApiSnap>> call, Response<List<ApiSnap>> response) {

            }

            @Override
            public void onFailure(Call<List<ApiSnap>> call, Throwable t) {
                Log.d("SNAPS", "Algo falló");
            }
        });
    }

    private void navigateToSnap() {
        Intent snapIntent = new Intent(this, SnapActivity.class);
        startActivity(snapIntent);
    }

    public User getCurrentUser() {
        return realm.where(User.class).findAll().first();
    }
}
