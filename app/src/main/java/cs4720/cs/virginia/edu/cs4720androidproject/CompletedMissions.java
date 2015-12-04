package cs4720.cs.virginia.edu.cs4720androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;

public class CompletedMissions extends AppCompatActivity {

    private ArrayAdapter arrayAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_missions);
        String FILENAME = "completed_file";
        Integer[] stuff = new Integer[15];
        String[] things = new String[15];
        for (int i = 0; i < 15 ; i++){
            stuff[i] = 666;
        }

        try {
            FileInputStream fis = openFileInput(FILENAME);
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = fis.read()) != -1) {
                builder.append((char) ch);
                System.out.println(ch);
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
        int j = 0;
        for (int i = 0; i < 15 ; i++){
            if (stuff[i] != 666){
                things[j] = missions_list[stuff[i]];
                j++;
            }
            else{
                break;
            }
        }
        arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                things);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_completed_missions, menu);
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
