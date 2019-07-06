package com.aseyel.tgbl.tristangaryleyesa.model;

import android.util.Log;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

/**
 * Created by Alex on 01/02/2019.
 */

public class SurveyModel {
    private static final String TAG = SurveyModel.class.getSimpleName();

    public static boolean Save(  String job_id,
                                 String client,
                                 String customer_meterno,
                                 String customer_name,
                                 String customer_address,
                                 String remarks,
                                 String  picture,
                                 String latitude,
                                 String longitude,
                                 String timestamp,
                                 String Reader_ID,
                                 String modifiedby,
                                 String route,
                                 String itinerary,
                                 String reading_date,
                                 String reading,
                                 String contactnumber,
                                 String emailadd,
                                 String meterbrand,
                                 String metertype,
                                 String structure


    ){
        try{

            boolean result = false;
            Liquid.LiquidColumns = Liquid.SurveyColumns;
            Liquid.LiquidValues = new String[] {
                    job_id,
                    client,
                    customer_meterno,
                    customer_name,
                    customer_address,
                    remarks,
                    picture,
                    latitude,
                    longitude,
                    timestamp,
                    Reader_ID,
                    modifiedby,
                    route,
                    itinerary,
                    reading_date,
                    reading,
                    contactnumber,
                    emailadd,
                    meterbrand,
                    metertype,
                    structure


            };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("meter_not_in_list",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }
}
