package com.aseyel.tgbl.tristangaryleyesa.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.BaseActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * Created by Romeo on 2018-01-12.
 */

public class LiquidGPS  {
    private static final String TAG = LiquidGPS.class.getSimpleName();
    public LocationManager mLocationManager;
    public Context mContext;
    public double Latitude;
    public double Longitude;
    public  Location location;
    public FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    public LiquidGPS(Context context) {

        mContext = context;
        mLocationPermissionsGranted = true;
        getDeviceLocation();
    }
    public double getLatitude(){
        return Latitude;
    }
    public double getLongitude(){
        return Longitude;
    }
    public void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);

        try{

            if(mLocationPermissionsGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        try{
                            if(task.isSuccessful()){
                                Location currentLocation = (Location) task.getResult();
                                Latitude = currentLocation.getLatitude();
                                Longitude = currentLocation.getLongitude();
                            }else{
                                Log.d(TAG, "onComplete: current location is null");
                                Toast.makeText(mContext, "unable to get current location", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Log.e(TAG,"Tristan Gary Leyesa "+e );
                        }

                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
        //CameraPosition LastPosition =  CameraPosition.builder().target(new LatLng(Double.parseDouble(String.valueOf(mLiquidGPS.getLatitude())),Double.parseDouble(String.valueOf(mLiquidGPS.getLongitude())))).zoom(30).bearing(0).tilt(45).build();
        //mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(LastPosition));
    }

}
