package achmadaffandi.mdisaster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import achmadaffandi.mdisaster.Model.DisasterData;

public class CreateDisasterActivity extends AppCompatActivity {

    private Button btn_createDis, btn_toDisLoc;
    private EditText et_cd_calendar, et_jenisBencana, et_ketLain;
    private RadioGroup rg_aksestrans;
    private RadioButton rb_aksestrans;
    private Spinner sp_alattrans;
    private String jenisBencana, tglKejadian, latLokasi, longLokasi, alamat, kabupaten, aksesTrans, alatTrans, ketLain;
    private int selectedTrans;
    private DatabaseReference mDatabase;
    private TextView tvDisType, tvLatLok, tvLongLok, tvAlamat;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_disaster);
        //deklarasi semua komponen
        tvDisType = (TextView) findViewById(R.id.tv_distype);
        et_jenisBencana = (EditText) findViewById(R.id.et_jenisBencana);
        et_cd_calendar = (EditText) findViewById(R.id.et_cd_calendar);
        btn_toDisLoc = (Button) findViewById(R.id.btn_toDisLoc);
        tvLatLok = (TextView) findViewById(R.id.tv_lat);
        tvLongLok = (TextView) findViewById(R.id.tv_lng);
        tvAlamat = (TextView) findViewById(R.id.tv_alamat);
        rg_aksestrans = (RadioGroup) findViewById(R.id.rg_aksestrans);
        sp_alattrans = (Spinner) findViewById(R.id.sp_alattrans);
        et_ketLain = (EditText) findViewById(R.id.et_ketLain);
        btn_createDis = (Button) findViewById(R.id.btn_createDis);
        //menghilangkan TextView alamat untuk sementara
        tvLatLok.setVisibility(View.GONE);
        tvLongLok.setVisibility(View.GONE);
        tvAlamat.setVisibility(View.GONE);
        //memanggil extra dari intent sebelumnya yaitu tipe bencana
        Intent i = getIntent();
        String disType = i.getStringExtra(InitiateDisasterActivity.KEY_DISTYPE);
        if (!disType.equals("Lain-lain")) {
            //memuat jenis bencana ke TextView
            setJenisBencana(disType);
            tvDisType.setText(getJenisBencana());
            et_jenisBencana.setVisibility(View.GONE);
        } else {
            //memunculkan EditText untuk jenis bencana yang belum didefinisikan (lain-lain)
            tvDisType.setVisibility(View.GONE);
            setJenisBencana(et_jenisBencana.getText().toString());
        }
        //inisiasi calendar
        et_cd_calendar.setInputType(InputType.TYPE_NULL);
        et_cd_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(et_cd_calendar);
            }
        });
        //memanggil PlacePicker dari Button
        btn_toDisLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(CreateDisasterActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        //mendapatkan data akses transportasi
        selectedTrans = rg_aksestrans.getCheckedRadioButtonId();
        rb_aksestrans = (RadioButton) findViewById(selectedTrans);
        //memuat spinner alat transportasi
        ArrayAdapter adapterAlatTrans = ArrayAdapter.createFromResource(
                CreateDisasterActivity.this,
                R.array.alat_trans,
                R.layout.spinner_layout);
        adapterAlatTrans.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        sp_alattrans.setAdapter(adapterAlatTrans);
        //memanggil fungsi membuat bencana baru dari button
        btn_createDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDisaster();
            }
        });
    }

    //menampilkan Date Dialog, pengaturan tanggal kejadian bencana
    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));
                setTglKejadian(date_in.getText().toString());
            }
        };
        new DatePickerDialog(CreateDisasterActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //fungsi membuat bencana baru
    public void createNewDisaster() {
        //memanggil data yang belum dipanggil di onCreate
        setAksesTrans(rb_aksestrans.getText().toString());
        setAlatTrans(sp_alattrans.getSelectedItem().toString());
        setKetLain(et_ketLain.getText().toString());
        //melakukan pengecekan terhadap setiap data yang dimasukkan
        if (getJenisBencana() == null) {
            et_jenisBencana.setError(getString(R.string.input_error_jenisbencana));
            et_jenisBencana.requestFocus();
            return;
        } else if (getTglKejadian() == null) {
            Toast.makeText(CreateDisasterActivity.this, R.string.input_error_tanggal, Toast.LENGTH_LONG).show();
        } else if (getAlamat() == null) {
            Toast.makeText(CreateDisasterActivity.this, R.string.input_error_lokasi, Toast.LENGTH_LONG).show();
        } else if (getAksesTrans().isEmpty()) {
            Toast.makeText(CreateDisasterActivity.this, R.string.input_error_aksestrans, Toast.LENGTH_LONG).show();
        } else if (getAlatTrans().isEmpty()) {
            Toast.makeText(CreateDisasterActivity.this, R.string.input_error_alattrans, Toast.LENGTH_LONG).show();
        } else if (getKetLain().isEmpty()) {
            setKetLain("-");
            inputDatabse();
        } else {
            inputDatabse();
        }
    }

    //fungsi memasukkan data bencana awal ke database
    public void inputDatabse() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Disaster").push();
        DisasterData dData = new DisasterData(getJenisBencana(), getTglKejadian(),
                getLatLokasi(), getLongLokasi(), getAlamat(), getKabupaten(), getAksesTrans(),
                getAlatTrans(), getKetLain(), "false");
        mDatabase.setValue(dData);
        Toast.makeText(CreateDisasterActivity.this,
                "Data bencana baru telah ditambahkan", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CreateDisasterActivity.this, DisListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    //mendapatkan lokasi dari PlacePicker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                if (place != null) {
                    try {
                        Geocoder geocoder = new Geocoder(CreateDisasterActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                place.getLatLng().latitude, place.getLatLng().longitude, 1
                        );
                        setLatLokasi(String.valueOf(addresses.get(0).getLatitude()));
                        setLongLokasi(String.valueOf(addresses.get(0).getLongitude()));
                        setAlamat(String.valueOf(addresses.get(0).getAddressLine(0)));
                        setKabupaten(String.valueOf(addresses.get(0).getLocality()));
                        tvLongLok.setVisibility(View.VISIBLE);
                        tvLatLok.setVisibility(View.VISIBLE);
                        tvAlamat.setVisibility(View.VISIBLE);
                        tvLatLok.setText(getLongLokasi());
                        tvLongLok.setText(getLatLokasi());
                        tvAlamat.setText(getAlamat());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //setter dan getter yang diperlukan untuk memuat objek
    public String getKetLain() {
        return ketLain;
    }

    public void setKetLain(String ketLain) {
        this.ketLain = ketLain;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getLatLokasi() {
        return latLokasi;
    }

    public void setLatLokasi(String latLokasi) {
        this.latLokasi = latLokasi;
    }

    public String getLongLokasi() {
        return longLokasi;
    }

    public void setLongLokasi(String longLokasi) {
        this.longLokasi = longLokasi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAksesTrans() {
        return aksesTrans;
    }

    public void setAksesTrans(String aksesTrans) {
        this.aksesTrans = aksesTrans;
    }

    public String getAlatTrans() {
        return alatTrans;
    }

    public void setAlatTrans(String alatTrans) {
        this.alatTrans = alatTrans;
    }

    public String getJenisBencana() {
        return jenisBencana;
    }

    public void setJenisBencana(String jenisBencana) {
        this.jenisBencana = jenisBencana;
    }

    public String getTglKejadian() {
        return tglKejadian;
    }

    public void setTglKejadian(String tglKejadian) {
        this.tglKejadian = tglKejadian;
    }

}