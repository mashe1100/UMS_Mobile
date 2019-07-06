package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;

public class TrackingCDEActivity extends BaseFormActivity implements CompoundButton.OnCheckedChangeListener  {
    private static final String TAG = TrackingCDEActivity.class.getSimpleName();
    @BindView(R.id.spinner_tcde_area)
    Spinner spinner_tcde_area;
    @BindView(R.id.switchtcdePlanogram)
    Switch switchtcdePlanogram;
    @BindView(R.id.switchtcde75KofProduct)
    Switch switchtcde75KofProduct;
    String PlanogramCompliant = "No";
    String FullKofProduct = "No";
    String Area;
    String Comment;
    String CdeCountString = "|CDE Compliance";
    String FinalCdeCountString = "";
    String QuestionOne = "Planogram Compliant";
    String QuestionTwo = "75% Full of KOF products";

    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;
    @BindView(R.id.txtComment)
    EditText txtComment;

    ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    int mBitmapCount = 0;

    File mFile;
    String Filename = "";
    LiquidFile mLiquidFile;

    ArrayList<Uri> mUri = new ArrayList<Uri>();
    static final int CAM_REQUEST = 1;
    String[] Subfolder;
    int CdeCount = 1;

    ArrayAdapter<CharSequence> adapter_cde_area;

    File[] listFile;
    File mImages;
    String TrackingCategory = "CDE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tracking_cde);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setup();
            GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCode,Liquid.SelectedPeriod);
            //GetImages();

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }

    }
    private void setup() {
        Subfolder = new String[1];
        Subfolder[0] = TrackingCategory;
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtComment = (EditText) findViewById(R.id.txtComment);
        mLiquidFile = new LiquidFile(this);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        txtQuestion.setText("Choose the correct  COOLER AREA and if is Planogram Complaint and 75% Full of KOF Product of COKE COOLER.");
        adapter_cde_area = ArrayAdapter.createFromResource(this,R.array.tracking_cde_area,android.R.layout.simple_spinner_item);
        adapter_cde_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tcde_area.setAdapter(adapter_cde_area);
        switchtcdePlanogram = (Switch) findViewById(R.id.switchtcdePlanogram);
        switchtcde75KofProduct = (Switch) findViewById(R.id.switchtcde75KofProduct);
        switchtcdePlanogram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchtcdePlanogram.isChecked()){
                    PlanogramCompliant = "Yes";
                }else{
                    PlanogramCompliant = "No";
                }
            }
        });
        switchtcde75KofProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchtcde75KofProduct.isChecked()){
                    FullKofProduct = "Yes";
                }else{
                    FullKofProduct = "No";
                }
            }
        });

        spinner_tcde_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area = parent.getItemAtPosition(position).toString();
                if(Area.equals("Select Area")){
                    Area = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (Area.equals("Select Area") || Area.equals("")) {
                        Liquid.showDialogError(TrackingCDEActivity.this, "Invalid", "Information Incomplete!");
                        return;
                    }
                    Filename = Liquid.SelectedAccountNumber +"_"+"CDE"+"_"+Liquid.RemoveSpecialCharacter(Area)+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedCode)+"_"+"Planogram_Compliant_"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
                    mFile = mLiquidFile.Directory(Liquid.SelectedAccountNumber,Filename.trim(),Subfolder);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mFile));

                    startActivityForResult(intent,CAM_REQUEST);

                }
                catch (Exception e){
                    Liquid.ShowMessage(getApplicationContext(),e.toString());
                }
            }
        });
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
               if(resultCode == RESULT_OK) {
                   if (requestCode == CAM_REQUEST) {
                       boolean result = false;
                       result = TrackingModel.doSubmitPicture(Liquid.SelectedAccountNumber,
                               TrackingCategory,
                               Area,
                               Liquid.SelectedCode,
                               "Planogram Compliant",
                               String.valueOf(mUri.size()),
                               Filename,
                               Liquid.SelectedPeriod
                       );
                       if(result == true){
                           Liquid.resizeImage(mFile.getAbsolutePath(),0.80,0.80);
                           Liquid.ShowMessage(getApplicationContext(),"Save Image Success");
                           mUri.add(Uri.fromFile(mFile));
                           tsImageCounter.setCurrentText(String.valueOf(mUri.size()));

                       }
                   }
               }
       }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                Comment = txtComment.getText().toString();
                FinalCdeCountString = Liquid.SelectedCode;
                boolean result = false;
                if (Area.equals("Select Area") || Area.equals("")) {
                    Liquid.showDialogError(this, "Invalid", "Information Incomplete!");
                    return false;
                }
                if (mUri.size() == 0) {
                    Liquid.showDialogError(this, "Invalid", "Please take picture!");
                    return false;
                }
                
                result =  TrackingModel.doSubmitCDE(
                        Liquid.SelectedAccountNumber,
                        Area,
                        FinalCdeCountString,
                        QuestionOne,
                        PlanogramCompliant,
                        "1",
                        Filename,
                        Comment,
                        Liquid.SelectedPeriod
                );

                result =  TrackingModel.doSubmitCDE(
                        Liquid.SelectedAccountNumber,
                        Area,
                        FinalCdeCountString,
                        QuestionTwo,
                        FullKofProduct,
                        "1",
                        Filename,
                        Comment,
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
                        String Code,
                        String Period){
        String Status = "";
        Cursor result = TrackingModel.GetCDE(
                AccountNumber,
                Code,
                QuestionOne,
                Period);
        Cursor result2 = TrackingModel.GetCDE(
                AccountNumber,
                Code,
                QuestionTwo,
                Period);

        try
        {
            if(result.getCount() == 0 || result2.getCount() == 0){
                return;
            }

            while(result.moveToNext()){
                Area = result.getString(1);
                PlanogramCompliant = result.getString(4);

            }
            while(result2.moveToNext()){
                Area = result2.getString(1);
                FullKofProduct = result2.getString(4);
                Comment = result2.getString(7);
            }
            txtComment.setText(Comment);
            if(FullKofProduct.equals("Yes")){
                switchtcde75KofProduct.setChecked(true);
            }
            if(PlanogramCompliant.equals("Yes")){
                switchtcdePlanogram.setChecked(true);
            }
            spinner_tcde_area.setSelection(adapter_cde_area.getPosition(Area));
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


            Filename = Liquid.SelectedAccountNumber +"_"+"CDE"+"_"+Liquid.RemoveSpecialCharacter(Area)+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedCode)+"_"+"Planogram_Compliant_"+String.valueOf(mUri.size()+1);

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[2].equals(Liquid.RemoveSpecialCharacter(Area))  && seperated[3].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedCode))){
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }
            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
