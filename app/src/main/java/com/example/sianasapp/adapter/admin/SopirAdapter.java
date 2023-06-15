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

import com.example.sianasapp.FragmentAdmin.AdminUpdateAnggotaFragment;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.Model.MobilModel;
import com.example.sianasapp.R;

import java.util.List;

public class SopirAdapter extends RecyclerView.Adapter<SopirAdapter.ViewHolder> {
    Context context;
    List<MobilModel> mobilModelList;

    public SopirAdapter(Context context, List<MobilModel> mobilModelList) {
        this.context = context;
        this.mobilModelList = mobilModelList;
    }

    @NonNull
    @Override
    public SopirAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_anggota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SopirAdapter.ViewHolder holder, int position) {

        holder.tvSubbag.setText(mobilModelList.get(holder.getAdapterPosition()).getJenisMobil());
        holder.tvNama.setText(mobilModelList.get(holder.getAdapterPosition()).getNama());
    }

    @Override
    public int getItemCount() {
        return mobilModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvSubbag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvSubbag = itemView.findViewById(R.id.tvSubbag);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminUpdateAnggotaFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_mobil", mobilModelList.get(getAdapterPosition()).getNoMobil());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
