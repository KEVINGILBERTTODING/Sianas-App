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
            bundle.putString("user_id", anggotaModelList.get(getAdapterPosition()).getNoAnggota());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
