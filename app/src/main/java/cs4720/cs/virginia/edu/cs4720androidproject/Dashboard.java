package cs4720.cs.virginia.edu.cs4720androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.location.*;
import android.util.Log;
import android.content.*;
import com.google.android.*;
import android.view.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.math.*;


public class Dashboard extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    public static double latitude;
    public static double longitude;
    public String coordinates;
    private static TextView latitudeView, longitudeView, temperatureView, conditionsView, closestMissionView;

    public static final String CONDITION = "weather";
    public static final String TEMP_F = "temp_f";
    public static final String TEMP_C = "temp_c";
    public String weatherString = "test";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        coordinates = Home.getCoordinates();
        String[] currCoordinateSplit = coordinates.split(",");
        latitude = Double.parseDouble(currCoordinateSplit[0]);
        longitude = Double.parseDouble(currCoordinateSplit[1]);
        latitudeView = (TextView) findViewById(R.id.latitudeView);
        longitudeView = (TextView) findViewById(R.id.longitudeView);
        latitudeView.setText("" + latitude);
        longitudeView.setText("" + longitude);

        temperatureView = (TextView) findViewById(R.id.temperatureView);
        conditionsView = (TextView) findViewById(R.id.conditionsView);
        getWeatherFromInterenet(latitude + "," + longitude);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String[] weatherData = weatherString.split(",");
        double theFTemp = Double.parseDouble(weatherData[0]);
        double theCTemp = Double.parseDouble(weatherData[1]);
        String theConditions = weatherData[2];

        temperatureView.setText(theFTemp + "°F / " + theCTemp + "°C");
        conditionsView.setText(theConditions);
        int closestMission = 0;
        double tempDistance = 6666666666666.0;
        for (int i = 0; i < 18; i++)
        {
            String blah = missionCoordinates[i];
            String[] blahSplit = blah.split(",");
            double x = Double.parseDouble(blahSplit[0]);
            double y = Double.parseDouble(blahSplit[1]);
            double distance = Math.sqrt(Math.abs(((latitude - x) * (latitude - x)) + ((longitude - y) * (longitude - y))));
            if (distance < tempDistance)
            {
                tempDistance = distance;
                closestMission = i;
            }
        }

        closestMissionView = (TextView) findViewById(R.id.closestMissionView);
        closestMissionView.setText(missions_list[closestMission]);
        System.out.println(missions_list[closestMission]);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
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


    void getWeatherFromInterenet(final String location){

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    String temperatureF = "", temperatureC = "", condition = "";
                    URL url;
                    try {
                        System.out.println("Location: " + location);
                        System.out.println("Coordinates " + latitude + "," + longitude);

                        url = new URL("http://api.wunderground.com/api/4d7b89426d7d4eb2/conditions/q/" + latitude + "," + longitude + ".json");
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
                        temperatureF = current_observation.getString(TEMP_F);
                        temperatureC = current_observation.getString(TEMP_C);
                        condition = current_observation.getString(CONDITION);
                        weatherString = temperatureF + "," + temperatureC + "," + condition;

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


}
