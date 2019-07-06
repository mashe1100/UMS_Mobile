package com.aseyel.tgbl.tristangaryleyesa.model;

import android.database.Cursor;

import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;

/**
 * Created by Romeo on 2018-01-02.
 */

public class ImageModel {
    public String Filepath;
    public String  Filename;


    public ImageModel(String Filepath, String Filename) {
        this.Filepath = Filepath;
        this.Filename = Filename;
    }

    public String getFilepath() {
        return Filepath;
    }

    public void setFilepath(String filepath) {
        Filepath = filepath;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }



}
