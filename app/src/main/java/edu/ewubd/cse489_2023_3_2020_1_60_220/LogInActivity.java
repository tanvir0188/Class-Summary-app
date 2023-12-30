package edu.ewubd.cse489_2023_3_2020_1_60_220;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log; // Import Log class
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    Button btnGo, btnExit;

    EditText UserId, Password;
    CheckBox remUserId, remPassword;
    TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        UserId = (EditText) findViewById(R.id.etUserId);
        Password = (EditText) findViewById(R.id.etPassword);

        remUserId =(CheckBox) findViewById(R.id.RemUID);
        remPassword=(CheckBox) findViewById(R.id.RemPass);

        btnGo = (Button) findViewById(R.id.btnGo);
        btnExit = (Button) findViewById(R.id.btnExit);

        signup = findViewById(R.id.signupLink);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUserId = UserId.getText().toString();
                String enteredPassword = Password.getText().toString();

                if (validator(UserId) && validator(Password)) {
                    if (checkCredentials(enteredUserId, enteredPassword)) {
                        // Credentials are correct
                        Intent intent = new Intent(LogInActivity.this, ClassLecturesActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Credentials are incorrect, show an error message or take appropriate action
                        Toast.makeText(LogInActivity.this, "Invalid credentials. Please check the fields.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogInActivity.this, "Invalid input. Please check the fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private boolean checkCredentials(String userId, String password) {
        SharedPreferences localPref = getSharedPreferences("myPrefsLogin", MODE_PRIVATE);
        String storedUserId = localPref.getString("User_Id", "");
        String storedPassword = localPref.getString("Password", "");

        return userId.equals(storedUserId) && password.equals(storedPassword);
    }


    public boolean validator(EditText value) {
        String valueToText = value.getText().toString();

        if (valueToText.isEmpty() || valueToText.length() < 4 || valueToText.length() > 20) {
            return false;
        }

        return true;
    }
}
