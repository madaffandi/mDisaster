package achmadaffandi.mdisaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import achmadaffandi.mdisaster.Model.User;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private static final String MyPrefs = "mdis";
    private EditText loginemail, loginpassword;
    private String email, password;
    private ProgressBar progressBar;
    private DatabaseReference mUsers;
    private Button btn_login;
    private RelativeLayout rellay_login1;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay_login1.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //run appIntro untuk pertama kali install
        /*SharedPreferences sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }*/

if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP){
    //memberikan peringatan untuk versi Android
    AlertDialog checkAndroidVersion = new AlertDialog.Builder(this)
            .setTitle("Peringatan")
            .setMessage("versi Android Anda tidak direkomendasikan, " +
                    "gunakan API 23 (Marsmellow) ke atas")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                    System.exit(0);
                    dialog.dismiss();
                }
            })
            .create();
    checkAndroidVersion.show();
}

        mAuth = FirebaseAuth.getInstance();
        //cek apakah pengguna sudah login
        if (mAuth.getCurrentUser() != null) {
            mUsers = FirebaseDatabase.getInstance().getReference().child("Users");
            Query query = mUsers.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        User user = childSnapshot.getValue(User.class);
                        String type = user.getType();
                        //jika peran pengguna adalah admin, maka menuju dashboard
                        if (type.equals("mainAdmin")) {
                            Intent i = new Intent(LoginActivity.this, DashboardDisasterActivity.class);
                            startActivity(i);
                            finish();
                        }
                        //jika peran pengguna adalah relawan, maka menuju daftar bencana
                        else if (type.equals("relawan")) {
                            Intent i = new Intent(LoginActivity.this, DisListActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "peran belum diatur" + type, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                }
            });
        }
        //deklarasi komponen
        rellay_login1 = (RelativeLayout) findViewById(R.id.rellay_login1);
        //rellay_login2 = (RelativeLayout) findViewById(R.id.rellay_login2);
        handler.postDelayed(runnable, 1500);
        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpassword = (EditText) findViewById(R.id.loginpassword);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        //mengambil data email dan password
        email = loginemail.getText().toString().trim();
        password = loginpassword.getText().toString().trim();
        //melakukan pengecekan terhadap email dan password
        if (email.isEmpty()) {
            loginemail.setError(getString(R.string.input_error_email));
            loginemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginemail.setError(getString(R.string.input_error_email));
            loginemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            loginpassword.setError(getString(R.string.input_error_password));
            loginpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            loginpassword.setError(getString(R.string.input_error_password_length));
            loginpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        //melakukan login dengan email dan password terkait
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mUsers = FirebaseDatabase.getInstance().getReference().child("Users");
                Query query = mUsers.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            User user = childSnapshot.getValue(User.class);
                            String type = user.getType();
                            if (task.isSuccessful()) {
                                //jika peran pengguna adalah admin, maka dialihkan menuju dashboard
                                if (type.equals("mainAdmin")) {
                                    Intent i = new Intent(LoginActivity.this, DashboardDisasterActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                //jika peran pengguna adalah relawan, maka dialihkan menuju daftar bencana
                                else if (type.equals("relawan")) {
                                    Intent i = new Intent(LoginActivity.this, DisListActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "peran belum diatur" + type, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
