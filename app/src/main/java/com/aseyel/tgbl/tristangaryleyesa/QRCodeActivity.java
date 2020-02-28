package com.aseyel.tgbl.tristangaryleyesa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class QRCodeActivity extends AppCompatActivity {
    private static final String TAG = QRCodeActivity.class.getSimpleName();
    @BindView (R.id.btnQrCode)
    Button btnQrCode;
    @BindView (R.id.rbTrainingKit)
    RadioButton rbTrainingKit;
    @BindView (R.id.rbLunch)
    RadioButton rbLunch;
    @BindView (R.id.rbTraining)
    RadioButton rbTraining;
    TextView txtGuide;
    String AseyelAPI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        setup();
    }

    private void setup(){
        btnQrCode = (Button) findViewById(R.id.btnQrCode);
        rbTrainingKit = (RadioButton) findViewById(R.id.rbTrainingKit);
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
                String IP = "192.168.254.108";
                AseyelAPI = "http://"+IP+"/aseyel/event/php/api/eventLunch.php";
                if(rbLunch.isChecked()){
                    AseyelAPI = "http://"+IP+"/aseyel/event/php/api/eventLunch.php";
                }
                if(rbTrainingKit.isChecked()){
                    AseyelAPI =  "http://"+IP+"/aseyel/lib/php/api/trainingKits.php";
                }
                if(rbTraining.isChecked()){
                    AseyelAPI = "http://"+IP+"/aseyel/event/php/api/eventTraining.php";
                }
                Intent i = new Intent(getApplicationContext(), QRCodeScannerActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
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
            mHttphandler.doBulkPostToServerUnecrypted(QRCodeActivity.this,AseyelAPI,final_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
