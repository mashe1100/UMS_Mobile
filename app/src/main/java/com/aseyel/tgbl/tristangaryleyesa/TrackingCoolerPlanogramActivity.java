package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingGalleryAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class TrackingCoolerPlanogramActivity extends BaseFormActivity {
    private static final String TAG = TrackingCoolerPlanogramActivity.class.getSimpleName();
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    int mBitmapCount = 0;
    File mImages;
    String Filename = "";
    LiquidFile mLiquidFile;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    static final int CAM_REQUEST = 1;
    File mFile;
    File[] listFile;
    TrackingGalleryAdapter Adapter;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    String[] Subfolder;
    String TrackingCategory = "Cooler Planogram";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_cooler_planogram);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        GetImages(true);
    }

    private void setup(){
        //Initialization
        Subfolder = new String[1];
        Subfolder[0] = TrackingCategory;
        rvList = (RecyclerView) findViewById(R.id.rvList);
        Adapter = new TrackingGalleryAdapter(this);
        int numberOfColumns = 3;
        GridLayoutManager glm = new GridLayoutManager(this,numberOfColumns);
        //Setting up
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(glm);
        rvList.setAdapter(Adapter);
        mLiquidFile = new LiquidFile(this);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Filename = Liquid.SelectedAccountNumber +"_"+"CoolerPlanogram"+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription)+"__"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
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
                               Liquid.SelectedDescription,
                               "",
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

                           doSubmitCoolerPlanogram();
                           //GetImages(false);
                       }
                   }
               }
       }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }

    private void doSubmitCoolerPlanogram(){
        boolean result = false;
        result =  TrackingModel.doSubmitCoolerPlanogram(
                Liquid.SelectedAccountNumber,
                Liquid.SelectedDescription,
                "YES",
                Filename,
                Liquid.SelectedPeriod
        );

        if(result == true){
            //Liquid.ShowMessage(this,"Successfully Saved");
        }else{
            //Liquid.ShowMessage(this,"Unsuccessfully Saved");
        }
    }

    private void GetImages(boolean animated){
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
                String[] separated  = listFile[a].getName().split("_");

                if(separated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription))){
                    mUri.add(Uri.fromFile(listFile[a]));
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }

            }
            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
            Adapter.updateItems(animated,final_result);
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
                Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
