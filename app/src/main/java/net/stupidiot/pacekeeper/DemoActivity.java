package net.stupidiot.pacekeeper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import net.stupidiot.pacekeeper.R;

import java.io.File;

public class DemoActivity extends Activity
{
    double minPace;
    double maxPace;
    MediaPlayer beepSongPlayer;

    TextView demoSpeed;
     float[] pace;
    TextView demoPace;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        minPace = b.getDouble("minPace");
        maxPace = b.getDouble("maxPace");

        File beepSong = findSong(Environment.getExternalStorageDirectory());
        Uri songUri = Uri.parse(beepSong.toString());
        beepSongPlayer = MediaPlayer.create(getApplicationContext(), songUri);

        NumberPicker np = (NumberPicker)findViewById(R.id.numberPicker1);
        np.setMinValue(1);// restricted number to minimum value i.e 1
        np.setMaxValue(20);// restricked number to maximum value i.e. 31
        np.setWrapSelectorWheel(true);
        np.setValue(10);

          demoSpeed = (TextView)this.findViewById(R.id.speedDemo);
        demoSpeed.setText("Speed : 10 m/s");
         pace = new float[]{(float) (10 / 0.44704)};
        pace[0] = (float) 60/pace[0];
        demoPace = (TextView)this.findViewById(R.id.paceDemo);
        demoPace.setText("Pace is " + pace[0] + " minutes per mile");

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                // TODO Auto-generated method stub

                demoSpeed.setText("Speed : " + newVal + " m/s");
                pace[0] = (float) (newVal / 0.44704);
                pace[0] = (float) 60 / pace[0];
                demoPace.setText("Pace is " + pace[0] + " minutes per mile");

                onLocationChangedSimulator(newVal);


            }
        });


        Log.d("NumberPicker", "NumberPicker");


    }

    private void onLocationChangedSimulator(int newVal) {


        if(newVal >= minPace && newVal <= maxPace){
            Toast.makeText(getApplicationContext(), "Inside If", Toast.LENGTH_SHORT).show();
            if(flag) {
                beepSongPlayer.pause();
                flag = false;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Inside Else", Toast.LENGTH_SHORT).show();
            if(!flag) {
                beepSongPlayer.start();
                flag = true;
            }
        }
    }

    private File findSong(File root) {

        String songPath = "/storage/emulated/0/Download/beep.mp3";
        File beep = new File(songPath);

        return beep;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
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
