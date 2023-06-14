package com.example.sianasapp.FragmentAnggota;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.adapter.anggota.AnggotaMobilAdapter;
import com.example.sianasapp.databinding.FragmentAnggotaMobilBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnggotaMobilFragment extends Fragment {
    private FragmentAnggotaMobilBinding binding;

    AnggotaIService anggotaIService;
    LinearLayoutManager linearLayoutManager;
    List<MobilModel> mobilModelList;
    AnggotaMobilAdapter anggotaMobilAdapter;

    AlertDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaMobilBinding.inflate(inflater, container, false);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Semua"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Digunakan"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Tidak digunakan"));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
        getMobil("all");

    }

    private void listener() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getMobil("all");
                }else if (tab.getPosition() == 1) {
                    getMobil("Digunakan");
                }else if (tab.getPosition() == 2) {
                    getMobil("Tidak_digunakan");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getMobil(String status) {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMobilByStatus(status).enqueue(new Callback<List<MobilModel>>() {
            @Override
            public void onResponse(Call<List<MobilModel>> call, Response<List<MobilModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    mobilModelList = response.body();
                    anggotaMobilAdapter = new AnggotaMobilAdapter(getContext(), mobilModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvMobil.setLayoutManager(linearLayoutManager);
                    binding.rvMobil.setAdapter(anggotaMobilAdapter);
                    binding.rvMobil.setHasFixedSize(true);
                    showProgressBar("dsd", "sd",false);
                }else {
                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<MobilModel>> call, Throwable t) {
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