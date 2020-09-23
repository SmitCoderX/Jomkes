package com.smitcoderx.jomkes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.smitcoderx.jomkes.JokesFragment.EXTRA_TITLE;

public class AllJokesActivity extends AppCompatActivity {

    public static boolean isRefreshed;
    public static int confirmation = 0;
    ProgressDialog progressDialog;
    private RecyclerView allJokesRecyclerView;
    private JokesAdapter adapter;
    private RequestQueue requestQueue;
    private ArrayList<JokesModelClass> list;
    private String title;
    private SwipeRefreshLayout layout;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alljokes);

        Intent intent = getIntent();
        title = intent.getStringExtra(EXTRA_TITLE);
        setTitle(title);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        layout = findViewById(R.id.swipeRefresh);
        allJokesRecyclerView = findViewById(R.id.allJokesRecyclerView);
        allJokesRecyclerView.setHasFixedSize(true);
        allJokesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        showProgressDialog();
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                list.clear();
                adapter.notifyDataSetChanged();
                parseJSON();
                layout.setRefreshing(false);
                Snackbar.make(allJokesRecyclerView, "Jokes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON() {
        String url = "";

        switch (title) {
            case "Dark Jokes":
                url = "https://sv443.net/jokeapi/v2/joke/Dark?amount=10";
                break;
            case "Pun Jokes":
                url = "https://sv443.net/jokeapi/v2/joke/Pun?amount=10";
                break;
            case "Programming Jokes":
                url = "https://sv443.net/jokeapi/v2/joke/Programming?amount=10";
                break;
            case "Miscellaneous Jokes":
                url = "https://sv443.net/jokeapi/v2/joke/Miscellaneous?amount=10";
                break;
            default:
                Snackbar.make(allJokesRecyclerView, "Error Occurred. Please try again Later", BaseTransientBottomBar.LENGTH_SHORT).show();
                break;
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("jokes");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String type = object.getString("type");

                                if (type.equals("single")) {
                                    String singleJoke = object.getString("joke");

                                    list.add(new JokesModelClass(singleJoke, "", ""));
                                } else {
                                    String setup = object.getString("setup");
                                    String delivery = object.getString("delivery");

                                    list.add(new JokesModelClass(null, setup, delivery));
                                }
                                Runnable progressRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.cancel();
                                        adapter = new JokesAdapter(list, AllJokesActivity.this);
                                        allJokesRecyclerView.setAdapter(adapter);
                                    }
                                };
                                Handler pdCanceller = new Handler();
                                pdCanceller.postDelayed(progressRunnable, 1000);
                                confirmation = 1;
                            }
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
        progressDialog = new ProgressDialog(AllJokesActivity.this);
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
                    Snackbar.make(layout, "Internet slow/not available", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 4000);
    }
}