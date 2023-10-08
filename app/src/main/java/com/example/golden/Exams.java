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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Exams extends AppCompatActivity {
    TextView class_nameExam;
    TextView totalMarks,Average;
    ImageView back_arrow, dark_modeup;
    RecyclerView recyclerView;
    LinearLayout LinearLayoutTotal,LinearLayoutAverage;
    JsonArray SubAndMar;
    JsonObject totalmarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        LinearLayoutTotal=findViewById(R.id.LinearLayoutTotal);
        LinearLayoutAverage = findViewById(R.id.LinearLayoutAverage);
        totalMarks = findViewById(R.id.totalMarks);
        Average = findViewById(R.id.Average);
        //back arrow
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exams.this, dashboard.class);
                startActivity(intent);
            }
        });
        exam_result();


    }


    //from api into recycleview

    private void exam_result(){

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String _Id = prefs.getString("_id", "");
        Log.d("eeeeeeeeee",_Id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("student_id", _Id);
        Log.d("bbbbbbbbb", String.valueOf(jsonObject));


        Api apiInterface = ApiClient.getClient().create(Api.class);
        Call<JsonObject> call = apiInterface.exam_result(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("response_MARKS", String.valueOf(response.body()));


                if (response.isSuccessful()) {

                    if (response.body().get("success").getAsBoolean()) {

                        SubAndMar=response.body().getAsJsonArray("record");
                        totalmarks= response.body().getAsJsonArray("totalmarks").get(0).getAsJsonObject();

                        //get data from api into total marks
                        String TOTmark = totalmarks.get("totalmarks").getAsString();
                        totalMarks.setText(TOTmark);

                        //get data from api into AVG
                        float TMARKS = totalmarks.get("totalmarks").getAsFloat();
                        Float TSUBJECT = totalmarks.get("total_sub").getAsFloat();
                        float Avg = ((float)TMARKS/TSUBJECT);
                        String AVG = String.format("%.2f",Avg)+"%";
                        Average.setText(AVG);







                        recyclerView_data();


                    } else {

                        Log.d("Onfailuren", "/25");
                        JsonObject message = response.body();
                        //Toast.makeText(Exams.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d("Onfailuren", "22");
                }

            }

            private void recyclerView_data() {
                //recycle view
                recyclerView = findViewById(R.id.recycle_view);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new RecycleAdapter(getBaseContext(), SubAndMar));
                LinearLayoutTotal.setVisibility(View.VISIBLE);
                LinearLayoutAverage.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Exams.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

                Log.d("ssssssssssssssss", t.getMessage());
            }

        });

    }


}



















