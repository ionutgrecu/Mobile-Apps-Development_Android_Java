package com.example.chatbeuca.database.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cinemas")
public class Cinema {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String denumire;
    private int nrSali;
    private String locatie;

    public Cinema(int id, String denumire, int nrSali, String locatie) {
        this.id = id;
        this.denumire = denumire;
        this.nrSali = nrSali;
        this.locatie = locatie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNrSali() {
        return nrSali;
    }

    public void setNrSali(int nrSali) {
        this.nrSali = nrSali;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", nrSali=" + nrSali +
                ", locatie='" + locatie + '\'' +
                '}';
    }
}
