package cs4720.cs.virginia.edu.cs4720androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AMissionsList extends AppCompatActivity {

    private String[] missions_list = {"Scale Mount Everest", "Get Bagels", "Pick Apples", "Visit the Rotunda", "Attend Class in Clark", "Go Downtown", "Check Out the Fralin", "Head to Tech", "Watch a Football Game", "See the Mona Lisa", "Meet THE Professor Sherriff", "Tour Monticello", "See Niagara Falls", "The Golden Gate Bridge?", "A Trip To The National Mall"};
    private ListView missionsView;
    private ArrayAdapter arrayAdapter;
    private TextView editView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amissions_list);

        missionsView = (ListView) findViewById(R.id.aMissListView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, missions_list);
        missionsView.setAdapter(arrayAdapter);
        missionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                String value = (String) adapter.getItemAtPosition(position);
                Intent choiceIntent = new Intent(getBaseContext(), Home.class);
                choiceIntent.putExtra("NEW_MISSION", value);
                startActivity(choiceIntent);
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
}
