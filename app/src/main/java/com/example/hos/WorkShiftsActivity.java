package com.example.hos;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class WorkShiftsActivity extends AppCompatActivity {
    DBHelper dbHelper;
    int doctorId;
    ListView shiftsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_shifts);

        dbHelper = new DBHelper(this);
        doctorId = getIntent().getIntExtra("doctor_id", -1);
        shiftsListView = findViewById(R.id.workShiftsListView);

        loadWorkShifts();
    }

    private void loadWorkShifts() {
        Cursor cursor = dbHelper.getDoctorShifts(doctorId);
        String[] from = {"shift_details"};
        int[] to = {android.R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_1, cursor, from, to, 0);

        shiftsListView.setAdapter(adapter);
    }
}
