package com.example.vm_drone;



import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.vm_drone.databinding.MainActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StartUp extends AppCompatActivity
{
    MainActivityBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // So a new fragment is not made every fragment switch
        Home home = new Home();
        Settings setting = new Settings();
        Visual visual = new Visual();
        Voice voice = new Voice();
        GPS gps = new GPS();


        replaceFragment(home);
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item ->
        {
            int menuItem = item.getItemId();
            if (menuItem == R.id.settings)
            {
                replaceFragment(setting);
                return true;
            }
            else if (menuItem == R.id.visual)
            {
                replaceFragment(visual);
                return true;
            }
            else if (menuItem == R.id.voice)
            {
                replaceFragment(voice);
                return true;
            }
            else if (menuItem == R.id.Home)
            {
                replaceFragment(home);
                return true;
            }
            else if (menuItem == R.id.location)
            {
                replaceFragment(gps);
                return true;
            }

            return false;
        });

    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}