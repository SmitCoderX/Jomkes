package com.smitcoderx.jomkes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class JokesFragment extends Fragment {

    private ImageView banner1, banner2, banner3, banner4, banner5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_jokes, null);

        banner1 = root.findViewById(R.id.jokes_banner1);
        banner2 = root.findViewById(R.id.jokes_banner2);
        banner3 = root.findViewById(R.id.jokes_banner3);
        banner4 = root.findViewById(R.id.jokes_banner4);
        banner5 = root.findViewById(R.id.jokes_banner5);

        OnClicks();

        return root;
        //inflater.inflate(R.layout.fragment_jokes, container, false);
    }

    public void OnClicks() {
        banner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hj = new Intent(getActivity(), HindiJokes.class);
                startActivity(hj);
            }
        });

        banner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dj = new Intent(getActivity(), DarkJokesActivity.class);
                startActivity(dj);
            }
        });

        banner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pj = new Intent(getActivity(), PunJokesActivity.class);
                startActivity(pj);
            }
        });

        banner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent progJ = new Intent(getActivity(), ProgrammingJokesActivity.class);
                startActivity(progJ);
            }
        });

        banner5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miscJ = new Intent(getActivity(), MiscellaneousJokesActivity.class);
                startActivity(miscJ);
            }
        });
    }
}