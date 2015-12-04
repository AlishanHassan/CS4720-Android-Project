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


public class Dashboard extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    public static double latitude;
    public static double longitude;
    public String coordinates;
    private static TextView latitudeView, longitudeView;

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
        double latitude = Double.parseDouble(currCoordinateSplit[0]);
        double longitude = Double.parseDouble(currCoordinateSplit[1]);
        latitudeView = (TextView) findViewById(R.id.latitudeView);
        longitudeView = (TextView) findViewById(R.id.longitudeView);
        latitudeView.setText("" + latitude);
        longitudeView.setText("" + longitude);


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


}
