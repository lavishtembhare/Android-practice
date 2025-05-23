package com.example.web_service;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText password,userName;
    Button login,resister;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        password=(EditText) findViewById(R.id.editText2);
        userName=(EditText) findViewById(R.id.editText1);
        login=(Button) findViewById(R.id.button1);
        resister=(Button) findViewById(R.id.button2);

        //progess_msz.setVisibility(View.GONE);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        resister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterUser.class);
                startActivity(intent);
            }
        });

    }
}