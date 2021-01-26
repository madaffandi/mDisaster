package achmadaffandi.mdisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    //btn_signup, btn_forgotpass;
    private RelativeLayout rellay_login1;
    //rellay_login2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay_login1.setVisibility(View.VISIBLE);
            //rellay_login2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        if (!sp.getBoolean("first", false)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }

        mAuth = FirebaseAuth.getInstance();
        //cek apakah user sudah login
        if (mAuth.getCurrentUser() != null) {
            mUsers = FirebaseDatabase.getInstance().getReference().child("Users");
            Query query = mUsers.orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        User user = childSnapshot.getValue(User.class);
                        String type = user.getType();
                        if (type.equals("mainAdmin")) {
                            Intent i = new Intent(LoginActivity.this, DashboardDisasterActivity.class);
                            startActivity(i);
                            finish();
                        } else if (type.equals("relawan")) {
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

        /*
        //fungsi daftar
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        //fungsi lupa password
        btn_forgotpass = (Button) findViewById(R.id.btn_forgotpass);
        btn_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (LoginActivity.this, ForgetPassActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        */
    }

    private void userLogin() {
        email = loginemail.getText().toString().trim();
        password = loginpassword.getText().toString().trim();

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
                                if (type.equals("mainAdmin")) {
                                    Intent i = new Intent(LoginActivity.this, DashboardDisasterActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (type.equals("relawan")) {
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
