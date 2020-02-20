package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.MainActivity;
import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Romeo on 2018-02-20.
 */
public class AuditModel {
    private static final String TAG = AuditModel.class.getSimpleName();
    public static boolean doSubmitAuditTravelRide(String JobOrderId,
                                              String Client,
                                              String AccountNumber,
                                              String Vehicle,
                                              String Fare,
                                                  String Comment,
                                                  String Latitude,
                                                  String Longitude,
                                                  String JobOrderDate,
                                                  String Status
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.audit_upload;
            Liquid.LiquidValues = new String[] {
                    JobOrderId,
                    Client,
                    AccountNumber,
                    Vehicle,
                    Fare,
                    Comment,
                    Latitude,
                    Longitude,
                    JobOrderDate,
                    Status
            };
            result = SplashActivity.mDatabaseHelper.SqliteInsertQuery("audit_upload",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static Cursor GetUploadPicture(String status) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "client," +//0
                        "AccountNumber, " +//1
                        "type, " +//2
                        "picture, " +//3
                        "timestamp, " +//4
                        "modifieddate, " +//5
                        "Reader_ID, " +//6
                        "modifiedby, " +//7
                        "reading_date, " +//8
                        "[rowid] "+//9
                        "FROM meter_reading_pictures  "+
                       "WHERE transfer_status = '"+status+"' ");
    }

    public static Cursor GetPicture(String AccountNumber) {
        String where = "";
        if(!AccountNumber.equals("")){
            where = "WHERE AccountNumber like '" + AccountNumber + "' ";
        }
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "client," +//0
                        "AccountNumber, " +//1
                        "type, " +//2
                        "picture, " +//3
                        "timestamp, " +//4
                        "modifieddate, " +//5
                        "Reader_ID, " +//6
                        "modifiedby, " +//7
                        "reading_date, " +//8
                        "[rowid] "+//9
                        "FROM meter_reading_pictures  "+
                         where +
                        "ORDER BY timestamp ASC ");
    }

    public static Cursor GetGroupPicture(String AccountNumber) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "client," +//0
                        "AccountNumber, " +//1
                        "type, " +//2
                        "picture, " +//3
                        "timestamp, " +//4
                        "modifieddate, " +//5
                        "Reader_ID, " +//6
                        "modifiedby, " +//7
                        "reading_date " +//8
                        "FROM meter_reading_pictures "+
                        "WHERE AccountNumber like '%" + AccountNumber + "%' " +
                        "GROUP BY AccountNumber "+
                        "ORDER BY timestamp ASC ");
    }

    public static boolean doSubmitPicture( String client,
                                           String AccountNumber,
                                           String type,
                                           String picture,
                                           String timestamp,
                                           String modifieddate,
                                           String Reader_ID,
                                           String modifiedby,
                                           String reading_date
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.ColumnPicture;
            Liquid.LiquidValues = new String[]{
                    client,
                    AccountNumber,
                    type,
                    picture,
                    timestamp,
                    modifieddate,
                    Reader_ID,
                    modifiedby,
                    reading_date,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("meter_reading_pictures",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitUpdateAuditTravelRide(
                                                  String AuditNoId,
                                                  String Vehicle,
                                                  String Fare,
                                                  String Comment

    ){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "Vehicle",
                    "Fare",
                    "Comment"
            };
            Liquid.LiquidValues = new String[] {
                    Vehicle,
                    Fare,
                    Comment,
            };

            whereClause = "AuditId=?";
            whereArgs = new String[] { AuditNoId };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("audit_upload",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitUpdateAuditStatus(
            String AccountNumber,
            String Status
    ){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "Status",

            };
            Liquid.LiquidValues = new String[] {
                    Status,
            };

            whereClause = "AccountNumber=?";
            whereArgs = new String[] { AccountNumber };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("audit_upload",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static Cursor GetAuditTravelRides(String JobOrderId, String AccountNumber, String AuditId,String search) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "JobOrderId," + //0
                "Client,"+ //1
                "AccountNumber, " + //2
                "Vehicle, " + //3
                "Fare, " + //4
                "Comment, " + //5
                "Latitude, " + //6
                "Longitude, " + //7
                "JobOrderDate, "+//8
                "sysentrydate, "+//9
                "modifiedby, "+//10
                "AuditId, " + //11
                "Status "+//12
                "FROM audit_upload " +
                "WHERE JobOrderId like '%" + JobOrderId + "%' AND AccountNumber like '%"+AccountNumber+"%' AND AuditId like '%"+AuditId+"%'" +
                "AND Vehicle like '%"+search+"%' " +
                "ORDER BY sysentrydate DESC ");
    }

    public static ArrayList<HashMap<String, String>> GetAuditDownload(String job_id, String AccountNumber){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

        Cursor result = workModel.GetAuditDownload(job_id,AccountNumber);
        try
        {
            if(result.getCount() == 0){
                return null;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                data.put("JobOrderId", result.getString(0));
                data.put("AccountNumber", result.getString(1));
                data.put("AccountName", "");
                data.put("MeterNumber", "");
                data.put("Title", result.getString(2));
                Details = result.getString(3);
                data.put("Details", Details);
                data.put("Date", result.getString(4));
                data.put("Latitude", result.getString(5));
                data.put("Longitude", result.getString(6));
                data.put("JobOrderDate",result.getString(7));
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
}
