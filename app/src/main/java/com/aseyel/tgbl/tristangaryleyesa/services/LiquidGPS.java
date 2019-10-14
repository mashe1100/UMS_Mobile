package com.aseyel.tgbl.tristangaryleyesa.services;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.BaseActivity;
import com.aseyel.tgbl.tristangaryleyesa.ReadingSummaryActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


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

    private Liquid.POSTApiData mPOSTApiData;

    public LiquidGPS(Context context) {

        mContext = context;
        mLocationPermissionsGranted = true;
        getDeviceLocation();
    }
    public double getLatitude(){ return Latitude; }
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

    public void PostRealtimeData(String Lat, String Lng)throws Exception{
        JSONArray UntransferedData = new JSONArray();

        String type = "0";
        String latitude = "";
        String longitude = "";
        String remark = "";
        String comment = "";
        String accountnumber = "";
        String tag_desciption = "";
        String details = "";
        String Message = "";
        String job_id = "";
        SmsManager mSmsManager = SmsManager.getDefault();
        Cursor result = ReadingModel.GetUntransferdData();

        if(result.getCount() == 0){
            Message = Liquid.User+","+
                    Liquid.Client+","+
                    type+","+
                    Lat +","+
                    Lng +","+
                    remark+","+
                    comment+","+
                    accountnumber+","+
                    tag_desciption+","+
                    details;

            JSONObject Details = new JSONObject();
            Details.put("data_type","location");
            Details.put("message", Message);
            Details.put("accountnumber", accountnumber);
            Details.put("job_id", job_id);

            UntransferedData.put(Details);
        } else{
            while (result.moveToNext()) {
                type = "1";
                latitude = result.getString(2);
                longitude = result.getString(3);
                remark = result.getString(4);
                accountnumber = result.getString(1);
                details = result.getString(5);
                job_id = result.getString(0);
                Message = Liquid.User + "," +
                                Liquid.Client + "," +
                                type + "," +
                                latitude + "," +
                                longitude + "," +
                                remark + "," +
                                comment + "," +
                                accountnumber + "," +
                                tag_desciption + "," +
                                details;

                JSONObject Details = new JSONObject();
                Details.put("data_type","accomplishment");
                Details.put("message", Message);
                Details.put("accountnumber", accountnumber);
                Details.put("job_id", job_id);

                UntransferedData.put(Details);
            }
        }

        // Temporary get last records and send all to server
        Cursor record = ReadingModel.GetRecentData(1);
        if(record.getCount() != 0){
            while (record.moveToNext()) {
                type = "1";
                latitude = record.getString(2);
                longitude = record.getString(3);
                remark = record.getString(4);
                accountnumber = record.getString(1);
                details = record.getString(5);
                job_id = record.getString(0);
                Message = Liquid.User + "," +
                        Liquid.Client + "," +
                        type + "," +
                        latitude + "," +
                        longitude + "," +
                        remark + "," +
                        comment + "," +
                        accountnumber + "," +
                        tag_desciption + "," +
                        details;

                JSONObject Details = new JSONObject();
                Details.put("data_type","accomplishment");
                Details.put("message", Message);
                Details.put("accountnumber", accountnumber);
                Details.put("job_id", job_id);

                UntransferedData.put(Details);
            }
        }


        for (int i=0; i<UntransferedData.length(); i++){
            new GPSCloudPosting().execute(UntransferedData.getJSONObject(i));
        }
    }


    public class GPSCloudPosting extends AsyncTask<JSONObject,Void,Void> {

        boolean result = false;
        String return_data = "";
        String Message = "";
        String job_id = "";
        String accountnumber = "";
        String data_type = "";

        String today = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date());
        SmsManager mSmsManager = SmsManager.getDefault();

        public void cancel(){
            this.cancel(true);
        }
        @Override
        protected Void doInBackground(JSONObject... json) {
            try{

                HttpHandler sh = new HttpHandler();
                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Realtime.php");

                Message = json[0].getString("message");
                job_id = json[0].getString("job_id");
                accountnumber = json[0].getString("accountnumber");
                data_type = json[0].getString("data_type");

                JSONObject dataObject = new JSONObject();
                dataObject.put("receiver_group","1");
                dataObject.put("senttime", today);
                dataObject.put("contact", Build.SERIAL); // serial muna -> dapat phone number
                dataObject.put("details", Message);
                dataObject.put("username",Liquid.Username);
                dataObject.put("password",Liquid.Password);
                dataObject.put("deviceserial", Build.SERIAL);
                dataObject.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(dataObject));
                String jsonStr = sh.makeServicePostCallWithTimeout(mPOSTApiData.API_Link,dataObject);
                Log.i(TAG, String.valueOf(jsonStr));
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

                switch (data_type){
                    case "accomplishment":
                        ReadingModel.UpdateTransferStatus(job_id, accountnumber);
                        break;
                    default:
                }
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }else{
                Log.i(TAG,return_data + "FALSE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);

                String SENT = "SMS_SENT";
                String DELIVERED = "SMS_DELIVERED";
                PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, new Intent(SENT), 0);
                PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,new Intent(DELIVERED), 0);

                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0,Intent arg1)
                    {
                        switch(getResultCode()) {
                            case Activity.RESULT_OK:
                                switch (data_type) {
                                    case "accomplishment":
                                        ReadingModel.UpdateTransferStatus(job_id, accountnumber);
                                        break;
                                    default:
                                }
                                Log.e("sms check","true");
                                break;
                            default:
                                Log.e("sms check","false");
                        }
                    }
                }, new IntentFilter(SENT));
                // ---when the SMS has been delivered---
                mContext.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0, Intent arg1)
                    {
                        switch(getResultCode()) {
                            case Activity.RESULT_OK:
                                Log.e("sms check","sent");
                                break;
                            case Activity.RESULT_CANCELED:
                                Log.e("sms check","not sent");
                                break;
                        }
                    }
                }, new IntentFilter(DELIVERED));


                String receiver = Liquid.ServerNumberSmart;
                if(Liquid.Client.matches("ileco2"))
                    receiver = Liquid.ServerNumberGlobe;

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(receiver, null,Message,sentPI, deliveredPI);
            }
        }
    }

}
