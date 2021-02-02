package com.example.chatbeuca.util;

import java.io.Serializable;
import java.util.Date;

public class Participant implements Serializable {
    private String name;
    private int code;
    private Date attDate;
    private ChMode chMode;
    private String team;

    public Participant(String name, int code, Date attDate, ChMode chMode, String team) {
        this.name = name;
        this.code = code;
        this.attDate = attDate;
        this.chMode = chMode;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public ChMode getAssType() {
        return chMode;
    }

    public void setAssType(ChMode chMode) {
        this.chMode = chMode;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return name + ", ani," + code + " inscris pe "
                + new DateConverter().toString()
                + ", la " + team + " - " + chMode;
    }
}
