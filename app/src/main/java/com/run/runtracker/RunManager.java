package com.run.runtracker;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by chichi on 2016/7/8.
 */
public class RunManager {

    private Context mApp;
    private static final String ACTION_LOCATION = "com.run.runtracker.ACTION_LOCATION";
    private static RunManager sRunmanager;
    private LocationManager mLocationManager;

    public static RunManager getRunManager(Context c) {
        if (sRunmanager == null) {
            sRunmanager = new RunManager(c.getApplicationContext());
        }
        return sRunmanager;
    }

    private RunManager(Context c) {
        this.mApp = c;
        mLocationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
    }

    private PendingIntent getPendingIntent(boolean shouldCreate) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flag = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        return PendingIntent.getBroadcast(mApp, 0, broadcast, flag);
    }

    public void startLoactionUpdates() {
        String provider = LocationManager.GPS_PROVIDER;
        Location lastKnown=mLocationManager.getLastKnownLocation(provider);
        if(lastKnown!=null){
            lastKnown.setTime(System.currentTimeMillis());
            broadcastLaction(lastKnown);
        }

        PendingIntent pi = getPendingIntent(true);
        if (ActivityCompat.checkSelfPermission(mApp, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mApp, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(provider, 0, 0, pi);

    }

    private void broadcastLaction(Location lastKnown) {
        Intent broadcast=new Intent(ACTION_LOCATION);
        broadcast.putExtra(LocationManager.KEY_LOCATION_CHANGED,lastKnown);
        mApp.sendBroadcast(broadcast);
    }

    public void stopLocationUpdates(){
        PendingIntent pi=getPendingIntent(false);
        if(pi!=null){
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    public boolean isTrackingRun(){
        return getPendingIntent(false)!=null;
    }
}
