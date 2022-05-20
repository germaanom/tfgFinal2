package com.german.proyectofinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.german.proyectofinal.R;
import com.german.proyectofinal.fragments.AddFragment;
import com.german.proyectofinal.fragments.HomeFragment;
import com.german.proyectofinal.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavActivity extends AppCompatActivity {

    BottomNavigationView mBtnNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        showSelectedFragment(new HomeFragment());
        mBtnNav = (BottomNavigationView) findViewById(R.id.btnNav);
        mBtnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.menu_home){
                    showSelectedFragment(new HomeFragment());
                }

                if(item.getItemId() == R.id.menu_add){
                    showSelectedFragment(new AddFragment());
                }

                if(item.getItemId() == R.id.menu_profile){
                    showSelectedFragment(new ProfileFragment());
                }

                return true;
            }
        });
    }

    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                commit();
    }
}

