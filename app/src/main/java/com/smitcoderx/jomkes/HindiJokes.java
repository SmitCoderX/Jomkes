package com.smitcoderx.jomkes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;

public class HindiJokes extends AppCompatActivity {
    public static int confirmation = 0;
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HindiJokesPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi_jokes);

        setTitle("Hindi Jokes");
        showProgressDialog();


        confirmation = 1;

        viewPager = findViewById(R.id.hindiViewpager);
        tabLayout = findViewById(R.id.hindiTabLayout);

        adapter = new HindiJokesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Alia_JokesFragment fragment = new Alia_JokesFragment();

        // getSupportFragmentManager().beginTransaction().replace(R.id.hindiJokesContainer, fragment).commit();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(HindiJokes.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation == 1) {
                    progressDialog.cancel();
                    //Toast.makeText(getContext(), "Internet slow/not available", Toast.LENGTH_SHORT).show();
                } else if (confirmation != 1) {
                    progressDialog.cancel();
                    Snackbar.make(tabLayout, "Internet slow/not available", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 4000);
    }
}