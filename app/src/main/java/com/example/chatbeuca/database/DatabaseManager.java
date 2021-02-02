package com.example.chatbeuca.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chatbeuca.database.dao.UsersDao;
import com.example.chatbeuca.database.model.User;
import com.example.chatbeuca.util.DateConverter;

@Database(entities = {User.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DAM_DB = "dam_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context,
                            DatabaseManager.class, DAM_DB)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract UsersDao getUserDao();
}
