package com.smitcoderx.jomkes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HindiJokesPagerAdapter extends FragmentPagerAdapter {


    public HindiJokesPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Alia_JokesFragment fragment = new Alia_JokesFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 24;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Alia Jokes";
            case 1:
                return "Berojgari Jokes";
            case 2:
                return "Bollywood Jokes";
            case 3:
                return "Boss/Employee Jokes";
            case 4:
                return "Chintu Jokes";
            case 5:
                return "Country Jokes";
            case 6:
                return "Cricket Jokes";
            case 7:
                return "Doctor/Patient Jokes";
            case 8:
                return "Engineering Jokes";
            case 9:
                return "Father/Son Jokes";
            case 10:
                return "Foreigner Jokes";
            case 11:
                return "Friendship Jokes";
            case 12:
                return "Girl/Boy Jokes";
            case 13:
                return "Husband/Wife Jokes";
            case 14:
                return "Maithili Jokes";
            case 15:
                return "Pappu Jokes";
            case 16:
                return "Politics Jokes";
            case 17:
                return "Rajnikant Jokes";
            case 18:
                return "Random Jokes";
            case 19:
                return "Sarcasm Jokes";
            case 20:
                return "Sharabi Jokes";
            case 21:
                return "Teacher/Student Jokes";
            case 22:
                return "Valentine Day Jokes";
            case 23:
                return "WhatsApp Jokes";
        }
        return super.getPageTitle(position);
    }
}
