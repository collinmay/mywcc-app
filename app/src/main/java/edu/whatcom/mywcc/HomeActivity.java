package edu.whatcom.mywcc;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import edu.whatcom.mywcc.models.StudentProfile;

public class HomeActivity extends AppCompatActivity {
    private Backend backend = new StaticBackend();
    private ImageButton profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profileButton = (ImageButton) findViewById(R.id.studentprofile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });

        StudentProfile profile = backend.getStudentProfile();

        ListView canvasAssignmentListView = (ListView) findViewById(R.id.canvas_assignment_list);
        canvasAssignmentListView.setAdapter(
                new ArrayAdapter<>(
                        this,
                        R.layout.layout_canvas_assignment,
                        R.id.canvas_assignment_text,
                        profile.canvasAssignments));
    }

    public void openProfile(){
        Intent intent1 = new Intent(this, StudentProfilePage.class);
        startActivity(intent1);
    }

}

