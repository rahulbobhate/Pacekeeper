package net.stupidiot.pacekeeper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;

public class PaceActivity extends AppCompatActivity implements LocationListener {

    TextView minPaceTxt;
    TextView maxPaceTxt;
    TextView paceTxt;
    double minPace;
    double maxPace;
    MediaPlayer beepSongPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pace);

        minPaceTxt = (TextView)findViewById(R.id.minP);
        maxPaceTxt = (TextView)findViewById(R.id.maxP);
        paceTxt = (TextView)findViewById(R.id.pace);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        minPace = b.getDouble("minPace");
        maxPace = b.getDouble("maxPace");

        File beepSong = findSong(Environment.getExternalStorageDirectory());
        Uri songUri = Uri.parse(beepSong.toString());
        beepSongPlayer = MediaPlayer.create(getApplicationContext(), songUri);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        this.onLocationChanged(null);



    }

    private File findSong(File root) {

        String songPath = "/storage/emulated/0/Download/beep.mp3";
        File beep = new File(songPath);

        return beep;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pace, menu);
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
    public void onLocationChanged(Location location) {
        if(location != null) {
            float speed = location.getSpeed();
            paceTxt.setText("Speed "+location.getSpeed());
            if (speed >= minPace && speed <= maxPace) {
                beepSongPlayer.stop();
            } else {
                beepSongPlayer.start();
            }
        }else{
            minPaceTxt.setText("Minpace "+minPace);
            maxPaceTxt.setText("Maxpace "+maxPace);
            paceTxt.setText("Speed -.-");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
