package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;

import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;


import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidPrintBill;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Date;
import java.util.HashMap;


public class ReadingV2Activity extends BaseActivity {

    private static final String TAG = ReadingV2Activity.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private String Latitude = "";
    private String Longitude = "";
    private Liquid mLiquid;
    private EditText[] mEditTextData;
    private AHBottomNavigation mBottomNavigationView;
    private TextView txtHeader;
    private TextView txtDetails;
    private TextView txtAccountNumber;
    private TextView txtAccountName;
    private TextView txtAccountAddress;
    private TextView txtMeterNumber;
    private TextView txtAccountSequence;
    private TextView txtAccountStatus;
    private TextView txtAccountType;
    private Button btnNext;
    private CardView CardConsumerInformation;
    private EditText etxtReading;
    private Switch switchReverse;
    private MenuItem searchMenuItem;
    private ProgressDialog pDialog;
    private LiquidGPS mLiquidGPS;
    int focusEditText = 1;
    int ReadingAttempt = 0;
    //Validation
    int HighConsumptionAttempt = 0;
    int LowConsumptionAttempt = 0;
    int NegativeConsumptionAttempt = 0;
    int ZeroConsumptionAttempt = 0;

    //Camera

    private LiquidPrintBill mLiquidPrintBill;
    private EditText txtDemand;
    private LinearLayout formDemand;


    //Reading Variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reading_v2);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            init();
        }catch (Exception e){
            Log.e(TAG,"Tristan Leyesa",e);
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

    public void Next() {
        try{
            LiquidBilling.clearData();
            Intent i = new Intent(ReadingV2Activity.this, ReadingRemarksActivity.class);
            //If there no reading
            if (etxtReading.getText().toString().equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReadingV2Activity.this);
                builder.setCancelable(true);
                builder.setMessage("You did not input a reading, Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(ReadingV2Activity.this, ReadingRemarksActivity.class);
                        Liquid.Reading = "";
                        Liquid.Present_Consumption = "0";
                        Liquid.ReadingInputTemporaryHolder = Liquid.Reading;
                        Liquid.PresentConsumptionTemporaryHolder = Liquid.Present_Consumption;
                        startActivity(i);
                        dialog.cancel();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        etxtReading.requestFocus();
                        dialog.cancel();

                    }
                });
                builder.show();

                return;
            }

            //Initialization of computation
            if (!initializationForComputation("")) {
                return;
            }

            //Temporary hold reading input for baliwag since there are remarks that changes reading variable to average
            Liquid.ReadingInputTemporaryHolder = Liquid.Reading;
            Liquid.PresentConsumptionTemporaryHolder = Liquid.Present_Consumption;
            //This is the section where the status of the reading where will it go.
            switch (Liquid.reading_remarks) {
                case "LOW CONSUMPTION":
                    if (LowConsumptionAttempt == 0) {
                        LowConsumptionAttempt = 1;
                        ConsumptionFindingsNotification(Liquid.reading_remarks);
                    } else {
                        Computation();
                        switch (Liquid.Client){
                            case "baliwag_wd":
                                break;
                            default:
                                Liquid.save_only = true;
                        }
                        startActivity(i);
                    }
                    break;
                case "HIGH CONSUMPTION":
                    if (HighConsumptionAttempt == 0) {
                        HighConsumptionAttempt = 1;
                        ConsumptionFindingsNotification(Liquid.reading_remarks);
                    } else {
                        Computation();
                        startActivity(i);
                    }

                    break;
                case "NEGATIVE CONSUMPTION":
                    if (NegativeConsumptionAttempt == 0) {
                        NegativeConsumptionAttempt = 1;
                        ConsumptionFindingsNotification(Liquid.reading_remarks);
                    } else {
                        Liquid.save_only = true;
                        Liquid.Present_Consumption = "0";
                        startActivity(i);
                    }
                    break;
                case "ZERO CONSUMPTION":
                    if (ZeroConsumptionAttempt == 0) {
                        ZeroConsumptionAttempt = 1;
                        ConsumptionFindingsNotification(Liquid.reading_remarks);
                        etxtReading.requestFocus();
                    } else {
                        Liquid.save_only = true;
                        startActivity(i);
                    }
                    break;
                default:
                    Computation();
                    startActivity(i);
            }
        }catch(Exception e){
            Log.e(TAG,"Tristan Error ",e);
        }

    }
    private void init() {
        initData();
        mLiquid = new Liquid();
        mLiquidGPS = new LiquidGPS(this);
        txtDemand = (EditText) findViewById(R.id.txtDemand);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        etxtReading = (EditText) findViewById(R.id.etxtReading);
        CardConsumerInformation = (CardView) findViewById(R.id.CardConsumerInformation);
        txtAccountNumber = (TextView) findViewById(R.id.txtAccountNumber);
        txtAccountName =(TextView) findViewById(R.id.txtAccountName);
        txtAccountAddress =(TextView) findViewById(R.id.txtAccountAddress);
        txtMeterNumber =(TextView) findViewById(R.id.txtMeterNumber);
        txtAccountSequence =(TextView) findViewById(R.id.txtAccountSequence);
        txtAccountStatus =(TextView) findViewById(R.id.txtAccountStatus);
        txtAccountType =(TextView) findViewById(R.id.txtAccountType);
        btnNext = (Button) findViewById(R.id.btnNext);
        formDemand = (LinearLayout) findViewById(R.id.formDemand);

        txtAccountNumber.setText("Account No. : ----");
        txtAccountName.setText("Name : ----");
        txtAccountAddress.setText("Address : ----");
        txtMeterNumber.setText("Meter No. : ----");
        txtAccountSequence.setText("Seq. : ----");
        txtAccountStatus.setText("Status : ----");
        txtAccountType.setText("Type : ----");

        mLiquidPrintBill = new LiquidPrintBill();
        switchReverse = (Switch) findViewById(R.id.switchReverse);
        mBottomNavigationView = (AHBottomNavigation) findViewById(R.id.mBottomNavigationView);

        Liquid.AccountNumber = Liquid.SelectedAccountNumber;
        GetReadAndBillData(Liquid.SelectedId, Liquid.AccountNumber, Liquid.Client);
        GetReadingDetails(Liquid.SelectedId, Liquid.AccountNumber);
        GetMOA(Liquid.Client,Liquid.AccountNumber,Liquid.ReadingDate);
        Liquid.GetSettings();

        if(Liquid.ReverseInput == 1){
            switchReverse.setChecked(true);
        }


        BottomNavigation();

        if(Liquid.HideKeyboard == 1){
            mEditTextData = new EditText[2];
            mEditTextData[0] = etxtReading;
            mEditTextData[1] = txtDemand;
            mLiquid.hideSoftKeyboard(mEditTextData);
            //mLiquid.hideSoftKeyboard(this);
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


        etxtReading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                focusEditText = 1;
                return false;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });





        etxtReading.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    Next();
                    return true;
                }
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

        txtDemand.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    Next();
                    return true;
                }
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
        etxtReading.requestFocus();

    }


    public void ConsumptionFindingsNotification(String findings) {
       // Liquid.showDialogInfo(this,"Invalid","The Consumption is " + findings + " Please check the READING again!");
        etxtReading.setText("");
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        etxtReading.requestFocus();
                        dialog.cancel();
                        break;
                    default:
                        etxtReading.requestFocus();
                        dialog.cancel();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Consumption is " + findings + " Please check the READING again!").
                setPositiveButton("Done", dialogClickListener).show();
        ;
    }
    private void GetReadingDetails(String job_id, String AccountNumber) {

        try {

            Cursor result = ReadingModel.GetReadingDetails(job_id, AccountNumber);
             HighConsumptionAttempt = 0;
             LowConsumptionAttempt = 0;
             NegativeConsumptionAttempt = 0;
             ZeroConsumptionAttempt = 0;

            if (result.getCount() == 0) {
                Liquid.Reading  = "";
                etxtReading.setText(Liquid.Reading);
                Liquid.Demand = "";
                txtDemand.setText(Liquid.Demand);
                Liquid.reactive = "0";
                Liquid.powerfactor = "0";
                Liquid.kw_cummulative = "0";
                Liquid.reading1 =   Liquid.Reading;
                Liquid.reading2 = Liquid.Demand;
                Liquid.reading3 = Liquid.reactive;
                Liquid.reading4 = Liquid.powerfactor;
                Liquid.reading5 = Liquid.kw_cummulative;
                Liquid.reading6 = "";
                Liquid.reading7 = "";
                Liquid.reading8 = "";
                Liquid.reading9 = "";
                Liquid.reading10 = "";
                Liquid.iwpowerfactor ="";
                if(Liquid.Demand.equals("")){
                    Liquid.demand_consumption = String.valueOf(0 *  Double.parseDouble(Liquid.multiplier));
                }else{
                    Liquid.demand_consumption = String.valueOf(Double.parseDouble(Liquid.Demand )*  Double.parseDouble(Liquid.multiplier));
                }

                Liquid.reactive_consumption = Liquid.reactive;
                Liquid.ReaderComment = "";
                Liquid.RemarksValue = "0-NO FIELD FINDINGS";

                Liquid.Print_Attempt = "0";
                Liquid.Reading_Attempt  = "0";
                Liquid.Reader_ID = Liquid.User;
                Liquid.meter_reader = Liquid.User;
                Liquid.RemarksCode = "0";
                Liquid.Remarks = "NO FIELD FINDINGS";
                Liquid.reading_remarks = "";
                Liquid.Reading_TimeStamp  = "";
                Liquid.Print_TimeStamp = "";
                Liquid.r_latitude = "0.0";
                Liquid.r_longitude  = "0.0";
                Liquid.check_previous = "0";
                Liquid.Present_Consumption  = "";
                etxtReading.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Liquid.reprint = false;

                return;
            }

            while (result.moveToNext()) {
                //Customer Data

                HashMap<String, String> data = new HashMap<>();
                Liquid.C_ID = result.getString(1);
                Liquid.Reading  = result.getString(10);
                etxtReading.setText(Liquid.Reading );
                Liquid.Demand = result.getString(24).equals(null) || result.getString(24).equals("") ? "" : result.getString(24);
                txtDemand.setText(Liquid.Demand);
                Log.i(TAG,"Tristan2 "+txtDemand.getText().toString());
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
                Liquid.reprint = true;
                etxtReading.setBackgroundColor(Color.YELLOW);
                txtDemand.setBackgroundColor(Color.YELLOW);



            }


        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }

    }

    private void GetMOA(String client, String accountnumber, String readingdate) {

        try {
            Cursor result = ReadingModel.get_customer_moa(client, accountnumber, readingdate);

            if (result.getCount() == 0) {
                Liquid.moa = "0";

                return;
            }

            while (result.moveToNext()) {
                //Bill Deposit
                Liquid.moa = result.getString(6);
            }


        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }
    }

    public void initData(){
        LiquidBilling.clearData();
        Liquid.Reading = "";

        Liquid.classification = "";
        Liquid.arrears = "";
        Liquid.ReaderComment = "";

        Liquid.route  = "";
        Liquid.itinerary  = "";
        Liquid.previous_reading  = "0";
        Liquid.present_Reading = "";
        Liquid.Present_Consumption= "0";
        Liquid.previous_reading_date= "";
        Liquid.duedate= "";
        Liquid.discondate= "";
        Liquid.BillYear= "";
        Liquid.BillMonth= "";
        Liquid.Average_Reading = "False";
        Liquid.multiplier= "";
        Liquid.rate_code= "";
        Liquid.bill_number= "";
        Liquid.rentalfee= "";
        Liquid.Remarks = "";
        Liquid.RemarksCode = "";
        Liquid.Details = "";
        Liquid.Date = "";
        Liquid.JobId = "";
        Liquid.senior_tagging = "0";
        Liquid.eda_tagging = "0";
        Liquid.change_meter = "0";
        Liquid.interest = "0";
        Liquid.RemarksValue = "0-NO FIELD FINDINGS";

        Liquid.C_ID = "";
        Liquid.job_id  = "";
        Liquid.name  = "";
        Liquid.serial  = "";
        Liquid.previous_consumption= "";
        Liquid.present_reading_date= "";
        Liquid.Reading_Date= "";
        Liquid.BillDate= "";
        Liquid.month= "";
        Liquid.day= "";
        Liquid.year= "0";
        Liquid.Demand = "0";
        Liquid.reactive= "0";
        Liquid.powerfactor= "0";
        Liquid.kw_cummulative= "0";
        Liquid.reading1= "0";
        Liquid.reading2= "0";
        Liquid.reading3= "0";
        Liquid.reading4= "0";
        Liquid.reading5= "0";
        Liquid.reading6= "0";
        Liquid.reading7= "0";
        Liquid.reading8= "0";
        Liquid.reading9= "0";
        Liquid.reading10= "0";
        Liquid.iwpowerfactor= "0";
        Liquid.demand_consumption= "0";
        Liquid.reactive_consumption= "0";
        Liquid.Averange_Consumption= "0";
        Liquid.Meter_Box= "";
        Liquid.Demand_Reset= "";
        Liquid.Test_Block= "";

        Liquid.remarks_abbreviation= "";

        Liquid.ReaderComment = "";
        Liquid.Reader_ID= "";
        Liquid.meter_reader= "";
        Liquid.Reading_Attempt = "0";
        Liquid.Print_Attempt = "0";
        Liquid.force_reading= "";
        Liquid.r_latitude= "";
        Liquid.r_longitude= "";
        Liquid.printlatitude= "";
        Liquid.printlongitude= "";
        Liquid.improbable_reading= "";
        Liquid.negative_reading= "";
        Liquid.change_reading= "";
        Liquid.cg_vat_zero_tag= "";
        Liquid.reading_remarks= "";
        Liquid.old_key= "";
        Liquid.new_key= "";
        Liquid.transfer_data_status= "Pending";
        Liquid.upload_status= "Pending";
        Liquid.code= "";
        Liquid.area= "";
        Liquid.cummulative_multiplier= "";
        Liquid.oebr_number= "";

        Liquid.Reading_TimeStamp= "";
        Liquid.Print_TimeStamp= "";
        Liquid.timestamp= "";
        Liquid.GenerationSystem = "";
        Liquid.BenHost= "";
        Liquid.GRAM= "";
        Liquid.ICERA= "";
        Liquid.PowerArtReduction= "";
        Liquid.TransmissionDelivery= "";
        Liquid.TransmissionDelivery2= "";
        Liquid.System_Loss= "";
        Liquid.Gen_Trans_Rev= "";
        Liquid.DistributionNetwork= "";
        Liquid.DistributionNetwork2= "";
        Liquid.DistributionNetwork3= "";
        Liquid.RetailElectricService= "";
        Liquid.RetailElectricService2= "";
        Liquid.Metering_cust= "";
        Liquid.Metering_cust_2= "";
        Liquid.Metering_kwh= "";
        Liquid.loan= "";
        Liquid.RFSC= "";
        Liquid.Distribution_Rev= "";
        Liquid.MissionaryElectrification= "";
        Liquid.EnvironmentCharge= "";
        Liquid.NPC_StrandedDebts= "";
        Liquid.NPC_StrandedCost= "";
        Liquid.DUsCost= "";
        Liquid.DCDistributionCharge= "";
        Liquid.DCDemandCharge= "";
        Liquid.TCTransSystemCharge= "";
        Liquid.SCSupplySysCharge= "";
        Liquid.equal_tax= "";
        Liquid.CrossSubsidyRemoval= "";
        Liquid.Universal_Charges= "";
        Liquid.Lifeline_Charge= "";
        Liquid.InterclassCrossSubsidy= "";
        Liquid.SeniorCitizenSubsidy= "";
        Liquid.ICCS_Adjustment= "";
        Liquid.ICCrossSubsidyCharge= "";
        Liquid.FitAllCharge= "";
        Liquid.PPD_Adjustment= "";
        Liquid.GenerationCostAdjustment= "";
        Liquid.PowerCostAdjustment= "";
        Liquid.Other_Rev= "";
        Liquid.GenerationVat= "";
        Liquid.TransmissionVat= "";
        Liquid.SystemLossVat= "";
        Liquid.DistributionVat= "";
        Liquid.OtherVat= "";
        Liquid.Government_Rev= "";
        Liquid.CurrentBill= "";
        Liquid.amountdue= "";
        Liquid.overdue= "";
        Liquid.franchise_tax= "";
        Liquid.coreloss= "";
        Liquid.DateChangeMeter = "";
        Liquid.surcharge= "";
        Liquid.delivered= "";
        Liquid.check_previous = "False";
        Liquid.ispn= "";
        Liquid.SCD= "";
        Liquid.pnrecvdte= "";
        Liquid.pnrecvby= "";
        Liquid.recvby= "";
        Liquid.hash= "";
        Liquid.isreset= "";
        Liquid.isprntd= "";
        Liquid.meter_count= "";
        Liquid.delivery_id= "";
        Liquid.delivery_comment= "";
        Liquid.reading_signature= "";
        Liquid.real_property_tax= "";
        Liquid.cc_rstc_refund= "";
        Liquid.cc_rstc_refund2= "";
        Liquid.moa= "";
        Liquid.eda= "";
        Liquid.ModifiedDate= "";
        Liquid.ModifiedBy= "";
        Liquid.delivery_remarks= "";
        Liquid.connectionload = "0";
        Liquid.LoadFactor = "0";
    }
    public void GetReadAndBillData(String job_id, String AccountNumber, String client) {
        try {

            Cursor result = workModel.GetReadAndBillSelectedJobOrderDetails(job_id, AccountNumber);

            if (result.getCount() == 0) {
                return;
            }
            while (result.moveToNext()) {
                //Customer Data

                HashMap<String, String> data = new HashMap<>();
                Liquid.ConsumerStatus = result.getString(9);
                Liquid.AccountNumber = result.getString(30);

                switch (client)
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
                Liquid.previous_reading = !result.getString(47).equals("") ? result.getString(47) : "0";
                Liquid.previous_reading = Liquid.FixDecimal(Liquid.previous_reading);
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
                Liquid.bill_number = !result.getString(65).equals("") ? result.getString(65) : Liquid.year + Liquid.BillMonth + AccountNumber;
                Liquid.rowid = result.getString(84);

                //Discon and DueDate
                Liquid.duedate = result.getString(64);

                if (Liquid.duedate.equals("")) {
                    Liquid.duedate = Liquid.getDueDate(Liquid.present_reading_date);
                }
                Liquid.discondate = Liquid.getDisconDate(Liquid.duedate);
                Liquid.present_reading_date = Liquid.currentDate();
                Liquid.BillingCycle = Liquid.year + "-" + Liquid.BillMonth;
                //Reading


            }

            txtAccountNumber.setText("Account No. : "+Liquid.AccountNumber);
            txtAccountName.setText("Name : "+Liquid.AccountName);
            txtAccountAddress.setText("Address : "+Liquid.Complete_Address);
            txtMeterNumber.setText("Meter No. : "+Liquid.MeterNumber);
            txtAccountSequence.setText("Seq. : "+Liquid.Sequence);
            txtAccountStatus.setText("Status : "+Liquid.ConsumerStatus);
            txtAccountType.setText("Type : "+Liquid.AccountType);

            if(Liquid.ConsumerStatus.equals("DISCD") || Liquid.ConsumerStatus.equals("WOFF")){
                CardConsumerInformation.setCardBackgroundColor(Color.RED);
            }else{
                CardConsumerInformation.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            }


            MeterCount(Liquid.meter_count);

        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try{

            switch(keyCode){
                case 67:
                    if(Liquid.ReverseInput == 1){
                       DeleteReverse();
                    }
                    break;
                case 22:
                    //Remove due to the first step is camera
                    //new GetNextConsumer().execute(String.valueOf(keyCode));
                    break;
                case 21:
                    //Remove due to the first step is camera
                    //new GetNextConsumer().execute(String.valueOf(keyCode));
                    break;
            }
        }
        catch (Exception e){
            Log.e(TAG,"Tristan Garyl Leyesa",e);
        }



        return super.onKeyDown(keyCode, event);
    }
    public void MeterCount(String meter_count){

        switch(meter_count){
            case "1":
                switch(Liquid.Client){
                    case "more_power":
                        //Survey
                            //formDemand.setVisibility(View.VISIBLE);
                        formDemand.setVisibility(View.GONE);
                        break;
                        default:
                            formDemand.setVisibility(View.GONE);
                }

                break;
            case "2":
                formDemand.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }
    public void BottomNavigation(){
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
                            Liquid.showDialogError(ReadingV2Activity.this,"Invalid","This consumer is still unread.");
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

    public void Reprint(){
        try {

            if(Liquid.reprint == false){
                Liquid.showDialogError(this,"Invalid","There no bill to reprint!");
                return;
            }
            if(!initializationForComputation("Reprint")){
                return;
            };
            switch(Liquid.reading_remarks){
                case "LOW CONSUMPTION":
                    Liquid.showDialogError(this,"Invalid","Low Consumption cannot print!");
                    switch (Liquid.Client){
                        case "baliwag_wd":
                            break;
                        default:
                            Liquid.save_only = true;
                    }
                    return;
                case "HIGH CONSUMPTION":
                    break;
                case "NEGATIVE CONSUMPTION":
                    Liquid.showDialogError(this,"Invalid","Negative Consumption cannot print!");
                    Liquid.save_only = true;
                    return;
                case "ZERO CONSUMPTION":
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
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa",e);
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
            Liquid.Reading = etxtReading.getText().toString();
            Liquid.Demand = txtDemand.getText().toString();
            Liquid.Reading_Attempt = String.valueOf(Integer.parseInt(Liquid.Reading_Attempt) + 1);
            String LastDial = String.valueOf(Liquid.previous_reading.charAt(0));
            Liquid.Reading_TimeStamp = Liquid.currentDateTime();
            Liquid.timestamp = Liquid.Reading_TimeStamp;
            Liquid.ModifiedDate =Liquid. Reading_TimeStamp;
            Liquid.ModifiedBy = Liquid.User;
            Liquid.reading1 = Liquid.Reading;
            Liquid.reading2 = Liquid.Demand;
            String[] RemarksData = Liquid.RemarksValue.split("-");
            Liquid.RemarksCode = RemarksData[0];
            Liquid.Remarks = RemarksData[1];

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
                    default:
                        Liquid.Average_Reading = Liquid.AverageValidation(Liquid.Remarks,Liquid.RemarksCode);
                        Liquid.Present_Consumption = Liquid.Averange_Consumption;
                        Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);
                        return true;
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
            Liquid.reading_remarks = Liquid.ConsumptionValidation(Liquid.Averange_Consumption, Liquid.previous_consumption, Liquid.Present_Consumption);

            return true;
        }catch(Exception e){
            Log.e(TAG,"Error Reading",e);
            return false;

        }


    }


    public double ChangeMeterKWH(Date DateChangeMeter,Date PresentReadingDate, double KWH){
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

    public static void Computation(){

        LiquidBilling mLiquidBilling = new LiquidBilling();

        switch (Liquid.Client){
            case "batelec2":
                mLiquidBilling.Batellec2ElectricBillingComputaion(
                        Liquid.Present_Consumption,
                        Liquid.Demand,
                        Liquid.AccountType,
                        Liquid.classification,
                        Liquid.BillingCycle,
                        Liquid.arrears,
                        Liquid.senior_tagging,
                        Liquid.eda_tagging
                );
                break;
            case "iselco2":
                mLiquidBilling.Iselco2ElectricBillingComputaion(
                        Liquid.Present_Consumption,
                        Liquid.Demand,
                        Liquid.AccountType,
                        Liquid.classification,
                        Liquid.BillingCycle,
                        Liquid.arrears,
                        Liquid.senior_tagging,
                        Liquid.eda_tagging
                );
                break;
            case "ileco2":
                mLiquidBilling.Ileco2ElectricBillingComputaion(
                        Liquid.Present_Consumption,
                        Liquid.Demand,
                        Liquid.AccountType,
                        Liquid.classification,
                        Liquid.BillingCycle,
                        Liquid.arrears,
                        Liquid.eda_tagging,
                        Liquid.senior_tagging
                );
                break;
            case "more_power":
                mLiquidBilling.MorePowerElectricBillingComputaion(
                        Liquid.Present_Consumption,
                        Liquid.demand_consumption,
                        Liquid.AccountType,
                        Liquid.classification,
                        Liquid.BillingCycle,
                        Liquid.arrears,
                        Liquid.eda_tagging,
                        Liquid.senior_tagging
                );
                break;
            case "pelco2":
                mLiquidBilling.Pelco2ElectricBillingComputaion(
                        Liquid.Present_Consumption,
                        Liquid.Demand,
                        Liquid.AccountType,
                        Liquid.classification,
                        Liquid.BillingCycle,
                        Liquid.arrears,
                        Liquid.eda_tagging,
                        Liquid.senior_tagging
                );
                break;
            case "baliwag_wd":
                mLiquidBilling.BaliwagWDBillingComputaion(
                        Liquid.Present_Consumption,
                        Liquid.Demand,
                        Liquid.AccountType,
                        Liquid.classification,
                        Liquid.BillingCycle,
                        Liquid.arrears,
                        Liquid.eda_tagging,
                        Liquid.senior_tagging
                );
                break;
        }
    }


    public void DeleteReverse(){

        try{
            switch (focusEditText){
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
    public void OpenGallery(){
        Liquid.SelectedCategory = "reading";
        Liquid.ReadingDate = Liquid.ReadingDate;
        Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
        startActivity(i);
    }
    public void ShowPreviosReading(){
        Liquid.showDialogInfo(ReadingV2Activity.this, "Details", "Previous Reading : " + Liquid.previous_reading);
        Liquid.check_previous = "True";
    }

    public class PrintBill extends AsyncTask<Void,Void,Void> {
        boolean result = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadingV2Activity.this);
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
                Liquid.showReadingDialogNext(ReadingV2Activity.this, "Valid", "Successfully Print!");
                //ShowReprint();
            } else {
                Liquid.showDialogError(ReadingV2Activity.this, "Invalid", "Unsuccessfully Print!");
            }
            pDialog.dismiss();

        }
    }

    public class GetNextConsumer extends AsyncTask<String,Void,Void> {
        boolean result = false;
        Cursor result_data = null;
        String mAccountNumber = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ReadingV2Activity.this);
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

            if(mAccountNumber.equals("")){

            }else{
                Liquid.AccountNumber = mAccountNumber;
                GetReadAndBillData(Liquid.SelectedId,  Liquid.AccountNumber, Liquid.Client);
                GetReadingDetails(Liquid.SelectedId,  Liquid.AccountNumber);
                GetMOA(Liquid.Client,Liquid.AccountNumber,Liquid.ReadingDate);
                etxtReading.requestFocus();
            }

            pDialog.dismiss();
        }
    }
}
