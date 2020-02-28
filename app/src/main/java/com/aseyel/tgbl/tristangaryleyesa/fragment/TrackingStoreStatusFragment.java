package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;

import com.aseyel.tgbl.tristangaryleyesa.GalleryActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingStoreStatusFragment extends Fragment {
    private static final String TAG = TrackingStoreStatusFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    Liquid.ApiData mApiData;
    View mView;
    @BindView(R.id.rgTrackingStoreStatus)
    RadioGroup rg;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    int mBitmapCount = 0;
    String Price;
    String Comment;
    LiquidFile mLiquidFile;
    String Filename = "";
    @BindView(R.id.floatBtnAdd)
    FloatingActionButton floatBtnAdd;
    FloatingActionButton floatBtnGallery;
    RadioButton rbOpen;
    RadioButton rbClosed;
    RadioButton rbRefused;
    RadioButton rb;

    String Status;
    String Latitude;
    String Longitude;
    String TransferStatus;
    String[] Subfolder;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    static final int CAM_REQUEST = 1;
    File mFile;
    File[] listFile;
    File mImages;
    String Category = "Store Status";
    TrackingActivity mTrackingActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_tab_tracking_storestatus, container, false);
            setup(mView);
            GetImages();
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
        return mView;
    }

   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            boolean final_result = false;
            if (requestCode == CAM_REQUEST) {
                final_result = TrackingModel.doSubmitPicture(Liquid.SelectedAccountNumber,
                        Category,
                        "",
                        "",
                        "",
                        String.valueOf(mUri.size()),
                        Filename,
                        Liquid.SelectedPeriod
                );
                if(final_result == true) {
                    Liquid.resizeImage(mFile.getAbsolutePath(),0.80,0.80);
                    Liquid.ShowMessage(mView.getContext(), "Save Image Success");
                    mUri.add(Uri.fromFile(mFile));
                    tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                }
            }
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }

    private void setup(final View view) {
        try {
            Subfolder = new String[1];
            Subfolder[0] = Category;
            //Initialization
            rbOpen = (RadioButton) view.findViewById(R.id.rbOpen);
            rbClosed = (RadioButton) view.findViewById(R.id.rbClosed);
            rbRefused = (RadioButton) view.findViewById(R.id.rbRefused);
            btnCamera = (ImageButton) view.findViewById(R.id.btnCamera);
            floatBtnAdd = (FloatingActionButton) view.findViewById(R.id.floatBtnAdd);
            tsImageCounter = (TextSwitcher) view.findViewById(R.id.tsImageCounter);
            rg = (RadioGroup) view.findViewById(R.id.rgTrackingStoreStatus);
            mLiquidFile = new LiquidFile(view.getContext());
            btnCamera = (ImageButton) view.findViewById(R.id.btnCamera);
            floatBtnGallery = (FloatingActionButton) view.findViewById(R.id.floatBtnGallery);
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if(rbOpen.isChecked()){
                            Liquid.SelectedStoreStatus = (String) rbOpen.getText();
                        }
                        if(rbClosed.isChecked()){
                            Liquid.SelectedStoreStatus = (String) rbClosed.getText();
                        }
                        if(rbRefused.isChecked()){
                            Liquid.SelectedStoreStatus = (String) rbRefused.getText();
                        }
                        if(Liquid.SelectedStoreStatus.isEmpty() || Liquid.SelectedStoreStatus.equals("") ){
                            Liquid.showDialogError(mView.getContext(),"Invalid","Please answer the questions before taking a image!");
                            return;
                        }
                        Filename = Liquid.SelectedAccountNumber +"_"+"StoreStatus"+"___"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
                        mFile = mLiquidFile.Directory(Liquid.SelectedAccountNumber,Filename.trim(),Subfolder);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mFile));
                        startActivityForResult(intent,CAM_REQUEST);
                    }
                    catch (Exception e){
                        Liquid.ShowMessage(mView.getContext(),e.toString());
                        Log.e(TAG,"Error :",e);
                    }
                }
            });

            floatBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = Category;
                    Intent i = new Intent(view.getContext(), GalleryActivity.class);
                    view.getContext().startActivity(i);
                }
            });

            floatBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = false;
                    LiquidGPS mLiquidGPS = new LiquidGPS(getActivity());
                    if(rbOpen.isChecked()){
                        Liquid.SelectedStoreStatus = (String) rbOpen.getText();
                    }
                    if(rbClosed.isChecked()){
                        Liquid.SelectedStoreStatus = (String) rbClosed.getText();
                    }
                    if(rbRefused.isChecked()){
                        Liquid.SelectedStoreStatus = (String) rbRefused.getText();
                    }
                    if(Liquid.SelectedStoreStatus.isEmpty()){
                        Liquid.showDialogError(mView.getContext(),"Invalid","Information Incomplete!");
                        return;
                    }
                    if(mUri.size() == 0){
                        Liquid.showDialogError(mView.getContext(),"Invalid","Please take picture!");
                        return;
                    }
                    Status = Liquid.SelectedStoreStatus;
                    Latitude = String.valueOf(mLiquidGPS.getLatitude());
                    Longitude = String.valueOf(mLiquidGPS.getLongitude());
                    if(Latitude.equals("0.0") && Longitude.equals("0.0")){
                        Latitude = "0";
                        Longitude = "0";
                    }
                    TransferStatus = "Pending";

                    result =  TrackingModel.doSubmitStoreStatus(
                            Liquid.SelectedAccountNumber,
                            Latitude,
                            Longitude,
                            Status,
                            TransferStatus,
                            Filename,
                            Liquid.SelectedPeriod
                    );

                    if(result == true){

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        getActivity().finish();
                                        startActivity(getActivity().getIntent());
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Successfully Saved!").setTitle("Valid")
                                .setNegativeButton("Done", dialogClickListener).show();

                    } else {
                        Liquid.showDialogError(mView.getContext(), "Invalid", "Unsuccessfully Saved!");
                    }
                }
            });

            GetData(Liquid.SelectedAccountNumber,Liquid.SelectedPeriod);
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    public void GetData(String AccountNumber,
                        String Period){
        String Status = "";
        Cursor result = TrackingModel.GetStoreStatus(AccountNumber,
                Period);
        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                Status = result.getString(0);

            }
            switch (Status){
                case "Open":
                    rbOpen.setChecked(true);
                    break;
                case "Closed":
                    rbClosed.setChecked(true);
                    break;
                case "Refused":
                    rbRefused.setChecked(true);
                    break;
                default:
                    return;
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

            Liquid.ShowMessage(mView.getContext(),"Can't create directory to save image");
        }
        else{
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                mUri.add(Uri.fromFile(listFile[a]));
                tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
            }
        }
    }
}
