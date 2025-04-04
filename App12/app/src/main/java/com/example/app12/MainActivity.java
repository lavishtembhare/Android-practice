package com.example.app12;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        Button btnOK=dialog.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Toast.makeText(MainActivity.this,"Closed",Toast.LENGTH_SHORT).show();
                                         dialog.dismiss();
                                     }
                                 });
                dialog.show();

    }
}