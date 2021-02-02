package com.example.chatbeuca.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chatbeuca.database.dao.CursuriDao;
import com.example.chatbeuca.database.model.CursValutar;
import com.example.chatbeuca.util.DateConverter;

@Database(entities = {CursValutar.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})

public abstract class DatabaseManager2 extends RoomDatabase {

    private final static String DB_NAME = "cursuri.db";
    private static DatabaseManager2 databaseManager2;

    public static DatabaseManager2 getInstance(Context context)
    {
        if (databaseManager2==null)
            databaseManager2 = Room.databaseBuilder(context, DatabaseManager2.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        return databaseManager2;
    }

    public abstract CursuriDao getCursDao();
}
