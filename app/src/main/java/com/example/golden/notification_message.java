package com.example.golden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class notification_message extends AppCompatActivity {

    ImageView back_arrow,picture;
    TextView title, message;
    String Token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_message);
        back_arrow = findViewById(R.id.back_arrow);
        picture = findViewById(R.id.picture);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);


        //back arrow
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notification_message.this,dashboard.class);
                startActivity(intent);
            }
        });


        SharedPreferences prefs = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
         Token = prefs.getString("Token","");

        Intent intent = getIntent();
        String Title = intent.getStringExtra("title");
        title.setText(Title);
        String Message = intent.getStringExtra("message");
        message.setText(Message);
    }
}