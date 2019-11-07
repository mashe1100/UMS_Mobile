package com.aseyel.tgbl.tristangaryleyesa.data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.aseyel.tgbl.tristangaryleyesa.JobOrderActivity;
import com.aseyel.tgbl.tristangaryleyesa.MainActivity;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.SettingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.ExportCSV;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidPrintBill;
import com.aseyel.tgbl.tristangaryleyesa.services.Speech;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tristan on 12/12/2017.
 */

public class Liquid extends AppCompatActivity {
    public static final String DATABASE_NAME = "ums_mobile.db";
//    public static String pathEnvironment = "USI_TEST";
   // public static String pathEnvironment = "USI_BETA";
    //MORE POWER path
    public static String pathEnvironment = "USI";
    //UMS server
    //private static final String umsUrl = "usi.3utilities.com:14147";
    //MORE POWER server
    private static final String umsUrl = "125.5.181.225:8080";

    private static final String TAG = "Liquid";
    public static String DefaultErrorMessage = "An error has occured!";
    public static int RecyclerItemLimit = 50;
    public static int UpdateRecyclerItemLimit = 0;
    public static String DeliveryStep = "";
    public static String DeliveryItemTypeCode = "";
    public static String DeliveryItemTypeDescription = "";
    public static double Latitude = 0;
    public static double Longitude = 0;
    public static String SelectedId = "";
    public static String SelectedJobType = "";
    public static String SelectedAuditId = "";
    public static String SelectedJobOrderDate = "";
    public static String SelectedAccountName = "";
    public static String SelectedAccountNumber = "";
    public static int SelectedImagePosition = 0;
    public static String SelectedImage = "";
    public static ArrayList<Bitmap> SelectedGallery = new ArrayList();
    public static String SelectedDescription = "";
    public static String SelectedType = "";
    public static String SelectedCategory = "";
    public static String ReadingDate = "";
    public static String SelectedCode = "";
    public static String SelectedPeriod = "";
    public static String SelectedStoreStatus = "";
    public static double TotalPrograssBar = 0;
    public static double CountProgressBar = 0;
    public static Bitmap UserSignature ;
    public static boolean UploadResult = false;
    public static String imageFormat = ".jpg";
    public static String imageOrientation = "";
    public static String QRCode = "";
    public static String Username = "tgbleyesa";
    public static String Password = "C0mpl3x17y";
    public static String AccountUsername = "";
    public static String AccountPassword = "";
    public static String Status = "";
    public static String Cycle = "";
    public static String LoadFactor = "0";
    public static String UserFullname = "";
    public static String SearchLocation = "";
    public static String SearchLocationLatitude = "0";
    public static String SearchLocationLongtitude = "0";
    public static String GoingToLocation = "";
    public static String GoingToLocationLatitude = "0";
    public static String GoingToLocationLongtitude = "0";
    public static String SearchId = "";
    public static String SearchFullname = "";
    public static String SelectedMeterNumber = "";
    public static String OldMeterNumber = "";
    public static String OldReading = "";
    public static String OldConsumption = "0";
    public static int ReverseInput = 0;
    public static int HideKeyboard = 0;
    public static String rowid = "0";
    public static String TrackingNumber = "";
    public static String SurveyType = "";
    public static String SurveyAmpirCapacity = "";
    //Phone Server
//    public static String ServerNumber = "+639291332538";
    public static String ServerNumberGlobe = "+639064783858";
    public static String ServerNumberSmart = "+639989626300";
    //Coke
    //public static String Client = "coke";
    //public static String ServiceType = "TRACKING";

    //Auditor
    //public static String Client = "magelco";
    //public static String ServiceType = "AUDIT";


    //READ AND BILL
//    public static String Client = "batelec2";
    //public static String Client = "baliwag_wd";
    public static String Client = "more_power";
//     EDIT INIT LINE 99 AUDIT to READING (READING GALLERY ACTIVITY)
    // EDIT GetImages Line 180 AUDIT to READING (READING GALLERY ACTIVITY)
    // EDIT GetImages Line 1475 AUDIT to READING (TAB LOCAL FRAGMENT)
    // Please edit remarks reference // MeterReadingRemarksData
//    public static String Client = "ileco2"; //Please edit remarks reference // MeterReadingIleco2RemarksData
//    public static String Client = "pelco2"; //Please edit remarks reference // MeterReadingPelco2RemarksData
    public static String ServiceType = "READ AND BILL";
//    public static String ImageType = "audit";[


//    public static String Client = "ngc_express";
//    public static String ServiceType = "h";
//    public static String Client = "meralco_batangas";
//    public static String Client = "meralco_lucena";
//    public static String Client = "philpost";
//    public static String ServiceType = "MESSENGER";

    //public static String Client = "athena";
    //public static String ServiceType = "ADMIN";

    //Disconnection
//    public static String Client = "primewater_bulacan";
//    public static String Client = "primewater_cavite";
//    public static String ServiceType = "DISCONNECTION";

    public static String LiquidTable = "";
    public static String LiquidPackageName;

    public static int screenHeight;
    public static int screenWidth;

    //DELIVERY
    public static String m_type = "";
    public static String m_type_description = "";
    public static String m_status = "";
    public static String m_remark_code = "";
    public static String m_remark = "";
    public static String battery_life = "";

    public static String LiquidDBPath = "sdcard/UMS/BackUp/";
    public static JSONObject ErrorFileSignatureUpload = new JSONObject();
    public static JSONArray ErrorUpload = new JSONArray();
    public static JSONObject ErrorDataUpload = new JSONObject();
    public static JSONObject ErrorFileUpload = new JSONObject();


    //Reading
    public static boolean save_only = false;
    public static String BillingCycle;
    public static String DateChangeMeter = "";
    public static String AccountNumber;
    public static String MeterNumber;
    public static String AccountName;
    public static String Complete_Address;
    public static String Sequence;
    public static String ShareCap;
    public static String PoleRent = "0";
    public static String OtherBill;

    public static String ConsumerStatus;
    public static String AccountType;
    public static String Reading = "";
    public static String ReadingInputTemporaryHolder = "";
    public static String PresentConsumptionTemporaryHolder = "";

    public static String classification = "";
    public static String arrears = "";
    public static String ReaderComment = "";

    public static  String route  = "";
    public static String itinerary  = "";
    public static String previous_reading  = "0";
    public static String present_Reading = "";
    public static String Present_Consumption= "0";
    public static String previous_reading_date= "";
    public static String duedate= "";
    public static String discondate= "";
    public static String BillYear= "";
    public static String BillMonth= "";
    public static String Average_Reading = "False";
    public static String multiplier= "";
    public static String rate_code= "";
    public static String rate_description = "";
    public static String bill_number= "";
    public static String rentalfee= "";
    public static String Remarks = "";
    public static String RemarksCode = "";
    public static String RemarksAbbreviation = "";
    public static String Details;
    public static String Date;
    public static String JobId = "";
    public static String senior_tagging = "0";
    public static String eda_tagging = "0";
    public static String change_meter = "0";
    public static String interest = "0";
    public static String RemarksValue = "";

    public static String C_ID = "";
    public static String job_id  = "";
    public static String name  = "";
    public static String serial  = "";
    public static String connectionload  = "0";
    public static String previous_consumption= "";
    public static String present_reading_date= "";
    public static String Reading_Date= "";
    public static String BillDate= "";
    public static String month= "";
    public static String day= "";
    public static String year= "0";
    public static String Demand = "0";
    public static String reactive= "0";
    public static String powerfactor= "0";
    public static String kw_cummulative= "0";
    public static String reading1= "0";
    public static String reading2= "0";
    public static String reading3= "0";
    public static String reading4= "0";
    public static String reading5= "0";
    public static String reading6= "0";
    public static String reading7= "0";
    public static String reading8= "0";
    public static String reading9= "0";
    public static String reading10= "0";
    public static String iwpowerfactor= "0";
    public static String demand_consumption= "0";
    public static String reactive_consumption= "0";
    public static String Averange_Consumption= "0";
    public static String Meter_Box= "";
    public static String Demand_Reset= "";
    public static String Test_Block= "";

    public static String remarks_abbreviation= "";


    public static String Reader_ID= "";
    public static String meter_reader= "";
    public static String Reading_Attempt = "0";
    public static String Print_Attempt = "0";
    public static String force_reading= "";
    public static String r_latitude= "";
    public static String r_longitude= "";
    public static String printlatitude= "";
    public static String printlongitude= "";
    public static String improbable_reading= "";
    public static String negative_reading= "";
    public static String change_reading= "";
    public static String cg_vat_zero_tag= "";
    public static String reading_remarks= "";
    public static String old_key= "";
    public static String new_key= "";
    public static String transfer_data_status= "Pending";
    public static String upload_status= "Pending";
    public static String code= "";
    public static String area= "";
    public static String cummulative_multiplier= "";
    public static String oebr_number= "";

    public static String Reading_TimeStamp= "";
    public static String Print_TimeStamp= "";
    public static String timestamp= "";
    public static String GenerationSystem = "";
    public static String BenHost= "";
    public static String GRAM= "";
    public static String ICERA= "";
    public static String PowerArtReduction= "";
    public static String TransmissionDelivery= "";
    public static String TransmissionDelivery2= "";
    public static String System_Loss= "";
    public static String Gen_Trans_Rev= "";
    public static String DistributionNetwork= "";
    public static String DistributionNetwork2= "";
    public static String DistributionNetwork3= "";
    public static String RetailElectricService= "";
    public static String RetailElectricService2= "";
    public static String Metering_cust= "";
    public static String Metering_cust_2= "";
    public static String Metering_kwh= "";
    public static String loan= "";
    public static String RFSC= "";
    public static String Distribution_Rev= "";
    public static String MissionaryElectrification= "";
    public static String EnvironmentCharge= "";
    public static String NPC_StrandedDebts= "";
    public static String NPC_StrandedCost= "";
    public static String DUsCost= "";
    public static String DCDistributionCharge= "";
    public static String DCDemandCharge= "";
    public static String TCTransSystemCharge= "";
    public static String SCSupplySysCharge= "";
    public static String equal_tax= "";
    public static String CrossSubsidyRemoval= "";
    public static String Universal_Charges= "";
    public static String Lifeline_Charge= "";
    public static String InterclassCrossSubsidy= "";
    public static String SeniorCitizenSubsidy= "";
    public static String ICCS_Adjustment= "";
    public static String ICCrossSubsidyCharge= "";
    public static String FitAllCharge= "";
    public static String PPD_Adjustment= "";
    public static String GenerationCostAdjustment= "";
    public static String PowerCostAdjustment= "";
    public static String Other_Rev= "";
    public static String GenerationVat= "";
    public static String TransmissionVat= "";
    public static String SystemLossVat= "";
    public static String DistributionVat= "";
    public static String OtherVat= "";
    public static String Government_Rev= "";
    public static String CurrentBill= "";
    public static String amountdue= "";
    public static String overdue= "";
    public static String franchise_tax= "";
    public static String coreloss= "";
    public static String surcharge= "";
    public static String delivered= "";
    public static String check_previous = "False";
    public static String ispn= "";
    public static String SCD= "";
    public static String pnrecvdte= "";
    public static String pnrecvby= "";
    public static String recvby= "";
    public static String hash= "";
    public static String isreset= "";
    public static String isprntd= "";
    public static String meter_count= "";
    public static String delivery_id= "";
    public static String delivery_comment= "";
    public static String reading_signature= "";
    public static String real_property_tax= "";
    public static String cc_rstc_refund= "";
    public static String cc_rstc_refund2= "";
    public static String moa= "";
    public static String eda= "";
    public static String sysgenpenalty= "";
    public static String ModifiedDate= "";
    public static String ModifiedBy= "";
    public static String delivery_remarks= "";
    public static boolean reprint = false;
    public static boolean PrintResponse = false;

    public static String ContactNumber = "";
    public static String EmailAdd = "";
    public static String MeterBrand = "";
    public static String MeterTypeValue = "";
    public static String StructureValue = "";

    //public static String UpdatedAccountName = "";
    public static String UpdatedAddress = "";
    //public static String UpdatedMeterNumber = "";
   // public static String UpdatedSequence = "";


    public static String WrapAround(String PreviousReading, String Reading) {
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

    public static void DelayFunction(int milliseconds){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },milliseconds);
    }
    public static boolean SaveReading(String Client, //ok
                               String CustomerID, //ok
                               String AccountNumber, //ok
                               String JobId, //ok
                               String  AccountName, //ok
                               String route, //ok
                               String itinerary, //ok
                               String MeterNumber, //ok
                               String serial, //ok
                               String previous_reading, //ok
                               String Reading, //ok
                               String previous_consumption, //ok
                               String  Present_Consumption, //computation
                               String previous_reading_date, //ok
                               String present_reading_date, //ok
                               String duedate, // if there value currentdate +6 //ok
                               String discondate, // duedate +2 days //ok
                               String ReadingDate, //ok
                               String BillYear, //ok
                               String BillMonth, //ok
                               String BillDate, //ok
                               String  month, //ok
                               String day, //ok
                               String year, //ok
                               String Demand, // New TextBox
                               String reactive, // ok
                               String powerfactor, //ok
                               String kw_cummulative, //ok
                               String reading1, //ok
                               String reading2, //ok
                               String reading3, //ok
                               String reading4, //ok
                               String  reading5, //ok
                               String  reading6, //ok
                               String  reading7, //ok
                               String  reading8, //ok
                               String  reading9, //ok
                               String  reading10, //ok
                               String  iwpowerfactor, //ok
                               String demand_consumption, //ok
                               String reactive_consumption, //ok
                               String Averange_Consumption, //ok
                               String Average_Reading, //ok
                               String multiplier, //ok
                               String Meter_Box, //ok
                               String Demand_Reset, //ok
                               String Test_Block, //ok
                               String RemarksCode,
                               String remarks_abbreviation, // not OK
                               String Remarks, //ok
                               String Comment, //ok
                               String Reader_ID, //ok
                               String meter_reader, //ok
                               String Reading_Attempt, //ok
                               String Print_Attempt, // ok but no print
                               String force_reading, //ok
                               String r_latitude, //ok
                               String r_longitude, //ok
                               String printlatitude, //ok
                               String printlongitude, //ok
                               String improbable_reading, //ok
                               String negative_reading, //ok
                               String change_reading, //ok
                               String cg_vat_zero_tag, //ok
                               String reading_remarks, //ok
                               String old_key, // not textbox
                               String new_key, // not textbox
                               String transfer_data_status, // SMS or LTE function
                               String upload_status, //upload function status
                               String code, //ok
                               String area,
                               String rate_code, //ok
                               String cummulative_multiplier, //ok
                               String oebr_number, //ok
                               String Sequence, //ok
                               String Reading_TimeStamp, //ok
                               String Print_TimeStamp, //ok
                               String timestamp, //ok
                               String bill_number, // ok
                               String GenerationSystem , // ok
                               String BenHost, // Bill PECO
                               String GRAM , // ok
                               String ICERA , // ok
                               String PowerArtReduction , // ok
                               String  TransmissionDelivery , // ok
                               String TransmissionDelivery2, // ok
                               String  System_Loss,// ok
                               String  Gen_Trans_Rev, // ok
                               String  DistributionNetwork, // ok
                               String  DistributionNetwork2,// ok
                               String   DistributionNetwork3, // ok
                               String   RetailElectricService , // ok
                               String  RetailElectricService2, // ok
                               String   Metering_cust, // ok
                               String  Metering_cust_2, // ok
                               String Metering_kwh, // Bill
                               String loan , // Bill
                               String RFSC , // ok
                               String  Distribution_Rev , // ok
                               String  MissionaryElectrification, // ok
                               String  EnvironmentCharge, // ok
                               String  NPC_StrandedDebts,  // ok
                               String   NPC_StrandedCost, // ok
                               String  DUsCost, // ok
                               String    DCDistributionCharge, // ok
                               String   DCDemandCharge, // ok
                               String  TCTransSystemCharge, // ok
                               String   SCSupplySysCharge, // ok
                               String  equal_tax, // ok
                               String   CrossSubsidyRemoval , // ok
                               String  Universal_Charges , // ok
                               String   Lifeline_Charge, // ok
                               String   InterclassCrossSubsidy , // ok
                               String   SeniorCitizenSubsidy, // ok
                               String   ICCS_Adjustment, // ok
                               String   ICCrossSubsidyCharge, // ok //ME Renewable Energy Dev ILECO II
                               String   FitAllCharge, // ok
                               String   PPD_Adjustment, // ok
                               String    GenerationCostAdjustment, // ok
                               String    PowerCostAdjustment, // ok
                               String    Other_Rev, // ok
                               String   GenerationVat, // ok
                               String     TransmissionVat , // ok
                               String   SystemLossVat , // ok
                               String    DistributionVat, // ok
                               String    OtherVat, // ok
                               String     Government_Rev,  // ok
                               String     CurrentBill, // ok
                               String     amountdue, // ok
                               String     overdue, // ok
                               String      franchise_tax, // Bill
                               String    coreloss, //ok
                               String       surcharge, //ok //ILP ILECO2
                               String      rentalfee, // Gov Subsidy ILECO2
                               String    delivered, //ok
                               String       check_previous, // Taging if previous is check
                               String       ispn,  //PECO
                               String     SCD , //ok
                               String        pnrecvdte, //PECO
                               String      pnrecvby, //PECO
                               String    recvby, // Delivery
                               String   hash, //PECO
                               String      isreset, // DI ALAM NI ALEX
                               String       isprntd, // DI ALAM NI ALEX
                               String       meter_count, //ok
                               String        delivery_id, // Delivery
                               String          delivery_remarks, // Delivery
                               String        delivery_comment, // Delivery
                               String       reading_signature, // Delivery
                               String       real_property_tax,  //Bill
                               String         cc_rstc_refund, //Bill
                               String        cc_rstc_refund2, //Bill
                               String      moa, //Bill
                               String      eda, //Bill
                               String       ModifiedDate,
                               String       ModifiedBy) {
        boolean result = false;

        result = ReadingModel.doReading(
                Client, //ok
                CustomerID, //ok
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
                GenerationSystem, // ok
                BenHost, // Bill PECO
                GRAM, // ok
                ICERA , // ok
                PowerArtReduction, // ok
                TransmissionDelivery, // ok
                TransmissionDelivery2, // ok
                System_Loss,// ok
                Gen_Trans_Rev , // ok
                DistributionNetwork, // ok
                DistributionNetwork2 ,// ok
                DistributionNetwork3 , // ok
                RetailElectricService , // ok
                RetailElectricService2 , // ok
                Metering_cust , // ok
                Metering_cust_2, // ok
                Metering_kwh, // Bill
                loan , // Bill
                RFSC , // ok
                Distribution_Rev , // ok
                MissionaryElectrification , // ok
                EnvironmentCharge , // ok
                NPC_StrandedDebts ,  // ok
                NPC_StrandedCost, // ok
                DUsCost, // ok
                DCDistributionCharge, // ok
                DCDemandCharge, // ok
                TCTransSystemCharge, // ok
                SCSupplySysCharge, // ok
                equal_tax , // ok
                CrossSubsidyRemoval , // ok
                Universal_Charges, // ok
                Lifeline_Charge , // ok
                InterclassCrossSubsidy  , // ok
                SeniorCitizenSubsidy, // ok
                ICCS_Adjustment, // ok
                ICCrossSubsidyCharge, // ok //ME Renewable Energy Dev ILECO II
                FitAllCharge, // ok
                PPD_Adjustment, // ok
                GenerationCostAdjustment, // ok
                PowerCostAdjustment, // ok
                Other_Rev, // ok
                GenerationVat , // ok
                TransmissionVat , // ok
                SystemLossVat , // ok
                DistributionVat, // ok
                OtherVat, // ok
                Government_Rev ,  // ok
                CurrentBill, // ok
                amountdue , // ok
                overdue, // ok
                franchise_tax, // Bill
                coreloss, //ok
                surcharge, //ok //ILP ILECO2
                rentalfee, // Gov subsidy ILECO2
                delivered, //ok
                check_previous, // Taging if previous is check
                ispn,  //PECO
                SCD, //ok
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

    public static  boolean SaveReadingLogs(String Client, //ok
                                           String CustomerID, //ok
                                           String AccountNumber, //ok
                                           String JobId, //ok
                                           String  AccountName, //ok
                                           String route, //ok
                                           String itinerary, //ok
                                           String MeterNumber, //ok
                                           String serial, //ok
                                           String previous_reading, //ok
                                           String Reading, //ok
                                           String previous_consumption, //ok
                                           String  Present_Consumption, //computation
                                           String previous_reading_date, //ok
                                           String present_reading_date, //ok
                                           String duedate, // if there value currentdate +6 //ok
                                           String discondate, // duedate +2 days //ok
                                           String ReadingDate, //ok
                                           String BillYear, //ok
                                           String BillMonth, //ok
                                           String BillDate, //ok
                                           String  month, //ok
                                           String day, //ok
                                           String year, //ok
                                           String Demand, // New TextBox
                                           String reactive, // ok
                                           String powerfactor, //ok
                                           String kw_cummulative, //ok
                                           String reading1, //ok
                                           String reading2, //ok
                                           String reading3, //ok
                                           String reading4, //ok
                                           String  reading5, //ok
                                           String  reading6, //ok
                                           String  reading7, //ok
                                           String  reading8, //ok
                                           String  reading9, //ok
                                           String  reading10, //ok
                                           String  iwpowerfactor, //ok
                                           String demand_consumption, //ok
                                           String reactive_consumption, //ok
                                           String Averange_Consumption, //ok
                                           String Average_Reading, //ok
                                           String multiplier, //ok
                                           String Meter_Box, //ok
                                           String Demand_Reset, //ok
                                           String Test_Block, //ok
                                           String RemarksCode,
                                           String remarks_abbreviation, // not OK
                                           String Remarks, //ok
                                           String Comment, //ok
                                           String Reader_ID, //ok
                                           String meter_reader, //ok
                                           String Reading_Attempt, //ok
                                           String Print_Attempt, // ok but no print
                                           String force_reading, //ok
                                           String r_latitude, //ok
                                           String r_longitude, //ok
                                           String printlatitude, //ok
                                           String printlongitude, //ok
                                           String improbable_reading, //ok
                                           String negative_reading, //ok
                                           String change_reading, //ok
                                           String cg_vat_zero_tag, //ok
                                           String reading_remarks, //ok
                                           String old_key, // not textbox
                                           String new_key, // not textbox
                                           String transfer_data_status, // SMS or LTE function
                                           String upload_status, //upload function status
                                           String code, //ok
                                           String area,
                                           String rate_code, //ok
                                           String cummulative_multiplier, //ok
                                           String oebr_number, //ok
                                           String Sequence, //ok
                                           String Reading_TimeStamp, //ok
                                           String Print_TimeStamp, //ok
                                           String timestamp, //ok
                                           String bill_number, // ok
                                           String GenerationSystem , // ok
                                            String BenHost, // Bill PECO
                                            String GRAM , // ok
                                                    String ICERA , // ok
                                                            String PowerArtReduction , // ok
    String  TransmissionDelivery , // ok
            String TransmissionDelivery2, // ok
    String  System_Loss,// ok
    String  Gen_Trans_Rev, // ok
    String  DistributionNetwork, // ok
    String  DistributionNetwork2,// ok
    String   DistributionNetwork3, // ok
    String   RetailElectricService , // ok
    String  RetailElectricService2, // ok
    String   Metering_cust, // ok
    String  Metering_cust_2, // ok
    String Metering_kwh, // Bill
            String loan , // Bill
                    String RFSC , // ok
    String  Distribution_Rev , // ok
    String  MissionaryElectrification, // ok
    String  EnvironmentCharge, // ok
    String  NPC_StrandedDebts,  // ok
    String   NPC_StrandedCost, // ok
    String  DUsCost, // ok
    String    DCDistributionCharge, // ok
    String   DCDemandCharge, // ok
    String  TCTransSystemCharge, // ok
    String   SCSupplySysCharge, // ok
    String  equal_tax, // ok
    String   CrossSubsidyRemoval , // ok
    String  Universal_Charges , // ok
    String   Lifeline_Charge, // ok
    String   InterclassCrossSubsidy , // ok
    String   SeniorCitizenSubsidy, // ok
    String   ICCS_Adjustment, // ok
    String   ICCrossSubsidyCharge, // ok //ME Renewable Energy Dev ILECO II
    String   FitAllCharge, // ok
    String   PPD_Adjustment, // ok
    String    GenerationCostAdjustment, // ok
    String    PowerCostAdjustment, // ok
    String    Other_Rev, // ok
    String   GenerationVat, // ok
    String     TransmissionVat , // ok
    String   SystemLossVat , // ok
    String    DistributionVat, // ok
    String    OtherVat, // ok
    String     Government_Rev,  // ok
    String     CurrentBill, // ok
    String     amountdue, // ok
    String     overdue, // ok
    String      franchise_tax, // Bill
    String    coreloss, //ok
    String       surcharge, //ok
    String      rentalfee,
    String    delivered, //ok
    String       check_previous, // Taging if previous is check
    String       ispn,  //PECO
    String     SCD , //ok
    String        pnrecvdte, //PECO
    String      pnrecvby, //PECO
    String    recvby, // Delivery
    String   hash, //PECO
    String      isreset, // DI ALAM NI ALEX
    String       isprntd, // DI ALAM NI ALEX
    String       meter_count, //ok
    String        delivery_id, // Delivery
    String          delivery_remarks, // Delivery
    String        delivery_comment, // Delivery
    String       reading_signature, // Delivery
    String       real_property_tax,  //Bill
    String         cc_rstc_refund, //Bill
    String        cc_rstc_refund2, //Bill
    String      moa, //Bill
    String      eda, //Bill
    String       ModifiedDate,
    String       ModifiedBy){
        boolean result = false;

        result = ReadingModel.doReadingLogs(
                Client, //ok
                CustomerID, //ok
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
                GenerationSystem, // ok
                BenHost, // Bill PECO
                GRAM, // ok
                ICERA , // ok
                PowerArtReduction, // ok
                TransmissionDelivery, // ok
                TransmissionDelivery2, // ok
                System_Loss,// ok
                Gen_Trans_Rev , // ok
                DistributionNetwork, // ok
                DistributionNetwork2 ,// ok
                DistributionNetwork3 , // ok
                RetailElectricService , // ok
                RetailElectricService2 , // ok
                Metering_cust , // ok
                Metering_cust_2, // ok
                Metering_kwh, // Bill
                loan , // Bill
                RFSC , // ok
                Distribution_Rev , // ok
                MissionaryElectrification , // ok
                EnvironmentCharge , // ok
                NPC_StrandedDebts ,  // ok
                NPC_StrandedCost, // ok
                DUsCost, // ok
                DCDistributionCharge, // ok
                DCDemandCharge, // ok
                TCTransSystemCharge, // ok
                SCSupplySysCharge, // ok
                equal_tax , // ok
                CrossSubsidyRemoval , // ok
                Universal_Charges, // ok
                Lifeline_Charge , // ok
                InterclassCrossSubsidy  , // ok
                SeniorCitizenSubsidy, // ok
                ICCS_Adjustment, // ok
                ICCrossSubsidyCharge, // ok //ME Renewable Energy Dev ILECO II
                FitAllCharge, // ok
                PPD_Adjustment, // ok
                GenerationCostAdjustment, // ok
                PowerCostAdjustment, // ok
                Other_Rev, // ok
                GenerationVat , // ok
                TransmissionVat , // ok
                SystemLossVat , // ok
                DistributionVat, // ok
                OtherVat, // ok
                Government_Rev ,  // ok
                CurrentBill, // ok
                amountdue , // ok
                overdue, // ok
                franchise_tax, // Bill
                coreloss, //ok
                surcharge, //ok
                rentalfee,
                delivered, //ok
                check_previous, // Taging if previous is check
                ispn,  //PECO
                SCD, //ok
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
       // ExportCSV mExportCSV = new ExportCSV();
       // mExportCSV.Excute("Test");

        return result;
    }

    public static String AverageIleco2Validation(String Remarks,String RemarksCode) {
        String Average_Reading = "False";
        return Average_Reading;
    }
    public static String AverageValidation(String Remarks,String RemarksCode) {
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
    public static String ConsumptionValidation(String AvgKWH, String PreviousKWH, String KWH) {

        double percentage = 0.80;
        double consumption_percentage = 0;
        double high_consumption_range = 0;
        double low_consumption_range = 0;
        double average_consumption = 0;
        average_consumption = Double.valueOf(AvgKWH);
        if(Double.parseDouble(KWH) == 0){
            return "ZERO CONSUMPTION";
        }
        if (Double.parseDouble(KWH) < 0) {
            return "NEGATIVE CONSUMPTION";
        }
        if(average_consumption <= 10){
            return "";
        }
        consumption_percentage = average_consumption * percentage;
        //consumption_percentage = Double.parseDouble(AvgKWH) * percentage;
        high_consumption_range = average_consumption + consumption_percentage;
        low_consumption_range = average_consumption - consumption_percentage;
        Log.i(TAG,"AVG "+ AvgKWH);
        Log.i(TAG,"KWH "+ KWH);
        Log.i(TAG,"consumption_percentage "+ consumption_percentage);
        Log.i(TAG,"high_consumption_range "+ high_consumption_range);
        Log.i(TAG,"low_consumption_range "+ low_consumption_range);
        if (Double.parseDouble(KWH) <= low_consumption_range) {
            return "LOW CONSUMPTION";
        }
        if (Double.parseDouble(KWH) >= high_consumption_range) {
            return "HIGH CONSUMPTION";
        }

        return "";
    }

    public static String GetKWH(String Multiplier, String PreviousReading, String Reading) {

        if(Reading.equals("") || Reading.equals(null) || Reading.isEmpty()){
            Reading = "0";
        }

      
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

    public static boolean SaveReadingLogs(){
        boolean result_logs = false;
        String customer_id = Liquid.AccountNumber;
        switch (Liquid.Client){
            case "baliwag_wd":
                customer_id = Liquid.C_ID;
                break;
            default:
                customer_id = Liquid.AccountNumber;
        }
        result_logs = Liquid.SaveReadingLogs(Liquid.Client, //ok
                customer_id, //ok
                Liquid.AccountNumber, //ok
                Liquid.JobId, //ok
                Liquid.AccountName, //ok
                Liquid.route, //ok
                Liquid.itinerary, //ok
                Liquid. MeterNumber, //ok
                Liquid.serial, //ok
                Liquid.previous_reading, //ok
                Liquid.Reading, //ok
                Liquid.previous_consumption, //ok
                Liquid.Present_Consumption, //computation
                Liquid.previous_reading_date, //ok
                Liquid.present_reading_date, //ok
                Liquid.duedate, // if there value currentdate +6 //ok
                Liquid.discondate, // duedate +2 days //ok
                Liquid.ReadingDate, //ok
                Liquid.BillYear, //ok
                Liquid.BillMonth, //ok
                Liquid.BillDate, //ok
                Liquid.month, //ok
                Liquid. day, //ok
                Liquid. year, //ok
                Liquid. Demand, // New TextBox
                Liquid. reactive, // ok
                Liquid.  powerfactor, //ok
                Liquid.kw_cummulative, //ok
                Liquid. reading1, //ok
                Liquid. reading2, //ok
                Liquid.  reading3, //ok
                Liquid.  reading4, //ok
                Liquid.reading5, //ok
                Liquid.  reading6, //ok
                Liquid.  reading7, //ok
                Liquid.reading8, //ok
                Liquid.reading9, //ok
                Liquid.reading10, //ok
                Liquid.iwpowerfactor, //ok
                Liquid.demand_consumption, //ok
                Liquid.reactive_consumption, //ok
                Liquid. Averange_Consumption, //ok
                Liquid.Average_Reading, //ok
                Liquid.multiplier, //ok
                Liquid.Meter_Box, //ok
                Liquid.Demand_Reset, //ok
                Liquid.Test_Block, //ok
                Liquid.RemarksCode,
                Liquid.remarks_abbreviation, // not OK
                Liquid.Remarks, //ok
                Liquid.ReaderComment, //ok
                Liquid.Reader_ID, //ok
                Liquid.meter_reader, //ok
                Liquid.Reading_Attempt, //ok
                Liquid. Print_Attempt, // ok but no print
                Liquid.force_reading, //ok
                Liquid.r_latitude, //ok
                Liquid.r_longitude, //ok
                Liquid.printlatitude, //ok
                Liquid.printlongitude, //ok
                Liquid.improbable_reading, //ok
                Liquid.negative_reading, //ok
                Liquid.change_reading, //ok
                Liquid.cg_vat_zero_tag, //ok
                Liquid.reading_remarks, //ok
                Liquid.old_key, // not textbox
                Liquid.new_key, // not textbox
                Liquid.transfer_data_status, // SMS or LTE function
                Liquid.upload_status, //upload function status
                Liquid.code, //ok
                Liquid.area,
                Liquid.rate_code, //ok
                Liquid.cummulative_multiplier, //ok
                Liquid.oebr_number, //ok
                Liquid.Sequence, //ok
                Liquid.Reading_TimeStamp, //ok
                Liquid.Print_TimeStamp, //ok
                Liquid.timestamp, //ok
                Liquid.bill_number, // ok
                String.valueOf(LiquidBilling.total_gen_sys_charge), // ok
                Liquid.BenHost, // Bill PECO
                String.valueOf(LiquidBilling.total_psalm_daa), // ok
                String.valueOf(LiquidBilling.total_gram_icera_daa_erc), // ok
                String.valueOf(LiquidBilling.total_power_act_reduc), // ok
                String.valueOf(LiquidBilling.total_trans_del_charge_1), // ok
                String.valueOf(LiquidBilling.total_trans_del_charge_2), // ok
                String.valueOf(LiquidBilling.total_sys_loss_1),// ok
                String.valueOf(LiquidBilling.total_gen_trans), // ok
                String.valueOf(LiquidBilling.total_distrib_net_charge_1), // ok
                String.valueOf(LiquidBilling.total_distrib_net_charge_2),// ok
                String.valueOf(LiquidBilling.total_distrib_net_charge_3), // ok
                String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1), // ok
                String.valueOf(LiquidBilling.total_ret_elec_serv_charge_2), // ok
                String.valueOf(LiquidBilling.total_met_charge_1), // ok
                String.valueOf(LiquidBilling.total_met_charge_2), // ok
                Liquid.Metering_kwh, // Bill
                String.valueOf(LiquidBilling.total_final_loan_con), // Bill
                String.valueOf(LiquidBilling.total_rfsc), // ok
                String.valueOf(LiquidBilling.total_distribution_revenue), // ok
                String.valueOf(LiquidBilling.total_missionary_elec), // ok
                String.valueOf(LiquidBilling.total_environmental_charge), // ok
                String.valueOf(LiquidBilling.total_npc_stran_deb),  // ok
                String.valueOf(LiquidBilling.total_npc_stran_cos), // ok
                Liquid.DUsCost, // ok
                Liquid.DCDistributionCharge, // ok
                Liquid.DCDemandCharge, // ok
                Liquid.TCTransSystemCharge, // ok
                Liquid.SCSupplySysCharge, // ok
                String.valueOf(LiquidBilling.equalization_taxes), // ok
                String.valueOf(LiquidBilling.cross_subsidy_removal), // ok
                String.valueOf(LiquidBilling.total_universal), // ok
                String.valueOf(LiquidBilling.total_lifeline), // ok
                String.valueOf(LiquidBilling.total_interclass), // ok
                String.valueOf(LiquidBilling.total_senior), // ok
                Liquid.ICCS_Adjustment, // ok
                String.valueOf(LiquidBilling.total_me_renewable_energy_dev), // ok //ME Renewable Energy Dev ILECO II
                String.valueOf(LiquidBilling.total_fit_all), // ok
                String.valueOf(LiquidBilling.total_prompt_payment_disc_adj), // ok
                Liquid.GenerationCostAdjustment, // ok
                Liquid.PowerCostAdjustment, // ok
                String.valueOf(LiquidBilling.total_other_charges), // ok
                String.valueOf(LiquidBilling.total_generation), // ok
                String.valueOf(LiquidBilling.total_transmission), // ok
                String.valueOf(LiquidBilling.total_sys_loss_2), // ok
                String.valueOf(LiquidBilling.total_distribution), // ok
                String.valueOf(LiquidBilling.total_others), // ok
                String.valueOf(LiquidBilling.total_government_revenue),  // ok
                String.valueOf(LiquidBilling.total_current_bill), // ok
                String.valueOf(LiquidBilling.total_amount_due), // ok
                Liquid.overdue, // ok
                Liquid.franchise_tax, // Bill
                Liquid.coreloss, //ok
                Liquid.surcharge, //ok
                Liquid.rentalfee,
                Liquid.delivered, //ok
                Liquid.check_previous, // Taging if previous is check
                Liquid.ispn,  //PECO
                String.valueOf(LiquidBilling.total_senior), //ok
                Liquid.pnrecvdte, //PECO
                Liquid.pnrecvby, //PECO
                Liquid.recvby, // Delivery
                Liquid.hash, //PECO
                Liquid.isreset, // DI ALAM NI ALEX
                Liquid.isprntd, // DI ALAM NI ALEX
                Liquid.meter_count, //ok
                Liquid.delivery_id, // Delivery
                Liquid.delivery_remarks, // Delivery
                Liquid.delivery_comment, // Delivery
                Liquid.reading_signature, // Delivery
                Liquid.real_property_tax,  //Bill
                Liquid.cc_rstc_refund, //Bill
                Liquid.cc_rstc_refund2, //Bill
                Liquid.moa, //Bill
                Liquid.eda, //Bill
                Liquid.ModifiedDate,
                Liquid.ModifiedBy);
        return result_logs;
    }
    public static boolean SaveReading(){
        boolean result = false;
        String customer_id = Liquid.AccountNumber;
        switch (Liquid.Client){
            case "baliwag_wd":
                customer_id = Liquid.C_ID;
                break;
            default:
                customer_id = Liquid.AccountNumber;
        }
        result = Liquid.SaveReading(Liquid.Client, //ok
                customer_id, //ok
                Liquid.AccountNumber, //ok
                Liquid.JobId, //ok
                Liquid.AccountName, //ok
                Liquid.route, //ok
                Liquid.itinerary, //ok
                Liquid. MeterNumber, //ok
                Liquid.serial, //ok
                Liquid.previous_reading, //ok
                Liquid.Reading, //ok
                Liquid.previous_consumption, //ok
                Liquid.Present_Consumption, //computation
                Liquid.previous_reading_date, //ok
                Liquid.present_reading_date, //ok
                Liquid.duedate, // if there value currentdate +6 //ok
                Liquid.discondate, // duedate +2 days //ok
                Liquid.ReadingDate, //ok
                Liquid.BillYear, //ok
                Liquid.BillMonth, //ok
                Liquid.BillDate, //ok
                Liquid.month, //ok
                Liquid.day, //ok
                Liquid.year, //ok
                Liquid.Demand, // New TextBox
                Liquid.reactive, // ok
                Liquid.powerfactor, //ok
                Liquid.kw_cummulative, //ok
                Liquid.reading1, //ok
                Liquid.reading2, //ok
                Liquid.reading3, //ok
                Liquid.reading4, //ok
                Liquid.reading5, //ok
                Liquid.reading6, //ok
                Liquid.reading7, //ok
                Liquid.reading8, //ok
                Liquid.reading9, //ok
                Liquid.reading10, //ok
                Liquid.iwpowerfactor, //ok
                Liquid.demand_consumption, //ok
                Liquid.reactive_consumption, //ok
                Liquid. Averange_Consumption, //ok
                Liquid.Average_Reading, //ok
                Liquid.multiplier, //ok
                Liquid.Meter_Box, //ok
                Liquid.Demand_Reset, //ok
                Liquid.Test_Block, //ok
                Liquid.RemarksCode,
                Liquid.remarks_abbreviation, // not OK
                Liquid.Remarks, //ok
                Liquid.ReaderComment, //ok
                Liquid.Reader_ID, //ok
                Liquid.meter_reader, //ok
                Liquid.Reading_Attempt, //ok
                Liquid. Print_Attempt, // ok but no print
                Liquid.force_reading, //ok
                Liquid.r_latitude, //ok
                Liquid.r_longitude, //ok
                Liquid.printlatitude, //ok
                Liquid.printlongitude, //ok
                Liquid.improbable_reading, //ok
                Liquid.negative_reading, //ok
                Liquid.change_reading, //ok
                Liquid.cg_vat_zero_tag, //ok
                Liquid.reading_remarks, //ok
                Liquid.old_key, // not textbox
                Liquid.new_key, // not textbox
                Liquid.transfer_data_status, // SMS or LTE function
                Liquid.upload_status, //upload function status
                Liquid.code, //ok
                Liquid.area,
                Liquid.rate_code, //ok
                Liquid.cummulative_multiplier, //ok
                Liquid.oebr_number, //ok
                Liquid.Sequence, //ok
                Liquid.Reading_TimeStamp, //ok
                Liquid.Print_TimeStamp, //ok
                Liquid.timestamp, //ok
                Liquid.bill_number, // ok
                String.valueOf(LiquidBilling.total_gen_sys_charge), // ok
                Liquid.BenHost, // Bill PECO
                String.valueOf(LiquidBilling.total_psalm_daa), // ok
                String.valueOf(LiquidBilling.total_gram_icera_daa_erc), // ok
                String.valueOf(LiquidBilling.total_power_act_reduc), // ok
                String.valueOf(LiquidBilling.total_trans_del_charge_1), // ok
                String.valueOf(LiquidBilling.total_trans_del_charge_2), // ok
                String.valueOf(LiquidBilling.total_sys_loss_1),// ok
                String.valueOf(LiquidBilling.total_gen_trans), // ok
                String.valueOf(LiquidBilling.total_distrib_net_charge_1), // ok
                String.valueOf(LiquidBilling.total_distrib_net_charge_2),// ok
                String.valueOf(LiquidBilling.total_distrib_net_charge_3), // ok
                String.valueOf(LiquidBilling.total_ret_elec_serv_charge_1), // ok
                String.valueOf(LiquidBilling.total_ret_elec_serv_charge_2), // ok
                String.valueOf(LiquidBilling.total_met_charge_1), // ok
                String.valueOf(LiquidBilling.total_met_charge_2), // ok
                Liquid.Metering_kwh, // Bill
                String.valueOf(LiquidBilling.total_final_loan_con), // Bill
                String.valueOf(LiquidBilling.total_rfsc), // ok
                String.valueOf(LiquidBilling.total_distribution_revenue), // ok
                String.valueOf(LiquidBilling.total_missionary_elec), // ok
                String.valueOf(LiquidBilling.total_environmental_charge), // ok
                String.valueOf(LiquidBilling.total_npc_stran_deb),  // ok
                String.valueOf(LiquidBilling.total_npc_stran_cos), // ok
                Liquid.DUsCost, // ok
                Liquid.DCDistributionCharge, // ok
                Liquid.DCDemandCharge, // ok
                Liquid.TCTransSystemCharge, // ok
                Liquid.SCSupplySysCharge, // ok
                String.valueOf(LiquidBilling.equalization_taxes), // ok
                String.valueOf(LiquidBilling.cross_subsidy_removal), // ok
                String.valueOf(LiquidBilling.total_universal), // ok
                String.valueOf(LiquidBilling.total_lifeline), // ok
                String.valueOf(LiquidBilling.total_interclass), // ok
                String.valueOf(LiquidBilling.total_senior), // ok
                Liquid.ICCS_Adjustment, // ok
                String.valueOf(LiquidBilling.total_me_renewable_energy_dev), // ok //ME Renewable Energy Dev ILECO II
                String.valueOf(LiquidBilling.total_fit_all), // ok
                String.valueOf(LiquidBilling.total_prompt_payment_disc_adj), // ok
                Liquid.GenerationCostAdjustment, // ok
                Liquid.PowerCostAdjustment, // ok
                String.valueOf(LiquidBilling.total_other_charges), // ok
                String.valueOf(LiquidBilling.total_generation), // ok
                String.valueOf(LiquidBilling.total_transmission), // ok
                String.valueOf(LiquidBilling.total_sys_loss_2), // ok
                String.valueOf(LiquidBilling.total_distribution), // ok
                String.valueOf(LiquidBilling.total_others_vat), // ok
                String.valueOf(LiquidBilling.total_government_revenue),  // ok
                String.valueOf(LiquidBilling.total_current_bill), // ok
                String.valueOf(LiquidBilling.total_amount_due), // ok
                Liquid.overdue, // ok // inclusion ileco2
                Liquid.franchise_tax, // Bill
                Liquid.coreloss, //ok
                Liquid.surcharge, //ok // ILP ILECO2
                Liquid.rentalfee, // GOV SUBSIDIES ILECO2
                Liquid.delivered, //ok
                Liquid.check_previous, // Taging if previous is check
                Liquid.ispn,  //PECO
                String.valueOf(LiquidBilling.total_senior), //ok
                Liquid.pnrecvdte, //PECO
                Liquid.pnrecvby, //PECO
                Liquid.recvby, // Delivery
                Liquid.hash, //PECO
                Liquid.isreset, // DI ALAM NI ALEX
                Liquid.isprntd, // DI ALAM NI ALEX
                Liquid.meter_count, //ok
                Liquid.delivery_id, // Delivery
                Liquid.delivery_remarks, // Delivery
                Liquid.delivery_comment, // Delivery
                Liquid.reading_signature, // Delivery
                Liquid.real_property_tax,  //Bill
                Liquid.cc_rstc_refund, //Bill
                Liquid.cc_rstc_refund2, //Bill
                Liquid.moa, //Bill
                Liquid.eda, //Bill
                Liquid.ModifiedDate,
                Liquid.ModifiedBy);


       // ExportCSV mExportCSV = new ExportCSV();
       // mExportCSV.Excute("Test");
        return result;
    }

    public static boolean SaveSurvey(){

        boolean result = false;

        result = MeterNotInListModel.Save(  Liquid.SelectedId,
                Liquid.Client,
                Liquid.MeterNumber  ,
                Liquid.AccountName,
                Liquid.Complete_Address,
                Liquid.Remarks,
                Liquid.Demand,
                Liquid.SurveyType,
                Liquid.SurveyAmpirCapacity,
                "",
                Liquid.r_latitude,
                Liquid.r_longitude,
                Liquid.printlatitude,
                Liquid.printlongitude,
                Liquid.currentDateTime(),
                Liquid.User,
                Liquid.User,
                Liquid.route,
                Liquid.itinerary,
                Liquid.ReadingDate,
                "",
                "",
                Liquid.Reading,
                Liquid.ContactNumber,
                Liquid.EmailAdd,
                Liquid.MeterBrand,
                Liquid.MeterTypeValue,
                Liquid.StructureValue,
                Liquid.AccountNumber,
                Liquid.serial
        );

        return result;
    }

    public static String[] CokeCustomerColumns = {
            "INCLUDED" ,
            "MKTSGM" ,
            "SEGM" ,
            "CU" ,
            "REGION" ,
            "Customer_No",
            "Sales_Route" ,
            "Name" ,
            "Name_2" ,
            "City" ,
            "Postal_Codes" ,
            "Street" ,
            "House_No" ,
            "Street_4" ,
            "Longitude" ,
            "Latitude" ,
            "Remarks" ,
            "period",


    };

    public static String[] UMSUserAccountColumn = {
            "UserId" ,
            "Username" ,
            "Password" ,
            "Status" ,
            "branch" ,
            "position",
            "name" ,
            "lastname" ,
            "firstname" ,
            "middlename" ,
    };




    public static String[] CustomerDeliveryColumns = {
            "tracking_number" ,
            "barcode" ,
            "client" ,
            "job_id" ,
            "user_id" ,
            "fullname",
            "position" ,
            "code" ,
            "type" ,
            "quantity" ,
            "timestamp" ,
            "delivery_date" ,
            "received" ,
            "status" ,
            "latitude" ,
            "longitude" ,


    };
    public static String[] MeterNotInListColumnsUpdate = {
            "[id]" ,
            "[job_id]" ,
            "[client]" ,
            "[customer_meterno]",
            "[customer_name]" ,
            "[customer_address]" ,
            "[remarks]" ,
            "[demand]" ,
            "[type]" ,
            "[ampirCapacity]" ,
            "[picture]" ,
            "[latitude]" ,
            "[longitude]" ,
            "[house_latitude]" ,
            "[house_longitude]" ,
            "[timestamp]" ,
            "[Reader_ID]" ,
            "[modifiedby]" ,
            "[route]",
            "[itinerary]",
            "[reading_date]",
            "[nearest_meter]",
            "[nearest_seq]",
            "[reading]",
            "[contactnumber]",
            "[emailaddress]",
            "[meterbrand]",
            "[metertype]",
            "[structure]",
            "[accountnumber]",
            "[serial]"
    };
    public static String[] MeterNotInListColumns = {
                    "[job_id]" ,
                    "[client]" ,
                    "[customer_meterno]",
                    "[customer_name]" ,
                    "[customer_address]" ,
                    "[remarks]" ,
                    "[demand]" ,
                    "[type]" ,
                    "[ampirCapacity]" ,
                    "[picture]" ,
                    "[latitude]" ,
                    "[longitude]" ,
            "[house_latitude]" ,
            "[house_longitude]" ,
                    "[timestamp]" ,
                    "[Reader_ID]" ,
                    "[modifiedby]" ,
                    "[route]",
                    "[itinerary]",
                    "[reading_date]",
                    "[nearest_meter]",
                    "[nearest_seq]",
                    "[reading]",
                    "[contactnumber]",
                    "[emailaddress]",
                    "[meterbrand]",
                    "[metertype]",
                    "[structure]",
            "[accountnumber]",
            "[serial]"
    };



    public static String[] ReadingColumns = {
            "[Client] ",
            "[C_ID] ",
            "[AccountNumber] ",
            "[job_id]",
            "[name]",
            "[route]",
            "[itinerary]",
            "[meter_number] ",
            "[serial]",
            "[previous_reading]",
            "[present_Reading]",
            "[previous_consumption]",
            "[Present_Consumption]",
            "[previous_reading_date]",
            "[present_reading_date]",
            "[duedate]",
            "[discondate]",
            "[Reading_Date] ",
            "[BillYear]",
            "[BillMonth]",
            "[BillDate]",
            "[month]",
            "[day]",
            "[year]",
            "[Demand]",
            "[reactive]",
            "[powerfactor]",
            "[kw_cummulative]",
            "[reading1]",
            "[reading2]",
            "[reading3]",
            "[reading4]",
            "[reading5]",
            "[reading6]",
            "[reading7]",
            "[reading8]",
            "[reading9]",
            "[reading10]",
            "[iwpowerfactor]",
            "[demand_consumption]",
            "[reactive_consumption]",
            "[Averange_Consumption]",
            "[Average_Reading]",
            "[multiplier]",
            "[Meter_Box]",
            "[Demand_Reset]",
            "[Test_Block]",
            "[Remarks_Code]",
            "[remarks_abbreviation]",
            "[Remarks]",
            "[Comment]",
            "[Reader_ID]",
            "[meter_reader]",
            "[Reading_Attempt]",
            "[Print_Attempt]",
            "[force_reading]",
            "[r_latitude]",
            "[r_longitude]",
            "[printlatitude]",
            "[printlongitude]",
            "[improbable_reading]",
            "[negative_reading]",
            "[change_reading]",
            "[cg_vat_zero_tag]",
            "[reading_remarks]",
            "[old_key]",
            "[new_key]",
            "[transfer_data_status]",
            "[upload_status]",
            "[code]",
            "[area]",
            "[rate_code]",
            "[cummulative_multiplier]",
            "[oebr_number]",
            "[sequence]",
            "[Reading_TimeStamp]",
            "[Print_TimeStamp]",
            "[timestamp]",
            "[bill_number]",
            "[GenerationSystem]",
            "[BenHost]",
            "[GRAM]",
            "[ICERA]",
            "[PowerArtReduction]",
            "[TransmissionDelivery]",
            "[TransmissionDelivery2]",
            "[System_Loss]",
            "[Gen_Trans_Rev]",
            "[DistributionNetwork]",
            "[DistributionNetwork2]",
            "[DistributionNetwork3]",
            "[RetailElectricService]",
            "[RetailElectricService2]",
            "[Metering(cust)]",
            "[Metering(cust)2]",
            "[Metering(kwh)]",
            "[loan]",
            "[RFSC]",
            "[Distribution_Rev]",
            "[MissionaryElectrification]",
            "[EnvironmentCharge]",
            "[NPC_StrandedDebts]",
            "[NPC_StrandedCost]",
            "[DUsCost]",
            "[DCDistributionCharge]",
            "[DCDemandCharge]",
            "[TCTransSystemCharge]",
            "[SCSupplySysCharge]",
            "[equal_tax]",
            "[CrossSubsidyRemoval]",
            "[Universal_Charges]",
            "[Lifeline(Charge)]",
            "[InterclassCrossSubsidy]",
            "[SeniorCitizenSubsidy]",
            "[ICCS_Adjustment]",
            "[ICCrossSubsidyCharge]",
            "[FitAllCharge]",
            "[PPD_Adjustment]",
            "[GenerationCostAdjustment]",
            "[PowerCostAdjustment]",
            "[Other_Rev]",
            "[GenerationVat]",
            "[TransmissionVat]",
            "[SystemLossVat]",
            "[DistributionVat]",
            "[OtherVat]",
            "[Government_Rev]",
            "[CurrentBill]",
            "[amountdue]",
            "[overdue]",
            "[franchise_tax]",
            "[coreloss]",
            "[surcharge]",
            "[rentalfee]",
            "[delivered]",
            "[check_previous]",
            "[ispn]",
            "[SCD]",
            "[pnrecvdte]",
            "[pnrecvby]",
            "[recvby]",
            "[hash]",
            "[isreset]",
            "[isprntd]",
            "[meter_count]",
            "[delivery_id]",
            "[delivery_remarks]",
            "[delivery_comment]",
            "[reading_signature]",
            "[real_property_tax]",
            "[cc_rstc_refund]",
            "[cc_rstc_refund2]",
            "[moa]",
            "[eda]",
            "[ModifiedDate]",
            "[ModifiedBy]",
    };


    public static String[] DisconnectionTable = {
            "job_id",
            "client",
            "id",
            "accountnumber",
            "name",
            "BA",
            "route",
            "itinerary",
            "tin",
            "meter_number",
            "meter_count",
            "serial",
            "previous_reading",
            "last_Reading",
            "previous_consumption",
            "last_consumption",
            "previous_reading_date",
            "disconnection_date",
            "month",
            "day",
            "year",
            "demand",
            "reactive",
            "powerfactor",
            "reading1",
            "reading2",
            "reading3",
            "reading4",
            "reading5",
            "reading6",
            "reading7",
            "reading8",
            "reading9",
            "reading10",
            "iwpowerfactor",
            "multiplier",
            "meter_box",
            "demand_reset",
            "remarks_code",
            "remarks_description",
            "remarks_reason",
            "comment",
            "meter_type",
            "meter_brand",
            "reader_id",
            "meter_reader",
            "disconnection_attempt",
            "r_latitude",
            "r_longitude",
            "transfer_data_status",
            "upload_status",
            "status",
            "code",
            "area",
            "region",
            "country",
            "province",
            "city",
            "brgy",
            "country_label",
            "province_label",
            "region_label",
            "municipality_city_label",
            "loc_barangay_label",
            "street",
            "complete_address",
            "rate_code",
            "sequence",
            "disconnection_timestamp",
            "timestamp",
            "isdisconnected",
            "ispayed",
            "disconnection_signature",
            "recvby",
            "amountdue",
            "duedate",
            "cyclemonth",
            "cycleyear",
            "or_number",
            "arrears",
            "total_amount_due",
            "last_payment_date",
            "sysentrydate",
            "modifieddate",
            "modifiedby",
    };
    public static String[] CustomerDisconnectionDownloadColumns = {
            "Client",
            "Code",
            "job_id",
            "id",
            "lastname",
            "firstname",
            "middlename",
            "type",
            "establishment",
            "accountnumber",
            "account_name",
            "tin",
            "BA",
            "route",
            "route_itinerary",
            "itinerary",
            "status",
            "status_description",
            "country",
            "region",
            "province",
            "city",
            "brgy",
            "country_label",
            "region_label",
            "province_label",
            "municipality_city_label",
            "loc_barangay_label",
            "street",
            "complete_address",
            "longitude",
            "latitude",
            "sequence",
            "multiplier",
            "meter_type",
            "meterbrand",
            "meternumber",
            "serial",
            "meter_count",
            "previousreading",
            "previousconsumption",
            "previous_reading_date",
            "disconnection_date",
            "amountdue",
            "duedate",
            "cycleyear",
            "cyclemonth",
            "or_number",
            "client_remarks",
            "arrears",
            "total_amount_due",
            "last_payment_date",
            "sysentrydate",
            "modifieddate",
            "modifiedby",

};

    public static String[] RatesColumns = {
            "R_Client", //1
            "R_ID",
            "R_Type",
            "RD_ID",
            "rate_description",
            "classification",
            "Rates_Price",
            "cycle",
            "rate_date_from",
            "rate_date_to",
            "Rates_Status",
            "Rates_SysEntryDate",
            "Rates_ModifiedDate",
            "Rates_ModifiedBy"
    };
    public static String[] CustomerReadingColumns = {
            "C_Client",
            "Code",
            "job_id",
            "C_ID",
            "C_Lastname",
            "C_Firstname",
            "C_Middlename",
            "C_Type",
            "classification",
            "C_Status",
            "C_Establishment",
            "route",
            "route_itinerary",
            "itinerary",
            "status_description",
            "C_Approval_Status",
            "R_Description",
            "account_name",
            "C_Country",
            "C_Region",
            "C_Province",
            "C_City",
            "C_Brgy",
            "Country_Label",
            "Region_Label",
            "Province_Label",
            "Municipality_City_Label",
            "Loc_Barangay_Label",
            "C_Street",
            "Complete_Address",
            "C_AccountNumber",
            "C_Meter_Type",
            "bill_route",
            "bill_itinerary",
            "bill_sequence",
            "CED_Sequence",
            "CED_MeterBrand",
            "CED_MeterNumber",
            "pipe_size",
            "special_meter_tag",
            "multiplier",
            "cg_vat_zero_tag",
            "meter_count",
            "arrears",
            "coreloss",
            "Average_Reading",
            "Averange_Consumption",
            "PreviousReading",
            "PreviousReactive",
            "PreviousDemand",
            "PreviousPowerFactor",
            "PreviousKWCummulative",
            "PreviousConsumption",
            "Previous_Reading_Date",
            "reading_date",
            "rentalfee", // government subsidies
            "Transformer", // ILP
            "inclusion",
            "senior_citizen_tag",
            "months_unpaid_bill",
            "change_meter_reading",
            "c_group",
            "book",
            "ispn",
            "interest",
            "serial",
            "duedate",
            "bill_number",
            "BillMonth",
            "BillDate",
            "year",
            "Average_amount",
            "eda",
            "over90days" ,
            "over60days" ,
            "advance_payment" ,
            "scap_bill" ,
            "scap_paid" ,
            "adjust_amount" ,
            "adjust_amount1" ,
            "add_deduct" ,
            "discount" ,
            "total_initial",
            "C_SysEntryDate",
            "C_ModifiedDate",
            "C_ModifiedBy",
            "load",
            "OCDate1",
            "latitude",
            "longitude",
            "CMPresentReadingKWH",
            "over30days",
            "over60days",


    };

    public static String BaliwagAverageRemarksAbbreviation[] = {
            "RMN",
            "RR",
            "BG",
            "BMG",
            "CC",
            "FA",
            "MIP",
            "MNL",
            "ST",
            "TG",
            "TS",
            "WD"
    };

    public static String RemoveSpecialCharacter(String Filename){
        Filename = Filename.replaceAll(":","");
        Filename = Filename.replace("|","");
        Filename = Filename.replaceAll(" ","");
        Filename = Filename.replaceAll("-","");
        return Filename;
    }
    public static String[] joborders = {
            "id",
            "client",
            "details",
            "title",
            "date",

    };
    public static double getPercent(double count,double over){
        double total = (count / over) * 100;
        return total;
    }
    public static String[] audit_job_order = {
            "JobOrderId",
            "Client",
            "AccountNumber",
            "JobOrderTitle",
            "JobOrderDetails",
            "Latitude",
            "Longitude",
            "JobOrderDate",

    };

    public static String[] audit_upload = {
            "JobOrderId",
            "Client",
            "AccountNumber",
            "Vehicle",
            "Fare",
            "Comment",
            "Latitude",
            "Longitude",
            "JobOrderDate",
            "Status"

    };

    public static String[] CokeCategory = {
            "Store Status",
            "Activation",
            "CDE",
            "Cooler Planogram",
            "Shelf Planogram",
            "SOVI",
            "SoviLocation",
    };

    public static String[] Availability = {
            "customer_id",
            "prodcode",
            "twobottles",
            "onebottle",
            "visible",
            "cold",
            "comment",
            "retprice",
            "picture",
            "period"
    };

    public static String[] CategoryComment = {
            "customer_id",
            "category",
            "comment",
            "period",
    };

    public static String[] StoreStatus = {
            "customer_id",
            "latitude",
            "longitude",
            "status",
            "transferdatastatus",
            "picture",
            "period",

    };

    public static String[] UMSAccounts = {
            "UserID",
            "Username",
            "Password",
            "client",
            "branch",
            "name",
            "lastname",
            "firstname",
            "middlename",
            "position"


    };
    public static String[] StoreStatusServer = {
                 "customer_no",
                "latitude",
                "longitude",
                "status",
                "transferdatastatus",
                "timestamp",
                "auditor",
                "period",
                "picture",
    };
    public static String[] AvailabilityServer = {
            "customer_no",
            "productCode",
            "price",
            "comment",
            "timestamp",
            "auditor",
            "period",
            "picture",
    };

    public static String[] SOVIServer = {
            "customer_no",
            "productName",
            "description",
            "sovi_type",
            "location",
            "numkof",
            "numnonkof",
            "cans",
            "sspet",
            "mspet",
            "ssdoy",
            "ssbrick",
            "msbrick",
            "sswedge",
            "box",
            "litro",
            "pounch",
            "picture",
            "timestamp",
            "auditor",
            "period",
            "comment",
    };

    public static String[] CDEServer = {
            "customer_no",
            "name",
            "category",
            "question",
            "questionvalue",
            "total_count",
            "timestamp",
            "auditor",
            "picture",
            "period",
            "comment",
    };
    public static String[] ActivationServer = {
            "customer_no",
            "name",
            "category",
            "value",
            "total_count",
            "picture",
            "timestamp",
            "auditor",
            "period"
    };

    public static String[] ShelfPlanogramServer = {
            "customer_no",
            "description",
            "value",
            "auditor",
            "timestamp",
            "picture",
            "period"
    };

    public static String[] SoviLocationServer = {
            "customer_no",
            "sovi_location",
            "auditor",
            "timestamp",
            "picture",
            "period"
    };

    public static String[] SignatureServer = {
            "customer_no",
            "store_name",
            "timestamp",
            "auditor",
            "period",
    };

    public static String[] CoolePlanogramServer = {
            "customer_no",
            "name",
            "value",
            "picture",
            "timestamp",
            "period",
            "auditor",
    };
    public static String[] TrackingCategory = {
            "StoreStatus",
            "Availability",
            "SOVI",
            "Activation",
            "CDE",
            "ShelfPlanogram",
            "SoviLocation",
            "CoolerPlanogram",
            "Signature",
            "Picture",
            "Comment",
            "CategoryComment",
    };

    public static String[] TrackingJSONCategory = {
            "store_status",
            "sovi",
            "activation",
            "cde",
            "shelfplanogram",
            "sovilocation",
            "coolerplanogram",
            "signature",
    };

    public static String[] Signature = {
            "customer_id",
            "name",
            "period",
    };

    public static String[] PictureServer = {
            "a_id",
            "customer_no",
            "category",
            "subcategory",
            "subsubcategory",
            "count",
            "picture",
            "timestamp",
            "period",
            "auditor",
            "subtype"
    };

    public static String[] TrackingPicture = {
            "customer_id",
            "category",
            "subcategory",
            "subsubcategory",
            "subtype",
            "count",
            "picture",
            "period",
    };

    public static String[] ColumnPicture = {
            "client", //key
            "AccountNumber", //key
            "type", //key
            "picture", //key
            "timestamp",
            "modifieddate",
            "Reader_ID",
            "modifiedby",
            "reading_date", //key
    };

    public static String[] Comment = {
            "customer_id",
            "comment",
            "period",
    };

    public static String[] Delivery = {
            "m_client",
            "job_id",
            "job_title",
            "m_accountnumber",
            "m_type",
            "m_type_description",
            "m_status",
            "m_remark_code",
            "m_remark",
            "m_comment",
            "m_latitude",
            "m_longitude",
            "transfer_data_status",
            "m_signature",
            "upload_status",
            "battery_life",
            "m_delivered_timestamp",
            "m_delivered_date",
            "user_id",
    };

    public static String[] Received = {
            "client",
            "job_id",
            "tracking_number",
            "user_id",
            "fullname",
            "code",
            "type",
            "quantity",
            "timestamp",
            "delivery_date",
            "status",
            "latitude",
            "longitude"
    };

    public static String[] StockInColumn = {
            "client",
            "stockInId",
            "stockInTitle",
            "itemTypeId",
            "itemDescription",
            "itemQuantity",
            "stockInDate",
            "userId",
            "sysEntryDate",
            "modifiedBy",
    };

    public static String[] SurveyColumns = {
            "[job_id]" ,
            "[client]" ,
            "[customer_meterno]",
            "[customer_name]" ,
            "[customer_address]" ,
            "[remarks]" ,
            "[picture]" ,
            "[latitude]" ,
            "[longitude]" ,
            "[timestamp]" ,
            "[Reader_ID]" ,
            "[modifiedby]" ,
            "[route]",
            "[itinerary]",
            "[reading_date]",
            "[reading]",
            "[contactnumber]",
            "[emailaddress]",
            "[meterbrand]",
            "[metertype]",
            "[structure]"
    };


    public static Bitmap scaleDown(Bitmap realImage,float maxImageSize,boolean filter){
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());

        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage,width,height,filter);
        return newBitmap;
    }
    public static JSONArray StringToJson(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject entries = jsonObj.optJSONObject("payload");
            String encodedData = entries.getString("entries");
            String decodedData = doDecode64(encodedData);
            JSONArray decodejsonObj = new JSONArray(decodedData);
            return decodejsonObj;
        } catch (JSONException e) {
            e.printStackTrace();
            JSONArray decodejsonObj = new JSONArray();
            return decodejsonObj;
        }
    }

    public static JSONObject NotEncryptedStringToJsonObject(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            return jsonObj;
        } catch (JSONException e) {
            e.printStackTrace();
            JSONObject jsonObj = new JSONObject();
            return jsonObj;
        }
    }

    public static JSONObject StringToJsonObject(String jsonStr){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject entries = jsonObj.optJSONObject("payload");
            String encodedData = entries.getString("entries");
            String decodedData = doDecode64(encodedData);
            JSONObject decodejsonObj = new JSONObject(decodedData);
            return decodejsonObj;
        } catch (JSONException e) {
            e.printStackTrace();
            JSONObject decodejsonObj = new JSONObject();
            return decodejsonObj;
        }
    }

    public static JSONArray FilterJSONObject(JSONObject jsonStr){
        try {

            JSONObject entries = jsonStr.optJSONObject("payload");
            String encodedData = entries.getString("entries");
            String decodedData = doDecode64(encodedData);
            JSONArray decodejsonObj = new JSONArray(decodedData);
            return decodejsonObj;
        } catch (JSONException e) {
            e.printStackTrace();
            JSONArray decodejsonObj = new JSONArray();
            return decodejsonObj;
        }
    }

    public static boolean deleteRecursive(File fileOrDirectory) {
        try{
            if (fileOrDirectory.isDirectory())
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child);

            fileOrDirectory.delete();
            return true;
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return false;
        }

    }



    public static String doDecode64(String encodeValue) {
        byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
        return new String(decodeValue);
    }

    public static File getDiscSignature(String AccountNumber){
        //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //String file = Environment.getExternalStorageDirectory().toString();
        String file = "sdcard/UMS";
        return new File(file + "/UMS_"+Liquid.Client+"_Signature/"+AccountNumber);
        //return new File(file,"/DCIM");
    }

    public static File getDiscByJOSignature(String JO){
        //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //String file = Environment.getExternalStorageDirectory().toString();
        String file = "sdcard/UMS";
        return new File(file + "/UMS_"+Liquid.Client+"_Signature/"+JO+"/");
        //return new File(file,"/DCIM");
    }

    public static void showDialogError(final Context context,String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setNegativeButton("Close",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public static void showDialogInfo(final Context context,String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setNegativeButton("Close",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }
    public static void showDialogNext(final Activity activity,String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton("Close",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

                dialog.cancel();
            }
        });

        builder.setNegativeButton("Done",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.finish();
                dialog.cancel();
            }
        });
        builder.show();
    }

    public static void showReadingDialogNext(final Activity activity,String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton("Close",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Done",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();


                Intent i = new Intent(activity, JobOrderActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(i);
                activity.finish();

            }
        });
        builder.show();
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static File getDiscPicture(String AccountNumber,String[] SubFolder){
        //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //String file = Environment.getExternalStorageDirectory().toString();
        String file = "sdcard/UMS/UMS_"+Liquid.Client+"_Picture/"+AccountNumber;

        for(int a = 0; a < SubFolder.length; a++){
            file += "/"+SubFolder[a];
        }
        Log.i(TAG,file);
        return new File(file);
        //return new File(file,"/DCIM");
    }

    public static File getDiscDefaultPicture(String[] SubFolder){
        //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //String file = Environment.getExternalStorageDirectory().toString();
        String file = "sdcard/UMS/UMS_"+Liquid.Client+"_Picture/";
        for(int a = 0; a < SubFolder.length; a++){
            file += "/"+SubFolder[a];
        }

        return new File(file);
        //return new File(file,"/DCIM");
    }
    public static void resizeImage(String mFile,double heightPercent,double widthPercent) {
        //resizes image file to smaller size so it is more efficient for uploading.
        //resize varies to image capture orientation. default is portrait ( height > width )
        Bitmap mBitmap = BitmapFactory.decodeFile(mFile);
//        double height = mBitmap.getHeight()  * heightPercent;
//        double width = mBitmap.getWidth()  * widthPercent;
        //HD
        double height = 1280;
        double width = 960;
        //Normal Resolution
//        double height = 800;
//        double width = 480;

//        if(Liquid.imageOrientation.matches("Landscape")){
//            height = 480;
//            width = 800;
//        }
        if(Liquid.imageOrientation.matches("Landscape")){
            height = 960;
            width = 1280;
        }

        Bitmap bMapScaled = Bitmap.createScaledBitmap(mBitmap,  (int)width, (int)height, true);
        bMapScaled = setImageWatermark(bMapScaled,Client.replace("_"," ").toUpperCase()+" - "+currentDateTime());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bMapScaled.compress(Bitmap.CompressFormat.JPEG, 70, baos);

        try {
                File f = new File(mFile);
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(baos.toByteArray());
                fo.close();
                //File file =  new File(mFile);
                //file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap setImageWatermark(Bitmap src, String watermark) {
        int text_size = 25;
//        int margin = 15;
        int w = src.getWidth();
        int h = src.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        Paint paint = new Paint();
        Paint.FontMetrics fm = new Paint.FontMetrics();
        paint.setColor(Color.WHITE);
        paint.getFontMetrics(fm);
        paint.setTextSize(30);
        int margin = 15;
        canvas.drawRect(margin,
                src.getHeight() - text_size - text_size - margin - 5,
                paint.measureText(watermark) + (margin*3),
                src.getHeight() - text_size - margin + 5,
                paint);

        paint.setColor(Color.RED);
//        paint.setAlpha(100);

        canvas.drawText(watermark, margin+margin, src.getHeight() - text_size - margin, paint);
        return result;
    }

//    public static Bitmap setImageWatermark(Bitmap src, String watermark) {
//        int text_size = 25;
//        int margin = 15;
//        int w = src.getWidth();
//        int h = src.getHeight();
//        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
//
//        Canvas canvas = new Canvas(result);
//        canvas.drawBitmap(src, 0, 0, null);
//
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setTextSize(text_size);
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        paint.setAntiAlias(true);
//        canvas.drawText(watermark, margin, src.getHeight() - text_size - margin, paint);
//
//        return result;
//    }

    public static String imageToString(String mFile){
        Bitmap mBitmap = BitmapFactory.decodeFile(mFile);
        //Bitmap bMapScaled = Bitmap.createScaledBitmap(mBitmap, 720, 1280, true);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), mBitmap.getHeight(), true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bMapScaled.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }
    public static String[] Sovi = {
            "customer_id",
            "productname",
            "description",
            "sovi_type",
            "location",
            "numkof",
            "numnonkof",
            "cans",
            "sspet",
            "mspet",
            "ssdoy",
            "ssbrick",
            "msbrick",
            "sswedge",
            "box",
            "litro",
            "pounch",
            "picture",
            "comment",
            "period"
    };

    public static String[] Activation = {
            "customer_id",
            "name",
            "available",
            "nummaterial",
            "categoriesUsedFor",
            "picture",
            "period",
    };
    public static String[] CoolerPlanogram = {
            "customer_id",
            "name",
            "value",
            "picture",
            "period",
    };

    public static String[] ShelfPlanogram = {
            "customer_id",
            "name",
            "value",
            "picture",
            "period",
    };
    public static String[] SoviLocation = {
            "customer_id",
            "location",
            "picture",
            "period",
    };


    public static String[] CDE = {
            "customer_id",
            "name",
            "category",
            "question",
            "questionvalue",
            "count",
            "picture",
            "comment",
            "period",
    };
    public static HashMap<String, Integer> LiquidImages = new HashMap<>();

    public static int IntValidation(String data){
        int result = 0;
        data = data.equals("") ? "0" : data;
        result = Integer.parseInt(data);

        return result;
    }
    public static void ShowMessage(Context context, String Message){
        Toast.makeText(context,Message,Toast.LENGTH_LONG).show();
    }
    public static String[] LiquidValues = {};
    public static String[] LiquidColumns = {};


    public static String User = "1";


    public static long diffDate(Date dateFrom, Date dateTo){

        long diff = dateFrom.getTime() - dateTo.getTime();
        return TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
    }
    public static Date ConvertStringToDate(String date){
        String dtStart = date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date mDate = null;
        try {
            mDate = format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }
    public static String currentDateTime(){
        String current = "";
        CharSequence currentDateTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date());
        current = currentDateTime.toString();
        return current;
    }
    public static String currentDate(){
        String current = "";
        CharSequence currentDateTime = android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date());
        current = currentDateTime.toString();
        return current;
    }

    public static String currentMonthYear(){
        String current = "";
        CharSequence currentDateTime = android.text.format.DateFormat.format("MMyyyy", new java.util.Date());
        current = currentDateTime.toString();
        return current;
    }

    public static String currentDateMonthYear(){
        String current = "";
        CharSequence currentDateTime = android.text.format.DateFormat.format("ddmmyyyy", new java.util.Date());
        current = currentDateTime.toString();
        return current;
    }


    public static String setUpCurrentDate(String format){
        String current = "";
        CharSequence currentDateTime = android.text.format.DateFormat.format(format, new java.util.Date());
        current = currentDateTime.toString();
        return current;
    }

    public static String currentDateTimeForID(){
        String current = "";
        CharSequence currentDateTime = android.text.format.DateFormat.format("yyyyMMdd", new java.util.Date());
        current = currentDateTime.toString();
        return current;
    }

    public static String NumberFormat(String Data){
        double answer = Double.parseDouble(Data);
        String final_response = "0.00";
        if(answer >= 1000){
            DecimalFormat df = new DecimalFormat("#,###,###.00");
             final_response = df.format(answer);
        }
        else{
            final_response = String.format("%.2f",answer );
        }
        return final_response;
    }

    public static String FixDecimal(String Data){
        double answer = Double.parseDouble(Data);
        DecimalFormat df = new DecimalFormat("###.#");
        String final_response = df.format(answer);
        return final_response;
    }

    public static String milliSecondtToDate(String millisecond){
        Date currentDate = new Date(Long.parseLong(millisecond));
        DateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        return String.valueOf(df.format(currentDate));
    }

    public static void displayPromptForEnablingGPS(final Activity activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Please open the GPS, Do you want open GPS setting?";
        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    public static boolean CheckGPS(Context mContext) {


        LocationManager lm = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;


        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return  gps_enabled;
        //if(!gps_enabled && !network_enabled) {\
        /*if (!gps_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("TEST");
            dialog.setPositiveButton("TEST", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    ReadingActivity.this.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }*/
    }

    @SuppressLint("MissingPermission")
    public static boolean CheckBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                mBluetoothAdapter.enable();
                return true;
            }
        }
        return false;
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static class ApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/wms/php/";
        public  String Route = "work-route.php";
        public  String Call_Class = "workController";
        public  String Call_Method = "get_job_list" ;
        public  String Parameters = "client=" + Client;
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";

        public ApiData(String Route,String Call_Class,String Call_Method,String Parameters) {

            this.Route = Route;
            this.Call_Class = Call_Class;
            this.Call_Method = Call_Method;
            this.Parameters = Parameters;
            this.API_Link = (this.Url +
                            this.Route +
                            "?class=" + this.Call_Class +
                            "&method="+ this.Call_Method +
                            "&"+ this.Parameters +
                            "&"+ this.Username +
                            "&"+ this.Password).replaceAll(" ", "%20");;

        }
    }

    public static class CokeApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/coke/php/";
        public  String Route = "work-route.php";
        public  String Call_Class = "workController";
        public  String Call_Method = "get_job_list" ;
        public  String Parameters = "client=" + Client;
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";

        public CokeApiData(String Route,String Call_Class,String Call_Method,String Parameters) {

            this.Route = Route;
            this.Call_Class = Call_Class;
            this.Call_Method = Call_Method;
            this.Parameters = Parameters;
            this.API_Link = this.Url +
                    this.Route +
                    "?class=" + this.Call_Class +
                    "&method="+ this.Call_Method +
                    "&"+ this.Parameters +
                    "&"+ this.Username +
                    "&"+ this.Password;

        }
    }

    public static class ApiDataField {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/fmts/php/";
        public  String Route = "work-route.php";
        public  String Call_Class = "workController";
        public  String Call_Method = "get_job_list" ;
        public  String Parameters = "client=coke";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";

        public ApiDataField(String Route,String Call_Class,String Call_Method,String Parameters) {

            this.Route = Route;
            this.Call_Class = Call_Class;
            this.Call_Method = Call_Method;
            this.Parameters = Parameters;
            this.API_Link = this.Url +
                    this.Route +
                    "?class=" + this.Call_Class +
                    "&method="+ this.Call_Method +
                    "&"+ this.Parameters +
                    "&"+ this.Username +
                    "&"+ this.Password;

        }
    }


    public static class GETUMSAPI {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/";
        public  String API = "";
        public  String Username = "tgbleyesa";
        public  String Password = "C0mpl3x17y";
        public  String API_Link = "";
        public  String Parameters = "";
        public GETUMSAPI(String API,String Parameters) {
            this.API = API;
            this.API_Link = (this.Url +
                    this.API +
                    "?username=" + Username +
                    "&password=" + Password +
                    Parameters).replaceAll(" ", "%20");
        }
    }

    public static class POSTUMSAPI {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/";
        //public  String Url = "http://192.168.1.15/USI_BETA/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public POSTUMSAPI(String API) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API;
        }
    }


    public static class GETUMSApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/tgbl/php/api/";
        public  String API = "";
        public  String Username = "tgbleyesa";
        public  String Password = "C0mpl3x17y";
        public  String API_Link = "";
        public  String Parameters = "";
        public GETUMSApiData(String API,String Parameters) {
            this.API = API;
            this.API_Link = this.Url +
                            this.API +
                            "?Username=" + Username +
                            "&Password=" + Password +
                            Parameters;
        }
    }

    public static class POSTAuditApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/ams/php/api/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public POSTAuditApiData(String API) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API;
        }
    }

    public static class GETBMSApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/bms/php/api/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public GETBMSApiData(String API,String Parameters) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API +"?"+Username+"&"+Password+"&"+Parameters;
        }
    }

    public static class POSTBMSApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/bms/php/api/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public POSTBMSApiData(String API) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API;
        }
    }



    public static class GETApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public GETApiData(String API,String Parameters) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API +"?"+Username+"&"+Password+"&"+Parameters;
        }
    }

    public static class POSTApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public POSTApiData(String API) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API;
        }
    }



    public static class CokePOSTUMSApiData {
        public  String Url = "http://"+umsUrl+"/"+pathEnvironment+"/coke/php/api/";
        public  String API = "";
        public  String Username = "username=tgbleyesa";
        public  String Password = "password=C0mpl3x17y";
        public  String API_Link = "";
        public CokePOSTUMSApiData(String API) {
            this.API = API;
            this.API_Link = this.Url +
                    this.API;
        }
    }




    public static String getCokePeriod(String JobId){
        return JobId.substring(0,6);
    }
    public static String getCurrentAddress(View view, Double Latitude, Double Longtitude){
        Geocoder mGeocoder;
        List<Address> mLocation;
        mGeocoder = new Geocoder(view.getContext() , Locale.getDefault());
        try{
            mLocation = mGeocoder.getFromLocation(Latitude,Longtitude,1);
            String address  = mLocation.get(0).getAddressLine(0);
            String area = mLocation.get(0).getLocality();
            String city = mLocation.get(0).getAdminArea();
            String country = mLocation.get(0).getCountryName();
            String postalcode = mLocation.get(0).getPostalCode();
            String fullAddress = address+", "+area+", "+city+", "+country+", "+postalcode;
            return fullAddress;

        }catch(Exception e){
            Log.e(TAG,"Error :",e);
            return "Can't get Address";
        }
    }




    public static Bitmap UriToBitmap(Uri uri){
        try{

            Bitmap bmp = BitmapFactory.decodeFile(uri.getPath());
            double height = Liquid.screenHeight  * 1;
            double width = Liquid.screenWidth  * 1;
            //Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp,  (int)height, (int)height, true);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp,  (int)height, (int)height, true);

            return bMapScaled;

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
            return null;
        }

    }
    public static String FormatKWH(String kwh)
    {
        /*int dot = kwh.indexOf('.');
        if (dot < 0)
            return kwh;
        while (kwh.endsWith("0"))
            kwh = kwh.substring(kwh.length() + 1, 1);
        if (kwh.endsWith("."))
            kwh = kwh.substring(kwh.length() + 1, 1);
        return kwh.replace(kwh,"");*/

        //last formatted
        int dot = kwh.indexOf('.');
        if (dot < 0)
            return kwh;
        //modified: 010519: include decimal in soa format for kwh
//        kwh = String.valueOf((int) Double.parseDouble(kwh));
//        kwh = String.valueOf(Liquid.RoundUp(Double.parseDouble(kwh)));

        return kwh;
    }

    public static String repeatChar(char data,int count){
        String original = "";
        char c = data;
        int number = count;

        char[] repeat = new char[number];
        Arrays.fill(repeat, c);
        original += new String(repeat);
        return original;
    }
    public static String dateChangeFormat(String data,String currentformat ,String newformat){
        try {
            SimpleDateFormat fromUser = new SimpleDateFormat(currentformat);
            SimpleDateFormat myFormat = new SimpleDateFormat(newformat);
            String reformattedStr = myFormat.format(fromUser.parse(data));
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getDueDate(String mDate){
        try {
            SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            date = dateFormat.parse(mDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 9);
            Date dueDate = c.getTime();
        return dateFormat.format(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static int StringLength(String str){
        return str.split("\\$", -1).length - 1;
    }

    public static String getDisconDate(String mDate){
        try {
            SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            date = dateFormat.parse(mDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 2);
            Date DisconDate = c.getTime();
            return dateFormat.format(DisconDate);
        }catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CLEAR_APP_CACHE,
            Manifest.permission.ACCESS_FINE_LOCATION,

    };
    public static String StringRoundUp2D(double num){

        DecimalFormat df = new DecimalFormat("###.####");
        return String.format("%.2f",num );
    }
    public static String StringRoundDown2D(double num){

        DecimalFormat df = new DecimalFormat("###.####");
        return String.format("%.2f",num );
    }
    public static String StringRoundDown4D(double num){

        DecimalFormat df = new DecimalFormat("###.####");
        return String.format("%.4f",num );
    }
    public static double RoundUp(double num){
       // DecimalFormat df = new DecimalFormat("#.##");
        //return Double.parseDouble(df.format(num));
        //-----------
        //BigDecimal bd = new BigDecimal(num);
        //bd= bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        //return bd.doubleValue();
        //double roundOff = Math.round(num * 100) / 100;
        //return roundOff;

        //modified: 01/02/19 Rounding Mode
        return new BigDecimal(String.valueOf(num)).setScale(2,RoundingMode.HALF_UP).doubleValue();
    }
    public static double RoundDown(double num){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(num));
        //BigDecimal bd = new BigDecimal(num);
        //bd= bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        //return bd.doubleValue();
        //double roundOff = Math.round(num * 100) / 100;
        //return roundOff;
    }
    public static float RoundDown(float num){

        float roundOff = (float) (Math.round(num * 100));
        roundOff = roundOff / 100;
        return roundOff;
    }
    public static double round(double number){
        BigDecimal bd = new BigDecimal(number);
        bd= bd.setScale(2,BigDecimal.ROUND_UP);
        return bd.doubleValue();
    }

    public static double newround2(double value) {


        return new BigDecimal(String.valueOf(value)).setScale(2,RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_camera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int permission_location = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }
        if (permission_camera != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }
        if (permission_location != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }

        return true;
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard(EditText[] mEditText) {
        for(int a = 0; a < mEditText.length; a++){
            mEditText[a].setInputType(InputType.TYPE_NULL);
            mEditText[a].setCursorVisible(true);
        }
        /*if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }*/
    }




    public void hideSoftKeyboard(Activity activity) {
        //InputMethodManager imm = ((InputMethodManager) getSystemService(activity.INPUT_METHOD_SERVICE));
        //imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public static String removeFirstChar(String s){
        return s.substring(1);
    }

    public static void GetSettings() {

        try {

            Cursor result = SettingModel.GetSettings();

            if (result.getCount() == 0) {
                return;
            }

            while (result.moveToNext()) {
                //Customer Data
                HashMap<String, String> data = new HashMap<>();
                Liquid.ReverseInput = Integer.parseInt(result.getString(0));
                Liquid.HideKeyboard  = Integer.parseInt(result.getString(1));
            }

        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }

    }


    public static void getDeviceLocation(Activity activity){
        try{
            Log.d(TAG, "getDeviceLocation: getting the devices current location");
            FusedLocationProviderClient mFusedLocationProviderClient;
            Boolean mLocationPermissionsGranted = true;
            mLocationPermissionsGranted = true;
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

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
                                    Liquid.Latitude = currentLocation.getLatitude();
                                    Liquid.Longitude = currentLocation.getLongitude();

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


    public static void GetUserDetails(){
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



}
