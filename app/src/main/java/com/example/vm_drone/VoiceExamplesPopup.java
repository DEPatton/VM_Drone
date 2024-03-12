package com.example.vm_drone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class VoiceExamplesPopup extends DialogFragment
{

    public interface DialogListener
    {
        void onDataEntered(String data);
    }
    private DialogListener listener;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (DialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement DialogListener");
        }
    }
    private Button StopCom,LandCom,ForwardCom;

    private String Command;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.voice_examples_popup, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        StopCom = view.findViewById(R.id.Stop_Command);
        LandCom = view.findViewById(R.id.Land_Command);
        ForwardCom = view.findViewById(R.id.GoForward_Command);

        StopCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listener.onDataEntered("Stop");
            }
        });
        LandCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listener.onDataEntered("Land");
            }
        });
        ForwardCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onDataEntered("Go Forward");
            }
        });
    }

    public void setCommand(String setCom)
    {
        Command = setCom;
    }
    public String GetCommand()
    {
        return Command;
    }
}
