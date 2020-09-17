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

public class PunJokesActivity extends AppCompatActivity {
    public static boolean isRefreshed;
    private RecyclerView punJokesRecyclerView;
    private JokesAdapter punJokesAdapter;
    private ArrayList<JokesModelClass> punJokesList;
    private RequestQueue punJokesQueue;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pun_jokes);

        setTitle("Pun Jokes");

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        punJokesRecyclerView = findViewById(R.id.punJokesRecyclerView);

        punJokesRecyclerView.setHasFixedSize(true);
        punJokesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        punJokesList = new ArrayList<>();

        punJokesQueue = Volley.newRequestQueue(this);
        parseJSON();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                punJokesList.clear();
                punJokesAdapter.notifyDataSetChanged();
                parseJSON();
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(punJokesRecyclerView, "Jokes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON() {
        String url = "https://sv443.net/jokeapi/v2/joke/Pun?amount=10";
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

                                    punJokesList.add(new JokesModelClass(singleJoke, "", ""));
                                } else {
                                    String setup = joke.getString("setup");
                                    String delivery = joke.getString("delivery");

                                    punJokesList.add(new JokesModelClass(null, setup, delivery));
                                }
                            }
                            punJokesAdapter = new JokesAdapter(punJokesList, PunJokesActivity.this);
                            punJokesRecyclerView.setAdapter(punJokesAdapter);

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
        punJokesQueue.add(request);
    }
}