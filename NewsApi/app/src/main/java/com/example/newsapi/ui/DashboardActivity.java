package com.example.newsapi.ui;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapi.Adapter.NewsAdapter;
import com.example.newsapi.Model.NewsModel;
import com.example.newsapi.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {


    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    private String API_KEY = "0ae4474c766f4adfa6e8e9febbbd5ad9";
    private String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";
    private String google_news_url = "https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=0ae4474c766f4adfa6e8e9febbbd5ad9";
    NewsAdapter newsAdapter;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ArrayList<NewsModel> news = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchData(news);


    }



    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem logout_btn = menu.findItem(R.id.logout_btn);
        MenuItem search_bar = menu.findItem(R.id.search_bar);

        logout_btn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder= new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Log Out");
                builder.setMessage("Are You Sure Want to Log Out !!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        })
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());

                AlertDialog alertDialog1 = builder.create();
                alertDialog1.show();
                return false;
            }
        });
        SearchView searchView =(SearchView)search_bar.getActionView();
        return super.onCreateOptionsMenu(menu);



    }
    private void fetchData(final ArrayList<NewsModel> news) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://newsapi.org/v2/everything?apiKey=af1d86fffb1b48f1b35c1621ccca8108&q=private",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                NewsModel newsModel = new NewsModel(jsonObject1.getString("title"),
                                        jsonObject1.getString("urlToImage"),
                                        jsonObject1.getString("content"));
                                news.add(newsModel);
                            }
                            newsAdapter = new NewsAdapter(news, DashboardActivity.this);
                            recyclerView.setAdapter(newsAdapter);
                            newsAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //Toast.makeText(DashboardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onErrorResponse: "+error.getMessage() );
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}