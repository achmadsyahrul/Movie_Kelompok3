package com.example.movie.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.R;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_login;
    SharedPreferences sharedPreferences;
    String username, password;
    Boolean check, reg;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        check = sharedPreferences.getBoolean("logged", false);
        reg = sharedPreferences.getBoolean("registered", false);

        if(!reg){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, "123180112");
            editor.putString(KEY_PASSWORD, "123");
            editor.putString(KEY_NAME, "Achmad Syahrul");
            editor.putBoolean("registered", true);
            editor.apply();
        }

        if(check){
            //jika ada data ke main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            et_username = findViewById(R.id.et_username);
            et_password = findViewById(R.id.et_password);
            btn_login = findViewById(R.id.btn_login);


            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = sharedPreferences.getString(KEY_USERNAME, null);
                    password = sharedPreferences.getString(KEY_PASSWORD, null);
                    if(et_username.getText().toString().equals(username) && et_password.getText().toString().equals(password)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("logged", true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        };
    }
}