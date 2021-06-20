package com.example.movie.base;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;

import org.jetbrains.annotations.Nullable;

@SuppressLint("Registered")
public class BaseAppCompactActivity extends AppCompatActivity {
    public static final String KEY_FRAGMENT = "fragment";
    public static final String KEY_TITLE = "title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(this);
    }
}
