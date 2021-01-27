package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import achmadaffandi.mdisaster.Model.User;

public class UserCreationActivity extends AppCompatActivity {

    private Button btn_signup;
    private EditText su_nama, su_email, su_password, su_repassword, su_phone;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        //deklarasi semua komponen
        su_nama = (EditText) findViewById(R.id.su_nama);
        su_email = (EditText) findViewById(R.id.su_email);
        su_password = (EditText) findViewById(R.id.su_password);
        su_repassword = (EditText) findViewById(R.id.su_repassword);
        su_phone = (EditText) findViewById(R.id.su_phone);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        //menggunakan aplikasi baru untuk membuat akun lain agar tetap bisa menggunakan akun utama
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("[https://mdisaster-2019.firebaseio.com]")
                .setApiKey("AIzaSyCtSXaHBO_NSrdM4t17GBHp6jiEh7Xw7YI")
                .setApplicationId("mdisaster-2019").build();
        try {
            FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth1 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e) {
            mAuth1 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }
    }

    //fungsi mendaftarkan pengguna
    private void registerUser() {
        //menginisasi variabel
        final String nama = su_nama.getText().toString().trim();
        final String email = su_email.getText().toString().trim();
        String password = su_password.getText().toString().trim();
        String repassword = su_repassword.getText().toString().trim();
        final String phone = su_phone.getText().toString().trim();
        final String disId = getIntent().getExtras().get("DIS_ID").toString();
        String type = "relawan";
        //melakukan pengecekan untuk data yang tidak sesuai
        if (nama.isEmpty()) {
            su_nama.setError(getString(R.string.input_error_nama));
            su_nama.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            su_email.setError(getString(R.string.input_error_email));
            su_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            su_email.setError(getString(R.string.input_error_email));
            su_email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            su_password.setError(getString(R.string.input_error_password));
            su_password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            su_password.setError(getString(R.string.input_error_password_length));
            su_password.requestFocus();
            return;
        }
        if (!repassword.equals(password)) {
            su_repassword.setError(getString(R.string.input_error_repassword));
            su_repassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            su_phone.setError(getString(R.string.input_error_phone));
            su_phone.requestFocus();
            return;
        }
        if (phone.length() < 9) {
            su_phone.setError(getString(R.string.input_error_phone));
            su_phone.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        //membuat user baru dengan data terkait
        mAuth1.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(
                                    nama,
                                    email,
                                    phone,
                                    type
                            );
                            //menugaskan pengguna sesuai bencana terkait pada database
                            FirebaseDatabase.getInstance().getReference().child("UserAssign").child(getIntent().getExtras().get("DIS_ID").toString()).
                                    child(mAuth1.getCurrentUser().getUid()).setValue("true");
                            //membuat objek user baru pada database
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(mAuth1.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserCreationActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        mAuth1.signOut();
                                        Intent i = new Intent(UserCreationActivity.this, DisListActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(getApplicationContext(), getString(R.string.registration_duplicate), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(UserCreationActivity.this, getString(R.string.registration_failed), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UserCreationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
