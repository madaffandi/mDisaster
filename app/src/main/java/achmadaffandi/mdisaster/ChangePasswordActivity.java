package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText cp_password, cp_newpassword, cp_repassword;
    private Button btn_changePass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
        //deklarasi semua komponen
        cp_password = (EditText) findViewById(R.id.cp_password);
        cp_newpassword = (EditText) findViewById(R.id.cp_newPass);
        cp_repassword = (EditText) findViewById(R.id.cp_repassword);
        btn_changePass = (Button) findViewById(R.id.btn_changePass);
        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    //fungsi mengubah password
    private void changePassword() {
        //mengambil data dari EditText
        final String password = cp_password.getText().toString().trim();
        final String newpassword = cp_newpassword.getText().toString().trim();
        final String repassword = cp_repassword.getText().toString().trim();
        //pengecekan terhadap password baru
        if (!repassword.equals(newpassword)) {
            cp_repassword.setError(getString(R.string.input_error_repassword));
            cp_repassword.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        //menggunakan credential untuk memastikan user berhak mengubah password
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), password);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                user.updatePassword(newpassword)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent i = new Intent(ChangePasswordActivity.this, UserProfileActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Toast.makeText(ChangePasswordActivity.this, "Password updated...", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
