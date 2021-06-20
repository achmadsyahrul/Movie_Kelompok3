package com.example.movie.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserTable {
    @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "username")
    String username;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "password")
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
