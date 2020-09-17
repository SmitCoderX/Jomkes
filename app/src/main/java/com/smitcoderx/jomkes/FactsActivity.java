package com.smitcoderx.jomkes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.smitcoderx.jomkes.FactsFragment.EXTRA_TITLE;

public class FactsActivity extends AppCompatActivity {

    private RecyclerView singleFactRecyclerView;
    private JokesAdapter adapter;
    private RequestQueue requestQueue;
    private ArrayList<JokesModelClass> list;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        Intent intent = getIntent();
        title = intent.getStringExtra(EXTRA_TITLE);
        setTitle(title);

        singleFactRecyclerView = findViewById(R.id.singleFactRecyclerView);
        singleFactRecyclerView.setHasFixedSize(true);
        singleFactRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    public void parseJSON() {
        String url = "";

        switch (title) {
            case "Animal Facts":
                url = "https://gofugly.in/api/content/49";
                break;
            case "Celebrity Facts":
                url = "https://gofugly.in/api/content/51";
                break;
            case "Cricket Facts":
                url = "https://gofugly.in/api/content/54";
                break;
            case "History Facts":
                url = "https://gofugly.in/api/content/50";
                break;
            case "Human Body Facts":
                url = "https://gofugly.in/api/content/47";
                break;
            case "Marijuana Facts":
                url = "https://gofugly.in/api/content/53";
                break;
            case "Mobile Facts":
                url = "https://gofugly.in/api/content/46";
                break;
            case "Movie/Shows Facts":
                url = "https://gofugly.in/api/content/52";
                break;
            case "Nature Facts":
                url = "https://gofugly.in/api/content/48";
                break;
            case "Random Facts":
                url = "https://gofugly.in/api/content/36";
                break;
            default:
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
                break;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("result");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                String fact = object.getString("joke");

                                list.add(new JokesModelClass(fact, "", ""));

                            }
                            adapter = new JokesAdapter(list, FactsActivity.this);
                            singleFactRecyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}