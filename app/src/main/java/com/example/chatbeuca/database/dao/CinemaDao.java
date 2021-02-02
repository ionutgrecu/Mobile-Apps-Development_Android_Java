package com.example.chatbeuca.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chatbeuca.database.model.Cinema;
import com.example.chatbeuca.database.model.Movie;


@Dao
public interface CinemaDao {
    @Query("select * from cinemas where denumire=:denumire")
    List<Cinema> getAllByCategory(String denumire);
    @Insert
    long insert(Cinema cinema);

    @Update
    int update(Cinema cinema);


    @Delete
    int delete(Cinema cinema);

    @Query("select * from cinemas")
    List<Cinema> getAll();

    @Query("delete from cinemas")
    void deleteAll();
}
