package com.example.farmiq;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.farmiq.HomeFragment;
import com.example.farmiq.SettingsFragment;
import com.example.farmiq.StoreFragment;
import com.example.farmiq.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);

        // Set default fragment
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_host_fragment, homeFragment);
            fragmentTransaction.commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_home) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.navigation_camera) {
                        selectedFragment = new WeatherFragment();
                    } else if (itemId == R.id.navigation_store) {
                        selectedFragment = new StoreFragment();
                    } else if (itemId == R.id.navigation_settings) {
                        selectedFragment = new SettingsFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, selectedFragment)
                                .commit();
                        return true;
                    }

                    return false;
                }
            };
}
