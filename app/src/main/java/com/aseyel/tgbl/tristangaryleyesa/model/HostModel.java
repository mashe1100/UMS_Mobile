package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.nfc.Tag;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.UpdateHostActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingActivationFragment;

/**
 * Created by Mariesher Zapico
 * Feb 07, 2020
 * Create a class for Host Model
 */
public class HostModel {
    private static final String TAG = HostModel.class.getSimpleName();

    public static Cursor GetHostID(String ID) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT id," +
                        "username," +
                        "password," +
                        "hostname " +
                        "FROM ums_host "+
                "WHERE id = '" + ID + "' "
        );
    }

    public static boolean DoUpdateHost(String Hostid,String Hostname,String Username,String Password){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "hostname",
                    "username",
                    "password",
            };

            Liquid.LiquidValues = new String[] {
                    Hostname,
                    Username,
                    Password
            };
            whereClause = "id=?";
            whereArgs = new String[] { Hostid };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("ums_host",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
}
