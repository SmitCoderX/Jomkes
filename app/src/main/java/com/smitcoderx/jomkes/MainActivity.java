package com.smitcoderx.jomkes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ImageView darkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        darkUI();
        checkAndRequestPermission();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        BubbleNavigationConstraintView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                Fragment selectedFragment = null;
                switch (position) {
                    case 0:
                        selectedFragment = new JokesFragment();
                        break;
                    case 1:
                        selectedFragment = new MemesFragment();
                        break;
                    case 2:
                        selectedFragment = new FactsFragment();
                        break;
                    case 3:
                        selectedFragment = new AboutFragment();
                        break;
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new JokesFragment()).commit();
        }
        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        String deviceOS = Build.VERSION.CODENAME;
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, deviceName);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.setUserProperty("os_version", deviceOS);
        mFirebaseAnalytics.setUserProperty("device_manufacturer", deviceMan);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void splashy() {

    }

    private void checkAndRequestPermission() {
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionChecked = new ArrayList<>();

        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionChecked.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionChecked.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionChecked.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionChecked.toArray
                    (new String[listPermissionChecked.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    public void darkUI() {

        darkImage = findViewById(R.id.darkNight);
        // Toast.makeText(MainActivity.this, "Choose Theme", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        darkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDarkModeOn) {

                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();

                } else {

                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();

                }
            }
        });
    }
}