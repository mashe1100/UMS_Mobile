package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class SurveyActivity extends BaseFormActivity {
    private final String TAG = SurveyActivity.class.getSimpleName();
    private TextView txtQuestion;
    private EditText txtContact;
    private EditText txtEmail;
    private EditText txtMeterBrand;
    private EditText txtAddress;
    private EditText txtampirCapacity;
    private EditText txtType;
    private Button btnNext;
    private EditText[] mEditTextData;
    private Liquid mLiquid;
    //private Spinner spinnerMeterType;
    //private ArrayAdapter<String> AdapterSpinnerMeterType;
    //private List<String> ListOfMeterType;
    //private Spinner spinnerStructure;
    //private ArrayAdapter<String> AdapterSpinnerStructure;
    //private List<String> ListOfStructure;
    Spinner spinnerMeterType;
    ArrayAdapter<CharSequence> AdapterSpinnerMeterType;
    Spinner spinnerStructure;
    ArrayAdapter<CharSequence> AdapterSpinnerStructure;
    private TextView txtAccountNumber;
    private TextView txtAccountName;
    private TextView txtAccountAddress;
    private TextView txtMeterNumber;
    private TextView txtAccountSequence;
    private TextView txtAccountStatus;
    private TextView txtAccountType;
    private CardView CardConsumerInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_survey);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            init();
        }catch (Exception e){
            Log.e(TAG,"Tristan Leyesa",e);
        }
    }

    public void GetNewMeterDetails(String id,String MeterNumber){
        Cursor result = MeterNotInListModel.GetMeterNotInDetails(id,MeterNumber);
        int total = 0;

        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()) {
                txtMeterNumber.setText(result.getString(2)) ;
                txtAddress.setText(result.getString(4));
                txtampirCapacity.setText(result.getString(21));
                txtType.setText(result.getString(20));
                txtMeterBrand.setText(result.getString(22));
                txtEmail.setText(result.getString(23));
                txtContact.setText(result.getString(24));
                Liquid.SelectedMeterNumber = txtMeterNumber.getText().toString();
                MeterNumber = Liquid.SelectedMeterNumber;
            }
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }

    private void init(){
        mLiquid = new Liquid();
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtContact = (EditText) findViewById(R.id.txtContact);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtMeterBrand = (EditText) findViewById(R.id.txtMeterBrand);
        txtampirCapacity = (EditText) findViewById(R.id.txtampirCapacity);
        txtType = (EditText) findViewById(R.id.txtType);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        btnNext = (Button) findViewById(R.id.btnNext);
        txtQuestion.setText("Please put the needed information.");
        CardConsumerInformation = (CardView) findViewById(R.id.CardConsumerInformation);
        txtAccountNumber = (TextView) findViewById(R.id.txtAccountNumber);
        txtAccountName =(TextView) findViewById(R.id.txtAccountName);
        txtAccountAddress =(TextView) findViewById(R.id.txtAccountAddress);
        txtMeterNumber =(TextView) findViewById(R.id.txtMeterNumber);
        txtAccountSequence =(TextView) findViewById(R.id.txtAccountSequence);
        txtAccountStatus =(TextView) findViewById(R.id.txtAccountStatus);
        txtAccountType =(TextView) findViewById(R.id.txtAccountType);
        txtAccountNumber.setText("Account No. : ----");
        txtAccountName.setText("Name : ----");
        txtAccountAddress.setText("Address : ----");
        txtMeterNumber.setText("Meter No./Serial : ----");
        txtAccountSequence.setText("Seq. : ----");
        txtAccountStatus.setText("Status : ----");
        txtAccountType.setText("Type : ----");

        if(Liquid.HideKeyboard == 1){
            mEditTextData = new EditText[6];
            mEditTextData[0] = txtContact;
            mEditTextData[1] = txtEmail;
            mEditTextData[2] = txtMeterBrand;
            mEditTextData[3] = txtAddress;
            mEditTextData[4] = txtampirCapacity;
            mEditTextData[5] = txtType;
            mLiquid.hideSoftKeyboard(mEditTextData);
        }

        spinnerMeterType = (Spinner) findViewById(R.id.spinnerMeterType);
        spinnerStructure = (Spinner) findViewById(R.id.spinnerStructure);
        AdapterSpinnerMeterType = ArrayAdapter.createFromResource(this,R.array.meter_type,android.R.layout.simple_spinner_item);
        AdapterSpinnerMeterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeterType.setAdapter(AdapterSpinnerMeterType);
        AdapterSpinnerStructure = ArrayAdapter.createFromResource(this,R.array.structure,android.R.layout.simple_spinner_item);
        AdapterSpinnerStructure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStructure.setAdapter(AdapterSpinnerStructure);

        spinnerMeterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Liquid.MeterTypeValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStructure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Liquid.StructureValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
            }
        });
        Liquid.AccountNumber = Liquid.SelectedAccountNumber;
        GetReadAndBillData(Liquid.SelectedId, Liquid.AccountNumber);
        GetNewMeterDetails(Liquid.SelectedId,Liquid.MeterNumber);
    }

    public void GetReadAndBillData(String job_id, String AccountNumber) {
        try {
            Cursor result = workModel.GetReadAndBillJobOrderDetails(job_id, AccountNumber);

            if (result.getCount() == 0) {
                return;
            }
            while (result.moveToNext()) {
                //Customer Data
                HashMap<String, String> data = new HashMap<>();
                Liquid.ConsumerStatus = result.getString(9);
                Liquid.AccountNumber = result.getString(30);
                Liquid.AccountName = result.getString(17);
                Liquid.MeterNumber = result.getString(37);
                Liquid. AccountType = result.getString(7);
                Liquid.Complete_Address = result.getString(29);
                Liquid.Sequence = result.getString(35);
                Liquid.Date = result.getString(71);
                Liquid.ReadingDate = result.getString(54);
                String[] ReadingDateSplit = Liquid.ReadingDate.split("-");
                Liquid.JobId = result.getString(2);
                Liquid.route = result.getString(11);
                Liquid.itinerary = result.getString(13);
                Liquid.serial = result.getString(63);
                Liquid.previous_reading = !result.getString(47).equals("") ? result.getString(47) : "0";
                Liquid.previous_reading = Liquid.FixDecimal(Liquid.previous_reading);
                Liquid.previous_consumption = !result.getString(52).equals("") ? result.getString(52) : "0";
                Liquid.present_reading_date = Liquid.currentDate();
                Liquid.previous_reading_date = !result.getString(53).equals("") ? result.getString(53) : Liquid.ReadingDate;
                Liquid.classification = result.getString(8);
                Liquid.BillMonth = result.getString(66);
                Liquid.BillYear = result.getString(68);
                Liquid. BillDate = result.getString(67);
                Liquid.month = Liquid.BillMonth;
                Liquid.year = Liquid.BillYear;
                Liquid.day = ReadingDateSplit[1];
                Liquid.Averange_Consumption = !result.getString(46).equals("") ? result.getString(46) : "0";
                Liquid.multiplier = !result.getString(40).equals("") ? result.getString(40) : "1";
                Liquid.multiplier = Liquid.FixDecimal(Liquid.multiplier);
                Liquid.Meter_Box = result.getString(36);
                Liquid.code = result.getString(1);
                Liquid.rate_code = result.getString(7);
                Liquid.coreloss = !result.getString(44).equals("") ? result.getString(44) : "0";
                Liquid.meter_count = !result.getString(42).equals("") ? result.getString(42) : "1";
                Liquid.arrears = !result.getString(43).equals("") ? result.getString(43) : "0";
                Liquid.senior_tagging = result.getString(56);
                Liquid.eda_tagging = result.getString(70);
                Liquid.change_meter = !result.getString(58).equals("") ? result.getString(58) : "0";
                Liquid.interest = !result.getString(62).equals("") ? result.getString(62) : "0";

                switch (Liquid.Client){
                    case "ileco2":
                        Liquid.overdue = !result.getString(86).equals("") ? result.getString(86) : "0"; //inclusion
                        Liquid.surcharge = !result.getString(85).equals("") ? result.getString(85) : "0"; // ILP
                        break;
                    default:
                        Liquid.overdue = Liquid.arrears;
                        Liquid.surcharge = Liquid.interest;
                        break;
                }

                Liquid.ShareCap =  !result.getString(78).equals("") ? result.getString(78) : "0";
                Liquid.rentalfee = !result.getString(55).equals("") ? result.getString(55) : "0";
                Liquid.OtherBill = !result.getString(83).equals("") ? result.getString(83) : "0";
                Liquid.bill_number = !result.getString(65).equals("") ? result.getString(65) : Liquid.year + Liquid.BillMonth + AccountNumber;
                Liquid.rowid = result.getString(84);
                //Discon and DueDate
                Liquid.duedate = result.getString(64);

                if (Liquid.duedate.equals("")) {
                    Liquid.duedate = Liquid.getDueDate(Liquid.present_reading_date);
                }
                Liquid.discondate = Liquid.getDisconDate(Liquid.duedate);
                Liquid.present_reading_date = Liquid.currentDate();
                Liquid.BillingCycle = Liquid.year + "-" + Liquid.BillMonth;
                //Reading
            }

            txtAccountNumber.setText("Account No. : "+Liquid.AccountNumber);
            txtAccountName.setText("Name : "+Liquid.AccountName);
            txtAccountAddress.setText("Address : "+Liquid.Complete_Address);
            txtMeterNumber.setText("Meter No./Serial : "+Liquid.MeterNumber +"/"+Liquid.serial);
            txtAccountSequence.setText("Seq. : "+Liquid.Sequence);
            txtAccountStatus.setText("Status : "+Liquid.ConsumerStatus);
            txtAccountType.setText("Type : "+Liquid.AccountType);
            //txtName.setText(Liquid.AccountName);
            txtAddress.setText(Liquid.Complete_Address);
            //txtMeter.setText(Liquid.MeterNumber);
            //txtSeq.setText(Liquid.Sequence);

            if(Liquid.ConsumerStatus.equals("DISCD") || Liquid.ConsumerStatus.equals("WOFF")){
                CardConsumerInformation.setCardBackgroundColor(Color.RED);
            }else{
                CardConsumerInformation.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
            //MeterCount(Liquid.meter_count);
        } catch (Exception e) {
            Log.e(TAG, "Error : ", e);
            return;
        }
    }

    public void saveValue(){
        Liquid.ContactNumber  = txtContact.getText().toString();
        Liquid.EmailAdd = txtEmail.getText().toString();
        Liquid.MeterBrand = txtMeterBrand.getText().toString();
        Liquid.UpdatedAddress = txtAddress.getText().toString();
        Liquid.SurveyType = txtType.getText().toString();
        Liquid.SurveyAmpirCapacity = txtampirCapacity.getText().toString();
       // Liquid.UpdatedAccountName = txtName.getText().toString();
        //Liquid.UpdatedMeterNumber = txtMeter.getText().toString();
        //Liquid.UpdatedSequence = txtSeq.getText().toString();

        if (Liquid.MeterNumber.equals("") || Liquid.MeterNumber.replace(" ", "").equals("")){
            Liquid.showDialogError(this, "Invalid", "Meter Number is required!");
            return;
        }
        else{
            Intent i =  i = new Intent(SurveyActivity.this, ReadingGalleryActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;

                case R.id.action_form_submit:

                    saveValue();

                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }
}
