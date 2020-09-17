package com.smitcoderx.jomkes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FactsFragment extends Fragment {

    public static final String EXTRA_TITLE = "title";

    private RecyclerView factTopicRecyclerView;
    ArrayList<FactTopicModel> mTopicList;
    private FactTopicAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facts, container, false);
        createTopic();


        factTopicRecyclerView = view.findViewById(R.id.factTopicRecyclerView);
        factTopicRecyclerView.setHasFixedSize(true);
        factTopicRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new FactTopicAdapter(getContext(), mTopicList);
        factTopicRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FactTopicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), FactsActivity.class);
                FactTopicModel clickedItem = mTopicList.get(position);

                intent.putExtra(EXTRA_TITLE, clickedItem.getName());

                startActivity(intent);
            }
        });
        return view;
    }

    public void createTopic() {
        mTopicList = new ArrayList<>();
        mTopicList.add(new FactTopicModel(R.drawable.animals, "Animal Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.celebrity, "Celebrity Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.cricket, "Cricket Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.history, "History Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.human, "Human Body Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.marijuana, "Marijuana Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.mobile, "Mobile Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.movie, "Movie/Shows Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.nature, "Nature Facts"));
        mTopicList.add(new FactTopicModel(R.drawable.random, "Random Facts"));
    }

}