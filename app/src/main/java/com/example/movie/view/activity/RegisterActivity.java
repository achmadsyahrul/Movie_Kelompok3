package com.example.movie.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.R;
import com.example.movie.database.UserDao;
import com.example.movie.database.UserDatabase;
import com.example.movie.database.UserTable;

public class RegisterActivity extends AppCompatActivity {

    EditText name, username, password;
    Button register, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.et_name);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.btn_tologin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTable userTable = new UserTable();
                userTable.setUsername(username.getText().toString());
                userTable.setName(name.getText().toString());
                userTable.setPassword(name.getText().toString());
                if(!validation(userTable)){
                    Toast.makeText(getApplicationContext(),"Fill all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userTable);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private Boolean validation(UserTable userTable){
        if (userTable.getName().isEmpty() ||
                userTable.getUsername().isEmpty() ||
                userTable.getPassword().isEmpty()){
            return false;
        }
        return true;


    }
}