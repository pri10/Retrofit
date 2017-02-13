package com.example.pri.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private ArrayList<Data> data;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.myrecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.androidbegin.com/tutorial/jsonparsetutorial.txt")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface request = retrofit.create(ApiInterface.class);
        Call<JsonResponse.JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JsonResponse.JSONResponse>() {
            @Override
            public void onResponse(Call<JsonResponse.JSONResponse> call, Response<JsonResponse.JSONResponse> response) {

                JsonResponse.JSONResponse jsonResponse = response.body();
                int statusCode = response.code();
                Data[] data = response.body().getAndroid();
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonResponse.JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}