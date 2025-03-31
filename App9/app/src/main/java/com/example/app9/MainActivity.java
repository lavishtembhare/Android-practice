package com.example.app9;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
RecyclerView recycler;
ArrayList<ContactModel>arrModel=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
//        ContactModel model= new ContactModel(R.drawable.img2,"A","653464");
        arrModel.add(new ContactModel(R.drawable.img,"A","456456435"));
        arrModel.add(new ContactModel(R.drawable.img1,"B","455676435"));
        arrModel.add(new ContactModel(R.drawable.img2,"C","456456435"));
        arrModel.add(new ContactModel(R.drawable.img3,"D","456456435"));
        arrModel.add(new ContactModel(R.drawable.img4,"E","456456435"));
        arrModel.add(new ContactModel(R.drawable.img5,"F","456456435"));
        arrModel.add(new ContactModel(R.drawable.img6,"G","456456435"));
        arrModel.add(new ContactModel(R.drawable.img7,"H","456456435"));
        arrModel.add(new ContactModel(R.drawable.img8,"I","456456435"));
        arrModel.add(new ContactModel(R.drawable.img9,"J","456456435"));
      Recycle r= new Recycle(this, arrModel);
        recycler.setAdapter(r);
    }
}