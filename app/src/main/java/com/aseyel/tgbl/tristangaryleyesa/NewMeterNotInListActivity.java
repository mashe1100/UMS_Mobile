package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewMeterNotInListActivity extends BaseFormActivity {
    private final String TAG = NewMeterNotInListActivity.class.getSimpleName();
    private FloatingActionButton btnGallery;
    private TextView txtQuestion;
    private EditText[] mEditTextData;
    private EditText txtampirCapacity;
    private EditText txtType;
    private EditText txtDemand;
    private EditText txtMeterNumber;
    private EditText txtSerialNumber;
    private EditText txtCustomerName;
    private EditText txtAddress;
    private EditText txtReading;
    private EditText txtNearMeter;
    private EditText txtNearSequence;
    private EditText txtComment;
    private EditText txtContact;
    private EditText txtEmail;
    private EditText txtMeterBrand;
    private ImageButton btnCamera;
    private Spinner spinnerRemarks;
    private ArrayAdapter<String> AdapterSpinnerRemarks;
    private List<String> ListOfRemarks;
    Spinner spinnerMeterType;
    ArrayAdapter<CharSequence> AdapterSpinnerMeterType;
    Spinner spinnerStructure;
    ArrayAdapter<CharSequence> AdapterSpinnerStructure;
    private String RemarksValue = "";
    private String MeterNumber  = "";
    private String SerialNumber  = "";
    private String CustomerName  = "";
    private String Address  = "";
    private String Reading  = "";
    private String NearMeter  = "";
    private String NearSequence  = "";
    private String Comment  = "";
    private String ContactNumber = "";
    private String EmailAdd = "";
    private String MeterBrand = "";
    private String MeterTypeValue = "";
    private String StructureValue = "";
    private String Demand = "";
    private String ampirCapacity = "";
    private String Type = "";
    private Liquid mLiquid;
    private LiquidGPS mLiquidGPS;
    //Camera
    private static final int CAM_REQUEST = 1;
    private String Filename;
    private File[] listFile;
    private LiquidFile mLiquidFile;
    private File mFile;
    private  String[] Subfolder;
    private ArrayList<Uri> mUri = new ArrayList<Uri>();
    private File mImages;
    private TextSwitcher tsImageCounter;
    int ImageCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meter_not_in_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        Subfolder = new String[1];
        Subfolder[0] = "";
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnGallery = (FloatingActionButton) findViewById(R.id.btnGallery);
        txtMeterNumber = (EditText) findViewById(R.id.txtMeterNumber) ;
        txtSerialNumber = (EditText) findViewById(R.id.txtSerialNumber) ;
        txtCustomerName = (EditText) findViewById(R.id.txtCustomerName);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtDemand = (EditText) findViewById(R.id.txtDemand) ;
        txtampirCapacity = (EditText) findViewById(R.id.txtampirCapacity);
        txtType = (EditText) findViewById(R.id.txtType);
        txtReading = (EditText) findViewById(R.id.txtReading);
        txtNearMeter = (EditText) findViewById(R.id.txtNearMeter);
        txtNearSequence = (EditText) findViewById(R.id.txtNearSequence);
        txtComment = (EditText) findViewById(R.id.txtComment);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtContact = (EditText) findViewById(R.id.txtContact);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtMeterBrand = (EditText) findViewById(R.id.txtMeterBrand);
        spinnerRemarks = (Spinner) findViewById(R.id.spinnerRemarks);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        tsImageCounter = (TextSwitcher) findViewById(R.id.tsImageCounter);
        mLiquidFile = new LiquidFile(this);
        ListOfRemarks = new ArrayList<String>();
        mLiquidGPS = new LiquidGPS(this);
        mLiquid = new Liquid();
        GetNewMeterDetails(Liquid.SelectedId,Liquid.SelectedMeterNumber);
        GetReadingRemarks();
        GetImages(Liquid.SelectedId);
        txtQuestion.setText("Please put the needed information for the meter not include on the list.");
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
                MeterTypeValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStructure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StructureValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    MeterNumber = txtMeterNumber.getText().toString();
                    if(MeterNumber.equals("")){
                        Liquid.showDialogError(NewMeterNotInListActivity.this,"Invalid","Please put the Meter Number first!");
                        return;
                    }

                    Filename = Liquid.RemoveSpecialCharacter(MeterNumber) + "_"+"audit"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + Liquid.User + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                    mFile = mLiquidFile.Directory(Liquid.SelectedId+"_audit",Liquid.RemoveSpecialCharacter(Filename), Subfolder);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    startActivityForResult(intent, CAM_REQUEST);
                } catch (Exception e) {
                    Liquid.ShowMessage(getApplicationContext(), e.toString());
                }
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        if(Liquid.HideKeyboard == 1){
            mEditTextData = new EditText[14];
            mEditTextData[0] = txtMeterNumber;
            mEditTextData[1] = txtSerialNumber;
            mEditTextData[2] = txtCustomerName;
            mEditTextData[3] = txtAddress;
            mEditTextData[4] = txtReading;
            mEditTextData[5] = txtNearMeter;
            mEditTextData[6] = txtNearSequence;
            mEditTextData[7] = txtComment;
            mEditTextData[8] = txtContact;
            mEditTextData[9] = txtEmail;
            mEditTextData[10] = txtMeterBrand;
            mEditTextData[11] = txtDemand;
            mEditTextData[12] = txtampirCapacity;
            mEditTextData[13] = txtType;
            mLiquid.hideSoftKeyboard(mEditTextData);
        }
    }

    public void OpenGallery(){
        Liquid.SelectedCategory = "new_meter";
        Intent i = new Intent(NewMeterNotInListActivity.this, GalleryActivity.class);
        startActivity(i);
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
                txtSerialNumber.setText(result.getString(25)) ;
                txtCustomerName.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtReading.setText(result.getString(17));
                txtNearMeter.setText(result.getString(15));
                txtNearSequence.setText(result.getString(16));
                txtDemand.setText(result.getString(19));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;

                    result = ReadingModel.doSubmitPicture(Liquid.Client,
                            MeterNumber,
                            "audit",
                            Filename,
                            Liquid.currentDateTime(),
                            Liquid.currentDateTime(),
                            Liquid.User,
                            Liquid.User,
                            Liquid.ReadingDate
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

    public void GetReadingRemarks() {
        try {
            Cursor result = workModel.GetReadingRemarks("");
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

    public void Save(){
          boolean result = false;
          MeterNumber  = txtMeterNumber.getText().toString();
          SerialNumber  = txtSerialNumber.getText().toString();
          CustomerName  = txtCustomerName.getText().toString();
          Address  = txtAddress.getText().toString();
          Reading  = txtReading.getText().toString();
          NearMeter  = txtNearMeter.getText().toString();
          NearSequence  = txtNearSequence.getText().toString();
          Comment  = txtComment.getText().toString();
          ContactNumber  = txtContact.getText().toString();
          EmailAdd  = txtEmail.getText().toString();
          MeterBrand  = txtMeterBrand.getText().toString();
          Demand = txtDemand.getText().toString();
          ampirCapacity = txtampirCapacity.getText().toString();
          Type = txtType.getText().toString();
          Liquid.SelectedMeterNumber = MeterNumber;

            if(MeterNumber.equals("")){
                Liquid.showDialogError(this, "Invalid", "Incomplete Information!");
                return;
            }
            if (ImageCount == 0) {
                Liquid.showDialogError(this, "Invalid", "Please take picture!");
                return;
            }
            if(Liquid.SelectedCode.equals("")){
                result = MeterNotInListModel.Save(
                        Liquid.SelectedId,
                        Liquid.Client,
                        MeterNumber,
                        CustomerName,
                        Address,
                        RemarksValue,
                        Demand,
                        Type,
                        ampirCapacity,
                        Filename,
                        String.valueOf(mLiquidGPS.getLatitude()),
                        String.valueOf(mLiquidGPS.getLongitude()),
                        Liquid.printlatitude,
                        Liquid.printlongitude,
                        Liquid.currentDateTime(),
                        Liquid.User,
                        Liquid.User,
                        Liquid.route,
                        Liquid.itinerary,
                        Liquid.ReadingDate,
                        NearMeter,
                        NearSequence,
                        Reading,
                        ContactNumber,
                        EmailAdd,
                        MeterBrand,
                        MeterTypeValue,
                        StructureValue,
                        "",
                        SerialNumber);

            }else{
                result = MeterNotInListModel.Update(  Liquid.SelectedCode,
                        Liquid.SelectedId,
                        Liquid.Client,
                        MeterNumber,
                        CustomerName,
                        Address,
                        RemarksValue,
                        Demand,
                        Type,
                        ampirCapacity,
                        Filename,
                        String.valueOf(mLiquidGPS.getLatitude()),
                        String.valueOf(mLiquidGPS.getLongitude()),
                        Liquid.printlatitude,
                        Liquid.printlongitude,
                        Liquid.currentDateTime(),
                        Liquid.User,
                        Liquid.User,
                        Liquid.route,
                        Liquid.itinerary,
                        Liquid.ReadingDate,
                        NearMeter,
                        NearSequence,
                        Reading,
                        ContactNumber,
                        EmailAdd,
                        MeterBrand,
                        MeterTypeValue,
                        StructureValue,
                        "",
                        SerialNumber
                        );
            }

        if (result == true) {
            Liquid.showDialogNext(this, "Valid", "Successfully Saved!");

        } else {
            Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
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

                    Save();

                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }

    private void GetImages(String JobId) {
        mUri.clear();

        mImages = Liquid.getDiscPicture(JobId +"_audit",Subfolder);
        if (!mImages.exists() && !mImages.mkdirs()) {

            Liquid.ShowMessage(this, "Can't create directory to save image");
        } else {
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for (int a = 0; a < listFile.length; a++) {
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if (seperated[0].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedMeterNumber)) && seperated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.ReadingDate))) {
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }
            //ImageCount = mUri.size();
            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
        }
    }
}
