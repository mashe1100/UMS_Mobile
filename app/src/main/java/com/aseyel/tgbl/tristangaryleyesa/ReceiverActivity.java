package com.aseyel.tgbl.tristangaryleyesa;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReceiverActivity extends AppCompatActivity {
    private static final String TAG = ReceiverActivity.class.getSimpleName();
    private static final int SMS_PERMISSION_CODE = 0;
    private static final String DEVICE_DEFAULT_SMS_PACKAGE_KEY = "com.aseyel.tgbl.tristangaryleyesa.deviceDefaultSmsPackage";
    private static final String INVALID_PACKAGE = "invalid_package";
    Liquid.POSTApiData PostApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        init();
    }

    private void init(){
        //setUpViews();
        setDeviceDefaultSmsPackage(getPackageName());
        saveDeviceDefaultSmsPackage();

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }
        final Handler mHandler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = 0;
                while (x == 0) {
                    try {
                        Thread.sleep(10000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                // TODO Auto-generated method stub
                                new GPSPosting().execute();
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

    private void saveDeviceDefaultSmsPackage() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (hasNoPreviousSmsDefaultPackage(preferences)) {
            String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(this);
            preferences.edit().putString(DEVICE_DEFAULT_SMS_PACKAGE_KEY, defaultSmsPackage).apply();
        }
    }

    private boolean hasNoPreviousSmsDefaultPackage(SharedPreferences preferences) {
        return !preferences.contains(DEVICE_DEFAULT_SMS_PACKAGE_KEY);
    }

    private void setUpViews() {
       /* findViewById(R.id.set_as_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeviceDefaultSmsPackage(getPackageName());
            }
        });

        findViewById(R.id.restore_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeviceDefaultSmsPackage(getPreviousSmsDefaultPackage());
            }
        });*/
    }

    private void setDeviceDefaultSmsPackage(String packageName) {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
        startActivity(intent);
    }

    private String getPreviousSmsDefaultPackage() {
        return getPreferences(MODE_PRIVATE).getString(DEVICE_DEFAULT_SMS_PACKAGE_KEY, INVALID_PACKAGE);
    }

    private String GetSMS(){
        String msgData = "[";
        int thread_length = 0;
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                long id = cursor.getLong(0);
                long thread = cursor.getLong(1);
                String date = cursor.getString(3);
                thread_length++;
                msgData+="{";
                for(int idx=0;idx<cursor.getColumnCount();idx++)
                {
                    if(idx == (cursor.getColumnCount() -1)){
                        msgData += "  \"" + cursor.getColumnName(idx) + "\":\"" + cursor.getString(idx)+"\"";
                     }else{
                        msgData += "  \"" + cursor.getColumnName(idx) + "\":\"" + cursor.getString(idx)+"\",";
                    }

                }
                // use msgData
                if(thread_length == (cursor.getCount())){
                    msgData += "}";
                }else{
                    msgData += "},";
                }
                try {
                    Thread.sleep(0);
                    getContentResolver().delete(Uri.parse("content://sms/"+ id), null,  null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }
        msgData += "]";
        Log.i(TAG,msgData);
        return msgData;
    }
    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }
    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(ReceiverActivity.this,
                android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(ReceiverActivity.this,
                        android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ReceiverActivity.this, android.Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(ReceiverActivity.this, new String[]{android.Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
        GetSMS();
    }

    public class GPSPosting extends AsyncTask<Void,Void,Void> {
        String Client = "";
        String Details = "";
        boolean result = false;
        String return_data = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //865,iselco2,0,17.1415353166667,121.882277033333,,,,, (Structure)
            Client  = Liquid.Client;
            //Details = Build.SERIAL + "," + Liquid.Client + "," + "0" + "," + Latitude + ","+ Longitude + ",,,,,";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                HttpHandler sh = new HttpHandler();
                PostApi = new Liquid.POSTApiData("fmts/php/api/Realtime.php");
                JSONArray dataArray = new JSONArray(GetSMS());

                if(dataArray.length() == 0){
                    return null;
                }

                for(int a = 0; a < dataArray.length(); a++){
                    JSONObject c = dataArray.getJSONObject(a);
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("receiver_group",c.getString("_id"));
                    dataObject.put("senttime",Liquid.milliSecondtToDate(c.getString("date_sent")));
                    dataObject.put("contact",c.getString("address"));
                    dataObject.put("details",c.getString("body"));
                    dataObject.put("username",Liquid.Username);
                    dataObject.put("password",Liquid.Password);
                    dataObject.put("deviceserial", Build.SERIAL);
                    dataObject.put("sysid",Liquid.User);
                    Log.i(TAG, String.valueOf(dataObject));
                    String jsonStr = sh.makeServicePostCall(PostApi.API_Link,dataObject);
                    Log.i(TAG, String.valueOf(jsonStr));
                    return_data = jsonStr;
                    JSONObject response = Liquid.StringToJsonObject(jsonStr);
                    if (response.getString("result").equals("false")) {
                        result = false;
                    } else {
                        result = true;
                    }
                }
                /*JSONObject dataObject = new JSONObject();
                dataObject.put("receiver_group","1");
                dataObject.put("senttime",Liquid.currentDateTime());
                //dataObject.put("contact",MyPhoneNumber);
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
                }*/

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
}
