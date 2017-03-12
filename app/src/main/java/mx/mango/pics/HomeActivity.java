package mx.mango.pics;

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

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmResults;
import mx.mango.pics.models.User;

public class HomeActivity extends AppCompatActivity {
    @InjectView(R.id.tv_user_first_name)
    TextView tvFirstName;

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
    }

    public User getCurrentUser() {
        return realm.where(User.class).findAll().first();
    }
}
