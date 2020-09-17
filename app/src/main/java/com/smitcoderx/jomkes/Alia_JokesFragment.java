package com.smitcoderx.jomkes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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


public class Alia_JokesFragment extends Fragment {

    private RecyclerView recyclerView;
    private JokesAdapter jokesAdapter;
    private ArrayList<JokesModelClass> list;
    private RequestQueue requestQueue;
    private int pos;

    public Alia_JokesFragment(int position) {
        this.pos = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_aliajokes, null);
        recyclerView = root.findViewById(R.id.hindiRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        list = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this.getContext());
        parseJSON();

        return root;
    }

    public void parseJSON() {
        String url = "";

        if (pos == 0) {
            url = "https://gofugly.in/api/content/3";
        } else if (pos == 1) {
            url = "https://gofugly.in/api/content/16";
        } else if (pos == 2) {
            url = "https://gofugly.in/api/content/6";
        } else if (pos == 3) {
            url = "https://gofugly.in/api/content/20";
        } else if (pos == 4) {
            url = "https://gofugly.in/api/content/5";
        } else if (pos == 5) {
            url = "https://gofugly.in/api/content/13";
        } else if (pos == 6) {
            url = "https://gofugly.in/api/content/8";
        } else if (pos == 7) {
            url = "https://gofugly.in/api/content/11";
        } else if (pos == 8) {
            url = "https://gofugly.in/api/content/14";
        } else if (pos == 9) {
            url = "https://gofugly.in/api/content/68";
        } else if (pos == 10) {
            url = "https://gofugly.in/api/content/17";
        } else if (pos == 11) {
            url = "https://gofugly.in/api/content/33";
        } else if (pos == 12) {
            url = "https://gofugly.in/api/content/31";
        } else if (pos == 13) {
            url = "https://gofugly.in/api/content/19";
        } else if (pos == 14) {
            url = "https://gofugly.in/api/content/57";
        } else if (pos == 15) {
            url = "https://gofugly.in/api/content/4";
        } else if (pos == 16) {
            url = "https://gofugly.in/api/content/10";
        } else if (pos == 17) {
            url = "https://gofugly.in/api/content/67";
        } else if (pos == 18) {
            url = "https://gofugly.in/api/content/18";
        } else if (pos == 19) {
            url = "https://gofugly.in/api/content/22";
        } else if (pos == 20) {
            url = "https://gofugly.in/api/content/56";
        } else if (pos == 21) {
            url = "https://gofugly.in/api/content/9";
        } else if (pos == 22) {
            url = "https://gofugly.in/api/content/66";
        } else {
            url = "https://gofugly.in/api/content/21";
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                String jokes = result.getString("joke");

                                list.add(new JokesModelClass(jokes, "", ""));
                            }

                            jokesAdapter = new JokesAdapter(list, getContext());
                            recyclerView.setAdapter(jokesAdapter);
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
}