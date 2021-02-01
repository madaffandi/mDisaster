package achmadaffandi.mdisaster;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisDetailActivity extends AppCompatActivity {

    private TextView tv_judulDis, tv_subJudulDis, tv_tglKejadian, tv_latlongLokasi, tv_alamatLokasi,
            tv_aksesTransportasi, tv_keteranganLain, tv_kondisiListrik, tv_kondisiAir,
            tv_kondisiDrainase, tv_pengungsiBalita, tv_pengungsiAnak, tv_pengungsiRemaja,
            tv_pengungsiDewasa, tv_pengungsiLansia, tv_korbanMeninggal, tv_korbanHilang,
            tv_korbanLukaBerat, tv_korbanLukaRingan, tv_rumahHancur, tv_rumahRusakBerat,
            tv_rumahRusakRingan;
    private String DIS_ID;
    private DatabaseReference mDataRef, mDataDis;
    private Button btn_editBencana, btn_hapusBencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_detail);
        //deklarasi semua komponen
        tv_judulDis = (TextView) findViewById(R.id.tv_judulDisaster);
        tv_subJudulDis = (TextView) findViewById(R.id.tv_subJudulDisaster);
        tv_tglKejadian = (TextView) findViewById(R.id.tv_tglKejadian);
        tv_latlongLokasi = (TextView) findViewById(R.id.tv_latlongLokasi);
        tv_alamatLokasi = (TextView) findViewById(R.id.tv_alamatLokasi);
        tv_aksesTransportasi = (TextView) findViewById(R.id.tv_aksesTransportasi);
        tv_keteranganLain = (TextView) findViewById(R.id.tv_keteranganLain);
        tv_kondisiListrik = (TextView) findViewById(R.id.tv_kondisiListrik);
        tv_kondisiAir = (TextView) findViewById(R.id.tv_kondisiAir);
        tv_kondisiDrainase = (TextView) findViewById(R.id.tv_kondisiDrainase);
        tv_pengungsiBalita = (TextView) findViewById(R.id.tv_pengungsiBalita);
        tv_pengungsiAnak = (TextView) findViewById(R.id.tv_pengungsiAnak);
        tv_pengungsiRemaja = (TextView) findViewById(R.id.tv_pengungsiRemaja);
        tv_pengungsiDewasa = (TextView) findViewById(R.id.tv_pengungsiDewasa);
        tv_pengungsiLansia = (TextView) findViewById(R.id.tv_pengungsiLansia);
        tv_korbanMeninggal = (TextView) findViewById(R.id.tv_korbanMeninggal);
        tv_korbanHilang = (TextView) findViewById(R.id.tv_korbanHilang);
        tv_korbanLukaBerat = (TextView) findViewById(R.id.tv_korbanLukaBerat);
        tv_korbanLukaRingan = (TextView) findViewById(R.id.tv_korbanLukaRingan);
        tv_rumahHancur = (TextView) findViewById(R.id.tv_rumahHancur);
        tv_rumahRusakBerat = (TextView) findViewById(R.id.tv_rumahRusakBerat);
        tv_rumahRusakRingan = (TextView) findViewById(R.id.tv_rumahRusakRingan);
        btn_editBencana = (Button) findViewById(R.id.btn_editBencana);
        btn_hapusBencana = (Button) findViewById(R.id.btn_hapusBencana);
        //memanggil ekstra dari intent sebelumnya yaitu ID Bencana
        DIS_ID = getIntent().getExtras().get("DIS_ID").toString();
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.keepSynced(true);
        mDataDis = mDataRef.child("Disaster");
//memasukkan data dari database ke TextView terkait
        mDataDis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(DIS_ID)) {
                        tv_judulDis.setText(ds.child("jenisBencana").getValue().toString());
                        tv_subJudulDis.setText(ds.child("kabupaten").getValue().toString());
                        tv_tglKejadian.setText(ds.child("tanggalKejadian").getValue().toString());
                        tv_latlongLokasi.setText(ds.child("latLokasi").getValue().toString()
                                + ", " + ds.child("longLokasi").getValue().toString());
                        tv_alamatLokasi.setText(ds.child("alamat").getValue().toString());
                        tv_aksesTransportasi.setText(ds.child("aksesTransportasi").getValue().toString()
                                + ", " + ds.child("alatTransportasi").getValue().toString());
                        tv_keteranganLain.setText(ds.child("keteranganLain").getValue().toString());
                        tv_kondisiListrik.setText(ds.child("kondisiListrik").getValue().toString()
                                + ", " + ds.child("sumberListrik").getValue().toString());
                        tv_kondisiAir.setText(ds.child("kondisiAir").getValue().toString()
                                + ", " + ds.child("sumberAir").getValue().toString());
                        tv_kondisiDrainase.setText(ds.child("kondisiDrainase").getValue().toString()
                                + ", " + ds.child("jumlahJamban").getValue().toString() + " jamban");
                        tv_pengungsiBalita.setText(ds.child("lakiBalita").getValue().toString()
                                + " laki-laki, \n" + ds.child("perempuanBalita").getValue().toString()
                                + " perempuan");
                        tv_pengungsiAnak.setText(ds.child("lakiAnak").getValue().toString()
                                + " laki-laki, \n" + ds.child("perempuanAnak").getValue().toString()
                                + " perempuan");
                        tv_pengungsiRemaja.setText(ds.child("lakiRemaja").getValue().toString()
                                + " laki-laki, \n" + ds.child("perempuanRemaja").getValue().toString()
                                + " perempuan");
                        tv_pengungsiDewasa.setText(ds.child("lakiDewasa").getValue().toString()
                                + " laki-laki, \n" + ds.child("perempuanDewasa").getValue().toString()
                                + " perempuan");
                        tv_pengungsiLansia.setText(ds.child("lakiLansia").getValue().toString()
                                + " laki-laki, \n" + ds.child("perempuanLansia").getValue().toString()
                                + " perempuan");
                        tv_korbanMeninggal.setText(ds.child("korbanMeninggal").getValue().toString()
                                + " orang");
                        tv_korbanHilang.setText(ds.child("korbanHilang").getValue().toString() + " orang");
                        tv_korbanLukaBerat.setText(ds.child("korbanLukaBerat").getValue().toString() + " orang");
                        tv_korbanLukaRingan.setText(ds.child("korbanLukaRingan").getValue().toString() + " orang");
                        tv_rumahHancur.setText(ds.child("rumahHancur").getValue().toString());
                        tv_rumahRusakBerat.setText(ds.child("rumahRusakBerat").getValue().toString());
                        tv_rumahRusakRingan.setText(ds.child("rumahRusakRingan").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //mengarahkan edit bencana
        btn_editBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisDetailActivity.this, CreateReviewDisasterActivity.class);
                i.putExtra("DIS_ID", DIS_ID);
                startActivity(i);
            }
        });
        //memanggil fungsi konfirmasi pengapusan data
        btn_hapusBencana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });
    }

    //fungsi konfirmasi penghapusan data
    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Hapus")
                .setMessage("apakah Anda yakin untuk menghapus data ini?")
                //.setIcon(R.drawable.delete)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //menghapus data dari database
                        mDataDis.child(DIS_ID).removeValue();
                        Intent i = new Intent(DisDetailActivity.this, DisListActivity.class);
                        startActivity(i);
                        Toast.makeText(DisDetailActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
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