package com.example.shlomie.ramhack2;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import at.markushi.ui.CircleButton;


public class MainActivity extends Activity {

    private CircleButton button;
    String[] APIInput = new String[2];
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick();

    }

     public void btnClick() {
        button = (CircleButton) findViewById(R.id.circleButton);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 button.setVisibility(View.GONE);

                 gpsTracker = new GPSTracker(MainActivity.this);
                 System.out.println("Lat " + gpsTracker.getLatitude());
                 System.out.println("Long " + gpsTracker.getLongitude());
                 Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + gpsTracker.getLatitude() + "\nLong: " + gpsTracker.getLongitude(), Toast.LENGTH_LONG).show();
                 APIInput[0] = "%7B%22UserID%22%3Anull%2C%22Location%22%3A%5B%7B%22LongLat%22%3A%22" + gpsTracker.getLongitude() + "%2C" + gpsTracker.getLatitude() +
                         "%22%2C%22Date%22%3A+%22" + cal() + "%22%2C+%22Time%22%3A%22"+ cal("time")+"%22%7D%5D%0D%0A%7D";
                 System.out.println(APIInput[0]);
                 System.out.println(cal());
                 //APICall client = new APICall();
                 //client.setListener(clientListener);//pass through listener instantiated above
                 //client.execute(APIInput[0]);
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
}
