package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import achmadaffandi.mdisaster.Model.User;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tv_userPName, tv_userPPhone, tv_userPEmail, tv_userPType;
    private Button btn_toLogOut, btn_toChangePass;
    private DatabaseReference mDataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //deklarasi semua komponen
        tv_userPName = (TextView) findViewById(R.id.tv_userPNama);
        tv_userPEmail = (TextView) findViewById(R.id.tv_userPEmail);
        tv_userPPhone = (TextView) findViewById(R.id.tv_userPPhone);
        tv_userPType = (TextView) findViewById(R.id.tv_userPType);
        btn_toLogOut = (Button) findViewById(R.id.btn_toLogOut);
        btn_toChangePass = (Button) findViewById(R.id.btn_toChangePass);
        //memanggil data dari database
        mDataUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mDataUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    //memunculkan data pengguna terkait
                    if (ds.getKey().equals(FirebaseAuth.getInstance().getUid())) {
                        tv_userPName.setText(user.getNama());
                        tv_userPEmail.setText(user.getEmail());
                        tv_userPType.setText(user.getType());
                        tv_userPPhone.setText(user.getPhone());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //logout dari aplikasi
        btn_toLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(UserProfileActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        //menuju laman ubah password
        btn_toChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, ChangePasswordActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}