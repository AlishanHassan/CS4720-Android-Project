package cs4720.cs.virginia.edu.cs4720androidproject;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import org.json.*;
import java.net.HttpURLConnection;
import java.net.*;
import android.os.Message;
import android.os.Messenger;
import android.os.*;
import java.io.*;
import org.apache.http.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;



import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.app.Activity;

public class AMissionsList extends AppCompatActivity implements SensorEventListener {


    public static final String WUNDERGROUND_API_KEY = "4d7b89426d7d4eb2";
    public static final String WUNDERGROUND_URL = "http://api.wunderground.com/api/4d7b89426d7d4eb2/conditions/q/";
    public static final String WUNDERGROUND_URL_TEST = "http://api.wunderground.com/api/4d7b89426d7d4eb2/conditions/q/37.776289,-122.395234.json";
    public static final String WUNDERGROUND_URL_EXT = ".json";
    public String weatherString = "test";
    public double daLatitude, daLongitude;
    //http://api.wunderground.com/api/542e792185b9e21f/conditions/q/CA/San_Francisco.json
    //http://api.wunderground.com/api/542e792185b9e21f/conditions/q/37.776289,-122.395234.json
    //http://www.wunderground.com/cgi-bin/findweather/getForecast?query=37.773285,-122.417725
    public static final String CONDITION = "weather";
    public static final String TEMP_F = "temp_f";
    final int CONNECTION_TIMEOUT = 0;
    final int DATARETRIEVAL_TIMEOUT = 0;
    final String CLASS_NAME = this.getClass().getName();
    ProgressDialog message;
    //private WeatherDownloader weatherDownloader;
    private JSONObject weatherJSON;
    public boolean scallywag = false;
    public int listLength = 18;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 612;
    public float speed;







    private String[] missions_list = {"Scale Mount Everest without Freezing too Badly - 1000000 XP",
            "Protect your Bagels from the Rain - 1000 XP",
            "Pick Apples - 5000 XP",
            "Visit the Rotunda - 2000 XP",
            "Attend Class in Clark - 1000 XP + BONUS ROCKING",
            "Go Downtown - 3000 XP",
            "Check Out the Fralin - 3000 XP",
            "Head to Tech as Armageddon Rises - 1 XP",
            "It's a Nice Day to Watch a Football Game - 2000 XP + BONUS ROCKING",
            "See the Mona Lisa in Typical British Weather- 100000 XP",
            "Rain or Shine, Meet THE Professor Sherriff - 5000000 XP",
            "On a day hotter than Death Valley, Tour Monticello - 5000 XP",
            "On a bright Canadian Day, See Niagara Falls - 20000 XP",
            "The Golden Gate Bridge? - 50000 XP",
            "A Trip To The National Mall - 10000 XP",
            "Rock out in the Rice Auditorium - 7000 XP + BONUS ROCKING",
            "Olsson is Dark and Scary. Go Somewhere Happy. - 2000 XP",
            "Laugh at the Wet Floor Signs As You Head to Web and Mobile - 2500 XP"
    };
    private int[] points = {1000000,
            1000,
            5000,
            2000,
            1000,
            3000,
            3000,
            1,
            2000,
            100000,
            5000000,
            5000,
            20000,
            50000,
            10000,
            7000,
            2000
    };
    private String[] missionCoordinates = {"27.9881,86.9253",
            "38.0456989,-78.510402",
            "37.9916772,-78.4736202",
            "38.0335571,-78.5101605",
            "38.0363182,-78.5099734",
            "38.0299486,-78.4809297",
            "38.0382902,-78.5051853",
            "37.2283886,-80.4256",
            "38.031459,-78.5151362",
            "48.8606146,2.3354607",
            "38.0314226,-78.5109111",
            "38.0086085,-78.4553827",
            "43.0540984,-79.2277753",
            "37.8199328,-122.4804384",
            "38.8855611,-77.0338853",
            "38.0314226,-78.5109111",
            "38.0314226,-78.5109111",
            "38.0314226,-78.5109111"
    };
    private String[] missionConditions = {"32,NULL",
            "666,Rain",
            "666,NULL",
            "666,NULL",
            "666,NULL",
            "666,Drizzle",
            "666,NULL",
            "0,Thunderstorm",
            "666,Partly Cloudy",
            "666,Overcast",
            "666,NULL",
            "144,NULL",
            "666,SUNNY",
            "666,NULL",
            "666,NULL",
            "666,NULL",
            "666,NULL",
            "666,Rain"
    };

    private String[] missions_description = {"The greatest of the 7 summits. You need to reach the top, while freezing.",
            "Bodo's Bagels are delicious. Protect them on a rainy day.",
            "Carter's Mountain is a great place to get some pies.",
            "Yeah, it's not the original, but you should still be able to identify it.",
            "Clark is a bit quiet most of the time. Rock your heart away!",
            "A nice mist in your face is required for the ultimate downtown experience.",
            "I'm not sure what it is, either. Ask someone from the College.",
            "Hell awaits. Tread lightly. Bone chilling temperatures and devastating lightning await.",
            "The weather is just right. Rock away in the stadium.",
            "Anglo influences have crossed the Channel.",
            "The one and the only. It will be an honor.",
            "Death Valley holds the record for hottest day in America.",
            "It's a pleasant way to experience the longest border between two countries in the world.",
            "It opened in 1937. It's probably still there.",
            "You're on Maine Street! Check out the Paddle Boats while you're there.",
            "Rice Rocks",
            "There's a tunnel in the basement. At the end is a light.",
            "It's always raining when we have to go to class, isn't it?"
    };

    private int totalScore;
    private int currPoints;
    private List<String[]> activeMissionsList;
    private ListView missionsView;
    private ArrayAdapter arrayAdapter;
    private ListAdapter listAdapter;
    private TextView editView;
    private double lati, longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amissions_list);
        missionsView = (ListView) findViewById(R.id.aMissListView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = mSensorManager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            mSensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }

        activeMissionsList = new LinkedList<>();
        activeMissionsList.add(new String[]{"Scale Mount Everest", "Reach the top of the world. Stand high and mighty among your peers."});

        String s = Home.getCoordinates();
        String tempPoints = Home.getScore();
        currPoints = Integer.parseInt(tempPoints);
        Integer[] stuff = new Integer[listLength];
        for (int i = 0; i < listLength; i++){
            stuff[i] = 666;
        }
        String FILENAME = "completed_file";
        try {
            FileInputStream fis = openFileInput(FILENAME);
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = fis.read()) != -1) {
                builder.append((char) ch);
            }
            String completed = new String(builder);
            String temp = new String();
            int j = 0;
            for (int i = 0; i < completed.length(); i++) {
                if (completed.charAt(i) != ' ' ){
                    temp += completed.charAt(i);
                }
                else {
                    stuff[j] = Integer.parseInt(temp);
                    j++;
                    temp = "";
                }
            }
        } catch (Exception e) {
            Log.e("StorageExample", e.getMessage());
        }

        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        for (int i = 0; i < listLength; i++){
            if (stuff[i] != 666){
                missions_list[stuff[i]] = "Completed!";
            }
        }
        arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                missions_list);

        missionsView.setAdapter(arrayAdapter);

        missionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                String currentCoordinates = Home.getCoordinates();



                String value = (String) adapter.getItemAtPosition(position);
                Integer[] stuff = new Integer[listLength];
                for (int i = 0; i < listLength; i++){
                    stuff[i] = 666;
                }
                String FILENAME = "completed_file";
                try {
                    FileInputStream fis = openFileInput(FILENAME);
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while((ch = fis.read()) != -1) {
                        builder.append((char) ch);
                    }
                    String completed = new String(builder);
                    String temp = new String();
                    int j = 0;
                    for (int i = 0; i < completed.length(); i++) {
                        if (completed.charAt(i) != ' ' ){
                            temp += completed.charAt(i);
                        }
                        else {
                            stuff[j] = Integer.parseInt(temp);
                            j++;
                            temp = "";
                        }
                    }
                } catch (Exception e) {
                    Log.e("StorageExample", e.getMessage());
                }

                //Toast.makeText(this, value, Toast.LENGTH_LONG).show();
                if(accomplished(position)){
                    int check = 0;
                    for (int i = 0; i < listLength; i++){
                        if (stuff[i] == position) {
                            scallywag = true;
                            Toast.makeText(AMissionsList.this,"Stop that you scallywag!", Toast.LENGTH_LONG).show();
                            check = 1;
                            Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                            startActivity(choiceIntent);
                        }
                    }
                    if(scallywag == false)
                        Toast.makeText(AMissionsList.this, "Mission accomplished", Toast.LENGTH_LONG).show();
                    if(position == 4 || position == 8 || position == 15) {
                        currPoints += (points[position] + (int)speed);
                    }
                    else {
                        currPoints += points[position];
                    }
                    //Toast.makeText(AMissionsList.this, "Total Points: " + currPoints, Toast.LENGTH_LONG).show();
                    FILENAME = "scores_file";
                    String OTHER_FILENAME = "completed_file";
                    try {
                        if (check == 0) {
                            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                            String stringPoints = Integer.toString(currPoints);
                            fos.write(stringPoints.getBytes());
                            fos.close();
                        }
                    }catch(Exception e) {
                        Log.e("MissionQuest", e.getMessage());
                    }
                    try {
                        FileOutputStream ofos = openFileOutput(OTHER_FILENAME, Context.MODE_PRIVATE);
                        String outStrung = "";
                        for (int i = 0; i < listLength; i++){
                            if (stuff[i] != 666){
                                outStrung = outStrung + stuff[i].toString() + " ";
                                System.out.println(outStrung);
                            }
                        }
                        outStrung = outStrung + Integer.toString(position) + " ";
                        ofos.write(outStrung.getBytes());
                        System.out.println("Outstrung getbytes" + outStrung.getBytes());
                        ofos.close();
                    }catch(Exception e) {
                        Log.e("MissionQuest", e.getMessage());
                        System.out.println("Exception with outStrung, dude");
                    }
                        //StringBuilder builder = new StringBuilder();
                        //int ch;
                        //while ((ch == fis.read()) != 1) {

                        //}
                    Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                    choiceIntent.putExtra("ADDED_POINTS", currPoints);
                    startActivity(choiceIntent);
                    }
                    //Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                    //choiceIntent.putExtra("ADDED_POINTS", currPoints);
                    //startActivity(choiceIntent);
                }



                //Home.autocompleteText.setText("Autocomplete missions off");

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amissions_list, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean accomplished(int missionNumber)
    {
        String currentCoordinates = Home.getCoordinates();
        String[] currCoordinateSplit = currentCoordinates.split(",");
        double currLatitude = Double.parseDouble(currCoordinateSplit[0]);
        double currLongitude = Double.parseDouble(currCoordinateSplit[1]);

        String[] tempMissionCoordinates = missionCoordinates[missionNumber].split(",");

        double missionLatitude = Double.parseDouble(tempMissionCoordinates[0]);
        double missionLongitude = Double.parseDouble(tempMissionCoordinates[1]);


        String[] tempMissionConditions = missionConditions[missionNumber].split(",");
        Double missionTemp = Double.parseDouble(tempMissionConditions[0]);
        String missionCond = tempMissionConditions[1];

        System.out.println("temp mission stuff: " + missionTemp + " " + missionCond);

        daLatitude = missionLatitude;
        daLongitude = missionLongitude;

        getWeatherFromInterenet("hi");


        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        //System.out.println("the weather string: " + weatherString);
        String[] weatherData = weatherString.split(",");
        double theTemp = Double.parseDouble(weatherData[0]);
        String theConditions = weatherData[1];
        System.out.println("temp: " + theTemp + " cond: " + theConditions);


        double latDiff = currLatitude - missionLatitude;
        double longDiff = currLongitude - missionLongitude;


        //Toast.makeText(this, latDiff + " " + longDiff, Toast.LENGTH_LONG).show();

        if((latDiff <= .0005 && latDiff >= -.0005) && (longDiff <= .0005 && longDiff >= -.0005)
                && (missionTemp == theTemp || missionTemp == 666)
              && (missionCond.equals(theConditions) || missionCond.equals("NULL"))
                )
        {
            System.out.println("conditions met!");
            return true;
        }
        else {
            System.out.println("conditions not met");
            Toast.makeText(AMissionsList.this, "Mission Requirements Not Met", Toast.LENGTH_LONG).show();

            new AlertDialog.Builder(this)
                    .setTitle("Mission Requirements Unfulfilled")
                    .setMessage(missions_description[missionNumber])
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();



            return false;
        }


/*
        try{
        JSONObject reader = getWundergroundWeatherForecast(missionLatitude,missionLongitude);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            System.out.println("failure");
        }
*/


    /*
        try {
            String wunderURL = "http://api.wunderground.com/api/4d7b89426d7d4eb2/conditions/q/" + missionLatitude + "," + missionLatitude + ".json";
            URL url = new URL(wunderURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //readStream(in);
                JSONObject reader = new JSONObject(wunderURL);
                String weather = reader.getString("weather");
                Toast.makeText(this, weather, Toast.LENGTH_LONG).show();
            }
            finally{
                        urlConnection.disconnect();
            }

        }
        catch (Exception e)
        {
            System.out.println("blah");
        }
*/








        /*
        try {

            JSONObject reader = new JSONObject(wunderURL);
            String weather = reader.getString("weather");
            Toast.makeText(this, weather, Toast.LENGTH_LONG).show();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            System.out.println("failure");
        }
*/


    }

/*
    public JSONObject getWundergroundWeatherForecast(double latitude, double longitude) {
        Log.i("GetWundergroundWeather", "Going to fetch weather");
        weatherDownloader = new WeatherDownloader();
        weatherDownloader.execute(String.valueOf(latitude),String.valueOf(longitude));
        return weatherJSON;
    }

    private static String getWeatherResponseText(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }

    private class WeatherDownloader extends AsyncTask<String, Integer, JSONObject> {
        ProgressDialog message;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            Log.i("WeatherDownloader", "Going to fetch weather");
            publishProgress(1);
            try {
                URL url = new URL(WUNDERGROUND_URL+params[0]+","+params[1]+WUNDERGROUND_URL_EXT);
                urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() != 200) {
                    throw new Exception("Failed to connect");
                }

                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                weatherJSON = new JSONObject(getWeatherResponseText(inputStream));
                return weatherJSON;

            } catch (MalformedURLException malExp) {
                Log.e(CLASS_NAME, "URL is incorrect");
            } catch (ProtocolException proExp) {
                Log.e(CLASS_NAME, "Website down");
            } catch (IOException ioExp) {
                Log.e(CLASS_NAME, "Could not read in any JSON");

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (Throwable throwable){}
                try {
                    urlConnection.disconnect();
                } catch (Throwable throwable){}
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.i("WeatherDownloader", "Weather was fetched");
        }
    }*/









    void getWeatherFromInterenet(final String location){

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    String temperature = "", condition = "";
                    URL url;
                    try {
                        System.out.println(location);
                        System.out.println("Coordinates " + daLatitude + "," + daLongitude);

                        url = new URL("http://api.wunderground.com/api/4d7b89426d7d4eb2/conditions/q/" + daLatitude + "," + daLongitude + ".json");
                        System.out.println("URL: " + url);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        //wait(2000);
                        InputStream input = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        String oneLineFromInternet;
                        String wholeReplyFromInternet = "";
                        while ((oneLineFromInternet = reader.readLine()) != null) {
                            wholeReplyFromInternet += oneLineFromInternet + " ";
                        }
                        JSONObject jsonObject = new JSONObject(wholeReplyFromInternet);
                        JSONObject current_observation = jsonObject.getJSONObject("current_observation");
                        temperature = current_observation.getString(TEMP_F);
                        condition = current_observation.getString(CONDITION);
                        weatherString = temperature + "," + condition;

                        System.out.println(weatherString);
                    }
                    catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        thread.start();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        //Toast.makeText(this, x + " " + y + " " + z, Toast.LENGTH_LONG).show();

        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;
            }

            last_x = x;
            last_y = y;
            last_z = z;


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    /*
    void sendWeatherToClient(String actualWeatherString) {
        Bundle reply = new Bundle();
        reply.putString("weather", actualWeatherString);
        Message replyMessage = Message.obtain();
        replyMessage.setData(reply);
        try {
            messengerToClient.send(replyMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    */



}
