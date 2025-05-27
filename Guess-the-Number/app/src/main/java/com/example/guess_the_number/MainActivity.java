package com.example.guess_the_number;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
int result;
static int getRandomNumber(int max, int min){
    return (int)((Math.random()*(max-min))+min);
}
    public void makeToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }
    public void clickFunction(View view){
    int userGuessing;
        EditText variable=(EditText) findViewById(R.id.editId);
        userGuessing=Integer.parseInt(variable.getText().toString());
        if(userGuessing<result){
            makeToast("Think of Higher Number, Try Again");
        } else if (userGuessing>result) {
            makeToast("Think of Lower Number, Try Again");
        }else {
            makeToast("Congratulations,"+" You Got the Number");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        result=getRandomNumber(1,100);
    }
}