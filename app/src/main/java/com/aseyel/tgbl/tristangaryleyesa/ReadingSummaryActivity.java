package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
                mLiquidGPS = new LiquidGPS(ReadingSummaryActivity.this);

                if(result.getCount() == 0){
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
                    mSmsManager.sendTextMessage("+639064783858",null,Message,null,null);
                }
                else{
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
                Log.i(TAG,Message);
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
                                    new GPSPosting().execute();
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
                LiquidPrintBill mLiquidPrintBill = new LiquidPrintBill();
                result = mLiquidPrintBill.pairPrinter();
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                result2 = e.getMessage().toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {



                if (result) {
                    result = false;
                    boolean result_logs = false;
                    result = Liquid.SaveReading();
                    result_logs = Liquid.SaveReadingLogs();
                    Liquid.showReadingDialogNext(ReadingSummaryActivity.this, "Valid", "Successfully Print!");
                    //ShowReprint();
                } else {
                    Liquid.showDialogError(ReadingSummaryActivity.this, "Invalid", result2);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }

}
