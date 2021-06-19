package com.example.movie.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insertMovie(MovieTable movieTable);

    @Query("SELECT * FROM favorite_movie")
    List<MovieTable> getMovieDb();

    @Delete
    void deleteMovie(MovieTable movieTable);

    @Query("SELECT * FROM favorite_movie WHERE id = :idMovie LIMIT 1")
    List<MovieTable> getById(int idMovie);

    @Query("SELECT * FROM favorite_movie WHERE category = :category")
    List<MovieTable> getByCategory(String category);

    //query bawah ini untuk yg contentprovider

    @Query("SELECT COUNT(*) FROM " + "favorite_movie")
    int count();

    @Insert
    long[] insertAll(MovieTable[] movie);

    @Insert
    long insert(MovieTable movieModelDb);

    @Query("SELECT * FROM favorite_movie")
    Cursor getAllMovie();

    @Query("SELECT * FROM favorite_movie WHERE id = :idMovie LIMIT 1")
    Cursor getMovieById(long idMovie);
}
