package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class messages extends AppCompatActivity {
    ImageView back_arrow;
    JsonArray MESSAGE;
    RecyclerView recycle_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        //back arrow
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(messages.this,dashboard.class);
                startActivity(intent);

            }
        });

        all_messages();
    }

    private  void all_messages(){


        JsonObject jsonObject = new JsonObject();



        Api apiInterface = ApiClient.getClient().create(Api.class);
        Call<JsonObject> call = apiInterface.all_messages(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                 Log.d("responseggggg", String.valueOf(response.body()));

                if (response.isSuccessful()){

                    if (response.body().get("success").getAsBoolean()){

                        MESSAGE =response.body().getAsJsonArray("record");


                        recyclermessage_data();



                    }
                    else {

                        Log.d("Onfailuren", "/25");
                        String message = response.body().get("record").getAsString();
                        Toast.makeText(messages.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Log.d("Onfailuren", "22");

                }
            }

            private void recyclermessage_data() {
                //recycle view
                recycle_view = findViewById(R.id.recycle_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                recycle_view.setLayoutManager(linearLayoutManager);
                recycle_view.setHasFixedSize(true);
                recycle_view.setAdapter(new messageAdapter(getBaseContext(), MESSAGE));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(messages.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

                Log.d("ssssssssssssssss", t.getMessage());

            }
        });
    }
}