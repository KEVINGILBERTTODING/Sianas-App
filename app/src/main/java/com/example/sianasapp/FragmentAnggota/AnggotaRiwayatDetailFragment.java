package com.example.sianasapp.FragmentAnggota;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAnggotaRiwayatDetailBinding;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnggotaRiwayatDetailFragment extends Fragment {
    private String noPemesanan;

    private FragmentAnggotaRiwayatDetailBinding binding;
    private AnggotaIService anggotaIService;

    private AlertDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaRiwayatDetailBinding.inflate(inflater, container, false);
        noPemesanan = getArguments().getString("no_pengajuan");
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);

        binding.etAlamattujuan1.setText(getArguments().getString("alamat1"));
        binding.etAlamattujuan2.setText(getArguments().getString("alamat2"));
        binding.etAlamattujuan3.setText(getArguments().getString("alamat3"));
        binding.etKota1.setText(getArguments().getString("kota1"));
        binding.etKota2.setText(getArguments().getString("kota2"));
        binding.etKota3.setText(getArguments().getString("kota3"));
        binding.etTujuankunjungan1.setText(getArguments().getString("tujuan1"));
        binding.etTujuankunjungan2.setText(getArguments().getString("tujuan2"));
        binding.etTujuankunjungan3.setText(getArguments().getString("tujuan3"));
        binding.etJenismobil.setText(getArguments().getString("jenis_mobil"));
        binding.etNoPolisi.setText(getArguments().getString("nopol"));
        binding.etTglPeminjaman.setText(getArguments().getString("tgl_pinjam"));
        binding.etTglKembali.setText(getArguments().getString("tgl_kembali"));
        binding.etNamaSopir.setText(getArguments().getString("nama_sopir"));
        binding.etBanyakPenumpang.setText(getArguments().getString("penumpang"));
        binding.etKmAwal.setText(getArguments().getString("km_awal"));
        binding.etKmAkhir.setText(getArguments().getString("km_akhir"));

        if (getArguments().getString("konfirmasi").equals("Dicancel")) {
            binding.btnBatal.setVisibility(View.GONE);
        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPengajuan();
            }
        });
    }

    private void cancelPengajuan() {
        showProgressBar("Loading", "Cacel pengajuan...", true);
        anggotaIService.cancelPengajuan(noPemesanan).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showProgressBar("sdd", "dss", false);
                    showToast("success", "Berhasil cancel pengajuan");
                    getActivity().onBackPressed();
                }else {
                    showProgressBar("sdd", "dss", false);
                    showToast("error", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showProgressBar("sdd", "dss", false);
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