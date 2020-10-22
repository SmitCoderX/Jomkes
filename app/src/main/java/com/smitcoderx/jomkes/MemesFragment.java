package com.smitcoderx.jomkes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
import java.util.Objects;

public class MemesFragment extends Fragment {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";
    public static final String EXTRA_TITLE = "titleName";

    public static boolean isRefreshed;
    public static int confirmation = 0;
    ProgressDialog progressDialog;
    private RecyclerView memeRecyclerView;
    private MemeAdapter memeAdapter;
    private RequestQueue requestQueue;
    private ArrayList<MemeModelClass> memeList;
    private SwipeRefreshLayout memeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_memes, null);

        memeLayout = root.findViewById(R.id.memeSwipeRefresh);
        memeRecyclerView = root.findViewById(R.id.memeRecyclerView);
        memeRecyclerView.setHasFixedSize(true);
        memeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        memeList = new ArrayList<>();
        showProgressDialog();
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
                Snackbar.make(memeLayout, "Memes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void parseMemeJSON() {
        String memeUrl = getString(R.string.meme_api);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, memeUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("memes");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("author");
                                String imageURL = hit.getString("url");
                                String title = hit.getString("title");
                                int likes = hit.getInt("ups");

                                memeList.add(new MemeModelClass(imageURL, creatorName, title, likes));
                            }

                            Runnable progressRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    memeAdapter = new MemeAdapter(getContext(), memeList);
                                    memeRecyclerView.setAdapter(memeAdapter);

                                    memeAdapter.setOnItemClickListener(new MemeAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            Intent intent = new Intent(getContext(), SingleMemeActivity.class);
                                            MemeModelClass clickedItem = memeList.get(position);

                                            intent.putExtra(EXTRA_URL, clickedItem.getURL());
                                            intent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
                                            intent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
                                            intent.putExtra(EXTRA_LIKES, clickedItem.getUpVotes());

                                            startActivity(intent);
                                        }
                                    });

                                }
                            };

                            Handler pdCanceller = new Handler();
                            pdCanceller.postDelayed(progressRunnable, 1000);
                            confirmation = 1;


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

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
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
                    Snackbar.make(memeRecyclerView, "Internet slow/not available", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 4000);
    }
}