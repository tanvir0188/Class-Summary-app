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

public class LogInActivity extends AppCompatActivity {

    Button btnGo, btnBack;

    EditText UserId, Password;
    CheckBox remUserId, remPassword;
    TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        UserId = (EditText) findViewById(R.id.etUserId);
        Password = (EditText) findViewById(R.id.etPassword);

        remUserId =(CheckBox) findViewById(R.id.signUpRemUID);
        remPassword=(CheckBox) findViewById(R.id.signUpRemPass);

        btnGo = (Button) findViewById(R.id.btnGo);
        btnBack = (Button) findViewById(R.id.btnBack);

        signup = findViewById(R.id.signupLink);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean userIdValid = validator(UserId);
                boolean passwordValid = validator(Password);

                if (userIdValid && passwordValid) {
                    SharedPreferences localPref = LogInActivity.this.getSharedPreferences("myPrefsLogin", MODE_PRIVATE);
                    SharedPreferences.Editor edit = localPref.edit();
                    edit.putString("User_Id", UserId.getText().toString());
                    edit.putString("Password", Password.getText().toString());
                    edit.putBoolean("Remem_Uid",remUserId.isChecked());
                    edit.putBoolean("Remem_Pass",remPassword.isChecked());
                    edit.apply();


                    Intent intent = new Intent(LogInActivity.this, ClassLecturesActivity.class);
                    startActivity(intent);

                } else {
                    System.out.println("invalid input");
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean validator(EditText value) {
        String valueToText = value.getText().toString();

        if (valueToText.isEmpty() || valueToText.length() < 4 || valueToText.length() > 20) {
            return false;
        }

        return true;
    }
}
