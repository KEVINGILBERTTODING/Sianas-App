package com.example.sianasapp.Util;

import com.example.sianasapp.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthInterface {

    @FormUrlEncoded
    @POST("auth/login")
    Call<UserModel>login(
            @Field("username") String username,
            @Field("password") String password
    );
}
