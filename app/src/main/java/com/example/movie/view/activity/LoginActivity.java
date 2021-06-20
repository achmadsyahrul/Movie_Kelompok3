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
import com.example.movie.database.UserDao;
import com.example.movie.database.UserDatabase;
import com.example.movie.database.UserTable;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_login, btn_register;
    SharedPreferences sharedPreferences;
    Boolean check;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        check = sharedPreferences.getBoolean("logged", false);

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
            btn_register = findViewById(R.id.btn_toregister);


            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = et_username.getText().toString();
                    final String password = et_password.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    if(username.isEmpty()|| password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                        final UserDao userDao = userDatabase.userDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UserTable userTable = userDao.login(username, password);
                                if(userTable == null){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    String name = userTable.getName();
                                    int id = userTable.getId();
                                    editor.putInt(KEY_USER_ID, id);
                                    editor.putString(KEY_USERNAME, username);
                                    editor.putString(KEY_PASSWORD, password);
                                    editor.putString(KEY_NAME, name);
                                    editor.putBoolean("logged", true);
                                    editor.apply();
                                    startActivity(new Intent(
                                            LoginActivity.this, MainActivity.class)
                                    );
                                }
                            }
                        }).start();
                    }

                }
            });

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            });


        };
    }
}