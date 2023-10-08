package com.example.API;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String Tag = "ApiClient";
    public static RequestBody makeJSONRequestBody;
    private static MediaType MEDIA_TYPE_TEXT = MediaType.parse("multipart/from-data");
    private static Retrofit retrofit = null;
    private static Gson gson;
    private static String BASE_URL = "http://16.171.146.219:8000/";


    public static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }


    @NonNull
    public  static RequestBody makeJSONRequestBody(JsonObject jsonObject){
        String params = jsonObject.toString();
        return RequestBody.create(MEDIA_TYPE_TEXT, params);
    }

    @NonNull
    public static String JSONResponse(Object jsonObject){
        if (gson == null){
            gson = new Gson();
        }
        return gson.toJson(jsonObject);
    }
}
