package edu.whatcom.mywcc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    private ImageButton StudentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StudentProfile = (ImageButton) findViewById(R.id.studentprofile);
        StudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });
    }

    public void openProfile(){
        Intent intent1 = new Intent(this, ProfileActivity.class);
        startActivity(intent1);
    }

}

