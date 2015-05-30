package com.example.shlomie.ramhack2;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shlomie on 30/05/2015.
 */
public class APICall extends AsyncTask<String, String, JSONArray> { //Parameters: Input, updates to onProgressUpdates, returns doInBackground()
    private Listener listener; //instantiate local variable for listener

    @Override
    protected JSONArray doInBackground(String... params) { //main method

        postData(params[0]);
        return null;
    }

    public void postData(String valueIWantToSend) {
        //Create a new HttpClient and Post Header
        HttpParams params = new BasicHttpParams();
        HttpClient client = new DefaultHttpClient(params);
        HttpPost post = new HttpPost("http://lionsrace.altervista.org/serverRAM.php");

        try
        {
            int lastReportIndex = Data.getReports().size() - 1;
            Report myRep = Data.getReports().get(lastReportIndex);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("userID", myRep.getUerID());
            jsonObj.put("location", myRep.getLat() + "," + myRep.getLon());
            jsonObj.put("date", myRep.getDate());
            jsonObj.put("time", myRep.getTime());

            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("data", jsonObj.toString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
            entity.setContentEncoding(HTTP.UTF_8);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            String result = inputStreamToString(response.getEntity().getContent()).toString();
            System.out.println(result);

        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private StringBuilder inputStreamToString(InputStream is) throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end
        while ((line = rd.readLine()) != null) {
            total.append(line);
        }

        // Return full string
        return total;
    }



//    private JSONArray getJsonArray(HttpURLConnection urlConnection, JSONArray response, String[] params, int x) { //actual API Call
//        URL url;
//        JSONObject res;
//        try {
//            url = new URL(params[x]);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            int responseCode = urlConnection.getResponseCode();
//
//            if (responseCode == HttpStatus.SC_OK) {
//                String responseString = readStream(urlConnection.getInputStream());
//                Log.v("FusionTableCall", +x + "" + responseString);//this is where the printout is happening
//
//                res = new JSONObject(responseString);//first get whole object
//                if (res.has("rows")) {//check if row exists
//                    JSONArray response2 = res.getJSONArray("rows");//get row called rows from JSON object
//                    response = (JSONArray) response2.get(0); //get first value which contains API value
//                    Log.i("responsevalue", response + "");
//                } else {
//                    Log.i("I am null", "");
//                    JSONArray noValue = new JSONArray();
//                    noValue.put(0, "0");//create Array containing 0
//                    response = noValue;
//                    Log.i("responsevalueNull", response + "");
//                }
//            } else {
//                Log.v("FusionTableCall", "Response code:" + responseCode);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null)
//                urlConnection.disconnect();
//        }
//        return response;
//    }

//    private String readStream(InputStream in) {
//        BufferedReader reader = null;
//        StringBuilder response = new StringBuilder();
//        try {
//            reader = new BufferedReader(new InputStreamReader(in));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return response.toString();
//    }

    // Called after doInBackground finishes executing
    @Override
    protected void onPostExecute(JSONArray result) {
//        String R1;
//        String R2;
//        String R3;
//        String R4;
//        try { //get all 4 values from the API call
//            R1 = (String) result.get(0);
//            int Count_int1 = Integer.parseInt(R1);
//            R2 = (String) result.get(1);
//            int Count_int2 = Integer.parseInt(R2);
//            R3 = (String) result.get(2);
//            int Count_int3 = Integer.parseInt(R3);
//            R4 = (String) result.get(3);
//            int Count_int4 = Integer.parseInt(R4);
//            Log.i("test2", R1);
//
//            Log.i("CountIntValue", Count_int2 + "");
//
//            double count_int;//final statistic to be passed to the user
//            if (Count_int1 == 0 || Count_int2 == 0) { //assign 0 if no value is available to either of the 2 calls
//                count_int = 0;
//                Log.i("whenNull", count_int + "");
//            } else {
//                count_int = ((double) (Count_int1 / Count_int2)); //divide API value 1 by 2 to get statistic %. Double brackets rounds up value
//                Log.i("", count_int + "");
//            }
//            listener.updateTextView(count_int);//pass value to listener in Statistics Display Class
//            listener.chartData(Count_int3, Count_int4);//update chartview
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public void setListener(Listener listener) {//set local listener variable above
        this.listener = listener;
    }

    public interface Listener {
        void updateTextView(double text);//setting up interface to require class implementation of updateTextView();

        void chartData(int x, int y);//update chart view
    }
}