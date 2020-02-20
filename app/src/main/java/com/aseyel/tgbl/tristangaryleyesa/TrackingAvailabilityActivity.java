package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import butterknife.BindView;

public class TrackingAvailabilityActivity extends BaseFormActivity {
    private static final String TAG = TrackingAvailabilityActivity.class.getSimpleName();
    @BindView(R.id.txttaPrice)
    EditText txttaPrice;
    @BindView(R.id.txttaComment)
    EditText txttaComment;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;
    String Price;
    String Comment;
    LiquidFile mLiquidFile;
    String Filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tracking_availability);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setup();
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }

    private void setup(){
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,Liquid.SelectedPeriod);
        txtQuestion.setText("What is the price of "+Liquid.SelectedDescription+"? Please leave any comment.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                boolean result = false;
                Price = txttaPrice.getText().toString();
                Comment = txttaComment.getText().toString();
                if (Price.equals("")) {
                    Liquid.showDialogError(this, "Invalid", "Information Incomplete!");
                    return false;
                }

                result =  TrackingModel.doSubmitAvailability(
                       Liquid.SelectedAccountNumber,
                       Liquid.SelectedCode,
                       "",
                       "",
                       "",
                       "",
                       Comment,
                       Price,
                        "",
                       Liquid.SelectedPeriod
               );

                if (result == true) {
                    Liquid.showDialogNext(this, "Valid", "Successfully Saved!");

                } else {
                    Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetData(String AccountNumber,
                        String ItemCode,
                        String Period){
        String Status = "";
        Cursor result = TrackingModel.GetAvailability(
                ItemCode,
                AccountNumber,
                Period);
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                Price = result.getString(0);
                Comment = result.getString(1);
            }
            txttaPrice.setText(Price);
            txttaComment.setText(Comment);
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }
}
