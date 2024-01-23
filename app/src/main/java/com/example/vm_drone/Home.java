package com.example.vm_drone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.main_menu_layout, container, false);

            return view;

        }
        @Override
        public void onViewCreated(View view,Bundle savedInstanceState)
        {
            TextView visualView = view.findViewById(R.id.Visual_text);
            TextView voiceView = view.findViewById(R.id.Voice_text);
            SwitchCompat visualSwitch = view.findViewById(R.id.Visual_switch);
            SwitchCompat voiceSwitch = view.findViewById(R.id.Voice_switch);

        }


}

