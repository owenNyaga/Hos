package com.example.hos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorDashboardActivity extends AppCompatActivity {
    DBHelper dbHelper;
    int doctorId;
    Button postJobButton, viewShiftsButton, logoutButton;
    ListView jobsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        dbHelper = new DBHelper(this);
        doctorId = getIntent().getIntExtra("doctor_id", -1);

        postJobButton = findViewById(R.id.postJobButton);
        viewShiftsButton = findViewById(R.id.viewShiftsButton);
        logoutButton = findViewById(R.id.logoutButton);
        jobsListView = findViewById(R.id.jobsListView);

        loadJobs();

        // Post Job
        postJobButton.setOnClickListener(v -> {
            dbHelper.postJob("Sample Job Title", "Sample Job Description", doctorId);
            Toast.makeText(this, "Job Posted Successfully", Toast.LENGTH_SHORT).show();
            loadJobs();
        });

        // View Work Shifts
        viewShiftsButton.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDashboardActivity.this, WorkShiftsActivity.class);
            intent.putExtra("doctor_id", doctorId);
            startActivity(intent);
        });

        // Logout
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Apply for Jobs
        jobsListView.setOnItemClickListener((parent, view, position, id) -> {
            dbHelper.applyForJob((int) id, doctorId);
            Toast.makeText(this, "Applied for Job Successfully", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadJobs() {
        Cursor cursor = dbHelper.getAllJobs();
        String[] from = {"title", "description"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2, cursor, from, to, 0);

        jobsListView.setAdapter(adapter);
    }
}
