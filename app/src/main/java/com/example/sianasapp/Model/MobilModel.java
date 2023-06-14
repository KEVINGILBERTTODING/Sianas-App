package com.example.sianasapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MobilModel implements Serializable {
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
    private String tglPjk;

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


    public MobilModel(String nama, String noHp, String alamat, String username, String password, String role, String jenisMobil, String noPlat, String tglPjk, String noRangka, String noMesin, String noStnk, String warna, String status, String foto) {
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
        this.username = username;
        this.password = password;
        this.role = role;
        this.jenisMobil = jenisMobil;
        this.noPlat = noPlat;
        this.tglPjk = tglPjk;
        this.noRangka = noRangka;
        this.noMesin = noMesin;
        this.noStnk = noStnk;
        this.warna = warna;
        this.status = status;
        this.foto = foto;
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

    public String getTglPjk() {
        return tglPjk;
    }

    public void setTglPjk(String tglPjk) {
        this.tglPjk = tglPjk;
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
}
