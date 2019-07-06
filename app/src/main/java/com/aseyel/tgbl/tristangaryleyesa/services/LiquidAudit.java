package com.aseyel.tgbl.tristangaryleyesa.services;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.LiquidModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LiquidAudit {
    public static final String TAG = LiquidAudit.class.getSimpleName();
    public static JSONObject UploadAudit(){
        try{
            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_picture = new JSONArray();

            final_response = UploadAudit(Liquid.SelectedId,"");
            final_response_picture = UploadPicture("");

            final_data_response.put("data",final_response);
            final_data_response.put("picture",final_response_picture);

            return final_data_response;
        }catch(Exception e){
            Log.e(TAG,"Error: ",e);
            return null;
        }
    }

    public static JSONObject UploadAccountAudit(String AccountNumber){
        try{

            JSONObject final_data_response = new JSONObject();
            JSONArray final_response = new JSONArray();
            JSONArray final_response_picture = new JSONArray();

            final_response = UploadAudit(Liquid.SelectedId,AccountNumber);
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
    public static JSONArray UploadAudit(String JobOrderId,String AccountNumber){
        JSONArray final_response = new JSONArray();
        ArrayList<HashMap<String, String>> joborder_result = new ArrayList<>();
        ArrayList<HashMap<String, String>> download_result = new ArrayList<>();
        joborder_result = LiquidModel.GetLocalJobOrder(JobOrderId);
        for(int a = 0; a < joborder_result.size(); a++){

            download_result = AuditModel.GetAuditDownload(joborder_result.get(a).get("id"),AccountNumber);

            for(int b = 0; b < download_result.size(); b++){

                Cursor result = AuditModel.GetAuditTravelRides(download_result.get(b).get("JobOrderId"),download_result.get(b).get("AccountNumber"),"","");

                if (result.getCount() == 0) {
                    return final_response;
                }
                while(result.moveToNext()){
                    JSONObject data = new JSONObject();
                    try {
                        Log.i(TAG,result.getString(4));
                        data.put("JobOrderId",result.getString(0));
                        data.put("JobTitle",joborder_result.get(a).get("title"));
                        data.put("JobDetails",joborder_result.get(a).get("details"));
                        data.put("AccountTitle",download_result.get(b).get("Title"));
                        data.put("AccountDetails",download_result.get(b).get("Details"));
                        data.put("Client",result.getString(1));
                        data.put("AccountNumber",result.getString(2));
                        data.put("Vehicle",result.getString(3));
                        data.put("Fare",result.getString(4));
                        data.put("Comment",result.getString(5));
                        data.put("Latitude", result.getString(6));
                        data.put("Longitude",result.getString(7));
                        data.put("JobOrderDate",result.getString(8));
                        data.put("TimeStamp",result.getString(9));
                        data.put("sysid",result.getString(10));
                        data.put("Status",result.getString(12));
                        data.put("username",Liquid.Username);
                        data.put("password",Liquid.Password);
                        data.put("deviceserial", Build.SERIAL);
                        final_response.put(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return final_response;
    }
}
