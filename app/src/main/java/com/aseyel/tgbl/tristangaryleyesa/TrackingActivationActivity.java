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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class TrackingActivationActivity extends BaseFormActivity {
    private static final String TAG = TrackingActivationActivity.class.getSimpleName();
    @BindView(R.id.spinner_tactvt_activation)
    Spinner spinner_tactvt_activation;
    @BindView(R.id.spinner_tactvt_category)
    Spinner spinner_tactvt_category;
    String Activation;
    String Category;
    String Available = "true"; //true or false
    int NumMaterial = 1;
    ArrayAdapter<CharSequence> adapter_activation_activation;
    ArrayAdapter<CharSequence> adapter_activation_category;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    @BindView(R.id.image_preview)
    ImageView image_preview;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;
    ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    int mBitmapCount = 0;
    File mFile;
    String Filename = "";
    LiquidFile mLiquidFile;
    String[] Subfolder;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    static final int CAM_REQUEST = 1;
    File[] listFile;
    File mImages;
    String TrackingCategory = "Merchandising Materials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_activation);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
    }

    private void setup() {
        Subfolder = new String[1];
        Subfolder[0] = TrackingCategory;
        mLiquidFile = new LiquidFile(this);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        image_preview = (ImageView) findViewById(R.id.image_preview);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        txtQuestion.setText("Choose the correct COKE ACTIVATION and what is the CATEGORY inside the COKE ACTIVATION.");
        adapter_activation_activation = ArrayAdapter.createFromResource(this,R.array.tracking_activation,android.R.layout.simple_spinner_item);
        adapter_activation_activation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_activation_category = ArrayAdapter.createFromResource(this,R.array.tracking_activation_category,android.R.layout.simple_spinner_item);
        adapter_activation_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tactvt_activation.setAdapter(adapter_activation_activation);
        spinner_tactvt_category.setAdapter(adapter_activation_category);

        spinner_tactvt_activation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Activation = parent.getItemAtPosition(position).toString();
                if(Activation.equals("Select Activation")){
                    Activation = "";
                }
                GetData(Liquid.SelectedAccountNumber,Activation,Category,Liquid.SelectedPeriod);
                image_preview.setImageResource(doPreviewImage(Activation));
                GetImages();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_tactvt_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category = parent.getItemAtPosition(position).toString();
                if(Category.equals("Select Category")){
                    Category = "";
                }
                GetData(Liquid.SelectedAccountNumber,Activation,Category,Liquid.SelectedPeriod);
                GetImages();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (Activation.equals("Select Activation")
                            || Category.equals("Select Category")
                            || Activation.equals("")
                            || Category.equals("")
                            ) {
                        Liquid.showDialogError(TrackingActivationActivity.this, "Invalid", "Please answer the questions before taking a image!");
                        return;
                    }
                    Filename = Liquid.SelectedAccountNumber +"_"+"MerchandisingMaterials"+"_"+Liquid.RemoveSpecialCharacter(Activation)+"_"+Liquid.RemoveSpecialCharacter(Category)+"_"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
                    mFile = mLiquidFile.Directory(Liquid.SelectedAccountNumber,Filename,Subfolder);
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

    private int doPreviewImage(String value){
        int imageURL = R.drawable.img_toolbar_logo;
        switch(value){
            case "Mass Display Unit":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_massdisplayunit");
                break;
            case "Stack Pallet":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_stackpallet");
                break;
            case "End Cap":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_endcap");
                break;
            case "Rack: Cross-Category":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_crosscategory");
                break;
            case "Rack: Wilkins 6L":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_wilkins6l");
                break;
            case "Sidewall Shelf Display":
                imageURL = R.drawable.img_toolbar_logo;
                break;
            case "Special Displays":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_specialdisplay");
                break;
            case "POI 1X1":
                imageURL = Liquid.LiquidImages.get("img_coke_activation_poi1x1");
                break;
            default:
                imageURL = R.drawable.img_toolbar_logo;
        }
        return imageURL;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if(resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {
                    boolean result = false;
                    result = TrackingModel.doSubmitPicture(Liquid.SelectedAccountNumber,
                            TrackingCategory,
                            Activation,
                            Category,
                            "",
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_form_submit:
                boolean result = false;

                if (Activation.equals("Select Activation")
                        || Category.equals("Select Category")
                        || Activation.equals("")
                        || Category.equals("")
                        ) {
                    Liquid.showDialogError(this, "Invalid", "Information Incomplete!");
                    return false;
                }
                if (mUri.size() == 0) {
                    Liquid.showDialogError(this, "Invalid", "Please take picture!");
                    return false;
                }
                result =  TrackingModel.doSubmitActivation(
                        Liquid.SelectedAccountNumber,
                        Activation,
                        Available,
                        String.valueOf(NumMaterial),
                        Category,
                        Filename,
                        Liquid.SelectedPeriod
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

    public void GetData(
                        String AccountNumber,
                        String Description,
                        String Category,
                        String Period){

        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        if(Description.equals("") || Category.equals("")){
            return;
        }

        Cursor result = TrackingModel.GetTrackingActivation(AccountNumber,
                Description,
                Category,
                Period);
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                NumMaterial = Integer.parseInt(result.getString(3).equals("") ? "0" : result.getString(3))+1;
            }
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

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[2].equals(Liquid.RemoveSpecialCharacter(Activation))  && seperated[3].equals(Liquid.RemoveSpecialCharacter(Category))){
                    mUri.add(Uri.fromFile(listFile[a]));
                }
            }
        }
    }
}
