package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

public class DisconnectionModel {
    private static final String TAG = DisconnectionModel.class.getSimpleName();

    public static Cursor get_next_customer_sequence(String job_id, String rowid)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select job_id,accountnumber,rowid from customer_disconnection_downloads " +
                        "where job_id = '"+job_id+"' and rowid = ('"+rowid+"' + 1) " +
                        "LIMIT 1"

        );
    }

    public static Cursor get_prev_customer_sequence(String job_id, String rowid)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select job_id,accountnumber,rowid from customer_disconnection_downloads " +
                        "where job_id = '"+job_id+"' and rowid = ('"+rowid+"' - 1) " +
                        "LIMIT 1"

        );
    }
    public static Cursor GetDisconnected(String job_id,String AccountNumber) {
        String where = "";
        if(!AccountNumber.equals("")){
            where = "AND c.accountnumber like '%" + AccountNumber + "%' ";
        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "c.job_id,"+ //1
                        "c.client,"+//2
                        "c.id,"+//3
                        "c.accountnumber,"+//4
                        "c.name,"+//5
                        "c.BA,"+//6
                        "c.route,"+//7
                        "c.itinerary,"+//8
                        "c.tin,"+//9
                        "c.meter_number,"+//10
                        "c.meter_count,"+//11
                        "c.serial,"+//12
                        "c.previous_reading,"+//13
                        "c.last_Reading,"+//14
                        "c.previous_consumption,"+//15
                        "c.last_consumption,"+//16
                        "c.previous_reading_date,"+//17
                        "c.disconnection_date,"+//18
                        "c.month,"+//19
                        "c.day,"+//20
                        "c.year,"+//21
                        "c.demand,"+//22
                        "c.reactive,"+//23
                        "c.powerfactor,"+//24
                        "c.reading1,"+//25
                        "c.reading2,"+//26
                        "c.reading3,"+//27
                        "c.reading4,"+//28
                        "c.reading5,"+//29
                        "c.reading6,"+//30
                        "c.reading7,"+//31
                        "c.reading8,"+//32
                        "c.reading9,"+//33
                        "c.reading10,"+//34
                        "c.iwpowerfactor,"+//35
                        "c.multiplier,"+//36
                        "c.meter_box,"+//37
                        "c.demand_reset,"+//38
                        "c.remarks_code,"+//39
                        "c.remarks_description,"+//40
                        "c.remarks_reason,"+//41
                        "c.comment,"+//42
                        "c.meter_type,"+//43
                        "c.meter_brand,"+//44
                        "c.reader_id,"+//45
                        "c.meter_reader,"+//46
                        "c.disconnection_attempt,"+//47
                        "c.r_latitude,"+//48
                        "c.r_longitude,"+//49
                        "c.transfer_data_status,"+//50
                        "c.upload_status,"+//51
                        "c.status,"+//52
                        "c.code,"+//53
                        "c.area,"+//54
                        "c.region,"+//55
                        "c.country,"+//56
                        "c.province,"+//57
                        "c.city,"+//58
                        "c.brgy,"+//59
                        "c.country_label,"+//60
                        "c.province_label,"+//61
                        "c.region_label,"+//62
                        "c.municipality_city_label,"+//63
                        "c.loc_barangay_label,"+//64
                        "c.street,"+//65
                        "c.complete_address,"+//66
                        "c.rate_code,"+//67
                        "c.sequence,"+//68
                        "c.disconnection_timestamp,"+//69
                        "c.timestamp,"+//70
                        "c.isdisconnected,"+//71
                        "c.ispayed,"+//72
                        "c.disconnection_signature,"+//73
                        "c.recvby,"+//74
                        "c.amountdue,"+//75
                        "c.duedate,"+//76
                        "c.cyclemonth,"+//77
                        "c.cycleyear,"+//78
                        "c.or_number,"+//79
                        "c.arrears,"+//80
                        "c.total_amount_due,"+//81
                        "c.last_payment_date,"+//82
                        "c.sysentrydate,"+//83
                        "c.modifieddate,"+//84
                        "c.modifiedby "+//85
                        "FROM disconnection c " +
                        "WHERE c.job_id like '%" + job_id + "%' " +
                        where +
                        "ORDER BY sysentrydate DESC ");


    }

    public static ArrayList<HashMap<String, String>> GetDisconnectionSearchDownload(String job_id, String AccountNumber){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

        Cursor result = workModel.GetDisconnectionSearchDownload(job_id,AccountNumber);
        try
        {

            if(result.getCount() == 0){
                return null;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String AccountName = result.getString(10);
                String CAccountNumber = result.getString(9);
                String MeterNumber = result.getString(36);
                String DisconenctionDate = result.getString(42);
                String Latitude = result.getString(31);
                String Longitude = result.getString(32);
                String JobOrderDate = result.getString(52);
                String Address = result.getString(29);
                String Remarks = result.getString(48);


                data.put("JobOrderId", result.getString(2));
                data.put("AccountNumber", CAccountNumber);
                data.put("AccountName", AccountName);
                data.put("MeterNumber", MeterNumber);
                data.put("Title", MeterNumber);
                Details = "Account Number : "+CAccountNumber+"\n"+
                        "Name : "+AccountName+"\n"+
                        "Address : "+Address+"\n"+
                        "Remarks : "+Remarks+"\n"+
                        "Discon Date : "+DisconenctionDate;
                data.put("Details", Details);
                data.put("Date", JobOrderDate);
                data.put("Latitude", Latitude);
                data.put("Longitude", Longitude);
                data.put("JobOrderDate",JobOrderDate);
                data.put("Accomplishment","");
                final_result.add(data);
            }


            return final_result;

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return null;
        }
    }

    public static ArrayList<HashMap<String, String>> GetDisconnectionDownload(String job_id, String AccountNumber){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

        Cursor result = workModel.GetDisconnectionDownload(job_id,AccountNumber);
        try
        {
            if(result.getCount() == 0){
                return null;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String AccountName = result.getString(10);
                String CAccountNumber = result.getString(9);
                String MeterNumber = result.getString(36);
                String DisconenctionDate = result.getString(42);
                String Latitude = result.getString(31);
                String Longitude = result.getString(32);
                String JobOrderDate = result.getString(52);
                String Address = result.getString(29);
                String Remarks = result.getString(48);


                data.put("JobOrderId", result.getString(2));
                data.put("AccountNumber", CAccountNumber);
                data.put("AccountName", AccountName);
                data.put("MeterNumber", MeterNumber);
                data.put("Title", MeterNumber);
                Details = "Account Number : "+CAccountNumber+"\n"+
                          "Name : "+AccountName+"\n"+
                          "Address : "+Address+"\n"+
                          "Remarks : "+Remarks+"\n"+
                          "Discon Date : "+DisconenctionDate;
                data.put("Details", Details);
                data.put("Date", JobOrderDate);
                data.put("Latitude", Latitude);
                data.put("Longitude", Longitude);
                data.put("JobOrderDate",JobOrderDate);
                data.put("Accomplishment","");
                final_result.add(data);
            }


            return final_result;

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return null;
        }
    }

    public static boolean doDisconnection(   String job_id,
                                       String client,
                                       String id,
                                       String accountnumber,
                                       String name,
                                       String BA,
                                       String route,
                                       String itinerary,
                                       String tin,
                                       String meter_number,
                                       String meter_count,
                                       String serial,
                                       String previous_reading,
                                       String last_Reading,
                                       String previous_consumption,
                                       String last_consumption,
                                       String previous_reading_date,
                                       String disconnection_date,
                                       String month,
                                       String day,
                                       String year,
                                       String demand,
                                       String reactive,
                                       String powerfactor,
                                       String reading1,
                                       String reading2,
                                       String reading3,
                                       String reading4,
                                       String reading5,
                                       String reading6,
                                       String reading7,
                                       String reading8,
                                       String reading9,
                                       String reading10,
                                       String iwpowerfactor,
                                       String multiplier,
                                       String meter_box,
                                       String demand_reset,
                                       String remarks_code,
                                       String remarks_description,
                                       String remarks_reason,
                                       String comment,
                                       String meter_type,
                                       String meter_brand,
                                       String reader_id,
                                       String meter_reader,
                                       String disconnection_attempt,
                                       String r_latitude,
                                       String r_longitude,
                                       String transfer_data_status,
                                       String upload_status,
                                       String status,
                                       String code,
                                       String area,
                                       String region,
                                       String country,
                                       String province,
                                       String city,
                                       String brgy,
                                       String country_label,
                                       String province_label,
                                       String region_label,
                                       String municipality_city_label,
                                       String loc_barangay_label,
                                       String street,
                                       String complete_address,
                                       String rate_code,
                                       String sequence,
                                       String disconnection_timestamp,
                                       String timestamp,
                                       String isdisconnected,
                                       String ispayed,
                                       String disconnection_signature,
                                       String recvby,
                                       String amountdue,
                                       String duedate,
                                       String cyclemonth,
                                       String cycleyear,
                                       String or_number,
                                       String arrears,
                                       String total_amount_due,
                                       String last_payment_date,
                                       String sysentrydate,
                                       String modifieddate,
                                       String modifiedby
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.DisconnectionTable;
            Liquid.LiquidValues = new String[]{
                    job_id,
                    client,
                    id,
                    accountnumber,
                    name,
                    BA,
                    route,
                    itinerary,
                    tin,
                    meter_number,
                    meter_count,
                    serial,
                    previous_reading,
                    last_Reading,
                    previous_consumption,
                    last_consumption,
                    previous_reading_date,
                    disconnection_date,
                    month,
                    day,
                    year,
                    demand,
                    reactive,
                    powerfactor,
                    reading1,
                    reading2,
                    reading3,
                    reading4,
                    reading5,
                    reading6,
                    reading7,
                    reading8,
                    reading9,
                    reading10,
                    iwpowerfactor,
                    multiplier,
                    meter_box,
                    demand_reset,
                    remarks_code,
                    remarks_description,
                    remarks_reason,
                    comment,
                    meter_type,
                    meter_brand,
                    reader_id,
                    meter_reader,
                    disconnection_attempt,
                    r_latitude,
                    r_longitude,
                    transfer_data_status,
                    upload_status,
                    status,
                    code,
                    area,
                    region,
                    country,
                    province,
                    city,
                    brgy,
                    country_label,
                    province_label,
                    region_label,
                    municipality_city_label,
                    loc_barangay_label,
                    street,
                    complete_address,
                    rate_code,
                    sequence,
                    disconnection_timestamp,
                    timestamp,
                    isdisconnected,
                    ispayed,
                    disconnection_signature,
                    recvby,
                    amountdue,
                    duedate,
                    cyclemonth,
                    cycleyear,
                    or_number,
                    arrears,
                    total_amount_due,
                    last_payment_date,
                    sysentrydate,
                    modifieddate,
                    modifiedby
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("disconnection",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
}
