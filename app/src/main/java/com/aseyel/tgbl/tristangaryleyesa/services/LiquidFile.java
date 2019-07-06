package com.aseyel.tgbl.tristangaryleyesa.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Romeo on 2018-01-29.
 */

public class LiquidFile extends View{
    private static final String TAG = LiquidFile.class.getSimpleName();
    Context mContext;

    public LiquidFile(Context context) {
        super(context);
        this.mContext = context;
    }

    public File Directory(String AccountNumber,String Filename,String[] SubFolder){
        File file = Liquid.getDiscPicture(AccountNumber,SubFolder);
        if(!file.exists() && !file.mkdirs()){
            Liquid.ShowMessage(mContext,"Can't create directory to save image");
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = simpleDateFormat.format(new Date());
            String name = Liquid.RemoveSpecialCharacter(Filename);
            //String name = Filename;
            String file_name = file.getAbsolutePath()+"/"+name;
            file = new File(file,name);
        }
        return file;
    }

    public File DefaultDirectory(String Filename,String[] SubFolder){
        File file = Liquid.getDiscDefaultPicture(SubFolder);
        if(!file.exists() && !file.mkdirs()){
            Liquid.ShowMessage(mContext,"Can't create directory to save image");
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = simpleDateFormat.format(new Date());
            String name = Liquid.RemoveSpecialCharacter(Filename);
            //String name = Filename;
            String file_name = file.getAbsolutePath()+"/"+name;
            file = new File(file,name);
        }
        return file;
    }
    public void Save(File Directory, Bitmap mBitmap){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(Directory);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Liquid.ShowMessage(mContext,"Save Image Success");
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);

        } catch (Exception e) {
            Log.e(TAG,"Error : ",e);
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            Log.i(TAG, String.valueOf(children));
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
