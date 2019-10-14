package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 04/04/2018.
 */

public class DeliveryModel {
    private static final String TAG = DeliveryModel.class.getSimpleName();
    ArrayList<HashMap<String, String>> EmptyData;
    public static Cursor result;
    public static Cursor GetUntransferdData()
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select " +
                        "job_id," + //0
                        "m_accountnumber," + //1
                        "m_latitude," + //2
                        "m_longitude," + //3
                        "m_remark_code, " +//4
                        "m_remark " +//5

                        " from messengerial  WHERE transfer_data_status = 'Pending' LIMIT 1"
        );
    }

    public static boolean UpdateUploadStatus(String JobId,String AccountNumber){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "upload_status",
            };
            Liquid.LiquidValues = new String[] {
                    "Uploaded",
            };
            whereClause = "m_accountnumber=? AND job_id =?";
            whereArgs = new String[] {AccountNumber,JobId};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("messengerial",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }
    public static Cursor getRemarks(String Query){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select remarks_id," + //0
                        "remarks_description " + //1
                        "from ref_delivery_remarks " +
                        "where remarks_description like '%"+Query+"%' OR remarks_id like '%"+Query+"%'" // +
//                        "order by remarks_id  "
        );
    }

    public static Cursor GetSearchMessengerial(String JobId,String TrackingNumber){





        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select " +
                        "m_client,"+ //0
                        "job_id,"+//1
                        "job_title,"+//2
                        "m_accountnumber,"+//3
                        "m_type,"+//4
                        "m_type_description,"+//5
                        "m_status,"+//6
                        "m_remark_code,"+//7
                        "m_remark,"+//8
                        "m_comment,"+//9
                        "m_latitude,"+//10
                        "m_longitude,"+//11
                        "transfer_data_status,"+//12
                        "m_signature,"+//13
                        "upload_status,"+//14
                        "battery_life,"+//15
                        "m_delivered_timestamp,"+//16
                        "m_delivered_date,"+//17
                        "user_id "+//18
                        "from messengerial " +
                        "where job_id = '"+JobId+"' AND m_accountnumber = '"+TrackingNumber+"' " +
                        "order by m_delivered_timestamp DESC "

        );
    }
    public static Cursor GetMessengerial(String JobId,String TrackingNumber){





        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select " +
                        "m_client,"+ //0
                        "job_id,"+//1
                        "job_title,"+//2
                        "m_accountnumber,"+//3
                        "m_type,"+//4
                        "m_type_description,"+//5
                        "m_status,"+//6
                        "m_remark_code,"+//7
                        "m_remark,"+//8
                        "m_comment,"+//9
                        "m_latitude,"+//10
                        "m_longitude,"+//11
                        "transfer_data_status,"+//12
                        "m_signature,"+//13
                        "upload_status,"+//14
                        "battery_life,"+//15
                        "m_delivered_timestamp,"+//16
                        "m_delivered_date,"+//17
                        "user_id "+//18
                        "from messengerial " +
                        "where job_id = '"+JobId+"' AND (m_accountnumber like '%"+TrackingNumber+"%' OR m_type_description like '%"+TrackingNumber+"%') " +
                        "order by m_delivered_timestamp DESC LIMIT "+(Liquid.RecyclerItemLimit+Liquid.UpdateRecyclerItemLimit)

        );
    }

    public static Cursor GetMessengerialForUpload(String JobId,String TrackingNumber,String Status){

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select " +
                        "m_client,"+ //0
                        "job_id,"+//1
                        "job_title,"+//2
                        "m_accountnumber,"+//3
                        "m_type,"+//4
                        "m_type_description,"+//5
                        "m_status,"+//6
                        "m_remark_code,"+//7
                        "m_remark,"+//8
                        "m_comment,"+//9
                        "m_latitude,"+//10
                        "m_longitude,"+//11
                        "transfer_data_status,"+//12
                        "m_signature,"+//13
                        "upload_status,"+//14
                        "battery_life,"+//15
                        "m_delivered_timestamp,"+//16
                        "m_delivered_date,"+//17
                        "user_id "+//18
                        "from messengerial " +
                        "where job_id = '"+JobId+"' AND m_accountnumber like '%"+TrackingNumber+"%' AND  upload_status = '"+Status+"' "

        );
    }

    public static Cursor GetAllMessengerialForUpload(String JobId,String TrackingNumber){

        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select " +
                        "m_client,"+ //0
                        "job_id,"+//1
                        "job_title,"+//2
                        "m_accountnumber,"+//3
                        "m_type,"+//4
                        "m_type_description,"+//5
                        "m_status,"+//6
                        "m_remark_code,"+//7
                        "m_remark,"+//8
                        "m_comment,"+//9
                        "m_latitude,"+//10
                        "m_longitude,"+//11
                        "transfer_data_status,"+//12
                        "m_signature,"+//13
                        "upload_status,"+//14
                        "battery_life,"+//15
                        "m_delivered_timestamp,"+//16
                        "m_delivered_date,"+//17
                        "user_id "+//18
                        "from messengerial " +
                        "where job_id = '"+JobId+"' AND m_accountnumber like '%"+TrackingNumber+"%'"

        );
    }

    public static Cursor GetDeliveryDetails(String stockInId){
        String Query = "SELECT " +
                "( a.itemDescription || ' : '|| (SELECT COUNT(m_type) FROM messengerial WHERE m_type = a.itemTypeId) || '/' || a.itemQuantity) as ItemsDetails " +
                "FROM StockInItem a " +
                "WHERE a.stockInId = '"+stockInId + "'";
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                Query
        );
    }
    public static Cursor GetStockIn(String stockInId){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select client," + //0
                        "stockInId," + //1
                        "stockInTitle, " + //2
                        "itemTypeId, " + //3
                        "itemDescription, " + //4
                        "itemQuantity, " + //5
                        "stockInDate, " + //6
                        "userId, " + //7
                        "sysEntryDate, " + //8
                        "modifiedBy, " + //9
                        "SUM(itemQuantity) as totalItem " + //10
                        "from StockInItem " +
                        "where stockInTitle like '%"+stockInId+"%' OR stockInId like '%"+stockInId+"%' " +
                        "group by stockInId "+
                        "order by stockInDate DESC LIMIT "+(Liquid.RecyclerItemLimit+Liquid.UpdateRecyclerItemLimit)

        );
    }
    public static Cursor getItemType(String Query)
    {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "select id," +
                        "description," +
                        "abbreviation " +
                        "from ItemType " +
                        "where description like '%"+Query+"%' OR id like '%"+Query+"%' OR abbreviation like '%"+Query+"%'" +
                        "order by cast(id as int)"

        );
    }

    public static boolean doSubmitDelivery(String client,
                                            String job_id,
                                            String job_title,
                                            String customer_id,
                                            String m_type,
                                            String m_type_description,
                                            String m_status,
                                            String m_remark_code,
                                            String m_remark,
                                            String m_comment,
                                            String m_latitude,
                                            String m_longitude,
                                            String transfer_data_status,
                                            String m_signature,
                                            String upload_status,
                                            String battery_life,
                                            String m_delivered_timestamp,
                                            String m_delivered_date,
                                            String user_id
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Delivery;
            Liquid.LiquidValues = new String[]{
                    client,
                    job_id,
                    job_title,
                    customer_id,
                    m_type,
                    m_type_description,
                    m_status,
                    m_remark_code,
                    m_remark,
                    m_comment,
                    m_latitude,
                    m_longitude,
                    transfer_data_status,
                    m_signature,
                    upload_status,
                    battery_life,
                    m_delivered_timestamp,
                    m_delivered_date,
                    user_id
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("messengerial",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitReceive(String client,
                                           String job_id,
                                           String tracking,
                                           String user,
                                           String name,
                                           String code,
                                           String type,
                                           String quantity,
                                          String timestamp,
                                          String date,
                                          String status,
                                          String m_latitude,
                                          String m_longitude



    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Received;
            Liquid.LiquidValues = new String[]{
                    client,
                    job_id,
                    tracking,
                    user,
                    name,
                    code,
                    type,
                    quantity,
                    timestamp,
                    date,
                    status,
                    m_latitude,
                    m_longitude
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("customer_delivery_downloads",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitStockIn(String client,
                                          String stockInId,
                                          String stockInTitle,
                                          String itemTypeId,
                                          String itemDescription,
                                          String itemQuantity,
                                          String stockInDate,
                                          String userId,
                                          String sysEntryDate,
                                          String modifiedBy


    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.StockInColumn;
            Liquid.LiquidValues = new String[]{
                    client,
                    stockInId,
                    stockInTitle,
                    itemTypeId,
                    itemDescription,
                    itemQuantity,
                    stockInDate,
                    userId,
                    sysEntryDate,
                    modifiedBy,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("StockInItem",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static ArrayList<HashMap<String, String>> GetLocalJobOrder(String job_id){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = GetLocalDeliveryJobOrder(job_id);
        try
        {
            if(result.getCount() == 0){
                return null;
            }
            while(result.moveToNext()){
                int total_upload = 0;
                int total_download = 0;
                int total_pending = 0;
                Cursor result_jo = GetDataDownload(result.getString(0));
                Cursor result_job_upload = GetDataUploaded(result.getString(0),"Uploaded");
                Cursor result_job_Pending = GetDataUploaded(result.getString(0),"Pending");
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
                data.put("title", result.getString(2));
                data.put("details", result.getString(1) + "" +
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

    public static Cursor GetDataDownload(String job_id){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "COUNT(rowid) as total_download " + //0
                        "FROM customer_delivery_downloads "+
                        "WHERE job_id = '"+job_id+"'");
    }

    public static Cursor GetDataUploaded(String job_id, String status){
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "COUNT(rowid) as total_upload " + //0
                        "FROM messengerial "+
                        "WHERE job_id = '"+job_id+"' AND upload_status ='"+status+"'");


    }

    public static Cursor GetLocalDeliveryJobOrder(String job_id) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "job_id," +
                "client, " +
                "position, " +
                "delivery_date " +
                "FROM customer_delivery_downloads " +
                "WHERE job_id like '%" + job_id + "%' " +
                "GROUP BY job_id ORDER BY sysentrydate DESC ");


    }
}
