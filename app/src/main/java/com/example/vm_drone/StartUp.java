package com.example.vm_drone;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.vm_drone.databinding.MainActivityBinding;

public class StartUp extends AppCompatActivity implements VoiceExamplesPopup.DialogListener
{
    // So a new fragment is not made every fragment switch
    private final Home home = new Home();
    private final Settings setting = new Settings();
    private final Visual visual = new Visual();
    private final Voice voice = new Voice();
    private final GPS gps = new GPS();
    private MainActivityBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



        //replaceFragment(home);
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

    @Override
    public void onDataEntered(String data)
    {
        voice.AddChatHistory(data);
    }
}