package com.aseyel.tgbl.tristangaryleyesa.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class ExternalDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ExternalDatabaseHelper";
    File file = new File(Liquid.LiquidDBPath+Liquid.DATABASE_NAME );
    public ExternalDatabaseHelper(Context context) {
        super(context, Liquid.DATABASE_NAME, null, 1);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor SqliteSelectQuery(String query){
        try
        {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file,null);
            return db.rawQuery(query,null);

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return null;
        }
    }
}
