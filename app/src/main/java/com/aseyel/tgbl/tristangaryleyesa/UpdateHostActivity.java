package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
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
    private EditText txtOldHost;
    private EditText txtNewHost;
    private EditText txtConfirmHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_host);

        Log.i(TAG, "mashe test db file path: " + DatabaseHelper.DB_FILEPATH);

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

                new DoChangeHost().execute(txtOldHost.getText().toString(),
                        txtNewHost.getText().toString(),
                        txtConfirmHost.getText().toString());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void init(){
        //initialize the textBox
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtOldHost = (EditText) findViewById(R.id.txtOldHost);
        txtNewHost = (EditText) findViewById(R.id.txtNewHost);
        txtConfirmHost = (EditText) findViewById(R.id.txtConfirmHost);
        txtQuestion.setText("Please input the current host to change new host");
    }
    //Create a function for Host change
    public class DoChangeHost extends AsyncTask<String,Void,Void> {
        JSONObject result = new JSONObject();
        JSONObject postData = new JSONObject();
        HttpHandler sh = new HttpHandler();
        boolean updateLocal = false;
        String CurrentHost = "";
        String OldHost = "";
        String NewHost = "";
        String ConfirmHost = "";


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

                OldHost = params[0];
                NewHost = params[1];
                ConfirmHost = params[2];
                //get the current host

                Cursor hostData = HostModel.GetHostID(Liquid.id);
                if(hostData.getCount() == 0){

                }
                while(hostData.moveToNext()){
                    CurrentHost = hostData.getString(1);
                }
                //if the old host is not equal to current host it will return false
                Log.i(TAG,"mashe Old host: "+ OldHost);
                Log.i(TAG,"mashe Current host:  "+ CurrentHost);

                if(!OldHost.equals(CurrentHost)){
                    result.put("result",false);
                    result.put("message","Incorrect Host!");
                    return null;
                }
                if(NewHost.equals(null)||NewHost.equals("")){
                    result.put("result",false);
                    result.put("message","Host is empty!");
                }
                //Control if the new host is equal to confirm host
                if(!NewHost.equals(ConfirmHost)||NewHost.equals(null)||NewHost.equals("")){
                    result.put("result",false);
                    result.put("message","Host not match!");
                }else{
                    //change the host
                    //save the host on the local
                    updateLocal = HostModel.DoUpdateHost(
                            Liquid.id,
                            NewHost
                    );

                    if(!updateLocal){
                        //return if something wrong in saving to local
                        result.put("result",false);
                        result.put("message","An error has occured! (Change Host)");
                    }else{

                        postData.put("method","changehost");
                        postData.put("id",Liquid.id);
                        postData.put("OldHost", OldHost);
                        postData.put("NewHost",NewHost);
                        result.put("result",true);
                        result.put("message","Successfully updated");
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
                    Liquid.showDialogNext(UpdateHostActivity.this,"Information",result.get("message").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
