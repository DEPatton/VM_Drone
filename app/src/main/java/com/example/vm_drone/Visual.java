package com.example.vm_drone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Visual extends Fragment
{

    private Button visualButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.visual_layout, container, false);


        //Todo background should be the drone camera eventually
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        visualButton = view.findViewById(R.id.visual_button);

        visualButton.setOnClickListener(view1 -> {
            Intent i = new Intent(view1.getContext(), VisualExample.class);

            // Start the activity
            startActivity(i);
        });
    }
}
