package com.example.dhrumil.healthcare.hospital.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dhrumil on 4/5/2018.
 */

public class GpsStateReceiver extends BroadcastReceiver {

    protected Set<GpsStateReceiverListener> listeners;
    protected Boolean gpsOn;
    private LocationManager locationManager;


    public GpsStateReceiver() {
        listeners = new HashSet<GpsStateReceiverListener>();
        gpsOn = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent == null || intent.getExtras() == null)
            return;

        /*if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)){*/
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                gpsOn = true;
            }
            else if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                gpsOn = false;
            }
//        }
        notifyStateToAll();
    }

    private void notifyStateToAll() {
        for(GpsStateReceiverListener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(GpsStateReceiverListener listener) {
        if(gpsOn == null || listener == null)
            return;

        if(gpsOn == true)
            listener.gpsAvailable();
        else
            listener.gpsUnAvailable();
    }

    public void addListener(GpsStateReceiverListener l) {
        listeners.add(l);
        notifyState(l);
    }

    public void removeListener(GpsStateReceiverListener l) {
        listeners.remove(l);
    }


    public interface GpsStateReceiverListener{
        void gpsAvailable();
        void gpsUnAvailable();
    }
}
