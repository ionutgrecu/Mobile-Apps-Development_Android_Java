package com.example.chatbeuca.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import com.example.chatbeuca.util.DateConverter;

@Entity(tableName = "users")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "time")
    private Double time;
    @ColumnInfo(name = "description")
    private String description;

    public User(long id, Date date, String category, Double time, String description) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.time = time;
        this.description = description;
    }

    @Ignore
    public User(Date date, String category, Double time, String description) {
        this.date = date;
        this.category = category;
        this.time = time;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Date: " + DateConverter.fromDate(date) +
                " Timee: " + time +
                " Category: " + category +
                " Description: " + (description != null ? description : "-");
    }
}
