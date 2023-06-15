package com.example.sianasapp.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sianasapp.Model.AnggotaModel;
import com.example.sianasapp.R;

import java.util.List;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.ViewHolder> {
    Context context;
    List<AnggotaModel> anggotaModelList;

    public AnggotaAdapter(Context context, List<AnggotaModel> anggotaModelList) {
        this.context = context;
        this.anggotaModelList = anggotaModelList;
    }

    @NonNull
    @Override
    public AnggotaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_anggota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnggotaAdapter.ViewHolder holder, int position) {

        holder.tvSubbag.setText(anggotaModelList.get(holder.getAdapterPosition()).getSubbag());
        holder.tvNama.setText(anggotaModelList.get(holder.getAdapterPosition()).getNama());
    }

    @Override
    public int getItemCount() {
        return anggotaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvSubbag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvSubbag = itemView.findViewById(R.id.tvSubbag);
        }
    }
}
