package com.example.app13;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
private static final String CHANNEL_ID="My Channel";
    private static final int NOTIFICATION_ID=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Drawable dr= ResourcesCompat.getDrawable(getResources(),R.drawable.icon,null);
        BitmapDrawable bitmapDrawable=(BitmapDrawable) dr;
        Bitmap largeIcon=bitmapDrawable.getBitmap();
        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification n;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             n=new Notification.Builder(this).setLargeIcon(largeIcon).setSmallIcon(R.drawable.icon).setContentText("New Message").setSubText("New Message from Lavish").setChannelId(CHANNEL_ID).build();
             nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
        }else {
            n=new Notification.Builder(this).setLargeIcon(largeIcon).setSmallIcon(R.drawable.icon).setContentText("New Message").setSubText("New Message from Lavish").build();

        }
        nm.notify(NOTIFICATION_ID,n);
    }
}