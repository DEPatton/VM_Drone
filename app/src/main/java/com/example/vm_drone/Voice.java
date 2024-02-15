package com.example.vm_drone;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.List;

public class Voice extends Fragment {

    private String spokenText = "";
    private ImageButton voiceImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.voice_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        voiceImage = view.findViewById(R.id.microphone);

        EditText speechInput = view.findViewById(R.id.TextDisplay);
        //Todo Add a recyclerview to hold all string values given within the apps lifecycle
        voiceImage.setOnClickListener(view1 -> displaySpeechRecognizer());
        String spoken = GetSpokenText();
        speechInput.setText(spoken);
    }
    private static final int SPEECH_REQUEST_CODE = 0;

    //Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    //This callback is invoked when the Speech Recognizer returns.
    //This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            //spokenText can now be accessed via a setter
            SetSpokenText(results.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String GetSpokenText()
    {
        return spokenText;
    }

    private void SetSpokenText(String text)
    {
        spokenText = text;
    }

}
