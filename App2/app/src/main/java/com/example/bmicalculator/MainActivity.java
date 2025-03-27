package com.example.bmicalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView text;
        EditText editweight, editheightft, editheightinch;
        Button btncalculate;
        LinearLayout main;
        editweight = findViewById(R.id.editweight);
        editheightft = findViewById(R.id.editheightft);
        editheightinch = findViewById(R.id.editheightinch);
        btncalculate = findViewById(R.id.btncalculate);
        main=findViewById(R.id.main);
        text = findViewById(R.id.text);
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wt=Integer.parseInt(editweight.getText().toString());
                int ft=Integer.parseInt(editheightft.getText().toString());
                int inch=Integer.parseInt(editheightinch.getText().toString());
                int totalinch=ft*12+inch;
                double totalCm=totalinch*2.54;
                double totalM=totalCm/100;
                double bmi=wt/(totalM*totalM);
                if(bmi>25){
                    text.setText("Overweight");
                    main.setBackgroundColor(Color.RED);
                }
                else if(bmi<18.5){
                    text.setText("Underweight");
                    main.setBackgroundColor(Color.BLUE);
                }else{
                    text.setText("Normal");
                    main.setBackgroundColor(Color.GREEN);

                }
            }
        });
    }
}
