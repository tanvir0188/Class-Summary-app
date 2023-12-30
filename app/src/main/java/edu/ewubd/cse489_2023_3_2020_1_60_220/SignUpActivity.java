package edu.ewubd.cse489_2023_3_2020_1_60_220;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {


    Button btnGo, btnBack;
    EditText name, email, userId, password, rePassword;
    CheckBox remUserId, remPassword;
    TextView login;

    public boolean validator(EditText value) {
        String valueToText = value.getText().toString();

        if (valueToText.isEmpty() || (valueToText.length() < 4 || valueToText.length() > 20)) {
            return false;
        }

        return true;
    }

    public boolean emailValidator(EditText email) {
        String emailText = email.getText().toString().trim();

        if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return false;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        userId = findViewById(R.id.etUserId);
        password = findViewById(R.id.etPassword);
        rePassword = findViewById(R.id.etrePassword);
        remUserId = findViewById(R.id.signUpRemUID);
        remPassword = findViewById(R.id.signUpRemPass);
        btnGo = findViewById(R.id.btnGo);
        btnBack= findViewById(R.id.btnBack);
        login = findViewById(R.id.loginLink);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
// create a condition so that the login page loads first if there are data saved in shared preference

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nameValid = validator(name);
                boolean emailValid = emailValidator(email);
                boolean userIdValid = validator(userId);
                boolean passwordValid = validator(password);
                boolean rePasswordValid = password.getText().toString().equals(rePassword.getText().toString());


                if(nameValid && emailValid && userIdValid && passwordValid && rePasswordValid)
                {
                    SharedPreferences localPref = SignUpActivity.this.getSharedPreferences("myPrefsLogin", MODE_PRIVATE);
                    SharedPreferences.Editor edit = localPref.edit();
                    edit.putString("Name", name.getText().toString());
                    edit.putString("Email", email.getText().toString());
                    edit.putString("User_Id", userId.getText().toString());
                    edit.putString("Password", password.getText().toString());
                    edit.putBoolean("Remem_Uid",remUserId.isChecked());
                    edit.putBoolean("Remem_Pass",remPassword.isChecked());
                    edit.apply();

                    Intent intent = new Intent(SignUpActivity.this, ClassLecturesActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(SignUpActivity.this, "Invalid input. Please check the fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
