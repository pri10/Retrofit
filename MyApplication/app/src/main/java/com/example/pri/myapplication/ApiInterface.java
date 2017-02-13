package com.example.pri.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pri on 2/13/2017.
 */

public interface ApiInterface {

    @GET("android/jsonandroid")
    Call<JsonResponse.JSONResponse> getJSON();
}
