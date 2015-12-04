package cs4720.cs.virginia.edu.cs4720androidproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.*;
import android.content.*;
import android.widget.Toast;

public class Splash extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    */

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = mSensorManager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            mSensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    //borrowed most of this code from here: http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
    @Override
    public void onSensorChanged(SensorEvent event) {

        //Toast.makeText(this, "The sensor is changing", Toast.LENGTH_LONG).show();
        //Sensor mySensor = event.sensor;

        //if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];


        //Toast.makeText(this, x + " " + y + " " + z, Toast.LENGTH_LONG).show();

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    mSensorManager.unregisterListener(this);
                    startActivity(new Intent(Splash.this, Home.class));
                    this.finish();

                    //int daSpeed = (int)speed;
                    //Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                    //choiceIntent.putExtra("DA_SPEED", daSpeed);
                    //startActivity(choiceIntent);
                    //System.out.println(daSpeed);
                   // this.finish();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
   // }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
