package com.example.sianasapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MotorModel implements Serializable {

    @SerializedName("no_motor")
    Integer noMotor;
    @SerializedName("jenis_motor")
    String jenisMotor;
    @SerializedName("no_plat")
    String noPlat;
    @SerializedName("tgl_pjk")
    String tglPajak;

    public MotorModel(Integer noMotor, String jenisMotor, String noPlat, String tglPajak) {
        this.noMotor = noMotor;
        this.jenisMotor = jenisMotor;
        this.noPlat = noPlat;
        this.tglPajak = tglPajak;
    }

    public Integer getNoMotor() {
        return noMotor;
    }

    public void setNoMotor(Integer noMotor) {
        this.noMotor = noMotor;
    }

    public String getJenisMotor() {
        return jenisMotor;
    }

    public void setJenisMotor(String jenisMotor) {
        this.jenisMotor = jenisMotor;
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
}
