package com.example.sianasapp.FragmentSopir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sianasapp.FragmentAdmin.AdminProfilFragment;
import com.example.sianasapp.FragmentAdmin.AdminRiwayatFragment;
import com.example.sianasapp.FragmentAdmin.FragmentAdminHome;
import com.example.sianasapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SopirMainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopir_main); bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new FragmentSopirHome());
                    return true;
                }else if (item.getItemId() == R.id.menuRiwayat) {
                    replace(new AdminRiwayatFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new FragmentSopirProfil());
                    return true;
                }
                return false;
            }
        });
        replace(new FragmentSopirHome());


    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameSopir, fragment)
                .commit();
    }
}