package achmadaffandi.mdisaster.Model;

public class DisasterData {
    private String title, description, imageId;
    private String jenisBencana, latLokasi, longLokasi, alamat, kabupaten, tanggalKejadian, aksesTransportasi, alatTransportasi;
    private String rumahHancur, lakiBalita, perempuanBalita, lakiAnak, perempuanAnak, lakiRemaja, perempuanRemaja, lakiDewasa, perempuanDewasa, lakiLansia, perempuanLansia;
    /*private String kondisiListrik, sumberListrik, kondisiAir, sumberAir, kondisiDrainase, jumlahJamban;
    public Map<String, Boolean> stars = new HashMap<>();*/

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

    public DisasterData() {

    }

    public String getRumahHancur() {
        return rumahHancur;
    }

    public void setRumahHancur(String rumahHancur) {
        this.rumahHancur = rumahHancur;
    }

    public String getLakiBalita() {
        return lakiBalita;
    }

    public void setLakiBalita(String lakiBalita) {
        this.lakiBalita = lakiBalita;
    }

    public String getPerempuanBalita() {
        return perempuanBalita;
    }

    public void setPerempuanBalita(String perempuanBalita) {
        this.perempuanBalita = perempuanBalita;
    }

    public String getLakiAnak() {
        return lakiAnak;
    }

    public void setLakiAnak(String lakiAnak) {
        this.lakiAnak = lakiAnak;
    }

    public String getPerempuanAnak() {
        return perempuanAnak;
    }

    public void setPerempuanAnak(String perempuanAnak) {
        this.perempuanAnak = perempuanAnak;
    }

    public String getLakiRemaja() {
        return lakiRemaja;
    }

    public void setLakiRemaja(String lakiRemaja) {
        this.lakiRemaja = lakiRemaja;
    }

    public String getPerempuanRemaja() {
        return perempuanRemaja;
    }

    public void setPerempuanRemaja(String perempuanRemaja) {
        this.perempuanRemaja = perempuanRemaja;
    }

    public String getLakiDewasa() {
        return lakiDewasa;
    }

    public void setLakiDewasa(String lakiDewasa) {
        this.lakiDewasa = lakiDewasa;
    }

    public String getPerempuanDewasa() {
        return perempuanDewasa;
    }

    public void setPerempuanDewasa(String perempuanDewasa) {
        this.perempuanDewasa = perempuanDewasa;
    }

    public String getLakiLansia() {
        return lakiLansia;
    }

    public void setLakiLansia(String lakiLansia) {
        this.lakiLansia = lakiLansia;
    }

    public String getPerempuanLansia() {
        return perempuanLansia;
    }

    public void setPerempuanLansia(String perempuanLansia) {
        this.perempuanLansia = perempuanLansia;
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
