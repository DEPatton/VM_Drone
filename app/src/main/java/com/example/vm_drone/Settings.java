package com.example.vm_drone;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.zerokol.views.joystickView.JoystickView;

public class Settings extends Fragment
{
    private static final String JOY_STATE_KEY = "Joy_Stick";
    private TextView DarkText, JoyText;

    private Switch DarkModeSwitch, JoyStickSwitch;

    private boolean darkModeState, joyStickState;

    private Activity activity = getActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.settings_layout, container, false);
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
                // Checks to see if the joystick are on or not

                //if the joystick is Enabled and the joy state is false
                if(JoyStickSwitch.isEnabled() && GetJoyState() == false)
                {
                    SetJoyState(true);
                    CharSequence text = "Joystick Enabled";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getActivity(), text, duration).show();
                }
                else
                {
                    SetJoyState(false);
                    CharSequence text = "Joystick Disabled";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getActivity(), text, duration).show();
                }
            }
        });

    }
    @Override
    public void onResume()
    {
        super.onResume();
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
