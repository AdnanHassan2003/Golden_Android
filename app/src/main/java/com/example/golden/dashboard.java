package com.example.golden;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.bumptech.glide.Glide;

public class dashboard extends AppCompatActivity {
    //Recycle view part
    private RecyclerView RecycleView;
    RecyclerView.LayoutManager layoutManager;
    RecycleViewAdapter recycleViewAdapter;


    ImageView email,dark_modeUp,home, profile_image,messagebox;
    //LinearLayout dark_modedown;
    Switch switch1;


    int [] images = {R.drawable.exam, R.drawable.finance,
            R.drawable.attendence, R.drawable.profile, R.drawable.quiz};

    String [] names = {"Exam", "Finance", "Attendance", "Profile","Quiz" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recycle view
        setContentView(R.layout.activity_dashboard);
        RecycleView = findViewById(R.id.RecycleView);
        layoutManager = new GridLayoutManager(this,2);
        RecycleView.setLayoutManager(layoutManager);
        recycleViewAdapter = new RecycleViewAdapter(images,names,dashboard.this);
        RecycleView.setAdapter(recycleViewAdapter);
        RecycleView.setHasFixedSize(true);



        //profile image
        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this,profile.class);
                startActivity(intent);
            }
        });
        SharedPreferences prefs  = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String Image = prefs.getString("picture", "");
        Glide.with(dashboard.this)
                .asBitmap()
                .load("http://16.171.146.219:8000/"+Image)
                .centerCrop()
                .into(profile_image);



        //messsage box
        messagebox = findViewById(R.id.messagebox);
        messagebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this,messages.class);
                startActivity(intent);
            }
        });




        //DARK AND MODE
        SharedPreferences.Editor editor = getSharedPreferences("Mode",Context.MODE_PRIVATE).edit();
        switch1 = findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!switch1.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("night",false);
                    editor.apply();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("night",true);
                    editor.apply();
                }
            }
        });





    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finishAffinity();
    }
}