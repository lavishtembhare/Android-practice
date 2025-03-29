package com.example.app6;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
TextView txtAnim;
Button Translate,Alpha,Rotate,Scale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txtAnim=findViewById(R.id.text);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.move);
        Translate=findViewById(R.id.btnTranslate);
        Alpha=findViewById(R.id.btnAlpha);
        Rotate=findViewById(R.id.btnRotate);
        Scale=findViewById(R.id.btnScale);
        Translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation move=AnimationUtils.loadAnimation(MainActivity.this,R.anim.move);
                txtAnim.startAnimation(move);
            }
        });
        Alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation alpha=AnimationUtils.loadAnimation(MainActivity.this,R.anim.move);
                txtAnim.startAnimation(alpha);
            }
        });
        Rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation rotate=AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);
                txtAnim.startAnimation(rotate);
            }
        });
        Scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation scale=AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                txtAnim.startAnimation(scale);
            }
        });
    }
}