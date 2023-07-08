package com.example.sianasapp.FragmentSopir;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sianasapp.LoginActivity;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.Util.SopirService;
import com.example.sianasapp.databinding.FragmentSopirProfilBinding;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class FragmentSopirProfil extends Fragment {
    private FragmentSopirProfilBinding binding;
    private SopirService sopirService;
    private AlertDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSopirProfilBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        sopirService = DataApi.getClient().create(SopirService.class);
        Log.d("s", "onCreateView: " + userId);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
        getProfile();
    }

    private void listener() {
        binding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();

            }
        });
        binding.btnLogOut.setOnClickListener(View -> {
            editor.clear();
            editor.apply();
            showToast("Berhasil", "Berhasil Logout");
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();

        });


    }

    private void getProfile() {
        showProgressBar("Loading", "Memuat data...", true);
        sopirService.getProfile(userId).enqueue(new Callback<MobilModel>() {
            @Override
            public void onResponse(Call<MobilModel> call, Response<MobilModel> response) {
                showProgressBar("Sd", "Sd", false);
                if (response.isSuccessful() && response.body() != null) {
                    binding.etNamaLengkap.setText(response.body().getNama());
                    binding.tvJenisMobil.setText(response.body().getJenisMobil());
                    binding.tvNoPol.setText(response.body().getNoPlat());
                    binding.etNoTelp.setText(response.body().getNoHp());
                    binding.etUsername.setText(response.body().getUsername());
                    binding.etPassword.setText(response.body().getPassword());


                }else {
                    showToast("err", "Terjadi kesalahan");



                }


            }

            @Override
            public void onFailure(Call<MobilModel> call, Throwable t) {
                showProgressBar("s", "S", false);
                showToast("err", "Tidak ada koneksi internet");


            }
        });





    }

    private void updateProfile() {
        showProgressBar("Loading", "Update profile...", false);
        sopirService.updateProfile(
                userId,
                binding.etNamaLengkap.getText().toString(),
                binding.etNoTelp.getText().toString(),
                binding.etUsername.getText().toString(),
                binding.etPassword.getText().toString()
        ).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                showProgressBar("s", "s", false);
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showToast("success", "Berhasil mengubah profil");
                    getProfile();
                }else {
                    showToast("err", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showProgressBar("s", "s", false);
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
    private void showToast(String jenis, String text) {
        if (jenis.equals("success")) {
            Toasty.success(getContext(), text, Toasty.LENGTH_SHORT).show();
        }else {
            Toasty.error(getContext(), text, Toasty.LENGTH_SHORT).show();
        }
    }
}