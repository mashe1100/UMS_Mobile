package com.aseyel.tgbl.tristangaryleyesa.services;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LiquidDelivery {
    public static final String TAG = LiquidDelivery.class.getSimpleName();
    public static JSONObject UploadDelivery(String JobID){
        try{
            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray all_data = new JSONArray();


            final_response = UploadDelivery(JobID,"");
            all_data = UploadAllDelivery(JobID,"");


            final_data_response.put("data",final_response);
            final_data_response.put("all_data",all_data);

            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }

    public static JSONArray UploadDelivery(String JobOrderId,String TrackingNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = DeliveryModel.GetMessengerialForUpload(JobOrderId, TrackingNumber,"Pending");
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {

                data.put("client",result.getString(0));
                data.put("job_id",result.getString(1));
                data.put("ngc_job_id","1");
                data.put("job_title",result.getString(2));
                data.put("trackingNumber",result.getString(3));
                data.put("itemType",result.getString(4));
                data.put("itemTypeDescription",result.getString(5));
                data.put("status",result.getString(6));
                data.put("remarksCode",result.getString(7));
                data.put("remarks",result.getString(8));
                data.put("comment",result.getString(9));
                data.put("latitude",result.getString(10));
                data.put("longitude",result.getString(11));
                data.put("signature",result.getString(13));
                data.put("date",result.getString(17));
                data.put("timeStamp",result.getString(16));
                data.put("userID",result.getString(18));
                data.put("transferDataStatus",result.getString(12));
                data.put("batteryLife",result.getString(17));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(data));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }

    public static JSONArray UploadAllDelivery(String JobOrderId,String TrackingNumber){
        JSONArray final_response = new JSONArray();
        Cursor result = DeliveryModel.GetAllMessengerialForUpload(JobOrderId, TrackingNumber);
        if (result.getCount() == 0) {
            return final_response;
        }

        while(result.moveToNext()){
            JSONObject data = new JSONObject();
            try {

                data.put("client",result.getString(0));
                data.put("job_id",result.getString(1));
                data.put("ngc_job_id","1");
                data.put("job_title",result.getString(2));
                data.put("trackingNumber",result.getString(3));
                data.put("itemType",result.getString(4));
                data.put("itemTypeDescription",result.getString(5));
                data.put("status",result.getString(6));
                data.put("remarksCode",result.getString(7));
                data.put("remarks",result.getString(8));
                data.put("comment",result.getString(9));
                data.put("latitude",result.getString(10));
                data.put("longitude",result.getString(11));
                data.put("signature",result.getString(13));
                data.put("date",result.getString(17));
                data.put("timeStamp",result.getString(16));
                data.put("userID",result.getString(18));
                data.put("transferDataStatus",result.getString(12));
                data.put("batteryLife",result.getString(17));
                data.put("username",Liquid.Username);
                data.put("password",Liquid.Password);
                data.put("deviceserial", Build.SERIAL);
                data.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(data));
                final_response.put(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return final_response;
    }



}
