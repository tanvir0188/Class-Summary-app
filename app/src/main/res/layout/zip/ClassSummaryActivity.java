package edu.ewubd.cse489_2023_3_2020_1_60_220;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ClassSummaryActivity extends AppCompatActivity {

    private TextView Name, Id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_summary);
         Name = findViewById(R.id.tvName);
         Id = findViewById(R.id.tvId);

         Intent i = this.getIntent();
         String name = i.getStringExtra("Name");
         String id = i.getStringExtra("User_Id");

         Name.setText(name);
         Id.setText(id);


    }
}
