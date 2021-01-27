package achmadaffandi.mdisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

public class InitiateDisasterActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    public static final String KEY_DISTYPE = "disaster_type";
    private String disasterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_disaster);
        gridLayout = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(gridLayout);
    }

    //onClickListener untuk jenis bencana terpilih
    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callDisasterType(finalI);
                    Intent i = new Intent(InitiateDisasterActivity.this, CreateDisasterActivity.class);
                    i.putExtra(KEY_DISTYPE, getDisasterType());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
        }
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    private void callDisasterType(int i) {
        if (i == 0) {
            setDisasterType("Gempa");
        } else if (i == 1) {
            setDisasterType("Kebakaran");
        } else if (i == 2) {
            setDisasterType("Banjir");
        } else if (i == 3) {
            setDisasterType("Tanah Longsor");
        } else if (i == 4) {
            setDisasterType("Erupsi");
        } else if (i == 5) {
            setDisasterType("Lain-lain");
        }
    }
}
