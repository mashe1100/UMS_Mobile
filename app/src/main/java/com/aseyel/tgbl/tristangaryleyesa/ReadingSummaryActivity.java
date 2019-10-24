package com.aseyel.tgbl.tristangaryleyesa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.SurveyModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidPrintBill;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ReadingSummaryActivity extends BaseFormActivity {
    private final String TAG = ReadingSummaryActivity.class.getSimpleName();
    private TextView txtAmountDue;
    private TextView txtRemarks;
    private TextView txtStatus;
    private TextView txtConsumption;
    private Button btnSubmit;
    private String reading_remarks;
    private LiquidGPS mLiquidGPS;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private String Latitude = "0";
    private String Longitude = "0";
    private ProgressDialog pDialog;
    private boolean isRequestingCoordinates = false;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_summary);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){

        pDialog = new ProgressDialog(this);
        txtAmountDue = (TextView) findViewById(R.id.txtAmountDue);
        txtRemarks = (TextView) findViewById(R.id.txtRemarks);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtConsumption = (TextView) findViewById(R.id.txtConsumption);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QuenedForSave();
//                if(Liquid.r_latitude.equals("0") || Liquid.r_latitude.equals("0.0")){
//                    getDeviceLocation();
//                    Liquid.r_latitude = Latitude;
//                    Liquid.r_longitude = Longitude;
//                    Liquid.ShowMessage(ReadingSummaryActivity.this,"There is no coordinates detected!");
//                }else{
                    finalSave();
//                }

            }
        });
        reading_remarks = Liquid.reading_remarks;
        if(Liquid.reading_remarks.equals("")){
            reading_remarks = "NORMAL CONSUMPTION";
        }
        switch(Liquid.Client){
            case "ileco2":
                txtAmountDue.setText("Amount Due : P "+ LiquidBilling.total_amount_due);
                txtRemarks.setText("Remarks : "+ Liquid.Remarks);
                txtStatus.setText("Status : "+ reading_remarks);
                txtConsumption.setText(Liquid.Present_Consumption + " kWh");
                break;
            case "baliwag_wd":
                txtAmountDue.setText("Amount Due : P "+ LiquidBilling.total_amount_due);
                txtRemarks.setText("Remarks : "+ Liquid.Remarks);
                txtStatus.setText("Status : "+ reading_remarks);
                txtConsumption.setText(Liquid.Present_Consumption + " Cubic Meter");
                break;
            default:
                    txtAmountDue.setText("Amount Due : P "+ LiquidBilling.total_amount_due);
                    txtRemarks.setText("Remarks : "+ Liquid.Remarks);
                    txtStatus.setText("Status : "+ reading_remarks);
                    txtConsumption.setText(Liquid.Present_Consumption + " kWh");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                 //QuenedForSave();
                 finalSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void finalSave(){
        int delay = 0;
        if(Double.parseDouble(Latitude) == 0.0) {
            Handler.postDelayed(CoodinatesChecker,500);
            getDeviceLocation();
            delay = 10000;

            pDialog = new ProgressDialog(ReadingSummaryActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        isRequestingCoordinates = true;
        Handler.postDelayed(DisplaySaveDialog,delay);
    }

    public static final Handler Handler = new Handler();
    private final Runnable DisplaySaveDialog = new Runnable() {
        public void run() {
            isRequestingCoordinates = false;
            pDialog.dismiss();

            Liquid.r_latitude = Latitude;
            Liquid.r_longitude = Longitude;

            switch (Liquid.Client){
                case "more_power":
                    //SaveSurveyData();
                    QuenedForSave();
                    break;
                default:
                    QuenedForSave();
                    break;
            }

        }
    };
    private final Runnable CoodinatesChecker = new Runnable() {
        public void run() {
            if(Double.parseDouble(Latitude) != 0.0){
                Handler.removeCallbacks(DisplaySaveDialog);
                Handler.post(DisplaySaveDialog);
            }else
                if(isRequestingCoordinates)
                    Handler.postDelayed(CoodinatesChecker,500);
        }
    };

    public void QuenedForSave() {

      if(Liquid.save_only){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result = false;
                            boolean result_logs = false;
                            result = Liquid.SaveReading();
                            result_logs = Liquid.SaveReadingLogs();

                            if (result) {
                                Liquid.showReadingDialogNext(ReadingSummaryActivity.this, "Valid", "Successfully Saved!");
                            } else {
                                Liquid.showDialogError(ReadingSummaryActivity.this, "Invalid", "Unsuccessfully Saved!");
                            }

                            getDeviceLocation();
                            dialog.cancel();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.cancel();
                            break;

                        case DialogInterface.BUTTON_NEUTRAL:
                            finalSave();
                            dialog.cancel();
                            break;

                    }
                }
            };
          AlertDialog.Builder builder = new AlertDialog.Builder(this);

          if(Double.parseDouble(Latitude) == 0.0)
              builder.setPositiveButton("Save",dialogClickListener)
                      .setNeutralButton("Retry",dialogClickListener)
                      .setNegativeButton("Cancel",dialogClickListener)
                      .setMessage("There is no coordinates detected. Do you want to retry?");
          else
              builder.setMessage("Are you sure you want to Save?")
                      .setPositiveButton("Yes", dialogClickListener)
                      .setNegativeButton("No", dialogClickListener);

          builder.show();
        }else{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result = false;
                            boolean result_logs = false;
                            result = Liquid.SaveReading();
                            result_logs = Liquid.SaveReadingLogs();

                            if (result) {
                                Liquid.showReadingDialogNext(ReadingSummaryActivity.this, "Valid", "Successfully Saved!");
                            } else {
                                Liquid.showDialogError(ReadingSummaryActivity.this, "Invalid", "Unsuccessfully Saved!");
                            }
                            getDeviceLocation();
                            dialog.cancel();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            Liquid.printlatitude = Latitude;
                            Liquid.printlongitude = Longitude;
                            Liquid.Print_TimeStamp = Liquid.currentDateTime();
                            Liquid.Print_Attempt = String.valueOf(Integer.parseInt(Liquid.Print_Attempt) + 1);
                            if(!bluetoothAdapter.isEnabled()){
                                Intent intentOpenBluetoothSettings = new Intent();
                                intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                                startActivity(intentOpenBluetoothSettings);
                                return;
                            }
                            result = Liquid.SaveReading();
                            result_logs = Liquid.SaveReadingLogs();

                            new PrintBill().execute();
                            getDeviceLocation();
                            dialog.cancel();
                            break;

                        case DialogInterface.BUTTON_NEUTRAL:
                            finalSave();
                            dialog.cancel();
                            break;

                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            if(Double.parseDouble(Latitude) == 0.0)
                 builder.setPositiveButton("Save",dialogClickListener)
                      .setNeutralButton("Retry",dialogClickListener)
                      .setNegativeButton("Save & Print Bill", dialogClickListener)
                      .setMessage("There is no coordinates detected. Do you want to retry?");
            else
                 builder.setMessage("Are you sure you want to Save only or Save & Print Bill?")
                      .setPositiveButton("Save", dialogClickListener)
                      .setNegativeButton("Save & Print Bill", dialogClickListener);

            builder.show();
        }

    }

    public void SaveSurveyData(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean survey_result = false;
                        boolean reading_result = false;
                        boolean logs_result = false;
                        survey_result = Liquid.SaveSurvey();
                        reading_result = Liquid.SaveReading();
                        logs_result = Liquid.SaveReadingLogs();
                        UpdateCustomerData(Liquid.SelectedId, Liquid.AccountNumber);
                        if (survey_result) {
                            Liquid.showReadingDialogNext(ReadingSummaryActivity.this, "Valid", "Successfully Saved!");

                        } else {
                            Liquid.showDialogError(ReadingSummaryActivity.this, "Invalid", "Unsuccessfully Saved!");
                        }

                        getDeviceLocation();
                        dialog.cancel();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();

                        break;

                    case DialogInterface.BUTTON_NEUTRAL:
                        finalSave();
                        dialog.cancel();
                        break;

                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(Double.parseDouble(Latitude) == 0.0)
            builder.setPositiveButton("Save",dialogClickListener)
                    .setNeutralButton("Retry",dialogClickListener)
                    .setNegativeButton("Cancel",dialogClickListener)
                    .setMessage("There is no coordinates detected. Do you want to retry?");
        else
            builder.setMessage("Are you sure you want to Save?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener);

        builder.show();
    }

    public static boolean UpdateCustomerData(String JobId,String AccountNumber){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "account_name",
                    "Complete_Address",
                    "CED_MeterNumber",
                    "CED_Sequence",
            };
            Liquid.LiquidValues = new String[] {
                    Liquid.AccountName,
                    Liquid.Complete_Address,
                    Liquid.MeterNumber,
                    Liquid.Sequence,
            };
            whereClause = "C_AccountNumber=? AND job_id =?";
            whereArgs = new String[] {AccountNumber,JobId};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateCustomerQuery("customer_reading_downloads",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            Log.i("Tristan Gary Leyesa : ", "SUCCESS");
            return true;
        }catch(Exception e){
            Log.e("Tristan Gary Leyesa : ",e.toString());
            return false;
        }
    }


    private void getDeviceLocation(){
        try{
            Log.d(TAG, "getDeviceLocation: getting the devices current location");
            mLocationPermissionsGranted = true;
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            try{

                if(mLocationPermissionsGranted){

                    final Task location = mFusedLocationProviderClient.getLastLocation();

                    location.addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                try{
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();
                                    Latitude = String.valueOf(currentLocation.getLatitude());
                                    Longitude = String.valueOf(currentLocation.getLongitude());

                                    LiquidGPS liquidGPS = new LiquidGPS(activity);
                                    liquidGPS.PostRealtimeData(Latitude,Longitude);
                                }catch (Exception e){
                                    Log.e(TAG,"Error",e);
                                }
                            }else{
                                Log.d(TAG, "onComplete: current location is null");
                            }
                        }
                    });
                }
            }catch (SecurityException e){
                Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
            } catch(Exception e){
                Log.e(TAG, "Error",e);
            }
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }


        //CameraPosition LastPosition =  CameraPosition.builder().target(new LatLng(Double.parseDouble(String.valueOf(mLiquidGPS.getLatitude())),Double.parseDouble(String.valueOf(mLiquidGPS.getLongitude())))).zoom(30).bearing(0).tilt(45).build();
        //mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(LastPosition));
    }


    public class PrintBill extends AsyncTask<Void,Void,Void> {
        boolean result = false;
        String result2 = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setTitle("UMS");
            pDialog.setMessage("Printing...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Liquid.printlatitude = Latitude;
                Liquid.printlongitude = Longitude;
                Liquid.Print_TimeStamp = Liquid.currentDateTime();
                Liquid.Print_Attempt = String.valueOf(Integer.parseInt(Liquid.Print_Attempt) + 1);
                Liquid.PrintResponse = false;
                LiquidPrintBill mLiquidPrintBill = new LiquidPrintBill();
                result = mLiquidPrintBill.pairPrinter();
                Thread.sleep(4000);
            } catch (Exception e) {
                e.printStackTrace();
                result2 = e.getMessage().toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        boolean result_logs = false;

                          Liquid.showReadingDialogNext(ReadingSummaryActivity.this, "Information.", "Choose done to go back to the list and close to try again.");
//                        if (Liquid.PrintResponse) {
//                            result = false;
//
//                            Liquid.showReadingDialogNext(ReadingSummaryActivity.this, "Valid", "Successfully Print!");
//                            //ShowReprint();
//                        } else {
//                            Liquid.showDialogError(ReadingSummaryActivity.this, "Invalid", "There are some problem in printing.");
//                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            },1000);
        }
    }

}
