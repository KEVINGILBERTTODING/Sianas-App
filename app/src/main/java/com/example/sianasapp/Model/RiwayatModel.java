package com.example.sianasapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RiwayatModel implements Serializable {
    @SerializedName("no_pengajuan")
    private String noPengajuan;

    @SerializedName("no_anggota")
    private String noAnggota;

    @SerializedName("subbag")
    String subbag;

    @SerializedName("no_mobil")
    private String noMobil;

    @SerializedName("tujuan_1")
    private String tujuan1;

    @SerializedName("tujuan_2")
    private String tujuan2;

    @SerializedName("tujuan_3")
    private String tujuan3;

    @SerializedName("alamat_1")
    private String alamat1;

    @SerializedName("alamat_2")
    private String alamat2;

    @SerializedName("alamat_3")
    private String alamat3;

    @SerializedName("kota_1")
    private String kota1;

    @SerializedName("kota_2")
    private String kota2;

    @SerializedName("kota_3")
    private String kota3;

    @SerializedName("muatan")
    private String muatan;

    @SerializedName("tgl_digunakan")
    private String tglDigunakan;

    @SerializedName("tgl_kembali")
    private String tglKembali;

    @SerializedName("tgl_upload")
    private String tglUpload;

    @SerializedName("km_awal")
    private String kmAwal;

    @SerializedName("km_akhir")
    private String kmAkhir;

    @SerializedName("surat")
    private String surat;

    @SerializedName("bukti")
    private String bukti;

    @SerializedName("konfirmasi")
    private String konfirmasi;

    @SerializedName("konfirmasi_sopir")
    private String konfirmasiSopir;

    @SerializedName("nama")
    private String nama;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("jenis_mobil")
    private String jenisMobil;

    @SerializedName("no_plat")
    private String noPlat;

    @SerializedName("tgl_pjk")
    private String tglPajak;

    @SerializedName("no_rangka")
    private String noRangka;

    @SerializedName("no_mesin")
    private String noMesin;

    @SerializedName("no_stnk")
    private String noStnk;

    @SerializedName("warna")
    private String warna;

    @SerializedName("status")
    private String status;

    @SerializedName("foto")
    private String foto;
    @SerializedName("lat_1")
    private String lat1;
    @SerializedName("lat_2")
    private String lat2;
    @SerializedName("lat_3")
    private String lat3;
    @SerializedName("lng_1")
    private String lng1;
    @SerializedName("lng_2")
    private String lng2;
    @SerializedName("lng_3")
    private String lng3;

    public RiwayatModel(String noPengajuan, String noAnggota, String subbag, String noMobil, String tujuan1, String tujuan2, String tujuan3, String alamat1, String alamat2, String alamat3, String kota1, String kota2, String kota3, String muatan, String tglDigunakan, String tglKembali, String tglUpload, String kmAwal, String kmAkhir, String surat, String bukti, String konfirmasi, String konfirmasiSopir, String nama, String noHp, String alamat, String username, String password, String role, String jenisMobil, String noPlat, String tglPajak, String noRangka, String noMesin, String noStnk, String warna, String status, String foto, String lat1, String lat2, String lat3, String lng1, String lng2, String lng3) {
        this.noPengajuan = noPengajuan;
        this.noAnggota = noAnggota;
        this.subbag = subbag;
        this.noMobil = noMobil;
        this.tujuan1 = tujuan1;
        this.tujuan2 = tujuan2;
        this.tujuan3 = tujuan3;
        this.alamat1 = alamat1;
        this.alamat2 = alamat2;
        this.alamat3 = alamat3;
        this.kota1 = kota1;
        this.kota2 = kota2;
        this.kota3 = kota3;
        this.muatan = muatan;
        this.tglDigunakan = tglDigunakan;
        this.tglKembali = tglKembali;
        this.tglUpload = tglUpload;
        this.kmAwal = kmAwal;
        this.kmAkhir = kmAkhir;
        this.surat = surat;
        this.bukti = bukti;
        this.konfirmasi = konfirmasi;
        this.konfirmasiSopir = konfirmasiSopir;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
        this.username = username;
        this.password = password;
        this.role = role;
        this.jenisMobil = jenisMobil;
        this.noPlat = noPlat;
        this.tglPajak = tglPajak;
        this.noRangka = noRangka;
        this.noMesin = noMesin;
        this.noStnk = noStnk;
        this.warna = warna;
        this.status = status;
        this.foto = foto;
        this.lat1 = lat1;
        this.lat2 = lat2;
        this.lat3 = lat3;
        this.lng1 = lng1;
        this.lng2 = lng2;
        this.lng3 = lng3;
    }

    public String getNoPengajuan() {
        return noPengajuan;
    }

    public void setNoPengajuan(String noPengajuan) {
        this.noPengajuan = noPengajuan;
    }

    public String getNoAnggota() {
        return noAnggota;
    }

    public void setNoAnggota(String noAnggota) {
        this.noAnggota = noAnggota;
    }

    public String getNoMobil() {
        return noMobil;
    }

    public void setNoMobil(String noMobil) {
        this.noMobil = noMobil;
    }

    public String getTujuan1() {
        return tujuan1;
    }

    public void setTujuan1(String tujuan1) {
        this.tujuan1 = tujuan1;
    }

    public String getTujuan2() {
        return tujuan2;
    }

    public void setTujuan2(String tujuan2) {
        this.tujuan2 = tujuan2;
    }

    public String getTujuan3() {
        return tujuan3;
    }

    public void setTujuan3(String tujuan3) {
        this.tujuan3 = tujuan3;
    }

    public String getAlamat1() {
        return alamat1;
    }

    public void setAlamat1(String alamat1) {
        this.alamat1 = alamat1;
    }

    public String getAlamat2() {
        return alamat2;
    }

    public void setAlamat2(String alamat2) {
        this.alamat2 = alamat2;
    }

    public String getAlamat3() {
        return alamat3;
    }

    public void setAlamat3(String alamat3) {
        this.alamat3 = alamat3;
    }

    public String getKota1() {
        return kota1;
    }

    public void setKota1(String kota1) {
        this.kota1 = kota1;
    }

    public String getKota2() {
        return kota2;
    }

    public void setKota2(String kota2) {
        this.kota2 = kota2;
    }

    public String getKota3() {
        return kota3;
    }

    public void setKota3(String kota3) {
        this.kota3 = kota3;
    }

    public String getMuatan() {
        return muatan;
    }

    public void setMuatan(String muatan) {
        this.muatan = muatan;
    }

    public String getTglDigunakan() {
        return tglDigunakan;
    }

    public void setTglDigunakan(String tglDigunakan) {
        this.tglDigunakan = tglDigunakan;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    public String getTglUpload() {
        return tglUpload;
    }

    public void setTglUpload(String tglUpload) {
        this.tglUpload = tglUpload;
    }

    public String getKmAwal() {
        return kmAwal;
    }

    public void setKmAwal(String kmAwal) {
        this.kmAwal = kmAwal;
    }

    public String getKmAkhir() {
        return kmAkhir;
    }

    public void setKmAkhir(String kmAkhir) {
        this.kmAkhir = kmAkhir;
    }

    public String getSurat() {
        return surat;
    }

    public void setSurat(String surat) {
        this.surat = surat;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getKonfirmasiSopir() {
        return konfirmasiSopir;
    }

    public void setKonfirmasiSopir(String konfirmasiSopir) {
        this.konfirmasiSopir = konfirmasiSopir;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJenisMobil() {
        return jenisMobil;
    }

    public void setJenisMobil(String jenisMobil) {
        this.jenisMobil = jenisMobil;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getTglPajak() {
        return tglPajak;
    }

    public void setTglPajak(String tglPajak) {
        this.tglPajak = tglPajak;
    }

    public String getNoRangka() {
        return noRangka;
    }

    public void setNoRangka(String noRangka) {
        this.noRangka = noRangka;
    }

    public String getNoMesin() {
        return noMesin;
    }

    public void setNoMesin(String noMesin) {
        this.noMesin = noMesin;
    }

    public String getNoStnk() {
        return noStnk;
    }

    public void setNoStnk(String noStnk) {
        this.noStnk = noStnk;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSubbag() {
        return subbag;
    }

    public void setSubbag(String subbag) {
        this.subbag = subbag;
    }

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getLat2() {
        return lat2;
    }

    public void setLat2(String lat2) {
        this.lat2 = lat2;
    }

    public String getLat3() {
        return lat3;
    }

    public void setLat3(String lat3) {
        this.lat3 = lat3;
    }

    public String getLng1() {
        return lng1;
    }

    public void setLng1(String lng1) {
        this.lng1 = lng1;
    }

    public String getLng2() {
        return lng2;
    }

    public void setLng2(String lng2) {
        this.lng2 = lng2;
    }

    public String getLng3() {
        return lng3;
    }

    public void setLng3(String lng3) {
        this.lng3 = lng3;
    }
}
