package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAdminInsertAnggotaProfilBinding;
import com.example.sianasapp.databinding.FragmentAdminInsertSopirBinding;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AdminInsertSopirFragment extends Fragment {
    SharedPreferences sharedPreferences;
    AnggotaIService anggotaIService;
    AdminService adminService;
    private FragmentAdminInsertSopirBinding binding;
    private String userId;
    private AlertDialog progressDialog;
    private SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminInsertSopirBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);
        adminService = DataApi.getClient().create(AdminService.class);
        editor = sharedPreferences.edit();




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertSopir();
            }
        });
        binding.btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


    }



    private void insertSopir() {
        showProgressBar("Loading", "Menyimpan data....", true);
        adminService.insertSopir(
                binding.etNama.getText().toString(),
                binding.etJenisMobil.getText().toString(),
                binding.etnopolisi.getText().toString(),
                binding.etNoTelepon.getText().toString(),
                binding.etUsername.getText().toString(),
                binding.etPassword.getText().toString()

        ).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showProgressBar("dsd", "ddss", false);
                    showToast("success", "Berhasil menambahkan sopir");
                    getActivity().onBackPressed();
                }else {
                    showToast("error", "Gagal menambahkan sopir");
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