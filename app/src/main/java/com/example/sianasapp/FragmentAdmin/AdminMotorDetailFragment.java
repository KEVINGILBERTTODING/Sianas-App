package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAdminMotorDetailBinding;
import com.example.sianasapp.databinding.FragmentAnggotaMotorDetailBinding;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMotorDetailFragment extends Fragment {

    private FragmentAdminMotorDetailBinding binding;
    private AlertDialog progressDialog;
    AdminService adminService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminMotorDetailBinding.inflate(inflater, container, false);
        binding.etJenismotor.setText(getArguments().getString("nama_motor"));
        binding.etNoPolisi.setText(getArguments().getString("no_pol"));
        binding.etTglPajak.setText(getArguments().getString("tgl_pajak"));
        adminService = DataApi.getClient().create(AdminService.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMotor();
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMotor();
            }
        });
    }

    private void updateMotor() {
        showProgressBar("Loading", "Menyimpan perubahan...", true);
        adminService.updateMotor(
                getArguments().getString("no_motor"),
                binding.etJenismotor.getText().toString(),
                binding.etNoPolisi.getText().toString(),
                binding.etTglPajak.getText().toString()
        ).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                showProgressBar("sd", "sds", false);
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showToast("success", "Berhasil menyimpan perubahan");
                    getActivity().onBackPressed();

                }else {
                    showToast("err", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showToast("err", "Tidak ada koneksi internet");


            }
        });
    }

    private void deleteMotor() {
        showProgressBar("Loading", "Menyimpan perubahan...", true);
        adminService.deleteMotor(
                getArguments().getString("no_motor")

        ).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                showProgressBar("sd", "sds", false);
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showToast("success", "Berhasil menghapus data");
                    getActivity().onBackPressed();

                }else {
                    showToast("err", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
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