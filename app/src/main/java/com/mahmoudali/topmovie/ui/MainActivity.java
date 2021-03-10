package com.mahmoudali.topmovie.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mahmoudali.topmovie.R;

import com.mahmoudali.topmovie.databinding.ActivityBottomMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityBottomMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityBottomMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(MainActivity.this,R.id.fragment);
        binding.smoothBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_movie:
                        navController.navigate(R.id.home2);
                        return true;
                    case R.id.favorite_movie:
                        navController.navigate(R.id.favorite);
                        return true;
                    case R.id.search_movie:
                        navController.navigate(R.id.searchMovies);
                        return true;
                }
                return false;
            }
        });

    }

}