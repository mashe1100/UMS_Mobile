package com.aseyel.tgbl.tristangaryleyesa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidPrintTripTicket;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewTripActivity extends BaseFormActivity {
    private static final String TAG = NewTripActivity.class.getSimpleName();
    private EditText txtDate;
    private TextView txtGuide;
    private TextView mSearchingLocation;
    private TextView mGoingtoLocation;
    private EditText txtPlateNo;
    private EditText txtDriverName;
    public static String DriversName,Date,SearchingLocation,GoingtoLocation,PlateNo;
    public static String CtrlNumber;
    private ProgressDialog mProgressDialog;
    private LiquidPrintTripTicket mLiquidPrintTripTicket;
    private Button btnSearchDriver;
    private Calendar myCalendar;
    private static final int REQUEST_CODE_SEARCH = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtDriverName = (EditText) findViewById(R.id.txtDriverName);
        txtPlateNo = (EditText) findViewById(R.id.txtPlateNo);
        txtGuide = (TextView) findViewById(R.id.txtGuide);
        mGoingtoLocation = (TextView) findViewById(R.id.mGoingtoLocation);
        mSearchingLocation = (TextView) findViewById(R.id.mSearchingLocation);
        btnSearchDriver = (Button) findViewById(R.id.btnSearchDriver);
        mLiquidPrintTripTicket = new LiquidPrintTripTicket();
        myCalendar = Calendar.getInstance();
        txtPlateNo.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtDate.setText(Liquid.currentDate());
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearchDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewTripActivity.this,SearchActivity.class);
                startActivityForResult(i, REQUEST_CODE_SEARCH);
            }
        });

        setData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check that the result was from the autocomplete widget.
        Log.i(TAG, String.valueOf(requestCode));
        if (requestCode == REQUEST_CODE_SEARCH) {
                String[] Fullname = Liquid.SearchFullname.split(",");
                String Firstname = String.valueOf(Fullname[1].charAt(0));
                txtDriverName.setText(Firstname + ". "+Fullname[0]);

        }
    }
    private void setData(){
        mSearchingLocation.setText(Liquid.SearchLocation);
        mGoingtoLocation.setText(Liquid.GoingToLocation);
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                DriversName = txtDriverName.getText().toString();
                Date = txtDate.getText().toString();
                PlateNo = txtPlateNo.getText().toString();
                if(PlateNo.equals("") || DriversName.equals("")){
                    Liquid.showDialogError(this,"Invalid","Incomplete Informations!");
                }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                new DoNewTripTicket().execute();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(NewTripActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class DoNewTripTicket extends AsyncTask<String,Void,Void>{

        boolean result = false;
        String return_message = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewTripActivity.this);
            mProgressDialog.setMessage("Posting...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try{
                HttpHandler sh = new HttpHandler();
                Liquid.POSTUMSAPI mPOSTUMSAPI;
                mPOSTUMSAPI = new Liquid.POSTUMSAPI("lms/php/api/TripTicket.php");
                JSONObject dataObject = new JSONObject();
                dataObject.put("username",Liquid.Username);
                dataObject.put("password",Liquid.Password);
                dataObject.put("request_type","DoPostTripTicket");
                dataObject.put("client",Liquid.Client);
                dataObject.put("user",Liquid.User);
                dataObject.put("date",Date);
                dataObject.put("platenumber",PlateNo);
                dataObject.put("placefrom",Liquid.SearchLocation);
                dataObject.put("placeto",Liquid.GoingToLocation);
                dataObject.put("departuretime","");
                dataObject.put("arrivaltime","");
                dataObject.put("driversname",DriversName);
                dataObject.put("passengers_in","");
                dataObject.put("passengers_out","");
                dataObject.put("latitudefrom",Liquid.SearchLocationLatitude);
                dataObject.put("longitudefrom",Liquid.SearchLocationLongtitude);
                dataObject.put("latitudeto",Liquid.GoingToLocationLatitude);
                dataObject.put("longitudeto",Liquid.GoingToLocationLongtitude);
                String jsonStr  = sh.makeServicePostCall(mPOSTUMSAPI.API_Link,dataObject);

                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                response =  new JSONObject(response.getString("TripTicket"));
                if(response.getString("result").equals("false")){
                    result = false;
                    return_message = response.getString("message").toString();
                }else{
                    result = true;
                    CtrlNumber = response.getString("trip_ticket_number");
                }


            }catch (Exception e){
                Log.e(TAG,"Error :", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                mProgressDialog.dismiss();
                if(result == false){
                    Liquid.showDialogError(NewTripActivity.this,"Invalid",return_message);
                }else{
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    new PrintTripTicket().execute();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Liquid.showDialogNext(NewTripActivity.this, "Valid", "Successfully Saved!");
                                    break;

                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewTripActivity.this);
                    builder.setMessage("Do you want to Print?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }catch (Exception e){
                Log.e(TAG,"Error :", e);
            }
        }
    }


    public class PrintTripTicket extends AsyncTask<Void,Void,Void> {
        boolean result = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewTripActivity.this);
            mProgressDialog.setMessage("Printing...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                result = mLiquidPrintTripTicket.pairPrinter();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            if (result) {
                Liquid.showDialogNext(NewTripActivity.this, "Valid", "Successfully Saved!");
            } else {
                Liquid.showDialogError(NewTripActivity.this, "Invalid", "Unsuccessfully Saved!");
            }

        }
    }
}
