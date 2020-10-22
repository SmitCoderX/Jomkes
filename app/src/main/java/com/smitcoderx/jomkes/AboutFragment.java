package com.smitcoderx.jomkes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    private Button button;
    private LinearLayout aboutDev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        button = view.findViewById(R.id.buyCoffee);

        aboutDev = view.findViewById(R.id.aboutProg);

        aboutDev();
        buyCoffee();

        return view;
    }

    public void aboutDev() {
        aboutDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutDevActivity.class);
                startActivity(intent);
            }
        });
    }

    public void buyCoffee() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Thanks for this...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
