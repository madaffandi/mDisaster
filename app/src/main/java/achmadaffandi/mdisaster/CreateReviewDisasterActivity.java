package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateReviewDisasterActivity extends AppCompatActivity {

    private TextView tv_judulDis, tv_judulSubDis, tv_judulDesDis;
    private EditText et_sumberListrik, et_sumberAir, et_jamban;
    private RadioGroup rg_listrik, rg_air, rg_drainase;
    private RadioButton rb_listrik, rb_air, rb_drainase;
    private Button btn_toPengungsi;
    private String DIS_ID, kondisiListrik, kondisiAir, kondisiDrainase, sumberListrik, sumberAir, jumlahJamban;
    private int selectedListrik, selectedAir, selectedDrainase;
    private DatabaseReference mDatabase, mDatabaseDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review_disaster);
        tv_judulDis = (TextView) findViewById(R.id.tv_judulDis);
        tv_judulSubDis = (TextView) findViewById(R.id.tv_judulSubDis);
        tv_judulDesDis = (TextView) findViewById(R.id.tv_judulDesDis);
        et_sumberListrik = (EditText) findViewById(R.id.et_sumberListrik);
        et_sumberAir = (EditText) findViewById(R.id.et_sumberAir);
        et_jamban = (EditText) findViewById(R.id.et_jamban);
        btn_toPengungsi = (Button) findViewById(R.id.btn_toPengungsi);
        rg_listrik = (RadioGroup) findViewById(R.id.rg_listrik);
        rg_air = (RadioGroup) findViewById(R.id.rg_air);
        rg_drainase = (RadioGroup) findViewById(R.id.rg_drainase);

        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseDis = mDatabase.child("Disaster");
        mDatabaseDis.keepSynced(true);

        mDatabaseDis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(DIS_ID)) {
                        tv_judulDis.setText(ds.child("jenisBencana").getValue().toString());
                        tv_judulSubDis.setText(ds.child("kabupaten").getValue().toString());
                        tv_judulDesDis.setText(ds.child("tanggalKejadian").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        selectedListrik = rg_listrik.getCheckedRadioButtonId();
        rb_listrik = (RadioButton)findViewById(selectedListrik);
        kondisiListrik = rb_listrik.getText().toString();
        selectedAir = rg_air.getCheckedRadioButtonId();
        rb_air = (RadioButton)findViewById(selectedAir);
        kondisiAir = rb_air.getText().toString();
        selectedDrainase = rg_drainase.getCheckedRadioButtonId();
        rb_drainase = (RadioButton)findViewById(selectedDrainase);
        kondisiDrainase = rb_drainase.getText().toString();

        btn_toPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReviewBencana();
            }
        });
    }

    private void updateReviewBencana(){
        setSumberListrik(et_sumberListrik.getText().toString());
        setSumberAir(et_sumberAir.getText().toString());
        setJumlahJamban(et_jamban.getText().toString());
        if (getSumberListrik().isEmpty()) {
            et_sumberListrik.setError(getString(R.string.input_error_sumberlistrik));
            et_sumberListrik.requestFocus();
            return;
        }
        if (getSumberAir().isEmpty()) {
            et_sumberAir.setError(getString(R.string.input_error_sumberair));
            et_sumberAir.requestFocus();
            return;
        }
        if (getJumlahJamban().isEmpty()) {
            et_jamban.setError(getString(R.string.input_error_jamban));
            et_jamban.requestFocus();
            return;
        }
        updateDataBencana(DIS_ID, kondisiListrik, getSumberListrik(), kondisiAir, getSumberAir(), kondisiDrainase, getJumlahJamban());
        Intent i = new Intent(CreateReviewDisasterActivity.this, DataPengungsiActivity.class);
        i.putExtra("DIS_ID", DIS_ID);
        startActivity(i);
    }

    private void updateDataBencana(String DIS_ID, String kondisiListrik,
                                      String sumberListrik, String kondisiAir, String sumberAir,
                                      String kondisiDrainase, String jumlahJamban){
        String key = DIS_ID;
        mDatabase.child("Disaster").child(key).child("kondisiListrik").setValue(kondisiListrik);
        mDatabase.child("Disaster").child(key).child("sumberListrik").setValue(sumberListrik);
        mDatabase.child("Disaster").child(key).child("kondisiAir").setValue(kondisiAir);
        mDatabase.child("Disaster").child(key).child("sumberAir").setValue(sumberAir);
        mDatabase.child("Disaster").child(key).child("kondisiDrainase").setValue(kondisiDrainase);
        mDatabase.child("Disaster").child(key).child("jumlahJamban").setValue(jumlahJamban);
    }

    public String getSumberListrik() {
        return sumberListrik;
    }

    public void setSumberListrik(String sumberListrik) {
        this.sumberListrik = sumberListrik;
    }

    public String getSumberAir() {
        return sumberAir;
    }

    public void setSumberAir(String sumberAir) {
        this.sumberAir = sumberAir;
    }

    public String getJumlahJamban() {
        return jumlahJamban;
    }

    public void setJumlahJamban(String jumlahJamban) {
        this.jumlahJamban = jumlahJamban;
    }
}

