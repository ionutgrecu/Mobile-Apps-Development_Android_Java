package com.example.chatbeuca.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatbeuca.database.model.CursValutar;

import java.util.List;

@Dao
public interface CursuriDao {

    @Insert
    void insert(CursValutar cv);

    @Insert
    void insert(List<CursValutar> cursuri);

    @Query("select * from cursuri")
    List<CursValutar> getAll();

    @Query("delete from cursuri")
    void deleteAll();

    @Delete
    void deleteCV(CursValutar cv);
}
