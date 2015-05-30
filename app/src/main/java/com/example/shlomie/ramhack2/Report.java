package com.example.shlomie.ramhack2;

/**
 * Created by nik on 30/05/15.
 */
public class Report {

    private int userID;
    private String time;
    private String date;
    private String lat;
    private String lon;

    public Report(int userID, String time, String date, String lat, String lon)
    {
        this.userID = userID;
        this.time = time;
        this.lat = lat;
        this.lon = lon;
    }

    public int getUerID() {
        return userID;
    }

    public String getLat() {
        return lat;
    }

    public String getDate() {

        return date;
    }

    public String getLon() {
        return lon;
    }

    public String getTime() {

        return time;
    }
}
