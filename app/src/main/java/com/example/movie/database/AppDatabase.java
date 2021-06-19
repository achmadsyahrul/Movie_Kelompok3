package com.example.movie.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {MovieTable.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDAO();

    private static AppDatabase appDatabase;

    public static AppDatabase initDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "favorite_database"
            ).allowMainThreadQueries().build();

            appDatabase.populateInitialData();
        }
        return appDatabase;
    }

    public static void switchToInMemory(Context context) {
        appDatabase = Room.inMemoryDatabaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class
        ).build();
    }

    private void populateInitialData() {
        if (movieDAO().count() == 0) {
            runInTransaction(new Runnable() {

                List<MovieTable> modelDb = new ArrayList<>();
                MovieTable getData;

                @Override
                public void run() {

                    modelDb = movieDAO().getMovieDb();
                    for (int a = 0; a < movieDAO().getAllMovie().getCount(); a++) {
                        getData = new MovieTable();
                        getData = modelDb.get(a);
                        movieDAO().insert(getData);
                    }
                }
            });
        }
    }
}
