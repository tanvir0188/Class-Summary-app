package edu.ewubd.cse489_2023_3_2020_1_60_220;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;


public class ClassLecturesActivity extends AppCompatActivity {

    Button btnBack, btnAddNew;
    TextView name, id;
    private LectureSummaryDB db;

    private ArrayList<LectureSummary> classes;
    private ClassSummaryAdapter adapter;
    private ListView lvClasses;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_lectures);




        db = new LectureSummaryDB(this);
        btnBack = findViewById(R.id.btnBack);
        btnAddNew = findViewById(R.id.btnAddNew);
        name = findViewById(R.id.txtName);
        id = findViewById(R.id.txtUId);
        lvClasses = findViewById(R.id.vLec);  // Add this line to initialize the ListView
        classes = new ArrayList<>();




        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myPrefsLogin", MODE_PRIVATE);
        String userId = prefs.getString("User_Id", "");
        String userName = prefs.getString("Name", "");

        name.setText("Name: "+  userName);
        id.setText("User Id: "+userId);

//        loadClassSummaries();






        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClassLecturesActivity.this, ClassSummaryActivity.class);
                i.putExtra("User_Id", userId);
                i.putExtra("Name", userName);
                startActivity(i);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassLecturesActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }

        });

    }
    public  void onStart(){
        super.onStart();
        loadClassSummaries();
        //loadData();

    }

    private void loadClassSummaries() {
        classes.clear();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myPrefsLogin", MODE_PRIVATE);
        String userId = prefs.getString("User_Id", "");
        String query = "SELECT * FROM LectureSummary WHERE ID = ?";
        String[] selectionArgs = {userId};
        LectureSummaryDB db = new LectureSummaryDB(this);
        Cursor rows = db.getReadableDatabase().rawQuery(query, selectionArgs);

        if (rows != null) {
            if (rows.getCount() > 0) {
                StringBuilder summaries = new StringBuilder();

                while (rows.moveToNext()) {
                    String id = rows.getString(0);
                    String name = rows.getString(1);
                    int course = Integer.parseInt(rows.getString(2));
                    String datetime = rows.getString(3);
                    int type = Integer.parseInt(rows.getString(4));
                    String topic = rows.getString(5);
                    int lecture = Integer.parseInt(rows.getString(6));

                    String description = rows.getString(7);


                    LectureSummary cs = new LectureSummary (id, name, course, lecture, datetime, type, description, topic);
                    classes.add(cs);


                    System.out.println(id);
                    System.out.println(name);
                    System.out.println(datetime);
                    System.out.println(course);
                    System.out.println(type);
                    System.out.println(topic);
                    System.out.println(lecture);
                    System.out.println(description);
                }

            }
            rows.close();
        }
        db.close();
        adapter = new ClassSummaryAdapter(this, classes);
        lvClasses.setAdapter(adapter);

        lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
// String item = (String) parent.getItemAtPosition(position);
                System.out.println(position);
                Intent i = new Intent(ClassLecturesActivity.this, ClassSummaryActivity.class);
                i.putExtra("User_Id", classes.get(position).id);
                i.putExtra("CourseCode", classes.get(position).course);
                i.putExtra("Name", classes.get(position).name);
                i.putExtra("Date", classes.get(position).datetime);
                i.putExtra("Type", classes.get(position).type);
                i.putExtra("Topic", classes.get(position).topic);
                i.putExtra("Lecture", classes.get(position).lecture);
                i.putExtra("Description", classes.get(position).description);
                startActivity(i);
                finish();
            }
        });

        lvClasses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });

    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.delete_dialogue, null);
        builder.setView(view);

        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        AlertDialog dialog = builder.create();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(position);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deleteEvent(int position) {
        String topic = classes.get(position).topic;
        String datetime = classes.get(position).datetime;

        LectureSummaryDB db = new LectureSummaryDB(this);
        db.deleteEvent(topic, datetime);
        classes.remove(position);
        adapter.notifyDataSetChanged();
        db.close();

        Toast.makeText(this, "Class-summary deleted successfully", Toast.LENGTH_SHORT).show();
    }



}
