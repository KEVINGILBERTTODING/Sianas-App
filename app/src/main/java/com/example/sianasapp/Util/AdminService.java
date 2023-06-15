package com.example.sianasapp.Util;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Model.RiwayatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AdminService {

    @GET("admin/getMobilById")
    Call<MobilModel> getMobilById(
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("admin/updatemobil")
    Call<ResponseModel> updateMobil(
            @Field("no_mobil") String no_mobile,
            @Field("jenis_mobil") String jenis_mobil,
            @Field("no_plat") String no_plat,
            @Field("tgl_pjk") String tgl_pjk,
            @Field("no_rangka") String no_rangka,
            @Field("no_mesin") String no_mesin,
            @Field("no_stnk") String no_stnk,
            @Field("warna") String warna,
            @Field("status") String status

    );

    @FormUrlEncoded
    @POST("admin/updatemotor")
    Call<ResponseModel> updateMotor(
            @Field("id") String id,
            @Field("jenis_motor") String jenis_motor,
            @Field("no_plat") String no_plat,
            @Field("tgl_pjk") String tgl_pjk
    );

    @FormUrlEncoded
    @POST("admin/deleteMotor")
    Call<ResponseModel> deleteMotor(
            @Field("id") String id
    );

    @GET("admin/getPengajuanByStatus")
    Call<List<RiwayatModel>> getAllPengajuanByStatus(
            @Query("status") String status
     );
}
