package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sianasapp.Model.AnggotaModel;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.MotorModel;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAdminHomeBinding;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAdminHome extends Fragment {
    private FragmentAdminHomeBinding binding;
    AnggotaIService anggotaIService;
    private AlertDialog progressDialog;
    private AdminService adminService;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);
        adminService = DataApi.getClient().create(AdminService.class);





        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllMobil();
        getMotor();
        getKonfirmasi();
        getAnggota();
        getSopir();
        listener();
    }

    private void listener() {
        binding.cvMenuMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminMobilFragment());
            }
        });

        binding.cvMenuMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminMotorFragment());
            }
        });

        binding.cvMenuKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminRiwayatFragment());
            }
        });
        binding.cvAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminAnggotaFragment());
            }
        });
        binding.cvMenuSopir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminSopirFragment());

            }
        });


    }

    private void getMotor() {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMotor().enqueue(new Callback<List<MotorModel>>() {
            @Override
            public void onResponse(Call<List<MotorModel>> call, Response<List<MotorModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvtTotalMotor.setText(String.valueOf(response.body().size()));
                    showProgressBar("dsd", "sd",false);
                }else {
                    binding.tvtTotalMotor.setText("0");

                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<MotorModel>> call, Throwable t) {
                showProgressBar("Sds", "dss", false);
                binding.tvtTotalMotor.setText("0");

                showToast("error", "Tidak ada koneksi internet");


            }
        });

    }
    private void getAnggota() {
        showProgressBar("Loading", "Memuat data...", true);
        adminService.getAllAnggota().enqueue(new Callback<List<AnggotaModel>>() {
            @Override
            public void onResponse(Call<List<AnggotaModel>> call, Response<List<AnggotaModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvTotalAnggota.setText(String.valueOf(response.body().size()));
                    showProgressBar("dsd", "sd",false);
                }else {
                    binding.tvTotalAnggota.setText("0");

                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<AnggotaModel>> call, Throwable t) {
                showProgressBar("Sds", "dss", false);
                binding.tvTotalAnggota.setText("0");

                showToast("error", "Tidak ada koneksi internet");


            }
        });

    }
    private void getSopir() {
        showProgressBar("Loading", "Memuat data...", true);
        adminService.getAllSopir().enqueue(new Callback<List<MobilModel>>() {
            @Override
            public void onResponse(Call<List<MobilModel>> call, Response<List<MobilModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvTotalSopir.setText(String.valueOf(response.body().size()));
                    showProgressBar("dsd", "sd",false);
                }else {
                    binding.tvTotalAnggota.setText("0");

                    showProgressBar("Sds", "dss", false);
                }
            }

            @Override
            public void onFailure(Call<List<MobilModel>> call, Throwable t) {
                showProgressBar("Sds", "dss", false);
                binding.tvTotalAnggota.setText("0");

                showToast("error", "Tidak ada koneksi internet");


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

    private void getKonfirmasi() {
        showProgressBar("Loading", "Memuat data...", true);
        adminService.getAllPengajuanByStatus("Menunggu").enqueue(new Callback<List<RiwayatModel>>() {
            @Override
            public void onResponse(Call<List<RiwayatModel>> call, Response<List<RiwayatModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    binding.tvTotalKonfirmasi.setText(String.valueOf(response.body().size()));
                    showProgressBar("Sd", "Dss", false);
                }else {
                    showProgressBar("sds", "sd", false);
                    binding.tvTotalKonfirmasi.setText("0");
                }
            }

            @Override
            public void onFailure(Call<List<RiwayatModel>> call, Throwable t) {
                showProgressBar("sds", "sd", false);
                binding.tvTotalKonfirmasi.setText("0");
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .addToBackStack(null).commit();
    }

}