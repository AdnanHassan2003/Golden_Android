package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class score extends AppCompatActivity {
    TextView correct, wrong;
    Button save;
    ImageView back_arrow;

    String correctt;
    String wrongg;
    String marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        correct  =findViewById(R.id.correct);
        wrong = findViewById(R.id.wrong);

        Intent intent = getIntent();
         correctt  = intent.getStringExtra("Correct");
         wrongg  = intent.getStringExtra("Wrong");


        correct.setText(correctt);
        wrong.setText(wrongg);





        //save quiz result
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(score.this, "Success To Save Data", Toast.LENGTH_SHORT).show();
                save_result_quiz();

            }
        });








        //back arrow
        back_arrow  =findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(score.this,dashboard.class);
                startActivity(intent);
            }
        });
    }


    private  void save_result_quiz(){

        Intent intent = getIntent();
        correctt  = intent.getStringExtra("Correct");
        wrongg  = intent.getStringExtra("Wrong");
        marks = intent.getStringExtra("Marks");

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        //get in main activity
        String Name = prefs.getString("name","");
        String Student_id = prefs.getString("_id","");
        //get in quiz activity
        String Quiz_id = prefs.getString("Quiz_id","");
        String Subject_id  =prefs.getString("Subject_id","");
        String Class_id = prefs.getString("Class_id","");



        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",Name);
        jsonObject.addProperty("student_id",Student_id);
        jsonObject.addProperty("quiz_id",Quiz_id);
        jsonObject.addProperty("subject_id",Subject_id);
        jsonObject.addProperty("class_id",Class_id);
        jsonObject.addProperty("correct",correctt);
        jsonObject.addProperty("wrong",wrongg);
        jsonObject.addProperty("marks",marks);



        Api apiInterface = ApiClient.getClient().create(Api.class);
        Call<JsonObject> call = apiInterface.save_result_quiz(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // Log.d("response", String.valueOf(response.body()));

                if (response.isSuccessful()){

                    if (response.body().get("success").getAsBoolean()){

                        JsonObject jsonObject1 = response.body().getAsJsonArray("record").get(0).getAsJsonObject();


                        Intent intent1 = new Intent(score.this,dashboard.class);
                        startActivity(intent1);

                    }
                    else {

                        Log.d("Onfailuren", "/25");
                        String message = response.body().get("record").getAsString();

                        Toast.makeText(score.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Log.d("Onfailuren", "22");

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(score.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

                Log.d("ssssssssssssssss", t.getMessage());

            }
        });
    }

}