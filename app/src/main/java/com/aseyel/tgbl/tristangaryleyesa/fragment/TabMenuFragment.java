package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.ChangePasswordActivity;
import com.aseyel.tgbl.tristangaryleyesa.LoginActivity;
import com.aseyel.tgbl.tristangaryleyesa.QRCodeActivity;
import com.aseyel.tgbl.tristangaryleyesa.QRCodeScannerActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.SettingsActivity;
import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.UpdateHostActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.MainMenuAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.ExternalDatabaseHelper;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ExternalDbModel;
import com.aseyel.tgbl.tristangaryleyesa.model.LiquidModel;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TabMenuFragment extends Fragment {
    public static final String TAG = TabMenuFragment.class.getSimpleName();

    private ArrayList<HashMap<String,String>> List;
    private ArrayList<HashMap<String,String>> ListDetails;
    private MainMenuAdapter Adapter;
    private RecyclerView rvList;
    private View mView;
    public static ExternalDatabaseHelper mExternalDatabaseHelper;
    private ProgressDialog mProgressDialog;
    private boolean final_result = false;
    private String version ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_menu, container, false);
        setup(mView);
        GetMainMenu();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if( Liquid.QRCode != ""){
//            PostQRCode(Liquid.QRCode);
            Log.i(TAG,Liquid.QRCode);
        }

    }

    private void setup(View view){
        try{
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new MainMenuAdapter(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvList);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(llm);
            rvList.setAdapter(Adapter);
            version = getResources().getString(R.string.app_version);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }

    private void GetMainMenu(){
        ArrayList<HashMap<String,String>> final_response = new ArrayList<>();
        HashMap<String,String> Logout = new HashMap<>();
        HashMap<String,String> Import = new HashMap<>();
        HashMap<String,String> Backup = new HashMap<>();
        HashMap<String,String> About = new HashMap<>();
        HashMap<String,String> ClearData = new HashMap<>();
        HashMap<String,String> Settings = new HashMap<>();
        HashMap<String,String> ChangePassword = new HashMap<>();
        HashMap<String,String> OfficialBusiness = new HashMap<>();
        //mashe try
        HashMap<String,String> ChangeHost = new HashMap<>();

        Backup.put("Id","001");
        Backup.put("Description","Data Back Up");
        Backup.put("FilePath","Back Up");

        Import.put("Id","002");
        Import.put("Description","Data Import");
        Import.put("FilePath","Import");

        Logout.put("Id","003");
        Logout.put("Description","Logout");
        Logout.put("FilePath","Logout");

        About.put("Id","004");
        About.put("Description","About");
        About.put("FilePath","About");

        Settings.put("Id","006");
        Settings.put("Description","Settings");
        Settings.put("FilePath","Settings");

        ChangePassword.put("Id","007");
        ChangePassword.put("Description","Change Password");
        ChangePassword.put("FilePath","Change Password");
        final_response.add(ChangePassword);

        OfficialBusiness.put("Id","008");
        OfficialBusiness.put("Description","Official Business");
        OfficialBusiness.put("FilePath","Official Business");
//        final_response.add(OfficialBusiness);

        //mashe try
        if(Liquid.User.equals("000") || Liquid.User.equals("ums_admin")){
            ChangeHost.put("Id","009");
            ChangeHost.put("Description","Change Host");
            ChangeHost.put("FilePath","Change Host");
            final_response.add(ChangeHost);
        }

        if(Liquid.User.equals("000") || Liquid.User.equals("ums_admin")){
            ClearData.put("Id","005");
            ClearData.put("Description","Clear Data");
            ClearData.put("FilePath","Clear Data");
            final_response.add(ClearData);
        }


        final_response.add(Backup);
        final_response.add(Import);
        final_response.add(Settings);
        final_response.add(About);
        final_response.add(Logout);

        Adapter.updateItems(true,final_response);

    }

    public void DoQRScan(){
        try{
            //go to change password activity
            Liquid.QRCode = "";
            Intent i = new Intent(getActivity(), QRCodeScannerActivity.class);
            getActivity().startActivity(i);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }

    public void ChangePassword(){
        try{
            //go to change password activity
            Intent i = new Intent(getActivity(), ChangePasswordActivity.class);
            getActivity().startActivity(i);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }
    //mashe try
    public void ChangeHost(){
        try{
            //go to Update/change host activity
            Intent i = new Intent(getActivity(), UpdateHostActivity.class);
            getActivity().startActivity(i);
        }catch(Exception e){
            Log.e(TAG,"mashe update host error ",e);
        }
    }

    public void Logout(){
        Intent i = new Intent(getActivity(), LoginActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().startActivity(i);
        getActivity().finish();
    }

    public void DoLogout(){
        try{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            boolean result = false;
                            result = AccountModel.DoUpdateAccountToOut();
                            if(!result){
                                Liquid.showDialogError(getActivity(),"Invalid",Liquid.DefaultErrorMessage);
                            }else{

                                Logout();

                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }
    public void Settings(){

        try{
            Intent i = new Intent(getActivity(), SettingsActivity.class);
            getActivity().startActivity(i);
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }


    }
    public void ClearData(){

        try{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            final_result = false;
                            new ClearData().execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("This will delete all saved data and file of UMS!\n Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }


    }
    public void CreateBackUp(){

        try {


            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            final_result = false;
                            new CreateBackUp().execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Please import before back up the data.\n Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
            Liquid.showDialogInfo(mView.getContext(),"Invalid",Liquid.DefaultErrorMessage);
        }

    }

    public void ImportData(){

        try {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            final_result = false;
                             new ImportData().execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
            Liquid.showDialogInfo(mView.getContext(),"Invalid",Liquid.DefaultErrorMessage);
        }

    }

    public void GoToAbout(){
        boolean result = false;
        try {
            /*Intent i = new Intent(getActivity(), DeliveryMainActivity.class);
            getActivity().startActivity(i);
            Intent i = new Intent(getActivity(), SurveyActivity.class);
            getActivity().startActivity(i); */
           Liquid.showDialogInfo(getContext(),"About",
                   "Develop by Tristan Gary Leyesa \n In case of problem with the app kindly send  an email to tgbleyesa@gmail.com ("+version+")");

        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }

    }

    public class CreateBackUp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{

                final_result = SplashActivity.mDatabaseHelper.ExportDatabase(Liquid.LiquidDBPath);


            }catch(Exception e){
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

                if(!final_result){
                    Liquid.showDialogInfo(mView.getContext(),"Invalid","Can't create back up!");
                }else{
                    Liquid.showDialogInfo(mView.getContext(),"Valid","Back up successfully!");
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
    public class ClearData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{

                final_result = AccountModel.DoUpdateAccountToOut();

                Liquid.deleteRecursive(new File("sdcard/UMS/UMS_"+Liquid.Client+"_Picture"));
                Liquid.deleteRecursive(new File("sdcard/UMS/UMS_"+Liquid.Client+"_Signature"));


            }catch(Exception e){
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
                if(!final_result){
                    Liquid.showDialogError(getActivity(),"Invalid",Liquid.DefaultErrorMessage);
                }else{
                    SplashActivity.mDatabaseHelper.ClearData();
                    Logout();
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



    public class ImportData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
                try{
                     String[] Columns;
                     String[] Values;
                     String Table;
                    //Get Table
                    mExternalDatabaseHelper = new ExternalDatabaseHelper(getContext());
                    Cursor result_tables = ExternalDbModel.GetAllTable();

                    if(result_tables.getCount() == 0){
                        Log.i(TAG," No Table");
                    }
                    while(result_tables.moveToNext()){

                        Table = result_tables.getString(0);

                        if(Table.equals("products") ||
                                Table.equals("sovi_product") ||
                                Table.equals("ums_account") ||
                                Table.equals("product_category") ||
                                Table.equals("android_metadata") ||
                                Table.equals("rates_description") ||
                                Table.equals("ItemType") ||
                                Table.equals("ref_disconnection_remarks") ||
                                Table.equals("ref_delivery_remarks") ){
                            Log.i(TAG,"This is no need to migrate : "+Table);
                        }else{
                            //Get Columns
                            Cursor result_columns = ExternalDbModel.GetColumns(Table);
                            if(result_columns.getCount() == 0){
                                Log.i(TAG,Table+" No Columns");
                            }
                            Columns = new String[result_columns.getCount()];
                            int x = 0;

                            while(result_columns.moveToNext()) {

                                    Columns[x] = "["+result_columns.getString(1)+"]";
                                    x++;
                            }
                            result_columns.close();
                            //Get Data
                            Cursor result_data = ExternalDbModel.GetData(Table);
                            if(result_data.getCount() == 0){
                                Log.i(TAG,Table+" No Data");
                            }
                            Log.i(TAG,Table+" Migrating :"+result_data.getCount());
                            while(result_data.moveToNext()) {
                                Values = new String[Columns.length];
                                for(int a = 0; a < Columns.length; a++){
                                    //data.put(Columns[a], result_data.getString(a));
                                    Values[a] =  result_data.getString(a);
                                }

                                final_result = LiquidModel.DoImportData(Table,Columns,Values);
                            }
                            result_data.close();
                        }

                    }
                    result_tables.close();
                }catch(Exception e){
                    Log.e(TAG,"Error : ",e);
                    Liquid.showDialogInfo(mView.getContext(),"Invalid","There is no back file!");
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                if(!final_result){
                    Liquid.showDialogInfo(mView.getContext(),"Invalid","There is no back up to be imported");
                }else{
                    Liquid.showDialogInfo(mView.getContext(),"Valid","Data are imported successfully!");
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


}

