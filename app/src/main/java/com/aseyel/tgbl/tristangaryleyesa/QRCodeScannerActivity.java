package com.aseyel.tgbl.tristangaryleyesa;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QRCodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String TAG = QRCodeScannerActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        mView = scannerView;
        Log.i(TAG, "Permission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Log.i(TAG, "Permission Granted");
            } else {
                requestPermissions();
                Log.i(TAG, "Permission Denied");
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(QRCodeScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCode, String permission[], int grantResult[]) {

        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResult.length > 0) {
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        }
                                );
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(QRCodeScannerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();

            } else {

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
            Liquid.QRCode = scanResult;
            Liquid.TrackingNumber = scanResult;
            this.finish();
        }
    }


