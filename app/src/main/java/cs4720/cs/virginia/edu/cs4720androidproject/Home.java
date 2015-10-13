package cs4720.cs.virginia.edu.cs4720androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
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

import java.io.FileInputStream;

//basically everything comes from this: https://github.com/googlesamples/android-play-location/blob/master/BasicLocationSample/app/src/main/java/com/google/android/gms/location/sample/basiclocationsample/MainActivity.java

public class Home extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Button locationFinderButton;
    //private Button settingsButton;
    private Button aboutButton;
    private EditText textField;
    private TextView texty;
    private TextView lat;
    private TextView lon;
    public static double latitude;
    public static double longitude;
    private Button availableMissionsButton;
    private static int MISSION_REQUEST = 1;
    private Location mLastLocation;

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
        //Toast.makeText(this, "Yay, something worked", Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        //locationFinderButton = (Button) findViewById(R.id.button);
        //textField = (EditText) findViewById(R.id.editText);
        texty = (TextView) findViewById(R.id.textView2);
    //import view for the listener, geez

        /*
        locationFinderButton.setOnClickListener(new View.OnClickListener() {
                                                    public void onClick(View v) {
                                                        texty.setText(textField.getText());
                                                        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


                                                        TextView mLatitudeText = (TextView) findViewById(R.id.mLatitudeText);
                                                        TextView mLongitudeText = (TextView) findViewById(R.id.mLongitudeText);

                                                        if (mLastLocation != null) {
                                                            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                                                            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                                                        } else {
                                                            mLatitudeText.setText("You know where you are");
                                                            mLongitudeText.setText("You know where you are");
                                                        }
                                                    }
                                                }

        );
        */



        String FILENAME = "scores_file";
        TextView scoreView = (TextView) findViewById(R.id.scoreView);

        try {
            FileInputStream fis = openFileInput(FILENAME);
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = fis.read()) != -1){
                builder.append((char)ch);
            }
            scoreView.setText(builder);
            fis.close();
        } catch (Exception e) {
            Log.e("StorageExample", e.getMessage());
        }


        aboutButton = (Button) findViewById(R.id.aboutbutton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, About.class));
            }
        });

        availableMissionsButton = (Button) findViewById(R.id.availablemissionsbutton);
        availableMissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newMissionIntent = new Intent(Home.this, AMissionsList.class);
                startActivityForResult(newMissionIntent, MISSION_REQUEST);
                String mission = getIntent().getStringExtra("NEW_MISSION");
                Toast.makeText(getBaseContext(), mission, Toast.LENGTH_LONG).show();
            }
        });

      //  settingsButton = (Button)findViewById(R.id.settingsButton);
        //settingsButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  startActivity(new Intent(Home.this, NewSettingsActivity.class));
            //}
        //});


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
//https://developers.google.com/android/guides/api-client#Starting
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        lat = (TextView) findViewById(R.id.mLatitudeText);
        lon = (TextView) findViewById(R.id.mLongitudeText);

        if (mLastLocation != null) {
            lat.setText(String.valueOf(mLastLocation.getLatitude()));
            lon.setText(String.valueOf(mLastLocation.getLongitude()));
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
        else{
            Toast.makeText(this, "Grr...location isn't working", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

        Toast.makeText(this, "This probably isn't Google Play's fault, but I'm blaming it, anyway", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Grr...Google Play is being irritating...", Toast.LENGTH_LONG).show();
    }


    public static String getCoordinates()
    {
        String blah = latitude + "," + longitude;
        return blah;
    }
}

