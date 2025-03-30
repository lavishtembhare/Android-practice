package com.example.app7;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listview;
ArrayList<String> arr=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.listview);
        arr.add("Element1");
        arr.add("Element2");
        arr.add("Element3");
        arr.add("Element4");
        arr.add("Element5");
        arr.add("Element6");
        arr.add("Element7");
        arr.add("Element8");
        arr.add("Element9");
        arr.add("Element10");
        arr.add("Element11");
        arr.add("Element12");
        arr.add("Element13");
        arr.add("Element14");
        arr.add("Element15");
        arr.add("Element16");
        arr.add("Element17");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr);

    }
}