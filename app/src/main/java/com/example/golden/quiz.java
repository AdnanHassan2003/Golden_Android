package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quiz extends AppCompatActivity {

    ImageView back_arrow;

    //waa like 1/10 2/10----10/10
    TextView NumQuation, TotalQuation, Slash;
    private int numquation, totalquation;


    LinearLayout Li1, Li2;
    RelativeLayout Ri1;
    TextView Quiz_not_ready;


    //waa quation 1 radio group 3 radio button 1button
    private  TextView textquestion;
    private RadioGroup rbgGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonconfirmNext;


    private int correct,wrong;
    private int marks;
    private int Totalmarks;
    private boolean answered;


    JsonObject currentQuestion;
    private  int questioncounter;
    private int questioncountTotal;


    JsonArray quiz_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textquestion = findViewById(R.id.tv_question);
        rbgGroup =findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_botton1);
        rb2 = findViewById(R.id.radio_botton2);
        rb3 = findViewById(R.id.radio_botton3);
        buttonconfirmNext = findViewById(R.id.btn_confirm);

        //waa like sidaan 1/10
        NumQuation = findViewById(R.id.NumQuation);
        TotalQuation = findViewById(R.id.TotalQuation);
        Slash = findViewById(R.id.slash);


        Li1 = findViewById(R.id.Li1);
        Li2 = findViewById(R.id.Li2);
        Ri1 = findViewById(R.id.Ri1);
        Quiz_not_ready  =findViewById(R.id.Quiz_not_rady);

        //back arrow
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(quiz.this,dashboard.class);
                startActivity(intent);
            }
        });

       //api function active
        read_quiz();


        currentQuestion=new JsonObject();
        buttonconfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    if(rb1.isChecked()||rb2.isChecked()|| rb3.isChecked()){
                        checkanswer();
                    }else {
                        Toast.makeText(quiz.this, "please select answer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    shownextquestion();
                }
            }
        });



    }
    private  void checkanswer(){
        answered=true;

        RadioButton rbselected = findViewById(rbgGroup.getCheckedRadioButtonId());
        int answerNr = rbgGroup.indexOfChild(rbselected) +1;
        if(answerNr == currentQuestion.get("correct").getAsInt()){
            correct++;



            //some logic
            int totalmarks = currentQuestion.get("marks").getAsInt();
            Totalmarks = marks+totalmarks;
            marks = Totalmarks;
            Log.d("ffffffff", String.valueOf(marks));



//            textscore.setText("score: " +score);
        }else {
            wrong++;
        }
        if (questioncounter<questioncountTotal){
            buttonconfirmNext.setText("Next");
        }else {
                buttonconfirmNext.setText("finish");
        }

    }
    private  void read_quiz(){

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        String _id = prefs.getString("_id","");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("student_id",_id);


        Api apiInterface = ApiClient.getClient().create(Api.class);
        Call<JsonObject> call = apiInterface.read_quiz(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("response", String.valueOf(response.body()));


                if (response.isSuccessful()) {

                    if (response.body().get("success").getAsBoolean()) {

                        JsonObject jsonObject1 = response.body().getAsJsonArray("record").get(0).getAsJsonObject();
                        quiz_array = response.body().getAsJsonArray("record");
                        questioncountTotal = quiz_array.size();
                        Collections.shuffle(Collections.singletonList(quiz_array));
                        shownextquestion();

                        Li1.setVisibility(View.VISIBLE);
                        Li2.setVisibility(View.VISIBLE);
                        Ri1.setVisibility(View.VISIBLE);
                        Quiz_not_ready.setVisibility(View.GONE);






                        SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",MODE_PRIVATE).edit();

                        String quiz_id = jsonObject1.get("quiz_id").getAsString();
                        String subject_id = jsonObject1.get("subject_id").getAsString();
                        String class_id = jsonObject1.get("class_id").getAsString();

                        editor.putString("Quiz_id",quiz_id);
                        editor.putString("Subject_id",subject_id);
                        editor.putString("Class_id",class_id);

                        editor.apply();




                    } else {

                        Log.d("Onfailuren", "/25");
//                        String message = response.body().get("record").getAsString();
//                        Toast.makeText(quiz.this, message, Toast.LENGTH_SHORT).show();

                        Li1.setVisibility(View.GONE);
                        Li2.setVisibility(View.GONE);
                        Ri1.setVisibility(View.GONE);
                        Quiz_not_ready.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("Onfailuren", "22");


                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(quiz.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

                Log.d("ssssssssssssssss", t.getMessage());

            }
        });
    }

    private  void shownextquestion(){

        rbgGroup.clearCheck();
        if (questioncounter <questioncountTotal){
          currentQuestion = quiz_array.get(questioncounter).getAsJsonObject();
          Log.d("currentQuestion", String.valueOf(currentQuestion));
            textquestion.setText(currentQuestion.get("quetion").getAsString());
            rb1.setText(currentQuestion.get("answer1").getAsString());
            rb2.setText(currentQuestion.get("answer2").getAsString());
            rb3.setText(currentQuestion.get("answer3").getAsString());
            questioncounter++;
//=========================================================
            //total su,alaha
            totalquation = quiz_array.size();
            String change = Integer.toString(totalquation);
            TotalQuation.setText(change);

            Slash.setVisibility(View.VISIBLE);

            //su,asha lajoogo
            numquation = questioncounter;
            String changee  = Integer.toString(numquation);
            NumQuation.setText(changee);
//=========================================================
          answered = false;
          buttonconfirmNext.setText("confirm");


        }
        else {
           finishquiz();
        }

    }
    private void finishquiz(){
        String CORRECT = Integer.toString(correct);
        String WRONG = Integer.toString(wrong);
        String MARKS = Integer.toString(marks);

        Intent intent = new Intent(quiz.this, score.class);
        intent.putExtra("Correct",CORRECT);
        intent.putExtra("Wrong",WRONG);
        intent.putExtra("Marks",MARKS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}