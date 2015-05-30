package com.example.shlomie.ramhack2;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nik on 30/05/15.
 */
public class Data {

    private static ArrayList<Report> myReports;

    public static void init()
    {
        myReports = new ArrayList<Report>();
    }

    public static ArrayList<Report> getReports()
    {
        return myReports;
    }

    public static Report generateReport(String lat, String lon)
    {
        String currentDateStr = DateFormat.getDateInstance().format(new Date());
        String currentTimeStr = DateFormat.getTimeInstance().format(new Date());
        Report temp = new Report(Data.getReports().size(), currentTimeStr, currentDateStr, lat, lon);
        Data.getReports().add(temp);
        return temp;
    }
}
