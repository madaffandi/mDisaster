package achmadaffandi.mdisaster.Model;

public class DisasterData {
    private String title, description, imageId;
    private String jenisBencana, latLokasi, longLokasi, alamat, kabupaten, tanggalKejadian, aksesTransportasi, alatTransportasi, keteranganLain;

    public DisasterData(String title, String description, String imageId) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public DisasterData(String jenisBencana, String tanggalKejadian, String latLokasi,
                        String longLokasi, String alamat, String kabupaten,
                        String aksesTransportasi, String alatTransportasi, String keteranganLain) {
        this.jenisBencana = jenisBencana;
        this.latLokasi = latLokasi;
        this.longLokasi = longLokasi;
        this.alamat = alamat;
        this.kabupaten = kabupaten;
        this.tanggalKejadian = tanggalKejadian;
        this.aksesTransportasi = aksesTransportasi;
        this.alatTransportasi = alatTransportasi;
        this.keteranganLain = keteranganLain;
    }

    public DisasterData() {

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

    public String getKeteranganLain() {
        return keteranganLain;
    }

    public void setKeteranganLain(String keteranganLain) {
        this.keteranganLain = keteranganLain;
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
