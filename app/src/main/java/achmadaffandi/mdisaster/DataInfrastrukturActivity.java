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
    private DatabaseReference mDataRef, mDataDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_infrastruktur);
        //deklarasi semua komponen
        tv_judulBenInf = (TextView) findViewById(R.id.tv_judulBenInf);
        tv_judulSubBenInf = (TextView) findViewById(R.id.tv_judulSubBenInf);
        tv_judulDesBenInf = (TextView) findViewById(R.id.tv_judulDesBenInf);
        et_rumahHancur = (EditText) findViewById(R.id.et_rumahHancur);
        et_rumahRusakBerat = (EditText) findViewById(R.id.et_rumahRusakBerat);
        et_rumahRusakRingan = (EditText) findViewById(R.id.et_rumahRusakRingan);
        btn_toSubmitInf = (Button) findViewById(R.id.btn_toSubmitInf);
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
        btn_toSubmitInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKondisiInfrastruktur();

            }
        });
    }

    private void updateKondisiInfrastruktur() {
        //mendata variabel
        setRumahHancur(et_rumahHancur.getText().toString());
        setRumahRusakBerat(et_rumahRusakBerat.getText().toString());
        setRumahRusakRingan(et_rumahRusakRingan.getText().toString());
        //melakukan pengecekan data, tidak boleh kosong
        if (getRumahHancur().isEmpty() || getRumahRusakBerat().isEmpty() || getRumahRusakRingan().isEmpty()) {
            Toast.makeText(DataInfrastrukturActivity.this, R.string.input_error_infrastruktur, Toast.LENGTH_LONG);
        } else {
            //memanggil method penambahan data pada database
            updateDataInfrastruktur(DIS_ID, getRumahHancur(), getRumahRusakBerat(), getRumahRusakRingan());
            Toast.makeText(DataInfrastrukturActivity.this, "Update data telah dilakukan", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(DataInfrastrukturActivity.this, DisListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    //method menambahkan data infrastruktur pada database
    private void updateDataInfrastruktur(String DIS_ID, String rumahHancur, String rumahRusakBerat, String rumahRusakRingan) {
        String key = DIS_ID;
        mDataDis.child(key).child("rumahHancur").setValue(rumahHancur);
        mDataDis.child(key).child("rumahRusakBerat").setValue(rumahRusakBerat);
        mDataDis.child(key).child("rumahRusakRingan").setValue(rumahRusakRingan);
        mDataDis.child(key).child("isCompleted").setValue("true");
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

