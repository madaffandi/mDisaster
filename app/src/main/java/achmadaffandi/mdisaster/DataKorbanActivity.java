package achmadaffandi.mdisaster;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataKorbanActivity extends AppCompatActivity {

    private TextView tv_judulBenKor, tv_judulSubBenKor, tv_judulDesBenKor;
    private String DIS_ID, korbanMeninggal, korbanHilang, korbanLukaBerat, korbanLukaRingan;
    private EditText et_korbanMeninggal, et_korbanHilang, et_korbanLukaBerat, et_korbanLukaRingan;
    private Button btn_toInfrastruktur;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_korban);
        tv_judulBenKor = (TextView)findViewById(R.id.tv_judulBenKor);
        tv_judulSubBenKor = (TextView)findViewById(R.id.tv_judulSubBenKor);
        tv_judulDesBenKor = (TextView)findViewById(R.id.tv_judulDesBenKor);

        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mDatabase.child("Disaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(DIS_ID)) {
                        tv_judulBenKor.setText(ds.child("jenisBencana").getValue().toString());
                        tv_judulSubBenKor.setText(ds.child("kabupaten").getValue().toString());
                        tv_judulDesBenKor.setText(ds.child("tanggalKejadian").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        et_korbanMeninggal = (EditText)findViewById(R.id.et_korbanMeninggal);
        et_korbanHilang = (EditText)findViewById(R.id.et_korbanHilang);
        et_korbanLukaBerat = (EditText)findViewById(R.id.et_korbanLukaBerat);
        et_korbanLukaRingan = (EditText)findViewById(R.id.et_korbanLukaRingan);
        btn_toInfrastruktur = (Button)findViewById(R.id.btn_toInfrastruktur);
        btn_toInfrastruktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setKorbanMeninggal(et_korbanMeninggal.getText().toString());
                setKorbanHilang(et_korbanHilang.getText().toString());
                setKorbanLukaBerat(et_korbanLukaBerat.getText().toString());
                setKorbanLukaRingan(et_korbanLukaRingan.getText().toString());
                updateDataKorban(DIS_ID, getKorbanMeninggal(), getKorbanHilang(),
                        getKorbanLukaBerat(), getKorbanLukaRingan());
                Intent i = new Intent(DataKorbanActivity.this, DataInfrastrukturActivity.class);
                i.putExtra("DIS_ID", DIS_ID);
                startActivity(i);
            }
        });
    }

    private void updateDataKorban(String DIS_ID, String korbanMeninggal, String korbanHilang, String korbanLukaBerat, String korbanLukaRingan){
        String key = DIS_ID;
        mDatabase.child("Disaster").child(key).child("korbanMeninggal").setValue(korbanMeninggal);
        mDatabase.child("Disaster").child(key).child("korbanHilang").setValue(korbanHilang);
        mDatabase.child("Disaster").child(key).child("korbanLukaBerat").setValue(korbanLukaBerat);
        mDatabase.child("Disaster").child(key).child("korbanLukaRingan").setValue(korbanLukaRingan);
    }

    public String getKorbanMeninggal() {
        return korbanMeninggal;
    }

    public void setKorbanMeninggal(String korbanMeninggal) {
        this.korbanMeninggal = korbanMeninggal;
    }

    public String getKorbanHilang() {
        return korbanHilang;
    }

    public void setKorbanHilang(String korbanHilang) {
        this.korbanHilang = korbanHilang;
    }

    public String getKorbanLukaBerat() {
        return korbanLukaBerat;
    }

    public void setKorbanLukaBerat(String korbanLukaBerat) {
        this.korbanLukaBerat = korbanLukaBerat;
    }

    public String getKorbanLukaRingan() {
        return korbanLukaRingan;
    }

    public void setKorbanLukaRingan(String korbanLukaRingan) {
        this.korbanLukaRingan = korbanLukaRingan;
    }
}

