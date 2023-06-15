package com.example.sianasapp.Util;

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

public interface SopirService {
    @GET("sopir/getJadwalSaya")
    Call<List<RiwayatModel>> getJadwalSaya(
            @Query("id") String id
    );

    @Multipart
    @POST("sopir/konfirmasi")
    Call<ResponseModel> konfirmasiJadwal(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @POST("sopir/tolakJadwal")
    Call<ResponseModel> tolakJadwal(
            @Field("id") String id

    );
}
