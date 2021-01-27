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

public class DataPengungsiActivity extends AppCompatActivity {

    private TextView tv_judulBenPeng, tv_judulSubBenPeng, tv_judulDesBenPeng;
    private EditText et_lakiBalita, et_perempuanBalita, et_lakiAnak, et_perempuanAnak, et_lakiRemaja, et_perempuanRemaja, et_lakiDewasa, et_perempuanDewasa, et_lakiLansia, et_perempuanLansia;
    private String DIS_ID, lakiBalita, perempuanBalita, lakiAnak, perempuanAnak, lakiRemaja, perempuanRemaja, lakiDewasa, perempuanDewasa, lakiLansia, perempuanLansia;
    private DatabaseReference mDataRef, mDataDis;
    private Button btn_toKorban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengungsi);
        //deklarasi semua komponen
        tv_judulBenPeng = (TextView) findViewById(R.id.tv_judulBenPeng);
        tv_judulSubBenPeng = (TextView) findViewById(R.id.tv_judulSubBenPeng);
        tv_judulDesBenPeng = (TextView) findViewById(R.id.tv_judulDesBenPeng);
        et_lakiBalita = (EditText) findViewById(R.id.et_lakiBalita);
        et_lakiAnak = (EditText) findViewById(R.id.et_lakiAnak);
        et_lakiRemaja = (EditText) findViewById(R.id.et_lakiRemaja);
        et_lakiDewasa = (EditText) findViewById(R.id.et_lakiDewasa);
        et_lakiLansia = (EditText) findViewById(R.id.et_lakiLansia);
        et_perempuanBalita = (EditText) findViewById(R.id.et_perempuanBalita);
        et_perempuanAnak = (EditText) findViewById(R.id.et_perempuanAnak);
        et_perempuanRemaja = (EditText) findViewById(R.id.et_perempuanRemaja);
        et_perempuanDewasa = (EditText) findViewById(R.id.et_perempuanDewasa);
        et_perempuanLansia = (EditText) findViewById(R.id.et_perempuanLansia);
        btn_toKorban = (Button) findViewById(R.id.btn_toKorban);
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
                        tv_judulBenPeng.setText(ds.child("jenisBencana").getValue().toString());
                        tv_judulSubBenPeng.setText(ds.child("kabupaten").getValue().toString());
                        tv_judulDesBenPeng.setText(ds.child("tanggalKejadian").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_toKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKondisiPengungsi();
            }
        });
    }

    private void updateKondisiPengungsi() {
        //mendata variabel
        setLakiBalita(et_lakiBalita.getText().toString());
        setLakiAnak(et_lakiAnak.getText().toString());
        setLakiRemaja(et_lakiRemaja.getText().toString());
        setLakiDewasa(et_lakiDewasa.getText().toString());
        setLakiLansia(et_lakiLansia.getText().toString());
        setPerempuanBalita(et_perempuanBalita.getText().toString());
        setPerempuanAnak(et_perempuanAnak.getText().toString());
        setPerempuanRemaja(et_perempuanRemaja.getText().toString());
        setPerempuanDewasa(et_perempuanDewasa.getText().toString());
        setPerempuanLansia(et_perempuanLansia.getText().toString());
        //melakukan pengecekan untuk tidak mengizinkan nilai kosong
        if (getLakiBalita().isEmpty() || getLakiAnak().isEmpty() || getLakiRemaja().isEmpty() ||
                getLakiDewasa().isEmpty() || getLakiLansia().isEmpty() ||
                getPerempuanBalita().isEmpty() || getPerempuanAnak().isEmpty() ||
                getPerempuanRemaja().isEmpty() || getPerempuanDewasa().isEmpty() ||
                getPerempuanLansia().isEmpty()) {
            Toast.makeText(DataPengungsiActivity.this, R.string.input_error_pengungsi, Toast.LENGTH_LONG).show();
        } else {
            //memanggil method penambahan data pengungsi
            updateDataPengungsi(DIS_ID, getLakiBalita(), getPerempuanBalita(),
                    getLakiAnak(), getPerempuanAnak(),
                    getLakiRemaja(), getPerempuanRemaja(),
                    getLakiDewasa(), getPerempuanDewasa(),
                    getLakiLansia(), getPerempuanLansia());
            Intent i = new Intent(DataPengungsiActivity.this, DataKorbanActivity.class);
            i.putExtra("DIS_ID", DIS_ID);
            startActivity(i);
        }
    }

    //method pendambahan data pengungsi ke database
    private void updateDataPengungsi(String DIS_ID, String lakiBalita, String perempuanBalita, String lakiAnak, String perempuanAnak,
                                     String lakiRemaja, String perempuanRemaja, String lakiDewasa, String perempuanDewasa,
                                     String lakiLansia, String perempuanLansia) {
        String key = DIS_ID;
        mDataDis.child(key).child("lakiBalita").setValue(lakiBalita);
        mDataDis.child(key).child("perempuanBalita").setValue(perempuanBalita);
        mDataDis.child(key).child("lakiAnak").setValue(lakiAnak);
        mDataDis.child(key).child("perempuanAnak").setValue(perempuanAnak);
        mDataDis.child(key).child("lakiRemaja").setValue(lakiRemaja);
        mDataDis.child(key).child("perempuanRemaja").setValue(perempuanRemaja);
        mDataDis.child(key).child("lakiDewasa").setValue(lakiDewasa);
        mDataDis.child(key).child("perempuanDewasa").setValue(perempuanDewasa);
        mDataDis.child(key).child("lakiLansia").setValue(lakiLansia);
        mDataDis.child(key).child("perempuanLansia").setValue(perempuanLansia);
    }

    public String getLakiBalita() {
        return lakiBalita;
    }

    public void setLakiBalita(String lakiBalita) {
        this.lakiBalita = lakiBalita;
    }

    public String getPerempuanBalita() {
        return perempuanBalita;
    }

    public void setPerempuanBalita(String perempuanBalita) {
        this.perempuanBalita = perempuanBalita;
    }

    public String getLakiAnak() {
        return lakiAnak;
    }

    public void setLakiAnak(String lakiAnak) {
        this.lakiAnak = lakiAnak;
    }

    public String getPerempuanAnak() {
        return perempuanAnak;
    }

    public void setPerempuanAnak(String perempuanAnak) {
        this.perempuanAnak = perempuanAnak;
    }

    public String getLakiRemaja() {
        return lakiRemaja;
    }

    public void setLakiRemaja(String lakiRemaja) {
        this.lakiRemaja = lakiRemaja;
    }

    public String getPerempuanRemaja() {
        return perempuanRemaja;
    }

    public void setPerempuanRemaja(String perempuanRemaja) {
        this.perempuanRemaja = perempuanRemaja;
    }

    public String getLakiDewasa() {
        return lakiDewasa;
    }

    public void setLakiDewasa(String lakiDewasa) {
        this.lakiDewasa = lakiDewasa;
    }

    public String getPerempuanDewasa() {
        return perempuanDewasa;
    }

    public void setPerempuanDewasa(String perempuanDewasa) {
        this.perempuanDewasa = perempuanDewasa;
    }

    public String getLakiLansia() {
        return lakiLansia;
    }

    public void setLakiLansia(String lakiLansia) {
        this.lakiLansia = lakiLansia;
    }

    public String getPerempuanLansia() {
        return perempuanLansia;
    }

    public void setPerempuanLansia(String perempuanLansia) {
        this.perempuanLansia = perempuanLansia;
    }
}

