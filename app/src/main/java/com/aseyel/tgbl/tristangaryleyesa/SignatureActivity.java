package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidCanvas;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;

public class SignatureActivity extends BaseFormActivity {
    private static final String TAG = SignatureActivity.class.getSimpleName();
    @BindView(R.id.btnClear)
    ImageButton btnClear;
    private String SignatureName;
    private LinearLayout mContent;
    private View mView;
    private LiquidCanvas mSignature;
    private LiquidGPS mLiquidGPS;
    private String Latitude = "0";
    private String Longitude = "0";
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signature);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            init();
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:

                Save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void init(){
        Liquid.getDeviceLocation(this);
        mContent = (LinearLayout) findViewById(R.id.rlSignature);
        btnClear = (ImageButton) findViewById(R.id.btnClear);
        btnDone = (Button) findViewById(R.id.btnDone);
        mSignature = new LiquidCanvas(this, null,mContent);
        mContent.addView(mSignature);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clear();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Save();
            }
        });
    }

    private void Save(){
        boolean result = false;

        SignatureName = Liquid.TrackingNumber+"_"+Liquid.currentDateTimeForID();
        result = DeliveryModel.doSubmitDelivery(
                Liquid.Client,
                Liquid.SelectedId,
                "Messengerial",
                Liquid.TrackingNumber,
                Liquid.DeliveryItemTypeCode,
                Liquid.DeliveryItemTypeDescription,
                "Delivered",
                Liquid.RemarksCode,
                Liquid.Remarks,
                Liquid.ReaderComment,
                String.valueOf(Liquid.Latitude),
                String.valueOf(Liquid.Longitude),
                "Pending",
                SignatureName,
                "Pending",
                "100",
                Liquid.currentDateTime(),
                Liquid.currentDate(),
                Liquid.User

        );

        if(result == true){
            new GPSPosting().execute();
            mSignature.saveSubFolder(SignatureName);
            Liquid.showReadingDialogNext(this, "Valid", "Successfully Saved!");
        }else{
            Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
        }
    }

    public class GPSPosting extends AsyncTask<Void,Void,Void> {

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
                String type = "0";
                String latitude = "";
                String longitude = "";
                String remark = "";
                String comment = "";
                String accountnumber = "";
                String tag_desciption = "";
                String details = "";
                String Message = "";
                String job_id = "";
                SmsManager mSmsManager = SmsManager.getDefault();
                Cursor result = DeliveryModel.GetUntransferdData();

                if(result.getCount() == 0){
                    Message =
                            Liquid.User+","+
                                    Liquid.Client+","+
                                    type+","+
                                    Liquid.Latitude +","+
                                    Liquid.Longitude +","+
                                    remark+","+
                                    comment+","+
                                    accountnumber+","+
                                    tag_desciption+","+
                                    details;
                    mSmsManager.sendTextMessage(Liquid.ServerNumberSmart,null,Message,null,null);
                }
                else{
                    while (result.moveToNext()) {
                        type = "2";
                        latitude = result.getString(2);
                        longitude = result.getString(3);
                        remark = result.getString(4);
                        accountnumber = result.getString(1);
                        details = result.getString(5);
                        job_id = result.getString(0);
                        Message =
                                Liquid.User + "," +
                                        Liquid.Client + "," +
                                        type + "," +
                                        latitude + "," +
                                        longitude + "," +
                                        remark + "," +
                                        comment + "," +
                                        accountnumber + "," +
                                        tag_desciption + "," +
                                        details;
                        mSmsManager.sendTextMessage(Liquid.ServerNumberSmart, null, Message, null, null);
                        DeliveryModel.UpdateTransferStatus(job_id, accountnumber);
                    }
                }
                //"user,client,type,latitude,longitude,remark,comment,accountnumber,tag_desciption,details"
                Log.i(TAG,Message);
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
