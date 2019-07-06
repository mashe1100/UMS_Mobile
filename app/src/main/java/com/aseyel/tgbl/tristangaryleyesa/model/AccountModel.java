package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;
import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.LoginActivity;
import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

/**
 * Created by Romeo on 2018-03-03.
 */

public class AccountModel {
    private static final String TAG = AccountModel.class.getSimpleName();

    public static Cursor GetAccount(String Username) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                "Username, " + //0
                "Password "+ //1
                "FROM ums_account " +
                "WHERE Username = '" + Username + "' "
        );
    }

    public static Cursor GetLoginAccount() {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                "SELECT " +
                        "Username, " + //0
                        "Password, "+ //1
                        "name "+ //2
                        "FROM ums_account " +
                        "WHERE Status = 'Login' "
        );
    }


    public static boolean DoUpdateAccountAutoLogin(
            String Username
    ){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "Status",
            };
            Liquid.LiquidValues = new String[] {
                    "Login",
            };
            whereClause = "Username=?";
            whereArgs = new String[] { Username };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("ums_account",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean DoUpdatePassword(String username,String password){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "Password",
            };
            Liquid.LiquidValues = new String[] {
                    password,
            };
            whereClause = "Username=?";
            whereArgs = new String[] { username };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("ums_account",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean DoUpdateAccountToOut(){
        try{
            boolean result = false;
            String whereClause = "";
            String[] whereArgs = {};

            Liquid.LiquidColumns = new String[]{
                    "Status",
            };
            Liquid.LiquidValues = new String[] {
                    "Logout",
            };
            whereClause = "Status=?";
            whereArgs = new String[] { "Login" };
            result = SplashActivity.mDatabaseHelper.SqliteUpdateQuery("ums_account",Liquid.LiquidColumns,Liquid.LiquidValues,whereClause,whereArgs);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public static boolean doSubmitAccountDetails(String UserID,
                                                String username,
                                              String password,
                                              String client,
                                              String branch,
                                              String name,
                                              String lastname,
                                                 String firstname,
                                                 String middlename,
                                                 String position

    ){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.UMSAccounts;
            Liquid.LiquidValues = new String[]{
                    UserID,
                    username,
                    password,
                    client,
                    branch,
                    name,
                    lastname,
                    firstname,
                    middlename,
                    position
            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("ums_account",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }


    }

}
