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

import com.example.sianasapp.FragmentAdmin.AdminMotorDetailFragment;
import com.example.sianasapp.FragmentAnggota.AnggotaMotorDetailFragment;
import com.example.sianasapp.Model.MotorModel;
import com.example.sianasapp.R;

import java.util.List;

public class AdminMotorAdapter extends RecyclerView.Adapter<AdminMotorAdapter.ViewHolder> {
    Context context;
    List<MotorModel> motorModelList;

    public AdminMotorAdapter(Context context, List<MotorModel> motorModelList) {
        this.context = context;
        this.motorModelList = motorModelList;
    }

    @NonNull
    @Override
    public AdminMotorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_motor, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMotorAdapter.ViewHolder holder, int position) {
        holder.tvNamaMobil.setText(motorModelList.get(holder.getAdapterPosition()).getJenisMotor());
        holder.tvNoPol.setText(motorModelList.get(holder.getAdapterPosition()).getNoPlat());


    }

    @Override
    public int getItemCount() {
        return motorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNamaMobil, tvNoPol, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMobil = itemView.findViewById(R.id.tvNamaMotor);
            tvNoPol = itemView.findViewById(R.id.tvNoPol);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminMotorDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("no_motor", String.valueOf(motorModelList.get(getAdapterPosition()).getNoMotor()));
            bundle.putString("nama_motor", motorModelList.get(getAdapterPosition()).getJenisMotor());
            bundle.putString("no_pol", motorModelList.get(getAdapterPosition()).getNoPlat());
            bundle.putString("tgl_pajak", motorModelList.get(getAdapterPosition()).getTglPajak());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();

        }
    }
}
