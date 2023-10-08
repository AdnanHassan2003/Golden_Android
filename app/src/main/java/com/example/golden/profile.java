package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.nio.charset.StandardCharsets;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    ImageView back_arrow,dark_modeup;
    ImageView profile_image;
    LinearLayout edit;
    TextView name,phone,email,user;
    androidx.cardview.widget.CardView logout,change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name  = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        user = findViewById(R.id.user);
        profile_image = findViewById(R.id.profile_image);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String Name =  prefs.getString("name","");
        String Phone =  prefs.getString("phone","");
        String Email =  prefs.getString("email","");
        String User_name =  prefs.getString("user_name","");
        String Image = prefs.getString("picture", "");


        name.setText(Name);
        phone.setText(Phone);
        email.setText(Email);
        user.setText(User_name);
        //profile_image
        Glide.with(profile.this)
                .asBitmap()
                .load("http://16.171.146.219:8000/"+Image)
                .centerCrop()
                .into(profile_image);





        //back arrow
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this,dashboard.class);
                startActivity(intent);
            }
        });



        //change password
        change_password = findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, com.example.golden.change_password.class);
                startActivity(intent);
            }
        });


        //logout
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(profile.this,MainActivity.class);

                startActivity(intent);
            }
        });



    }
}