package cs4720.cs.virginia.edu.cs4720androidproject;

import android.content.Context;
import android.content.Intent;
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
import java.io.*;
import org.apache.http.*;


import java.io.FileInputStream;
import java.io.FileOutputStream;
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



        activeMissionsList = new LinkedList<>();
        activeMissionsList.add(new String[]{"Scale Mount Everest", "Reach the top of the world. Stand high and mighty among your peers."});

        String s = Home.getCoordinates();
        String tempPoints = Home.getScore();
        currPoints = Integer.parseInt(tempPoints);
        Integer[] stuff = new Integer[15];
        for (int i = 0; i < 15; i++){
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
        for (int i = 0; i < 15; i++){
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
                Integer[] stuff = new Integer[15];
                for (int i = 0; i < 15; i++){
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
                    for (int i = 0; i < 15; i++){
                        if (stuff[i] == position) {
                            Toast.makeText(AMissionsList.this,"Stop that you scallywag!", Toast.LENGTH_LONG).show();
                            check = 1;
                            Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                            startActivity(choiceIntent);
                        }
                    }
                    Toast.makeText(AMissionsList.this, "Mission accomplished", Toast.LENGTH_LONG).show();
                    currPoints += points[position];
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
                        for (int i = 0; i < 15; i++){
                            if (stuff[i] != 666){
                                outStrung = outStrung + stuff[i].toString() + " ";
                            }
                        }
                        outStrung = outStrung + Integer.toString(position) + " ";
                        ofos.write(outStrung.getBytes());
                        ofos.close();
                    }catch(Exception e) {
                        Log.e("MissionQuest", e.getMessage());
                    }
                        //StringBuilder builder = new StringBuilder();
                        //int ch;
                        //while ((ch == fis.read()) != 1) {

                        //}

                    }
                    Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                    choiceIntent.putExtra("ADDED_POINTS", currPoints);
                    startActivity(choiceIntent);
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

        String wunderURL = "http://api.wunderground.com/api/4d7b89426d7d4eb2/conditions/q/" + missionLatitude + "," + missionLatitude + ".json";



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

        double latDiff = currLatitude - missionLatitude;
        double longDiff = currLongitude - missionLongitude;


        //Toast.makeText(this, latDiff + " " + longDiff, Toast.LENGTH_LONG).show();

        if((latDiff <= .0005 && latDiff >= -.0005) && (longDiff <= .0005 && longDiff >= -.0005))
        {
            return true;
        }
        else {
            return false;
        }
    }
}
