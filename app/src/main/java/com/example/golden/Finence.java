package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Finence extends AppCompatActivity {
    ImageView back_arrow,dark_modeup;
    TextView profit,balance;
    LinearLayout dark_modedown;


    int PROFIT, BALANCE, bayble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finence);

        profit = findViewById(R.id.profit);
        balance = findViewById(R.id.balance);

        

        blance_fee();

        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finence.this,dashboard.class);
                startActivity(intent);
            }
        });
    }

    private  void blance_fee(){

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        String _id = prefs.getString("_id","");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("student_id",_id);




        Api apiInterface = ApiClient.getClient().create(Api.class);
        Call<JsonObject> call = apiInterface.blance_fee(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("nnnnnnnnn", String.valueOf(response.body()));


                if (response.isSuccessful()){

                    if (response.body().get("success").getAsBoolean()){

                        JsonObject jsonObject1 = response.body().getAsJsonArray("record").get(0).getAsJsonObject();


                         PROFIT = 60;
                         BALANCE = jsonObject1.get("payment").getAsInt();
                         bayble = PROFIT - BALANCE;
                         balance.setText("$"+bayble);











                    }
                    else {

                        Log.d("Onfailuren", "/25");
                        String message = response.body().get("record").getAsString();

                        Toast.makeText(Finence.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Log.d("Onfailuren", "22");

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(Finence.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

                Log.d("ssssssssssssssss", t.getMessage());

            }
        });
    }
}