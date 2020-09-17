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

public class ProgrammingJokesActivity extends AppCompatActivity {
    public static boolean isRefreshed;
    private RecyclerView programJokesRecyclerView;
    private JokesAdapter programJokesAdapter;
    private ArrayList<JokesModelClass> programJokesList;
    private RequestQueue programJokesQueue;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_jokes);

        setTitle("Programming Jokes");

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        programJokesRecyclerView = findViewById(R.id.programmingJokesRecyclerView);

        programJokesRecyclerView.setHasFixedSize(true);
        programJokesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        programJokesList = new ArrayList<>();

        programJokesQueue = Volley.newRequestQueue(this);
        parseJSON();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                programJokesList.clear();
                programJokesAdapter.notifyDataSetChanged();
                parseJSON();
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(programJokesRecyclerView, "Jokes Refreshed", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSON() {
        String url = "https://sv443.net/jokeapi/v2/joke/Programming?amount=10";
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

                                    programJokesList.add(new JokesModelClass(singleJoke, "", ""));
                                } else {
                                    String setup = joke.getString("setup");
                                    String delivery = joke.getString("delivery");

                                    programJokesList.add(new JokesModelClass(null, setup, delivery));
                                }
                            }
                            programJokesAdapter = new JokesAdapter(programJokesList, ProgrammingJokesActivity.this);
                            programJokesRecyclerView.setAdapter(programJokesAdapter);

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
        programJokesQueue.add(request);
    }
}