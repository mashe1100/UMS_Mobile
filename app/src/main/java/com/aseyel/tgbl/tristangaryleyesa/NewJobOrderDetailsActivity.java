package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;

public class NewJobOrderDetailsActivity extends BaseFormActivity {
    private static final String TAG = NewJobOrderDetailsActivity.class.getSimpleName();
    EditText txtJobOrderTitle;
    EditText txtJobOrderDescription;
    TextView txtQuestion;
    String JobOrderTitle;
    String JobOrderDescription;
    String Latitude;
    String Longitude;
    String Date;
    String AccountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job_order_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
    }

    private void setup(){
        txtJobOrderTitle = (EditText) findViewById(R.id.txtJobOrderTitle);
        txtJobOrderDescription = (EditText) findViewById(R.id.txtJobOrderDescription);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtQuestion.setText("Fill up the required details for creating Job Order Details.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                boolean result = false;
                Latitude = "0";
                Longitude = "0";
                JobOrderTitle = txtJobOrderTitle.getText().toString();
                JobOrderDescription = txtJobOrderDescription.getText().toString();
                Date = Liquid.currentDate();
                Log.i(TAG,Liquid.SelectedId + " " + Date);
                AccountNumber = String.valueOf(GetLastAccountNumberId());
                     result = workModel.doSubmitJobOrderDetails(
                             Liquid.SelectedId,
                             Liquid.Client,
                             AccountNumber,
                             JobOrderTitle,
                             JobOrderDescription,
                             Latitude,
                             Longitude,
                             Date
                     );

                    if (result == true) {
                        Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
                    }else{
                        Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
                    }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int GetLastAccountNumberId(){
        Cursor result = workModel.GetLastAccountNumber();
        int total = 0;
        try
        {
            if(result.getCount() == 0){
                return 1;
            }
            while(result.moveToNext()) {
                total = Integer.parseInt(result.getString(0));
            }
            return total;
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return 1;
        }
    }
}
