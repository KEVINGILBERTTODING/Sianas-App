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
import com.example.sianasapp.FragmentAdmin.AdminUpdateSopirFragment;
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
            Fragment fragment = new AdminUpdateSopirFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", mobilModelList.get(getAdapterPosition()).getNoMobil());
            bundle.putString("jenis_mobil", mobilModelList.get(getAdapterPosition()).getJenisMobil());
            bundle.putString("nopol", mobilModelList.get(getAdapterPosition()).getNoPlat());
            bundle.putString("nama", mobilModelList.get(getAdapterPosition()).getNama());
            bundle.putString("telepon", mobilModelList.get(getAdapterPosition()).getNoHp());
            bundle.putString("username", mobilModelList.get(getAdapterPosition()).getUsername());
            bundle.putString("password", mobilModelList.get(getAdapterPosition()).getNoHp());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
