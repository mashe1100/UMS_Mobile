package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabMenuFragment;

public class ExternalDbModel {
    private static final String TAG = ExternalDbModel.class.getSimpleName();

    public static Cursor GetAllTable() {
        return TabMenuFragment.mExternalDatabaseHelper.SqliteSelectQuery(
                                "SELECT " +
                                "name " +
                                " FROM sqlite_master WHERE type='table';"
        );
    }

    public static Cursor GetColumns(String table) {
        return TabMenuFragment.mExternalDatabaseHelper.SqliteSelectQuery(
                "PRAGMA table_info("+table+");"
        );
    }

    public static Cursor GetData(String Columns){
        return TabMenuFragment.mExternalDatabaseHelper.SqliteSelectQuery(
                "SELECT * FROM "+Columns+""
        );
    }


}
