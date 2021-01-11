package achmadaffandi.mdisaster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

    private Button btn_backInitateDis, btn_createDis, btn_toDisLoc;
    private EditText et_cd_calendar, et_jenisBencana;
    private AutoCompleteTextView ac_aksestrans, ac_alattrans;
    private String[] arrAksesTrans, arrAlatTrans;
    private String jenisBencana, tglKejadian, latLokasi, longLokasi, alamat, kabupaten, aksesTrans, alatTrans;
    private DatabaseReference mDatabase;
    private TextView tvDisType, tvLatLok, tvLongLok, tvAlamat;
    private FusedLocationProviderClient fusedLocationProviderClient;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_disaster);
        btn_backInitateDis = (Button) findViewById(R.id.btn_backInitiateDis);
        btn_createDis = (Button) findViewById(R.id.btn_createDis);
        et_cd_calendar = (EditText) findViewById(R.id.et_cd_calendar);
        ac_aksestrans = (AutoCompleteTextView) findViewById(R.id.cd_aksestrans);
        arrAksesTrans = getResources().getStringArray(R.array.akses_trans);
        ac_alattrans = (AutoCompleteTextView) findViewById(R.id.cd_alattrans);
        arrAlatTrans = getResources().getStringArray(R.array.alat_trans);
        btn_toDisLoc = (Button) findViewById(R.id.btn_toDisLoc);
        tvDisType = (TextView) findViewById(R.id.tv_distype);
        tvLatLok = (TextView) findViewById(R.id.tv_lat);
        tvLongLok = (TextView) findViewById(R.id.tv_lng);
        tvAlamat = (TextView) findViewById(R.id.tv_alamat);
        et_jenisBencana = (EditText) findViewById(R.id.et_jenisBencana);

        Intent i = getIntent();
        String disType = i.getStringExtra(InitiateDisasterActivity.KEY_DISTYPE);
        if (disType != "Lain-lain") {
            setJenisBencana(disType);
            tvDisType.setText(getJenisBencana());
            et_jenisBencana.setVisibility(View.INVISIBLE);
        } else {
            tvDisType.setVisibility(View.INVISIBLE);
            setJenisBencana(et_jenisBencana.getText().toString());
        }

        et_cd_calendar.setInputType(InputType.TYPE_NULL);
        et_cd_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(et_cd_calendar);
            }
        });

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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        tvLatLok.setText(getLatLokasi());
        tvLongLok.setText(getLongLokasi());
        tvAlamat.setText(getAlamat());

        ArrayAdapter<String> adapAksesTrans = new ArrayAdapter<String>(CreateDisasterActivity.this,
                android.R.layout.simple_list_item_1, arrAksesTrans);
        ac_aksestrans.setAdapter(adapAksesTrans);
        ac_aksestrans.setThreshold(1);
        ac_aksestrans.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ac_aksestrans.showDropDown();
                }
            }
        });

        ArrayAdapter<String> adapAlatTrans = new ArrayAdapter<String>(CreateDisasterActivity.this,
                android.R.layout.simple_list_item_1, arrAlatTrans);
        ac_alattrans.setAdapter(adapAlatTrans);
        ac_alattrans.setThreshold(1);
        ac_alattrans.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ac_alattrans.showDropDown();
                }
            }
        });

        btn_createDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDisaster();
            }
        });

        btn_backInitateDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateDisasterActivity.this, InitiateDisasterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

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

    public void createNewDisaster() {
        setAksesTrans(ac_aksestrans.getText().toString());
        setAlatTrans(ac_alattrans.getText().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Disaster").push();
        DisasterData dData = new DisasterData(getJenisBencana(), getTglKejadian(), getLatLokasi(), getLongLokasi(), getAlamat(), getKabupaten(), getAksesTrans(), getAlatTrans());
        mDatabase.setValue(dData);
        Toast.makeText(CreateDisasterActivity.this, "Data bencana baru telah ditambahkan", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CreateDisasterActivity.this, DisListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

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