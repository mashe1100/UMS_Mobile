package com.aseyel.tgbl.tristangaryleyesa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;

public class OCRActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    int total=0;
    String scanned = "";
    ArrayList<String> alldata = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionID:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);

        Button button = (Button)findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Detector not available yet.", Toast.LENGTH_SHORT).show();
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(OCRActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    final StringBuilder texttext = new StringBuilder();
                    if(items.size() != 0){
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> scannedAnswer = new ArrayList<>();
                                int items_hit = 0;

                                for (int i=0; i<items.size(); i++){
                                    TextBlock item = items.get(i);
                                    if(item != null) {



                                        try{
                                                String value = item.getValue();
                                                if(value.contains(" ")){
                                                    String[] split = value.split(" ");

                                                    for (int x=0; x<split.length; x++){
                                                        if (split[x].length() == 11) {
                                                            if ((split[x].charAt(9) + "").matches("-")) {
                                                                for (int z = 0; z < split[x].length(); z++) {
                                                                    int tester = 0;
                                                                    if (z != 9)
                                                                        tester = Integer.parseInt(split[x].charAt(z) + "");
                                                                }

                                                                if(scanned.matches(split[x])){
                                                                    total++;

                                                                    if(total >= 1) {
                                                                        Liquid.TrackingNumber = split[x];
                                                                        if(!Liquid.CheckGPS(OCRActivity.this)) {
                                                                            Liquid.showDialogInfo(OCRActivity.this, "Invalid", "Please enable GPS!");
                                                                        } else {
                                                                            Intent intent = new Intent(getApplicationContext(), ReadingRemarksActivity.class);
                                                                            startActivity(intent);
                                                                        }
                                                                        finish();
                                                                        return;
                                                                    }
                                                                }else{
                                                                    scanned = split[x];
                                                                    total = 0;
                                                                }
                                                            }
                                                        }

                                                    }
                                                }else {
                                                    if (value.length() == 11) {
                                                        if ((value.charAt(9) + "").matches("-")) {
                                                            for (int x = 0; x < value.length(); x++) {
                                                                int tester = 0;
                                                                if (x != 9)
                                                                    tester = Integer.parseInt(value.charAt(x) + "");
                                                            }


                                                            if(scanned.matches(value)){
                                                                total++;

                                                                if(total >= 1) {
                                                                    Liquid.TrackingNumber = value;
                                                                    if(!Liquid.CheckGPS(OCRActivity.this)) {
                                                                        Liquid.showDialogInfo(OCRActivity.this, "Invalid", "Please enable GPS!");
                                                                    } else {
                                                                        Intent intent = new Intent(getApplicationContext(), ReadingRemarksActivity.class);
                                                                        startActivity(intent);
                                                                    }
                                                                    finish();
                                                                    return;
                                                                }
                                                            }else{
                                                                scanned = value;
                                                                total = 0;
                                                            }
                                                        }
                                                    }
                                                }
                                        }catch (Exception e){}
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
