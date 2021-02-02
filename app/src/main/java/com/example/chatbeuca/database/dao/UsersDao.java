package com.example.chatbeuca.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.chatbeuca.database.model.User;

@Dao
public interface UsersDao {

    @Query("select * from users")
    List<User> getAll();

    @Query("select * from users where category=:category")
    List<User> getAllByCategory(String category);

    @Insert
    long insert(User user);

    @Update
    int update(User user);

    @Delete
    int delete(User user);
}
