package com.example.sianasapp.FragmentSopir;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.Util.SopirService;
import com.example.sianasapp.adapter.admin.AdminRiwayatAdapter;
import com.example.sianasapp.adapter.sopir.SopirJadwalAdapter;
import com.example.sianasapp.databinding.FragmentAdminRiwayatBinding;
import com.example.sianasapp.databinding.FragmentJadwalSopirBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SopirJadwalFragment extends Fragment {
    private FragmentJadwalSopirBinding binding;
    LinearLayoutManager linearLayoutManager;
    List<RiwayatModel> riwayatModelList;
    SopirJadwalAdapter sopirJadwalAdapter;
    private SopirService sopirService;
    AnggotaIService anggotaIService;
    SharedPreferences sharedPreferences;
    private AlertDialog progressDialog;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJadwalSopirBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);
        sopirService = DataApi.getClient().create(SopirService.class);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getJadwal();
        listener();

    }

    private void listener() {

    }

    private void getJadwal() {
        showProgressBar("Loading", "Memuat data...", true);
        sopirService.getJadwalSaya(userId).enqueue(new Callback<List<RiwayatModel>>() {
            @Override
            public void onResponse(Call<List<RiwayatModel>> call, Response<List<RiwayatModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    riwayatModelList = response.body();
                    sopirJadwalAdapter = new SopirJadwalAdapter(getContext(), riwayatModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvRiwayat.setLayoutManager(linearLayoutManager);
                    binding.rvRiwayat.setAdapter(sopirJadwalAdapter);
                    binding.rvRiwayat.setHasFixedSize(true);
                    showProgressBar("dsd", "sd",false);
                }else {
                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<RiwayatModel>> call, Throwable t) {
                showProgressBar("Sds", "dss", false);
                showToast("error", "Tidak ada koneksi internet");


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
    private void showToast(String jenis, String text) {
        if (jenis.equals("success")) {
            Toasty.success(getContext(), text, Toasty.LENGTH_SHORT).show();
        }else {
            Toasty.error(getContext(), text, Toasty.LENGTH_SHORT).show();
        }
    }

    private void getDateTimePicker(TextView tvDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateFormatted, monthFormatted;
                if (month < 10) {
                    monthFormatted = String.format("%02d", month + 1);
                }else {
                    monthFormatted = String.valueOf(month + 1);
                }

                if (dayOfMonth < 10) {
                    dateFormatted = String.format("%02d", dayOfMonth);
                }else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }

                tvDate.setText(year + "-" + monthFormatted + "-" + dateFormatted);
            }
        });

        datePickerDialog.show();
    }
}