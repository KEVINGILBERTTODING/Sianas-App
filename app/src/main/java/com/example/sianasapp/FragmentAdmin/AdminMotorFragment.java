package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sianasapp.Model.MotorModel;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.adapter.admin.AdminMotorAdapter;
import com.example.sianasapp.adapter.anggota.AnggotaMotorAdapter;
import com.example.sianasapp.databinding.FragmentAnggotaMotorBinding;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMotorFragment extends Fragment {

    private FragmentAnggotaMotorBinding binding;

    LinearLayoutManager linearLayoutManager;
    List<MotorModel> motorModelList;
    AlertDialog progressDialog;
    private AdminMotorAdapter adminMotorAdapter;
    AnggotaIService anggotaIService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaMotorBinding.inflate(inflater, container, false);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMotor();

    }

    private void getMotor() {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMotor().enqueue(new Callback<List<MotorModel>>() {
            @Override
            public void onResponse(Call<List<MotorModel>> call, Response<List<MotorModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    motorModelList = response.body();
                    adminMotorAdapter = new AdminMotorAdapter(getContext(), motorModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvMotor.setLayoutManager(linearLayoutManager);
                    binding.rvMotor.setAdapter(adminMotorAdapter);
                    binding.rvMotor.setHasFixedSize(true);
                    showProgressBar("dsd", "sd",false);
                }else {
                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<MotorModel>> call, Throwable t) {
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
}