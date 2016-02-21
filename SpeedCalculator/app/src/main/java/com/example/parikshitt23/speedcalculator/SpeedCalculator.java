package com.example.parikshitt23.speedcalculator;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by parikshitt23 on 20-02-2016.
 */

public class SpeedCalculator implements LocationListener {
    String str;


    @Override
    public void onLocationChanged(Location loc) {
        loc.getLatitude();
        loc.getLongitude();
        Log.e("ASDAS", String.valueOf(loc.getLongitude()));
        Log.e("ASDAS", String.valueOf(loc.getSpeed()));

         str = String.valueOf(loc.getSpeed());


    }

    public String getSpeed(){
        return str;
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
