package edu.ewubd.cse489_2023_3_2020_1_60_220;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ClassLecturesActivity extends AppCompatActivity {

    Button btnBack, btnAddNew;
    TextView name, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_lectures);

        btnBack = findViewById(R.id.btnBack);
        btnAddNew = findViewById(R.id.btnAddNew);
        name = findViewById(R.id.txtName);
        id = findViewById(R.id.txtUId);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myPrefsLogin", MODE_PRIVATE);
        String userId = prefs.getString("User_Id", "");
        String userName = prefs.getString("Name", "");

        name.setText(userName);
        id.setText(userId);


        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassLecturesActivity.this, ClassSummaryActivity.class);
                startActivity(intent);
            }
        });
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClassLecturesActivity.this, ClassSummaryActivity.class);
                i.putExtra("User_Id", userId);
                i.putExtra("Name", userName);
                startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassLecturesActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
