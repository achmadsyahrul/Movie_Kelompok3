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
import com.example.movie.view.fragment.UserFragment;

public class UpdateActivity extends AppCompatActivity {

    EditText name, username, password;
    Button update;
    SharedPreferences sharedPreferences;
    String sName, sUsername, sPassword;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name = findViewById(R.id.et_name);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        update = findViewById(R.id.btn_update);

        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        id = sharedPreferences.getInt("user_id",0);
        sName = sharedPreferences.getString("name", null);
        sUsername = sharedPreferences.getString("username",null);
        sPassword = sharedPreferences.getString("password",null);

        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        final UserDao userDao = userDatabase.userDao();
        UserTable userTable = new UserTable();
        name.setText(sName);
        username.setText(sUsername);
        password.setText(sPassword);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editName = name.getText().toString();
                String editUsername = username.getText().toString();
                String editPassword = password.getText().toString();

                if(editName.isEmpty() || editUsername.isEmpty() || editPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    userTable.setName(editName);
                    userTable.setUsername(editUsername);
                    userTable.setPassword(editPassword);

                    userDatabase.userDao().update(id, editName, editUsername, editPassword);
                    editor.putInt("user_id", id);
                    editor.putString("username", editUsername);
                    editor.putString("password", editPassword);
                    editor.putString("name", editName);
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}