package com.example.sianasapp.Util;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.MotorModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Model.RiwayatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AnggotaIService {

    @GET("anggotaapi/getMobilByStatus")
    Call<List<MobilModel>> getMobilByStatus(
            @Query("status") String status
    );

    @GET("anggotaapi/getMotor")
    Call<List<MotorModel>> getMotor(
    );

    @GET("anggotaapi/getMyHistory")
    Call<List<RiwayatModel>> getMyHistory(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("anggotaapi/cancelPengajuan")
    Call<ResponseModel> cancelPengajuan(
            @Field("id") String id
    );


}