package com.example.API;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface Api {
    //api login
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("use_login")
    Call<JsonObject> use_login(@Body RequestBody requestBody);

    //api exam result
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("exam_result")
    Call<JsonObject> exam_result(@Body RequestBody requestBody);


    //api all messages
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("all_messages")
    Call<JsonObject> all_messages(@Body RequestBody requestBody);


    //api change password
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("change_password")
    Call<JsonObject> change_password(@Body RequestBody requestBody);



    //api read quiz
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("read_quiz")
    Call<JsonObject> read_quiz(@Body RequestBody requestBody);




    //api save quiz result
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("save_result_quiz")
    Call<JsonObject> save_result_quiz(@Body RequestBody requestBody);




    //api save quiz result
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("blance_fee")
    Call<JsonObject> blance_fee(@Body RequestBody requestBody);

}
