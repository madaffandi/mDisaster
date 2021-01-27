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

public class DataKorbanActivity extends AppCompatActivity {

    private TextView tv_judulBenKor, tv_judulSubBenKor, tv_judulDesBenKor;
    private String DIS_ID, korbanMeninggal, korbanHilang, korbanLukaBerat, korbanLukaRingan;
    private EditText et_korbanMeninggal, et_korbanHilang, et_korbanLukaBerat, et_korbanLukaRingan;
    private Button btn_toInfrastruktur;
    private DatabaseReference mDataRef, mDataDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_korban);
        //deklarasi semua komponen
        tv_judulBenKor = (TextView) findViewById(R.id.tv_judulBenKor);
        tv_judulSubBenKor = (TextView) findViewById(R.id.tv_judulSubBenKor);
        tv_judulDesBenKor = (TextView) findViewById(R.id.tv_judulDesBenKor);
        et_korbanMeninggal = (EditText) findViewById(R.id.et_korbanMeninggal);
        et_korbanHilang = (EditText) findViewById(R.id.et_korbanHilang);
        et_korbanLukaBerat = (EditText) findViewById(R.id.et_korbanLukaBerat);
        et_korbanLukaRingan = (EditText) findViewById(R.id.et_korbanLukaRingan);
        btn_toInfrastruktur = (Button) findViewById(R.id.btn_toInfrastruktur);
        //mendapatkan ekstra dari intent sebelumnya yaitu ID Bencana
        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.keepSynced(true);
        mDataDis = mDataRef.child("Disaster");
        //mengatur informasi umum
        mDataDis.addValueEventListener(new ValueEventListener() {
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
        btn_toInfrastruktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKondisiKorban();
            }
        });
    }

    private void updateKondisiKorban() {
        //mendata variabel
        setKorbanMeninggal(et_korbanMeninggal.getText().toString());
        setKorbanHilang(et_korbanHilang.getText().toString());
        setKorbanLukaBerat(et_korbanLukaBerat.getText().toString());
        setKorbanLukaRingan(et_korbanLukaRingan.getText().toString());
        //melakukan pengecekan agar data tidak kosong
        if (getKorbanMeninggal().isEmpty() || getKorbanHilang().isEmpty() ||
                getKorbanLukaBerat().isEmpty() || getKorbanLukaRingan().isEmpty()) {
            Toast.makeText(DataKorbanActivity.this, R.string.input_error_korban, Toast.LENGTH_LONG);
        } else {
            //memanggil method untuk penambahan data korban
            updateDataKorban(DIS_ID, getKorbanMeninggal(), getKorbanHilang(),
                    getKorbanLukaBerat(), getKorbanLukaRingan());
            Intent i = new Intent(DataKorbanActivity.this, DataInfrastrukturActivity.class);
            i.putExtra("DIS_ID", DIS_ID);
            startActivity(i);
        }
    }

    //method pendambahan data korban pada database
    private void updateDataKorban(String DIS_ID, String korbanMeninggal, String korbanHilang, String korbanLukaBerat, String korbanLukaRingan) {
        String key = DIS_ID;
        mDataDis.child(key).child("korbanMeninggal").setValue(korbanMeninggal);
        mDataDis.child(key).child("korbanHilang").setValue(korbanHilang);
        mDataDis.child(key).child("korbanLukaBerat").setValue(korbanLukaBerat);
        mDataDis.child(key).child("korbanLukaRingan").setValue(korbanLukaRingan);
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

