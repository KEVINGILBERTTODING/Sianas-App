package com.example.sianasapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnggotaModel implements Serializable {
    @SerializedName("no_anggota")
    private String noAnggota;

    @SerializedName("subbag")
    private String subbag;

    @SerializedName("nama")
    private String nama;

    @SerializedName("nip")
    private String nip;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("foto")
    private String foto;

    public AnggotaModel(String noAnggota, String subbag, String nama, String nip, String jabatan, String noHp, String username, String password, String role, String foto) {
        this.noAnggota = noAnggota;
        this.subbag = subbag;
        this.nama = nama;
        this.nip = nip;
        this.jabatan = jabatan;
        this.noHp = noHp;
        this.username = username;
        this.password = password;
        this.role = role;
        this.foto = foto;
    }

    public String getNoAnggota() {
        return noAnggota;
    }

    public void setNoAnggota(String noAnggota) {
        this.noAnggota = noAnggota;
    }

    public String getSubbag() {
        return subbag;
    }

    public void setSubbag(String subbag) {
        this.subbag = subbag;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
