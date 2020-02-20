package com.aseyel.tgbl.tristangaryleyesa;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingGalleryAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class GalleryActivity extends BaseActivity {
    private final String TAG  = GalleryActivity.class.getSimpleName();
    RecyclerView rvList;
    TrackingGalleryAdapter Adapter;
    int numberOfColumns = 3;
    LiquidFile mLiquidFile;
    private MenuItem searchMenuItem;
    String[] Subfolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init(Liquid.SelectedCategory);
    }

    private void init(String Folder){
        Subfolder = new String[1];
        Subfolder[0] = Folder;
        rvList = (RecyclerView) findViewById(R.id.rvList);
        Adapter = new TrackingGalleryAdapter(this);
        GridLayoutManager glm = new GridLayoutManager(this , numberOfColumns);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(glm);
        rvList.setAdapter(Adapter);
        mLiquidFile = new LiquidFile(this);

       switch(Subfolder[0]){
           case "SOVI Location":
                   GetImageSoviLocation(true,Subfolder);
               break;
           case "reading":
               Subfolder[0] = "";
               GetReadingImages(true,Subfolder);

               break;

           case "disconnection":
               Subfolder[0] = "";
               GetDisconnectionImages(true,Subfolder);

               break;
           case "new_meter":
               Subfolder[0] = "";
               GetNewMeterImages(true,Subfolder);
               break;
           case "Audit":
               GetAuditImages(true,Subfolder);
               break;
               default:
                   GetImages(true,Subfolder,"");
        }
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

    private void GetNewMeterImages(boolean animated,String[] Subfolder) {
        File mImages = Liquid.getDiscPicture(Liquid.SelectedId+"_audit",Subfolder);

        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[0].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedMeterNumber)) && seperated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.ReadingDate))){
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }
            }
            Adapter.updateItems(animated,final_result);
        }
    }

    private void GetAuditImages(boolean animated,String[] Subfolder){
        Subfolder[0] = "";
        File mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);

        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[3].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedCode))){
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }
            }
            Adapter.updateItems(animated,final_result);
        }
    }

    private void GetReadingImages(boolean animated,String[] Subfolder){
        Log.i(TAG,Liquid.SelectedId);
        Log.i(TAG,Liquid.SelectedAccountNumber);
        Log.i(TAG,Liquid.ReadingDate);
        File mImages = Liquid.getDiscPicture(Liquid.SelectedId,Subfolder);

        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[0].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedAccountNumber)) && seperated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.ReadingDate))){
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }
            }
            Adapter.updateItems(animated,final_result);
        }
    }

    private void GetDisconnectionImages(boolean animated,String[] Subfolder){
        File mImages = Liquid.getDiscPicture(Liquid.SelectedId,Subfolder);

        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] seperated = listFile[a].getName().split("_");
                if(seperated[0].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedAccountNumber)) && seperated[1].equals(Liquid.RemoveSpecialCharacter(Liquid.ReadingDate))){
                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }
            }
            Adapter.updateItems(animated,final_result);
        }
    }

    private void GetImageSoviLocation(boolean animated,String[] Subfolder){
        File mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);

        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }
        else{
            File[] listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();

            for(int a = 0; a < listFile.length; a++){
                HashMap<String, String> data = new HashMap<>();
                String[] separated  = listFile[a].getName().split("_");
                if(separated[2].equals(Liquid.RemoveSpecialCharacter(Liquid.SelectedDescription))){

                    data.put("FilePath", listFile[a].getAbsolutePath());
                    data.put("Filaname", listFile[a].getName());
                    final_result.add(data);
                }
            }
            Adapter.updateItems(animated,final_result);
        }
    }

    private void GetImages(boolean animated,String[] Subfolder,String Filename){
        File mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);

        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(this,"Can't create directory to save image");
        }else{
            File[] listFile = mImages.listFiles();
            ArrayList<HashMap<String,String>> final_result = new ArrayList<HashMap<String,String>>();
            for(int a = 0; a < listFile.length; a++){
                HashMap<String,String> data = new HashMap<>();
                if(Filename != ""){
                    if(Filename == listFile[a].getName()){
                        data.put("FilePath",listFile[a].getAbsolutePath());
                        data.put("Filename",listFile[a].getName());
                    }
                }else{
                    data.put("FilePath",listFile[a].getAbsolutePath());
                    data.put("Filename",listFile[a].getName());
                }
                final_result.add(data);
            }
            Adapter.updateItems(animated,final_result);
        }
    }
}
