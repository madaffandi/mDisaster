package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import achmadaffandi.mdisaster.Holder.DisList_Holder;
import achmadaffandi.mdisaster.Model.DisasterData;

public class DisListActivity extends AppCompatActivity {

    private DatabaseReference mDataDis, mDataRef;
    private RecyclerView recyclerView;
    private String userType;
    private FloatingActionButton fabCreateDL, fabToUserDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_list);
        //deklarasi semua komponen
        fabToUserDL = (FloatingActionButton) findViewById(R.id.fab_user_dislist);
        fabCreateDL = (FloatingActionButton) findViewById(R.id.fab_create_dislist);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //set recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(200);
        itemAnimator.setRemoveDuration(200);
        recyclerView.setItemAnimator(itemAnimator);
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.keepSynced(true);
        mDataDis = mDataRef.child("Disaster");
        //mendapatkan peran pengguna, untuk dilanjutkan ke intent berikutnya
        mDataRef.child("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("type")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        setUserType(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        //menuju inisiasi bencana dari fab terkait
        fabCreateDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DisListActivity.this, InitiateDisasterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        //menuju profil pengguna dari fab terkait
        fabToUserDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisListActivity.this, UserProfileActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mengatur RecyvlerView untuk bisa memuat data dari database
        FirebaseRecyclerAdapter<DisasterData, DisList_Holder> mAdapter = new FirebaseRecyclerAdapter<DisasterData, DisList_Holder>
                (DisasterData.class, R.layout.row_dis_list, DisList_Holder.class, mDataDis) {
            @Override
            protected void populateViewHolder(DisList_Holder viewHolder, DisasterData model, int position) {
                viewHolder.setTitle(model.getJenisBencana());
                viewHolder.setDesc(model.getKabupaten() + "\n" + model.getTanggalKejadian());
                viewHolder.callImage(getApplicationContext(), model.getJenisBencana());
            }

            @Override
            public DisList_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                DisList_Holder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new DisList_Holder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String DIS_ID = getRef(position).getKey();
                        Intent intent = new Intent(DisListActivity.this, DisReviewActivity.class);
                        intent.putExtra("DIS_ID", DIS_ID);
                        intent.putExtra("USER_TYPE", getUserType());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(DisListActivity.this, "Item long clicked at " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
