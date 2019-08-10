package com.aseyel.tgbl.tristangaryleyesa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private final Handler mHandler = new Handler();
    private  Runnable mRunnable = null;
    private  Thread mThread = null;
    JSONArray final_result_user;
    Liquid.ApiDataField mApiData;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    String Username;
    String Password;
    boolean result;
    private ProgressDialog mProgressDialog;
    public boolean auth_result = false;
    private int RefreshUpdateUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();


        AutoLogin("ums_job");
//        AutoLogin("ums_delivery");

    }

    private void BarcodeLogin(String barcode){
        try{
            Username = barcode;
            Password = barcode;
            new GetUserListDetails().execute();

            new UMSAuth().execute(Username,Password,"ums_job");
        }catch (Exception e){}
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getCharacters() != null && !event.getCharacters().isEmpty()){

            try{

                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(event.getCharacters())
                            .setTitle(event.getCharacters())
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {




                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    dialog.cancel();
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

            }catch (Exception e){}
        }

        return super.dispatchKeyEvent(event);
    }

    private void UpdateUser(){
        new GetUserListDetails().execute();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                while (RefreshUpdateUser == 0) {
                    try {
                        Thread.sleep(10000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                new GetUserListDetails().execute();
                                // Write your code here to update the UI.
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(TAG,"Tristan Gary Leyesa",e);
                    }
                }
            }
        };
        mThread = new Thread(mRunnable);
        mThread.start();
    }
    private void setup(){
        try{

            etUsername  = (EditText) findViewById(R.id.etUsername);
            etUsername.addTextChangedListener(new TextWatcher() {
                int charcount = 0;
                String character = "";
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    charcount = etUsername.getText().toString().length();
                    character = etUsername.getText().toString();
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(etUsername.getText().toString().length() != 0)
                        if(etUsername.getText().toString().length() - charcount > 2){

                            if(charcount !=0)
                                etUsername.setText(etUsername.getText().toString().substring(charcount,etUsername.getText().toString().length()));

                            BarcodeLogin(etUsername.getText().toString());
                            etUsername.setText("");
                        }
                }
            });
            etPassword = (EditText) findViewById(R.id.etPassword);
            etPassword.addTextChangedListener(new TextWatcher() {
                int charcount = 0;
                String character = "";
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    charcount = etPassword.getText().toString().length();
                    character = etPassword.getText().toString();
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(etPassword.getText().toString().length() != 0)
                        if(etPassword.getText().toString().length() - charcount > 2){

                            if(charcount !=0)
                                etPassword.setText(etPassword.getText().toString().substring(charcount,etPassword.getText().toString().length()));

                            BarcodeLogin(etPassword.getText().toString());
                            etPassword.setText("");
                        }
                }
            });
            btnLogin = (Button) findViewById(R.id.btnLogin);
            final_result_user = new JSONArray();

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        String status = "";

                        Username = etUsername.getText().toString();
                        Password = etPassword.getText().toString();
                        //result = UMSAuthentication(Username,Password);
                        new GetUserListDetails().execute();

                        new UMSAuth().execute(Username,Password,"ums_job");
//                        new UMSAuth().execute(Username,Password,"ums_delivery");



                        //new UMSAuthAPI().execute(Username,Password,"ums_ai");
                        //new UMSAuthAPI().execute(Username,Password,"ums_logistics");
                        //new UMSAuth().execute(Username,Password,"ums_delivery");

                }
            });
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }

        //getting the user on the server
        //UpdateUser();
    }

    private void Login(String type){
        Intent i =  new Intent(LoginActivity.this, MainActivity.class);
        switch(type){
            case "ums_job":
                 i = new Intent(LoginActivity.this, MainActivity.class);
                break;
            case "ums_logistics":
                 i = new Intent(LoginActivity.this, LogisticsActivity.class);
                break;
            case "ums_ai":
                 i = new Intent(LoginActivity.this, AthenaActivity.class);
                break;
            case "ums_delivery":
               // i = new Intent(LoginActivity.this, DeliveryMainActivity.class);
                i = new Intent(LoginActivity.this, DeliveryMainActivity.class);
                break;
        }


        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        finish();
    }

    private void AutoLogin(String type){
        Cursor result = AccountModel.GetLoginAccount();
        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){


                Liquid.User = result.getString(0);
                Login(type);
            }
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }
    public boolean UMSAuthentication(String Username,String Password){
        Cursor result = AccountModel.GetAccount(Username);
        try
        {
            if(result.getCount() == 0){
                return false;
            }
            while(result.moveToNext()){


                if(result.getString(0).equals(Username) && result.getString(1).equals(Liquid.md5(Password))){
                    return true;
                }else{
                    return false;
                }
            }
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return false;
        }
        return true;
    }





    public class UMSAuthAPI extends AsyncTask<String, Void, Void> {

        String type;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try{
                String Username = params[0];
                String Password = params[1];
                type = params[2];
                HttpHandler sh = new HttpHandler();
                Liquid.POSTUMSAPI mPOSTUMSAPI;
                //GetLocation
                String jsonStr = sh.makeServiceCall("http://gd.geobytes.com/GetCityDetails?callback=?");
                jsonStr = jsonStr.replace("?","");
                jsonStr = jsonStr.replace("(","");
                jsonStr = jsonStr.replace(")","");
                jsonStr = jsonStr.replace(";","");
                Log.i(TAG,jsonStr);
                JSONObject locationObject = new JSONObject(jsonStr);
                //UMS Login
                JSONObject dataObject = new JSONObject();
                dataObject.put("username",Username);
                dataObject.put("password",Password);
                dataObject.put("ip",locationObject.getString("geobytesipaddress"));
                dataObject.put("latitude",locationObject.getString("geobyteslatitude"));
                dataObject.put("longitude",locationObject.getString("geobyteslongitude"));
                dataObject.put("country",locationObject.getString("geobytescountry"));
                dataObject.put("region",locationObject.getString("geobytesregion"));
                dataObject.put("os","Android OS");
                dataObject.put("browser","None");
                dataObject.put("ismobile","true");
                dataObject.put("class","login");
                dataObject.put("method","LoginAuthentication");
                mPOSTUMSAPI = new Liquid.POSTUMSAPI("auth/php/login-route.php");
                jsonStr = sh.makeServicePostCall(mPOSTUMSAPI.API_Link,dataObject);
                JSONObject resultObject = new JSONObject(jsonStr);

                if(resultObject.getString("result").equals("success")){
                    auth_result = true ;
                    boolean query_result = false;
                    JSONObject infoObject = new JSONObject(resultObject.getString("data"));
                    String UserId = infoObject.getString("U_ID");
                    Username = infoObject.getString("U_Username");
                    Password = infoObject.getString("U_Password");
                    String Client = "UMS";
                    String branch = infoObject.getString("P_Branch");
                    String firstname = infoObject.getString("firstname");
                    String lastname = infoObject.getString("lastname");
                    String middlename = infoObject.getString("middlename");
                    String name = lastname + ", "+ firstname +" "+middlename;
                    String position = infoObject.getString("position");
                    query_result = AccountModel.doSubmitAccountDetails(
                            UserId,
                            Username,
                            Password,
                            Client,
                            branch,
                            name,
                            lastname,
                            firstname,
                            middlename,
                            position
                    );

                }else{
                    auth_result = false;
                }
            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if(!auth_result){
                    Liquid.showDialogInfo(LoginActivity.this,"Invalid","Username / Password");
                }else{
                    auth_result = AccountModel.DoUpdateAccountToOut();
                    if(!auth_result){
                        Liquid.showDialogInfo(LoginActivity.this,"Invalid","Error has Occured!");
                    }else{
                        auth_result = AccountModel.DoUpdateAccountAutoLogin(Username);
                        if(!auth_result) {
                            Liquid.showDialogInfo(LoginActivity.this,"Invalid","Error has Occured!");
                        }else{
                            Liquid.User = Username;
                            Login(type);
                        }
                    }
                }

            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }
        }

        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }
    public class UMSAuth extends AsyncTask<String, Void, Void> {
        String type;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try{
                String Username = params[0];
                String Password = params[1];
                type =  params[2];
                Cursor result = AccountModel.GetAccount(Username);
                if(result.getCount() == 0){
                    auth_result = false;
                }
                while(result.moveToNext()){
                    if(result.getString(0).equals(Username) && result.getString(1).equals(Liquid.md5(Password))){
                        auth_result = true;
                    }else{
                        auth_result = false;
                    }
                }

            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


                if(!auth_result){
                    Liquid.showDialogInfo(LoginActivity.this,"Invalid","Username / Password");
                }else{
                    auth_result = AccountModel.DoUpdateAccountToOut();
                    if(!auth_result){
                        Liquid.showDialogInfo(LoginActivity.this,"Invalid","Error has Occured!");
                    }else{
                        auth_result = AccountModel.DoUpdateAccountAutoLogin(Username);
                        if(!auth_result) {
                            Liquid.showDialogInfo(LoginActivity.this,"Invalid","Error has Occured!");
                        }else{
                            Liquid.User = Username;
                            Login(type);
                        }
                    }
                }
            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }
        }

        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }

    public class GetUserListDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(LoginActivity.this);
//            mProgressDialog.setMessage("Please wait...");
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();

            try{
                mApiData = new Liquid.ApiDataField("field-route.php","fieldController","get_rover_users","client="+ Liquid.Client);

                String jsonStr = sh.makeServiceCall(mApiData.API_Link);

                // Making a request to url and getting response
                if (jsonStr != null) {
                    try {
                        JSONArray mJsonArray = new JSONArray(jsonStr);

                        // looping through All Data
                        for (int i = 0; i < mJsonArray.length(); i++) {
                            JSONObject c = mJsonArray.getJSONObject(i);
                            JSONObject data = new JSONObject();
                            data.put("UserID",c.getString("id"));
                            data.put("Username",c.getString("id"));
                            data.put("Password",c.getString("id"));
                            data.put("Client", c.getString("client"));
                            data.put("branch", c.getString("branch"));
                            data.put("name", c.getString("name"));
                            data.put("lastname", c.getString("lastname"));
                            data.put("firstname", c.getString("firstname"));
                            data.put("middlename", c.getString("middlename"));
                            data.put("position", c.getString("position"));

                            final_result_user.put(data);

                        }

                        boolean query_result = false;
                        for(int a = 0; a < final_result_user.length(); a++){
                            JSONObject c = final_result_user.getJSONObject(a);
                            String UserId = c.getString("UserID");
                            String Username = c.getString("Username");
                            String Password = Liquid.md5(c.getString("Password"));
                            String Client = c.getString("Client");
                            String branch = c.getString("branch");
                            String name = c.getString("name");
                            String firstname = c.getString("lastname");
                            String lastname = c.getString("firstname");
                            String middlename = c.getString("middlename");
                            String position = c.getString("position");


                            query_result = AccountModel.doSubmitAccountDetails(
                                    UserId,
                                    Username,
                                    Password,
                                    Client,
                                    branch,
                                    name,
                                    lastname,
                                    firstname,
                                    middlename,
                                    position
                            );
                        }
                        if (query_result == true) {
                            //Liquid.showDialogNext(LoginActivity.this, "Valid", "Successfully Saved!");
                            Log.i(TAG, "Successfully Saved!");
                        } else {
                            Log.i(TAG, "Unsuccessfully Saved!" );
                            //Liquid.showDialogError(LoginActivity.this, "Invalid", "Unsuccessfully Saved!");
                        }

                    } catch (final JSONException e) {
                        Log.e(TAG,"Error : ",e);
                    }
                } else {
                    Log.e(TAG,"No Data");
                }
            } catch (Exception e){
                Log.e(TAG,"Error: ",e);
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{

//                if (mProgressDialog.isShowing())
//                    mProgressDialog.dismiss();

            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }

        }

        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }


}

