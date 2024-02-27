package com.example.vm_drone;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class VisualExample extends AppCompatActivity
{
    private ImageButton imageButton;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visual_examples_layout);

        imageButton = findViewById(R.id.visual_exam_back_button);

        imageButton.setOnClickListener(view -> finish());
    }
}
