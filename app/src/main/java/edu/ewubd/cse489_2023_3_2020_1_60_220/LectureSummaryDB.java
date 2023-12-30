package edu.ewubd.cse489_2023_3_2020_1_60_220;

import  android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;





public class LectureSummaryDB extends SQLiteOpenHelper {
    public LectureSummaryDB(Context context) {
        super(context, "LectureSummaryDB.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB@OnCreate");
        String sql = "CREATE TABLE LectureSummary  ("
                + "ID TEXT,"
                + "name TEXT,"
                + "course INT,"
                + "datetime TEXT,"
                + "type INT,"
                + "topic TEXT,"
                + "lecture INT,"
                + "description TEXT,"
                + "PRIMARY KEY (topic, datetime)"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Write code to modify database schema here");
    }

    public void insertEvent(String id, String name, int course, String datetime, int type, String topic, int lecture, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cols = new ContentValues();
        cols.put("ID", id);
        cols.put("name", name);
        cols.put("lecture", lecture);
        cols.put("type", type);
        cols.put("topic", topic);
        cols.put("course", course);
        cols.put("datetime", datetime);
        cols.put("description", description);
        db.insert("LectureSummary", null ,  cols);
        db.close();
    }

    public void updateEvent(String id, String name, String topic, int course, int lecture, String datetime, int type,String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cols = new ContentValues();
        cols.put("ID", id);
        cols.put("name", name);
        cols.put("lecture", lecture);
        cols.put("type", type);
        cols.put("topic", topic);
        cols.put("course", course);
        cols.put("datetime", datetime);
        cols.put("description", description);
        String whereClause = "topic=? AND datetime=?";
        String[] whereArgs = {topic, datetime};
        db.update("LectureSummary", cols, whereClause, whereArgs);
        db.close();
    }

    public boolean eventExists(String id, String topic, String datetime) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM LectureSummary WHERE ID=? AND topic=? AND datetime=?";
            String[] selectionArgs = {id, topic, datetime};
            cursor = db.rawQuery(query, selectionArgs);

            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }


    public void deleteEvent(String topic, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "topic=? AND datetime=?";
        db.delete("LectureSummary", whereClause, new String[ ] {topic, datetime} );
        db.close();
    }
    public Cursor selectEvents(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery(query, null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }


}
