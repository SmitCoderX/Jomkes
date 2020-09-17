package com.smitcoderx.jomkes;

import android.os.Bundle;

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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MiscellaneousJokesActivity extends AppCompatActivity {
    public static boolean isRefreshed;
    private RecyclerView miscJokesRecyclerView;
    private JokesAdapter miscJokesAdapter;
    private ArrayList<JokesModelClass> miscJokesList;
    private RequestQueue miscJokesQueue;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscellaneous_jokes);

        setTitle("Miscellaneous Jokes");

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        miscJokesRecyclerView = findViewById(R.id.miscJokesRecyclerView);

        miscJokesRecyclerView.setHasFixedSize(true);
        miscJokesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        miscJokesList = new ArrayList<>();

        miscJokesQueue = Volley.newRequestQueue(this);
        parseJSON();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                miscJokesList.clear();
                miscJokesAdapter.notifyDataSetChanged();
                parseJSON();
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(miscJokesRecyclerView, "Jokes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON() {
        String url = "https://sv443.net/jokeapi/v2/joke/Miscellaneous?amount=10";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("jokes");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject joke = jsonArray.getJSONObject(i);

                                String type = joke.getString("type");

                                if (type.equals("single")) {
                                    String singleJoke = joke.getString("joke");

                                    miscJokesList.add(new JokesModelClass(singleJoke, "", ""));
                                } else {
                                    String setup = joke.getString("setup");
                                    String delivery = joke.getString("delivery");

                                    miscJokesList.add(new JokesModelClass(null, setup, delivery));
                                }
                            }
                            miscJokesAdapter = new JokesAdapter(miscJokesList, MiscellaneousJokesActivity.this);
                            miscJokesRecyclerView.setAdapter(miscJokesAdapter);

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
        miscJokesQueue.add(request);
    }
}