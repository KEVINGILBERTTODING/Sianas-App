package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAdminMobilDetailBinding;
import com.example.sianasapp.databinding.FragmentAnggotaMobilDetailBinding;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.OPTIONS;

public class AdminMobilDetailFragment extends Fragment {
    private FragmentAdminMobilDetailBinding binding;
    private AlertDialog progressDialog;
    private AdminService adminService;
    private String status;
    private String [] opsiStatus = {"Digunakan", "Tidak Digunakan"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminMobilDetailBinding.inflate(inflater, container, false);
        adminService = DataApi.getClient().create(AdminService.class);

        ArrayAdapter adapterStatus = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiStatus);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spStatus.setAdapter(adapterStatus);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
        getDetailMobil();
    }

    private void listener() {
        binding.spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = opsiStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMobil();
            }
        });
    }

    private void updateMobil() {
        showProgressBar("Loading", "Menyimpan perubahan...", true);
        adminService.updateMobil(
                getArguments().getString("no_mobil"),
                binding.etJenismobil.getText().toString(),
                binding.etNoPolisi.getText().toString(),
                binding.etTglPajak.getText().toString(),
                binding.etNoRangka.getText().toString(),
                binding.etNoMesin.getText().toString(),
                binding.etNoStnk.getText().toString(),
                binding.etWarna.getText().toString(),
                status
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

    private void getDetailMobil() {
        showProgressBar("Loading", "Memuat data...", true);
        adminService.getMobilById(getArguments().getString("no_mobil")).enqueue(new Callback<MobilModel>() {
            @Override
            public void onResponse(Call<MobilModel> call, Response<MobilModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.etJenismobil.setText(response.body().getJenisMobil());
                    binding.etNoPolisi.setText(response.body().getNoPlat());
                    binding.etNamaSopir.setText(response.body().getNama());
                    binding.etNoTelp.setText(response.body().getNoHp());
                    binding.etAlamat.setText(response.body().getAlamat());
                    binding.etNoRangka.setText(response.body().getNoRangka());
                    binding.etNoMesin.setText(response.body().getNoMesin());
                    binding.etNoStnk.setText(response.body().getNoStnk());
                    binding.etWarna.setText(response.body().getWarna());
                    binding.etTglPajak.setText(response.body().getTglPjk());
                    showProgressBar("sd", "sdsd", false);

                }else {
                    showProgressBar("Sd", "sds", false);
                    binding.btnUpdate.setEnabled(false);
                    showToast("err", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<MobilModel> call, Throwable t) {
                showProgressBar("Sd", "sds", false);
                binding.btnUpdate.setEnabled(false);
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