package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class GPSActivity extends AppCompatActivity {
    private static final String TAG = GPSActivity.class.getSimpleName();
    private String Service_Type = "3";
    private String MyPhoneNumber = "";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private String Latitude = "";
    private String Longitude = "";
    LiquidGPS mGPS;
    Liquid.POSTApiData PostApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        init();
    }

    private void init() {
        mGPS = new LiquidGPS(this);
        permissions();
        final Handler mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = 0;
                getDeviceLocation();
                while (x == 0) {
                    try {
                        Thread.sleep(15000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                if(!Liquid.CheckGPS(GPSActivity.this)){
                                    Liquid.displayPromptForEnablingGPS(GPSActivity.this);
                                }else{
                                    getDeviceLocation();
                                }

                                // Write your code here to update the UI.
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(TAG,"Tristan Gary Leyesa",e);
                    }
                }
            }
        }).start();


    }

    private void permissions(){

        mLocationPermissionsGranted = true;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneNumber = tMgr.getLine1Number();
    }
    private class GPSPosting extends AsyncTask<Void,Void,Void>{


        String Client = "";
        String Details = "";
        boolean result = false;
        String return_data = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //865,iselco2,0,17.1415353166667,121.882277033333,,,,, (Structure)
            Client  = Liquid.Client;
            Details = Build.SERIAL + "," + Liquid.Client + "," + "0" + "," + Latitude + ","+ Longitude + ",,,,,";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                HttpHandler sh = new HttpHandler();
                PostApi = new Liquid.POSTApiData("fmts/php/api/Realtime.php");
                JSONObject dataObject = new JSONObject();
                dataObject.put("receiver_group","1");
                dataObject.put("senttime",Liquid.currentDateTime());
                dataObject.put("contact",MyPhoneNumber);
                dataObject.put("details",Details);
                dataObject.put("username",Liquid.Username);
                dataObject.put("password",Liquid.Password);
                dataObject.put("deviceserial", Build.SERIAL);
                dataObject.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(dataObject));
                String jsonStr = sh.makeServicePostCall(PostApi.API_Link,dataObject);
                return_data = jsonStr;

                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                if (response.getString("result").equals("false")) {
                    result = false;

                } else {
                    result = true;
                }

            }catch(Exception e){
                Log.e(TAG,"Tristan Gary Leyesa : ",e);
                result = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(result){
                Log.i(TAG,return_data + "TRUE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }else{
                Log.i(TAG,return_data + "FALSE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{

            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            Latitude = String.valueOf(currentLocation.getLatitude());
                            Longitude = String.valueOf(currentLocation.getLongitude());

                            new GPSPosting().execute();
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
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
