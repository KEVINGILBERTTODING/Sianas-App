package com.example.sianasapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdminModel implements Serializable {
    @SerializedName("no_admin")
    private String no_admin;
    @SerializedName("nama")
    private String nama;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;

    public AdminModel(String no_admin, String nama, String username, String password, String role) {
        this.no_admin = no_admin;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getNo_admin() {
        return no_admin;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
