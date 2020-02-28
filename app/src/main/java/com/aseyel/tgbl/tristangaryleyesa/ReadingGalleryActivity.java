package com.aseyel.tgbl.tristangaryleyesa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingGalleryAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidPrintBill;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;

public class ReadingGalleryActivity extends BaseActivity {
    private final String TAG = ReadingGalleryActivity.class.getSimpleName();
    private LiquidGPS mLiquidGPS;
    private RecyclerView rvList;
    private TrackingGalleryAdapter Adapter;
    private ProgressDialog pDialog;
    private int numberOfColumns = 3;
    private LiquidFile mLiquidFile;
    private MenuItem searchMenuItem;
    private String[] Subfolder;
    private Button btnNext;
    private ImageButton btnCamera;
    private FloatingActionButton btnReprint;
    private TextSwitcher tsImageCounter;
    private int ImageCount = 0;
    private  ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    private int mBitmapCount = 0;
    private File mFile;
    private File mImages;
    private File[] listFile;
    private String Filename = "";
    private ArrayList<Uri> mUri = new ArrayList<Uri>();
    private static final int CAM_REQUEST = 1;
    private boolean animation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reading_gallery);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Liquid.SelectedCategory = "";
            init(Liquid.SelectedCategory);
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa ",e);
        }
    }

    private void init(String Folder){
        mLiquidFile = new LiquidFile(this);
        pDialog = new ProgressDialog(this);
        btnReprint = (FloatingActionButton) findViewById(R.id.btnReprint);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnNext = (Button) findViewById(R.id.btnNext);
        tsImageCounter = (TextSwitcher) findViewById(R.id.tsImageCounter);
        Subfolder = new String[1];
        Subfolder[0] = Folder;
        rvList = (RecyclerView) findViewById(R.id.rvList);
        Adapter = new TrackingGalleryAdapter(this);
        GridLayoutManager glm = new GridLayoutManager(this , numberOfColumns);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(glm);
        rvList.setAdapter(Adapter);
        mLiquidFile = new LiquidFile(this);
        new GetImages().execute();

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    permissionCamera();
                    Filename = Liquid.AccountNumber + "_"+"reading"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;

                    if (Liquid.Client == "more_power"){
                        //for Survey
//                        Filename = Liquid.MeterNumber + "_"+"audit"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + Liquid.User + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                       Filename = Liquid.AccountNumber + "_"+"reading"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                    }

                    mFile = mLiquidFile.Directory(Liquid.SelectedId,Liquid.RemoveSpecialCharacter(Filename), Subfolder);
                    Log.i(TAG, String.valueOf(mFile));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    startActivityForResult(intent, CAM_REQUEST);
                } catch (Exception e) {
                    Liquid.ShowMessage(getApplicationContext(), e.toString());
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ImageCount == 0){
                    Liquid.showDialogInfo(ReadingGalleryActivity.this,"Warning","Please take a picture to prove your reading and remarks. Thank you.");
                }else{
                    Liquid.draftBill = true;
                    //Intent i = new Intent(ReadingGalleryActivity.this, ReadingSummaryActivity.class);
                    Intent i = new Intent();
                    switch(Liquid.ServiceType){
                        case "MESSENGER":
                            i = new Intent(ReadingGalleryActivity.this, DeliveryActivity.class);
                            break;
                            default:
                                i = new Intent(ReadingGalleryActivity.this, ReadingV2Activity.class);
                                break;
                    }
                    startActivity(i);
                }
            }
        });

        btnReprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reprint();
            }
        });

        ReadingV2Activity.initData();
        GetReadingDetails();
        GetReadAndBillData();
    }

    private void permissionCamera(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public class GetImages extends AsyncTask<Void,Void,Void>{
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading....");
            pDialog.show();
            tsImageCounter.setCurrentText("0");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final_result = GetReadingImages(Liquid.SelectedId,Subfolder);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
            Adapter.updateItems(animation,final_result);
            if(pDialog.isShowing())
                 pDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;
                    String AccountNumber = "";
                    String ImageType = "";
                    if(Liquid.Client == "more_power"){
                        //for Survey
//                        AccountNumber = Liquid.MeterNumber;
                        //ImageType = "audit";
                        AccountNumber = Liquid.AccountNumber;
                        ImageType = "reading";
                    }
                    else{
                        AccountNumber = Liquid.AccountNumber;
                        ImageType = "reading";
                    }

                    result = ReadingModel.doSubmitPicture(
                            Liquid.Client,
                            AccountNumber,
                            ImageType,
                            Liquid.RemoveSpecialCharacter(Filename),
                            Liquid.currentDateTime(),
                            Liquid.currentDateTime(),
                            Liquid.User,
                            Liquid.User,
                            Liquid.ReadingDate
                    );

                    if (result == true) {
                        Liquid.imageOrientation = "";
                        ExifInterface exif = new ExifInterface(mFile.getAbsolutePath());
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                Liquid.imageOrientation = "Landscape";
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                Liquid.imageOrientation = "Portrait";

                                break;
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                Liquid.imageOrientation = "Landscape";
                                break;
                            default:
                                Liquid.imageOrientation = "Portrait";
                        }

                        Log.e("Image Orientation: ",Liquid.imageOrientation +" ("+orientation+")");
                        Liquid.resizeImage(mFile.getAbsolutePath(), 0.80, 0.80);
                        Liquid.ShowMessage(getApplicationContext(), "Save Image Success");
                        mUri.add(Uri.fromFile(mFile));
                        //tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                        ImageCount = mUri.size();
                    }

                    animation = false;
                    new GetImages().execute();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
        }
    }

    public void GetReadAndBillData() {
        try {
            Cursor result = workModel.GetReadAndBillSelectedJobOrderDetails(Liquid.SelectedId, Liquid.AccountNumber);

            if (result.getCount() == 0) {
                return;
            }
            while (result.moveToNext()) {
                //Customer Data
                HashMap<String, String> data = new HashMap<>();
                Liquid.ConsumerStatus = result.getString(9);
                Liquid.AccountNumber = result.getString(30);

                switch (Liquid.Client)
                {
                    case "more_power":
                        //for Survey
                        //Liquid.Complete_Address = Liquid.UpdatedAddress;
                        Liquid.AccountName = result.getString(17);
                        Liquid.Complete_Address = result.getString(29);
                        Liquid.Sequence = result.getString(35);
                        Liquid.MeterNumber = result.getString(37);
                        break;

                    default:
                        Liquid.AccountName = result.getString(17);
                        Liquid.Complete_Address = result.getString(29);
                        Liquid.Sequence = result.getString(35);
                        Liquid.MeterNumber = result.getString(37);
                        break;
                }
                Liquid.C_ID = result.getString(3);
                Liquid.connectionload = result.getString(87);
                Liquid.serial =  result.getString(63);
                Liquid.AccountType = result.getString(7);
                Liquid.Date = result.getString(71);
                Liquid.ReadingDate = result.getString(54);
                String[] ReadingDateSplit = Liquid.ReadingDate.split("-");
                Liquid.JobId = result.getString(2);
                Liquid.route = result.getString(11);
                Liquid.itinerary = result.getString(13);
                Liquid.serial = result.getString(63);
                Liquid.OldConsumption  = "0";
                Liquid.OldReading  = result.getString(89);
                Liquid.OldPreviousReading  = result.getString(91);
                Liquid.OldMeterNumber  = "";
                Liquid.previous_reading = !result.getString(47).equals("") ? result.getString(47) : "0";
//              Liquid.previous_reading = Liquid.FixDecimal(Liquid.previous_reading);
                Liquid.previous_consumption = !result.getString(52).equals("") ? result.getString(52) : "0";
                Liquid.present_reading_date = Liquid.currentDate();
                Liquid.previous_reading_date = !result.getString(53).equals("") ? result.getString(53) : Liquid.ReadingDate;
                Liquid.classification = result.getString(8);
                Liquid.BillMonth = result.getString(66);
                Liquid.BillYear = result.getString(68);
                Liquid.BillDate = result.getString(67);
                Liquid.month = Liquid.BillMonth;
                Liquid.year = Liquid.BillYear;
                Liquid.day = ReadingDateSplit[1];
                Liquid.Averange_Consumption = !result.getString(46).equals("") ? result.getString(46) : "0";
                Liquid.multiplier = !result.getString(40).equals("") ? result.getString(40) : "1";
                Liquid.multiplier = Liquid.FixDecimal(Liquid.multiplier);
                Liquid.Meter_Box = result.getString(36);
                Liquid.code = result.getString(1);
                Liquid.rate_code = result.getString(7);
                Liquid.rate_description = result.getString(16);
                Liquid.coreloss = !result.getString(44).equals("") ? result.getString(44) : "0";
                Liquid.meter_count = !result.getString(42).equals("") ? result.getString(42) : "1";
                Liquid.arrears = !result.getString(43).equals("") ? result.getString(43) : "0";
                Liquid.arrears = Liquid.arrears.replace(",","");
                Liquid.senior_tagging = result.getString(56);
                Liquid.eda_tagging = result.getString(70);
                Liquid.DateChangeMeter = result.getString(88);
                Liquid.change_meter = !result.getString(58).equals("") ? result.getString(58) : "0";
                Liquid.interest = !result.getString(62).equals("") ? result.getString(62) : "0";

                switch (Liquid.Client){
                    case "ileco2":
                        Liquid.overdue = !result.getString(86).equals("") ? result.getString(86) : "0"; //inclusion
                        Liquid.surcharge = !result.getString(85).equals("") ? result.getString(85) : "0"; // ILP
                        break;
                    case "pelco2":
                        Liquid.overdue = !result.getString(86).equals("") ? result.getString(86) : "0"; //inclusion
                        Liquid.surcharge = !result.getString(85).equals("") ? result.getString(85) : "0"; // ILP
                        break;
                    default:
                        Liquid. overdue = Liquid.arrears;
                        Liquid.surcharge = Liquid.interest;
                        break;
                }
                Liquid.ShareCap =  !result.getString(78).equals("") ? result.getString(78) : "0";
                Liquid.rentalfee = !result.getString(55).equals("") ? result.getString(55) : "0";
                Liquid.OtherBill = !result.getString(83).equals("") ? result.getString(83) : "0";
                Liquid.bill_number = !result.getString(65).equals("") ? result.getString(65) : Liquid.year + Liquid.BillMonth + Liquid.AccountNumber;
                Liquid.rowid = result.getString(84);
                Liquid.pn_promotitle = result.getString(92);
                Liquid.pn_promo = !result.getString(92).equals("") ? result.getString(92) : "0";
                Liquid.pn_promo = Liquid.pn_promo!=null ? Liquid.pn_promo : "0";

                if(!Liquid.pn_promo.matches("0"))
                    try{
                        Liquid.pn_promotitle = Liquid.pn_promo.split("~")[0];

                        Liquid.pn_promo = Liquid.pn_promo.split("~")[1];
                        Liquid.pn_promo = Liquid.pn_promo.replace(",","");
                        Liquid.pn_promo = Liquid.pn_promo.replace("/","");
                    }catch (Exception e){}

                //Discon and DueDate
                switch (Liquid.Client){
                    case "baliwag_wd":
                        if(Double.parseDouble(Liquid.arrears) <= 0){
                            Liquid.duedate = result.getString(64);
                            Liquid.discondate = result.getString(75);
                        }else{
                            Liquid.duedate = result.getString(90);
                            Liquid.discondate = Liquid.getDisconDate(Liquid.duedate,1);
                        }
                        break;
                    default:
                        Liquid.duedate = result.getString(64);
                        if (Liquid.duedate.equals("")) {
                            Liquid.duedate = Liquid.getDueDate(Liquid.present_reading_date);
                        }
                        Liquid.discondate = Liquid.getDisconDate(Liquid.duedate);
                        break;
                }
                Liquid.present_reading_date = Liquid.currentDate();
                Liquid.BillingCycle = Liquid.year + "-" + Liquid.BillMonth;
                //Reading
            }
        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }
    }

    private void GetReadingDetails() {
        try {
            Cursor result = ReadingModel.GetReadingDetails(Liquid.SelectedId, Liquid.AccountNumber);
            if (result.getCount() != 0) {
                btnReprint.setVisibility(View.VISIBLE);
                mLiquidGPS = new LiquidGPS(this);

                while (result.moveToNext()) {
                    HashMap<String, String> data = new HashMap<>();
                    Liquid.C_ID = result.getString(1);
                    Liquid.Reading  = result.getString(10);
                    Liquid.Demand = result.getString(24).equals(null) || result.getString(24).equals("") ? "" : result.getString(24);
                    Liquid.reactive = result.getString(25);
                    Liquid.powerfactor = result.getString(26);
                    Liquid.kw_cummulative = result.getString(27);
                    Liquid.reading1 =   Liquid.Reading;
                    Liquid.reading2 = Liquid.Demand;
                    Liquid.reading3 = Liquid.reactive;
                    Liquid.reading4 = Liquid.powerfactor;
                    Liquid.reading5 = Liquid.kw_cummulative;
                    Liquid.reading6 = result.getString(33);
                    Liquid.reading7 = result.getString(34);
                    Liquid.reading8 = result.getString(35);
                    Liquid.reading9 = result.getString(36);
                    Liquid.reading10 = result.getString(27);
                    Liquid.iwpowerfactor = result.getString(38);

                    try {
                        Liquid.demand_consumption = String.valueOf(Double.parseDouble(Liquid.Demand )*  Double.parseDouble(Liquid.multiplier));

                    }catch(Exception e) {
                        Liquid.demand_consumption = "0";
                    }
                    Liquid.reactive_consumption = Liquid.reactive;
                    Liquid.ReaderComment = result.getString(50);
                    Liquid.RemarksValue = result.getString(47) + "-" + result.getString(49);
                    Liquid.Print_Attempt = result.getString(54);
                    Liquid.Reading_Attempt  = result.getString(53);
                    Liquid.Reader_ID = result.getString(51);
                    Liquid.meter_reader = result.getString(52);
                    Liquid. RemarksCode = result.getString(47);
                    Liquid.Remarks = result.getString(49);
                    Liquid.reading_remarks = result.getString(64);
                    Liquid.Reading_TimeStamp  = result.getString(75);
                    Liquid.Print_TimeStamp = result.getString(76);
                    Liquid.r_latitude = result.getString(56);
                    Liquid.r_longitude  = result.getString(57);
                    Liquid.check_previous = result.getString(135);
                    Liquid.Present_Consumption  = result.getString(12);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error Galery Reprint: ", e);
            return;
        }
    }

    public boolean initializationForComputation(String type){
        //Declaration
        try{
            if(!Liquid.CheckGPS(this)){
                Liquid.showDialogInfo(this,"Invalid","Please enable GPS!");
                return false;
            }
            Liquid.save_only = false;
            Liquid.Reader_ID = Liquid.User;
            Liquid.meter_reader = Liquid.User;
//          Liquid.Reading = etxtReading.getText().toString();
//          Liquid.Demand = txtDemand.getText().toString();
            Liquid.Reading_Attempt = String.valueOf(Integer.parseInt(Liquid.Reading_Attempt) + 1);
            String LastDial = String.valueOf(Liquid.previous_reading.charAt(0));
            Liquid.Reading_TimeStamp = Liquid.currentDateTime();
            Liquid.timestamp = Liquid.Reading_TimeStamp;
            Liquid.ModifiedDate =Liquid. Reading_TimeStamp;
            Liquid.ModifiedBy = Liquid.User;
            Liquid.reading1 = Liquid.Reading;
            Liquid.reading2 = Liquid.Demand;
            Liquid.RemarksCode = "0";
            Liquid.Remarks = "NO FIELD FINDINGS";
            String[] RemarksData = Liquid.RemarksValue.split("-");

            if(type.matches("Reprint")) {
                Liquid.RemarksCode = RemarksData[0];
                Liquid.Remarks = RemarksData[1];
            }
            //Reading Location
            Liquid.r_latitude = String.valueOf(mLiquidGPS.getLatitude());
            Liquid.r_longitude = String.valueOf(mLiquidGPS.getLongitude());

            if (LastDial.equals("9")) {
                Liquid.Present_Consumption = Liquid.WrapAround(Liquid.previous_reading, Liquid.Reading);
                Liquid.Remarks = "WRAP AROUND MTR";
                Liquid.RemarksCode = "28";
            } else {
                Liquid.Present_Consumption = Liquid.GetKWH(Liquid.multiplier, Liquid.previous_reading, Liquid.Reading);
            }
            Liquid.demand_consumption = Liquid.GetKWH(Liquid.multiplier, String.valueOf(0), Liquid.Demand);

            if (Liquid.RemarksCode.equals("0") || Liquid.RemarksCode.equals("28")) {

            }else{
                //avg validation
                switch(Liquid.Client){
                    case "ileco2":
                        Liquid.Average_Reading = Liquid.AverageIleco2Validation(Liquid.Remarks,Liquid.RemarksCode);
                        Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);
                        return true;
                    case "pelco2":
                        Liquid.Average_Reading = Liquid.AverageIleco2Validation(Liquid.Remarks,Liquid.RemarksCode);
                        Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);
                        return true;
                    case "baliwag_wd":
                        for (int x=0; x<Liquid.BaliwagAverageRemarksAbbreviation.length; x++){
                            if(Liquid.RemarksAbbreviation.matches(Liquid.BaliwagAverageRemarksAbbreviation[x])){
                                Liquid.Reading = Integer.toString((int)(Integer.parseInt(Liquid.previous_reading)+Integer.parseInt(Liquid.Averange_Consumption)));
                                Liquid.Present_Consumption = Liquid.Averange_Consumption;
                            }
                        }

                        break;

                    case "more_power":
                        break;

                    default:
                        Liquid.Average_Reading = Liquid.AverageValidation(Liquid.Remarks,Liquid.RemarksCode);
                        Liquid.Present_Consumption = Liquid.Averange_Consumption;

                        Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);
                        return true;
//                        Liquid.Average_Reading = Liquid.AverageValidation(Liquid.Remarks,Liquid.RemarksCode);
//                        Liquid.Present_Consumption = Liquid.Averange_Consumption;
//                        Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);
//                        return true;
                }
            };

            if(!Liquid.DateChangeMeter.equals("") &&
                    Liquid.ConsumerStatus.equals("CHANGE METER") &&
                    (Liquid.coreloss.equals("0") ||
                            Liquid.coreloss.equals("") ||
                            Double.parseDouble(Liquid.coreloss) <= 0)){

                Liquid.Present_Consumption = String.valueOf(ChangeMeterKWH(
                        Liquid.ConvertStringToDate(Liquid.DateChangeMeter),
                        Liquid.ConvertStringToDate(Liquid.present_reading_date),
                        Double.parseDouble(Liquid.Present_Consumption)));
            }

            Liquid.Present_Consumption = String.valueOf(AddCons(Double.parseDouble(Liquid.coreloss),Double.parseDouble(Liquid.Present_Consumption)));

            switch (Liquid.Client)
            {
                case "more_power":
                    if (Double.parseDouble(Liquid.Averange_Consumption) < Double.parseDouble(Liquid.Present_Consumption)) {
                        Liquid.reading_remarks = Liquid.MorePowerHighConsumptions(Liquid.Averange_Consumption, Liquid.Present_Consumption);

                        return true;

                    } else if (Double.parseDouble(Liquid.Averange_Consumption) > Double.parseDouble(Liquid.Present_Consumption)) {

                        Liquid.reading_remarks = Liquid.MorePowerLowConsumptions(Liquid.Averange_Consumption, Liquid.Present_Consumption);
                        return true;
                    }

                    break;

                default:
                    Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);

                    return true;
            }
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error Reading",e);
            return false;
        }
    }

    public double ChangeMeterKWH(Date DateChangeMeter, Date PresentReadingDate, double KWH){
        double daysKWH = 0;
        double avgKWH = 0;
        long days = Liquid.diffDate(PresentReadingDate,DateChangeMeter);

        if(days == 0){
            days = 1;
        }

        daysKWH = Math.round(KWH / days);

        avgKWH = daysKWH * 30;

        return avgKWH;
    }

    public double AddCons(double addcons,double kwh){
        double totalKWH = 0;
        totalKWH = addcons + kwh;
        return Liquid.RoundUp(totalKWH);
    }

    public void Reprint(){
        try {
            if(!initializationForComputation("Reprint")){
                return;
            };
            switch(Liquid.reading_remarks){
                case "LOW CONSUMPTION":
                    switch (Liquid.Client){
                        case "baliwag_wd":
                            break;
                        default:
                            Liquid.showDialogError(ReadingGalleryActivity.this,"Invalid","Low Consumption cannot print!");
                            Liquid.save_only = true;
                            return;
                    }
                case "HIGH CONSUMPTION":
                    // Jan 31, 2020
                    // Mariesher Zapico
                    // Cannot reprint if the bill is super bill which is present consumption >= 1000 percent of average consumption
                   /* if(Liquid.SuperBillAverageconsumption(Liquid.Present_Consumption,Liquid.Averange_Consumption)){
                        Liquid.showDialogError(this,"Invalid","Super Consumption cannot print!");
                        Liquid.save_only = true;
                        return;
                    }*/
                    break;
                case "NEGATIVE CONSUMPTION":
                    Liquid.showDialogError(this,"Invalid","Negative Consumption cannot print!");
                    Liquid.save_only = true;
                    return;
                case "ZERO CONSUMPTION":
                    switch (Liquid.Client){
                        case "baliwag_wd":
                            break;
                        default:
                            Liquid.showDialogError(this,"Invalid","Zero Consumption cannot print!");
                            Liquid.save_only = true;
                            return;
                    }
                default:
            }

            switch (Liquid.Client){
                case "more_power":
                    if(Liquid.reading_remarks.matches("HIGH CONSUMPTION")){
                        ReadingV2Activity.Computation();
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Liquid.draftBill = false;
                                        boolean result_logs = false;
                                        Liquid. ModifiedDate = Liquid.currentDateTime();
                                        Liquid.timestamp = Liquid.currentDateTime();
                                        Liquid.ModifiedBy = Liquid.User;
                                        ReadingModel.UpdatePrintAttempt(Liquid.SelectedId,Liquid.AccountNumber,Liquid.Print_Attempt);
                                        new PrintBill().execute();
                                        result_logs = Liquid.SaveReadingLogs();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        Liquid.draftBill = true;
                                        Liquid. ModifiedDate = Liquid.currentDateTime();
                                        Liquid.timestamp = Liquid.currentDateTime();
                                        Liquid.ModifiedBy = Liquid.User;
                                        ReadingModel.UpdatePrintAttempt(Liquid.SelectedId,Liquid.AccountNumber,Liquid.Print_Attempt);
                                        new PrintBill().execute();
                                        result_logs = Liquid.SaveReadingLogs();
                                        break;
                                    case DialogInterface.BUTTON_NEUTRAL:
                                        break;

                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Which bill do you want to reprint?").setPositiveButton("Official", dialogClickListener)
                                .setNegativeButton("Draft", dialogClickListener).setNeutralButton("Cancel", dialogClickListener).show();
                        break;
                    }

                default:
                    ReadingV2Activity.Computation();
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    boolean result_logs = false;
                                    Liquid. ModifiedDate = Liquid.currentDateTime();
                                    Liquid.timestamp = Liquid.currentDateTime();
                                    Liquid.ModifiedBy = Liquid.User;
                                    ReadingModel.UpdatePrintAttempt(Liquid.SelectedId,Liquid.AccountNumber,Liquid.Print_Attempt);
                                    new PrintBill().execute();
                                    result_logs = Liquid.SaveReadingLogs();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Do you really want to reprint bill?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
            }
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa",e);
        }
    }

    public class PrintBill extends AsyncTask<Void,Void,Void> {
        boolean result = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadingGalleryActivity.this);
            pDialog.setMessage("Printing...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Liquid.printlatitude = "";
                Liquid.printlongitude = "";
                Liquid.Print_TimeStamp = Liquid.currentDateTime();
                Liquid.Print_Attempt = String.valueOf(Integer.parseInt(Liquid.Print_Attempt) + 1);
                LiquidPrintBill mLiquidPrintBill = new LiquidPrintBill();
                result = mLiquidPrintBill.pairPrinter();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result) {
                result = false;
                boolean result_logs = false;
                result = Liquid.SaveReading();
                result_logs = Liquid.SaveReadingLogs();
                Liquid.showReadingDialogNext(ReadingGalleryActivity.this, "Valid", "Successfully Print!");
                //ShowReprint();
            } else {
                Liquid.showDialogError(ReadingGalleryActivity.this, "Invalid", "Unsuccessfully Print!");
            }
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private ArrayList<HashMap<String, String>> GetReadingImages(String id,String[] Subfolder){
        String SelectedAccountNumber = "";

        if(Liquid.Client == "more_power"){
            //for Survey
            //SelectedAccountNumber = Liquid.serial;
            SelectedAccountNumber = Liquid.SelectedAccountNumber;
        }
        else{

            SelectedAccountNumber = Liquid.SelectedAccountNumber;
        }
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Log.i(TAG,"Tristan "+ Liquid.SelectedId);
        Log.i(TAG,"Tristan "+ SelectedAccountNumber);
        Log.i(TAG,"Tristan "+ Liquid.ReadingDate);
        File mImages = Liquid.getDiscPicture(id,Subfolder);
        mUri.clear();
        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[0].equals(Liquid.RemoveSpecialCharacter(SelectedAccountNumber)) && seperated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.ReadingDate))){
                    mUri.add(Uri.fromFile(listFile[a]));
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }
            }
        }
        return final_result;
    }
}
