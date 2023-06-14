package com.example.sianasapp.adapter.admin;

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

import com.example.sianasapp.FragmentAdmin.AdminMobilDetailFragment;
import com.example.sianasapp.FragmentAnggota.AnggotaMobilDetailFragment;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.R;

import java.util.List;

public class AdminMobilAdapter extends RecyclerView.Adapter<AdminMobilAdapter.ViewHolder> {
    Context context;
    List<MobilModel> mobilModelList;

    public AdminMobilAdapter(Context context, List<MobilModel> mobilModelList) {
        this.context = context;
        this.mobilModelList = mobilModelList;
    }

    @NonNull
    @Override
    public AdminMobilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_mobil, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMobilAdapter.ViewHolder holder, int position) {
        holder.tvNamaMobil.setText(mobilModelList.get(holder.getAdapterPosition()).getJenisMobil());
        holder.tvNoPol.setText(mobilModelList.get(holder.getAdapterPosition()).getNoPlat());

        if (mobilModelList.get(holder.getAdapterPosition()).getStatus().equals("Digunakan")) {
            holder.tvStatus.setText("Digunakan");
            holder.tvStatus.setTextColor(context.getColor(R.color.orange));
        }else {
            holder.tvStatus.setText("Tidak digunakan");
            holder.tvStatus.setTextColor(context.getColor(R.color.main));

        }

    }

    @Override
    public int getItemCount() {
        return mobilModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNamaMobil, tvNoPol, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMobil = itemView.findViewById(R.id.tvNamaMobil);
            tvNoPol = itemView.findViewById(R.id.tvNoPol);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminMobilDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_mobil", mobilModelList.get(getAdapterPosition()).getNoMobil());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
