package com.example.mywccapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SecondActivity extends AppCompatActivity {

    private ImageButton StudentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        StudentProfile = (ImageButton) findViewById(R.id.studentprofile);
        StudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });
    }

    public void openProfile(){
        Intent intent1 = new Intent(this, ProfilePage.class);
        startActivity(intent1);
    }

}

