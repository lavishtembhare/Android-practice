package com.example.app15;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AFragment extends Fragment {

    private static final String TAG = "AFragment";

    // Argument keys
    private static final String ARG_NAME = "Argument1";
    private static final String ARG_AGE = "Argument2";
    private static final String ARG_DOMAIN = "Argument3";

    // Member variables
    private String name;
    private int age;
    private String domain;

    public AFragment() {
        // Required empty public constructor
    }

    public static AFragment newInstance(String name, int age, String domain) {
        AFragment fragment = new AFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_AGE, age);
        args.putString(ARG_DOMAIN, domain);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            age = getArguments().getInt(ARG_AGE);
            domain = getArguments().getString(ARG_DOMAIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout once
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        // Initialize UI components
        TextView textView_a = view.findViewById(R.id.txtFrag);

        // Set text and log values if arguments are present
        if (getArguments() != null) {
            String logMessage = "Name: " + name + ", Age: " + age + ", Domain: " + domain;
            Log.d(TAG, logMessage); //Important
            textView_a.setText(logMessage);
        }

        return view;
    }
}
