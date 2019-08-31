package com.aseyel.tgbl.tristangaryleyesa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaScannerConnection;
import android.util.Log;
import android.widget.Switch;

import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Romeo on 2017-12-29.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ums_mobile.db";
    private static final String TAG = "SQLiteOpenHelper";
    public static String DB_FILEPATH = "/data/user/0/"+Liquid.LiquidPackageName+"/databases/"+DATABASE_NAME;
    public static final int CONFLICT_REPLACE = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

        //Log.i(TAG,"Tristan "+context.getDatabasePath(DATABASE_NAME).toString());
        //onUpgrade(db,1,1);
    }
    public void UpdateDatabase(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(LiquidReference.AlterStatusPicture);
            db.execSQL(LiquidReference.AlterStatusMeterNotInList);
            db.execSQL(LiquidReference.AlterDemandMeterNotInList);
            db.execSQL(LiquidReference.AlterHouseLatitudeMeterNotInList);
            db.execSQL(LiquidReference.AlterHouseLongitudeMeterNotInList);
            db.execSQL(LiquidReference.AlterAmpirCapacityMeterNotInList);
            db.execSQL(LiquidReference.AlterTypeMeterNotInList);
            db.execSQL(LiquidReference.AlterLatitudeCustomerReadingDownload);
            db.execSQL(LiquidReference.AlterLongitudeCustomerReadingDownload);

        }catch(Exception e){
            Log.e(TAG,"Tristan :",e);
        }
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            /*db.execSQL(LiquidReference.DeleteRemarks);
            for(int a = 0; a < LiquidReference.MeterReadingRemarksData.length; a++) {
                db.execSQL(LiquidReference.MeterReadingRemarksData[a]);
            }*/

        }catch(Exception e){
            Log.e(TAG,"Tristan :",e);
        }
    }

    public void UpdateDb(){

    }
    public void ClearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    public void CreateExternalDatabase(File dbFile){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
            onCreate(db);
            db.close();

        }

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table

        try{
            db.execSQL(LiquidReference.UMSSettings);
            for(int a = 0; a < LiquidReference.SettingsData.length; a++){
                db.execSQL(LiquidReference.SettingsData[a]);
            }

            db.execSQL(LiquidReference.UMSAccount);
            db.execSQL(LiquidReference.Admin);
            //Audit TABLE
            db.execSQL(LiquidReference.AuditDownload);
            db.execSQL(LiquidReference.AuditUpload);

            //Tracking TABLE
            db.execSQL(LiquidReference.CokeCustomerTable);
            db.execSQL(LiquidReference.JobOrderTable);
            db.execSQL(LiquidReference.ProductList);
            db.execSQL(LiquidReference.SoviProductList);
            db.execSQL(LiquidReference.TrackingAvalability);
            db.execSQL(LiquidReference.TrackingSovi);
            db.execSQL(LiquidReference.TrackingActivation);
            db.execSQL(LiquidReference.TrackingCDE);
            db.execSQL(LiquidReference.TrackingCoolerPlanogram);
            db.execSQL(LiquidReference.TrackingShelfPlanogram);
            db.execSQL(LiquidReference.CoolerPlanogramList);
            db.execSQL(LiquidReference.ProductCategoryList);
            db.execSQL(LiquidReference.LocationCategoryList);
            db.execSQL(LiquidReference.TrackingStoreStatus);
            db.execSQL(LiquidReference.TrackingComment);
            db.execSQL(LiquidReference.TrackingSignature);
            db.execSQL(LiquidReference.TrackingSoviLocation);
            db.execSQL(LiquidReference.TrackingPicture);
            db.execSQL(LiquidReference.TrackingCategoryComment);
            //TrackingData
           /* for(int a = 0; a < LiquidReference.LocationCategoryListData.length; a++){
                db.execSQL(LiquidReference.LocationCategoryListData[a]);
            }
            for(int a = 0; a < LiquidReference.ProductListData.length; a++){
                db.execSQL(LiquidReference.ProductListData[a]);
            }
            for(int a = 0; a < LiquidReference.ProductCategoryListData.length; a++){
                db.execSQL(LiquidReference.ProductCategoryListData[a]);
            }
            for(int a = 0; a < LiquidReference.ProductSoviListData.length; a++){
                Log.i(TAG,LiquidReference.ProductSoviListData[a]);
                db.execSQL(LiquidReference.ProductSoviListData[a]);
            }
            for(int a = 0; a < LiquidReference.CoolerPlanogramListData.length; a++){
                Log.i(TAG,LiquidReference.CoolerPlanogramListData[a]);
                db.execSQL(LiquidReference.CoolerPlanogramListData[a]);
            }*/

            //Messengerial
            db.execSQL(LiquidReference.DeliveryTableList);
            db.execSQL(LiquidReference.CustomerDeliveryList);
            db.execSQL(LiquidReference.ItemType);
            db.execSQL(LiquidReference.StockInItem);


            //MeterReading
            db.execSQL(LiquidReference.MeterReadingCustomerReadingDownloads);
            db.execSQL(LiquidReference.MeterReadingMeterNotInList);
            db.execSQL(LiquidReference.MeterReadingPictures);
            db.execSQL(LiquidReference.MeterReadingMOA);
            db.execSQL(LiquidReference.MeterReadingRates);
            db.execSQL(LiquidReference.MeterReadingRates2);
            db.execSQL(LiquidReference.MeterReadingRatesDescription);
            db.execSQL(LiquidReference.MeterReadingLifeline);
            db.execSQL(LiquidReference.MeterReadingReading);
            db.execSQL(LiquidReference.MeterReadingReadingLogs);
            db.execSQL(LiquidReference.MeterReadingDeliveryRemarks);
            db.execSQL(LiquidReference.MeterReadingImprobable);
            db.execSQL(LiquidReference.MeterReadingMeterBrand);
            db.execSQL(LiquidReference.MeterReadingNegativeReading);
            db.execSQL(LiquidReference.MeterReadingRemarks);
            db.execSQL(LiquidReference.MeterReadingRoute);
            db.execSQL(LiquidReference.IndexCustomerMeterReading);
            db.execSQL(LiquidReference.IndexMeterReadingPicture);
            db.execSQL(LiquidReference.IndexMeterReadingRates);
            db.execSQL(LiquidReference.IndexMeterReadingRates2);
            db.execSQL(LiquidReference.IndexMeterReadingReading);
            db.execSQL(LiquidReference.IndexMeterReadingMOA);
            db.execSQL(LiquidReference.CreateViewJobOrderDetails);

            switch (Liquid.Client){
                case "more_power":
                    for(int a = 0; a < LiquidReference.MeterReadingMorePowerRemarksData.length; a++) {
                        db.execSQL(LiquidReference.MeterReadingMorePowerRemarksData[a]);
                    }
                    break;
                case "baliwag_wd":
                    for(int a = 0; a < LiquidReference.MeterReadingBaliwagWDRemarksData.length; a++) {
                        db.execSQL(LiquidReference.MeterReadingBaliwagWDRemarksData[a]);
                    }
                    break;
                default:
                    for(int a = 0; a < LiquidReference.MeterReadingIleco2RemarksData.length; a++) {
                        db.execSQL(LiquidReference.MeterReadingIleco2RemarksData[a]);
                    }
            }
            //MORE POWER
//            for(int a = 0; a < LiquidReference.MeterReadingMorePowerRemarksData.length; a++) {
//                db.execSQL(LiquidReference.MeterReadingMorePowerRemarksData[a]);
//            }
//            //ILECO II REMARKS
//            for(int a = 0; a < LiquidReference.MeterReadingIleco2RemarksData.length; a++) {
//                        db.execSQL(LiquidReference.MeterReadingIleco2RemarksData[a]);
//            }

        // Messengerial Data
            for(int a = 0; a < LiquidReference.ItemTypeData.length; a++) {
                db.execSQL(LiquidReference.ItemTypeData[a]);
            }

            //Default Remarks (MORE POWER)
//            for(int a = 0; a < LiquidReference.MeterReadingRemarksData.length; a++) {
//                        db.execSQL(LiquidReference.MeterReadingRemarksData[a]);
//            }


            for(int a = 0; a < LiquidReference.MeterReadingDeliveryRemarksData.length; a++) {
                db.execSQL(LiquidReference.MeterReadingDeliveryRemarksData[a]);
            }
            for(int a = 0; a < LiquidReference.RatesDescriptionData.length; a++) {
                db.execSQL(LiquidReference.RatesDescriptionData[a]);
            }

            //Disconnection
            db.execSQL(LiquidReference.CustomerDiconnectionDownloads);
            db.execSQL(LiquidReference.DiconnectionTable);
            db.execSQL(LiquidReference.DisconnectionRemarks);

            for(int a = 0; a < LiquidReference.DisconnectionRemarksData.length; a++) {
                db.execSQL(LiquidReference.DisconnectionRemarksData[a]);
            }


        }catch(Exception e){
            Log.e(TAG, "Error at ", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Existing Table
        try{
            db.execSQL(LiquidReference.DropUMSSettings);
            //AUDIT TABLE
            db.execSQL(LiquidReference.DropCokeCustomerTable);
            db.execSQL(LiquidReference.DropJobOrderTable);
            db.execSQL(LiquidReference.DropProductList);
            db.execSQL(LiquidReference.DropSoviProductList);
            db.execSQL(LiquidReference.DropTrackingAvalability);
            db.execSQL(LiquidReference.DropTrackingSovi);
            db.execSQL(LiquidReference.DropTrackingActivation);
            db.execSQL(LiquidReference.DropTrackingCDE);
            db.execSQL(LiquidReference.DropTrackingCoolerPlanogram);
            db.execSQL(LiquidReference.DropTrackingShelfPlanogram);
            db.execSQL(LiquidReference.DropCoolerPlanogramList);
            db.execSQL(LiquidReference.DropProductCategoryList);
            db.execSQL(LiquidReference.DropLocationCategoryList);
            db.execSQL(LiquidReference.DropTrackingStoreStatus);
            db.execSQL(LiquidReference.DropTrackingComment);
            db.execSQL(LiquidReference.DropTrackingSignature);
            db.execSQL(LiquidReference.DropTrackingSoviLocation);
            db.execSQL(LiquidReference.DropTrackingPicture);
            db.execSQL(LiquidReference.DropTrackingCategoryComment);
            db.execSQL(LiquidReference.DropAuditDownload);
            db.execSQL(LiquidReference.DropAuditUpload);
            db.execSQL(LiquidReference.DropUMSAccount);

            //DELIVERY TABLE
            //db.execSQL(LiquidReference.DropDeliveryRemarksList);
            db.execSQL(LiquidReference.DropDeliveryTableList);
            db.execSQL(LiquidReference.DropCustomerDeliveryList);
            db.execSQL(LiquidReference.DropItemType);
            db.execSQL(LiquidReference.DropStockInItem);
            //MeterReadnig
            db.execSQL(LiquidReference.DropMeterReadingCustomerReadingDownloads);
            db.execSQL(LiquidReference.DropMeterReadingMeterNotInList);
            db.execSQL(LiquidReference.DropMeterReadingPictures);
            db.execSQL(LiquidReference.DropMeterReadingMOA);
            db.execSQL(LiquidReference.DropMeterReadingRates);
            db.execSQL(LiquidReference.DropMeterReadingRates2);
            db.execSQL(LiquidReference.DropMeterReadingRatesDescription);
            db.execSQL(LiquidReference.DropMeterReadingLifeline);
            db.execSQL(LiquidReference.DropMeterReadingReading);
            db.execSQL(LiquidReference.DropMeterReadingReadingLogs);
            db.execSQL(LiquidReference.DropMeterReadingDeliveryRemarks);
            db.execSQL(LiquidReference.DropMeterReadingImprobable);
            db.execSQL(LiquidReference.DropMeterReadingMeterBrand);
            db.execSQL(LiquidReference.DropMeterReadingNegativeReading);
            db.execSQL(LiquidReference.DropMeterReadingRemarks);
            db.execSQL(LiquidReference.DropMeterReadingRoute);
            db.execSQL(LiquidReference.DropCreateViewJobOrderDetails);

            //Disconnection
            db.execSQL(LiquidReference.DropCustomerDiconnectionDownloads);
            db.execSQL(LiquidReference.DropDiconnectionTable);
            db.execSQL(LiquidReference.DropDisconnectionRemarks);

            onCreate(db);
        }catch(Exception e){
            Log.e(TAG, "Error at ", e);
        }

    }

    public boolean SqliteReplaceQuery(String table, String[] columns, String[] values){
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            for(int a = 0; a < columns.length; a++){
                contentValues.put(columns[a],values[a]);
            }
            contentValues.put("sysentrydate",Liquid.currentDateTime());
            contentValues.put("modifieddate",Liquid.currentDateTime());
            contentValues.put("modifiedby",Liquid.User);

            long result = db.replace(table,null,contentValues);

            if(result == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }


    public boolean SqliteReplaceQueryWithoutDefault(String table, String[] columns, String[] values){
        try
        {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            for(int a = 0; a < columns.length; a++){
                contentValues.put(columns[a],values[a]);
            }

            long result = db.replace(table,null,contentValues);

            if(result == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public boolean SqliteUpdateQuery(String table, String[] columns, String[] values,String whereClause,String[] whereArgs){
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            for(int a = 0; a < columns.length; a++){
                contentValues.put(columns[a],values[a]);
            }

            contentValues.put("modifiedby",Liquid.User);
            long result = db.update(table,contentValues,whereClause,whereArgs);

            if(result == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public boolean SqliteInsertQuery(String table, String[] columns, String[] values){
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            for(int a = 0; a < columns.length; a++){
                contentValues.put(columns[a],values[a]);
            }
            contentValues.put("sysentrydate",Liquid.currentDateTime());
            contentValues.put("modifieddate",Liquid.currentDateTime());
            contentValues.put("modifiedby",Liquid.User);
            long result = db.insert(table,null,contentValues);

            if(result == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public Cursor SqliteSelectQuery(String query){
        try
        {

            SQLiteDatabase db = this.getWritableDatabase();
            Log.i(TAG,query);
            Cursor res = db.rawQuery(query,null);

            return res;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return null;
        }
    }

    public boolean SqliteDeleteQuery(String query){
        try
        {

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public boolean SqliteUpdateCustomerQuery(String table, String[] columns, String[] values,String whereClause,String[] whereArgs){
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            for(int a = 0; a < columns.length; a++){
                contentValues.put(columns[a],values[a]);
            }

            contentValues.put("C_ModifiedBy",Liquid.User);
            long result = db.update(table,contentValues,whereClause,whereArgs);

            if(result == -1)
                return false;
            else
                return true;

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }


    public boolean ImportDatabase(String dbPath) throws IOException {
        //V1
        /*// Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        close();
        File newDb = new File(dbPath,DATABASE_NAME);
        File oldDb = new File(DB_FILEPATH);

        if (oldDb.exists()) {
            LiquidFileUtils.copyFile(new FileInputStream(oldDb), new FileOutputStream(newDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;*/

        //V2
        try{
            File newDb = new File(DB_FILEPATH);
            File oldDb = new File(dbPath);
            InputStream mInputStream = new FileInputStream(oldDb+"/"+DATABASE_NAME);
            // Open the empty db as the output stream

            OutputStream mOutputStream = new FileOutputStream(DB_FILEPATH);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer))>0){
                mOutputStream.write(buffer, 0, length);
            }
            // Close the streams
            // mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();



            return  true;
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
            return false;
        }

    }

    public boolean ExportDatabase(String dbPath) throws IOException {
        try{
        //V1
        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        Liquid.deleteRecursive(new File("sdcard/UMS/BackUp"));
        close();
        File newDbDirectory = new File(dbPath);
        File newDb = new File(dbPath,DATABASE_NAME);
        File oldDb = new File(DB_FILEPATH);
        if (!newDb.exists())
        {
            //Create Directory
            newDbDirectory.mkdirs();
            CreateExternalDatabase(newDb);

        }
        if (oldDb.exists()) {
            LiquidFileUtils.copyFile(new FileInputStream(oldDb), new FileOutputStream(newDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;

        //V2
//
//            File newDb = new File(dbPath);
//            File oldDb = new File(DB_FILEPATH);
//            InputStream mInputStream = new FileInputStream(oldDb);
//            // Open the empty db as the output stream
//            if (!newDb.exists())
//            {
//                newDb.mkdirs();
//            }
//            OutputStream mOutputStream = new FileOutputStream(dbPath+DATABASE_NAME);
//            // Transfer bytes from the inputfile to the outputfile
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = mInputStream.read(buffer))>0){
//                mOutputStream.write(buffer, 0, length);
//            }
//            // Close the streams
//           // mOutputStream.flush();
//            mOutputStream.close();
//            mInputStream.close();



        }catch(Exception e){
            Log.e(TAG,"Error ",e);
            return false;
        }

    }

}
