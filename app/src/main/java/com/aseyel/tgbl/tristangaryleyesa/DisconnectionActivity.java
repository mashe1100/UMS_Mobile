package com.aseyel.tgbl.tristangaryleyesa;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.DisconnectionModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisconnectionActivity extends BaseFormActivity {

    private static final String TAG = DisconnectionActivity.class.getSimpleName();
    private EditText[] mEditTextData;
    private TextView txtDetails;
    private EditText etxtReading;
    private EditText txtCollection;
    private Spinner spinnerRemarks;
    private EditText etxtComment;
    private FloatingActionButton btnGallery;
    private ImageButton btnCamera;
    private TextSwitcher tsImageCounter;
    private Switch switchtcdeDisconCollect;
    private Liquid mLiquid;
    private EditText txtOR;
    //Camera
    private static final int CAM_REQUEST = 1;
    private String Filename;
    private File[] listFile;
    private LiquidFile mLiquidFile;
    private File mFile;
    private String[] Subfolder;
    private ArrayList<Uri> mUri = new ArrayList<Uri>();
    private File mImages;


    //Data
    private static String AccountNumber;
    private String DisconDate;
    private int ImageCount = 0;
    private String AccountName = "";
    private String MeterNumber = "";
    private String Latitude = "";
    private String Longitude = "";
    private String JobOrderDate = "";
    private String Address = "";
    private String Remarks = "";
    private String LastReading = "";
    private String AmountDue = "";
    private String Arrears = "";
    private String TotalAmountDue = "";
    private String JobId = "";
    private String Client = "";
    private String ServiceType = "";
    private String MeterCount = "0";
    private String FinalReading = "";
    private LiquidGPS mLiquidGPS;
    private List<String> ListOfRemarks;
    private ArrayAdapter<String> AdapterSpinnerRemarks;
    private String RemarksValue = "";
    private String RemarksCode = "";
    private int focusEditText = 1;
    //Insert
    String job_id = "";
    String client = "";
    String id = "";
    String accountnumber = "";
    String name = "";
    String BA = "";
    String route = "";
    String itinerary = "";
    String tin = "";
    String meter_number = "";
    String meter_count = "";
    String serial = "";
    String previous_reading = "";
    String last_Reading = "";
    String previous_consumption = "";
    String last_consumption = "";
    String previous_reading_date = "";
    String disconnection_date = "";
    String month = "";
    String day = "";
    String year = "";
    String demand = "";
    String reactive = "";
    String powerfactor = "";
    String reading1 = "";
    String reading2 = "";
    String reading3 = "";
    String reading4 = "";
    String reading5 = "";
    String reading6 = "";
    String reading7 = "";
    String reading8 = "";
    String reading9 = "";
    String reading10 = "";
    String iwpowerfactor = "";
    String multiplier = "";
    String meter_box = "";
    String demand_reset = "";
    String remarks_code = "";
    String remarks_description = "";
    String remarks_reason = "";
    String comment = "";
    String meter_type = "";
    String meter_brand = "";
    String reader_id = "";
    String meter_reader = "";
    String disconnection_attempt = "0";
    String r_latitude = "";
    String r_longitude = "";
    String transfer_data_status = "Pending";
    String upload_status = "Pending";
    String status = "disconnection";
    String code = "";
    String area = "";
    String region = "";
    String country = "";
    String province = "";
    String city = "";
    String brgy = "";
    String country_label = "";
    String province_label = "";
    String region_label= "";
    String municipality_city_label = "";
    String loc_barangay_label = "";
    String street = "";
    String complete_address = "";
    String rate_code = "";
    String sequence = "";
    String disconnection_timestamp = "";
    String timestamp = "";
    String isdisconnected = "0";
    String ispayed = "0";
    String disconnection_signature = "";
    String recvby = "";
    String amountdue = "";
    String duedate = "";
    String cyclemonth = "";
    String cycleyear = "";
    String or_number = "";
    String arrears = "";
    String total_amount_due = "";
    String last_payment_date = "";
    String sysentrydate = "";
    String modifieddate = "";
    String modifiedby = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_disconnection);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            init();
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa ",e);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, String.valueOf(keyCode));
        switch(keyCode){
            case 67:
                DeleteReverse();
                break;
            case 22:
                new GetNextConsumer().execute(String.valueOf(keyCode));
                break;
            case 21:
                new GetNextConsumer().execute(String.valueOf(keyCode));
                break;
        }



        return super.onKeyDown(keyCode, event);
    }

    public void DeleteReverse(){
        Log.i(TAG, String.valueOf(focusEditText));
        try{
            switch (focusEditText){
                case 1:
                    etxtReading.setText(Liquid.removeFirstChar(etxtReading.getText().toString()));
                    break;

            }
        }catch (Exception e){
            Log.e(TAG,"Tristan Gary Leyesa ",e);
        }
    }
    private void init(){
          AccountNumber = Liquid.SelectedAccountNumber;
          JobId = Liquid.SelectedId;
          Subfolder = new String[1];
          Subfolder[0] = "";
          Client = Liquid.Client;
          ServiceType = Liquid.ServiceType;
          txtDetails = (TextView) findViewById(R.id.txtDetails);
          etxtReading = (EditText) findViewById(R.id.etxtReading);
          txtCollection = (EditText) findViewById(R.id.txtCollection);
          spinnerRemarks = (Spinner) findViewById(R.id.spinnerRemarks);
          etxtComment = (EditText) findViewById(R.id.etxtComment);
          btnGallery = (FloatingActionButton) findViewById(R.id.btnGallery);
          btnCamera = (ImageButton) findViewById(R.id.btnCamera);
          tsImageCounter = (TextSwitcher) findViewById(R.id.tsImageCounter);
          switchtcdeDisconCollect = (Switch) findViewById(R.id.switchtcdeDisconCollect);
          txtOR = (EditText) findViewById(R.id.txtOR);
          ListOfRemarks = new ArrayList<String>();
          mLiquid = new Liquid();
          mLiquidFile = new LiquidFile(this);
          mLiquidGPS = new LiquidGPS(this);
          txtCollection.setVisibility(View.GONE);
          etxtReading.setVisibility(View.GONE);
          txtOR.setVisibility(View.GONE);
            total_amount_due = "";
            status = "disconnection";
            etxtReading.setVisibility(View.VISIBLE);
            txtCollection.setVisibility(View.GONE);
            isdisconnected = "1";
            ispayed = "0";


          btnCamera.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  try {

                      Filename = Liquid.RemoveSpecialCharacter(AccountNumber) + "_" + Liquid.RemoveSpecialCharacter(DisconDate) + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                      mFile = mLiquidFile.Directory(JobId,Liquid.RemoveSpecialCharacter(Filename), Subfolder);
                      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                      startActivityForResult(intent, CAM_REQUEST);

                  } catch (Exception e) {
                     Log.e(TAG,"Tristan Gary Leyesa ",e);
                  }
              }
          });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Liquid.SelectedCategory = "disconnection";
                Liquid.ReadingDate = DisconDate;
                Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(i);
            }
        });

        switchtcdeDisconCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchtcdeDisconCollect.isChecked()){
                    txtCollection.setVisibility(View.VISIBLE);
                    txtOR.setVisibility(View.VISIBLE);
                    etxtReading.setVisibility(View.GONE);
                    etxtReading.setText("");
                    LastReading = "";
                    status = "collection";
                    isdisconnected = "0";
                    ispayed = "1";
                }else{
                    etxtReading.setVisibility(View.VISIBLE);
                    txtCollection.setVisibility(View.GONE);
                    txtOR.setVisibility(View.GONE);
                    txtOR.setText("");
                    txtCollection.setText("");
                    total_amount_due = "";
                    or_number = "";
                    status = "disconnection";
                    isdisconnected = "1";
                    ispayed = "0";
                }
            }
        });
        GetDisconnectionRemarks();
        ConsumerDetails(JobId,AccountNumber);
        DisconenctedDetails(JobId,AccountNumber);
        GetImages(JobId);
        etxtReading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Liquid.ReverseInput == 1){
                    etxtReading.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if(Liquid.HideKeyboard == 1){
            mEditTextData = new EditText[4];
            mEditTextData[0] = etxtReading;
            mEditTextData[1] = txtOR;
            mEditTextData[2] = txtCollection;
            mEditTextData[3] = etxtComment;
            mLiquid.hideSoftKeyboard(mEditTextData);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;

                    result = ReadingModel.doSubmitPicture(Client,
                            AccountNumber,
                            ServiceType,
                            Filename,
                            Liquid.currentDateTime(),
                            Liquid.currentDateTime(),
                            Liquid.User,
                            Liquid.User,
                            DisconDate
                    );

                    if (result == true) {

                        Liquid.resizeImage(mFile.getAbsolutePath(), 0.80, 0.80);
                        Liquid.ShowMessage(getApplicationContext(), "Save Image Success");
                        mUri.add(Uri.fromFile(mFile));
                        tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                        ImageCount = mUri.size();

                    }

                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
        }

    }

    public void doDisconnection(){
        boolean result = false;
        disconnection_attempt = String.valueOf(Integer.parseInt(disconnection_attempt) + 1);
        FinalReading = etxtReading.getText().toString();
        disconnection_timestamp = Liquid.currentDateTime();
        timestamp = Liquid.currentDateTime();
        sysentrydate = Liquid.currentDateTime();
        modifieddate = Liquid.currentDateTime();
        comment = etxtComment.getText().toString();
        modifiedby = Liquid.User;
        r_latitude = String.valueOf(mLiquidGPS.getLatitude());
        r_longitude = String.valueOf(mLiquidGPS.getLongitude());
        remarks_reason = etxtComment.getText().toString();
        reader_id = modifiedby;
        meter_reader = Liquid.User;
        reading1 = FinalReading;
        String[] RemarksData = RemarksValue.split("-");
        RemarksCode = RemarksData[0];
        Remarks = RemarksData[1];
        or_number = txtOR.getText().toString();
        total_amount_due = txtCollection.getText().toString();
        if(ImageCount == 0){
            Liquid.showDialogError(DisconnectionActivity.this, "Invalid", "Please take a picture!");
            return;
        }


        result = DisconnectionModel.doDisconnection(
                JobId,//ok
                Client,//ok
                AccountNumber,//ok
                AccountNumber,//ok
                AccountName,//ok
                BA,//ok
                route,//ok
                itinerary,//ok
                tin,//ok
                MeterNumber, //ok
                MeterCount, //ok
                serial, //ok
                LastReading, //ok
                FinalReading, //ok
                previous_consumption, //ok
                last_consumption, // ok
                previous_reading_date, //ok
                disconnection_date, //ok
                month, //ok
                day, //ok
                year, //ok
                demand, //ok
                reactive, //ok
                powerfactor, //ok
                reading1, //ok
                reading2, //ok
                reading3, //ok
                reading4, //ok
                reading5, //ok
                reading6, //ok
                reading7, //ok
                reading8, //ok
                reading9, //ok
                reading10, //ok
                iwpowerfactor, //ok
                multiplier, //ok
                meter_box, //ok
                demand_reset, //ok
                RemarksCode, //ok
                Remarks, //ok
                remarks_reason, //ok
                comment, //ok
                meter_type, //ok
                meter_brand, //ok
                reader_id, //ok
                meter_reader,
                disconnection_attempt,
                r_latitude, //ok
                r_longitude, //ok
                transfer_data_status, //ok
                upload_status, //ok
                status, //ok
                code, //ok
                area, //ok
                region, //ok
                country, //ok
                province, //ok
                city, //ok
                brgy, //ok
                country_label, //ok
                province_label, //ok
                region_label, //ok
                municipality_city_label, //ok
                loc_barangay_label, //ok
                street, //ok
                Address, //ok
                rate_code, //ok
                sequence, //ok
                disconnection_timestamp, //ok
                timestamp, //ok
                isdisconnected, //ok
                ispayed, //ok
                disconnection_signature, //ok
                recvby, //ok
                AmountDue, //ok
                duedate, //ok
                cyclemonth, //ok
                cycleyear, //ok
                or_number, //ok
                arrears, //ok
                total_amount_due, //ok
                last_payment_date, //ok
                sysentrydate, //ok
                modifieddate, //ok
                modifiedby); //ok


        if (result) {
            Liquid.showDialogNext(DisconnectionActivity.this, "Valid", "Successfully Saved!");
        } else {
            Liquid.showDialogError(DisconnectionActivity.this, "Invalid", "Unsuccessfully Saved!");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try{

            switch (item.getItemId()){
                case android.R.id.home:
                    this.finish();
                    return true;
                case R.id.action_form_submit:
                    Log.i(TAG,"What the hell?");
                    doDisconnection();
                    return true;
            }
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa : ",e);
        }

        Log.i(TAG,"weee");
        return super.onOptionsItemSelected(item);
    }

    private void GetImages(String JobId) {
        mUri.clear();
        mImages = Liquid.getDiscPicture(JobId,Subfolder);
        if (!mImages.exists() && !mImages.mkdirs()) {

            Liquid.ShowMessage(this, "Can't create directory to save image");
        } else {
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for (int a = 0; a < listFile.length; a++) {
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if (seperated[0].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedAccountNumber)) && seperated[1].equals(Liquid.RemoveSpecialCharacter(DisconDate))) {
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }
            //ImageCount = mUri.size();
            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));

        }
    }

    private void ConsumerDetails(String JobID,String AccountNumber){
        Cursor result = workModel.GetDisconnectionDownload(JobID,AccountNumber);
        String Details = "";
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){

                 AccountName = result.getString(10);
                 MeterNumber = result.getString(36);
                 DisconDate = result.getString(42);
                 Latitude = result.getString(31);
                 Longitude = result.getString(32);
                 JobOrderDate = result.getString(52);
                 Address = result.getString(29);
                 Remarks = result.getString(48);
                 LastReading = result.getString(39);
                 AmountDue = result.getString(43);
                 Arrears = result.getString(49);
                 TotalAmountDue = result.getString(50);
                 MeterCount= result.getString(38);
                 previous_consumption = result.getString(40);
                 BA = result.getString(12);
                 route = result.getString(13);
                 itinerary = result.getString(15);
                 tin = result.getString(11);
                 serial = result.getString(37);
                 previous_reading_date   = result.getString(41);
                 disconnection_date   = result.getString(42);
                 multiplier = result.getString(33);
                 meter_type = result.getString(34);
                 meter_brand = result.getString(35);
                 code = result.getString(1);
                 sequence = result.getString(32);
                 //or_number = result.getString(47);
                 arrears = result.getString(49);
                 total_amount_due = result.getString(50);
                 last_payment_date = result.getString(51);
                 duedate = result.getString(44);
                 cyclemonth = result.getString(45);
                 cycleyear = result.getString(46);


                Details = "Account Number : "+AccountNumber+"\n"+
                        "Name : "+AccountName+"\n"+
                        "Address : "+Address+"\n"+
                        "Remarks : "+Remarks+"\n"+
                        "Last Reading : "+LastReading+"\n"+
                        "Amount Due : "+AmountDue+"\n"+
                        "Arrears : P "+Arrears+"\n"+
                        "Total Amount Due : P "+TotalAmountDue+"\n"+
                        "Discon Date : "+DisconDate;

            }
                txtDetails.setText(Details);


            return;

        }
        catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa says ",e);
            return ;
        }

    }

    private void DisconenctedDetails(String JobID,String AccountNumber){
        Cursor result = DisconnectionModel.GetDisconnected(JobID,AccountNumber);
        try
        {

            if(result.getCount() == 0){
                FinalReading = "";
                disconnection_attempt = "0";
                total_amount_due = "";
                remarks_code = "";
                remarks_description = "";
                remarks_reason = "";
                comment = "";
                status = "";
                isdisconnected  = "";
                ispayed = "";
                RemarksValue = "0-NO FIELD FINDINGS";
                spinnerRemarks.setSelection(AdapterSpinnerRemarks.getPosition(RemarksValue));
                or_number = "";
                return;
            }
            while(result.moveToNext()){

                FinalReading = result.getString(13);
                disconnection_attempt = result.getString(46);
                total_amount_due = result.getString(80);
                remarks_code = result.getString(38);
                remarks_description = result.getString(39);
                remarks_reason = result.getString(40);
                comment = result.getString(41);
                status = result.getString(51);
                isdisconnected  = result.getString(70);
                ispayed = result.getString(71);
                RemarksValue = result.getString(38) + "-" + result.getString(39);
                spinnerRemarks.setSelection(AdapterSpinnerRemarks.getPosition(RemarksValue));
                or_number = result.getString(78);

                if(disconnection_attempt.equals("")){
                    disconnection_attempt = "0";
                }
            }



            etxtComment.setText(comment);
            switch(status){
                case "collection":
                    txtOR.setText(or_number);
                    txtCollection.setText(total_amount_due);
                    switchtcdeDisconCollect.setChecked(true);
                    txtCollection.setVisibility(View.VISIBLE);
                    break;
                case "disconnection":
                    switchtcdeDisconCollect.setChecked(false);
                    etxtReading.setVisibility(View.VISIBLE);
                    etxtReading.setText(FinalReading);
                    break;
                    default:
                        etxtReading.setText(FinalReading);
                        switchtcdeDisconCollect.setChecked(false);
                        etxtReading.setVisibility(View.VISIBLE);
            }

            return;

        }
        catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa says ",e);
            return ;
        }

    }

    public void GetDisconnectionRemarks(){
        try {
            Cursor result = workModel.GetDisconnectionRemarks();
            if (result.getCount() == 0) {
                return;
            }

            while (result.moveToNext()) {
                HashMap<String, String> data = new HashMap<>();
                ListOfRemarks.add(result.getString(0) + "-" + result.getString(1));

            }

            AdapterSpinnerRemarks = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListOfRemarks);
            AdapterSpinnerRemarks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRemarks.setAdapter(AdapterSpinnerRemarks);

            spinnerRemarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    RemarksValue = parent.getItemAtPosition(position).toString();

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

    public class GetNextConsumer extends AsyncTask<String,Void,Void> {
        boolean result = false;
        Cursor result_data = null;
        String AccountNumber = DisconnectionActivity.AccountNumber;
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DisconnectionActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                switch(Integer.parseInt(params[0])){
                    case 22:

                        result_data =  DisconnectionModel.get_next_customer_sequence(Liquid.SelectedId, Liquid.rowid);
                        break;
                    case 21:
                        result_data = DisconnectionModel.get_prev_customer_sequence(Liquid.SelectedId, Liquid.rowid);
                        break;
                }
                if (result_data.getCount() == 0) {
                    return null;
                }

                while (result_data.moveToNext()) {
                    //Customer Data
                    //HashMap<String, String> data = new HashMap<>();
                    AccountNumber = result_data.getString(1);
                    Liquid.rowid = result_data.getString(2);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ConsumerDetails(Liquid.SelectedId, AccountNumber);
            DisconenctedDetails(Liquid.SelectedId, AccountNumber);
            GetImages(Liquid.SelectedId);
            pDialog.dismiss();
        }
    }
}
