package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

public class SettingModel {
    private static final String TAG = SettingModel.class.getSimpleName();
    public static Cursor GetSettings() {


        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                         "SELECT " +
                        "ReverseInput," +//0
                        "HideKeyboard " +//1
                        "FROM ums_settings ");
    }

    public static boolean UpdateSettings(String column,String value){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    column,
            };
            Liquid.LiquidValues = new String[] {
                    value,
            };
            whereClause = "Id=?";
            whereArgs = new String[] {"0"};
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("ums_settings",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return true;
        }catch(Exception e){
            Log.e(TAG, "Tristan Gary Leyesa", e);
            return false;
        }
    }
}
