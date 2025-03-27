package com.example.app3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        Intent fromAct =getIntent();
        String title=fromAct.getStringExtra("title");
        String studentName=fromAct.getStringExtra("StudentName");
        int rollno=fromAct.getIntExtra("RollNo",23);
        TextView studentInfo;
        studentInfo=findViewById(R.id.studentInfo);
        studentInfo.setText("Roll No: "+rollno+", Name: "+studentName);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }


    }
}