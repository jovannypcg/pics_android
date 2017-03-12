package mx.mango.pics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;
import mx.mango.pics.models.ApiUser;
import mx.mango.pics.models.User;
import mx.mango.pics.rest.ApiClient;
import mx.mango.pics.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        if (isUserAlreadyLogged()) {
            navigateToHome();
            return;
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private boolean isUserAlreadyLogged() {
        return realm.where(User.class).findAll().size() != 0;
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed("Verificar datos");
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiUser> call = apiService.login(new ApiUser(null, null, email, password));

        call.enqueue(new Callback<ApiUser>() {
            @Override
            public void onResponse(Call<ApiUser> call, Response<ApiUser> response) {
                Log.d("LOGIN", " ============================== ");
                Log.d("LOGIN", "Status code: " + response.code());
                Log.d("LOGIN", "Is response body null?: " + (response.body() == null));
                Log.d("LOGIN", "Response: " + response.body());
                Log.d("LOGIN", " ============================== ");

                switch (response.code()) {
                    case 200:
                        onLoginSuccess(response.body());
                        break;
                    case 401:
                        onLoginFailed("Verificar datos");
                        break;
                    default:
                        onLoginFailed("Ingreso incorrecto");

                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ApiUser> call, Throwable t) {
                onLoginFailed("Ingreso incorrecto");
                progressDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "Registro satisfactorio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the LoginActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(ApiUser apiUser) {
        _loginButton.setEnabled(true);

        deleteCurrentSavedUser();
        saveUser(apiUser);

        navigateToHome();
    }

    public void onLoginFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public void saveUser(final ApiUser apiUser) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User _user = realm.createObject(User.class);
                _user.setEmail(apiUser.getEmail());
                _user.setFirstName(apiUser.getFirstName());
                _user.setId(apiUser.getId());
                _user.setToken(apiUser.getToken());

                Log.d("SAVING USER", _user.toString());
            }
        });
    }

    public void deleteCurrentSavedUser() {
        final RealmResults<User> results = realm.where(User.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Delete all matches
                results.deleteAllFromRealm();
            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Ingresa un email válido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Debe ser entre 4 y 10 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void navigateToHome() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
