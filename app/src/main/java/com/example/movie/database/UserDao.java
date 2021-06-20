package com.example.movie.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void registerUser(UserTable userTable);

    @Query("SELECT * from user where username=(:username) and password=(:password)")
    UserTable login(String username, String password);

    @Query("UPDATE user SET name=(:name), username=(:username), password=(:password) WHERE id = :id")
    void update(int id, String name, String username, String password);
}
