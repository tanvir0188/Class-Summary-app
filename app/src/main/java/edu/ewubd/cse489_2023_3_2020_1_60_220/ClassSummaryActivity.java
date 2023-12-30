package edu.ewubd.cse489_2023_3_2020_1_60_220;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.ewubd.cse489_2023_3_2020_1_60_220.LectureSummaryDB;

public class ClassSummaryActivity extends AppCompatActivity {
    private TextView Name;
    private TextView Id;
    private EditText etDate, etLecture, etTopic, etSummary;
    private Button Save, Cancel;
    private LectureSummaryDB db;

    private RadioGroup rBtnGrp1, rBtnGrp2;
    private List<LectureSummary> classes = new ArrayList<>();
    private ArrayAdapter<LectureSummary> listClasses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_summary);


        Name = findViewById(R.id.tvName);
        Id = findViewById(R.id.tvId);

        Save = findViewById(R.id.btnSave);
        Cancel = findViewById(R.id.btnCancel);

        rBtnGrp1 = findViewById(R.id.radioGp1);
        rBtnGrp2 = findViewById(R.id.radioGp2);

        etDate = findViewById(R.id.etDate);
        etLecture = findViewById(R.id.etLecture);
        etTopic = findViewById(R.id.etTopic);
        etSummary = findViewById(R.id.etSummary);
        listClasses = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classes);



        db = new LectureSummaryDB(this);

        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String id = i.getStringExtra("User_Id");
        String date = i.getStringExtra("Date");
        String topic = i.getStringExtra("Topic");
        int lecture = i.getIntExtra("Lecture", 0);
        String summary = i.getStringExtra("Description");



        etDate.setText(date);
        etTopic.setText(topic);
        etLecture.setText(String.valueOf(lecture));
        etSummary.setText(summary);
        Name.setText(name);
        Id.setText(id);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEvent();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(ClassSummaryActivity.this, ClassLecturesActivity.class);
               startActivity(i);
               finish();
            }
        });
        loadRemoteData();

    }

    private void httpRequest(final String keys[],final String values[]){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                for (int i=0; i<keys.length; i++){
                    params.add(new BasicNameValuePair(keys[i],values[i]));
                }
                String url= "https://www.muthosoft.com/univ/cse489/index.php";
                String data="";
                try {
                    data=JSONParser.getInstance().makeHttpRequest(url,"POST",params);
                    System.out.println(data);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){
                if(data!=null){
                    System.out.println(data);
                    System.out.println("Ok2");
                    updateClassSummaryListByServerData(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void updateClassSummaryListByServerData(String data) {
        System.out.println("found");
        try {
            JSONObject jo = new JSONObject(data);
            if (jo.has("classes")) {
                classes.clear();
                JSONArray ja = jo.getJSONArray("classes");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject summaryObject = ja.getJSONObject(i);
                    String id = summaryObject.getString("id");
                    String name = summaryObject.getString("name");
                    int course = summaryObject.getInt("course");
                    String topic = summaryObject.getString("topic");
                    int type = summaryObject.getInt("type");
                    String date = summaryObject.getString("date");
                    int lecture = summaryObject.getInt("lecture");
                    String summaryText = summaryObject.getString("summary");

                    LectureSummary e = new LectureSummary(id,name, course, lecture, date, type, summaryText,topic);
                    classes.add(e);
                }
                listClasses.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveEvent() {
        String name = Name.getText().toString();
        String id = Id.getText().toString();
        String date = etDate.getText().toString();
        int lecture = Integer.parseInt(etLecture.getText().toString());
        String topic = etTopic.getText().toString();
        String summary = etSummary.getText().toString();

        int selectedCourse = rBtnGrp1.getCheckedRadioButtonId();
        int selectedType = rBtnGrp2.getCheckedRadioButtonId();

        if (validateInput())
        {
            int course = getCourse(selectedCourse);
            int type = getType(selectedType);



            if (db.eventExists(id, topic, date))
            {
                db.updateEvent(id, name, topic, course, lecture, date, type, summary);
                Toast.makeText(ClassSummaryActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                db.insertEvent(id, name, course, date, type, topic, lecture, summary);
                Toast.makeText(ClassSummaryActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            }


            Intent i = new Intent(ClassSummaryActivity.this, ClassLecturesActivity.class);
            startActivity(i);
            finish();
            String keys[] = {"action", "sid", "semester", "id", "name", "course", "type", "topic", "date", "lecture", "summary"};
            String values[] = {"backup", "2020-1-60-220", "2023-3", id, name, String.valueOf(course), String.valueOf(type), topic, date, String.valueOf(lecture), summary};
            httpRequest(keys, values);

        }
        else
        {
            Toast.makeText(ClassSummaryActivity.this, "invalid database insertion", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadRemoteData() {
        String keys[] = {"action", "sid", "semester"};
        String values[] = {"load_data", "2020-1-60-220", "2023-3"};
        httpRequest(keys, values);
    }


    private boolean validateInput() {
        String date = etDate.getText().toString();
        String lecture = etLecture.getText().toString();
        String topic = etTopic.getText().toString();
        String summary = etSummary.getText().toString();

        if (date.isEmpty() || lecture.isEmpty() || topic.isEmpty() || summary.isEmpty() || rBtnGrp1.getCheckedRadioButtonId() == -1 || rBtnGrp2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(ClassSummaryActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!dateValidator(date)) {
            Toast.makeText(ClassSummaryActivity.this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean dateValidator(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        sdf.setLenient(false);

        try {
            sdf.parse(date);
        } catch (ParseException e) {
            Toast.makeText(ClassSummaryActivity.this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private int getCourse(int radioButtonId) {
        if (radioButtonId == R.id.radioBtn1) {
            return 477;
        } else if (radioButtonId == R.id.radioBtn2) {
            return 479;
        } else if (radioButtonId == R.id.radioBtn3) {
            return 489;
        } else if (radioButtonId == R.id.radioBtn4) {
            return 495;
        }
        return -1;
    }


    private int getType(int radioButtonId) {
        if (radioButtonId == R.id.radioBtn5) {
            return 1;
        } else if (radioButtonId == R.id.radioBtn6) {
            return 2;
        }
        return -1;
    }





}
