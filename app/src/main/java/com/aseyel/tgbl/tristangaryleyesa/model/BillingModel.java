package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidBilling;

public class BillingModel {
    public static final String TAG = LiquidBilling.class.getSimpleName();
    public static Cursor GetRates(String RateCode,String Classification, String BillingCycle) {
        return SplashActivity.mDatabaseHelper.SqliteSelectQuery(
                        "select a.*, b.R_Formula as R_Formula, b.R_Group as R_Group from rates a " +
                        "left join rates_description b " +
                        "on a.RD_ID = b.RD_ID " +
                        "where a.R_Type = '"+RateCode+"' and a.classification = '"+Classification+"' and rate_date_from like '%"+BillingCycle+"%' ");
    }
}
