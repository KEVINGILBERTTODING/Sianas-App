package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sianasapp.LoginActivity;
import com.example.sianasapp.Model.AdminModel;
import com.example.sianasapp.Model.AnggotaModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAdminProfilBinding;
import com.example.sianasapp.databinding.FragmentAnggotaProfilBinding;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProfilFragment extends Fragment {
    SharedPreferences sharedPreferences;
    AdminService adminService;
    private FragmentAdminProfilBinding binding;
    private String userId;
    private AlertDialog progressDialog;
    private SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProfilBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        adminService = DataApi.getClient().create(AdminService.class);
        editor = sharedPreferences.edit();




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMyProfile();
        listener();
    }

    private void listener() {
        binding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
            }
        });

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void getMyProfile() {
        showProgressBar("Loading", "Memuat data...", true);
        adminService.getMyProfile(userId).enqueue(new Callback<AdminModel>() {
            @Override
            public void onResponse(Call<AdminModel> call, Response<AdminModel> response) {
                if (response.isSuccessful() && response.body() !=null) {
                    binding.etnama.setText(response.body().getNama());

                    binding.etUsername.setText(response.body().getUsername());
                    binding.etPassword.setText(response.body().getPassword());
                    binding.etPassword.setText(response.body().getPassword());
                    showProgressBar("dsd", "Ssd", false);
                }else {
                    showProgressBar("Sd", "ds", false);
                    showToast("error", "Terjadi kesalahan");
                    binding.btnupdate.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<AdminModel> call, Throwable t) {
                showProgressBar("Sd", "ds", false);
                showToast("error", "Tidak ada koneksi internet");
                binding.btnupdate.setEnabled(false);

            }
        });
    }

    private void updateProfile() {
        showProgressBar("Loading", "Mengubah profil...", true);
        adminService.updateMyProfile(
                userId,
                binding.etnama.getText().toString(),
                binding.etUsername.getText().toString(),
                binding.etPassword.getText().toString()
        ).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showProgressBar("dsd", "ddss", false);
                    showToast("success", "Berhasil mengubah profil");
                    getMyProfile();
                }else {
                    showToast("error", "Gagal mengubah profil");
                    showProgressBar("dsd", "ddss", false);

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showToast("error", "Tidak ada koneksi internet");
                showProgressBar("dsd", "ddss", false);

            }
        });
    }

    private void logOut() {
        editor.clear().apply();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
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