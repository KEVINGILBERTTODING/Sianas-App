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

import com.example.sianasapp.Model.AnggotaModel;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.adapter.admin.AdminMobilAdapter;
import com.example.sianasapp.adapter.admin.AnggotaAdapter;
import com.example.sianasapp.databinding.FragmentAdminAnggotaBinding;
import com.example.sianasapp.databinding.FragmentAdminMobilBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminAnggotaFragment extends Fragment {
    private FragmentAdminAnggotaBinding binding;

    AdminService adminService;
    LinearLayoutManager linearLayoutManager;
    List<AnggotaModel> anggotaModelList;
    AnggotaAdapter anggotaAdapter;

    AlertDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAnggotaBinding.inflate(inflater, container, false);
        adminService = DataApi.getClient().create(AdminService.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();

    }

    private void listener() {
        getAllAnggota();
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameAdmin, new AdminInsertAnggotaFragment())
                        .addToBackStack(null).commit();

            }
        });

    }



    private void getAllAnggota() {
        showProgressBar("Loading", "Memuat data...", true);
        adminService.getAllAnggota().enqueue(new Callback<List<AnggotaModel>>() {
            @Override
            public void onResponse(Call<List<AnggotaModel>> call, Response<List<AnggotaModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    anggotaModelList = response.body();
                    anggotaAdapter = new AnggotaAdapter(getContext(), anggotaModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvMobil.setLayoutManager(linearLayoutManager);
                    binding.rvMobil.setAdapter(anggotaAdapter);
                    binding.rvMobil.setHasFixedSize(true);
                    showProgressBar("dsd", "sd",false);
                }else {
                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<AnggotaModel>> call, Throwable t) {
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