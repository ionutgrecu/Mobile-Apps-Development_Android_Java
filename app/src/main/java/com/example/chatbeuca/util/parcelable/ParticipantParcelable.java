package com.example.chatbeuca.util.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.chatbeuca.util.ChMode;
import com.example.chatbeuca.util.DateConverter;

import java.util.Date;

import static com.example.chatbeuca.util.ChMode.Encrypted;
import static com.example.chatbeuca.util.ChMode.NORMAL;


public class ParticipantParcelable implements Parcelable {
    private String name;
    private int age;
    private Date atDate;
    private ChMode chMode;
    private String team;

    public ParticipantParcelable(String name, int age, Date atDate, ChMode chMode, String team) {
        this.name = name;
        this.age = age;
        this.atDate = atDate;
        this.chMode = chMode;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getAtDate() {
        return atDate;
    }

    public void setAtDate(Date atDate) {
        this.atDate = atDate;
    }

    public ChMode getAssType() {
        return chMode;
    }

    public void setAssType(ChMode assType) {
        this.chMode = assType;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public static Creator<ParticipantParcelable> getCREATOR() {
        return CREATOR;
    }

    public static void setCREATOR(Creator<ParticipantParcelable> CREATOR) {
        ParticipantParcelable.CREATOR = CREATOR;
    }

    @Override
    public String toString() {
        return name + ", " + age + " ani, s-a inscris in conversatie pe "
                + new DateConverter().toString()
                + ", la " + team + " - " + chMode;
    }

    private ParticipantParcelable(Parcel source) {
        //ordinea de scriere in fisier trebuie respectata si la citire
        name = source.readString();
        age = source.readInt();
        atDate = new DateConverter().fromString(source.readString());
        chMode = Encrypted.name().equals(source.readString()) ? NORMAL : NORMAL;
        team = source.readString();
    }

    //Creatorul este public deoarece acesta trebuie sa fie invocat de pachetul
    // android care se afla in exteriorul clasei noastre
    //Este static deoarece dorim sa obtinem o instanta Java pe baza fisierului parcel,
    // ceea ce inseamna ca nu poate depinde de vreo instanta ci de clasa
    // (cititi ce inseamna o variabila static - POO si Java)
    public static Creator<ParticipantParcelable> CREATOR = new Creator<ParticipantParcelable>() {
        @Override
        public ParticipantParcelable createFromParcel(Parcel source) {
            return new ParticipantParcelable(source);
        }

        @Override
        public ParticipantParcelable[] newArray(int size) {
            return new ParticipantParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //ordinea de scriere in fisier trebuie respectata si la citire
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(new DateConverter().toString());
        dest.writeString(chMode.name());
        dest.writeString(team);
    }
}
