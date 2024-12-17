package com.example.hos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "hospital_system.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE doctors (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT UNIQUE, password TEXT, mobile TEXT)");
        db.execSQL("CREATE TABLE jobs (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, posted_by INTEGER)");
        db.execSQL("CREATE TABLE applications (id INTEGER PRIMARY KEY AUTOINCREMENT, job_id INTEGER, doctor_id INTEGER, status TEXT)");
        db.execSQL("CREATE TABLE work_shifts (id INTEGER PRIMARY KEY AUTOINCREMENT, doctor_id INTEGER, shift_details TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS doctors");
        db.execSQL("DROP TABLE IF EXISTS jobs");
        db.execSQL("DROP TABLE IF EXISTS applications");
        db.execSQL("DROP TABLE IF EXISTS work_shifts");
        onCreate(db);
    }

    // Register Doctor
    public boolean registerDoctor(String name, String email, String password, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);
        values.put("mobile", mobile);
        long result = db.insert("doctors", null, values);
        return result != -1;
    }

    // Doctor Login
    public Cursor doctorLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM doctors WHERE email=? AND password=?", new String[]{email, password});
    }

    // Admin Login
    public boolean adminLogin(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }

    // Post Job
    public boolean postJob(String title, String description, int postedBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("posted_by", postedBy);
        long result = db.insert("jobs", null, values);
        return result != -1;
    }

    // Get Jobs
    public Cursor getAllJobs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM jobs", null);
    }

    // Apply for Job
    public boolean applyForJob(int jobId, int doctorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("job_id", jobId);
        values.put("doctor_id", doctorId);
        values.put("status", "applied");
        long result = db.insert("applications", null, values);
        return result != -1;
    }

    // Admin Select Candidate
    public boolean selectCandidate(int applicationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "selected");
        int rowsAffected = db.update("applications", values, "id=?", new String[]{String.valueOf(applicationId)});
        return rowsAffected > 0;
    }

    // Get Work Shifts
    public Cursor getDoctorShifts(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM work_shifts WHERE doctor_id=?", new String[]{String.valueOf(doctorId)});
    }
}

