package net.stupidiot.pacekeeper;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PacekeeperService extends Service implements LocationListener
{
    private static final String TAG = PacekeeperService.class.toString();

    public PacekeeperService()
    {

    }

    int minPace;
    int maxPace;
    MediaPlayer beepSongPlayer;
    @Override
    public void onCreate()
    {
        Log.d(TAG, "Service created");
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
    public void onDestroy()
    {
        Log.d(TAG, "Service destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "Service started");
        if(intent != null) {
            Bundle b = intent.getExtras();
            minPace = b.getInt("minPace");
            maxPace = b.getInt("maxPace");
        }
        File beepSong = findSong(Environment.getExternalStorageDirectory());
        Uri songUri = Uri.parse(beepSong.toString());
        beepSongPlayer = MediaPlayer.create(getApplicationContext(), songUri);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            float speed = location.getSpeed();

            if (speed >= minPace && speed <= maxPace) {
                beepSongPlayer.stop();
            } else {
                beepSongPlayer.start();
            }
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
