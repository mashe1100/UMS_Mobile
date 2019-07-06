package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

public class MeterNotInListModel {
    private static final String TAG = ReadingModel.class.getSimpleName();
    public static Cursor GetMeterNotInList(String job_id)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                 "select [job_id],"+ //0
                        "[client],"+ //1
                        "[customer_meterno],"+ //2
                        "[customer_name],"+ //3
                        "[customer_address],"+ //4
                        "[remarks],"+ //5
                        "[picture],"+ //6
                        "[latitude],"+ //7
                        "[longitude],"+ //8
                        "[timestamp],"+ //9
                        "[Reader_ID],"+ //10
                        "[modifiedby],"+ //11
                        "[route],"+ //12
                        "[itinerary],"+ //13
                        "[reading_date],"+ //14
                        "[nearest_meter],"+ //15
                        "[nearest_seq],"+ //16
                        "[reading], " + //17
                          "[id], "+ //18
                         "[rowid] "+ //19
                        "from meter_not_in_list " +
                        "where job_id = '"+job_id+"' " +
                        "order by timestamp  DESC"

        );
    }

    public static Cursor GetMeterNotInListUpload(String job_id,String status)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select [job_id],"+ //0
                        "[client],"+ //1
                        "[customer_meterno],"+ //2
                        "[customer_name],"+ //3
                        "[customer_address],"+ //4
                        "[remarks],"+ //5
                        "[picture],"+ //6
                        "[latitude],"+ //7
                        "[longitude],"+ //8
                        "[timestamp],"+ //9
                        "[Reader_ID],"+ //10
                        "[modifiedby],"+ //11
                        "[route],"+ //12
                        "[itinerary],"+ //13
                        "[reading_date],"+ //14
                        "[nearest_meter],"+ //15
                        "[nearest_seq],"+ //16
                        "[reading], " + //17
                        "[contactnumber], " + //18
                        "[emailaddress], " + //19
                        "[meterbrand], " + //20
                        "[metertype], " + //21
                        "[structure], " + //22
                        "[accountnumber], " + //23
                        "[serial], " + //24
                        "[id], "+ //18 //23 //25
                        "[demand], "+ //19 //24 //26
                        "[ampirCapacity], "+ //19 //24 //26 //27
                        "[type], "+ //19 //24 //26 //28
                        "[house_latitude], "+ //19 //24 //26 //29
                        "[house_longitude] "+ //19 //24 //26 //30
                        "from meter_not_in_list " +
                        "where job_id = '"+job_id+"' AND transfer_status = '"+status+"'" +
                        "order by timestamp  DESC"

        );
    }

    public static ArrayList<HashMap<String, String>> GetMeterNotInListData(String job_id){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = GetMeterNotInList(job_id);
        try
        {
            if(result.getCount() == 0){
                return null;
            }
            while(result.moveToNext()){
                String Date = result.getString(9);
                String Details = "Meter No: "+ result.getString(2) +"\n"+
                                "Name: "+ result.getString(3) +"\n"+
                                "Address: "+ result.getString(4) +"\n"+
                                "Date: "+ Date +"";
                HashMap<String, String> data = new HashMap<>();
                data.put("Id", result.getString(18));
                data.put("Title", result.getString(2));
                data.put("Details", Details);
                data.put("Date", Date);
                data.put("Filepath", "");
                final_result.add(data);
            }
            return final_result;
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return null;
        }

    }
    public static Cursor GetMeterNotInDetails(String id,String MeterNumber)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                     "select " +
                            "[job_id],"+ //0
                             "[client],"+ //1
                            "[customer_meterno],"+ //2
                            "[customer_name],"+ //3
                            "[customer_address],"+ //4
                            "[remarks],"+ //5
                            "[picture],"+ //6
                            "[latitude],"+ //7
                            "[longitude],"+ //8
                            "[timestamp],"+ //9
                            "[Reader_ID],"+ //10
                            "[modifiedby],"+ //11
                            "[route],"+ //12
                            "[itinerary],"+ //13
                            "[reading_date],"+ //14
                            "[nearest_meter],"+ //15
                            "[nearest_seq],"+ //16
                            "[reading], " + //17
                            "[id], "+ //18
                             "[demand],"+ //19
                             "[type], " + //20
                             "[ampirCapacity], "+ //21
                             "[meterbrand], "+//22
                             "[emailaddress], "+//23
                             "[contactnumber] "+//24
                        "from meter_not_in_list " +
                        "where [job_id] = '"+id+"' AND [customer_meterno] = '"+MeterNumber+"' " +
                        "order by timestamp  DESC"

        );
    }

    public static boolean DeleteAudit(String id){
        try{
            boolean result = false;
            String query = "DELETE FROM meter_not_in_list  WHERE id = '" + id + "'";
            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean Update(
                                String id,
                                String job_id,
                                 String client,
                                 String customer_meterno,
                                 String customer_name,
                                 String customer_address,
                                 String remarks,
                                String demand,
                                String type,
                                String ampirCapacity,
                                 String  picture,
                                 String latitude,
                                 String longitude,
                                String house_latitude,
                                String house_longitude,
                                 String timestamp,
                                 String Reader_ID,
                                 String modifiedby,
                                 String route,
                                 String itinerary,
                                 String reading_date,
                                 String nearest_meter,
                                 String nearest_seq,
                                 String reading,
                                String contact,
                                String email,
                                String meterbrand,
                                String metertype,
                                String structure,
                                String accountnumber,
                                String serial



    ){
        try{

            boolean result = false;
            Liquid.LiquidColumns = Liquid.MeterNotInListColumnsUpdate;
            Liquid.LiquidValues = new String[] {
                    id,
                    job_id,
                    client,
                    customer_meterno,
                    customer_name,
                    customer_address,
                    remarks,
                     demand,
                     type,
                     ampirCapacity,
                    picture,
                    latitude,
                    longitude,
                     house_latitude,
                     house_longitude,
                    timestamp,
                    Reader_ID,
                    modifiedby,
                    route,
                    itinerary,
                    reading_date,
                    nearest_meter,
                    nearest_seq,
                    reading,
                    reading,
                    contact,
                    email,
                    meterbrand,
                    metertype,
                    structure,
                    accountnumber,
                    serial

            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("meter_not_in_list",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
    public static boolean Save(  String job_id,
                                 String client,
                                 String customer_meterno,
                                 String customer_name,
                                 String customer_address,
                                 String remarks,
                                 String demand,
                                 String type,
                                 String ampirCapacity,
                                 String  picture,
                                 String latitude,
                                 String longitude,
                                 String house_latitude,
                                 String house_longitude,
                                 String timestamp,
                                 String Reader_ID,
                                 String modifiedby,
                                 String route,
                                 String itinerary,
                                 String reading_date,
                                 String nearest_meter,
                                 String nearest_seq,
                                 String reading,
                                 String contact,
                                 String email,
                                 String meterbrand,
                                 String metertype,
                                 String structure,
                                 String accountnumber,
                                 String serial


    ){
        try{

            boolean result = false;
            Liquid.LiquidColumns = Liquid.MeterNotInListColumns;
            Liquid.LiquidValues = new String[] {
                    job_id,
                    client,
                    customer_meterno,
                    customer_name,
                    customer_address,
                    remarks,
                    demand,
                    type,
                    ampirCapacity,
                    picture,
                    latitude,
                    longitude,
                    house_latitude,
                    house_longitude,
                    timestamp,
                    Reader_ID,
                    modifiedby,
                    route,
                    itinerary,
                    reading_date,
                    nearest_meter,
                    nearest_seq,
                    reading,
                    contact,
                    email,
                    meterbrand,
                    metertype,
                    structure,
                    accountnumber,
                    serial

            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("meter_not_in_list",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
}
