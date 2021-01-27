package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardDisasterActivity extends AppCompatActivity {

    private DatabaseReference mDataDis;
    private GridLayout gridLayout;
    private int rumah, balita, lansia, perempuan, pengungsi;
    private String jumlahBencana;
    private TextView tv_jumlahBencana, tv_jumlahRumahHancur, tv_jumlahPengungsi, tv_jumlahPengungsiLansia, tv_jumlahPengungsiBalita, tv_jumlahPengungsiPerempuan;
    private FloatingActionButton fabCreateDisDas, fabToUserDas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_disaster);
        //deklarasi semua komponen
        gridLayout = (GridLayout) findViewById(R.id.mainGrid);
        tv_jumlahBencana = (TextView) findViewById(R.id.tv_jumlahBencana);
        tv_jumlahRumahHancur = (TextView) findViewById(R.id.tv_jumlahRumahHancur);
        tv_jumlahPengungsi = (TextView) findViewById(R.id.tv_jumlahPengungsi);
        tv_jumlahPengungsiLansia = (TextView) findViewById(R.id.tv_jumlahPengungsiLansia);
        tv_jumlahPengungsiBalita = (TextView) findViewById(R.id.tv_jumlahPengungsiBalita);
        tv_jumlahPengungsiPerempuan = (TextView) findViewById(R.id.tv_jumlahPengungsiPerempuan);
        fabCreateDisDas = (FloatingActionButton) findViewById(R.id.fab_create_dashboard);
        fabToUserDas = (FloatingActionButton)findViewById(R.id.fab_user_dashboard);
        setSingleEvent(gridLayout);
        mDataDis = FirebaseDatabase.getInstance().getReference().child("Disaster");
        //mendapatkan data jumlah bencana dengan menghitung ID/key dari child Disaster
        mDataDis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                count = (int) dataSnapshot.getChildrenCount();
                setJumlahBencana(String.valueOf(count));
                tv_jumlahBencana.setText(getJumlahBencana());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDataDis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //mendata jumlah rumah hancur
                rumah = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("rumahHancur").getValue() != null) {
                        rumah += Integer.valueOf(data.child("rumahHancur").getValue().toString());
                    }
                    tv_jumlahRumahHancur.setText(Integer.toString(rumah));
                }
                //mendata jumlah pengungsi balita baik perempuan maupun laki-laki
                balita = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("lakiBalita").getValue() != null) {
                        balita += Integer.valueOf(data.child("lakiBalita").getValue().toString());
                    }
                    if (data.child("perempuanBalita").getValue() != null) {
                        balita += Integer.valueOf(data.child("perempuanBalita").getValue().toString());
                    }
                    tv_jumlahPengungsiBalita.setText(Integer.toString(balita));
                }
                //mendata jumlah pengungsi perempuan dewasa dan remaja
                perempuan = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("perempuanRemaja").getValue() != null) {
                        perempuan += Integer.valueOf(data.child("perempuanRemaja").getValue().toString());
                    }
                    if (data.child("perempuanDewasa").getValue() != null) {
                        perempuan += Integer.valueOf(data.child("perempuanDewasa").getValue().toString());
                    }
                    tv_jumlahPengungsiPerempuan.setText(Integer.toString(perempuan));
                }
                //mendata jumlah pengungsi lansia baik perempuan maupun laki-laki
                lansia = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("lakiLansia").getValue() != null) {
                        lansia += Integer.valueOf(data.child("lakiLansia").getValue().toString());
                    }
                    if (data.child("perempuanLansia").getValue() != null) {
                        lansia += Integer.valueOf(data.child("perempuanLansia").getValue().toString());
                    }
                    tv_jumlahPengungsiLansia.setText(Integer.toString(lansia));
                }
                //mendata jumlah total pengungsi
                pengungsi = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("lakiAnak").getValue() != null) {
                        pengungsi += Integer.valueOf(data.child("lakiAnak").getValue().toString());
                    }
                    if (data.child("perempuanAnak").getValue() != null) {
                        pengungsi += Integer.valueOf(data.child("perempuanAnak").getValue().toString());
                    }
                    if (data.child("lakiRemaja").getValue() != null) {
                        pengungsi += Integer.valueOf(data.child("lakiRemaja").getValue().toString());
                    }
                    if (data.child("lakiDewasa").getValue() != null) {
                        pengungsi += Integer.valueOf(data.child("lakiDewasa").getValue().toString());
                    }
                    pengungsi = pengungsi + balita + perempuan + lansia;
                    tv_jumlahPengungsi.setText(Integer.toString(pengungsi));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //menuju inisiasi bencana dari fab terkait
        fabCreateDisDas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardDisasterActivity.this, InitiateDisasterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        //menuju profil pengguna dari fab terkait
        fabToUserDas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardDisasterActivity.this, UserProfileActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
    //mengarahkan ke DisListActivity untuk setiap card yang diklik
    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(DashboardDisasterActivity.this, DisListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
        }
    }

    public String getJumlahBencana() {
        return jumlahBencana;
    }

    public void setJumlahBencana(String jumlahBencana) {
        this.jumlahBencana = jumlahBencana;
    }
}
