package com.smitcoderx.jomkes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.appus.splash.Splash;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashy();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about_dev) {
            Intent intent = new Intent(this, AboutDevActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void splashy() {
        Splash.Builder builder = new Splash.Builder(this, getSupportActionBar());
        builder.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        builder.setSplashImage(getResources().getDrawable(R.drawable.default_splash_image));
        builder.perform();
    }
}