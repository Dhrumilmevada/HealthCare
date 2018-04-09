package com.example.dhrumil.healthcare.common;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dhrumil on 4/3/2018.
 */

public class DistanceBetweenLocation {

    private double end_location[];
    private double cur_location[];
    private Context mContext;
    private int maxLocation;
    private LocationManager locationManager;

    public DistanceBetweenLocation(Context mContext) {
        this.mContext = mContext;
        maxLocation = 1;
        end_location = new double[2];
        cur_location = new double[2];
    }

    public String getDistance(String location){
        double [] end = getLatLngFromAddress(location);
        Location end_location = new Location("");
        end_location.setLatitude(end[0]);
        end_location.setLongitude(end[1]);

        double [] cur = checkForCurrentLocation();
        if(cur[0] == 0 && cur[1] == 0) {
            return null;
        }
        Location cur_location = new Location("");
        cur_location.setLatitude(cur[0]);
        cur_location.setLongitude(cur[1]);

        float distance = cur_location.distanceTo(end_location)/1000;
        return  String.valueOf(distance);
    }

    public double[] getLatLngFromAddress(String locaton){
        Geocoder mGeoCoder = new Geocoder(mContext);
        try {
            List<Address> addressList = mGeoCoder.getFromLocationName(locaton,maxLocation);
             end_location[0] = addressList.get(0).getLatitude();
             end_location[1]= addressList.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return end_location;
    }

    public double[] checkForCurrentLocation(){
        double temp [] = {0,0};
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return  getCurrentLocation();
        }
        else if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMessageNoGPS();
            return temp;
        }
        return temp;
    }

    private void buildAlertMessageNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Please Turn On Your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
         AlertDialog alert = builder.create();
        alert.show();
    }

    public double[] getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.REQUEST_LOCATION);
        }
        else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                cur_location[0] = location.getLatitude();
                cur_location[1] = location.getLongitude();
            }
            else{
                Toast.makeText(mContext,"Unable to trace your location",Toast.LENGTH_SHORT).show();
            }
        }
        return cur_location;
    }
}
