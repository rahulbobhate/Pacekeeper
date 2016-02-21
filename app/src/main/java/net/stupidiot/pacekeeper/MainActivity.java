package net.stupidiot.pacekeeper;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.net.Uri;
import android.net.rtp.AudioStream;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MIN_PACE = 4;
    private static final int MAX_PACE = 15;
    double minPace;
    double maxPace;

    private Activity activity;
    private EditText maxPaceText;
    private EditText minPaceText;
    private Button startMonitorButton;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        minPaceText = (EditText) findViewById(R.id.minPace);
        maxPaceText = (EditText) findViewById(R.id.maxPace);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        startMonitorButton = (Button) findViewById(R.id.monitor_start_button);

        startMonitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 minPace = Double.parseDouble(minPaceText.getText().toString());
                 maxPace = Double.parseDouble(maxPaceText.getText().toString());

                if (minPace >= maxPace) {
                    Toast.makeText(getApplicationContext(), "Min pace is less than max pace. Please select correct values", Toast.LENGTH_LONG).show();
                } else {
                    if(!toggleButton.isChecked()){
                        Intent startServiceIntent = new Intent(activity, PaceActivity.class);
                        startServiceIntent.putExtra("minPace", minPace);
                        startServiceIntent.putExtra("maxPace", maxPace);
                        startActivity(startServiceIntent);
                    }else{
                        Intent startServiceIntent = new Intent(activity, DemoActivity.class);
                        startServiceIntent.putExtra("minPace", minPace);
                        startServiceIntent.putExtra("maxPace", maxPace);
                        startActivity(startServiceIntent);
                    }

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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