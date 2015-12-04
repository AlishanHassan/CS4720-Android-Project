package cs4720.cs.virginia.edu.cs4720androidproject;

import android.graphics.drawable.Drawable;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.math.*;


public class Dashboard extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    public static double latitude;
    public static double longitude;
    public String coordinates;
    private static TextView currentLocationView, conditionsView, closestMissionView;
    private static ImageView weatherIcon;
    public Drawable conditionImage;
    public static final String CONDITION = "weather";
    public static final String TEMP_F = "temp_f";
    public static final String TEMP_C = "temp_c";
    public static final String ICON_URL = "icon_url";
    public String weatherString = "test";

    private String[] missions_list = {"Scale Mount Everest without Freezing too Badly",
            "Protect your Bagels from the Rain",
            "Pick Apples",
            "Visit the Rotunda",
            "Attend Class in Clark",
            "Go Downtown",
            "Check Out the Fralin",
            "Head to Tech as Armageddon Rises",
            "It's a Nice Day to Watch a Football Game",
            "See the Mona Lisa in Typical British Weather",
            "Rain or Shine, Meet THE Professor Sherriff",
            "On a day hotter than Death Valley, Tour Monticello",
            "On a bright Canadian Day, See Niagara Falls",
            "The Golden Gate Bridge?",
            "A Trip To The National Mall",
            "Rock out in the Rice Auditorium",
            "Olsson is Dark and Scary. Go Somewhere Happy.",
            "Laugh at the Wet Floor Signs As You Head to Web and Mobile"
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

        currentLocationView = (TextView) findViewById(R.id.currentLocationView);
        conditionsView = (TextView) findViewById(R.id.conditionsView);
        closestMissionView = (TextView) findViewById(R.id.closestMissionView);
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);

        getWeatherFromInterenet(latitude + "," + longitude);
        try {
            Thread.sleep(1800);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        String[] weatherData = weatherString.split(",");
        double theFTemp = Double.parseDouble(weatherData[0]);
        double theCTemp = Double.parseDouble(weatherData[1]);
        String theConditions = weatherData[2];
        if (theConditions.length() == 0)
        {
            theConditions = "Uncharted";
        }
        String fullLocation = weatherData[4] + "," + weatherData[5];
        currentLocationView.setText(fullLocation + " at " + latitude + ", " + longitude);
        conditionsView.setText(theFTemp + "°F / " + theCTemp + "°C" + " and " + theConditions + "   ");


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
                    String temperatureF = "", temperatureC = "", condition = "", iconURL = "", fullLocation = "";
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
                        iconURL = current_observation.getString(ICON_URL);
                        fullLocation = current_observation.getString("display_location");
                        String[] fullLocationSplit = fullLocation.split("\"");
                        System.out.println("full location: " + fullLocationSplit[3]);
                        weatherString = temperatureF + "," + temperatureC + "," + condition + "," + iconURL + "," + fullLocationSplit[3];


                        System.out.println(weatherString);

                        Drawable image = ImageOperations(iconURL, "weatherIcon.gif");
                        weatherIcon.setImageDrawable(image);

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


    private Drawable ImageOperations(String url, String saveFilename) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Object fetch(String address) throws MalformedURLException,IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }


}
