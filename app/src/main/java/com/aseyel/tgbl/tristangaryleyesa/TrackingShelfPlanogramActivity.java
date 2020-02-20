package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingGalleryAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class TrackingShelfPlanogramActivity extends BaseFormActivity {
    private static final String TAG = TrackingShelfPlanogramActivity.class.getSimpleName();
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    @BindView(R.id.switchShelfPlanogram)
    Switch switchShelfPlanogram;
    @BindView(R.id.txtGuide)
    TextView txtGuide;
    TrackingGalleryAdapter Adapter;
    ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    int mBitmapCount = 0;
    File mFile;
    File mImages;
    File[] listFile;
    String Filename = "";
    LiquidFile mLiquidFile;
    String Category = "Shelf Planogram Compliance";
    //@BindView(R.id.rvList)
    //RecyclerView rvList;
    String ShelfCompliant = "No";
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    static final int CAM_REQUEST = 1;
    String[] Subfolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_tracking_shelf_planogram);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setup();
            GetImages(true);
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }

    private void setup(){
        try {
            //Initialization
            Subfolder = new String[1];
            Subfolder[0] = Category;
            //rvList = (RecyclerView) findViewById(R.id.rvList);
            switchShelfPlanogram = (Switch) findViewById(R.id.switchShelfPlanogram);
            txtGuide = (TextView) findViewById(R.id.txtGuide);
            Adapter = new TrackingGalleryAdapter(this);
            int numberOfColumns = 3;
            GridLayoutManager glm = new GridLayoutManager(this,numberOfColumns);
            //Setting up
           // rvList.setHasFixedSize(true);
            //rvList.setLayoutManager(glm);
            //rvList.setAdapter(Adapter);
            txtGuide.setText("If the Shelf is planogram compliant select YES, if Not switch it to No.");

            switchShelfPlanogram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(switchShelfPlanogram.isChecked()){
                        ShelfCompliant = "Yes";
                    }else{
                        ShelfCompliant = "No";
                    }
                }
            });
            mLiquidFile = new LiquidFile(this);
            btnCamera = (ImageButton) findViewById(R.id.btnCamera);

            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                try{
                    Filename = Liquid.SelectedAccountNumber +"_"+"ShelfPlanogramCompliance"+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription)+"__"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
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
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {
                    boolean result = false;
                    result = TrackingModel.doSubmitPicture(Liquid.SelectedAccountNumber,
                            Category,
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

                        //GetImages(false);
                        doSubmitShelfPlanogram();
                    }
                }
            }

        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }

    private void doSubmitShelfPlanogram(){
        boolean result = false;
        result =  TrackingModel.doSubmitShelfPlanogram(
                Liquid.SelectedAccountNumber,
                Liquid.SelectedDescription,
                ShelfCompliant,
                Filename,
                Liquid.SelectedPeriod
        );

        if(result == true){

           // Liquid.ShowMessage(this,"Successfully Saved");
        }else{
            //Liquid.ShowMessage(this,"Unsuccessfully Saved");
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
}
