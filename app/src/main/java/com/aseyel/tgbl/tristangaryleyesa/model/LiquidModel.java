package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LiquidModel {

    private static final String TAG = LiquidModel.class.getSimpleName();
    public static boolean DoImportData(
            String Table,
            String[] Columns,
            String[] Values
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Columns;
            Liquid.LiquidValues = Values;
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault(Table,Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }


    public static ArrayList<HashMap<String, String>> GetLocalJobOrder(String job_id){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = workModel.GetLocalJobOrder(job_id);
        try
        {
            if(result.getCount() == 0){
                return null;
            }
            while(result.moveToNext()){
                int total_upload = 0;
                int total_download = 0;
                int total_pending = 0;
                Cursor result_jo = ReadingModel.GetDataDownload(result.getString(0));
                Cursor result_job_upload = ReadingModel.GetDataUploaded(result.getString(0),"Uploaded");
                Cursor result_job_Pending = ReadingModel.GetDataUploaded(result.getString(0),"Pending");
                if(result_job_Pending.getCount() == 0){

                }
                while(result_job_Pending.moveToNext()){
                    total_pending = Integer.parseInt(result_job_Pending.getString(0));
                }
                if(result_jo.getCount() == 0){

                }
                while(result_jo.moveToNext()){
                    total_download = Integer.parseInt(result_jo.getString(0));
                }
                if(result_job_upload.getCount() == 0){

                }
                while(result_job_upload.moveToNext()){
                    total_upload = Integer.parseInt(result_job_upload.getString(0));
                }
                HashMap<String, String> data = new HashMap<>();
                data.put("id", result.getString(0));
                data.put("title", result.getString(1));
                data.put("details", result.getString(2) + "" +
                        "\nDownload : " +total_download+
                        "\nUploaded : "+total_upload +
                        "\nPending  : " +total_pending
                        );
                data.put("date", result.getString(3));

                final_result.add(data);
            }
            return final_result;
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return null;
        }

    }


}
