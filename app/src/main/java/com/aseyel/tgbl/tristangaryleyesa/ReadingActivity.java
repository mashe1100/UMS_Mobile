package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;

import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;

import android.text.TextWatcher;
import android.util.Log;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;


import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;

import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.KeyboardUtils;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;

import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidPrintBill;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ReadingActivity extends BaseFormActivity {

    private static final String TAG = ReadingActivity.class.getSimpleName();
    private boolean save_only = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private String Latitude = "";
    private String Longitude = "";
    Liquid mLiquid;
    private EditText[] mEditTextData;
    public static String BillingCycle;
    public static String AccountNumber;
    public static String MeterNumber;
    public static String AccountName;
    public static String Complete_Address;
    public static String Sequence;
    public static String ShareCap;
    public static String PoleRent = "0";
    public static String OtherBill;

    ProgressDialog pDialog;
    public static String Status;
    public static String AccountType;
    public static String Reading = "";
    String Remarks = "";
    String RemarksCode = "";
    String Details;
    String Date;
    String ReadingDate;
    String Client;
    String JobId = "";
    String ServiceType = "reading";
    public static String classification = "";
    public static String arrears = "";
    String senior_tagging = "0";
    String eda_tagging = "0";
    String change_meter = "0";
    String interest = "0";
    String RemarksValue = "";
    LiquidGPS mLiquidGPS;
    int focusEditText = 1;
    List<String> ListOfRemarks;
    int ReadingAttempt = 0;
    private FloatingActionButton btnDelete;
    private AHBottomNavigation mBottomNavigationView;
    //Validation
    int HighConsumptionAttempt = 0;
    int LowConsumptionAttempt = 0;
    int NegativeConsumptionAttempt = 0;
    int ZeroConsumptionAttempt = 0;

    TextView txtHeader;
    TextView txtDetails;
    EditText etxtReading;
    Spinner spinnerRemarks;
    ImageButton btnCamera;
    FloatingActionButton btnGallery;
    ArrayAdapter<String> AdapterSpinnerRemarks;

    //Camera
    static final int CAM_REQUEST = 1;
    String Filename;
    File[] listFile;
    LiquidFile mLiquidFile;
    File mFile;
    String[] Subfolder;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    File mImages;
    TextSwitcher tsImageCounter;
    int ImageCount = 0;
    EditText etxtComment;
    FloatingActionButton btnPreviousReading;
    LiquidPrintBill mLiquidPrintBill;
    EditText txtDemand;
    private FloatingActionButton btnReprint;
    private Switch switchReverse;
    //Reading Variable
    String C_ID = "";
    String job_id  = "";
    String name  = "";
    public static  String route  = "";
    public static String itinerary  = "";
    String meter_number  = "";
    String serial  = "";
    public static String previous_reading  = "0";
    public static String present_Reading = "";
    String previous_consumption= "";
    public static String Present_Consumption= "0";
    public static String previous_reading_date= "";
    String present_reading_date= "";
    public static String duedate= "";
    public static String discondate= "";
    String Reading_Date= "";
    public static String BillYear= "";
    public static String BillMonth= "";
    String BillDate= "";
    String month= "";
    String day= "";
    String year= "0";
    String Demand = "0";
    String reactive= "0";
    String powerfactor= "0";
    String kw_cummulative= "0";
    String reading1= "0";
    String reading2= "0";
    String reading3= "0";
    String reading4= "0";
    String reading5= "0";
    String reading6= "0";
    String reading7= "0";
    String reading8= "0";
    String reading9= "0";
    String reading10= "0";
    String iwpowerfactor= "0";
    String demand_consumption= "0";
    String reactive_consumption= "0";
    String Averange_Consumption= "0";
    public static String Average_Reading = "False";
    public static String multiplier= "";
    String Meter_Box= "";
    String Demand_Reset= "";
    String Test_Block= "";

    String remarks_abbreviation= "";

    String Comment= "";
    String Reader_ID= "";
    String meter_reader= "";
    String Reading_Attempt = "0";
    String Print_Attempt = "0";
    String force_reading= "";
    String r_latitude= "";
    String r_longitude= "";
    String printlatitude= "";
    String printlongitude= "";
    String improbable_reading= "";
    String negative_reading= "";
    String change_reading= "";
    String cg_vat_zero_tag= "";
    String reading_remarks= "";
    String old_key= "";
    String new_key= "";
    String transfer_data_status= "Pending";
    String upload_status= "Pending";
    String code= "";
    String area= "";
    public static String rate_code= "";
    String cummulative_multiplier= "";
    String oebr_number= "";

    String Reading_TimeStamp= "";
    String Print_TimeStamp= "";
    String timestamp= "";
    public static String bill_number= "";
    String GenerationSystem= "";
    String BenHost= "";
    String GRAM= "";
    String ICERA= "";
    String PowerArtReduction= "";
    String TransmissionDelivery= "";
    String TransmissionDelivery2= "";
    String System_Loss= "";
    String Gen_Trans_Rev= "";
    String DistributionNetwork= "";
    String DistributionNetwork2= "";
    String DistributionNetwork3= "";
    String RetailElectricService= "";
    String RetailElectricService2= "";
    String Metering_cust= "";
    String Metering_cust_2= "";
    String Metering_kwh= "";
    String loan= "";
    String RFSC= "";
    String Distribution_Rev= "";
    String MissionaryElectrification= "";
    String EnvironmentCharge= "";
    String NPC_StrandedDebts= "";
    String NPC_StrandedCost= "";
    String DUsCost= "";
    String DCDistributionCharge= "";
    String DCDemandCharge= "";
    String TCTransSystemCharge= "";
    String SCSupplySysCharge= "";
    String equal_tax= "";
    String CrossSubsidyRemoval= "";
    String Universal_Charges= "";
    String Lifeline_Charge= "";
    String InterclassCrossSubsidy= "";
    String SeniorCitizenSubsidy= "";
    String ICCS_Adjustment= "";
    String ICCrossSubsidyCharge= "";
    String FitAllCharge= "";
    String PPD_Adjustment= "";
    String GenerationCostAdjustment= "";
    String PowerCostAdjustment= "";
    String Other_Rev= "";
    String GenerationVat= "";
    String TransmissionVat= "";
    String SystemLossVat= "";
    String DistributionVat= "";
    String OtherVat= "";
    String Government_Rev= "";
    String CurrentBill= "";
    String amountdue= "";
    String overdue= "";
    String franchise_tax= "";
    String coreloss= "";
    String surcharge= "";
    public static String rentalfee= "";
    String delivered= "";
    String check_previous = "False";
    String ispn= "";
    String SCD= "";
    String pnrecvdte= "";
    String pnrecvby= "";
    String recvby= "";
    String hash= "";
    String isreset= "";
    String isprntd= "";
    String meter_count= "";
    String delivery_id= "";
    String delivery_remarks= "";
    String delivery_comment= "";
    String reading_signature= "";
    String real_property_tax= "";
    String cc_rstc_refund= "";
    String cc_rstc_refund2= "";
    String moa= "";
    String eda= "";
    String ModifiedDate= "";
    String ModifiedBy= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try{
            switch(keyCode){
                case 67:
                    DeleteReverse();
                    break;
                case 22:
                    new GetNextConsumer().execute(String.valueOf(keyCode));
                    break;
                case 21:
                    new GetNextConsumer().execute(String.valueOf(keyCode));
                    break;
            }
        }
        catch (Exception e){
            Log.e(TAG,"Tristan Garyl Leyesa",e);
        }



        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        mLiquid = new Liquid();
        mLiquidFile = new LiquidFile(this);
        mLiquidGPS = new LiquidGPS(this);

        Subfolder = new String[1];
        Subfolder[0] = "";
        txtDemand = (EditText) findViewById(R.id.txtDemand);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        etxtReading = (EditText) findViewById(R.id.etxtReading);
        spinnerRemarks = (Spinner) findViewById(R.id.spinnerRemarks);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        tsImageCounter = (TextSwitcher) findViewById(R.id.tsImageCounter);
        etxtComment = (EditText) findViewById(R.id.etxtComment);
        mLiquidPrintBill = new LiquidPrintBill();
        switchReverse = (Switch) findViewById(R.id.switchReverse);
        mBottomNavigationView = (AHBottomNavigation) findViewById(R.id.mBottomNavigationView);

        Client = Liquid.Client;
        ListOfRemarks = new ArrayList<String>();

        GetReadingRemarks();
        AccountNumber = Liquid.SelectedAccountNumber;
        GetReadAndBillData(Liquid.SelectedId, AccountNumber);
        GetReadingDetails(Liquid.SelectedId, AccountNumber);
        GetImages(Liquid.SelectedId);
        Liquid.GetSettings();

        if(Liquid.ReverseInput == 1){
            switchReverse.setChecked(true);
        }

        switchReverse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchReverse.isChecked()){
                    Liquid.ReverseInput = 1;
                }else{

                    Liquid.ReverseInput = 0;
                }
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Filename = AccountNumber + "_"+"reading"+"_"+ Liquid.RemoveSpecialCharacter(ReadingDate) + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                    mFile = mLiquidFile.Directory(Liquid.SelectedId,Liquid.RemoveSpecialCharacter(Filename), Subfolder);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    startActivityForResult(intent, CAM_REQUEST);

                } catch (Exception e) {
                    Liquid.ShowMessage(getApplicationContext(), e.toString());
                }
            }
        });

        etxtReading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                focusEditText = 1;
                return false;
            }
        });


        txtDemand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                focusEditText = 2;
                return false;
            }
        });

        etxtComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                focusEditText = 0;
                return false;
            }
        });

        etxtReading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Liquid.ReverseInput == 1){
                    etxtReading.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        FloatButtons();
        ShowReprint();
        BottomNavigation();

        if(Liquid.HideKeyboard == 1){
            mEditTextData = new EditText[3];
            mEditTextData[0] = etxtReading;
            mEditTextData[1] = txtDemand;
            mEditTextData[2] = etxtComment;
            mLiquid.hideSoftKeyboard(mEditTextData);
        }



    }


    public void OpenGallery(){
        Liquid.SelectedCategory = "reading";
        Liquid.ReadingDate = ReadingDate;
        Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
        startActivity(i);
    }
    public void ShowPreviosReading(){
        Liquid.showDialogInfo(ReadingActivity.this, "Details", "Previous Reading : " + previous_reading);
        check_previous = "True";
    }

    public void DeleteReverse(){

        try{
            switch ( focusEditText ){
                case 1:
                    etxtReading.setText(Liquid.removeFirstChar(etxtReading.getText().toString()));
                    break;
                case 2:
                    txtDemand.setText(Liquid.removeFirstChar(etxtReading.getText().toString()));
                    break;
            }
        }catch (Exception e){
            Log.e(TAG,"Tristan Gary Leyesa ",e);
        }
    }
    public void FloatButtons(){
        btnPreviousReading = (FloatingActionButton) findViewById(R.id.btnPreviousReading);
        btnReprint = (FloatingActionButton) findViewById(R.id.btnReprint);
        btnGallery = (FloatingActionButton) findViewById(R.id.btnGallery);
        btnDelete = (FloatingActionButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteReverse();

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        btnPreviousReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPreviosReading();
            }
        });

        btnReprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reprint();
            }
        });
    }
    public void BottomNavigation(){
        btnGallery.setVisibility(View.GONE);
        btnReprint.setVisibility(View.GONE);
        btnPreviousReading.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

        AHBottomNavigationItem item1 =  new AHBottomNavigationItem("Reprint", R.drawable.ic_action_printed, R.color.colorAccent);
        AHBottomNavigationItem item2 =  new AHBottomNavigationItem("Prev Reading", R.drawable.ic_action_previous, R.color.colorAccent);
        AHBottomNavigationItem item3 =  new AHBottomNavigationItem("Gallery", R.drawable.ic_action_gallery, R.color.colorAccent);
        AHBottomNavigationItem item4 =  new AHBottomNavigationItem("Delete", R.drawable.ic_action_delete, R.color.colorAccent);
        mBottomNavigationView.addItem(item1);
        mBottomNavigationView.addItem(item2);
        mBottomNavigationView.addItem(item3);
        mBottomNavigationView.addItem(item4);

        mBottomNavigationView.setAccentColor(R.color.colorAccent);
        mBottomNavigationView.setInactiveColor(R.color.colorAccent);
        int size = mBottomNavigationView.getItemsCount();
        mBottomNavigationView.setSelected(false);

        mBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        if (etxtReading.getText().toString().equals("")) {
                            Liquid.showDialogError(ReadingActivity.this,"Invalid","This consumer is still unread.");
                        }else{
                            Reprint();
                        }

                        break;
                    case 1:
                        ShowPreviosReading();
                        break;
                    case 2:
                        OpenGallery();
                        break;
                    case 3:
                        DeleteReverse();
                        break;

                        default:
                }
                return true;
            }
        });

    }


    public void ShowReprint(){
        if (etxtReading.getText().toString().equals("")) {
            btnReprint.setVisibility(View.GONE);
        }else{
            btnReprint.setVisibility(View.VISIBLE);
        }

    }
    public String WrapAround(String PreviousReading, String Reading) {

        Reading = Reading != "" ? Reading : "0";
        double dMultiplier = Double.parseDouble(multiplier);
        double dPreviousReading = Double.parseDouble(PreviousReading);
        double dReading = Double.parseDouble(Reading);
        if (Reading.contains(".")) {

            String[] ReadingSplit =  Reading.split("\\.");

            Reading = ReadingSplit[0];
        }
        if (PreviousReading.contains(".")) {
            String[] ReadingSplit =  PreviousReading.split("\\.");
            PreviousReading = ReadingSplit[0];
        }
        int DialLength2 = Reading.length();
        int DialLength = PreviousReading.length();
        String Consumption = "";


        if(DialLength2 == 2 || DialLength2 == 1 || DialLength2 == 3){
            switch (DialLength) {
                case 4:
                    Consumption = String.valueOf(((9999 - dPreviousReading) + dReading + 1) * dMultiplier);
                    break;
                case 5:
                    Consumption = String.valueOf(((99999 - dPreviousReading) + dReading + 1) * dMultiplier);
                    break;
                case 6:
                    Consumption = String.valueOf(((999999 - dPreviousReading) + dReading + 1)* dMultiplier);
                    break;
                default :
                    Consumption = GetKWH(multiplier, PreviousReading, Reading);
            }
        }else{
            Consumption = GetKWH(multiplier, PreviousReading, Reading);
        }


        return Consumption;

    }

    public String GetKWH(String Multiplier, String PreviousReading, String Reading) {

        Reading = Reading.equals("") ? "0" : Reading;
        double KWH = 0;
        String Consumption = "";

        double mMultiplier = Double.parseDouble(Multiplier);
        double mPreviousReading = Double.parseDouble(PreviousReading);
        double mReading = Double.parseDouble(Reading);

        KWH = mReading - mPreviousReading;

        KWH = KWH * mMultiplier;
//ALEX - KWH from rounddown to round Up // 010318
        Consumption = String.valueOf(Liquid.RoundUp(KWH));
        return Consumption;
    }
    public String AverageIleco2Validation(String Remarks) {
        String Average_Reading = "False";
        if(RemarksCode.equals("0")){
            save_only = false;
        }
        if (Remarks.contains("*")) {
            Average_Reading = "False";
            save_only = true;
            if(Reading.equals("")){
                Present_Consumption = "0";
            }
            if (RemarksCode.equals("9") && !Reading.equals("") ||
                    RemarksCode.equals("10") && !Reading.equals("") ||
                    RemarksCode.equals("11") && !Reading.equals("") ||
                    RemarksCode.equals("12") && !Reading.equals("") ||
                    RemarksCode.equals("13") && !Reading.equals("") ||
                    RemarksCode.equals("14") && !Reading.equals("") ||
                    RemarksCode.equals("15") && !Reading.equals("") ||
                    RemarksCode.equals("16") && !Reading.equals("") ||
                    RemarksCode.equals("17") && !Reading.equals("") ||
                    RemarksCode.equals("18") && !Reading.equals("") ||
                    RemarksCode.equals("20") && !Reading.equals("") ||
                    RemarksCode.equals("21") && !Reading.equals("") ||
                    RemarksCode.equals("24") && !Reading.equals("") ||
                    RemarksCode.equals("25") && !Reading.equals("") ||
                    RemarksCode.equals("26") && !Reading.equals("") ||
                    RemarksCode.equals("35") && !Reading.equals("") ||
                    RemarksCode.equals("42") && !Reading.equals("")
                    ) {
                Average_Reading = "False";
                save_only = true;
            } else if (RemarksCode.equals("28")) {
                Average_Reading = "False";
                save_only = false;
            }
        }
        return Average_Reading;
    }
    public String AverageValidation(String Remarks) {
        String Average_Reading = "False";
        if (Remarks.contains("*")) {
            Average_Reading = "True";
            if (RemarksCode.equals("9") && !Reading.equals("") ||
                    RemarksCode.equals("10") && !Reading.equals("") ||
                    RemarksCode.equals("11") && !Reading.equals("") ||
                    RemarksCode.equals("12") && !Reading.equals("") ||
                    RemarksCode.equals("13") && !Reading.equals("") ||
                    RemarksCode.equals("14") && !Reading.equals("") ||
                    RemarksCode.equals("15") && !Reading.equals("") ||
                    RemarksCode.equals("16") && !Reading.equals("") ||
                    RemarksCode.equals("17") && !Reading.equals("") ||
                    RemarksCode.equals("18") && !Reading.equals("") ||
                    RemarksCode.equals("20") && !Reading.equals("") ||
                    RemarksCode.equals("21") && !Reading.equals("") ||
                    RemarksCode.equals("24") && !Reading.equals("") ||
                    RemarksCode.equals("25") && !Reading.equals("") ||
                    RemarksCode.equals("26") && !Reading.equals("") ||
                    RemarksCode.equals("35") && !Reading.equals("") ||
                    RemarksCode.equals("42") && !Reading.equals("")
                    ) {
                Average_Reading = "False";
            } else if (RemarksCode.equals("28")) {
                Average_Reading = "False";
            }
        }
        return Average_Reading;
    }

    public boolean SaveReadingLogs(){
        boolean result = false;

        result = ReadingModel.doReadingLogs(
                Client, //ok
                AccountNumber, //ok
                AccountNumber, //ok
                JobId, //ok
                AccountName, //ok
                route, //ok
                itinerary, //ok
                MeterNumber, //ok
                serial, //ok
                previous_reading, //ok
                Reading, //ok
                previous_consumption, //ok
                Present_Consumption, //computation
                previous_reading_date, //ok
                present_reading_date, //ok
                duedate, // if there value currentdate +6 //ok
                discondate, // duedate +2 days //ok
                ReadingDate, //ok
                BillYear, //ok
                BillMonth, //ok
                BillDate, //ok
                month, //ok
                day, //ok
                year, //ok
                Demand, // New TextBox
                reactive, // ok
                powerfactor, //ok
                kw_cummulative, //ok
                reading1, //ok
                reading2, //ok
                reading3, //ok
                reading4, //ok
                reading5, //ok
                reading6, //ok
                reading7, //ok
                reading8, //ok
                reading9, //ok
                reading10, //ok
                iwpowerfactor, //ok
                demand_consumption, //ok
                reactive_consumption, //ok
                Averange_Consumption, //ok
                Average_Reading, //ok
                multiplier, //ok
                Meter_Box, //ok
                Demand_Reset, //ok
                Test_Block, //ok
                RemarksCode,
                remarks_abbreviation, // not OK
                Remarks, //ok
                Comment, //ok
                Reader_ID, //ok
                meter_reader, //ok
                Reading_Attempt, //ok
                Print_Attempt, // ok but no print
                force_reading, //ok
                r_latitude, //ok
                r_longitude, //ok
                printlatitude, //ok
                printlongitude, //ok
                improbable_reading, //ok
                negative_reading, //ok
                change_reading, //ok
                cg_vat_zero_tag, //ok
                reading_remarks, //ok
                old_key, // not textbox
                new_key, // not textbox
                transfer_data_status, // SMS or LTE function
                upload_status, //upload function status
                code, //ok
                area,
                rate_code, //ok
                cummulative_multiplier, //ok
                oebr_number, //ok
                Sequence, //ok
                Reading_TimeStamp, //ok
                Print_TimeStamp, //ok
                timestamp, //ok
                bill_number, // ok
                GenerationSystem = String.valueOf(LiquidBilling.total_gen_sys_charge), // ok
                BenHost, // Bill PECO
                GRAM = String.valueOf(LiquidBilling.total_psalm_daa), // ok
                ICERA = String.valueOf(LiquidBilling.total_gram_icera_daa_erc), // ok
                PowerArtReduction = String.valueOf(LiquidBilling.total_power_act_reduc), // ok
                TransmissionDelivery = String.valueOf(LiquidBilling.total_trans_del_charge_1), // ok
                TransmissionDelivery2 = String.valueOf(LiquidBilling.total_trans_del_charge_2), // ok
                System_Loss = String.valueOf(LiquidBilling.total_sys_loss_1),// ok
                Gen_Trans_Rev = String.valueOf(LiquidBilling.total_gen_trans), // ok
                DistributionNetwork = String.valueOf(LiquidBilling.total_distrib_net_charge_1), // ok
                DistributionNetwork2 = String.valueOf(LiquidBilling.total_distrib_net_charge_2),// ok
                DistributionNetwork3 = String.valueOf(LiquidBilling.total_distrib_net_charge_3), // ok
                RetailElectricService = String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1), // ok
                RetailElectricService2 = String.valueOf(LiquidBilling.total_ret_elec_serv_charge_2), // ok
                Metering_cust = String.valueOf(LiquidBilling.total_met_charge_1), // ok
                Metering_cust_2 = String.valueOf(LiquidBilling.total_met_charge_2), // ok
                Metering_kwh, // Bill
                loan = String.valueOf(LiquidBilling.total_final_loan_con), // Bill
                RFSC = String.valueOf(LiquidBilling.total_rfsc), // ok
                Distribution_Rev = String.valueOf(LiquidBilling.total_distribution_revenue), // ok
                MissionaryElectrification = String.valueOf(LiquidBilling.total_missionary_elec), // ok
                EnvironmentCharge = String.valueOf(LiquidBilling.total_environmental_charge), // ok
                NPC_StrandedDebts = String.valueOf(LiquidBilling.total_npc_stran_deb),  // ok
                NPC_StrandedCost = String.valueOf(LiquidBilling.total_npc_stran_cos), // ok
                DUsCost, // ok
                DCDistributionCharge, // ok
                DCDemandCharge, // ok
                TCTransSystemCharge, // ok
                SCSupplySysCharge, // ok
                equal_tax = String.valueOf(LiquidBilling.equalization_taxes), // ok
                CrossSubsidyRemoval  = String.valueOf(LiquidBilling.cross_subsidy_removal), // ok
                Universal_Charges = String.valueOf(LiquidBilling.total_universal), // ok
                Lifeline_Charge = String.valueOf(LiquidBilling.total_lifeline), // ok
                InterclassCrossSubsidy  = String.valueOf(LiquidBilling.total_interclass), // ok
                SeniorCitizenSubsidy = String.valueOf(LiquidBilling.total_senior), // ok
                ICCS_Adjustment, // ok
                ICCrossSubsidyCharge = String.valueOf(LiquidBilling.total_me_renewable_energy_dev), // ok //ME Renewable Energy Dev ILECO II
                FitAllCharge = String.valueOf(LiquidBilling.total_fit_all), // ok
                PPD_Adjustment = String.valueOf(LiquidBilling.total_prompt_payment_disc_adj), // ok
                GenerationCostAdjustment, // ok
                PowerCostAdjustment, // ok
                Other_Rev = String.valueOf(LiquidBilling.total_other_charges), // ok
                GenerationVat = String.valueOf(LiquidBilling.total_generation), // ok
                TransmissionVat = String.valueOf(LiquidBilling.total_transmission), // ok
                SystemLossVat = String.valueOf(LiquidBilling.total_sys_loss_2), // ok
                DistributionVat = String.valueOf(LiquidBilling.total_distribution), // ok
                OtherVat = String.valueOf(LiquidBilling.total_others), // ok
                Government_Rev = String.valueOf(LiquidBilling.total_government_revenue),  // ok
                CurrentBill = String.valueOf(LiquidBilling.total_current_bill), // ok
                amountdue = String.valueOf(LiquidBilling.total_amount_due), // ok
                overdue, // ok
                franchise_tax, // Bill
                coreloss, //ok
                surcharge, //ok
                rentalfee,
                delivered, //ok
                check_previous, // Taging if previous is check
                ispn,  //PECO
                SCD = String.valueOf(LiquidBilling.total_senior), //ok
                pnrecvdte, //PECO
                pnrecvby, //PECO
                recvby, // Delivery
                hash, //PECO
                isreset, // DI ALAM NI ALEX
                isprntd, // DI ALAM NI ALEX
                meter_count, //ok
                delivery_id, // Delivery
                delivery_remarks, // Delivery
                delivery_comment, // Delivery
                reading_signature, // Delivery
                real_property_tax,  //Bill
                cc_rstc_refund, //Bill
                cc_rstc_refund2, //Bill
                moa, //Bill
                eda, //Bill
                ModifiedDate,
                ModifiedBy
        );

        return result;
    }

    public boolean SaveReading() {
        boolean result = false;

        result = ReadingModel.doReading(
                Client, //ok
                AccountNumber, //ok
                AccountNumber, //ok
                JobId, //ok
                AccountName, //ok
                route, //ok
                itinerary, //ok
                MeterNumber, //ok
                serial, //ok
                previous_reading, //ok
                Reading, //ok
                previous_consumption, //ok
                Present_Consumption, //computation
                previous_reading_date, //ok
                present_reading_date, //ok
                duedate, // if there value currentdate +6 //ok
                discondate, // duedate +2 days //ok
                ReadingDate, //ok
                BillYear, //ok
                BillMonth, //ok
                BillDate, //ok
                month, //ok
                day, //ok
                year, //ok
                Demand, // New TextBox
                reactive, // ok
                powerfactor, //ok
                kw_cummulative, //ok
                reading1, //ok
                reading2, //ok
                reading3, //ok
                reading4, //ok
                reading5, //ok
                reading6, //ok
                reading7, //ok
                reading8, //ok
                reading9, //ok
                reading10, //ok
                iwpowerfactor, //ok
                demand_consumption, //ok
                reactive_consumption, //ok
                Averange_Consumption, //ok
                Average_Reading, //ok
                multiplier, //ok
                Meter_Box, //ok
                Demand_Reset, //ok
                Test_Block, //ok
                RemarksCode,
                remarks_abbreviation, // not OK
                Remarks, //ok
                Comment, //ok
                Reader_ID, //ok
                meter_reader, //ok
                Reading_Attempt, //ok
                Print_Attempt, // ok but no print
                force_reading, //ok
                r_latitude, //ok
                r_longitude, //ok
                printlatitude, //ok
                printlongitude, //ok
                improbable_reading, //ok
                negative_reading, //ok
                change_reading, //ok
                cg_vat_zero_tag, //ok
                reading_remarks, //ok
                old_key, // not textbox
                new_key, // not textbox
                transfer_data_status, // SMS or LTE function
                upload_status, //upload function status
                code, //ok
                area,
                rate_code, //ok
                cummulative_multiplier, //ok
                oebr_number, //ok
                Sequence, //ok
                Reading_TimeStamp, //ok
                Print_TimeStamp, //ok
                timestamp, //ok
                bill_number, // ok
                GenerationSystem = String.valueOf(LiquidBilling.total_gen_sys_charge), // ok
                BenHost, // Bill PECO
                GRAM = String.valueOf(LiquidBilling.total_psalm_daa), // ok
                ICERA = String.valueOf(LiquidBilling.total_gram_icera_daa_erc), // ok
                PowerArtReduction = String.valueOf(LiquidBilling.total_power_act_reduc), // ok
                TransmissionDelivery = String.valueOf(LiquidBilling.total_trans_del_charge_1), // ok
                TransmissionDelivery2 = String.valueOf(LiquidBilling.total_trans_del_charge_2), // ok
                System_Loss = String.valueOf(LiquidBilling.total_sys_loss_1),// ok
                Gen_Trans_Rev = String.valueOf(LiquidBilling.total_gen_trans), // ok
                DistributionNetwork = String.valueOf(LiquidBilling.total_distrib_net_charge_1), // ok
                DistributionNetwork2 = String.valueOf(LiquidBilling.total_distrib_net_charge_2),// ok
                DistributionNetwork3 = String.valueOf(LiquidBilling.total_distrib_net_charge_3), // ok
                RetailElectricService = String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1), // ok
                RetailElectricService2 = String.valueOf(LiquidBilling.total_ret_elec_serv_charge_2), // ok
                Metering_cust = String.valueOf(LiquidBilling.total_met_charge_1), // ok
                Metering_cust_2 = String.valueOf(LiquidBilling.total_met_charge_2), // ok
                Metering_kwh, // Bill
                loan = String.valueOf(LiquidBilling.total_final_loan_con), // Bill
                RFSC = String.valueOf(LiquidBilling.total_rfsc), // ok
                Distribution_Rev = String.valueOf(LiquidBilling.total_distribution_revenue), // ok
                MissionaryElectrification = String.valueOf(LiquidBilling.total_missionary_elec), // ok
                EnvironmentCharge = String.valueOf(LiquidBilling.total_environmental_charge), // ok
                NPC_StrandedDebts = String.valueOf(LiquidBilling.total_npc_stranded_debts),  // ok
                NPC_StrandedCost = String.valueOf(LiquidBilling.total_stran_contract_cost), // ok
                DUsCost, // ok
                DCDistributionCharge, // ok
                DCDemandCharge, // ok
                TCTransSystemCharge, // ok
                SCSupplySysCharge, // ok
                equal_tax = String.valueOf(LiquidBilling.equalization_taxes), // ok
                CrossSubsidyRemoval  = String.valueOf(LiquidBilling.cross_subsidy_removal), // ok
                Universal_Charges = String.valueOf(LiquidBilling.total_universal), // ok
                Lifeline_Charge = String.valueOf(LiquidBilling.total_lifeline), // ok
                InterclassCrossSubsidy  = String.valueOf(LiquidBilling.total_interclass), // ok
                SeniorCitizenSubsidy = String.valueOf(LiquidBilling.total_senior), // ok
                ICCS_Adjustment, // ok
                ICCrossSubsidyCharge = String.valueOf(LiquidBilling.total_me_renewable_energy_dev), // ok //ME Renewable Energy Dev ILECO II
                FitAllCharge = String.valueOf(LiquidBilling.total_fit_all), // ok
                PPD_Adjustment = String.valueOf(LiquidBilling.total_prompt_payment_disc_adj), // ok
                GenerationCostAdjustment, // ok
                PowerCostAdjustment, // ok
                Other_Rev = String.valueOf(LiquidBilling.total_other_charges), // ok
                GenerationVat = String.valueOf(LiquidBilling.total_generation), // ok
                TransmissionVat = String.valueOf(LiquidBilling.total_transmission), // ok
                SystemLossVat = String.valueOf(LiquidBilling.total_sys_loss_2), // ok
                DistributionVat = String.valueOf(LiquidBilling.total_distribution), // ok
                OtherVat = String.valueOf(LiquidBilling.total_others_vat), // ok
                Government_Rev = String.valueOf(LiquidBilling.total_government_revenue),  // ok
                CurrentBill = String.valueOf(LiquidBilling.total_current_bill), // ok
                amountdue = String.valueOf(LiquidBilling.total_amount_due), // ok
                overdue, // ok
                franchise_tax, // Bill
                coreloss, //ok
                surcharge, //ok
                rentalfee,
                delivered, //ok
                check_previous, // Taging if previous is check
                ispn,  //PECO
                SCD = String.valueOf(LiquidBilling.total_senior), //ok
                pnrecvdte, //PECO
                pnrecvby, //PECO
                recvby, // Delivery
                hash, //PECO
                isreset, // DI ALAM NI ALEX
                isprntd, // DI ALAM NI ALEX
                meter_count, //ok
                delivery_id, // Delivery
                delivery_remarks, // Delivery
                delivery_comment, // Delivery
                reading_signature, // Delivery
                real_property_tax,  //Bill
                cc_rstc_refund, //Bill
                cc_rstc_refund2, //Bill
                moa, //Bill
                eda, //Bill
                ModifiedDate,
                ModifiedBy
        );

        return result;
    }

    public boolean doPrintBill() {
        boolean result = false;
        boolean result_logs = false;
        try {

            result = mLiquidPrintBill.pairPrinter();

            if(result == true){
                printlatitude = String.valueOf(mLiquidGPS.getLatitude());
                printlongitude = String.valueOf(mLiquidGPS.getLongitude());
                Print_TimeStamp = Liquid.currentDateTime();
                Print_Attempt = String.valueOf(Integer.parseInt(Print_Attempt) + 1);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public String ConsumptionValidation(String AvgKWH, String PreviousKWH, String KWH) {

        double percentage = 0.80;
        double consumption_percentage = 0;
        double high_consumption_range = 0;
        double low_consumption_range = 0;

        if(Double.parseDouble(KWH) == 0){
            return "ZERO CONSUMPTION";
        }
        if (Double.parseDouble(KWH) < 0) {
            return "NEGATIVE CONSUMPTION";
        }
        if(     AvgKWH.equals("0") ||
                AvgKWH.equals("1") ||
                AvgKWH.equals("2") ||
                AvgKWH.equals("3") ||
                AvgKWH.equals("4") ||
                AvgKWH.equals("5") ||
                AvgKWH.equals("6") ||
                AvgKWH.equals("7") ||
                AvgKWH.equals("8") ||
                AvgKWH.equals("9") ||
                AvgKWH.equals("10")){
            return "";
        }
        consumption_percentage = Double.valueOf(AvgKWH) * percentage;
        //consumption_percentage = Double.parseDouble(AvgKWH) * percentage;
        high_consumption_range = Double.valueOf(AvgKWH) + consumption_percentage;
        low_consumption_range = Double.valueOf(AvgKWH) - consumption_percentage;

        if (Double.parseDouble(KWH) <= low_consumption_range) {
            return "LOW CONSUMPTION";
        }
        if (Double.parseDouble(KWH) >= high_consumption_range) {
            return "HIGH CONSUMPTION";
        }

        return "";
    }

    public void Reprint(){
        try {
            if(!initializationForComputation("Reprint")){
                Liquid.showDialogError(this,"Invalid","The remarks is "+Remarks+" that's why it cannot be print!.");
                return;
            };

            switch(reading_remarks){
                case "LOW CONSUMPTION":
                    Liquid.showDialogError(this,"Invalid","Low Consumption cannot print!");
                    save_only = true;
                    return;

                case "HIGH CONSUMPTION":
                    break;
                case "NEGATIVE CONSUMPTION":

                    Present_Consumption = "0";
                    Liquid.showDialogError(this,"Invalid","Low Consumption cannot print!");
                    save_only = true;
                    return;
                case "ZERO CONSUMPTION":
                    Present_Consumption = "0";
                    Liquid.showDialogError(this,"Invalid","Zero Consumption cannot print!");
                   return;
                default:

            }

            Computation();
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result_logs = false;
                            ModifiedDate = Liquid.currentDateTime();
                            timestamp = Liquid.currentDateTime();
                            ModifiedBy = Liquid.User;
                            ReadingModel.UpdatePrintAttempt(Liquid.SelectedId,AccountNumber,Print_Attempt);
                            new PrintBill().execute();
                            result_logs = SaveReadingLogs();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;

                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you really want to reprint bill?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa",e);
        }



    }

    public void QuenedForSave() {
        if(save_only){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result = false;
                            boolean result_logs = false;
                            result = SaveReading();
                            result_logs = SaveReadingLogs();

                            if (result) {
                                Liquid.showDialogNext(ReadingActivity.this, "Valid", "Successfully Saved!");
                                LowConsumptionAttempt = 0;
                                HighConsumptionAttempt = 0;
                                NegativeConsumptionAttempt = 0;
                                ZeroConsumptionAttempt = 0;
                            } else {
                                Liquid.showDialogError(ReadingActivity.this, "Invalid", "Unsuccessfully Saved!");

                            }
                            getDeviceLocation();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                           dialog.dismiss();

                            break;

                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Save?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }else{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result = false;
                            boolean result_logs = false;
                            result = SaveReading();
                            result_logs = SaveReadingLogs();

                            if (result) {
                                Liquid.showDialogNext(ReadingActivity.this, "Valid", "Successfully Saved!");
                                LowConsumptionAttempt = 0;
                                HighConsumptionAttempt = 0;
                                NegativeConsumptionAttempt = 0;
                            } else {
                                Liquid.showDialogError(ReadingActivity.this, "Invalid", "Unsuccessfully Saved!");

                            }
                            getDeviceLocation();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            result = false;
                            result_logs = false;
                            new PrintBill().execute();
                            result = SaveReading();
                            result_logs = SaveReadingLogs();
                            LowConsumptionAttempt = 0;
                            HighConsumptionAttempt = 0;
                            NegativeConsumptionAttempt = 0;
                            getDeviceLocation();
                            break;

                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Save only or Save & Print Bill?").setPositiveButton("Save", dialogClickListener)
                    .setNegativeButton("Save & Print Bill", dialogClickListener).show();

        }

    }




    public void ConsumptionFindingsNotification(String findings) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        etxtReading.setText("");
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Consumption is " + findings + " Please check the READING again!").
                setPositiveButton("Done", dialogClickListener).show();
        ;
    }

    public void Computation(){
        LiquidBilling mLiquidBilling = new LiquidBilling();
        switch (Liquid.Client){
            case "batelec2":
                mLiquidBilling.Batellec2ElectricBillingComputaion(
                        Present_Consumption,
                        Demand,
                        AccountType,
                        classification,
                        BillingCycle,
                        arrears,
                        senior_tagging,
                        eda_tagging
                );
                break;
            case "iselco2":
                mLiquidBilling.Iselco2ElectricBillingComputaion(
                        Present_Consumption,
                        Demand,
                        AccountType,
                        classification,
                        BillingCycle,
                        arrears,
                        senior_tagging,
                        eda_tagging
                );
                break;
            case "ileco2":
                mLiquidBilling.Ileco2ElectricBillingComputaion(
                        Present_Consumption,
                        Demand,
                        AccountType,
                        classification,
                        BillingCycle,
                        arrears,
                        eda_tagging,
                        senior_tagging
                );
                break;
        }
    }

    public boolean initializationForComputation(String type){
        //Declaration
        if(!Liquid.CheckGPS(this)){
            Liquid.showDialogInfo(this,"Invalid","Please enable GPS!");
            return false;
        }
        save_only = false;
        Comment = etxtComment.getText().toString();
        Reader_ID = Liquid.User;
        meter_reader = Liquid.User;
        Reading = etxtReading.getText().toString();
        Demand = txtDemand.getText().toString();
        Reading_Attempt = String.valueOf(Integer.parseInt(Reading_Attempt) + 1);
        String LastDial = String.valueOf(previous_reading.charAt(0));
        Reading_TimeStamp = Liquid.currentDateTime();
        timestamp = Reading_TimeStamp;
        ModifiedDate = Reading_TimeStamp;
        ModifiedBy = Liquid.User;
        reading1 = Reading;
        reading2 = Demand;
        String[] RemarksData = RemarksValue.split("-");
        RemarksCode = RemarksData[0];
        Remarks = RemarksData[1];

        //Reading Location
        r_latitude = String.valueOf(mLiquidGPS.getLatitude());
        r_longitude = String.valueOf(mLiquidGPS.getLongitude());

        if(type.equals("Reprint")){
            if (LastDial.equals("9") ) {
                Present_Consumption = WrapAround(previous_reading, Reading);
                Remarks = "WRAP AROUND MTR";
                RemarksCode = "28";
            } else {
                Present_Consumption = GetKWH(multiplier, previous_reading, Reading);
            }
            if (!RemarksCode.equals("0") || !RemarksCode.equals("28")) {
                //avg validation
                switch(Liquid.Client){
                    case "ileco2":
                            Average_Reading = AverageIleco2Validation(Remarks);
                            reading_remarks = ConsumptionValidation(Averange_Consumption, previous_consumption, Present_Consumption);
                            return true;

                    default:
                        Average_Reading = AverageValidation(Remarks);
                        Present_Consumption = Averange_Consumption;
                        break;
                }
            }
            reading_remarks = ConsumptionValidation(Averange_Consumption, previous_consumption, Present_Consumption);
            return true;
        }
        if (ImageCount == 0) {
            Liquid.showDialogError(this, "Invalid", "Please take picture!");
            return false;
        }

        if (Remarks.isEmpty() || Remarks.equals("")) {
            Liquid.showDialogError(this, "Invalid", "Incomplete Information!");
            return false;
        }

        if (Reading.equals("") && RemarksCode.equals("0")) {
            Liquid.showDialogError(this, "Invalid", "Please put remarks if there is a problem for reading the meter!");
            return false;
        }
        //Created emergency for ileco2 this will not print every consumer with remarks
        /*if(Reading.equals("") && !RemarksCode.equals("0") || !Reading.equals("") && !RemarksCode.equals("0")){
            save_only = true;
            return true;
        }*/

        if (LastDial.equals("9")) {
            Present_Consumption = WrapAround(previous_reading, Reading);
            Remarks = "WRAP AROUND MTR";
            RemarksCode = "28";
        } else {
            Present_Consumption = GetKWH(multiplier, previous_reading, Reading);
        }

        if (!RemarksCode.equals("0") || !RemarksCode.equals("28")) {
            //avg validation

            switch(Liquid.Client){
                    case "ileco2":

                        Average_Reading = AverageIleco2Validation(Remarks);
                        reading_remarks = ConsumptionValidation(Averange_Consumption, previous_consumption, Present_Consumption);
                        return true;

                    default:
                        Average_Reading = AverageValidation(Remarks);
                        Present_Consumption = Averange_Consumption;
            }

        }

        //Consumption Validation
        reading_remarks = ConsumptionValidation(Averange_Consumption, previous_consumption, Present_Consumption);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;

                case R.id.action_form_submit:
                    if(!initializationForComputation("")){
                        return false;
                    }

                    Computation();

                    switch (reading_remarks) {
                        case "LOW CONSUMPTION":
                            if (LowConsumptionAttempt == 0) {
                                LowConsumptionAttempt = 1;
                                ConsumptionFindingsNotification(reading_remarks);
                            } else {
                                save_only = true;
                                QuenedForSave();
                            }
                            break;
                        case "HIGH CONSUMPTION":
                            if (HighConsumptionAttempt == 0) {
                                HighConsumptionAttempt = 1;
                                ConsumptionFindingsNotification(reading_remarks);
                            } else {
                                QuenedForSave();
                            }

                            break;
                        case "NEGATIVE CONSUMPTION":
                            if (NegativeConsumptionAttempt == 0) {
                                NegativeConsumptionAttempt = 1;
                                ConsumptionFindingsNotification(reading_remarks);
                            } else {
                                save_only = true;
                                QuenedForSave();
                            }
                            break;
                        case "ZERO CONSUMPTION":
                            if (ZeroConsumptionAttempt == 0) {
                                ZeroConsumptionAttempt = 1;
                                ConsumptionFindingsNotification(reading_remarks);
                            } else {
                                save_only = true;
                                QuenedForSave();
                            }
                            break;
                        default:
                            QuenedForSave();
                    }


                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }

    public void GetReadingRemarks() {
        try {
            Cursor result = workModel.GetReadingRemarks("");
            if (result.getCount() == 0) {
                return;
            }

            while (result.moveToNext()) {
                HashMap<String, String> data = new HashMap<>();
                ListOfRemarks.add(result.getString(0) + "-" + result.getString(1));

            }

            AdapterSpinnerRemarks = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListOfRemarks);
            AdapterSpinnerRemarks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRemarks.setAdapter(AdapterSpinnerRemarks);

            spinnerRemarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RemarksValue = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

            Log.e(TAG, "Error : ", e);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;

                    result = ReadingModel.doSubmitPicture(Client,
                            AccountNumber,
                            ServiceType,
                            Liquid.RemoveSpecialCharacter(Filename),
                            Liquid.currentDateTime(),
                            Liquid.currentDateTime(),
                            Liquid.User,
                            Liquid.User,
                            ReadingDate
                    );

                    if (result == true) {

                        Liquid.resizeImage(mFile.getAbsolutePath(), 0.80, 0.80);
                        Liquid.ShowMessage(getApplicationContext(), "Save Image Success");
                        mUri.add(Uri.fromFile(mFile));
                        tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                        ImageCount = mUri.size();

                    }

                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
        }

    }


    public void MeterCount(String meter_count){
        switch(meter_count){
            case "1":
                txtDemand.setVisibility(View.GONE);
                break;
            case "2":
                txtDemand.setVisibility(View.VISIBLE);
                break;
                default:
        }
    }
    public void GetReadAndBillData(String job_id, String AccountNumber) {
        try {

            Cursor result = workModel.GetReadAndBillJobOrderDetails(job_id, AccountNumber);

            if (result.getCount() == 0) {
                return;
            }
            while (result.moveToNext()) {
                //Customer Data

                HashMap<String, String> data = new HashMap<>();
                Status = result.getString(9);
                AccountNumber = result.getString(30);
                AccountName = result.getString(17);
                MeterNumber = result.getString(37);
                AccountType = result.getString(7);
                Complete_Address = result.getString(29);
                Sequence = result.getString(35);
                Date = result.getString(71);
                ReadingDate = result.getString(54);
                String[] ReadingDateSplit = ReadingDate.split("-");
                JobId = result.getString(2);
                route = result.getString(11);
                itinerary = result.getString(13);
                serial = result.getString(63);
                previous_reading = !result.getString(47).equals("") ? result.getString(47) : "0";
                previous_reading = Liquid.FixDecimal(previous_reading);
                previous_consumption = !result.getString(52).equals("") ? result.getString(52) : "0";
                present_reading_date = Liquid.currentDate();
                previous_reading_date = !result.getString(53).equals("") ? result.getString(53) : ReadingDate;
                classification = result.getString(8);
                BillMonth = result.getString(66);
                BillYear = result.getString(68);
                BillDate = result.getString(67);
                month = BillMonth;
                year = BillYear;
                day = ReadingDateSplit[1];
                Averange_Consumption = !result.getString(46).equals("") ? result.getString(46) : "0";
                multiplier = !result.getString(40).equals("") ? result.getString(40) : "1";
                multiplier = Liquid.FixDecimal(multiplier);

                Meter_Box = result.getString(36);
                code = result.getString(1);
                rate_code = result.getString(7);
                coreloss = !result.getString(44).equals("") ? result.getString(44) : "0";
                meter_count = !result.getString(42).equals("") ? result.getString(42) : "1";
                arrears = !result.getString(43).equals("") ? result.getString(43) : "0";
                overdue = arrears;
                senior_tagging = result.getString(56);
                eda_tagging = result.getString(70);
                change_meter = !result.getString(58).equals("") ? result.getString(58) : "0";
                interest = !result.getString(62).equals("") ? result.getString(62) : "0";
                surcharge = interest;
                ShareCap =  !result.getString(78).equals("") ? result.getString(78) : "0";
                rentalfee = !result.getString(55).equals("") ? result.getString(55) : "0";
                OtherBill = !result.getString(83).equals("") ? result.getString(83) : "0";
                bill_number = !result.getString(65).equals("") ? result.getString(65) : year + BillMonth + AccountNumber;
                Liquid.rowid = result.getString(84);

                //Discon and DueDate
                duedate = result.getString(64);

                if (duedate.equals("")) {
                    duedate = Liquid.getDueDate(present_reading_date);
                }
                discondate = Liquid.getDisconDate(duedate);
                present_reading_date = Liquid.currentDate();
                BillingCycle = year + "-" + BillMonth;
                //Reading


            }


            Details =   "Account No.: " + AccountNumber + "\n" +
                        "Name            : " + AccountName + "\n" +
                        "Address        : " + Complete_Address + "\n" +
                        "Meter No.   : " + MeterNumber + "\n" +
                        "Sequence   : " + Sequence + "\n" +
                        "Status    : " + Status + "\n" +
                        "Type             : " + AccountType + "\n";

            txtDetails.setText(Details);
            MeterCount(meter_count);

        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
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
                mLiquidGPS = new LiquidGPS(ReadingActivity.this);

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
    private void GetReadingDetails(String job_id, String AccountNumber) {

        try {

            Cursor result = ReadingModel.GetReadingDetails(job_id, AccountNumber);

            if (result.getCount() == 0) {
                Reading  = "";
                etxtReading.setText(Reading);
                Demand = "0";
                txtDemand.setText(Demand);
                reactive = "0";
                powerfactor = "0";
                kw_cummulative = "0";
                reading1 = Reading;
                reading2 = Demand;
                reading3 = reactive;
                reading4 = powerfactor;
                reading5 = kw_cummulative;
                reading6 = "";
                reading7 = "";
                reading8 = "";
                reading9 = "";
                reading10 = "";
                iwpowerfactor ="";
                demand_consumption = Demand;
                reactive_consumption = reactive;
                etxtComment.setText("");
                RemarksValue = "0-NO FIELD FINDINGS";
                spinnerRemarks.setSelection(AdapterSpinnerRemarks.getPosition(RemarksValue));

                Print_Attempt = "0";
                Reading_Attempt  = "0";
                Reader_ID = Liquid.User;
                meter_reader = Liquid.User;
                RemarksCode = "0";
                Remarks = "NO FIELD FINDINGS";
                reading_remarks = "";
                Reading_TimeStamp  = "";
                Print_TimeStamp = "";
                r_latitude = "0.0";
                r_longitude  = "0.0";
                check_previous = "0";

                Present_Consumption  = "";
                etxtReading.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                etxtReading.setTextColor(getResources().getColor(R.color.colorAccent));

                return;
            }

            while (result.moveToNext()) {
                //Customer Data
                HashMap<String, String> data = new HashMap<>();
                Reading  = result.getString(10);
                etxtReading.setText(Reading);
                Demand = result.getString(24).equals(null) || result.getString(24).equals("") ? result.getString(24) : "";
                txtDemand.setText(Demand);
                reactive = result.getString(25);
                powerfactor = result.getString(26);
                kw_cummulative = result.getString(27);
                reading1 = Reading;
                reading2 = Demand;
                reading3 = reactive;
                reading4 = powerfactor;
                reading5 = kw_cummulative;
                reading6 = result.getString(33);
                reading7 = result.getString(34);
                reading8 = result.getString(35);
                reading9 = result.getString(36);
                reading10 = result.getString(27);
                iwpowerfactor = result.getString(38);
                demand_consumption = Demand;
                reactive_consumption = reactive;
                etxtComment.setText(result.getString(50));
                RemarksValue = result.getString(47) + "-" + result.getString(49);
                spinnerRemarks.setSelection(AdapterSpinnerRemarks.getPosition(RemarksValue));
                Print_Attempt = result.getString(54);
                Reading_Attempt  = result.getString(53);
                Reader_ID = result.getString(51);
                meter_reader = result.getString(52);
                RemarksCode = result.getString(47);
                Remarks = result.getString(49);
                reading_remarks = result.getString(64);
                Reading_TimeStamp  = result.getString(75);
                Print_TimeStamp = result.getString(76);
                r_latitude = result.getString(56);
                r_longitude  = result.getString(57);
                check_previous = result.getString(135);
                Present_Consumption  = result.getString(12);
                etxtReading.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                etxtReading.setTextColor(getResources().getColor(R.color.colorPrimary));

            }

        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }

    }

    private void GetImages(String JobId) {
        mUri.clear();

        tsImageCounter.setCurrentText("0");
        mImages = Liquid.getDiscPicture(JobId,Subfolder);
        if (!mImages.exists() && !mImages.mkdirs()) {

            Liquid.ShowMessage(this, "Can't create directory to save image");
        } else {
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for (int a = 0; a < listFile.length; a++) {
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");

                if (seperated[0].equals(Liquid.RemoveSpecialCharacter(AccountNumber)) && seperated[2].equals(Liquid.RemoveSpecialCharacter(ReadingDate))) {
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }
            //ImageCount = mUri.size();

            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));

        }
    }

    public class GetNextConsumer extends AsyncTask<String,Void,Void> {
        boolean result = false;
        Cursor result_data = null;
        String mAccountNumber;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ReadingActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                switch(Integer.parseInt(params[0])){
                    case 22:

                        result_data =  ReadingModel.get_next_customer_sequence(Liquid.SelectedId, Liquid.rowid);
                        break;
                    case 21:
                        result_data = ReadingModel.get_prev_customer_sequence(Liquid.SelectedId, Liquid.rowid);
                        break;
                }
                if (result_data.getCount() == 0) {
                    return null;
                }

                while (result_data.moveToNext()) {
                    //Customer Data
                    //HashMap<String, String> data = new HashMap<>();
                    mAccountNumber = result_data.getString(1);
                    Liquid.rowid = result_data.getString(2);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AccountNumber = mAccountNumber;
            GetReadAndBillData(Liquid.SelectedId, AccountNumber);
            GetReadingDetails(Liquid.SelectedId, AccountNumber);
            GetImages(Liquid.SelectedId);
            pDialog.dismiss();
        }
    }
    public class PrintBill extends AsyncTask<Void,Void,Void> {
        boolean result = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadingActivity.this);
            pDialog.setMessage("Printing...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                result = doPrintBill();
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

                result = SaveReading();
                result_logs = SaveReadingLogs();
                Liquid.showDialogNext(ReadingActivity.this, "Valid", "Successfully Print!");
                //ShowReprint();
            } else {
                Liquid.showDialogError(ReadingActivity.this, "Invalid", "Unsuccessfully Print!");
            }
            pDialog.dismiss();

        }
    }




}
