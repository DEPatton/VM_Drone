package com.example.vm_drone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class GPS extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove the super.onCreate(savedInstanceState) from onCreateView
        // It's not necessary and could cause issues
        // super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.gps_layout, container, false);

        // Example of finding a view within the inflated layout
        // Note: Uncomment the line below if you need to find a view by ID
        // TextView helloWorldTextView = view.findViewById(R.id.HelloWorld);

        return view;
    }
}
