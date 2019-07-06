package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.database.Cursor;
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
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;


public class AuditTravelRideActivity extends BaseFormActivity {
    private static final String TAG = AuditTravelRideActivity.class.getSimpleName();
    ArrayAdapter<CharSequence> adapter_vehicle;
    EditText txtFare;
    EditText txtComment;
    TextView txtQuestion;
    Spinner spinner_vehicle;
    String Fare;
    String Comment;
    String Vehicle;
    String JobOrderId;
    String Client = Liquid.Client;
    String AccountNumber =  Liquid.SelectedAccountNumber;
    String ServiceType = Liquid.ServiceType;
    String Latitude;
    String Longitude;
    String JobOrderDate;
    LiquidGPS mLiquidGPS;

    //Camera
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    FloatingActionButton btnGallery;
    static final int CAM_REQUEST = 1;
    String Filename = "";
    LiquidFile mLiquidFile;
    File mFile;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    String[] Subfolder;
    int ImageCount = 0;
    File mImages;
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_travel_ride);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();

    }

    private void setup(){
        Subfolder = new String[1];
        Subfolder[0] = "";
        txtComment = (EditText) findViewById(R.id.txtComment);
        txtFare = (EditText) findViewById(R.id.txtFare);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnGallery = (FloatingActionButton) findViewById(R.id.btnGallery);
        spinner_vehicle = (Spinner) findViewById(R.id.spinner_vehicle);
        adapter_vehicle = ArrayAdapter.createFromResource(this,R.array.vehicle,android.R.layout.simple_spinner_item);
        adapter_vehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vehicle.setAdapter(adapter_vehicle);
        txtQuestion.setText("Fill up the require field. Select the right vehicle and fare. Please put in the comment what is the location.");
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        mLiquidFile = new LiquidFile(this);
        spinner_vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vehicle = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(!Liquid.SelectedCode.equals("")){
            GetData(true, Liquid.SelectedId,Liquid.SelectedAccountNumber,Liquid.SelectedCode,"");
        }

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (Vehicle.equals("Select Vehicle") || txtFare.getText().toString().equals(""))  {
                        Liquid.showDialogError(AuditTravelRideActivity.this, "Invalid", "Please answer the questions before taking a image!");
                        return;
                    }
                    Filename = Liquid.RemoveSpecialCharacter(Liquid.SelectedId)+"_"+Liquid.RemoveSpecialCharacter(Liquid.Client)+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedAccountNumber) +"_"+Liquid.SelectedCode+"_"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
                    mFile = mLiquidFile.Directory(Liquid.SelectedAccountNumber,Liquid.RemoveSpecialCharacter(Filename),Subfolder);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));

                    startActivityForResult(intent,CAM_REQUEST);

                }
                catch (Exception e){
                    Log.e(TAG,"Error : ",e);
                    Liquid.ShowMessage(getApplicationContext(),e.toString());
                }
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Liquid.SelectedCategory = "Audit";
                Intent i = new Intent(AuditTravelRideActivity.this, GalleryActivity.class);
                startActivity(i);
            }
        });
        GetImages();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;
                    result = AuditModel.doSubmitPicture(Client,
                            AccountNumber,
                            ServiceType,
                            Filename,
                            Liquid.currentDateTime(),
                            Liquid.currentDateTime(),
                            Liquid.User,
                            Liquid.User,
                            Liquid.currentDate()
                    );



                    if(result == true){
                        Liquid.resizeImage(mFile.getAbsolutePath(),0.80,0.80);
                        Liquid.ShowMessage(getApplicationContext(),"Save Image Success");
                        mUri.add(Uri.fromFile(mFile));
                        tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                        ImageCount = mUri.size();
                        GetImages();
                    }

                }
            }

        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                boolean result = false;
                if(!Liquid.CheckGPS(this)){
                    Liquid.showDialogInfo(this,"Invalid","Please enable GPS!");
                }
                mLiquidGPS = new LiquidGPS(this);
                Fare = txtFare.getText().toString();
                Comment = txtComment.getText().toString();
                JobOrderId = Liquid.SelectedId;
                Client = Liquid.Client;
                AccountNumber = Liquid.SelectedAccountNumber;
                Latitude = String.valueOf(Liquid.Latitude);
                Longitude = String.valueOf(Liquid.Longitude);
                JobOrderDate = Liquid.SelectedJobOrderDate;
                if(Vehicle.equals("Select Vehicle")){
                    Liquid.showDialogError(this, "Invalid", "Incomplete Information!");
                    return false;
                }
                if(!Vehicle.equals("Select Vehicle") && Fare == ""){
                    Liquid.showDialogError(this, "Invalid", "Incomplete Information!");
                    return false;
                }
                /*if(ImageCount == 0){
                    Liquid.showDialogError(this, "Invalid", "Please take a picture!");
                    return false;
                }*/



                if(Liquid.SelectedCode.equals("")){
                    result = AuditModel.doSubmitAuditTravelRide(
                            JobOrderId,
                            Client,
                            AccountNumber,
                            Vehicle,
                            Fare,
                            Comment,
                            Latitude,
                            Longitude,
                            JobOrderDate,
                            Liquid.Status
                    );
                }else{
                    result = AuditModel.doSubmitUpdateAuditTravelRide(
                            Liquid.SelectedCode,
                            Vehicle,
                            Fare,
                            Comment

                    );
                }

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

    public void GetData(boolean animated,String JobOrderId,String AccountNumber, String AuditId,String Search){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

        Cursor result = AuditModel.GetAuditTravelRides(JobOrderId,AccountNumber,AuditId,Search);
        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
               Comment = result.getString(5);
               Fare = result.getString(4);
               Vehicle = result.getString(3);
            }
            txtFare.setText(Fare);
            txtComment.setText(Comment);
            spinner_vehicle.setSelection(adapter_vehicle.getPosition(Vehicle));
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    private void GetImages(){
        mUri.clear();
        mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);
        if(!mImages.exists() && !mImages.mkdirs()){

            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Filename = Liquid.RemoveSpecialCharacter(Liquid.SelectedId)+"_"+Liquid.RemoveSpecialCharacter(Liquid.Client)+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedAccountNumber) +"_"+String.valueOf(mUri.size()+1);
            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[3].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedCode))) {
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }

            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));

        }
    }
}
