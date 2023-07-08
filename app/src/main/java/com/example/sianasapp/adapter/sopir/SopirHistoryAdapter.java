package com.example.sianasapp.adapter.sopir;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sianasapp.FragmentSopir.SopirHistoryDetailFragment;
import com.example.sianasapp.FragmentSopir.SopirJadwalDetailFragment;
import com.example.sianasapp.Model.RiwayatModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AdminService;

import java.util.List;

public class SopirHistoryAdapter extends RecyclerView.Adapter<SopirHistoryAdapter.ViewHolder> {
    Context context;
    List<RiwayatModel> riwayatModelList;
    private AlertDialog progressDialog;
    private AdminService adminService;

    public SopirHistoryAdapter(Context context, List<RiwayatModel> riwayatModelList) {
        this.context = context;
        this.riwayatModelList = riwayatModelList;
    }

    @NonNull
    @Override
    public SopirHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_jadwal_sopir,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SopirHistoryAdapter.ViewHolder holder, int position) {

        holder.tvTanggalAwal.setText(riwayatModelList.get(holder.getAdapterPosition()).getTglDigunakan());
        holder.tvTglAkhir.setText(riwayatModelList.get(holder.getAdapterPosition()).getTglKembali());
        holder.tvNoTelp.setText(riwayatModelList.get(holder.getAdapterPosition()).getNoHp());
        holder.tvNama.setText(riwayatModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvSubbag.setText(riwayatModelList.get(holder.getAdapterPosition()).getSubbag());


    }

    @Override
    public int getItemCount() {
        return riwayatModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama,  tvSubbag, tvTanggalAwal, tvTglAkhir, tvNoTelp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggalAwal = itemView.findViewById(R.id.tvTglAwal);
            tvTglAkhir = itemView.findViewById(R.id.tvTglAkhir);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvSubbag = itemView.findViewById(R.id.tvNamaSubbag);
            tvNoTelp = itemView.findViewById(R.id.tvNoTelp);

            itemView.setOnClickListener(this);





        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new SopirHistoryDetailFragment();
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
                    .replace(R.id.frameSopir, fragment).addToBackStack(null).commit();

        }




    }
}
