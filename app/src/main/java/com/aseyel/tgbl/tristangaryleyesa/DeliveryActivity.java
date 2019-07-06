package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class DeliveryActivity extends BaseFormActivity {
    private static final String TAG = DeliveryActivity.class.getSimpleName();


    private TextView txtQuestion;
    private EditText txtComment;
    private EditText txtAccountNumber;
    @BindView(R.id.spinner_delivery_remarks)
    Spinner spinner_delivery_remarks;
    private ArrayAdapter<CharSequence> adapter_delivery_remarks;
    private String Category = "Delivery";
    private String SignatureName;
    private String Comment;
    private  String Remarks;
    private String Latitude;
    private String Longitude;

    private ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    private int mBitmapCount = 0;
    private  ArrayList<Uri> mUri = new ArrayList<Uri>();
    private static final int CAM_REQUEST = 1;
    private File mFile;
    private String[] Subfolder;
    private File[] listFile;
    private File mImages;
    private String Filename = "";
    private LiquidFile mLiquidFile;

    @BindView(R.id.ivSignature)
    ImageView ivSignature;

    private EditText[] mEditTextData;
    private Liquid mLiquid;

    private AHBottomNavigation mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Liquid.SelectedAccountName = "admin";
        Liquid.SelectedPeriod = "201804";
        Liquid.SelectedId = Liquid.User + Liquid.currentDateMonthYear();
        Liquid.ReadingDate = Liquid.currentDate();
        setup();
        GetImages();
    }

    public void onResume() {
        super.onResume();
        GetImages();
    }
    View mView;
    private void setup() {

        try{
            mLiquid = new Liquid();
            txtQuestion = (TextView) findViewById(R.id.txtQuestion);
            txtComment = (EditText) findViewById(R.id.txtComment);
            txtAccountNumber = (EditText) findViewById(R.id.txtAccountNumber);
            txtQuestion.setText("Scan or Input the Tracking Number.");
            adapter_delivery_remarks = ArrayAdapter.createFromResource(this,R.array.delivery_remarks,android.R.layout.simple_spinner_item);
            adapter_delivery_remarks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_delivery_remarks.setAdapter(adapter_delivery_remarks);
            mLiquidFile = new LiquidFile(this);
            mBottomNavigationView = (AHBottomNavigation) findViewById(R.id.mBottomNavigationView);
            spinner_delivery_remarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Remarks = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            BottomNavigation();

            if(Liquid.HideKeyboard == 1){
                mEditTextData = new EditText[2];
                mEditTextData[0] = txtAccountNumber;
                mEditTextData[1] = txtComment;
                mLiquid.hideSoftKeyboard(mEditTextData);
            }

        }catch(Exception e){
            Log.i(TAG,"Error : ",e);
        }

    }

    public void BottomNavigation(){
        AHBottomNavigationItem item1 =  new AHBottomNavigationItem("List", R.drawable.ic_list, R.color.colorAccent);
        AHBottomNavigationItem item2 =  new AHBottomNavigationItem("Signature", R.drawable.ic_action_todo, R.color.colorAccent);
        AHBottomNavigationItem item3 =  new AHBottomNavigationItem("Camera", R.drawable.ic_action_camera, R.color.colorAccent);
        mBottomNavigationView.addItem(item1);
        mBottomNavigationView.addItem(item2);
        mBottomNavigationView.addItem(item3);


        mBottomNavigationView.setAccentColor(R.color.colorAccent);
        mBottomNavigationView.setInactiveColor(R.color.colorAccent);
        int size = mBottomNavigationView.getItemsCount();
        mBottomNavigationView.setSelected(false);

        mBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Intent i = new Intent();
                Liquid.SelectedAccountNumber = txtAccountNumber.getText().toString();
                switch (position){
                    case 0:
                        i = new Intent(DeliveryActivity.this, DeliveryMainActivity.class);
                        break;
                    case 1:
                        i = new Intent(DeliveryActivity.this, TrackingSignatureActivity.class);
                        break;
                    case 2:
                       i = new Intent(DeliveryActivity.this, ReadingGalleryActivity.class);
                        break;

                    default:
                }
                startActivity(i);
                return true;
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        try{
        switch(item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                saveDelivery();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }

    }

    public void saveDelivery(){
        boolean result = false;

        Liquid.SelectedAccountNumber = txtAccountNumber.getText().toString();
        SignatureName = Liquid.SelectedAccountNumber;
        Comment = txtComment.getText().toString();
        Latitude = String.valueOf(Liquid.Latitude);
        Longitude = String.valueOf(Liquid.Longitude);
        result = DeliveryModel.doSubmitDelivery(
                Liquid.Client,
                Liquid.SelectedId,
                Liquid.ServiceType,
                Liquid.SelectedAccountNumber,
                "1",
                "Statement of Account",
                "Delivered",
                "0",
                Remarks,
                Comment,
                Latitude,
                Longitude,
                "Pending",
                SignatureName,
                "Pending",
                "100",
                Liquid.currentDateTime(),
                Liquid.currentDate(),
                Liquid.User

        );


        if(result == true){
            Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
        }else{
            Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
        }

    }

    private void GetImages(){
        try{
            mImages = Liquid.getDiscSignature(Liquid.SelectedAccountNumber);
            if(!mImages.exists() && !mImages.mkdirs()){
                Liquid.ShowMessage(mView.getContext(),"Can't create directory to save image");
            }
            else{
                listFile = mImages.listFiles();
                Bitmap bmp = BitmapFactory.decodeFile(listFile[0].getAbsolutePath());
                ivSignature.setImageBitmap(bmp);
            }
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrackingModel.doSubmitPicture(Liquid.SelectedAccountNumber,
                "Delivery",
                "",
                "",
                "",
                String.valueOf(mUri.size()),
                Filename,
                Liquid.SelectedPeriod
        );
        Liquid.ShowMessage(getApplicationContext(),"Save Image Success");
        //tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
    }


}
