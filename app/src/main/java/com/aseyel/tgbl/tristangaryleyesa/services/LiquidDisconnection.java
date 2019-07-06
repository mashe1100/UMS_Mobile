package com.aseyel.tgbl.tristangaryleyesa.services;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.DisconnectionModel;
import com.aseyel.tgbl.tristangaryleyesa.model.LiquidModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LiquidDisconnection {
    public static final String TAG = LiquidDisconnection.class.getSimpleName();
    public static JSONObject UploadDisconnection(){
        try{
            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_picture = new JSONArray();

            final_response = UploadDisconnection(Liquid.SelectedId,"");
            final_response_picture = UploadPicture("");
            Log.i(TAG, String.valueOf(final_response));
            final_data_response.put("data",final_response);
            final_data_response.put("picture",final_response_picture);

            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }

    public static JSONObject UploadAccountDisconnection(String AccountNumber){
        try{

            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_picture = new JSONArray();

            final_response = UploadDisconnection(Liquid.SelectedId,AccountNumber);
            final_response_picture = UploadPicture(AccountNumber);

            final_data_response.put("data",final_response);
            final_data_response.put("picture",final_response_picture);

            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }

    public static JSONArray UploadPicture(String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = AuditModel.GetPicture(AccountNumber);

        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("client",result.getString(0));
                data.put("AccountNumber",result.getString(1));
                data.put("type",result.getString(2));
                data.put("picture",result.getString(3));
                data.put("timestamp",result.getString(4));
                data.put("modifieddate",result.getString(5));
                data.put("Reader_ID",result.getString(6));
                data.put("modifiedby", result.getString(7));
                data.put("reading_date",result.getString(8));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",result.getString(7));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return final_response;
    }
    public static JSONArray UploadDisconnection(String JobOrderId,String AccountNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = DisconnectionModel.GetDisconnected(JobOrderId,AccountNumber);
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {
                data.put("job_id",result.getString(0));
                data.put("client",result.getString(1));
                data.put("id",result.getString(2));
                data.put("accountnumber",result.getString(3));
                data.put("name",result.getString(4));
                data.put("BA",result.getString(5));
                data.put("route",result.getString(6));
                data.put("itinerary",result.getString(7));
                data.put("tin",result.getString(8));
                data.put("meter_number",result.getString(9));
                data.put("meter_count",result.getString(10));
                data.put("serial",result.getString(11));
                data.put("previous_reading",result.getString(12));
                data.put("last_Reading",result.getString(13));
                data.put("previous_consumption",result.getString(14));
                data.put("last_consumption",result.getString(15));
                data.put("previous_reading_date",result.getString(16));
                data.put("disconnection_date",result.getString(17));
                data.put("month",result.getString(18));
                data.put("day",result.getString(19));
                data.put("year",result.getString(20));
                data.put("demand",result.getString(21));
                data.put("reactive",result.getString(22));
                data.put("powerfactor",result.getString(23));
                data.put("reading1",result.getString(24));
                data.put("reading2",result.getString(25));
                data.put("reading3",result.getString(26));
                data.put("reading4",result.getString(27));
                data.put("reading5",result.getString(28));
                data.put("reading6",result.getString(29));
                data.put("reading7",result.getString(30));
                data.put("reading8",result.getString(31));
                data.put("reading9",result.getString(32));
                data.put("reading10",result.getString(33));
                data.put("iwpowerfactor",result.getString(34));
                data.put("multiplier",result.getString(35));
                data.put("meter_box",result.getString(36));
                data.put("demand_reset",result.getString(37));
                data.put("remarks_code",result.getString(38));
                data.put("remarks_description",result.getString(39));
                data.put("remarks_reason",result.getString(40));
                data.put("comment",result.getString(41));
                data.put("meter_type",result.getString(42));
                data.put("meter_brand",result.getString(43));
                data.put("reader_id",result.getString(44));
                data.put("meter_reader",result.getString(45));
                data.put("disconnection_attempt",result.getString(46));
                data.put("r_latitude",result.getString(47));
                data.put("r_longitude",result.getString(48));
                data.put("transfer_data_status",result.getString(49));
                data.put("upload_status",result.getString(50));
                data.put("status",result.getString(51));
                data.put("code",result.getString(52));
                data.put("area",result.getString(53));
                data.put("region",result.getString(54));
                data.put("country",result.getString(55));
                data.put("province",result.getString(56));
                data.put("city",result.getString(57));
                data.put("brgy",result.getString(58));
                data.put("country_label",result.getString(59));
                data.put("province_label",result.getString(60));
                data.put("region_label",result.getString(61));
                data.put("municipality_city_label",result.getString(62));
                data.put("loc_barangay_label",result.getString(63));
                data.put("street",result.getString(64));
                data.put("complete_address",result.getString(65));
                data.put("rate_code",result.getString(66));
                data.put("sequence",result.getString(67));
                data.put("disconnection_timestamp",result.getString(68));
                data.put("timestamp",result.getString(69));
                data.put("isdisconnected",result.getString(70));
                data.put("ispayed",result.getString(71));
                data.put("disconnection_signature",result.getString(72));
                data.put("recvby",result.getString(73));
                data.put("amountdue",result.getString(74));
                data.put("duedate",result.getString(75));
                data.put("cyclemonth",result.getString(76));
                data.put("cycleyear",result.getString(77));
                data.put("or_number",result.getString(78));
                data.put("arrears",result.getString(79));
                data.put("total_amount_due",result.getString(80));
                data.put("last_payment_date",result.getString(81));
                data.put("modifiedby",result.getString(84));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",result.getString(84));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }
}
