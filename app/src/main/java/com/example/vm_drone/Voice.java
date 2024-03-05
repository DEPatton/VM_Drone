package com.example.vm_drone;

import static android.app.Activity.RESULT_OK;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Voice extends Fragment
{

    private String spokenText = "";
    private String readText;
    private ImageButton voiceImage;
    private HistoryAdapter adapter;

    private Home home = new Home();
    public static Handler handler = new Handler();

    private BluetoothDevice bluetoothMod;
    private final UUID uuid = home.arduinoUUID;

    private ConnectedBluetooth _connectedThread = null;
    private ConnectBluetooth connectBluetooth = null;

    private ItemViewModel viewModel;

    ArrayList<ChatHistory> chatHistoryArray = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.voice_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        voiceImage = view.findViewById(R.id.microphone);

        EditText speechInput = view.findViewById(R.id.TextDisplay);

        Button Voice_Button = view.findViewById(R.id.Voice_Exam);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);

        adapter = new HistoryAdapter(view.getContext(), chatHistoryArray);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);


        // Gets the Bluetooth device value from the Home fragment
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            // Perform an action with the latest item data.

            bluetoothMod = item;
        });

        speechInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int keyCode, KeyEvent keyEvent)
            {
                if(keyCode == KeyEvent.KEYCODE_ENDCALL)
                {
                    setReadText(speechInput.getText().toString());

                    // Todo try catch
                    if (bluetoothMod != null) {
                        Send(getReadText());
                        AddChatHistory(getReadText());
                        Receive();
                    } else {
                        CharSequence text = "Not Connected to Bluetooth Device";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(getActivity(), text, duration).show();
                    }
                    //AddChatHistory(getReadText());
                    return true;
                }
                return false;
            }
        });
        Voice_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(view.getContext(), VoiceExamples.class);

                // Start the activity
                startActivity(i);

                // Finish the current activity


            }
        });
        //Todo Add a recyclerview to hold all string values given within the apps lifecycle
        voiceImage.setOnClickListener(view1 -> displaySpeechRecognizer());
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(connectBluetooth != null || _connectedThread != null)
        {
            connectBluetooth.cancel();
            _connectedThread.cancel();
        }

    }
    private static final int SPEECH_REQUEST_CODE = 0;

    //Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    //This callback is invoked when the Speech Recognizer returns.
    //This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            //spokenText can now be accessed via a setter
            //SetSpokenText(results.get(0));

            // Todo add a char limit
            Send(results.get(0));
            AddChatHistory(results.get(0));
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

    // Adds String into Recycler View to view chat history
    public void AddChatHistory(String _chat)
    {
      ChatHistory chat = new ChatHistory(_chat);
      chatHistoryArray.add(chat);
      adapter.setChatHistory(chatHistoryArray);
      adapter.notifyDataSetChanged();
    }

    public String getSpokenText() {
        return spokenText;
    }
    public void setSpokenText(String spokenText) {
        this.spokenText = spokenText;
    }

    public String getReadText() {
        return readText;
    }

    public void setReadText(String readText) {
        this.readText = readText;
    }

    public void Send(String sentMessage)
    {
        if(connectBluetooth == null)
        {
            connectBluetooth = new ConnectBluetooth(bluetoothMod,uuid,handler);
            connectBluetooth.run();
        }
        //Check if Socket connected
        if (connectBluetooth.getSocket().isConnected())
        {
            if(_connectedThread == null)
            {
                _connectedThread = new ConnectedBluetooth(connectBluetooth.getSocket());
            }
            _connectedThread.write(sentMessage.getBytes());
        }
    }
    public void Receive()
    {
        if(connectBluetooth == null)
        {
            connectBluetooth = new ConnectBluetooth(bluetoothMod,uuid,handler);
            connectBluetooth.run();
        }
        //Check if Socket connected
        if (connectBluetooth.getSocket().isConnected())
        {
            if(_connectedThread == null)
            {
                _connectedThread = new ConnectedBluetooth(connectBluetooth.getSocket());
            }
            String RecievedMessage = _connectedThread.read();
        }
    }
}
