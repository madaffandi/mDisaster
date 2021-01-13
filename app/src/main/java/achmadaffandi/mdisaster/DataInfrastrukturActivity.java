package achmadaffandi.mdisaster;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataInfrastrukturActivity extends AppCompatActivity {

    private TextView tv_judulBenInf, tv_judulSubBenInf, tv_judulDesBenInf;
    private String DIS_ID, rumahHancur, rumahRusakBerat, rumahRusakRingan;
    private EditText et_rumahHancur, et_rumahRusakBerat, et_rumahRusakRingan;
    private Button btn_toSubmitInf;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_infrastruktur);
        tv_judulBenInf = (TextView)findViewById(R.id.tv_judulBenInf);
        tv_judulSubBenInf = (TextView)findViewById(R.id.tv_judulSubBenInf);
        tv_judulDesBenInf = (TextView)findViewById(R.id.tv_judulDesBenInf);

        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mDatabase.child("Disaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(DIS_ID)) {
                        tv_judulBenInf.setText(ds.child("jenisBencana").getValue().toString());
                        tv_judulSubBenInf.setText(ds.child("kabupaten").getValue().toString());
                        tv_judulDesBenInf.setText(ds.child("tanggalKejadian").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        et_rumahHancur = (EditText)findViewById(R.id.et_rumahHancur);
        et_rumahRusakBerat = (EditText)findViewById(R.id.et_rumahRusakBerat);
        et_rumahRusakRingan = (EditText)findViewById(R.id.et_rumahRusakRingan);
        btn_toSubmitInf = (Button)findViewById(R.id.btn_toSubmitInf);
        btn_toSubmitInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRumahHancur(et_rumahHancur.getText().toString());
                setRumahRusakBerat(et_rumahRusakBerat.getText().toString());
                setRumahRusakRingan(et_rumahRusakRingan.getText().toString());
                updateDataInfrastruktur(DIS_ID, getRumahHancur(), getRumahRusakBerat(), getRumahRusakRingan());
                Toast.makeText(DataInfrastrukturActivity.this, "Update data telah dilakukan", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DataInfrastrukturActivity.this, DisListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    private void updateDataInfrastruktur(String DIS_ID, String rumahHancur, String rumahRusakBerat, String rumahRusakRingan){
        String key = DIS_ID;
        mDatabase.child("Disaster").child(key).child("rumahHancur").setValue(rumahHancur);
        mDatabase.child("Disaster").child(key).child("rumahRusakBerat").setValue(rumahRusakBerat);
        mDatabase.child("Disaster").child(key).child("rumahRusakRingan").setValue(rumahRusakRingan);
    }

    public String getRumahHancur() {
        return rumahHancur;
    }

    public void setRumahHancur(String rumahHancur) {
        this.rumahHancur = rumahHancur;
    }

    public String getRumahRusakBerat() {
        return rumahRusakBerat;
    }

    public void setRumahRusakBerat(String rumahRusakBerat) {
        this.rumahRusakBerat = rumahRusakBerat;
    }

    public String getRumahRusakRingan() {
        return rumahRusakRingan;
    }

    public void setRumahRusakRingan(String rumahRusakRingan) {
        this.rumahRusakRingan = rumahRusakRingan;
    }
}

