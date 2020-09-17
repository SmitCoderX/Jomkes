package com.smitcoderx.jomkes;

import android.os.Bundle;
import android.widget.Toast;

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

public class DarkJokesActivity extends AppCompatActivity {
    private RecyclerView darkJokesRecyclerView;
    private JokesAdapter darkJokesAdapter;
    private ArrayList<JokesModelClass> darkJokesList;
    private RequestQueue darkJokesQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isRefreshed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark_jokes);

        setTitle("Dark Jokes");

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        darkJokesRecyclerView = findViewById(R.id.darkJokesRecyclerView);

        darkJokesRecyclerView.setHasFixedSize(true);
        darkJokesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        darkJokesList = new ArrayList<>();

        darkJokesQueue = Volley.newRequestQueue(this);
        parseJSON();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                darkJokesList.clear();
                darkJokesAdapter.notifyDataSetChanged();
                parseJSON();
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(darkJokesRecyclerView, "Jokes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON() {
        String url = "https://sv443.net/jokeapi/v2/joke/Dark?amount=10";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("jokes");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject joke = jsonArray.getJSONObject(i);

                                String type = joke.getString("type");

                                if (type.equals("single"))
                                {
                                    String singleJoke = joke.getString("joke");

                                    darkJokesList.add(new JokesModelClass(singleJoke, "", ""));
                                }
                                else {
                                    String setup = joke.getString("setup");
                                    String delivery = joke.getString("delivery");

                                    darkJokesList.add(new JokesModelClass(null, setup, delivery));
                                }
                            }
                            darkJokesAdapter = new JokesAdapter( darkJokesList,DarkJokesActivity.this);
                            darkJokesRecyclerView.setAdapter(darkJokesAdapter);

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
        darkJokesQueue.add(request);
    }
}