package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.API.Api;
import com.example.API.ApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class change_password extends AppCompatActivity {
    ImageView back_arrow;
    TextInputEditText current_password,new_password;
    AppCompatButton update_password;

    //global variable
    String Npassword;
    String Cpassword;
    JsonArray CHANEPASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        current_password = findViewById(R.id.Current_Password);
        new_password = findViewById(R.id.New_Password);
        update_password = findViewById(R.id.Update_Password);

        //waxaa new passwordka u pass grenayaa jsonobject
         Npassword = new_password.getText().toString();
         Cpassword = current_password.getText().toString();



        // waxuu wadaa password ka  lagu check garenayo Cpassword
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String Password = prefs.getString("Password","");
        Log.d("lllllllllllll",Password);


        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Cpassword = current_password.getText().toString();
                String Npassword = new_password.getText().toString();

                if (Cpassword.isEmpty()){
                    Toast.makeText(change_password.this, "Enter Current Password", Toast.LENGTH_SHORT).show();
                }
                else if(Npassword.isEmpty()){
                    Toast.makeText(change_password.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                }

                else if (!Cpassword.equals(Password)){
                    Toast.makeText(change_password.this, "Check Your Current Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(change_password.this, "Success To Change Password", Toast.LENGTH_SHORT).show();
                    chanege_password();
                }
            }
        });

        //back arrow
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(change_password.this,profile.class);
                startActivity(intent);
            }
        });

    }



    private  void chanege_password(){

        Npassword = new_password.getText().toString();
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
        String _Id = prefs.getString("_id","");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("_id",_Id);
        jsonObject.addProperty("PassWord",Npassword);


        Api apiInterface = ApiClient.getClient().create(Api.class);
        Call<JsonObject> call = apiInterface.change_password(ApiClient.makeJSONRequestBody(jsonObject));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                 Log.d("response", String.valueOf(response.body()));

                if (response.isSuccessful()){

                    if (response.body().get("success").getAsBoolean()){

                       // CHANEPASSWORD = response.body().getAsJsonArray("record");



                       Intent intent = new Intent(change_password.this,profile.class);
                        Npassword =  " ";
                        Cpassword = " ";
                       startActivity(intent);
                    }
                    else {

                        Log.d("Onfailuren", "/25");
                        String message = response.body().get("record").getAsString();
                        Toast.makeText(change_password.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Log.d("Onfailuren", "22");

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(change_password.this, "Check You Connection Network", Toast.LENGTH_SHORT).show();

                Log.d("ssssssssssssssss", t.getMessage());

            }
        });
    }
}