package achmadaffandi.mdisaster.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class DisasterData {
    private String title, description, imageId;
    private String jenisBencana, latLokasi, longLokasi, alamat, kabupaten, tanggalKejadian, aksesTransportasi, alatTransportasi;
    private String kondisiListrik, sumberListrik, kondisiAir, sumberAir, kondisiDrainase, jumlahJamban;
    public Map<String, Boolean> stars = new HashMap<>();

    public DisasterData(String title, String description, String imageId) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public DisasterData(String jenisBencana, String tanggalKejadian, String latLokasi,
                        String longLokasi, String alamat, String kabupaten, String aksesTransportasi, String alatTransportasi){
        this.jenisBencana = jenisBencana;
        this.latLokasi = latLokasi;
        this.longLokasi = longLokasi;
        this.alamat = alamat;
        this.kabupaten = kabupaten;
        this.tanggalKejadian = tanggalKejadian;
        this.aksesTransportasi = aksesTransportasi;
        this.alatTransportasi = alatTransportasi;
    }

    public DisasterData(String kondisiListrik, String sumberListrik, String kondisiAir, String sumberAir, String kondisiDrainase, String jumlahJamban){
        this.kondisiListrik = kondisiListrik;
        this.sumberListrik = sumberListrik;
        this.kondisiAir = kondisiAir;
        this.sumberAir = sumberAir;
        this.kondisiDrainase = kondisiDrainase;
        this.jumlahJamban = jumlahJamban;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("kondisiListrik", getKondisiListrik());
        result.put("sumberListrik", getSumberListrik());
        result.put("kondisiAir", getKondisiAir());
        result.put("sumberAir", getSumberAir());
        result.put("kondisiDrainase", getKondisiDrainase());
        result.put("jumlahJamban", getJumlahJamban());

        return result;
    }

    public DisasterData() {

    }

    public String getKondisiListrik() {
        return kondisiListrik;
    }

    public void setKondisiListrik(String kondisiListrik) {
        this.kondisiListrik = kondisiListrik;
    }

    public String getSumberListrik() {
        return sumberListrik;
    }

    public void setSumberListrik(String sumberListrik) {
        this.sumberListrik = sumberListrik;
    }

    public String getKondisiAir() {
        return kondisiAir;
    }

    public void setKondisiAir(String kondisiAir) {
        this.kondisiAir = kondisiAir;
    }

    public String getSumberAir() {
        return sumberAir;
    }

    public void setSumberAir(String sumberAir) {
        this.sumberAir = sumberAir;
    }

    public String getKondisiDrainase() {
        return kondisiDrainase;
    }

    public void setKondisiDrainase(String kondisiDrainase) {
        this.kondisiDrainase = kondisiDrainase;
    }

    public String getJumlahJamban() {
        return jumlahJamban;
    }

    public void setJumlahJamban(String jumlahJamban) {
        this.jumlahJamban = jumlahJamban;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getJenisBencana() {
        return jenisBencana;
    }

    public void setJenisBencana(String jenisBencana) {
        this.jenisBencana = jenisBencana;
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

    public String getTanggalKejadian() {
        return tanggalKejadian;
    }

    public void setTanggalKejadian(String tanggalKejadian) {
        this.tanggalKejadian = tanggalKejadian;
    }

    public String getAksesTransportasi() {
        return aksesTransportasi;
    }

    public void setAksesTransportasi(String aksesTransportasi) {
        this.aksesTransportasi = aksesTransportasi;
    }

    public String getAlatTransportasi() {
        return alatTransportasi;
    }

    public void setAlatTransportasi(String alatTransportasi) {
        this.alatTransportasi = alatTransportasi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
