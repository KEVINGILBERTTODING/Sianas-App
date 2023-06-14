package com.example.sianasapp.FragmentAnggota;

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
import android.widget.TextView;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.MotorModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.adapter.anggota.AnggotaMotorAdapter;
import com.example.sianasapp.databinding.FragmentAnggotaHomeBinding;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnggotaHomeFragment extends Fragment {

    SharedPreferences sharedPreferences;
    AlertDialog progressDialog;
    private AnggotaIService anggotaIService;

    private FragmentAnggotaHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaHomeBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        binding.tvUsername.setText(sharedPreferences.getString(Constans.SHARED_PREF_NAMA_LENGKAP, null));
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllMobil();
        getMotor();
        listener();
    }

    private void listener() {
        binding.cvMenuMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AnggotaMobilFragment());
            }
        });

        binding.cvMenuMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AnggotaMotorFragment());
            }
        });
    }


    private void getAllMobil() {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMobilByStatus("all").enqueue(new Callback<List<MobilModel>>() {
            @Override
            public void onResponse(Call<List<MobilModel>> call, Response<List<MobilModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvTotalMobil.setText(String.valueOf(response.body().size()));
                    showProgressBar("Sd", "Dss", false);
                }else {
                    showProgressBar("sds", "sd", false);
                    binding.tvTotalMobil.setText("0");
                }
            }

            @Override
            public void onFailure(Call<List<MobilModel>> call, Throwable t) {
                showProgressBar("sds", "sd", false);
                binding.tvTotalMobil.setText("0");
                showToast("error", "Tidak ada koneksi internet");

            }
        });
    }

    private void getMotor() {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMotor().enqueue(new Callback<List<MotorModel>>() {
            @Override
            public void onResponse(Call<List<MotorModel>> call, Response<List<MotorModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                   binding.tvTotalMotor.setText(String.valueOf(response.body().size()));
                    showProgressBar("dsd", "sd",false);
                }else {
                    binding.tvTotalMotor.setText("0");

                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<MotorModel>> call, Throwable t) {
                showProgressBar("Sds", "dss", false);
                binding.tvTotalMotor.setText("0");

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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAnggota, fragment)
                .addToBackStack(null).commit();
    }


}