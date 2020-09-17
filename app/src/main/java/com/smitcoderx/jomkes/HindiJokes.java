package com.smitcoderx.jomkes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HindiJokes extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HindiJokesPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi_jokes);

        setTitle("Hindi Jokes");

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
}