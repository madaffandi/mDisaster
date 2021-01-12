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

public class CreateDetailDisasterActivity extends AppCompatActivity {

    private TextView tv_judulDis, tv_judulSubDis, tv_judulDesDis;
    private EditText et_sumberListrik, et_sumberAir, et_jamban;
    private RadioGroup rg_listrik, rg_air, rg_drainase;
    private RadioButton rb_listrik, rb_air, rb_drainase;
    private Button btn_toPengungsi;
    private String DIS_ID, kondisiListrik, kondisiAir, kondisiDrainase, sumberListrik, sumberAir, jumlahJamban;
    private int selectedListrik, selectedAir, selectedDrainase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_detail_disaster);
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Disaster");
        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
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
        /*
        selectedListrik = rg_listrik.getCheckedRadioButtonId();
        rb_listrik = (RadioButton)findViewById(selectedListrik);
        kondisiListrik = rb_listrik.getText().toString();
        selectedAir = rg_air.getCheckedRadioButtonId();
        rb_air = (RadioButton)findViewById(selectedAir);
        kondisiAir = rb_air.getText().toString();
        selectedDrainase = rg_drainase.getCheckedRadioButtonId();
        rb_drainase = (RadioButton)findViewById(selectedDrainase);
        kondisiDrainase = rb_drainase.getText().toString();*/
        sumberListrik = et_sumberListrik.getText().toString();
        sumberAir = et_sumberAir.getText().toString();
        jumlahJamban = et_jamban.getText().toString();

        btn_toPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateKondisiBencana(DIS_ID, kondisiListrik, sumberListrik, kondisiAir, sumberAir, kondisiDrainase, jumlahJamban);
                Intent i = new Intent(CreateDetailDisasterActivity.this, DataPengungsiActivity.class);
                i.putExtra("DIS_ID", DIS_ID);
                startActivity(i);
            }
        });
    }
/*    private void updateKondisiBencana(String DIS_ID, String kondisiListrik, String sumberListrik, String kondisiAir, String sumberAir, String kondisiDrainase, String jumlahJamban){
        String key = DIS_ID;
        DisasterData disasterData = new DisasterData(kondisiListrik, sumberListrik, kondisiAir, sumberAir, kondisiDrainase, jumlahJamban);
        Map<String, Object> dataValues = disasterData.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Disaster/" + key, dataValues);
        mDatabase.updateChildren(childUpdates);
    }*/
}

