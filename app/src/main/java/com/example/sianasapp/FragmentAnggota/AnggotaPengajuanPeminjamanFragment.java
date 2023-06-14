package com.example.sianasapp.FragmentAnggota;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.adapter.anggota.SpinnerMobilAdapter;
import com.example.sianasapp.databinding.FragmentAnggotaPengajuanPeminjamanBinding;

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

public class AnggotaPengajuanPeminjamanFragment extends Fragment {

    private AlertDialog progressDialog;
    private File file;
    private SpinnerMobilAdapter spinnerMobilAdapter;
    List<MobilModel> mobilModelList;
    private AnggotaIService anggotaIService;
    SharedPreferences sharedPreferences;
    private String userId, noMobil;
    private FragmentAnggotaPengajuanPeminjamanBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaPengajuanPeminjamanBinding.inflate(inflater, container, false);
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAvailabelCar();
        listener();
    }

    private void listener() {
        binding.spJenisMobil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noMobil = spinnerMobilAdapter.getMobilId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);

            }
        });
        binding.etTglPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePickerDialog(binding.etTglPeminjaman);
            }
        });

        binding.etTglKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePickerDialog(binding.etTglKembali);
            }
        });

        binding.btnpengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etPath.getText().toString().isEmpty()) {
                    binding.etPath.setError("Tidak boleh kosong");
                }else {
                    simpan();
                }
            }
        });
    }

    private void getAvailabelCar() {
        showProgressBar("Loading", "Memuat data...", true);
        anggotaIService.getMobilByStatus("Tidak_digunakan").enqueue(new Callback<List<MobilModel>>() {
            @Override
            public void onResponse(Call<List<MobilModel>> call, Response<List<MobilModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    mobilModelList = response.body();
                    spinnerMobilAdapter = new SpinnerMobilAdapter(getContext(), mobilModelList);
                    showProgressBar("sds", "dss", false);
                    binding.spJenisMobil.setAdapter(spinnerMobilAdapter);
                }else {
                    binding.btnpengajuan.setEnabled(false);
                    showProgressBar("sds", "dss", false);
                    showToast("error", "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<List<MobilModel>> call, Throwable t) {
                binding.btnpengajuan.setEnabled(false);
                showProgressBar("sds", "dss", false);
                showToast("error", "Tidak ada koneksi internet");
            }
        });

    }

    private void simpan() {
        showProgressBar("Loading", "Menyimpan data...", true);
        HashMap map = new HashMap();
        map.put("id", RequestBody.create(MediaType.parse("text/plain"), userId));
        map.put("no_mobil", RequestBody.create(MediaType.parse("text/plain"), noMobil));
        map.put("tujuan_1", RequestBody.create(MediaType.parse("text/plain"), binding.etTujuankunjungan1.getText().toString()));
        map.put("tujuan_2", RequestBody.create(MediaType.parse("text/plain"), binding.etTujuankunjungan2.getText().toString()));
        map.put("tujuan_3", RequestBody.create(MediaType.parse("text/plain"), binding.etTujuankunjungan3.getText().toString()));
        map.put("alamat_1", RequestBody.create(MediaType.parse("text/plain"), binding.etAlamattujuan1.getText().toString()));
        map.put("alamat_2", RequestBody.create(MediaType.parse("text/plain"), binding.etAlamattujuan2.getText().toString()));
        map.put("alamat_3", RequestBody.create(MediaType.parse("text/plain"), binding.etAlamattujuan3.getText().toString()));
        map.put("kota_1", RequestBody.create(MediaType.parse("text/plain"), binding.etKota1.getText().toString()));
        map.put("kota_2", RequestBody.create(MediaType.parse("text/plain"), binding.etKota2.getText().toString()));
        map.put("kota_3", RequestBody.create(MediaType.parse("text/plain"), binding.etKota3.getText().toString()));
        map.put("muatan", RequestBody.create(MediaType.parse("text/plain"), binding.etBanyakPenumpang.getText().toString()));
        map.put("tgl_digunakan", RequestBody.create(MediaType.parse("text/plain"), binding.etTglPeminjaman.getText().toString()));
        map.put("tgl_kembali", RequestBody.create(MediaType.parse("text/plain"), binding.etTglKembali.getText().toString()));

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part filePdf = MultipartBody.Part.createFormData("surat", file.getName(), requestBody);

        anggotaIService.addPengajuan(map, filePdf).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getCode() == 200) {
                    showProgressBar("ssds", "ds", false);
                    showToast("success", "Berhasil mengajukan");
                    getActivity().onBackPressed();
                }else {
                    showToast("error", response.body().getMessage());
                    showProgressBar("dd", "sdsd", false);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showToast("error", "Tidak ada koneksi internet");
                showProgressBar("dd", "sdsd", false);

            }
        });

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

    private void getDatePickerDialog(TextView tvDate) {
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