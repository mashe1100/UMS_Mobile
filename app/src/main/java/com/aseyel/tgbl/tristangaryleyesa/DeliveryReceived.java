package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class DeliveryReceived extends BaseFormActivity {
    private static final String TAG = DeliveryReceived.class.getSimpleName();

    private TextView txtQuestion;
    private EditText txtQuantity;
    private EditText txtTrackingNumber;
    private TextInputLayout iltTrackingNumber;
    @BindView(R.id.spinnerItemType)
    Spinner spinnerItemType;
    private ArrayAdapter<String> adapterItemType;

    //data parameters
    private String Category = "Delivery";
    private String stockInId = "";
    private String stockInTitle = "";
    private String ItemTypeValue = "";
    private String ItemTypeCode = "";
    private String ItemTypeDescription = "";
    private int ItemQuantity = 0;
    private String stockInDate = "";
    private String TimeStamp = "";
    private String userId = Liquid.User;
    private String modifiedBy = "";
    private String[] ItemTypeData;
    private String Client = Liquid.Client;

    private List<String> ListOfItemType;
    private EditText[] mEditTextData;
    private Liquid mLiquid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_received);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        initData();
    }

    private void initData(){
        stockInId = Liquid.currentDateTimeForID();
        stockInTitle = "Messengerial";

    }
    private void setup() {

        try{
            mLiquid = new Liquid();
            txtQuestion = (TextView) findViewById(R.id.txtQuestion);
            txtTrackingNumber = (EditText) findViewById(R.id.txtTrackingNumber);
            txtQuantity = (EditText) findViewById(R.id.txtQuantity);
            iltTrackingNumber = (TextInputLayout) findViewById(R.id.iltTrackingNumber);
            iltTrackingNumber.setVisibility(View.GONE);
            ListOfItemType = new ArrayList<String>();
            txtQuestion.setText("This form is for the reference of the delivery items of the courier.");

            //init getting the reference data
            getItemType();

            //hiding the keypad on screen
            if(Liquid.HideKeyboard == 1){
                mEditTextData = new EditText[1];
                mEditTextData[0] = txtTrackingNumber;
                mLiquid.hideSoftKeyboard(mEditTextData);
            }



        }catch(Exception e){
            Log.i(TAG,"Error : ",e);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        try{
            switch(item.getItemId()){

                case android.R.id.home:
                    this.finish();
                    return true;
                case R.id.action_form_submit:
                    saveReceived();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }

    }

    public void getItemType() {
        try {
            Cursor result = DeliveryModel.getItemType("");
            if (result.getCount() == 0) {
                return;
            }

            while (result.moveToNext()) {
                HashMap<String, String> data = new HashMap<>();
                ListOfItemType.add(result.getString(0) + "-" + result.getString(1));

            }

            adapterItemType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListOfItemType);
            adapterItemType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerItemType.setAdapter(adapterItemType);

            spinnerItemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ItemTypeValue = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {

            Log.e(TAG, "Error : ", e);
            return;
        }
    }
    public void saveReceived(){
        boolean result = false;
        ItemTypeData = ItemTypeValue.split("-");
        ItemTypeCode = ItemTypeData[0];
        ItemTypeDescription = ItemTypeData[1];
        ItemQuantity = Integer.parseInt(txtQuantity.getText().toString());
        stockInDate = Liquid.currentDate();
        TimeStamp = Liquid.currentDateTime();
        if(ItemQuantity == 0 && ItemQuantity < 0){
            Liquid.showDialogError(this, "Invalid", "Invalid Item Quantity");
            return;
        }
        result = DeliveryModel.doSubmitStockIn(
                                         Client,
                                         stockInId,
                                         stockInTitle,
                                         ItemTypeCode,
                                         ItemTypeDescription,
                                        String.valueOf(ItemQuantity),
                                        stockInDate,
                                        userId,
                                        TimeStamp,
                                        userId
                );


        if(result == true){
            Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
        }else{
            Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
        }

    }


}
