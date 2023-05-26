package com.example.sianasapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("code")
    Integer code;
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;

    @SerializedName("nama")
    String nama;
    @SerializedName("role")
    Integer role;
    @SerializedName("user_id")
    String userId;

    public UserModel(Integer code, Boolean status, String message, String nama, Integer role, String userId) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.nama = nama;
        this.role = role;
        this.userId = userId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
