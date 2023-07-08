package com.example.sianasapp.FragmentSopir;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.Util.SopirService;
import com.example.sianasapp.databinding.FragmentSopirHistoryDetailBinding;
import com.example.sianasapp.databinding.FragmentSopirJadwalDetailBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class SopirHistoryDetailFragment extends Fragment {
    private String noPemesanan;

    private FragmentSopirHistoryDetailBinding binding;
    private SopirService sopirService;
    private File file;
    private String lat, lng, lat1, lat2, lat3, lng1, lng2, lng3;
    private AlertDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSopirHistoryDetailBinding.inflate(inflater, container, false);
        noPemesanan = getArguments().getString("no_pengajuan");
        sopirService = DataApi.getClient().create(SopirService.class);

        binding.etAlamattujuan1.setText(getArguments().getString("alamat1"));
        binding.etAlamattujuan2.setText(getArguments().getString("alamat2"));
        binding.etAlamattujuan3.setText(getArguments().getString("alamat3"));
        binding.etKota1.setText(getArguments().getString("kota1"));
        binding.etKota2.setText(getArguments().getString("kota2"));
        binding.etKota3.setText(getArguments().getString("kota3"));
        binding.etTujuankunjungan1.setText(getArguments().getString("tujuan1"));
        binding.etTujuankunjungan2.setText(getArguments().getString("tujuan2"));
        binding.etTujuankunjungan3.setText(getArguments().getString("tujuan3"));
        binding.etJenismobil.setText(getArguments().getString("jenis_mobil"));
        binding.etNoPolisi.setText(getArguments().getString("nopol"));
        binding.etTglPeminjaman.setText(getArguments().getString("tgl_pinjam"));
        binding.etTglKembali.setText(getArguments().getString("tgl_kembali"));
        binding.etNamaSopir.setText(getArguments().getString("nama_sopir"));
        binding.etBanyakPenumpang.setText(getArguments().getString("penumpang"));
        binding.etKmAwal.setText(getArguments().getString("km_awal"));
        binding.etKmAkhir.setText(getArguments().getString("km_akhir"));
        binding.tvStatusSopir.setText(getArguments().getString("konfirmasi_sopir"));
        lat1 = getArguments().getString("lat1");
        lat2 = getArguments().getString("lat2");
        lat3 = getArguments().getString("lat3");
        lng1 = getArguments().getString("lng1");
        lng2 = getArguments().getString("lng2");
        lng3 = getArguments().getString("ln3");






        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLatLngNow();
        listener();
    }

    private void listener() {
        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = Constans.URL_DOWNLOAD_BUKTI + noPemesanan;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);



            }
        });

        binding.btnLocationNow.setOnClickListener(View -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Peringatan").setMessage("Apakah ada yakin telah sampai pada lokasi tujuan?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (lat1.isEmpty()) {
                                        saveLocation("location_1");

                                    }else if (lat2.isEmpty()) {
                                        saveLocation("location_2");

                                    }else if (lat3.isEmpty()) {
                                        saveLocation("location_3");
                                    }else{
                                        showToast("err", "Anda telah menyelesaikan perjalanan anda");
                                    }

                                }
                            }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alert.show();
        });
    }

   // get lat dan lng
    private void getLatLngNow() {
        if (getActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }else {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull android.location.Location location) {
                        lat = String.valueOf(location.getLatitude());
                        lng = String.valueOf(location.getLongitude());


                    }
                });
            } else {
                Toasty.warning(getActivity(), "GPS tidak aktif", Toasty.LENGTH_SHORT).show();
                binding.btnLocationNow.setEnabled(false);
            }
        }
    }

    private void saveLocation(String jenis) {
        showProgressBar("Loading", "Mengirim lokasi sekarang", true);
        sopirService.setLocation(
                getArguments().getString("no_pengajuan"),
                lat, lng, jenis
        ).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                showProgressBar("s", "S", false);
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showToast("success", "Berhasil menambahkan lokasi saat ini");
                    getActivity().onBackPressed();
                }else {
                    showToast("err", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showProgressBar("s", "S", false);
                showToast("err", "Tidak ada koneksi internet");


            }
        });
    }

    private void showProgressBar(String title, String message, boolean isLoading) {
        if (isLoading) {
            // Membuat progress dialog baru jika belum ada
            if (progressDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setCancelable(false);
                progressDialog = builder.create();
            }
            progressDialog.show(); // Menampilkan progress dialog
        } else {
            // Menyembunyikan progress dialog jika ada
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }




    private void showToast(String status, String message){
        if(status.equals("success")) {
            Toasty.success(getContext(), message, Toasty.LENGTH_SHORT).show();
        }else {
            Toasty.error(getContext(), message, Toasty.LENGTH_SHORT).show();

        }
    }



}