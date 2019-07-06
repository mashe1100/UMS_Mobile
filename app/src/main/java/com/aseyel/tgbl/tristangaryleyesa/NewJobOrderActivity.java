package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;

public class NewJobOrderActivity extends BaseFormActivity {
    private static final String TAG = NewJobOrderActivity.class.getSimpleName();
    EditText txtDetails;
    Spinner spinner_title;
    TextView txtQuestion;
    ArrayAdapter<CharSequence> adapter_title;
    String Title;
    String Details;
    String JobOrderId;
    String Date;
    String LastJobOrderIdSerial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job_order);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
    }

    private void setup(){
        txtDetails = (EditText) findViewById(R.id.txtDetails);
        spinner_title = (Spinner) findViewById(R.id.spinner_title);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        adapter_title = ArrayAdapter.createFromResource(this,R.array.joborder_title,android.R.layout.simple_spinner_item);
        adapter_title.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_title.setAdapter(adapter_title);
        txtQuestion.setText("New Job Order, Just fill up the required details.");
        spinner_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Title = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                boolean result = false;
                LastJobOrderIdSerial = String.format("%05d", GetLastJobOrderId() + 1);
                Details = txtDetails.getText().toString();
                Date = Liquid.currentDate();
                JobOrderId = Liquid.currentDateTimeForID()+"-"+LastJobOrderIdSerial;
                if(Title.equals("Select Title") || Title.equals("") || Details.equals("")){
                    Liquid.showDialogError(this,"Invalid","Incomplete Information!");
                }else{

                    result =  workModel.doSubmitNewJobOrder(
                            JobOrderId,
                            Liquid.Client,
                            Title,
                            Details,
                            Date
                    );

                    if (result == true) {
                        Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
                    }else{
                        Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int GetLastJobOrderId(){
        Cursor result = workModel.GetLastJobID();
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
