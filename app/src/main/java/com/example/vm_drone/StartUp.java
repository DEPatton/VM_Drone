package com.example.vm_drone;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.vm_drone.databinding.MainActivityBinding;

public class StartUp extends AppCompatActivity
{
   MainActivityBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new Home());
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int menuItem = item.getItemId();
            if (menuItem == R.id.settings)
            {
                replaceFragment(new Settings());
                return true;
            }
            else if (menuItem == R.id.visual)
            {
                replaceFragment(new Visual());
                return true;
            }
            else if (menuItem == R.id.voice)
            {
                replaceFragment(new Voice());
                return true;
            }
            else if (menuItem == R.id.Home)
            {
                replaceFragment(new Home());
                return true;
            }
            else if (menuItem == R.id.location)
            {
                replaceFragment(new GPS());
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