package edu.ewubd.cse489_2023_3_2020_1_60_220;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class ClassSummaryAdapter extends ArrayAdapter<LectureSummary> {

    private final Context context;
    private final ArrayList<LectureSummary> values;

    public ClassSummaryAdapter(@NonNull Context context, @NonNull ArrayList<LectureSummary> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.class_summary_list_layout, parent, false);

        TextView courseCode = rowView.findViewById(R.id.tvCourse);
        TextView dateTime = rowView.findViewById(R.id.tvDate);
        TextView topic = rowView.findViewById(R.id.tvTopic);
        //TextView eventType = rowView.findViewById(R.id.tvEventType);

        LectureSummary e = values.get(position);
        courseCode.setText("Course: Cse" + String.valueOf(e.course));
        dateTime.setText("Date: " + e.datetime);
        topic.setText(e.topic);
        //eventType.setText(e.eventType);
        return rowView;
    }
}
