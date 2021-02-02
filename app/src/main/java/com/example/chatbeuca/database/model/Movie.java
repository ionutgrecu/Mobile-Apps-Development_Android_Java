package com.example.chatbeuca.database.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
enum Platforma {NETFLIX, HBOGO}

enum Gen {THRILLER, ROMANTIC, COMEDY, DRAMA, SF}

@Entity(tableName = "movies", foreignKeys = @ForeignKey(entity = Cinema.class, parentColumns = "id", childColumns = "idCinema", onDelete = CASCADE), indices = @Index("idCinema"))
public class Movie  implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Ignore
    private String uid;

    private String title;
    private Date data;
    private String regizor;
    private int profit;
    private String genFilm;
    private String platforma;
    /*private Gen genFilm;
    private Platforma platforma;*/

    public int getIdCinema() {
        return idCinema;
    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }

    private int idCinema;


    @Ignore
    public Movie(){

    }

    @Ignore
    public Movie(String title, Date data, String regizor, int profit, String genFilm, String platforma) {
        this.title = title;
        this.data = data;
        this.regizor = regizor;
        this.profit = profit;
        this.genFilm = genFilm;
        this.platforma = platforma;
    }

    public Movie(String title, Date data, String regizor, int profit, String genFilm, String platforma, int idCinema) {
        this.title = title;
        this.data = data;
        this.regizor = regizor;
        this.profit = profit;
        this.genFilm = genFilm;
        this.platforma = platforma;
        this.idCinema = idCinema;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getRegizor() {
        return regizor;
    }

    public void setRegizor(String regizor) {
        this.regizor = regizor;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public String getGenFilm() {
        return genFilm;
    }

    public void setGenFilm(String genFilm) {
        this.genFilm = genFilm;
    }

    public String getPlatforma() {
        return platforma;
    }

    public void setPlatforma(String platforma) {
        this.platforma = platforma;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", data=" + data +
                ", regizor='" + regizor + '\'' +
                ", profit=" + profit +
                ", genFilm=" + genFilm +
                ", platforma=" + platforma +
                '}';
    }

}
