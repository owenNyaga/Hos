package com.example.hos;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView applicationsListView;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        dbHelper = new DBHelper(this);
        applicationsListView = findViewById(R.id.applicationsListView);
        logoutButton = findViewById(R.id.logoutButton);

        loadApplications();

        // Select Candidate
        applicationsListView.setOnItemClickListener((parent, view, position, id) -> {
            boolean success = dbHelper.selectCandidate((int) id);
            if (success) {
                Toast.makeText(this, "Candidate Selected and Notified", Toast.LENGTH_SHORT).show();
                loadApplications();
            } else {
                Toast.makeText(this, "Failed to Select Candidate", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout
        logoutButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void loadApplications() {
        Cursor cursor = dbHelper.getAllJobs();  // Fetch all applications instead
        String[] from = {"job_id", "doctor_id", "status"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2, cursor, from, to, 0);

        applicationsListView.setAdapter(adapter);
    }
}
