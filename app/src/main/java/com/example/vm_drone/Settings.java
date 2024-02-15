package com.example.vm_drone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.zerokol.views.joystickView.JoystickView;

public class Settings extends Fragment
{
    private TextView DarkText, JoyText;

    private Switch DarkModeSwitch, JoyStickSwitch;

    private boolean darkModeState,joyStickState;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.settings_layout, container, false);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        DarkText = view.findViewById(R.id.Dark_mode_Text);
        JoyText = view.findViewById(R.id.Joystick_Text);
        DarkModeSwitch = view.findViewById(R.id.Dark_mode);
        JoyStickSwitch = view.findViewById(R.id.Enable_Joysticks);
        DarkModeSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Todo Dark Mode implementation goes here
            }
        });
        JoyStickSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Todo disable or enable the functionality of the joysticks in the home activity
            }
        });



    }
    public void SetDarkState(boolean current)
    {
        darkModeState = current;
    }
    public void SetJoyState(boolean current)
    {
        joyStickState = current;
    }
    public boolean GetDarkState()
    {
        return darkModeState;
    }
    public boolean GetJoyState()
    {
        return joyStickState;
    }
}
