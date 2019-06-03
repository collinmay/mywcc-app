package edu.whatcom.mywcc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.whatcom.mywcc.models.StudentProfile;

public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button login;
    private int countTimes;
    private Backend backend = new StaticBackend();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.LoginName);
        Password = (EditText) findViewById(R.id.LoginPassword);
        Info = (TextView) findViewById(R.id.LoginCount);
        login = (Button) findViewById(R.id.LoginButton);
        Info.setText("Incorrect Attempts: ");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify(Name.getText().toString(),Password.getText().toString());
            }
        });

    }

    private void verify(String userName, String userPassword){
        if(userName.equals("MisterPotato") && userPassword.equals("123456")){
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);

            startActivity(intent);
        }else{
            countTimes++;
            Info.setText("Incorrect Attempts: " + countTimes);

            if(countTimes == 0){
                login.setEnabled(false);
            }
        }
    }
}
