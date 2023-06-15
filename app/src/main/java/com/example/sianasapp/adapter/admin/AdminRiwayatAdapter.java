package com.example.sianasapp.adapter.admin;

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

import com.example.sianasapp.FragmentAnggota.AnggotaRiwayatDetailFragment;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;

import java.util.List;

public class AdminRiwayatAdapter extends RecyclerView.Adapter<AdminRiwayatAdapter.ViewHolder> {
    Context context;
    List<RiwayatModel> riwayatModelList;

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
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AnggotaRiwayatDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_pengajuan", riwayatModelList.get(getAdapterPosition()).getNoPengajuan());
            bundle.putString("tujuan1", riwayatModelList.get(getAdapterPosition()).getTujuan1());
            bundle.putString("tujuan2", riwayatModelList.get(getAdapterPosition()).getTujuan2());
            bundle.putString("tujuan3", riwayatModelList.get(getAdapterPosition()).getTujuan3());
            bundle.putString("alamat1", riwayatModelList.get(getAdapterPosition()).getAlamat1());
            bundle.putString("alamat2", riwayatModelList.get(getAdapterPosition()).getAlamat2());
            bundle.putString("alamat3", riwayatModelList.get(getAdapterPosition()).getAlamat3());
            bundle.putString("kota1", riwayatModelList.get(getAdapterPosition()).getKota1());
            bundle.putString("kota2", riwayatModelList.get(getAdapterPosition()).getKota2());
            bundle.putString("konfirmasi", riwayatModelList.get(getAdapterPosition()).getKonfirmasi());
            bundle.putString("kota3", riwayatModelList.get(getAdapterPosition()).getKota3());
            bundle.putString("jenis_mobil", riwayatModelList.get(getAdapterPosition()).getJenisMobil());
            bundle.putString("nopol", riwayatModelList.get(getAdapterPosition()).getNoPlat());
            bundle.putString("tgl_pinjam", riwayatModelList.get(getAdapterPosition()).getTglDigunakan());
            bundle.putString("tgl_kembali", riwayatModelList.get(getAdapterPosition()).getTglKembali());
            bundle.putString("nama_sopir", riwayatModelList.get(getAdapterPosition()).getNama());
            bundle.putString("penumpang", riwayatModelList.get(getAdapterPosition()).getMuatan());
            bundle.putString("km_awal", riwayatModelList.get(getAdapterPosition()).getKmAwal());
            bundle.putString("km_akhir", riwayatModelList.get(getAdapterPosition()).getKmAkhir());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
