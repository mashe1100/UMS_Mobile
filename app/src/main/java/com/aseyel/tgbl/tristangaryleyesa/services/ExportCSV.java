package com.aseyel.tgbl.tristangaryleyesa.services;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.aseyel.tgbl.tristangaryleyesa.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportCSV {
    public final String TAG = ExportCSV.class.getSimpleName();

    public ExportCSV(){

    }

    public void Excute(String fileName){
        File folder = new File(Environment.getExternalStorageDirectory()
                + "/UMS/CSV/");

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        final String filename = folder.toString() + "/" + fileName+".csv";
        // show waiting screen
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };

        new Thread() {
            public void run() {
                try {

                    FileWriter fw = new FileWriter(filename);
                    //Cursor cursor = db.selectAll();
                    fw.append("No");
                    fw.append(',');

                    fw.append("code");
                    fw.append(',');

                    fw.append("nr");
                    fw.append(',');

                    fw.append("Orde");
                    fw.append(',');

                    fw.append("Da");
                    fw.append(',');

                    fw.append("Date");
                    fw.append(',');

                    fw.append("Leverancier");
                    fw.append(',');

                    fw.append("Baaln");
                    fw.append(',');

                    fw.append("asd");
                    fw.append(',');

                    fw.append("Kwaliteit");
                    fw.append(',');

                    fw.append("asd");
                    fw.append(',');

                    fw.append('\n');

                    /*if (cursor.moveToFirst()) {
                        do {
                            fw.append(cursor.getString(0));
                            fw.append(',');

                            fw.append(cursor.getString(1));
                            fw.append(',');

                            fw.append(cursor.getString(2));
                            fw.append(',');

                            fw.append(cursor.getString(3));
                            fw.append(',');

                            fw.append(cursor.getString(4));
                            fw.append(',');

                            fw.append(cursor.getString(5));
                            fw.append(',');

                            fw.append(cursor.getString(6));
                            fw.append(',');

                            fw.append(cursor.getString(7));
                            fw.append(',');

                            fw.append(cursor.getString(8));
                            fw.append(',');

                            fw.append(cursor.getString(9));
                            fw.append(',');

                            fw.append(cursor.getString(10));
                            fw.append(',');

                            fw.append('\n');

                        } while (cursor.moveToNext());
                    }*/
                    /*if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }*/

                    // fw.flush();
                    fw.close();

                } catch (Exception e) {
                }
                handler.sendEmptyMessage(0);

            }
        }.start();

    }



}
