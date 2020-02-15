package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends BaseFormActivity {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    private Liquid.POSTApiData mPOSTApiData;
    private ProgressDialog pDialog;
    private TextView txtQuestion;
    private EditText txtOldPassword;
    private EditText txtNewPassword;
    private EditText txtConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:

                new DoChangePassword().execute(txtOldPassword.getText().toString(),
                        txtNewPassword.getText().toString(),
                        txtConfirmPassword.getText().toString());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void init(){
        //initialize the textBox
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtOldPassword = (EditText) findViewById(R.id.txtOldPassword);
        txtNewPassword = (EditText) findViewById(R.id.txtNewPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtQuestion.setText("Please input the current password and make a complicated password.");
    }

    public class DoChangePassword extends AsyncTask<String,Void,Void> {
        JSONObject result = new JSONObject();
        JSONObject postData = new JSONObject();
        HttpHandler sh = new HttpHandler();
        boolean updateLocal = false;
        String CurrentPassword = "";
        String OldPassword = "";
        String NewPassword = "";
        String ConfirmPassword = "";
        String jsonStr = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePasswordActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                OldPassword = params[0];
                NewPassword = params[1];
                ConfirmPassword = params[2];
                //get the current password
                Cursor accountsData = AccountModel.GetAccount(Liquid.User);
                if(accountsData.getCount() == 0){

                }
                while(accountsData.moveToNext()){
                    CurrentPassword = accountsData.getString(1);
                }
                //if the old password is not equal to current password it will return false
                 Log.i(TAG,"Old Password: "+ OldPassword);
                Log.i(TAG,"Hash Old Password: "+ Liquid.md5(OldPassword));
                Log.i(TAG,"Current Password: "+ CurrentPassword);


                if(!Liquid.md5(OldPassword).equals(CurrentPassword)){
                    result.put("result",false);
                    result.put("message","Incorrect Password!");
                    return null;
                }
                //Control if the new password if equal to confirm password
                if(!NewPassword.equals(ConfirmPassword)){
                    result.put("result",false);
                    result.put("message","Password not match!");
                }else{
                    //change the password
                    //save the password on the local
                    updateLocal = AccountModel.DoUpdatePassword(
                            Liquid.User,
                            Liquid.md5(NewPassword)
                    );
                    if(!updateLocal){
                        //return if something wrong in saving to local
                        result.put("result",false);
                        result.put("message","An error has occured! (Change Password)");
                    }else{
                        //save the password on the main server
                        postData.put("class","profileController");
                        postData.put("method","changepassword");
                        Log.i(TAG,"Sino ang user?"+ Liquid.User);
                        postData.put("user", Liquid.User);
                        postData.put("username", "tgbleyesa");
                        postData.put("old_password", OldPassword);
                        postData.put("new_password",NewPassword);
                        mPOSTApiData = new Liquid.POSTApiData("profile/php/profile-route.php");
                        jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, postData);
                        Log.i(TAG,"Tristan xxx"+ jsonStr);
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
                    Liquid.showDialogError(ChangePasswordActivity.this,"Warning",result.get("message").toString());
                }else{
                    Liquid.showDialogNext(ChangePasswordActivity.this,"Information",result.get("message").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
