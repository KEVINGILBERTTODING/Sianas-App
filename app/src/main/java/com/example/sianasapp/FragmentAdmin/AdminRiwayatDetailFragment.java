package com.example.sianasapp.FragmentAdmin;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.sianasapp.Model.ResponseModel;
import com.example.sianasapp.R;
import com.example.sianasapp.Util.AnggotaIService;
import com.example.sianasapp.Util.Constans;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.example.sianasapp.Util.DataApi;
import com.example.sianasapp.databinding.FragmentAdminRiwayatDetailBinding;
import com.example.sianasapp.databinding.FragmentAnggotaRiwayatDetailBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRiwayatDetailFragment extends Fragment implements OnMapReadyCallback{
    private String noPemesanan;

    private FragmentAdminRiwayatDetailBinding binding;
    private AnggotaIService anggotaIService;
    private MapView mapView;
    private GoogleMap googleMap;
    private FrameLayout frameLayout;
    private AlertDialog progressDialog;
    private String lat1, lat2, lat3, lng1, lng2, lng3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminRiwayatDetailBinding.inflate(inflater, container, false);
        noPemesanan = getArguments().getString("no_pengajuan");
        anggotaIService = DataApi.getClient().create(AnggotaIService.class);
        frameLayout = binding.getRoot().findViewById(R.id.frameGmaps);

        binding.etAlamattujuan1.setText(getArguments().getString("alamat1"));
        binding.etAlamattujuan2.setText(getArguments().getString("alamat2"));
        binding.etAlamattujuan3.setText(getArguments().getString("alamat3"));
        binding.etKota1.setText(getArguments().getString("kota1"));
        binding.etKota2.setText(getArguments().getString("kota2"));
        binding.etKota3.setText(getArguments().getString("kota3"));
        binding.etTujuankunjungan1.setText(getArguments().getString("tujuan1"));
        binding.etTujuankunjungan2.setText(getArguments().getString("tujuan2"));
        binding.etTujuankunjungan3.setText(getArguments().getString("tujuan3"));
        binding.etJenismobil.setText(getArguments().getString("jenis_mobil"));
        binding.etNoPolisi.setText(getArguments().getString("nopol"));
        binding.etTglPeminjaman.setText(getArguments().getString("tgl_pinjam"));
        binding.etTglKembali.setText(getArguments().getString("tgl_kembali"));
        binding.etNamaSopir.setText(getArguments().getString("nama_sopir"));
        binding.etBanyakPenumpang.setText(getArguments().getString("penumpang"));
        binding.etKmAwal.setText(getArguments().getString("km_awal"));
        binding.etKmAkhir.setText(getArguments().getString("km_akhir"));
        binding.tvStatusSopir.setText(getArguments().getString("konfirmasi_sopir"));
        lat1 = getArguments().getString("lat1");
        lat2 = getArguments().getString("lat2");
        lat3 = getArguments().getString("lat3");
        lng1 = getArguments().getString("lng1");
        lng2 = getArguments().getString("lng2");
        lng3 = getArguments().getString("lng3");


        if (getArguments().getString("bukti") == null) {
            binding.btnDownloadBukti.setEnabled(false);
        }else{
            binding.btnDownloadBukti.setEnabled(true);
        }



        mapView = new MapView(getContext());
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        frameLayout.addView(mapView);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.btnDownloadSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Constans.URL_DOWNLOAD_SURAT_PEMINJAMAAN + noPemesanan;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        binding.btnDownloadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Constans.URL_DOWNLOAD_BUKTI + noPemesanan;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (!lat1.isEmpty()) {
            LatLng latLng1 = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1));
            googleMap.addMarker(new MarkerOptions().position(latLng1).title("Tujuan 1"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));

        }

        if (!lat2.isEmpty()){
            LatLng latLng2 = new LatLng(Double.parseDouble(lat2), Double.parseDouble(lng2));
            googleMap.addMarker(new MarkerOptions().position(latLng2).title("Tujuan 2"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 15));
        }

        if (!lat3.isEmpty()){
            LatLng latLng3 = new LatLng(Double.parseDouble(lat3), Double.parseDouble(lng3));
            googleMap.addMarker(new MarkerOptions().position(latLng3).title("Tujuan 3"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng3, 15));
        }




        // Aktifkan kontrol zoom dan zoom menggunakan gerakan jari
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        // Memastikan peta memiliki prioritas untuk menerima gesture
        mapView.requestDisallowInterceptTouchEvent(true);
        
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}