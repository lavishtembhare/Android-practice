package com.example.app7;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listview;
Spinner spinner;
AutoCompleteTextView actxtView;
ArrayList<String> arr=new ArrayList<>();
ArrayList<String> arrIDs=new ArrayList<>();
ArrayList<String> arrLang=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.listview);
        spinner=findViewById(R.id.spinner);
        actxtView=findViewById(R.id.actxtView);
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
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Toast.makeText(MainActivity.this,"First Item Clicked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        arrIDs.add("ID 1");
        arrIDs.add("ID 2");
        arrIDs.add("ID 3");
        arrIDs.add("ID 4");
        arrIDs.add("ID 5");
        arrIDs.add("ID 6");
        arrIDs.add("ID 7");
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrIDs);
        spinner.setAdapter(spinnerAdapter);
        arrLang.add("C");
        arrLang.add("C++");
        arrLang.add("Java");
        arrLang.add("Python");
        arrLang.add("PHP");
        arrLang.add("Kotlin");
        arrLang.add("Dart");
        ArrayAdapter<String> actxtAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrLang);
        actxtView.setAdapter(actxtAdapter);
        actxtView.setThreshold(2);

    }
}