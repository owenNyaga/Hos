package com.example.hos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button loginButton, registerButton;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            // Admin Login
            if (userEmail.equals("admin") && userPassword.equals("admin")) {
                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
            } else {
                // Doctor Login
                Cursor cursor = dbHelper.doctorLogin(userEmail, userPassword);
                if (cursor.moveToFirst()) {
                    Intent intent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                    intent.putExtra("doctor_id", cursor.getInt(0));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}
