package com.aseyel.tgbl.tristangaryleyesa.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";
    String smsSender = "";
    String smsBody = "";
    String timestamp = "";
    String id = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
                smsSender = smsMessage.getOriginatingAddress();
                timestamp = String.valueOf(smsMessage.getTimestampMillis());
                id = String.valueOf(smsMessage.getIndexOnIcc());
            }

            if (smsBody.startsWith(SmsHelper.SMS_CONDITION)) {
                Log.d(TAG, "Sms with condition detected");
                Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
            }
            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody + " Timestamp : "+ timestamp);
            new GPSPosting().execute();
        }
    }


    public class GPSPosting extends AsyncTask<Void,Void,Void> {

        Liquid.POSTApiData PostApi;
        String Client = "";
        String Details = "";
        boolean result = false;
        String return_data = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //865,iselco2,0,17.1415353166667,121.882277033333,,,,, (Structure)
            Client  = Liquid.Client;
            //Details = Build.SERIAL + "," + Liquid.Client + "," + "0" + "," + Latitude + ","+ Longitude + ",,,,,";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                HttpHandler sh = new HttpHandler();
                PostApi = new Liquid.POSTApiData("fmts/php/api/Realtime.php");


                    JSONObject dataObject = new JSONObject();

                    dataObject.put("receiver_group",id);
                    dataObject.put("senttime",Liquid.milliSecondtToDate(timestamp));
                    dataObject.put("contact",smsSender);
                    dataObject.put("details",smsBody);
                    dataObject.put("username",Liquid.Username);
                    dataObject.put("password",Liquid.Password);
                    dataObject.put("deviceserial", Build.SERIAL);
                    dataObject.put("sysid",Liquid.User);
                    Log.i(TAG, String.valueOf(dataObject));
                    String jsonStr = sh.makeServicePostCall(PostApi.API_Link,dataObject);
                    Log.i(TAG, String.valueOf(jsonStr));
                    return_data = jsonStr;
                    JSONObject response = Liquid.StringToJsonObject(jsonStr);
                    if (response.getString("result").equals("false")) {
                        result = false;

                    } else {
                        result = true;
                    }


                /*JSONObject dataObject = new JSONObject();
                dataObject.put("receiver_group","1");
                dataObject.put("senttime",Liquid.currentDateTime());
                //dataObject.put("contact",MyPhoneNumber);
                dataObject.put("details",Details);
                dataObject.put("username",Liquid.Username);
                dataObject.put("password",Liquid.Password);
                dataObject.put("deviceserial", Build.SERIAL);
                dataObject.put("sysid",Liquid.User);
                Log.i(TAG, String.valueOf(dataObject));
                String jsonStr = sh.makeServicePostCall(PostApi.API_Link,dataObject);
                return_data = jsonStr;

                JSONObject response = Liquid.StringToJsonObject(jsonStr);
                if (response.getString("result").equals("false")) {
                    result = false;

                } else {
                    result = true;
                }*/

            }catch(Exception e){
                Log.e(TAG,"Tristan Gary Leyesa : ",e);
                result = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(result){
                Log.i(TAG,return_data + "TRUE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }else{
                Log.i(TAG,return_data + "FALSE");
                //Liquid.showDialogInfo(GPSActivity.this,"Test",return_data);
            }
        }
    }
}
