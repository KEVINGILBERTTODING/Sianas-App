package com.example.sianasapp.FragmentAnggota;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sianasapp.R;
import com.example.sianasapp.databinding.FragmentAnggotaMotorDetailBinding;

public class AnggotaMotorDetailFragment extends Fragment {

    private FragmentAnggotaMotorDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnggotaMotorDetailBinding.inflate(inflater, container, false);
        binding.etJenismotor.setText(getArguments().getString("nama_motor"));
        binding.etNoPolisi.setText(getArguments().getString("no_pol"));
        binding.etNorangka.setText(getArguments().getString("tgl_pajak"));
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}