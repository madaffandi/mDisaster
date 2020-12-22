package achmadaffandi.mdisaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import achmadaffandi.mdisaster.Holder.DisList_Holder;
import achmadaffandi.mdisaster.Model.DisasterData;

public class DisListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(200);
        itemAnimator.setRemoveDuration(200);
        recyclerView.setItemAnimator(itemAnimator);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Disaster");
        mDatabase.keepSynced(true);

        FloatingActionButton fab = findViewById(R.id.fab_create_dislist);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DisListActivity.this, InitiateDisasterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<DisasterData, DisList_Holder> mAdapter = new FirebaseRecyclerAdapter<DisasterData, DisList_Holder>
                (DisasterData.class, R.layout.row_dis_list, DisList_Holder.class, mDatabase) {
            @Override
            protected void populateViewHolder(DisList_Holder viewHolder, DisasterData model, int position) {
                viewHolder.setTitle(model.getJenisBencana());
                viewHolder.setDesc("Terjadi: " + model.getTanggalKejadian() + ", Akses Transportasi: " + model.getAksesTransportasi() + model.getAlatTransportasi());
                viewHolder.setImage(getApplicationContext(), model.getImageId());
            }

            @Override
            public DisList_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                DisList_Holder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new DisList_Holder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(DisListActivity.this, "Item clicked at " + position, Toast.LENGTH_SHORT).show();
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
}
