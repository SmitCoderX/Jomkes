package com.smitcoderx.jomkes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;


public class JokesFragment extends Fragment {

    public static final String EXTRA_TITLE = "title";

    private RecyclerView recyclerView;
    private ArrayList<FactTopicModel> list;
    private FactTopicAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_jokes, null);
        createJokesTopic();

        recyclerView = root.findViewById(R.id.jokesTopicRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FactTopicAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FactTopicAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                if (position == 0) {
                    Intent hj = new Intent(getActivity(), HindiJokes.class);
                    startActivity(hj);
                } else {
                    Intent all = new Intent(getActivity(), AllJokesActivity.class);
                    FactTopicModel currentItem = list.get(position);
                    all.putExtra(EXTRA_TITLE, currentItem.getName());
                    startActivity(all);
                }
            }
        });
        return root;
    }

    public void createJokesTopic() {
        list = new ArrayList<>();
        list.add(new FactTopicModel(R.drawable.hindi, "Hindi Jokes"));
        list.add(new FactTopicModel(R.drawable.dark, "Dark Jokes"));
        list.add(new FactTopicModel(R.drawable.pun, "Pun Jokes"));
        list.add(new FactTopicModel(R.drawable.programming, "Programming Jokes"));
        list.add(new FactTopicModel(R.drawable.misc, "Miscellaneous Jokes"));
    }
}