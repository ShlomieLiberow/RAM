package com.example.shlomie.ramhack2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;


public class MainActivity extends Activity {

    // views
    private CircleButton button;
    private ProgressBar progressBar;
    private ImageButton settingsButton;
    //objects, vars
    private boolean APIing;
    private  SharedPreferences sharedPref;
    private GPSTracker gpsTracker;
    private double timerSeconds;
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data.init();
        gpsTracker = new GPSTracker(MainActivity.this);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        //methods
        defineSize();
        findViews();
        btnClick();

    }

    private void defineSize()
    {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 1100;
        params.width = 800;
        params.y = -10;

        this.getWindow().setAttributes(params);
    }

    public void findViews()
    {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button = (CircleButton) findViewById(R.id.circleButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
    }

     public void btnClick()
     {
         button.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v) {
                 clickOnButton();
             }
         });

         settingsButton.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v) {
                 gpsTracker.showSettingsAlert();
             }
         });
    }

    private void clickOnButton()
    {
        if(APIing)
            return;

        APIing = true;
        button.setColor(Color.GREEN);
        String lat = "" + gpsTracker.getLatitude();
        String lon  = "" + gpsTracker.getLongitude();
        Toast.makeText(getApplicationContext(), "Reported!",
                Toast.LENGTH_SHORT).show();

        Report newReport = Data.generateReport(lat, lon);
        (new APICall()).execute("");

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("time", newReport.getTime());
        editor.putString("date", newReport.getDate());
        editor.commit();
        timerSeconds = Data.TIMER;

        myTimer = new Timer();
        myTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                runMethod();
            }
        }, 0, 10);
    }
    private void runMethod()
    {
        timerSeconds -= 0.01;
        button.setEnabled(true);
        progressBar.setProgress((int)(timerSeconds * 100) / Data.TIMER);
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable()
    {
        public void run()
        {
            if(timerSeconds < 0)
            {
                button.setVisibility(View.VISIBLE);
                finishTimer();
            }
        }
    };

    private void finishTimer()
    {
        APIing = false;
        button.setColor(Color.RED);
        timerSeconds = Data.TIMER;
        System.out.println("dies here");
        myTimer.cancel();
    }

//    public String cal(){
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c.getTime());
//        return formattedDate;
//    }
//
//    public String cal(String time){ // I know this is fucked up but testing something :)
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
//        String formattedDate = df.format(c.getTime());
//        return formattedDate;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
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
        String lastTime = sharedPref.getString("time", "");
        String lastDate = sharedPref.getString("date", "");
        System.out.println("timerSeconds " + lastTime + " .. date: " + lastDate);

    }
}
