package com.aseyel.tgbl.tristangaryleyesa;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.adapter.SectionsPageAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabCloudFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabLocalFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabMenuFragment;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;
import com.aseyel.tgbl.tristangaryleyesa.model.LiquidModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.SettingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidReading;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private MenuItem searchMenuItem;
    private Fragment Cloud, Local, MenuSettings;
    private int SelectedTab = 0;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private String Latitude = "";
    private String Longitude = "";
    private static final int SMS_PERMISSION_CODE = 0;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String DEVICE_DEFAULT_SMS_PACKAGE_KEY = "com.aseyel.tgbl.tristangaryleyesa.deviceDefaultSmsPackage";
    private static final String INVALID_PACKAGE = "invalid_package";
    private LiquidGPS mLiquidGPS;
    private Liquid.POSTApiData mPOSTApiData;
    private Activity activity = this;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    DisplayMetrics displaymetrics;

    static int exit_clicks = 0;
    static String result = "", attendance_action = "";
    static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //Update 11/16/2018

        SplashActivity.mDatabaseHelper.UpdateDatabase();
        initViews();
        initSMS();
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_cloud);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_global_menu_direct_blue);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SelectedTab = tab.getPosition();
                switch (SelectedTab) {
                    case 0:

                        break;
                    case 1:
                        //((TabLocalFragment) Local).GetLocalJobOrder(false,"");
                        break;
                    case 2:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        exit_clicks += 1;
        handler.removeCallbacks(reset_exit_call);
        handler.postDelayed(reset_exit_call, 800);

        if (exit_clicks >= 2) {
            finish();
        } else {
            Toast.makeText(activity, "Hit the back button again to close the application", Toast.LENGTH_SHORT).show();
        }
        ;
    }

    private Runnable reset_exit_call = new Runnable() {
        public void run() {
            exit_clicks = 0;

        }
    };

    private void initSMS() {
        setDeviceDefaultSmsPackage(getPackageName());
        saveDeviceDefaultSmsPackage();

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }

        // this is for tracking the device always point to point
        final Handler mHandler = new Handler();
        if(Liquid.verifyStoragePermissions(activity))
            getDeviceLocation();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = 0;
                while (x == 0) {
                    try {
                        Thread.sleep(120000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(Liquid.verifyStoragePermissions(activity))
                                    getDeviceLocation();

                                // TODO Auto-generated method stub
                                //new ReceiverActivity.GPSPosting().execute();
                                // Write your code here to update the UI.
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(TAG, "Tristan Gary Leyesa", e);
                    }
                }
            }
        }).start();

        final Handler mHandler2 = new Handler();

        /*if(isNetworkAvailable()){
            UploadBackground();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = 0;
                while (x == 0) {
                    try {
                        Thread.sleep(30000);
                        mHandler2.post(new Runnable() {
                            @Override
                            public void run() {
                                if(isNetworkAvailable()){
                                    UploadBackground();
                                }
                                // TODO Auto-generated method stub
                                //new ReceiverActivity.GPSPosting().execute();
                                // Write your code here to update the UI.
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(TAG,"Tristan Gary Leyesa",e);
                    }
                }
            }
        }).start();*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwrokInfo = mConnectivityManager.getActiveNetworkInfo();
        return activeNetwrokInfo != null && activeNetwrokInfo.isConnected();
    }

    public void UploadBackground() {
        new BackgroundUpload().execute();
        //new BackgroundPictureUpload().execute();

    }

    public class BackgroundPictureUpload extends AsyncTask<Void, Void, Void> {
        JSONObject postData;
        String data;
        String dataPicture;
        String dataLogs;
        String dataMeterNotInList;
        JSONObject response;
        JSONArray dataArray;
        JSONArray dataArrayPicture;
        JSONArray dataLogsArray;
        JSONArray dataArrayMeterNotInList;
        JSONArray imageArray;
        boolean result_status = false;
        String jsonStr;
        String AccountNumber = "";
        String Category;
        String imageData;
        int progress = 0;
        int total = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dataLogs = postData.getString("get_picture_data");
                dataMeterNotInList = postData.getString("meter_not_in_list");
                dataLogsArray = new JSONArray(dataLogs);
                dataArrayMeterNotInList = new JSONArray(dataMeterNotInList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpHandler sh = new HttpHandler();
                Liquid.POSTAuditApiData mPOSTAuditApiData;
                //Making a request to url and getting response

                //if(dataArray.length() == 0){
                //return 2;
                //}
                for (int a = 0; a < 1; a++) {
                    JSONObject dataObject = new JSONObject(dataLogsArray.getString(a));
                    if (!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))) {
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;

                        mImages = Liquid.getDiscPicture(AccountNumber, Subfolder);

                        if (!mImages.exists() && !mImages.mkdirs()) {

                        } else {
                            listFile = mImages.listFiles();
                            for (int e = 0; e < listFile.length; e++) {
                                JSONArray final_image_result = new JSONArray();
                                JSONObject final_image_response = new JSONObject();
                                JSONObject data = new JSONObject();

                                data.put("Client", Liquid.Client);
                                data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                data.put("Filename", listFile[e].getName());
                                data.put("service_type", "MeterReading");
                                data.put("date", String.valueOf(dataObject.get("BillMonth")) + String.valueOf(dataObject.get("BillYear")));

                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

                                jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, imageArray.getJSONObject(0));
                                Log.i(TAG, jsonStr);
                                response = Liquid.StringToJsonObject(jsonStr);

                                if (response.getString("result").equals("false")) {
                                    Liquid.ErrorUpload.put(dataObject);
                                } else {
                                    Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));


                                }
                            }
                        }
                    }
                }


                for (int a = 0; a < 1; a++) {
                    JSONObject dataObject = new JSONObject(dataArrayMeterNotInList.getString(a));

                    if (!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))) {
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;

                        mImages = Liquid.getDiscPicture(AccountNumber + "_audit", Subfolder);
                        if (!mImages.exists() && !mImages.mkdirs()) {

                        } else {
                            listFile = mImages.listFiles();


                            for (int e = 0; e < listFile.length; e++) {
                                JSONArray final_image_result = new JSONArray();
                                JSONObject final_image_response = new JSONObject();
                                JSONObject data = new JSONObject();

                                data.put("Client", Liquid.Client);
                                data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                data.put("Filename", listFile[e].getName());
                                data.put("service_type", "Audit");
                                data.put("date", Liquid.dateChangeFormat(Liquid.currentDate(), "yyyy-MM-dd", "MMyyyy"));

                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

                                jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, imageArray.getJSONObject(0));
                                Log.i(TAG, jsonStr);
                                response = Liquid.StringToJsonObject(jsonStr);

                                if (response.getString("result").equals("false")) {
                                    Liquid.ErrorUpload.put(dataObject);
                                } else {
                                    Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));


                                }
                            }
                        }
                    }
                }


            } catch (JSONException e) {
                Log.e(TAG, "Error :", e);
                //JSON Problem

            } catch (Exception e) {
                //An error has occured
                Log.e(TAG, "Error :", e);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i(TAG, "Successfully Upload");
        }
    }

    public class BackgroundUpload extends AsyncTask<Void, Void, Void> {
        JSONObject postData;
        String data;
        String dataPicture;
        String dataLogs;
        String dataMeterNotInList;
        JSONArray dataArray;
        JSONArray dataArrayPicture;
        JSONArray dataArrayLogs;
        JSONArray dataArrayMeterNotInList;
        boolean result_status = false;
        int progress = 0;
        int total = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<HashMap<String, String>> data_read = LiquidModel.GetLocalJobOrder("");
            for (int a = 0; a < 1; a++) {
                Log.i(TAG, "Start Upload....");
                JSONObject postData = LiquidReading.UploadReading(data_read.get(a).get("id"));
                try {
                    data = postData.getString("data");
                    dataPicture = postData.getString("picture");
                    dataLogs = postData.getString("logs");
                    dataMeterNotInList = postData.getString("meter_not_in_list");
                    dataArray = new JSONArray(data);
                    dataArrayPicture = new JSONArray(dataPicture);
                    dataArrayLogs = new JSONArray(dataLogs);
                    dataArrayMeterNotInList = new JSONArray(dataMeterNotInList);
                    try {
                        HttpHandler sh = new HttpHandler();
                        if (dataArray.length() == 0) {
                            //No Data
                            //return 2;
                        } else {
                            Log.i(TAG, data);
                            //Making a request to url and getting response
                            for (int b = 0; b < 1; b++) {
                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Reading.php");
                                JSONObject dataObject = new JSONObject(dataArray.getString(b));
                                //Log.i(TAG, String.valueOf(dataObject));
                                String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                                if (response.getString("result").equals("false")) {
                                    Liquid.ErrorUpload.put(dataObject);
                                } else {

                                    result_status = ReadingModel.UpdateUploadStatus(dataObject.getString("job_id").toString(), dataObject.getString("AccountNumber").toString());
                                }
                            }
                        }
                        if (dataArrayLogs.length() == 0) {
                            //No Data
                            //return 2;
                        } else {
                            for (int c = 0; c < 1; c++) {
                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingLogs.php");
                                JSONObject dataObject = new JSONObject(dataArrayLogs.getString(c));
                                String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                                Log.i(TAG, "Logs" + jsonStr);
                                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                                Log.i(TAG, "Logs" + response);
                                if (response.getString("result").equals("false")) {
                                    Liquid.ErrorUpload.put(dataObject);
                                } else {
                                    result_status = ReadingModel.UpdateUploadStatusLogs(dataObject.getString("row_id").toString());
                                    Log.i(TAG, String.valueOf(result_status));
                                }
                            }
                        }

                        if (dataArrayPicture.length() == 0) {
                            //No Data
                            //return 2;
                        } else {


                            for (int d = 0; d < 1; d++) {
                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingPicture.php");
                                JSONObject dataObject = new JSONObject(dataArrayPicture.getString(d));
                                String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                                Log.i(TAG, "Picture" + jsonStr);
                                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                                Log.i(TAG, "Picture" + response);
                                if (response.getString("result").equals("false")) {
                                    Liquid.ErrorUpload.put(dataObject);
                                } else {
                                    result_status = ReadingModel.UpdateUploadStatusPicture(dataObject.getString("row_id").toString());
                                    Log.i(TAG, String.valueOf(result_status));
                                }
                            }
                        }

                        if (dataArrayMeterNotInList.length() == 0) {
                            //No Data
                            //return 2;
                        } else {


                            for (int e = 0; e < 1; e++) {
                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/NewMeterNotInList.php");
                                JSONObject dataObject = new JSONObject(dataArrayMeterNotInList.getString(e));
                                String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                                Log.i(TAG, jsonStr);
                                Log.i(TAG, "MeterNotInList" + jsonStr);
                                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                                JSONObject response2 = new JSONObject(response.getString("Data"));
                                Log.i(TAG, "MeterNotInList" + response2);
                                if (response2.getString("result").equals("false")) {
                                    Liquid.ErrorUpload.put(dataObject);
                                } else {
                                    result_status = ReadingModel.UpdateUploadStatusMeterNotInList(dataObject.getString("row_id").toString());
                                    Log.i(TAG, String.valueOf(result_status));
                                }
                            }
                        }


                    } catch (JSONException e) {
                        Log.e(TAG, "Error :", e);
                        //JSON Problem
                    } catch (Exception e) {
                        //An error has occured
                        Log.e(TAG, "Error :", e);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i(TAG, "Successfully Upload");
        }
    }

    private void getDeviceLocation() {
        try {
            Log.d(TAG, "getDeviceLocation: getting the devices current location");
            mLocationPermissionsGranted = true;
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            try {

                if (mLocationPermissionsGranted) {

                    final Task location = mFusedLocationProviderClient.getLastLocation();

                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                try {
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();
                                    Latitude = String.valueOf(currentLocation.getLatitude());
                                    Longitude = String.valueOf(currentLocation.getLongitude());
                                    new GPSPosting().execute();
                                } catch (Exception e) {
                                    Log.e(TAG, "Error", e);
                                }


                            } else {
                                Log.d(TAG, "onComplete: current location is null");
                            }
                        }
                    });
                }
            } catch (SecurityException e) {
                Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Error", e);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error ", e);
        }


        //CameraPosition LastPosition =  CameraPosition.builder().target(new LatLng(Double.parseDouble(String.valueOf(mLiquidGPS.getLatitude())),Double.parseDouble(String.valueOf(mLiquidGPS.getLongitude())))).zoom(30).bearing(0).tilt(45).build();
        //mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(LastPosition));
    }

    public class GPSPosting extends AsyncTask<Void, Void, Void> {

        String Client = "";
        String Details = "";
        boolean result = false;
        String return_data = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //865,iselco2,0,17.1415353166667,121.882277033333,,,,, (Structure)
            Client = Liquid.Client;
            //Details = Build.SERIAL + "," + Liquid.Client + "," + "0" + "," + Latitude + ","+ Longitude + ",,,,,";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
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
                mLiquidGPS = new LiquidGPS(MainActivity.this);

                if (result.getCount() == 0) {
//                                    Message =
//                                    Liquid.User+","+
//                                    Liquid.Client+","+
//                                    type+","+
//                                            Latitude +","+
//                                    Longitude +","+
//                                    remark+","+
//                                    comment+","+
//                                    accountnumber+","+
//                                    tag_desciption+","+
//                                    details;
//                    mSmsManager.sendTextMessage("+639064783858",null,Message,null,null);

                    this.result = true;
                } else {
                    while (result.moveToNext()) {
                        type = "1";
                        latitude = result.getString(2);
                        longitude = result.getString(3);
                        remark = result.getString(4);
                        accountnumber = result.getString(1);
                        details = result.getString(5);
                        job_id = result.getString(0);
                        Message =
                                Liquid.User + "," +
                                        Liquid.Client + "," +
                                        type + "," +
                                        latitude + "," +
                                        longitude + "," +
                                        remark + "," +
                                        comment + "," +
                                        accountnumber + "," +
                                        tag_desciption + "," +
                                        details;
                        mSmsManager.sendTextMessage("+639064783858", null, Message, null, null);
                        ReadingModel.UpdateTransferStatus(job_id, accountnumber);
                    }
                }
                //"user,client,type,latitude,longitude,remark,comment,accountnumber,tag_desciption,details"


                Log.i(TAG, "LOCATION: " + Message);
            } catch (Exception e) {
                Log.e(TAG, "Tristan Gary Leyesa : ", e);
                result = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result) {
                Log.i(TAG, return_data + "TRUE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);

                new GPSCloudPosting().execute();
            }else{
                Log.i(TAG,return_data + "FALSE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }
        }
    }

    public class GPSCloudPosting extends AsyncTask<Void,Void,Void> {

        String Client = "";
        String Details = "";
        boolean result = false;
        String return_data = "";

        String type = "0";
        String remark = "";
        String comment = "";
        String accountnumber = "";
        String tag_desciption = "";
        String details = "";
        String Message = "";
        SmsManager mSmsManager = SmsManager.getDefault();

        String today = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date());

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
                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Realtime.php");

                Message =
                        Liquid.User+","+
                                Liquid.Client+","+
                                type+","+
                                Latitude +","+
                                Longitude +","+
                                remark+","+
                                comment+","+
                                accountnumber+","+
                                tag_desciption+","+
                                details;

                TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                }
                String mPhoneNumber = tMgr.getLine1Number();

                JSONObject dataObject = new JSONObject();

                dataObject.put("receiver_group","1");
                dataObject.put("senttime", today);
                dataObject.put("contact",Build.SERIAL); // serial muna -> dapat phone number
                dataObject.put("details", Message);
                dataObject.put("username",Liquid.Username);
                dataObject.put("password",Liquid.Password);
                dataObject.put("deviceserial", Build.SERIAL);
                dataObject.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(dataObject));
                String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link,dataObject);
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
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }else{
                Log.i(TAG,return_data + "FALSE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);

                mSmsManager.sendTextMessage("+639064783858",null,Message,null,null);
            }
        }
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

    private void setDeviceDefaultSmsPackage(String packageName) {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
        startActivity(intent);
    }

    private String getPreviousSmsDefaultPackage() {
        return getPreferences(MODE_PRIVATE).getString(DEVICE_DEFAULT_SMS_PACKAGE_KEY, INVALID_PACKAGE);
    }

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
        return ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);

    }
    private void GetUserDetails(){
        Cursor result = AccountModel.GetLoginAccount();
        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                Liquid.UserFullname = result.getString(2).toString().toUpperCase();

            }
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }
    private void getScreenSize(){
        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Liquid.screenHeight = displaymetrics.heightPixels;
        Liquid.screenWidth = displaymetrics.widthPixels;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {

                switch(SelectedTab){
                    case 0:
                        ((TabCloudFragment) Cloud).GetJobOrder(query);
                        break;
                    case 1:
                        ((TabLocalFragment) Local).GetLocalJobOrder(false,query);
                        break;
                    case 2:

                        break;
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );
        return true;
    }

    private void initViews(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //Set up the ViewPager with the sections adapters
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        Liquid.verifyStoragePermissions(this);
//        checkLocationPermission();
        GetUserDetails();
        getScreenSize();
        Liquid.GetSettings();
    }

    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Location Permision")
                        .setMessage("Using UMS Mobile, We need to access the GPS function.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    private void setupViewPager(ViewPager viewPager){
        Cloud =  new TabCloudFragment();
        Local =  new TabLocalFragment();
        MenuSettings =  new TabMenuFragment();
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragement(Cloud, "");
        adapter.addFragement(Local, "");
        adapter.addFragement(MenuSettings,  "");
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    MainActivity.this.startActivity(i);
                }
            });
        }
    }







}
