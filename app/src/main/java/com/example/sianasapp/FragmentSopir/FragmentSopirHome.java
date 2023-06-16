package com.example.sianasapp.FragmentSopir;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.Util.SopirService;
import com.example.sianasapp.adapter.sopir.SopirHistoryAdapter;
import com.example.sianasapp.databinding.FragmentSopirHomeBinding;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSopirHome extends Fragment {

    private AlertDialog progressDialog;
    private FragmentSopirHomeBinding binding;
    private SharedPreferences sharedPreferences;
    private String userId;
    private SopirService sopirService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSopirHomeBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        sopirService = DataApi.getClient().create(SopirService.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getJadwal();
        getRiwayat();

        listener();
    }

    private void listener() {
        binding.cvMenuJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new SopirJadwalFragment());
            }
        });

        binding.cvMenuRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new SopirRiwayatFragment());

            }
        });
    }

    private void getJadwal() {
        showProgressBar("Loading", "Memuat data...", true);
        sopirService.getJadwalSaya(userId).enqueue(new Callback<List<RiwayatModel>>() {
            @Override
            public void onResponse(Call<List<RiwayatModel>> call, Response<List<RiwayatModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvTotalJadwal.setText(String.valueOf(response.body().size()));
                    showProgressBar("Sd", "Dss", false);
                }else {
                    showProgressBar("sds", "sd", false);
                    binding.tvTotalJadwal.setText("0");
                }
            }

            @Override
            public void onFailure(Call<List<RiwayatModel>> call, Throwable t) {
                showProgressBar("sds", "sd", false);
                binding.tvTotalJadwal.setText("0");
                showToast("error", "Tidak ada koneksi internet");

            }
        });
    }

    private void getRiwayat() {
        showProgressBar("Loading", "Memuat data...", true);
        sopirService.getHistory(userId).enqueue(new Callback<List<RiwayatModel>>() {
            @Override
            public void onResponse(Call<List<RiwayatModel>> call, Response<List<RiwayatModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvTotalRiwayat.setText(String.valueOf(response.body().size()));

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

    private void replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameSopir, fragment)
                .addToBackStack(null).commit();
    }
}