package com.example.sianasapp.adapter.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sianasapp.FragmentAdmin.AdminRiwayatDetailFragment;
import com.example.sianasapp.FragmentAnggota.AnggotaRiwayatDetailFragment;
import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AdminService;
import com.example.sianasapp.Util.DataApi;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRiwayatAdapter extends RecyclerView.Adapter<AdminRiwayatAdapter.ViewHolder> {
    Context context;
    List<RiwayatModel> riwayatModelList;
    private AlertDialog progressDialog;
    private AdminService adminService;

    public AdminRiwayatAdapter(Context context, List<RiwayatModel> riwayatModelList) {
        this.context = context;
        this.riwayatModelList = riwayatModelList;
    }

    @NonNull
    @Override
    public AdminRiwayatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_riwayat_pengajuan_admin,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdminRiwayatAdapter.ViewHolder holder, int position) {
        holder.tvNamaMobil.setText(riwayatModelList.get(holder.getAdapterPosition()).getJenisMobil()
        + " | " + riwayatModelList.get(holder.getAdapterPosition()).getNoPlat()
        );
        holder.tvTanggalAwal.setText(riwayatModelList.get(holder.getAdapterPosition()).getTglDigunakan());
        holder.tvTglAkhir.setText(riwayatModelList.get(holder.getAdapterPosition()).getTglKembali());

        holder.tvStatus.setText(riwayatModelList.get(holder.getAdapterPosition()).getKonfirmasi());
        holder.tvSubbag.setText(riwayatModelList.get(holder.getAdapterPosition()).getSubbag());

        if (riwayatModelList.get(holder.getAdapterPosition()).getKonfirmasi().equals("Dicancel")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.red));
            holder.btnTolak.setEnabled(false);
            holder.btnKonfirmasi.setEnabled(false);
        }else if (riwayatModelList.get(holder.getAdapterPosition()).getKonfirmasi().equals("Menunggu")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.blue));
        }else if (riwayatModelList.get(holder.getAdapterPosition()).getKonfirmasi().equals("Dikonfirmasi")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.green));
            holder.btnKonfirmasi.setEnabled(false);
        }else if (riwayatModelList.get(holder.getAdapterPosition()).getKonfirmasi().equals("Ditolak")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.red));
            holder.btnTolak.setEnabled(false);
        }







    }

    @Override
    public int getItemCount() {
        return riwayatModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNamaMobil, tvSubbag, tvTanggalAwal, tvTglAkhir, tvStatus;
        Button btnTolak, btnKonfirmasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMobil = itemView.findViewById(R.id.tvJenisMobil);
            tvTanggalAwal = itemView.findViewById(R.id.tvTglAwal);
            tvTglAkhir = itemView.findViewById(R.id.tvTglAkhir);
            tvSubbag = itemView.findViewById(R.id.tvNamaSubbag);
            btnKonfirmasi = itemView.findViewById(R.id.btnKonfirmasi);
            btnTolak = itemView.findViewById(R.id.btnTolak);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(this);

            adminService = DataApi.getClient().create(AdminService.class);


            btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar("Loading", "Menyimpan perubahan...", true);
                    adminService.decision(
                            riwayatModelList.get(getAdapterPosition()).getNoPengajuan(),
                            "Dikonfirmasi"
                    ).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getCode() == 200) {
                                showProgressBar("Dsd", "d", false);
                                showToast("success", "Berhasil konfirmasi pengajuan...");
                                riwayatModelList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }else {
                                showProgressBar("sd", "Sd", false);
                                showToast("err", "Terjadi kesalahan");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            showProgressBar("sd", "Sd", false);
                            showToast("err", "Tidak ada koneksi internet");

                        }
                    });

                }
            });
            btnTolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar("Loading", "Menyimpan perubahan...", true);
                    adminService.decision(
                            riwayatModelList.get(getAdapterPosition()).getNoPengajuan(),
                            "Ditolak"
                    ).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getCode() == 200) {
                                showProgressBar("Dsd", "d", false);
                                showToast("success", "Berhasil konfirmasi pengajuan...");
                                riwayatModelList.remove(getAdapterPosition());
                                riwayatModelList.get(getAdapterPosition()).setKonfirmasi("Ditolak");
                                notifyDataSetChanged();
                            }else {
                                showProgressBar("sd", "Sd", false);
                                showToast("err", "Terjadi kesalahan");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            showProgressBar("sd", "Sd", false);
                            showToast("err", "Tidak ada koneksi internet");

                        }
                    });

                }
            });

        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminRiwayatDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_pengajuan", riwayatModelList.get(getAdapterPosition()).getNoPengajuan());
            bundle.putString("tujuan1", riwayatModelList.get(getAdapterPosition()).getTujuan1());
            bundle.putString("tujuan2", riwayatModelList.get(getAdapterPosition()).getTujuan2());
            bundle.putString("tujuan3", riwayatModelList.get(getAdapterPosition()).getTujuan3());
            bundle.putString("alamat1", riwayatModelList.get(getAdapterPosition()).getAlamat1());
            bundle.putString("alamat2", riwayatModelList.get(getAdapterPosition()).getAlamat2());
            bundle.putString("alamat3", riwayatModelList.get(getAdapterPosition()).getAlamat3());
            bundle.putString("bukti", riwayatModelList.get(getAdapterPosition()).getBukti());
            bundle.putString("kota1", riwayatModelList.get(getAdapterPosition()).getKota1());
            bundle.putString("kota2", riwayatModelList.get(getAdapterPosition()).getKota2());
            bundle.putString("konfirmasi", riwayatModelList.get(getAdapterPosition()).getKonfirmasi());
            bundle.putString("konfirmasi_sopir", riwayatModelList.get(getAdapterPosition()).getKonfirmasiSopir());
            bundle.putString("kota3", riwayatModelList.get(getAdapterPosition()).getKota3());
            bundle.putString("jenis_mobil", riwayatModelList.get(getAdapterPosition()).getJenisMobil());
            bundle.putString("nopol", riwayatModelList.get(getAdapterPosition()).getNoPlat());
            bundle.putString("tgl_pinjam", riwayatModelList.get(getAdapterPosition()).getTglDigunakan());
            bundle.putString("tgl_kembali", riwayatModelList.get(getAdapterPosition()).getTglKembali());
            bundle.putString("nama_sopir", riwayatModelList.get(getAdapterPosition()).getNama());
            bundle.putString("penumpang", riwayatModelList.get(getAdapterPosition()).getMuatan());
            bundle.putString("km_awal", riwayatModelList.get(getAdapterPosition()).getKmAwal());
            bundle.putString("km_akhir", riwayatModelList.get(getAdapterPosition()).getKmAkhir());
            bundle.putString("lat1", riwayatModelList.get(getAdapterPosition()).getLat1());
            bundle.putString("lat2", riwayatModelList.get(getAdapterPosition()).getLat2());
            bundle.putString("lat3", riwayatModelList.get(getAdapterPosition()).getLat3());
            bundle.putString("lng1", riwayatModelList.get(getAdapterPosition()).getLng1());
            bundle.putString("lng2", riwayatModelList.get(getAdapterPosition()).getLng2());
            bundle.putString("lng3", riwayatModelList.get(getAdapterPosition()).getLng3());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }





        private void showProgressBar(String title, String message, boolean isLoading) {
            if (isLoading) {
                // Membuat progress dialog baru jika belum ada
                if (progressDialog == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                Toasty.success(context, text, Toasty.LENGTH_SHORT).show();
            }else {
                Toasty.error(context, text, Toasty.LENGTH_SHORT).show();
            }
        }
    }
}
