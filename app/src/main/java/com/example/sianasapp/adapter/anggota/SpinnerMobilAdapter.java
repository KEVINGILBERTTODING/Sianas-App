package com.example.sianasapp.adapter.anggota;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.sianasapp.Model.MobilModel;

import java.util.List;

public class SpinnerMobilAdapter extends ArrayAdapter<MobilModel> {

   public SpinnerMobilAdapter(@NonNull Context context, List<MobilModel> rekeningmodel){
            super(context, android.R.layout.simple_spinner_item, rekeningmodel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getJenisMobil() + " - " + getItem(position).getNoPlat()  + " - " + getItem(position).getNama());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getJenisMobil() + " - " + getItem(position).getNoPlat()  + " - " + getItem(position).getNama());
            return view;
        }



    public String getMobilId(int position) {
        return getItem(position).getNoMobil();
    }





    }
