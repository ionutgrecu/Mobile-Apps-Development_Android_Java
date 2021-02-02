package com.example.chatbeuca.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chatbeuca.database.dao.CinemaDao;
import com.example.chatbeuca.database.dao.MovieDao;

import com.example.chatbeuca.database.model.Cinema;
import com.example.chatbeuca.database.model.Movie;
import com.example.chatbeuca.util.DateConverter;

@Database(entities = {Movie.class, Cinema.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager3 extends RoomDatabase {

    private final static String DB_NAME = "movies.db";
    private static DatabaseManager3 instanta;

    public static DatabaseManager3 getInstance(Context context) {
        if (instanta == null) {
            instanta = Room.databaseBuilder(context, DatabaseManager3.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instanta;
    }

    public abstract MovieDao getMoviesDao();

    public abstract CinemaDao getCinemaiDao();
}
