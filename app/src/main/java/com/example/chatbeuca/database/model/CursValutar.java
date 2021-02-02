package com.example.chatbeuca.database.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cursuri")
public class CursValutar {

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

    private String data;
    private String euro;
    private String dolar;
    private String gbp;
    private String aur;

    @Ignore
    public CursValutar(){

    }

    public CursValutar(String data, String euro, String dolar, String gbp, String aur) {
        this.data = data;
        this.euro = euro;
        this.dolar = dolar;
        this.gbp = gbp;
        this.aur = aur;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEuro() {
        return euro;
    }

    public void setEuro(String euro) {
        this.euro = euro;
    }

    public String getDolar() {
        return dolar;
    }

    public void setDolar(String dolar) {
        this.dolar = dolar;
    }

    public String getGbp() {
        return gbp;
    }

    public void setGbp(String gbp) {
        this.gbp = gbp;
    }

    public String getAur() {
        return aur;
    }

    public void setAur(String aur) {
        this.aur = aur;
    }

    @Override
    public String toString() {
        return "CursValutar{" +
                "data='" + data + '\'' +
                ", euro='" + euro + '\'' +
                ", dolar='" + dolar + '\'' +
                ", gbp='" + gbp + '\'' +
                ", aur='" + aur + '\'' +
                '}';
    }
}
