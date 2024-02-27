package com.example.vm_drone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class VoiceExamples extends AppCompatActivity
{

    private ImageButton imageButton;

    private Button StopCom,LandCom,ForwardCom;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_examples_layout);



        imageButton = findViewById(R.id.voice_exam_back_button);
        StopCom = findViewById(R.id.Stop_Command);
        LandCom = findViewById(R.id.Land_Command);
        ForwardCom = findViewById(R.id.GoForward_Command);
        
        StopCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) 
            {
                
            }
        });
        LandCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ForwardCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) 
            {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



       //Todo scrollview buttons should have clickable quick commands in them and be able to send them back to the voice screen


    }

    @Override
    public void onResume()
    {
        super.onResume();
        imageButton.setOnClickListener(view -> finish());
    }
}
