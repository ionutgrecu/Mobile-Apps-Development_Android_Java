package com.example.chatbeuca.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatbeuca.database.model.Movie;
import com.example.chatbeuca.database.model.User;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Insert
    void insert(List<Movie> movieList);

    @Query("select * from movies")
    List<Movie> getAll();

    @Query("delete from movies")
    void deleteAll();

    @Delete
    void delete(Movie movie);

    @Query("select * from movies where idCinema= :idCinema")
    List<Movie> getMoviesFromCinema(long idCinema);
    @Query("select * from movies where platforma=:platforma")
    List<Movie> getAllByCategory(String platforma);
}
