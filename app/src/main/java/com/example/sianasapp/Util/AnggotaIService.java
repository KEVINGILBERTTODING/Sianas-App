package com.example.sianasapp.Util;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.MotorModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnggotaIService {

    @GET("anggotaapi/getMobilByStatus")
    Call<List<MobilModel>> getMobilByStatus(
            @Query("status") String status
    );

    @GET("anggotaapi/getMotor")
    Call<List<MotorModel>> getMotor(
    );


}