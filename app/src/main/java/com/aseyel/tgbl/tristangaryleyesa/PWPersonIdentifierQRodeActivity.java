package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;

public class PWPersonIdentifierQRodeActivity extends AppCompatActivity {
    private static final String TAG = PWPersonIdentifierQRodeActivity.class.getSimpleName();
    private static final int CAM_REQUEST = 1;
    @BindView(R.id.btnQrCode)
    Button btnQrCode;
    private ProgressDialog pDialog;
    @BindView (R.id.rbLunch)
    RadioButton rbLunch;
    @BindView (R.id.rbTraining)
    RadioButton rbTraining;
    ImageButton btnCamera;
    TextView txtGuide;
    TextView tvDetails;
    String AseyelAPI = "";
    String Filename = "";
    File mFile;
    String[] Subfolder;
    File[] listFile;
    File mImages;
    LiquidFile mLiquidFile;
    ArrayList<Uri> mUri = new ArrayList<Uri>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_person_identifier_qrode);
        setup();
    }

    private void setup(){
        Subfolder = new String[1];
        Subfolder[0] = "PRIMOWORXX";
        mLiquidFile = new LiquidFile(this);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnQrCode = (Button) findViewById(R.id.btnQrCode);
        tvDetails = (TextView) findViewById(R.id.tvDetails);
        rbLunch = (RadioButton) findViewById(R.id.rbLunch);
        rbTraining = (RadioButton) findViewById(R.id.rbTraining);
        txtGuide = (TextView) findViewById(R.id.txtGuide);
        txtGuide.setText("Choose a service before scanning the QR code. Thank you.");
        //txtGuide.setVisibility(View.GONE);
        //rbLunch.setVisibility(View.GONE);
        //rbTrainingKit.setVisibility(View.GONE);
        btnQrCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Liquid.QRCode = "";
                String IP = "192.168.254.106";
                AseyelAPI = "http://"+IP+"/aseyel/event/php/api/eventLunch.php";
                if(rbLunch.isChecked()){
                    AseyelAPI = "http://"+IP+"/aseyel/event/php/api/eventLunch.php";
                }

                if(rbTraining.isChecked()){
                    AseyelAPI = "http://"+IP+"/aseyel/event/php/api/eventTraining.php";
                }

                Intent i = new Intent(getApplicationContext(), QRCodeScannerActivity.class);
                startActivity(i);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    Filename = "1"+Liquid.imageFormat;
                    mFile = mLiquidFile.Directory("1",Filename.trim(),Subfolder);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    mUri.add(Uri.fromFile(mFile));
                    startActivityForResult(intent,CAM_REQUEST);

                }
                catch (Exception e){
                   Log.e(TAG,"Error ",e);
                }
            }
        });
        tvDetails.setText("ID:\nFullname: ");
    }
    public void onResume() {
        super.onResume();
        if( Liquid.QRCode == ""){

        }else{
            PostQRCode(Liquid.QRCode);
            Log.i(TAG,Liquid.QRCode);
        }

    }

    public void PostQRCode(String QRCode){
        HttpHandler mHttphandler = new HttpHandler();
        JSONObject final_data = new JSONObject();
        try {
            final_data.put("UserId",QRCode);
            //Kits
            doBulkPostToServerUnecrypted(AseyelAPI,final_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void doBulkPostToServerUnecrypted(
                                             String ApiLink,
                                             final JSONObject final_data
    ){

        try{
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            StringRequest mStringRequest   = new StringRequest (Request.Method.POST,ApiLink ,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,response);
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    JSONObject final_response =  Liquid.NotEncryptedStringToJsonObject(response);
                    try {
                        JSONObject data_details =  Liquid.NotEncryptedStringToJsonObject(final_response.getString("data"));
                        tvDetails.setText("ID: "+ data_details.getString("UserId") + "\n"+
                                          "Fullname: "+ data_details.getString("Firstname") + " "+ data_details.getString("Lastname")
                        );
                        if(final_response.getString("result") == "true"){
                            Liquid.showDialogInfo(PWPersonIdentifierQRodeActivity.this,"Valid",final_response.getString("message"));
                        }else{
                            Liquid.showDialogInfo(PWPersonIdentifierQRodeActivity.this,"Invalid",final_response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Liquid.showDialogError(PWPersonIdentifierQRodeActivity.this,"Invalid", String.valueOf(error));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return final_data == null ? null : final_data.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", final_data, "utf-8");
                        return null;
                    }
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(PWPersonIdentifierQRodeActivity.this);
            requestQueue.add(mStringRequest);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }
}
