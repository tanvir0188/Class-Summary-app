package edu.ewubd.cse489_2023_3_2020_1_60_220;

import android.content.Intent;

public class LectureSummary {
    String id ;
    String name;
    String course;
    String datetime;
    int type;
    int lecture;

    String description;
    String topic;


    public LectureSummary(String id, String name, int course, int lecture, String datetime, int type, String description, String topic) {
        this.id = id;
        this.name = name;
        this.course = Integer.toString(course);
        this.datetime = datetime;;
        this.lecture = lecture;
        this.type = type;
        this.description = description;
        this.topic = topic;


    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getType() {
        return type;
    }

    public int getLecture() {
        return lecture;
    }

    public String getDescription() {
        return description;
    }

    public String getTopic() {
        return topic;
    }
}
