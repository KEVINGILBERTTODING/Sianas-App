package com.example.sianasapp.FragmentAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sianasapp.FragmentAnggota.AnggotaHomeFragment;
import com.example.sianasapp.FragmentAnggota.AnggotaProfilFragment;
import com.example.sianasapp.FragmentAnggota.AnggotaRiwayatFragment;
import com.example.sianasapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivty extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_activty);
        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new FragmentAdminHome());
                    return true;
                }else if (item.getItemId() == R.id.menuRiwayat) {
                    replace(new AnggotaRiwayatFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new AnggotaProfilFragment());
                    return true;
                }
                return false;
            }
        });
        replace(new FragmentAdminHome());


    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .commit();
    }

}