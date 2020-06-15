package com.kevappsgaming.coursemanagementsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kevappsgaming.coursemanagementsqlite.utils.CourseInformation;

import java.util.ArrayList;

public class CourseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "course.db";

    private static final String COURSEINFO = "courseinfo";

    // Attributes of the courseinfo table
    private static final String COURSENUMBER = "number";
    private static final String COURSENAME = "name";
    private static final String COURSECREDITS = "credits";


    public CourseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS courseinfo" + " (" + COURSENUMBER + " TEXT, " + COURSENAME + " TEXT, " + COURSECREDITS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COURSEINFO);
        onCreate(db);
    }

    public void emptyCourseInfoTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ COURSEINFO);
        db.close();
    }

    // Create
    public void saveCourseInfo(String course_number, String course_name, String course_credits){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSENUMBER,course_number);
        values.put(COURSENAME,course_name);
        values.put(COURSECREDITS,course_credits);
        db.insert(COURSEINFO, null, values);
    }

    // Update
    public void updateCourseInfo(String course_number, String course_name, String course_credits){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = new String[]{course_number};
        values.put(COURSENUMBER,course_number);
        values.put(COURSENAME,course_name);
        values.put(COURSECREDITS,course_credits);
        db.update(COURSEINFO, values, "number=?", args);
    }

    // Retrieve
    public ArrayList<CourseInformation> fetchCourseInfo() {
        ArrayList<CourseInformation> myArray= new ArrayList<CourseInformation>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ COURSEINFO, null);
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            String number = cur.getString(cur.getColumnIndex(COURSENUMBER));
            String name = cur.getString(cur.getColumnIndex(COURSENAME));
            String credits = cur.getString(cur.getColumnIndex(COURSECREDITS));
            myArray.add(new CourseInformation(number, name, credits));
            cur.moveToNext();
        }
        cur.close();
        return myArray;
    }

    // Check if course already exists
    public boolean searchCourse(String course_number){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COURSENUMBER + " = ?";
        Cursor cur = db.query(COURSEINFO, new String[]{COURSENAME}, whereClause, new String[]{course_number}, null, null, null);
        cur.moveToFirst();
        if(cur.getCount() > 0){
            return true;
        }
        return false;
    }

    public ArrayList<CourseInformation> queryCourseInfo(String course_number){
        ArrayList<CourseInformation> myArray= new ArrayList<CourseInformation>();
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COURSENUMBER + " = ?";
        Cursor cur = db.query(COURSEINFO, new String[]{COURSENAME}, whereClause, new String[]{course_number}, null, null, null);
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            String number = cur.getString(cur.getColumnIndex(COURSENUMBER));
            String name = cur.getString(cur.getColumnIndex(COURSENAME));
            String credits = cur.getString(cur.getColumnIndex(COURSECREDITS));
            myArray.add(new CourseInformation(number, name, credits));
            cur.moveToNext();
        }
        cur.close();
        return myArray;
    }

    public void deleteCourse(String courseNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSEINFO, COURSENUMBER + " = ?" , new String[]{courseNumber});
    }
}
