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

public class DisDetailActivity extends AppCompatActivity {

    private TextView tv_judulDis, tv_subJudulDis, tv_tglKejadian, tv_latlongLokasi, tv_alamatLokasi, tv_aksesTransportasi;
    private String receivedDisID;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_detail);
        tv_judulDis = (TextView) findViewById(R.id.tv_judulDisaster);
        tv_subJudulDis = (TextView) findViewById(R.id.tv_subJudulDisaster);
        tv_tglKejadian = (TextView) findViewById(R.id.tv_tglKejadian);
        tv_latlongLokasi = (TextView) findViewById(R.id.tv_latlongLokasi);
        tv_alamatLokasi = (TextView) findViewById(R.id.tv_alamatLokasi);
        tv_aksesTransportasi = (TextView) findViewById(R.id.tv_aksesTransportasi);

        receivedDisID = getIntent().getExtras().get("dis_id").toString();
        //Toast.makeText(DisDetailActivity.this, "Item long clicked at " + receivedDisID, Toast.LENGTH_SHORT).show();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Disaster");
        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(receivedDisID)) {
                        tv_judulDis.setText(ds.child("jenisBencana").getValue().toString());
                        tv_subJudulDis.setText(ds.child("kabupaten").getValue().toString());
                        tv_tglKejadian.setText(ds.child("tanggalKejadian").getValue().toString());
                        tv_latlongLokasi.setText(ds.child("latLokasi").getValue().toString()
                                + ", " + ds.child("longLokasi").getValue().toString());
                        tv_alamatLokasi.setText(ds.child("alamat").getValue().toString());
                        tv_aksesTransportasi.setText(ds.child("aksesTransportasi").getValue().toString()
                                + ", " + ds.child("alatTransportasi").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}