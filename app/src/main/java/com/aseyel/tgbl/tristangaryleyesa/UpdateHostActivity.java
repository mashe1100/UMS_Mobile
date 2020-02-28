package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.DatabaseHelper;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.data.LiquidReference;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabMenuFragment;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;
import com.aseyel.tgbl.tristangaryleyesa.model.HostModel;

import org.json.JSONException;
import org.json.JSONObject;
/*  Mariesher Zapico
    Feb 7, 2020
    Create an Activity for Host Change
    */
public class UpdateHostActivity extends BaseFormActivity {
    private static final String TAG = UpdateHostActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView txtQuestion;
    private EditText txtHostname;
    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_host);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    //Create a submit function for host change
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_form_submit:
                new DoChangeHost().execute(txtHostname.getText().toString(),
                                            txtUsername.getText().toString(),
                                            txtPassword.getText().toString());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(){
        //initialize the textBox
        SplashActivity.Host();
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtHostname = (EditText) findViewById(R.id.txtHostname);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtHostname.setText(Liquid.umsUrl);
        txtUsername.setText(Liquid.Username);
        txtPassword.setText(Liquid.Password);
        txtQuestion.setText("You can update the host here.");
    }
    //Create a function for Host change
    public class DoChangeHost extends AsyncTask<String,Void,Void> {
        JSONObject result = new JSONObject();
        JSONObject postData = new JSONObject();
        HttpHandler sh = new HttpHandler();
        boolean updateLocal = false;
        String Hostname = "";
        String Username = "";
        String Password = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateHostActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                Hostname = params[0];
                Username = params[1];
                Password = params[2];
                //if the old host is not equal to current host it will return false
                if(Hostname.equals("")){
                    result.put("result",false);
                    result.put("message","There is no host encoded!");
                }else{
                    //change the host
                    //save the host on the local
                    updateLocal = HostModel.DoUpdateHost(
                            "1",
                            Hostname,
                            Username,
                            Password
                    );

                    if(!updateLocal){
                        //return if something wrong in saving to local
                        result.put("result",false);
                        result.put("message","An error has occured! (Change Host)");
                    }else{
                        result.put("result",true);
                        result.put("message","Successfully change host!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            try {
                if(result.getBoolean("result") == false){
                    Liquid.showDialogError(UpdateHostActivity.this,"Warning",result.get("message").toString());
                }else{
                    Liquid.showDialogClose(UpdateHostActivity.this,"Information",result.get("message").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
