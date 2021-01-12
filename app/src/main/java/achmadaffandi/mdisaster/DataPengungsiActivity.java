package achmadaffandi.mdisaster;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataPengungsiActivity extends AppCompatActivity {

    private TextView tv_judulBenPeng, tv_judulSubBenPeng, tv_judulDesBenPeng;
    private String DIS_ID;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengungsi);
        tv_judulBenPeng = (TextView)findViewById(R.id.tv_judulBenPeng);
        tv_judulSubBenPeng = (TextView)findViewById(R.id.tv_judulSubBenPeng);
        tv_judulDesBenPeng = (TextView)findViewById(R.id.tv_judulDesBenPeng);

        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Disaster");
        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
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

    }
}

