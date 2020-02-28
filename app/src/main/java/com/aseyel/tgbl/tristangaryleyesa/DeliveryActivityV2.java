package com.aseyel.tgbl.tristangaryleyesa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class DeliveryActivityV2 extends BaseActivity {
    private static final String TAG = DeliveryActivityV2.class.getSimpleName();
    private static final int SCANNING_FORM = 1;
    private MenuItem searchMenuItem;
    private MaterialSearchView search_view;
    private com.aseyel.tgbl.tristangaryleyesa.utils.AlphaNumericKeyboard txtTrackingNumber;
    private Button btnScan;
    private Button btnNext;
    private TextView txtQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_v2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
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

    private void GetMessengerial(String JobId,String TrackingNumber){
            try {

                Cursor result = DeliveryModel.GetSearchMessengerial(JobId,TrackingNumber);
                if (result.getCount() == 0) {
                    return;
                }

                while (result.moveToNext()) {
                            txtTrackingNumber.setText(Liquid.TrackingNumber);
                            Liquid.DeliveryItemTypeCode = result.getString(4);
                            Liquid.DeliveryItemTypeDescription = result.getString(5);
                            Liquid.RemarksCode = result.getString(7);
                            Liquid.Remarks = result.getString(8);
                            Liquid.ReaderComment = result.getString(9);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error : ", e);
                return;
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SCANNING_FORM:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtTrackingNumber.setText(Liquid.TrackingNumber);
    }

    private void init(){
        txtTrackingNumber = (com.aseyel.tgbl.tristangaryleyesa.utils.AlphaNumericKeyboard)  findViewById(R.id.txtTrackingNumber);
        txtQuestion = (TextView)  findViewById(R.id.txtQuestion);
        btnScan = (Button)  findViewById(R.id.btnScan);
        btnNext = (Button)  findViewById(R.id.btnNext);
        txtTrackingNumber.setText("");
        txtQuestion.setText("Step 1: Scan the BARCODE/QR CODE of the ITEM or" +
                            " Type the BARCODE/QR CODE value of the ITEM. " +
                            "After the input please press the NEXT button.");
        GetMessengerial(Liquid.SelectedId,Liquid.TrackingNumber);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryActivityV2.this);
                builder.setMessage("Choose type of scanner")
                        .setTitle("SCAN")
                        .setPositiveButton("BARCODE", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                Liquid.TrackingNumber = "";
                                Intent i = new Intent(getApplicationContext(), QRCodeScannerActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("OCR", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                Intent intent = new Intent(DeliveryActivityV2.this,OCRActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                                dialog.dismiss();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    }
                });
                alert.show();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Liquid.TrackingNumber = txtTrackingNumber.getText().toString();
                if(Liquid.TrackingNumber.equals("")){
                    Liquid.showDialogInfo(DeliveryActivityV2.this,"Invalid","Invalid Tracking Number!");
                }
                else if(!Liquid.CheckGPS(DeliveryActivityV2.this)) {
                    Liquid.showDialogInfo(DeliveryActivityV2.this, "Invalid", "Please enable GPS!");
                }
                else{
//                    Intent i = new Intent(getApplicationContext(), TypeActivity.class);
                    //REVISION FOR FASTER USER INTERACTION
                    Intent i = new Intent(getApplicationContext(), ReadingRemarksActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
