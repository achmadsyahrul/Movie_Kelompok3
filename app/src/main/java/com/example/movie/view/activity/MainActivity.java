package com.example.movie.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.movie.view.fragment.MovieFragment;
import com.example.movie.R;
import com.example.movie.view.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment = new MovieFragment();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        Boolean check = sharedPreferences.getBoolean("logged", false);
        if(!check) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            bottomNavigationView = findViewById(R.id.main_bottomnav);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);

            loadFragment(selectedFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bottomnav_movie:
                selectedFragment = new MovieFragment();
                loadFragment(selectedFragment);
                break;

            case R.id.menu_bottomnav_user:
                selectedFragment = new UserFragment();
                loadFragment(selectedFragment);
                break;
        }

        return loadFragment(selectedFragment);
    }

    private boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragmentcontainer, selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }
}