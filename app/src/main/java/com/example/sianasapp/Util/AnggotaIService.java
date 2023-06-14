package com.example.sianasapp.Util;

import com.example.sianasapp.Model.AnggotaModel;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.MotorModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Model.RiwayatModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @Multipart
    @POST("anggotaapi/addPengajuan")
    Call<ResponseModel> addPengajuan(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part file
            );

    @GET("anggotaapi/getMyProfile")
    Call<AnggotaModel> getMyProfile(
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("anggotaapi/updateProfile")
    Call<ResponseModel> updateProfile(
            @Field("subbag") String subbag,
            @Field("nama") String nama,
            @Field("nip") String nip,
            @Field("jabatan") String jabatan,
            @Field("no_hp") String no_hp,
            @Field("username") String username,
            @Field("password") String password,
            @Field("id") String id
    );


}