package com.run.runtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by chichi on 2016/7/9.
 */
public class LocationReceiver extends BroadcastReceiver {

    private final String TAG="LOCATION RECEIVER";
    @Override
    public void onReceive(Context context, Intent intent) {
        Location loc=intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
        if(loc!=null){
            onLocationReceived(context,loc);
            return;
        }

        if(intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)){
            boolean enabled=intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED,false);
            onProvideEnableChanged(enabled);
        }
    }

    private void onProvideEnableChanged(boolean enabled) {
        Log.d(TAG, "ProviderEnabled:" + (enabled ? "ENABLE" : "DISABLE"));
    }

    private void onLocationReceived(Context context, Location location) {
        Log.d(TAG, "纬度:" + location.getLatitude()+"  经度："+ location.getLongitude());
    }


}
