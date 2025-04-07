package com.example.app14;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
Button btn_share,btn_dialer,btn_msg,btn_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn_share=findViewById(R.id.btn_share);
        btn_dialer=findViewById(R.id.btn_dialer);
        btn_email=findViewById(R.id.btn_email);
        btn_msg=findViewById(R.id.btn_msg);
        btn_dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDial=new Intent();
                iDial.setAction(Intent.ACTION_DIAL);
                iDial.setData(Uri.parse("tel: +918554043545"));
                startActivity(iDial);
            }
        });
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMsg=new Intent(Intent.ACTION_SENDTO);
                iMsg.setData(Uri.parse("smsto:"+Uri.encode("+918554019974")));
                iMsg.putExtra("sms body","Please solve this issue asap");
                startActivity(iMsg);

            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iEmail=new Intent(Intent.ACTION_SEND);
                iEmail.setType("message/rfc822");
                iEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"lavishtembhare93@gmail.com","lavishtembhare0802@gmail.com"});
                iEmail.putExtra(Intent.EXTRA_SUBJECT,"Queries");
                iEmail.putExtra(Intent.EXTRA_TEXT,"Resolve this issue");
                startActivity(Intent.createChooser(iEmail,"Email via"));
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iShare=new Intent(Intent.ACTION_SEND);
                iShare.setType("text/plain");
                iShare.putExtra(Intent.EXTRA_TEXT,"https://github.com/lavishtembhare");
                startActivity(Intent.createChooser(iShare,"Share it"));
            }
        });
    }
}