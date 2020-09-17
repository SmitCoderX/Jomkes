package com.smitcoderx.jomkes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MemesFragment extends Fragment implements MemeAdapter.OnItemClickListener {

    public static boolean isRefreshed;
    private RecyclerView memeRecyclerView;
    private MemeAdapter memeAdapter;
    private RequestQueue requestQueue;
    private ArrayList<MemeModelClass> memeList;
    private SwipeRefreshLayout memeLayout;
    private String imageURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_memes, null);

        memeLayout = root.findViewById(R.id.memeSwipeRefresh);
        memeRecyclerView = root.findViewById(R.id.memeRecyclerView);
        memeRecyclerView.setHasFixedSize(true);
        memeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        memeList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());
        parseMemeJSON();

        memeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                memeList.clear();
                memeAdapter.notifyDataSetChanged();
                parseMemeJSON();
                memeLayout.setRefreshing(false);
                Snackbar.make(memeRecyclerView, "Jokes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void parseMemeJSON() {
        String memeUrl = "https://meme-api.herokuapp.com/gimme/50";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, memeUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("memes");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("author");
                                imageURL = hit.getString("url");
                                int likes = hit.getInt("ups");

                                memeList.add(new MemeModelClass(imageURL, creatorName, likes));
                            }

                            memeAdapter = new MemeAdapter(getContext(), memeList);
                            memeRecyclerView.setAdapter(memeAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {


    }
}