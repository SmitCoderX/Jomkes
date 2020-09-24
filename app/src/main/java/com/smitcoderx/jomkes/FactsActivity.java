package com.smitcoderx.jomkes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import static com.smitcoderx.jomkes.FactsFragment.EXTRA_TITLE;

public class FactsActivity extends AppCompatActivity {

    public static int confirmation = 0;
    ProgressDialog progressDialog;
    private RecyclerView singleFactRecyclerView;
    private JokesAdapter adapter;
    private RequestQueue requestQueue;
    private ArrayList<JokesModelClass> list;
    private String title;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

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

        singleFactRecyclerView = findViewById(R.id.singleFactRecyclerView);
        singleFactRecyclerView.setHasFixedSize(true);
        singleFactRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        showProgressDialog();
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
                Snackbar.make(singleFactRecyclerView, "Error Occurred. Please try again Later", BaseTransientBottomBar.LENGTH_SHORT).show();
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
                            Runnable progressRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    adapter = new JokesAdapter(list, FactsActivity.this);
                                    singleFactRecyclerView.setAdapter(adapter);
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
        progressDialog = new ProgressDialog(FactsActivity.this);
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
                    Snackbar.make(singleFactRecyclerView, "Internet slow/not available", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 4000);
    }
}