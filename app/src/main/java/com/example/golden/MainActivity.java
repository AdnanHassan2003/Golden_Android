package com.example.golden;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText User, Password;
    ImageView btn_login;
    LinearLayout main_layout;
    ProgressBar progressBar;
    String token="";


    Animation btt,bttdua,bttiga,imgalpha;
    TextView animi1, animi2, animi7;
    ImageView animi3;
    LinearLayout animi4, animi5, animi6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



        //massage from firebase

//        FirebaseMessaging.getInstance().subscribeToTopic("notification");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String message = task.getResult();

                        // Log and toast
                        token = message;
                        Log.d("Tokbbbbbbbbben:", token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });





        //EditText
        User = findViewById(R.id.User);
        Password = findViewById(R.id.Password);
        btn_login = findViewById(R.id.btn_login);
        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progres_par);



        //import animation
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        bttdua = AnimationUtils.loadAnimation(this,R.anim.bttdua);
        bttiga = AnimationUtils.loadAnimation(this,R.anim.bttiga);
        imgalpha = AnimationUtils.loadAnimation(this,R.anim.imgalpha);
        animi1 = findViewById(R.id.animi1);
        animi2 = findViewById(R.id.animi2);
        animi3 = findViewById(R.id.animi3);
        animi4 = findViewById(R.id.animi4);
        animi5 = findViewById(R.id.animi5);
        animi6 = findViewById(R.id.animi6);
        animi7 = findViewById(R.id.animi7);

        //set animation
        animi1.startAnimation(btt);
        animi2.startAnimation(btt);
        animi3.startAnimation(btt);
        animi4.startAnimation(btt);
        animi5.startAnimation(btt);
        animi6.startAnimation(btt);
        animi7.startAnimation(btt);



        //qeebtaan iyo profile page logout kiisa shaqadeda waxey thy in hadii aad mar user iyo password kusoo gishid ilaa logout dhadid waxba laguma weydinayo
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String users = prefs.getString("email","");
        Log.d("gggggggg", prefs.getString("token",""));

        if( users != ""){
            Intent intent = new Intent(MainActivity.this,dashboard.class);
            startActivity(intent);
        }
        else {
            main_layout.setVisibility(View.VISIBLE);
        }


        //Button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,dashboard.class);

                String user = User.getText().toString();
                String password = Password.getText().toString();
                if (user.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter User", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    user_login();
                }

            }
        });

    }



private  void user_login(){
    String user = User.getText().toString();
    String password = Password.getText().toString();

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("email",user);
    jsonObject.addProperty("PassWord",password);
    jsonObject.addProperty("token",token);
    Log.d("hhhhhhhhhhhhhhhhh", String.valueOf(jsonObject));


    Api apiInterface = ApiClient.getClient().create(Api.class);
    Call<JsonObject> call = apiInterface.use_login(ApiClient.makeJSONRequestBody(jsonObject));
    call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
          // Log.d("response", String.valueOf(response.body()));

           if (response.isSuccessful()){

               if (response.body().get("success").getAsBoolean()){

                   JsonObject jsonObject1 = response.body().getAsJsonArray("record").get(0).getAsJsonObject();
                   Log.d("nnnnnnnnnnnn", String.valueOf(jsonObject1));

                   //shareprefrence
                   SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile",MODE_PRIVATE).edit();

                   String student_id = jsonObject1.get("_id").getAsString();
                   String student_name = jsonObject1.get("name").getAsString();
                   String student_phone = jsonObject1.get("phone").getAsString();
                   String student_email = jsonObject1.get("email").getAsString();
                   String student_user = jsonObject1.get("user_name").getAsString();
                   String student_image = jsonObject1.get("picture").getAsString();
                   String student_token = jsonObject1.get("token").getAsString();
                   String student_password = jsonObject1.get("PassWord").getAsString();
                   Log.d("gggggg",student_id);




                   editor.putString("_id",student_id);
                   editor.putString("name",student_name);
                   editor.putString("phone",student_phone);
                   editor.putString("email",student_email);
                   editor.putString("user_name",student_user);
                   editor.putString("picture", student_image);
                   editor.putString("Token",student_token);
                   editor.putString("Password",student_password);

                   editor.apply();


                   Intent intent = new Intent(MainActivity.this,dashboard.class);
                   progressBar.setVisibility(View.GONE);
                   User.setText(" ");
                   Password.setText(" ");
                   startActivity(intent);

               }
               else {

                   Log.d("Onfailuren", "/25");
                   String message = response.body().get("record").getAsString();
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
               }
           }

           else {
               Log.d("Onfailuren", "22");

           }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

            Log.d("ssssssssssssssss", t.getMessage());

        }
    });
}




}