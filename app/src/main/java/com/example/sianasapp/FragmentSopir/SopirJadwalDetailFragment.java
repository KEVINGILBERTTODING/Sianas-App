package com.example.sianasapp.FragmentSopir;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.Util.SopirService;
import com.example.sianasapp.databinding.FragmentSopirJadwalDetailBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SopirJadwalDetailFragment extends Fragment {
    private String noPemesanan;

    private FragmentSopirJadwalDetailBinding binding;
    private SopirService sopirService;
    private File file;

    private AlertDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSopirJadwalDetailBinding.inflate(inflater, container, false);
        noPemesanan = getArguments().getString("no_pengajuan");
        sopirService = DataApi.getClient().create(SopirService.class);

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
        binding.tvStatusSopir.setText(getArguments().getString("konfirmasi_sopir"));





        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }

        });

        binding.btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasiJadwal();

            }
        });

        binding.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolakKonfirmasi();
            }
        });


    }

    private void konfirmasiJadwal() {
        showProgressBar("Loading", "Mengirim data...", true);
        HashMap map = new HashMap();
        map.put("id", RequestBody.create(MediaType.parse("text/plain"), noPemesanan));
        map.put("km_awal", RequestBody.create(MediaType.parse("text/plain"), binding.etKmAwal.getText().toString()));
        map.put("km_akhir", RequestBody.create(MediaType.parse("text/plain"), binding.etKmAkhir.getText().toString()));

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("bukti", file.getName(), requestBody);

        sopirService.konfirmasiJadwal(map, image).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showProgressBar("s", "s", false);
                    showToast("success", "Berhasil konfirmasi jadwal");
                    getActivity().onBackPressed();
                }else {
                    showProgressBar("s", "s", false);
                    showToast("error", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showProgressBar("s", "s", false);
                showToast("error", "Tidak ada koneksi internet");

            }
        });
    }

    private void tolakKonfirmasi() {
        showProgressBar("Loading", "Tolak jadwal....", true);
        sopirService.tolakJadwal(noPemesanan).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showProgressBar("sd", "sd", false);
                    showToast("success", "Berhasil Tolak jadwal");
                    getActivity().onBackPressed();
                }else {
                    showProgressBar("sd", "sd", false);
                    showToast("err", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showProgressBar("sd", "sd", false);
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                file = new File(pdfPath);
                binding.etPath.setText(file.getName());
            }
        }
    }

    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}