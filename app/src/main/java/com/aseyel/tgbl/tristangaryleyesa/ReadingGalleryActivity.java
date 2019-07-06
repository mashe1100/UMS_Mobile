package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingGalleryAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class ReadingGalleryActivity extends BaseActivity {
    private final String TAG = ReadingGalleryActivity.class.getSimpleName();
    private RecyclerView rvList;
    private TrackingGalleryAdapter Adapter;
    private ProgressDialog pDialog;
    private int numberOfColumns = 3;
    private LiquidFile mLiquidFile;
    private MenuItem searchMenuItem;
    private String[] Subfolder;
    private Button btnNext;
    private ImageButton btnCamera;
    private TextSwitcher tsImageCounter;
    private int ImageCount = 0;
    private  ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
    private int mBitmapCount = 0;
    private File mFile;
    private File mImages;
    private File[] listFile;
    private String Filename = "";
    private ArrayList<Uri> mUri = new ArrayList<Uri>();
    private static final int CAM_REQUEST = 1;
    private boolean animation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reading_gallery);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Liquid.SelectedCategory = "";
            init(Liquid.SelectedCategory);
        }catch(Exception e){
            Log.e(TAG,"Tristan Gary Leyesa ",e);
        }

    }
    private void init(String Folder){


        mLiquidFile = new LiquidFile(this);
        pDialog = new ProgressDialog(this);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnNext = (Button) findViewById(R.id.btnNext);
        tsImageCounter = (TextSwitcher) findViewById(R.id.tsImageCounter);
        Subfolder = new String[1];
        Subfolder[0] = Folder;
        rvList = (RecyclerView) findViewById(R.id.rvList);
        Adapter = new TrackingGalleryAdapter(this);
        GridLayoutManager glm = new GridLayoutManager(this , numberOfColumns);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(glm);
        rvList.setAdapter(Adapter);
        mLiquidFile = new LiquidFile(this);

        new GetImages().execute();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Filename = Liquid.AccountNumber + "_"+"reading"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;

                    if (Liquid.Client == "more_power"){
                        //for Survey
//                        Filename = Liquid.MeterNumber + "_"+"audit"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + Liquid.User + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                       Filename = Liquid.AccountNumber + "_"+"reading"+"_"+ Liquid.RemoveSpecialCharacter(Liquid.ReadingDate) + "_" + String.valueOf(mUri.size() + 1) + Liquid.imageFormat;
                    }


                    mFile = mLiquidFile.Directory(Liquid.SelectedId,Liquid.RemoveSpecialCharacter(Filename), Subfolder);
                    Log.i(TAG, String.valueOf(mFile));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    startActivityForResult(intent, CAM_REQUEST);

                } catch (Exception e) {
                    Liquid.ShowMessage(getApplicationContext(), e.toString());
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ImageCount == 0){
                    Liquid.showDialogInfo(ReadingGalleryActivity.this,"Warning","Please take a picture to prove your reading and remarks. Thank you.");
                }else{
                    //Intent i = new Intent(ReadingGalleryActivity.this, ReadingSummaryActivity.class);
                    Intent i = new Intent();
                    switch(Liquid.ServiceType){
                        case "MESSENGER":
                            i = new Intent(ReadingGalleryActivity.this, DeliveryActivity.class);
                            break;
                            default:
                                i = new Intent(ReadingGalleryActivity.this, ReadingV2Activity.class);
                                break;
                    }

                    startActivity(i);
                }

            }
        });

    }

    public class GetImages extends AsyncTask<Void,Void,Void>{
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading....");
            pDialog.show();
            tsImageCounter.setCurrentText("0");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            final_result = GetReadingImages(Liquid.SelectedId,Subfolder);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
            Adapter.updateItems(animation,final_result);
            if(pDialog.isShowing())
                 pDialog.dismiss();


        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == CAM_REQUEST) {

                    boolean result = false;
                    String AccountNumber = "";
                    String ImageType = "";
                    if(Liquid.Client == "more_power"){
                        //for Survey
//                        AccountNumber = Liquid.MeterNumber;
                        ImageType = "audit";
                        AccountNumber = Liquid.AccountNumber;
//                        ImageType = "reading";
                    }
                    else{
                        AccountNumber = Liquid.AccountNumber;
                        ImageType = "reading";
                    }

                    result = ReadingModel.doSubmitPicture(
                            Liquid.Client,
                            AccountNumber,
                            ImageType,
                            Liquid.RemoveSpecialCharacter(Filename),
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
                        //tsImageCounter.setCurrentText(String.valueOf(mUri.size()));
                        ImageCount = mUri.size();

                    }
                    animation = false;
                    new GetImages().execute();

                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private ArrayList<HashMap<String, String>> GetReadingImages(String id,String[] Subfolder){
        String SelectedAccountNumber = "";
        if(Liquid.Client == "more_power"){
            //for Survey
            //SelectedAccountNumber = Liquid.serial;
            SelectedAccountNumber = Liquid.SelectedAccountNumber;
        }
        else{

            SelectedAccountNumber = Liquid.SelectedAccountNumber;
        }

        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Log.i(TAG,"Tristan "+ Liquid.SelectedId);
        Log.i(TAG,"Tristan "+ SelectedAccountNumber);
        Log.i(TAG,"Tristan "+ Liquid.ReadingDate);
        File mImages = Liquid.getDiscPicture(id,Subfolder);
        mUri.clear();
        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();


            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[0].equals(Liquid.RemoveSpecialCharacter(SelectedAccountNumber)) && seperated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.ReadingDate))){
                    mUri.add(Uri.fromFile(listFile[a]));
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);

                }
            }
        }
        return final_result;
    }
}
