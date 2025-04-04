package com.example.app11;

import static android.widget.Toast.LENGTH_SHORT;

import static com.example.app11.R.*;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the button from the layout
        Button toastBtn = findViewById(R.id.toast_btn);

        // Set the button's OnClickListener
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the custom toast
                Toast toast = new Toast(getApplicationContext());
                View view = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toastView));
                toast.setView(view);

                // Set message in TextView
                TextView txtMsg = view.findViewById(R.id.txtMsg);
                txtMsg.setText("Custom Toast");

                // Show toast with short duration
                toast.setDuration(LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.END,0,0);
                toast.show();
            }
        });
    }
}
