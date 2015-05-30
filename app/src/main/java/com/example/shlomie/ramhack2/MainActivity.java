package com.example.shlomie.ramhack2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import at.markushi.ui.CircleButton;


public class MainActivity extends Activity {

    // views
    private CircleButton button;
    private TextView textTimer;

    //objects, vars
    GPSTracker gpsTracker;
    private int timerSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init
        super.onCreate(savedInstanceState);
        Data.init();
        //set up
        setContentView(R.layout.activity_main);
        findViews();
        btnClick();

    }

    public void findViews()
    {
        textTimer = (TextView) findViewById(R.id.textTimer);
        button = (CircleButton) findViewById(R.id.circleButton);
    }

     public void btnClick() {
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 button.setVisibility(View.GONE);
                 gpsTracker = new GPSTracker(MainActivity.this);
                 String lat = "" + gpsTracker.getLatitude();
                 String lon  = "" + gpsTracker.getLongitude();

//                 Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " +
//                         gpsTracker.getLatitude() + "\nLong: " +
//                         gpsTracker.getLongitude(),
//                         Toast.LENGTH_LONG).show();

                 Report newReport = Data.generateReport(lat, lon);
                 (new APICall()).execute("");
             }
         });
    }

    public String cal(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public String cal(String time){ // I know this is fucked up but testing something :)
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            gpsTracker.showSettingsAlert();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
//                getString("timerSeconds"), Context.MODE_PRIVATE);
//        timerSeconds =

    }


}
