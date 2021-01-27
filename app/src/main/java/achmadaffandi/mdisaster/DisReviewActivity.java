package achmadaffandi.mdisaster;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisReviewActivity extends AppCompatActivity {

    private TextView tv_judulRevDis, tv_subJudulRevDis, tv_tglRevDisKejadian, tv_latlongRevDisLokasi, tv_alamatRevDisLokasi, tv_aksesRevDisTransportasi;
    private String DIS_ID, USER_TYPE;
    private DatabaseReference mDataRef, mDataDis;
    private Button btn_lihatBencana, btn_editBencana, btn_hapusBencana;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_review);
        //deklarasi semua komponen
        tv_judulRevDis = (TextView) findViewById(R.id.tv_judulRevDis);
        tv_subJudulRevDis = (TextView) findViewById(R.id.tv_subJudulRevDis);
        tv_tglRevDisKejadian = (TextView) findViewById(R.id.tv_tglRevDisKejadian);
        tv_latlongRevDisLokasi = (TextView) findViewById(R.id.tv_latlongRevDisLokasi);
        tv_alamatRevDisLokasi = (TextView) findViewById(R.id.tv_alamatRevDisLokasi);
        tv_aksesRevDisTransportasi = (TextView) findViewById(R.id.tv_aksesRevDisTransportasi);
        btn_lihatBencana = (Button) findViewById(R.id.btn_lihatBencana);
        btn_editBencana = (Button) findViewById(R.id.btn_editRevBencana);
        btn_hapusBencana = (Button) findViewById(R.id.btn_hapusRevBencana);
        FloatingActionButton fab = findViewById(R.id.fab_assign_user);
        //menginisiasi fitur yang tidak dapat diakses dari peran tertentu untuk ditiadakan sementara
        fab.setVisibility(View.GONE);
        btn_editBencana.setVisibility(View.GONE);
        btn_hapusBencana.setVisibility(View.GONE);
        btn_lihatBencana.setVisibility(View.GONE);
        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        USER_TYPE = getIntent().getExtras().get("USER_TYPE").toString();
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.keepSynced(true);
        mDataDis = mDataRef.child("Disaster");
        //menampilkan informasi umum
        mDataDis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(DIS_ID)) {
                        tv_judulRevDis.setText(ds.child("jenisBencana").getValue().toString());
                        tv_subJudulRevDis.setText(ds.child("kabupaten").getValue().toString());
                        tv_tglRevDisKejadian.setText(ds.child("tanggalKejadian").getValue().toString());
                        tv_latlongRevDisLokasi.setText(ds.child("latLokasi").getValue().toString()
                                + ", " + ds.child("longLokasi").getValue().toString());
                        tv_alamatRevDisLokasi.setText(ds.child("alamat").getValue().toString());
                        tv_aksesRevDisTransportasi.setText(ds.child("aksesTransportasi").getValue().toString()
                                + ", " + ds.child("alatTransportasi").getValue().toString());
                        //hanya menampilkan tombol detail apabila data bencana sudah lengkap
                        if(ds.child("isCompleted").getValue().toString().equals("true")){
                            btn_lihatBencana.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //melakukan pengecekan apakah pengguna ditugaskan pada bencana terkait
        FirebaseDatabase.getInstance().getReference().child("UserAssign").child(DIS_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String tmp1 = ds.getKey();
                    String tmp2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (tmp1.equals(tmp2)) {
                        btn_editBencana.setVisibility(View.VISIBLE);
                        btn_hapusBencana.setVisibility(View.VISIBLE);
                    } else {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //menuju detail bencana
        btn_lihatBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisReviewActivity.this, DisDetailActivity.class);
                i.putExtra("DIS_ID", DIS_ID);
                startActivity(i);
            }
        });
        //menuju edit bencana
        btn_editBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisReviewActivity.this, CreateReviewDisasterActivity.class);
                i.putExtra("DIS_ID", DIS_ID);
                startActivity(i);
            }
        });
        //tombol hapus bencana, memanggil konfirmasi
        btn_hapusBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });
        //jika tipe pengguna adalah admin, maka semua fitur terbuka kembali
        if (USER_TYPE.equals("mainAdmin")) {
            fab.setVisibility(View.VISIBLE);
            btn_editBencana.setVisibility(View.VISIBLE);
            btn_hapusBencana.setVisibility(View.VISIBLE);
        }
        //membuat pengguna baru untuk bencana terkait
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisReviewActivity.this, UserCreationActivity.class);
                i.putExtra("DIS_ID", DIS_ID);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    //konfirmasi penghapusan data
    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Hapus")
                .setMessage("apakah Anda yakin untuk menghapus data ini?")
                //.setIcon(R.drawable.delete)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //menghapus data dari database
                        mDataDis.child(DIS_ID).removeValue();
                        Intent i = new Intent(DisReviewActivity.this, DisListActivity.class);
                        startActivity(i);
                        Toast.makeText(DisReviewActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }
}