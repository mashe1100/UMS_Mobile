package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingGalleryAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class TrackingSoviLocationActivity extends BaseFormActivity {
    private static final String TAG = TrackingSoviLocationActivity.class.getSimpleName();
    private boolean final_result = false;
    private ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    private int mBitmapCount = 0;
    private File mFile;
    private String Filename = "";
    private LiquidFile mLiquidFile;
    private ArrayList<Uri> mUri = new ArrayList<Uri>();
    private static final int CAM_REQUEST = 1;
    private String Category = "SOVI Location";
    private File mImages;
    private File[] listFile;
    private TrackingGalleryAdapter Adapter;
    private ProgressDialog mProgressDialog;
    private String[] Subfolder;
    private ImageView ivImage;
    private FloatingActionButton fbGallery;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.tsImageCounter)
    TextSwitcher tsImageCounter;
    //@BindView(R.id.rvList)
    //RecyclerView rvList;
    int ImageCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tracking_sovi_location);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setup();
            //GetImages(true);
            new GetImages().execute();
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    private void setup(){
        //Initialization
        Subfolder = new String[1];
        Subfolder[0] = Category;
        //rvList = (RecyclerView) findViewById(R.id.rvList);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        fbGallery = (FloatingActionButton) findViewById(R.id.floatBtnGallery);
        //Adapter = new TrackingGalleryAdapter(this);
        //int numberOfColumns = 3;
        //GridLayoutManager glm = new GridLayoutManager(this,numberOfColumns);
        //Setting up
        //rvList.setHasFixedSize(true);
        //rvList.setLayoutManager(glm);
        //rvList.setAdapter(Adapter);
        mLiquidFile = new LiquidFile(this);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Filename = Liquid.SelectedAccountNumber +"_"+"SOVILocation"+"_"+Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription)+"__"+String.valueOf(mUri.size()+1)+Liquid.imageFormat;
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
        fbGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Liquid.SelectedCategory = "SOVI Location";
                Intent i = new Intent(TrackingSoviLocationActivity.this, GalleryActivity.class);
                startActivity(i);
            }
        });
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            try{
                if(resultCode == RESULT_OK){
                    if(requestCode == CAM_REQUEST){
                        try{
                            boolean result = false;
                            Uri uri = null;
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

                                Bitmap photo = (Bitmap) Liquid.UriToBitmap(mUri.get(mUri.size()-1));
                                ivImage.setImageBitmap(photo);
                                doSubmitSoviLocation();
                            }
                            //new GetImages().execute();

                        }catch(Exception e){
                            Log.e(TAG,"Error : ",e);
                        }
                     }
                }
            }catch(Exception e){
                Log.e(TAG,"Error :",e);
            }
    }

    private void doSubmitSoviLocation(){
        boolean result = false;
        result =  TrackingModel.doSubmitSoviLocation(
                Liquid.SelectedAccountNumber,
                Liquid.SelectedDescription,
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

    public class GetImages extends AsyncTask<Void, Void, Void> {
        ArrayList<HashMap<String, String>> final_result_images = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(TrackingSoviLocationActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                mUri.clear();
                mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);

                if(!mImages.exists() && !mImages.mkdirs()){
                    Liquid.ShowMessage(TrackingSoviLocationActivity.this,"Can't create directory to save image");
                }
                else{
                    listFile = mImages.listFiles();

                    for(int a = 0; a < listFile.length; a++){
                        HashMap<String, String> data = new HashMap<>();
                        String[] separated  = listFile[a].getName().split("_");
                        if(separated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription))){
                            mUri.add(Uri.fromFile(listFile[a]));
                            data.put("FilePath", listFile[a].getAbsolutePath());
                            data.put("Filaname", listFile[a].getName());
                            final_result_images.add(data);
                        }
                    }
                    tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                    ImageCounter =  mUri.size();
                }
            }catch(Exception e){
                Log.e(TAG,"Error : ",e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                //Adapter.updateItems(false,final_result_images);
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }
        }

        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }
}
