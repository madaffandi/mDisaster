package achmadaffandi.mdisaster;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisDetailActivity extends AppCompatActivity {

    private TextView tv_judulDisaster, tv_tglKejadian, tv_latlongLokasi, tv_alamatLokasi, tv_aksesTransportasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_detail);
        tv_judulDisaster = (TextView) findViewById(R.id.tv_judulDisaster);
        tv_tglKejadian = (TextView) findViewById(R.id.tv_tglKejadian);
        tv_latlongLokasi = (TextView) findViewById(R.id.tv_latlongLokasi);
        tv_alamatLokasi = (TextView) findViewById(R.id.tv_alamatLokasi);
        tv_aksesTransportasi = (TextView) findViewById(R.id.tv_aksesTransportasi);

    }
}