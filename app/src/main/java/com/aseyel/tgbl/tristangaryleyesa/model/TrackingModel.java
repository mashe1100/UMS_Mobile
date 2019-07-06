package com.aseyel.tgbl.tristangaryleyesa.model;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Romeo on 2018-01-17.
 */

public class TrackingModel {
    private static final String TAG = TrackingModel.class.getSimpleName();
    ArrayList<HashMap<String, String>> EmptyData;
    public static Cursor result;
    public static Cursor GetStoreStatus(String OutletNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "status," + //0
                "latitude," + //1
                "longitude," + //2
                "transferdatastatus,"+ //3
                "picture," + //4
                "period," + //5
                "sysentrydate," + //6
                "modifiedby "+  //7
                "FROM stores_status " +
                "WHERE customer_id = '" + OutletNumber + "' " +
                "AND period = '" + Period + "' " +
                "ORDER BY customer_id ASC ");
    }


    public static boolean DeleteAvailability(String OutletNumber,String ProductCode,String Period){
        try{
            boolean result = false;
            String query = "DELETE FROM availability WHERE customer_id ='"+ OutletNumber +"' AND prodcode = '"+ ProductCode +"' AND period = '"+ Period +"'";
            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }


    public static boolean DeleteSOVI(String OutletNumber,String ProductCode,String Category,String Period){
        try{
            boolean result = false;
            String query =
                    "DELETE FROM mt_sovi  WHERE customer_id = '" + OutletNumber + "' " +
                    "AND productname = '" + ProductCode + "' " +
                    //"AND location = '"+ Location+"' "+
                    "AND sovi_type = '" + Category + "' " +
                    "AND period = '" + Period + "' ";

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }
    public static boolean DeleteActivation(String OutletNumber,String Activation,String Category,String Period){
        try{
            boolean result = false;
            String query =
                    "DELETE FROM mt_activation  WHERE customer_id = '" + OutletNumber + "' " +
                            "AND name = '"+Activation+"' "+
                            "AND categoriesUsedFor = '" + Category + "' " +
                            "AND period = '" + Period + "' ";

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean DeleteCDE(String OutletNumber,String Name,String Period){
        try{
            boolean result = false;
            String query =
                    "DELETE FROM mt_cde  WHERE customer_id = '" + OutletNumber + "' " +
                            "AND category = '"+Name+"' "+
                            "AND period = '" + Period + "' ";

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }


    public static boolean DeleteLocalJobOrder(String JobOrderId){
        try{
            boolean result = false;
            String query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean DeleteLocalJobOrderDetails(String JobOrderId,String JobType){
        try{
            boolean result = false;
            String query = "";
            switch(JobType){
                case "AUDIT":
                    //query = "DELETE FROM audit_download  WHERE JobOrderId = '" + JobOrderId + "' AND AccountNumber = '"+AccountNumber +"' ";
                    break;
                case "TRACKING":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
                case "MESSENGER":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    query = "DELETE FROM customer_delivery_downloads  WHERE job_id = '" + JobOrderId + "' ";
                    break;
                case "METER READER":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    query = "DELETE FROM customer_reading_downloads  WHERE job_id = '" + JobOrderId + "' ";
                    break;
                case "DISCONNECTOR":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    query = "DELETE FROM customer_disconnection_downloads  WHERE job_id = '" + JobOrderId + "' ";
                    break;
            }

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean DeleteAudit(String JobOrderId,String AccountNumber,String Code){
        try{
            boolean result = false;
            String query = "DELETE FROM audit_upload  WHERE JobOrderId = '" + JobOrderId + "' AND AccountNumber  = '"+ AccountNumber+"' AND AuditId = '"+Code+"' ";
            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }
    public static boolean DeleteJobDetails(String JobType,String JobOrderId,String AccountNumber){
        try{
            boolean result = false;
            String query = "";
            switch(JobType){
                case "AUDIT":
                     query = "DELETE FROM audit_download  WHERE JobOrderId = '" + JobOrderId + "' AND AccountNumber = '"+AccountNumber +"' ";
                    break;
                case "TRACKING":
                     //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
                case "MESSENGERIAL":
                case "Messengerial":
                case "MESSENGER":
                     query = "DELETE FROM messengerial  WHERE m_accountnumber = '" + AccountNumber + "' ";
                    break;
                case "METER READER":
                     //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
            }

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }


    public static boolean DeleteJobOrderDetails(String JobType,String JobOrderId,String AccountNumber){
        try{
            boolean result = false;
            String query = "";
            switch(JobType){
                case "AUDIT":
                    //query = "DELETE FROM audit_download  WHERE JobOrderId = '" + JobOrderId + "' AND AccountNumber = '"+AccountNumber +"' ";
                    break;
                case "TRACKING":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    break;
                case "MESSENGER":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    query = "DELETE FROM customer_delivery_downloads  WHERE job_id = '" + JobOrderId + "' ";
                    break;
                case "METER READER":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    query = "DELETE FROM customer_reading_downloads  WHERE job_id = '" + JobOrderId + "' ";
                    break;
                case "DISCONNECTOR":
                    //query = "DELETE FROM joborder  WHERE id = '" + JobOrderId + "' ";
                    query = "DELETE FROM customer_disconnection_downloads  WHERE job_id = '" + JobOrderId + "' ";
                    break;
            }

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }
    public static boolean DeleteSOVIByLocation(String OutletNumber,String Sovi,String Description,String Location,String Period){
        try{
            boolean result = false;
            String query = "DELETE FROM mt_sovi  WHERE customer_id = '" + OutletNumber + "' " +
                    "AND productname = '" + Sovi +"' "+
                    "AND location = '" + Location +"' "+
                    "AND description = '" + Description +"' "+
                    "AND period = '" + Period +"'";
            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean DeleteShelfPlanogram(String OutletNumber,String Name,String Period){
        try{
            boolean result = false;
            String query =
                    "DELETE FROM mt_shelf_planogram  WHERE customer_id = '" + OutletNumber + "' " +
                            "AND name = '"+Name+"' "+
                            "AND period = '" + Period + "' ";

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }
    public static boolean DeleteCoolerPlanogram(String OutletNumber,String Name,String Period){
        try{
            boolean result = false;
            String query =
                    "DELETE FROM mt_cde_planogram  WHERE customer_id = '" + OutletNumber + "' " +
                            "AND name = '"+Name+"' "+
                            "AND period = '" + Period + "' ";

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean DeleteSOVILocation(String OutletNumber,String Location,String Period){
        try{
            boolean result = false;
            String query =
                    "DELETE FROM mt_sovi_location  WHERE customer_id = '" + OutletNumber + "' " +
                            "AND location = '" + Location + "' " +
                            "AND period = '" + Period + "' ";

            result = SplashActivity.mDatabaseHelper.SqliteDeleteQuery(query);
            return result;
        }catch(Exception e){
            return false;
        }
    }

    public static Cursor GetCategoryComment(String OutletNumber,String Category,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id, " + //0
                        "category, " + //1
                        "comment, " + //2
                        "period, " + //3
                        "sysentrydate, " + //4
                        "modifiedby " + //5
                        "FROM mt_category_comment " +
                        "WHERE customer_id = '" + OutletNumber + "' " +
                        "AND category like '%"+ Category +"%'" +
                        "AND period = '" + Period + "' " +
                        "ORDER BY customer_id ASC ");
    }

    public static Cursor GetComment(String OutletNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "comment, " +
                        "customer_id, " +
                        "sysentrydate, " +
                        "period, " +
                        "modifiedby " +
                        "FROM mt_comment " +
                        "WHERE customer_id = '" + OutletNumber + "' " +
                        "AND period = '" + Period + "' " +
                        "ORDER BY customer_id ASC ");
    }

    public static Cursor GetTrackingSovi(String ItemCode,String OutletNumber,String Location,String Category,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "location, "+ //0
                "numkof, " + //1
                "numnonkof, " +//2
                "cans, " +//3
                "sspet, " +//4
                "mspet, " +//5
                "ssdoy, " +//6
                "ssbrick, " +//7
                "msbrick, " +//8
                "sswedge, " +//9
                "box, " +//10
                "litro, " +//11
                "pounch, " +//12
                "comment, " +//13
                "productname, "+//14
                "description, "+//15
                "sovi_type, "+//16
                "picture, "+//17
                "period, "+//18
                "sysentrydate, "+//19
                "modifiedby "+//20
                "FROM mt_sovi " +
                "WHERE customer_id = '" + OutletNumber + "' " +
                "AND productname = '" + ItemCode + "' " +
                "AND location = '"+ Location+"' "+
                "AND description = '" + Category + "' " +
                "AND period = '" + Period + "' " +
                "ORDER BY customer_id ASC ");
    }

    public static Cursor GetSovi(String ItemCode,String OutletNumber,String Location,String Category,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "location, "+ //0
                "numkof, " + //1
                "numnonkof, " +//2
                "cans, " +//3
                "sspet, " +//4
                "mspet, " +//5
                "ssdoy, " +//6
                "ssbrick, " +//7
                "msbrick, " +//8
                "sswedge, " +//9
                "box, " +//10
                "litro, " +//11
                "pounch, " +//12
                "comment, " +//13
                "productname, "+//14
                "description, "+//15
                "sovi_type, "+//16
                "picture, "+//17
                "period, "+//18
                "sysentrydate, "+//19
                "modifiedby "+//20
                "FROM mt_sovi " +
                "WHERE customer_id = '" + OutletNumber + "' " +
                "AND productname like '%" + ItemCode + "%' " +
                "AND location like '%"+ Location+"%' "+
                "AND description like '%" + Category + "%' " +
                "AND period = '" + Period + "' " +
                "ORDER BY customer_id ASC ");
    }
    public static Cursor GetAvailability(String ItemCode,String OutletNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "retprice, " + //0
                        "comment, " + //1
                        "prodcode, " + //2
                        "period," + //3
                        "sysentrydate," + //4
                        "modifiedby "+  //5
                        "FROM availability " +
                        "WHERE customer_id = '" + OutletNumber + "' " +
                        "AND prodcode like '%" + ItemCode + "%' " +
                        "AND period = '" + Period + "' " +
                        "ORDER BY customer_id ASC ");
    }

    public static Cursor GetCDE(
            String OutletNumber,
            String Cooler,
            String Question,
            String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," + //0
                        "name,"+//1
                        "category,"+//2
                        "question,"+//3
                        "questionvalue,"+//4
                        "count,"+//5
                        "picture,"+//6
                        "comment,"+//7
                        "period "+ //8
                        "FROM mt_cde " +
                        "WHERE customer_id = '" + OutletNumber + "' " +
                        "AND question = '" + Question + "' " +
                        "AND category = '" + Cooler + "' " +
                        "AND period = '" + Period + "' " +
                        "GROUP BY customer_id,category ");
    }

    public static Cursor GetTrackingCDE(
            String OutletNumber,
            String Area,
            String Cooler,
            String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," + //0
                        "name,"+//1
                        "category,"+//2
                        "question,"+//3
                        "questionvalue,"+//4
                        "count,"+//5
                        "picture,"+//6
                        "comment,"+//7
                        "period, "+ //8
                        "sysentrydate, "+//9
                        "modifiedby "+//10
                        "FROM mt_cde " +
                        "WHERE customer_id like '%" + OutletNumber + "%' " +
                        "AND name like '%" + Area + "%' " +
                        "AND category like '%" + Cooler + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "GROUP BY customer_id,category ");
    }

    public static Cursor GetTrackingCDEData(
            String OutletNumber,
            String Area,
            String Cooler,
            String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," + //0
                        "name,"+//1
                        "category,"+//2
                        "question,"+//3
                        "questionvalue,"+//4
                        "count,"+//5
                        "picture,"+//6
                        "comment,"+//7
                        "period, "+ //8
                        "sysentrydate, "+//9
                        "modifiedby "+//10
                        "FROM mt_cde " +
                        "WHERE customer_id like '%" + OutletNumber + "%' " +
                        "AND name like '%" + Area + "%' " +
                        "AND category like '%" + Cooler + "%' " +
                        "AND period like '%" + Period + "%' "
                       );
    }
    public static Cursor GetProductList(String Search) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "productCode," +//0
                        "productName, " +//1
                        "productType " +//2
                        "FROM products " +
                        "WHERE productCode like '%" + Search + "%' AND category like 'MT' " +
                        "OR productName like '%" + Search + "%' " +
                        "AND category like 'MT' " +
                        "ORDER BY productName ASC ");
    }


    public static Cursor GetCoolerPlanogramList(String search) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "id," +
                        "description " +
                        "FROM cde_planogram_compliance " +
                        "WHERE id like '%" + search + "%' " +
                        "OR description like '%" + search + "%' " +
                        "ORDER BY description ASC ");
    }

    public static Cursor GetShelfPlanogramList(String search) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "id," +
                        "description " +
                        "FROM product_category " +
                        "WHERE id like '%" + search + "%' " +
                        "OR description like '%" + search + "%' " +
                        "ORDER BY id ASC ");
    }
    public static Cursor GetShelfPlanogram(String AccountNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," +//0
                        "name, " +//1
                        "value, " +//2
                        "picture, " +//3
                        "period, " +//4
                        "sysentrydate, " +//5
                        "modifiedby " +//6
                        "FROM mt_shelf_planogram " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "ORDER BY customer_id ASC ");
    }

    public static Cursor GetGroupPicture(String AccountNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "a_id," +//0
                        "customer_id, " +//1
                        "category, " +//2
                        "subcategory, " +//3
                        "subsubcategory, " +//4
                        "subtype, " +//5
                        "count, " +//6
                        "picture, " +//7
                        "period, " +//8
                        "count, " +//9
                        "sysentrydate, " +//10
                        "modifiedby " +//11
                        "FROM mt_picture " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "GROUP BY category " +
                        "ORDER BY customer_id ASC ");
    }
    public static Cursor GetPicture(String AccountNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "a_id," +//0
                        "customer_id, " +//1
                        "category, " +//2
                        "subcategory, " +//3
                        "subsubcategory, " +//4
                        "subtype, " +//5
                        "count, " +//6
                        "picture, " +//7
                        "period, " +//8
                        "count, " +//9
                        "sysentrydate, " +//10
                        "modifiedby " +//11
                        "FROM mt_picture " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "ORDER BY customer_id ASC ");
    }

    public static Cursor GetSignature(String AccountNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," +//0
                        "name, " +//1
                        "period, " +//2
                        "sysentrydate, " +//3
                        "modifiedby " +//4
                        "FROM mt_signature " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "ORDER BY customer_id ASC ");
    }


    public static Cursor GetCoolerPlanogram(String AccountNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," +//0
                        "name, " +//1
                        "value, " +//2
                        "picture, " +//3
                        "period, " +//4
                        "sysentrydate, " +//5
                        "modifiedby " +//6
                        "FROM mt_cde_planogram " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "ORDER BY customer_id ASC ");
    }


    public static Cursor GetSoviLocation(String AccountNumber,String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," +//0
                        "location, " +//1
                        "picture, " +//2
                        "period, " +//3
                        "sysentrydate, " +//4
                        "modifiedby " +//5
                        "FROM mt_sovi_location " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "ORDER BY customer_id ASC ");
    }


    public static Cursor GetSoviLocationList(String search) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "id," +
                        "description " +
                        "FROM location_category " +
                        "WHERE id like '%" + search + "%' " +
                        "OR description like '%" + search + "%' " +
                        "ORDER BY id ASC ");
    }


    public static Cursor GetTrackingActivation(String AccountNumber,
                                               String Description,
                                               String Category,
                                               String Period) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "customer_id," + //0
                        "name, " + //1
                        "available, " + //2
                        "nummaterial, " + //3
                        "categoriesUsedFor, " + //4
                        "picture, " + //5
                        "period, "+ //6
                        "sysentrydate, " + //7
                        "modifiedby "+  //8
                        "FROM mt_activation " +
                        "WHERE customer_id like '%" + AccountNumber + "%' " +
                        "AND name like '%" + Description + "%' " +
                        "AND categoriesUsedFor like '%" + Category + "%' " +
                        "AND period like '%" + Period + "%' " +
                        "ORDER BY name ASC ");
    }



    public static Cursor GetSoviList(String Search) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery("SELECT " +
                "id as productCode," +
                "description as productName, " +
                "product as productType, " +
                "type as productCategory " +
                "FROM sovi_product " +
                "WHERE id like '%" + Search + "%' " +
                "OR description like '%" + Search + "%' " +
                "ORDER BY description ASC ");
    }

    public static boolean doSubmitSignature(String customer_id,
                                            String name,
                                            String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Signature;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    name,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_signature",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitPicture(String customer_id,
                                          String category,
                                          String subcategory,
                                          String subsubcategory,
                                          String subtype,
                                          String count,
                                          String picture,
                                          String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.TrackingPicture;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    category,
                    subcategory,
                    subsubcategory,
                    subtype,
                    count,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_picture",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitCoolerPlanogram(String customer_id,
                                                  String name,
                                                  String value,
                                                  String picture,
                                                  String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.CoolerPlanogram;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    name,
                    value,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_cde_planogram",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }

    public static boolean doSubmitShelfPlanogram(String customer_id,
                                                 String name,
                                                 String value,
                                                 String picture,
                                                 String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.ShelfPlanogram;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    name,
                    value,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_shelf_planogram",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }

    public static boolean doSubmitSoviLocation(String customer_id,
                                               String location,
                                               String picture,
                                               String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.SoviLocation;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    location,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_sovi_location",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }

    public static boolean doSubmitActivation(String customer_id,
                                             String name,
                                             String available,
                                             String nummaterial,
                                             String categoriesUsedFor,
                                             String  picture,
                                             String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Activation;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    name,
                    available,
                    nummaterial,
                    categoriesUsedFor,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_activation",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }

    public static boolean doSubmitCDE(String customer_id,
                                      String name,
                                      String category,
                                      String question,
                                      String questionvalue,
                                      String count,
                                      String picture,
                                      String comment,
                                      String period){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.CDE;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    name,
                    category,
                    question,
                    questionvalue,
                    count,
                    picture,
                    comment,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_cde",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }
    public static boolean doSubmitComment(String customer_id,
                                          String category,
                                          String comment,
                                          String period){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.CategoryComment;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    category,
                    comment,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_category_comment",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }

    }
    public static boolean doSubmitAvailability(String customer_id,
                                               String prodcode,
                                               String twobottles,
                                               String onebottle,
                                               String visible,
                                               String cold,
                                               String comment,
                                               String retprice,
                                               String picture,
                                               String period){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Availability;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    prodcode,
                    twobottles,
                    onebottle,
                    visible,
                    cold,
                    comment,
                    retprice,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("availability",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }

    public static boolean doSubmitStoreStatus(String customer_id,
                                              String latitude,
                                              String longitude,
                                              String status,
                                              String transferdatastatus,
                                              String picture,
                                              String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.StoreStatus;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    latitude,
                    longitude,
                    status,
                    transferdatastatus,
                    picture,
                    period,
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("stores_status",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }
    public static boolean doSubmitComment(String customer_id,
                                          String comment,
                                          String period
    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Comment;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    comment,
                    period
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_comment",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }



    public static boolean doSubmitSovi(String customer_id,
                                       String productname,
                                       String description,
                                       String sovi_type,
                                       String location,
                                       String numkof,
                                       String numnonkof,
                                       String cans,
                                       String sspet,
                                       String mspet,
                                       String ssdoy,
                                       String ssbrick,
                                       String msbrick,
                                       String sswedge,
                                       String box,
                                       String litro,
                                       String pounch,
                                       String picture,
                                       String comment,
                                       String period){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.Sovi;
            Liquid.LiquidValues = new String[]{
                    customer_id,
                    productname,
                    description,
                    sovi_type,
                    location,
                    numkof,
                    numnonkof,
                    cans,
                    sspet,
                    mspet,
                    ssdoy,
                    ssbrick,
                    msbrick,
                    sswedge,
                    box,
                    litro,
                    pounch,
                    picture,
                    comment,
                    period
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("mt_sovi",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }
}
