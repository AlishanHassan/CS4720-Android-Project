package cs4720.cs.virginia.edu.cs4720androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class AMissionsList extends AppCompatActivity {

    private String[] missions_list = {"Scale Mount Everest - 1000000 XP",
            "Get Bagels - 1000 XP",
            "Pick Apples - 5000 XP",
            "Visit the Rotunda - 2000 XP",
            "Attend Class in Clark - 1000 XP",
            "Go Downtown - 3000 XP",
            "Check Out the Fralin - 3000 XP",
            "Head to Tech - 1 XP",
            "Watch a Football Game - 2000 XP",
            "See the Mona Lisa - 100000 XP",
            "Meet THE Professor Sherriff - 5000000 XP",
            "Tour Monticello - 5000 XP",
            "See Niagara Falls - 20000 XP",
            "The Golden Gate Bridge? - 50000 XP",
            "A Trip To The National Mall - 10000 XP"};
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
            10000
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
            "38.8855611,-77.0338853"
    };

    private int totalScore;
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



        activeMissionsList = new LinkedList<>();
        activeMissionsList.add(new String[]{"Scale Mount Everest", "Reach the top of the world. Stand high and mighty among your peers."});

        String s = Home.getCoordinates();

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

        arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                missions_list);

        missionsView.setAdapter(arrayAdapter);

        missionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                String currentCoordinates = Home.getCoordinates();

                String tempPoints;

                String value = (String) adapter.getItemAtPosition(position);

                //Toast.makeText(this, value, Toast.LENGTH_LONG).show();
                if(accomplished(position)){
                    Toast.makeText(AMissionsList.this, "Mission accomplished", Toast.LENGTH_LONG).show();

                    Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                    choiceIntent.putExtra("NEW_MISSION", value);
                    startActivity(choiceIntent);
                }



                //Home.autocompleteText.setText("Autocomplete missions off");
            }
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


        double latDiff = currLatitude - missionLatitude;
        double longDiff = currLongitude - missionLongitude;


        Toast.makeText(this, latDiff + " " + longDiff, Toast.LENGTH_LONG).show();

        if((latDiff <= .0005 && latDiff >= -.0005) && (longDiff <= .0005 && longDiff >= -.0005))
        {
            return true;
        }
        else {
            return false;
        }
    }
}
