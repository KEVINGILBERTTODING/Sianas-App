package com.example.sianasapp.FragmentAnggota;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.adapter.anggota.AnggotaMobilAdapter;
import com.example.sianasapp.adapter.anggota.AnggotaRiwayatAdapter;
import com.example.sianasapp.databinding.FragmentAnggotaRiwayatBinding;

import org.w3c.dom.Text;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnggotaRiwayatFragment extends Fragment {
    private FragmentAnggotaRiwayatBinding binding;
    LinearLayoutManager linearLayoutManager;
    List<RiwayatModel> riwayatModelList;
    AnggotaRiwayatAdapter anggotaRiwayatAdapter;
    AnggotaIService anggotaIService;
    SharedPreferences sharedPreferences;
    private AlertDialog progressDialog;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaRiwayatBinding.inflate(inflater, container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHistory();
        listener();
    }

    private void listener() {
        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_filter);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView tvTglAwal, tvTglAkhir;
                tvTglAwal = dialog.findViewById(R.id.tvTglAwal);
                tvTglAkhir = dialog.findViewById(R.id.tvTglAkhir);
                tvTglAwal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDateTimePicker(tvTglAwal);


                    }
                });
                tvTglAkhir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDateTimePicker(tvTglAkhir);

                    }
                });
                Button btnFilter = dialog.findViewById(R.id.btnFilter);

                btnFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvTglAwal.getText().toString().isEmpty()) {
                            tvTglAwal.setError("Tidak boleh kosong");
                        }else if (tvTglAkhir.getText().toString().isEmpty()) {
                            tvTglAkhir.setError("Tidak boleh kosong");
                        }else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(Constans.URL_DOWNLOAD_LAPORAN  + userId +"/" + tvTglAwal.getText().toString() + "/" + tvTglAkhir
                                    .getText().toString()));
                            startActivity(intent);
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void getHistory() {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMyHistory(userId).enqueue(new Callback<List<RiwayatModel>>() {
            @Override
            public void onResponse(Call<List<RiwayatModel>> call, Response<List<RiwayatModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    riwayatModelList = response.body();
                    anggotaRiwayatAdapter = new AnggotaRiwayatAdapter(getContext(), riwayatModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvRiwayat.setLayoutManager(linearLayoutManager);
                    binding.rvRiwayat.setAdapter(anggotaRiwayatAdapter);
                    binding.rvRiwayat.setHasFixedSize(true);
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

    private void getDateTimePicker(TextView tvDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateFormatted, monthFormatted;
                if (month < 10) {
                    monthFormatted = String.format("%02d", month + 1);
                }else {
                    monthFormatted = String.valueOf(month + 1);
                }

                if (dayOfMonth < 10) {
                    dateFormatted = String.format("%02d", dayOfMonth);
                }else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }

                tvDate.setText(year + "-" + monthFormatted + "-" + dateFormatted);
            }
        });

        datePickerDialog.show();
    }
}