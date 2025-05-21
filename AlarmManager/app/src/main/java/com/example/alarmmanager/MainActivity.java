package com.example.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlert();
            }
        });
    }

    public void startAlert() {
        EditText text = findViewById(R.id.time);
        String inputText = text.getText().toString().trim();

        // Validate input
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a valid time in seconds", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int seconds = Integer.parseInt(inputText);

            Intent intent = new Intent(MainActivity.this, MyBroadcastReceiver.class);

            // Use proper flag for PendingIntent
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags |= PendingIntent.FLAG_IMMUTABLE;
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(),
                    234342453,
                    intent,
                    flags
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            // Use appropriate method for setting the alarm based on Android version
            if (alarmManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + (seconds * 1000L),
                            pendingIntent
                    );
                } else {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + (seconds * 1000L),
                            pendingIntent
                    );
                }
                Toast.makeText(this, "Alarm set in " + seconds + " seconds", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }
}