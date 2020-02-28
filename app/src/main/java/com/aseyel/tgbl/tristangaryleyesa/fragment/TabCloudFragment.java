package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.aseyel.tgbl.tristangaryleyesa.MainActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.SplashActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.JobOrderAdapater;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid.ApiData;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


import butterknife.BindView;

import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.Client;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.CokeCustomerColumns;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.CustomerDeliveryColumns;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.CustomerDisconnectionDownloadColumns;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.CustomerReadingColumns;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.LiquidColumns;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.LiquidTable;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.RatesColumns;
import static com.aseyel.tgbl.tristangaryleyesa.data.Liquid.ServiceType;


public class TabCloudFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "CloudFragment";
    private int job_id;
    private Fragment fragment;
    private String[] GetColumns;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> JobOrderList;
    ArrayList<HashMap<String, String>> JobOrderListDetails;
    ArrayList<HashMap<String, String>> Rates;
    boolean animated = true;
    private boolean final_result = false;
    private String mJobOrderId;
    private String mdate;
    private String mtitle;
    private String mdetails;
    JSONArray final_result_customer;
    JobOrderAdapater mJobOrderAdapter;
    Liquid.GETUMSAPI mGETUMSAPI;
    ApiData mApiData;
    View view;
    @BindView(R.id.rvJobOrderList)
    RecyclerView rvJobOrderList;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_cloud, container, false);
        //Connection Setting Up
        setupJobOrder(view);

        try{
            new GetJobOrderList().execute("true","");
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
            Liquid.showDialogError(view.getContext(),"Connection Error","Can't Connect to the Server");
        }
        return view;
    }

    public void GetJobOrder(String JobId){
        JobOrderList.clear();
        new GetJobOrderList().execute("false",JobId);
    }

    private void setupJobOrder(View view) {
        try {
            //Initialization
            JobOrderList = new ArrayList<>();
            JobOrderListDetails = new ArrayList<>();
            Rates = new ArrayList<>();
            mJobOrderAdapter = new JobOrderAdapater(this);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            rvJobOrderList = (RecyclerView) view.findViewById(R.id.rvJobOrderList);
            job_id = 0;

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            //Setting up
            rvJobOrderList.setHasFixedSize(true);
            rvJobOrderList.setLayoutManager(llm);
            rvJobOrderList.setAdapter(mJobOrderAdapter);

        }catch(Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG ).show();
        }
        //mJobOrderAdapter = new JobOrderAdapater();
        //rvJobOrderList.setAdapter(mJobOrderAdapter);
    }

    public void DoOtherDownload(String JobOrderId,String date,String title,String details,String cycle){
        try {
            mJobOrderId = JobOrderId;
            Liquid.Cycle = cycle;
            mdate = date;
            mtitle = title;
            mdetails = details;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            final_result = false;

                                new GetReadandBillRatesDetails().execute(Liquid.Cycle);

                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do want to download the Rates?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoDownload(String JobOrderId,String date,String title,String details,String cycle){
        try {
            mJobOrderId = JobOrderId;
            mdate = date;
            mtitle = title;
            mdetails = details;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            final_result = false;
                            if(doDownloadJobOrder(mJobOrderId,mdate,mtitle,mdetails) == true) {
                                new GetJobOrderListDetails().execute(mJobOrderId);
                                dialog.cancel();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            if(doDownloadJobOrder(mJobOrderId,mdate,mtitle,mdetails) == true) {
                                new GetUnAccomplishmentJobOrder().execute(mJobOrderId);
                                dialog.cancel();
                            }
                            break;
                        case DialogInterface.BUTTON_NEUTRAL:
                            if(doDownloadJobOrder(mJobOrderId,mdate,mtitle,mdetails) == true) {
                                new GetInvalidJobOrder().execute(mJobOrderId);
                                dialog.cancel();
                            }
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("What will you download?").setPositiveButton("All", dialogClickListener)
                    .setNegativeButton("Not Done", dialogClickListener)
                    .setNeutralButton("Invalid", dialogClickListener).show();

            AlertDialog alert = builder.create();

            if(alert.isShowing()){
                alert.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void MigrateRates(ArrayList<HashMap<String, String>> Rates){
        try{
            boolean result = false;
            Liquid.LiquidValues = new String[Liquid.RatesColumns.length];
            Liquid.LiquidColumns = Liquid.RatesColumns;

            for(int a = 0; a < Rates.size(); a++) {
                for(int b = 0; b < Liquid.LiquidColumns.length; b++) {
                    Log.i(TAG, String.valueOf(Liquid.LiquidColumns[b]));
                    switch(Liquid.LiquidColumns[b]){
                        case "Rates_SysEntryDate":
                            Liquid.LiquidValues[b] = Liquid.currentDateTime();
                            break;
                        case "Rates_ModifiedDate":
                            Liquid.LiquidValues[b] = Liquid.currentDateTime();
                            break;
                        case "Rates_ModifiedBy":
                            Liquid.LiquidValues[b] = Liquid.User;
                            break;
                        default:
                            Liquid.LiquidValues[b] = Rates.get(a).get(Liquid.LiquidColumns[b]);
                    }

                }
                result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault("rates",Liquid.LiquidColumns,Liquid.LiquidValues);
            }

            if(result == true){
                Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Downloaded!");
            } else {
                Liquid.showDialogError(view.getContext(), "Invalid", "Unsuccessfully Downloaded!");
            }

        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return;
        }
    }

    public boolean doDownloadJobOrder(String id,String date,String title,String details){
        try{
            boolean result = false;
            Liquid.LiquidColumns = Liquid.joborders;
            Liquid.LiquidValues = new String[]{ id, Liquid.Client, details, title,date, Liquid.currentDateTime() };
            result = SplashActivity.mDatabaseHelper.SqliteReplaceQuery("joborder",Liquid.LiquidColumns,Liquid.LiquidValues);
            return result;
        }catch(Exception e){
            Log.e(TAG, "Error", e);
            return false;
        }
    }

    public void doDownloadJobOrderDetails(ArrayList<HashMap<String, String>> JobOrdersDetails){
        try{
            boolean result = false;
            switch(Liquid.ServiceType){
                case "TRACKING":
                    Liquid.LiquidValues = new String[CokeCustomerColumns.length];
                    Liquid.LiquidColumns = CokeCustomerColumns;
                    Liquid.LiquidTable = "customer";
                    break;
                case "MESSENGER":
                    Liquid.LiquidValues = new String[CustomerDeliveryColumns.length];
                    Liquid.LiquidColumns = CustomerDeliveryColumns;
                    Liquid.LiquidTable = "customer_delivery_downloads";
                    break;
                case "READ AND BILL":
                    Liquid.LiquidValues = new String[CustomerReadingColumns.length];
                    Liquid.LiquidColumns = CustomerReadingColumns;
                    Liquid.LiquidTable = "customer_reading_downloads";
                    break;
                case "DISCONNECTION":
                    Liquid.LiquidValues = new String[CustomerDisconnectionDownloadColumns.length];
                    Liquid.LiquidColumns = CustomerDisconnectionDownloadColumns;
                    Liquid.LiquidTable = "customer_disconnection_downloads";
                    break;
                default:
            }

            for(int a = 0; a < JobOrdersDetails.size(); a++) {
                for(int b = 0; b < Liquid.LiquidColumns.length; b++) {
                    Liquid.LiquidValues[b] = JobOrdersDetails.get(a).get(Liquid.LiquidColumns[b]);
                }
                result = SplashActivity.mDatabaseHelper.SqliteReplaceQueryWithoutDefault(LiquidTable,Liquid.LiquidColumns,Liquid.LiquidValues);
            }

            if(result == true){
                Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Downloaded!");
            } else {
                Liquid.showDialogError(view.getContext(), "Invalid", "Unsuccessfully Downloaded!");
            }
        }catch(Exception e){
            Log.e(TAG, "Error", e);
        }
    }

    @Override
    public void onRefresh() {
        JobOrderList.clear();
        new GetJobOrderList().execute("false","");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class GetJobOrderList extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            if(animated){
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(true);
                pDialog.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            try{
                HttpHandler sh = new HttpHandler();

                String JobId = params[1];
                if(params[0].equals("true")){
                    animated = true;
                }else{
                    animated = false;
                }

                mApiData = new ApiData("work-route.php","workController","get_job_list","client="+ Liquid.Client + "&job_id="+JobId+"&service_type="+Liquid.ServiceType);
                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(mApiData.API_Link);
                Log.i(TAG,"Tristan TEST" + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONObject entries = jsonObj.optJSONObject("payload");
                        String encodedData = entries.getString("entries");
                        String decodedData = doDecode64(encodedData);
                        // Getting JSON Array node
                        JSONArray decodejsonObj = new JSONArray(decodedData);
                        // looping through All Data

                        for (int i = 0; i < decodejsonObj.length(); i++) {
                            JSONObject c = decodejsonObj.getJSONObject(i);
                            String id = c.getString("id");
                            String title = c.getString("title");
                            String details = c.getString("details");
                            String date = c.getString("date");
                            String cycle = c.getString("cycle");
                            // JSONObject phone = c.getJSONObject("id");
                            // tmp hash map for single contact
                            HashMap<String, String> data = new HashMap<>();
                            // adding each child node to HashMap key => value
                            data.put("id", id);
                            data.put("title", title);
                            data.put("details", details);
                            data.put("date", date);
                            data.put("cycle", cycle);
                            // adding contact to contact list
                            JobOrderList.add(data);
                        }
                    } catch (final JSONException e) {
                       // Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG ).show();
                        Log.e(TAG,"Error ",e);
                    }
                } else {
                    //Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG ).show();
                    Log.i(TAG,"No Data");
                }
            }catch(Exception e){
                Log.e(TAG,"Error ",e);
                //Liquid.showDialogError(view.getContext(),"Invalid","Can't connect to server!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                if(JobOrderList.size() == 0){
                    Liquid.showDialogError(view.getContext(),"Invalid","There is no data / There is no internet connection!");
                }else{
                    mJobOrderAdapter.updateItems(animated,JobOrderList);
                }

                if (pDialog.isShowing())
                    pDialog.dismiss();
                //Toast.makeText(getActivity(), JobOrderList.toString(), Toast.LENGTH_LONG ).show();
            }catch (final Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG ).show();
            }
        }

        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }

    public class GetReadandBillRatesDetails extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            JobOrderListDetails.clear();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Downloading Rates...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String Cycle = params[0];
            Liquid.GETBMSApiData mGETBMSApiData;
            mGETBMSApiData = new Liquid.GETBMSApiData("BillRates.php","client="+Liquid.Client+"&cycle="+Cycle+"&sysid="+Liquid.User);
            LiquidColumns = RatesColumns;
            String jsonStr = sh.makeServiceCall(mGETBMSApiData.API_Link);
            Log.i(TAG, String.valueOf(mGETBMSApiData.API_Link));
            Log.i(TAG,jsonStr);
            // Making a request to url and getting response
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject entries = jsonObj.optJSONObject("payload");
                    String encodedData = entries.getString("entries");
                    String decodedData = doDecode64(encodedData);
                            // Getting JSON Array node
                    JSONObject jsonObject = new JSONObject(decodedData);
                    JSONArray decodejsonArray = new JSONArray(jsonObject.get("Rates").toString());

                            // looping through All Data
                    for (int i = 0; i < decodejsonArray.length(); i++) {
                                JSONObject c = decodejsonArray.getJSONObject(i);
                                HashMap<String, String> data = new HashMap<>();
                                for(int a = 0; a < LiquidColumns.length; a++){
                                    data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                }
                                Rates.add(data);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG,"Error : ",e);
                } catch (Exception e){
                    Log.e(TAG,"Error : ",e);
                }
            } else {
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG ).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{

                //Toast.makeText(getActivity(), "Successfully Downloaded", Toast.LENGTH_SHORT).show();

                MigrateRates(Rates);

                if (pDialog.isShowing())
                    pDialog.dismiss();


            }catch (final Exception e) {
                Log.e(TAG,"Error : ",e);
            }
        }

        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }

    public class GetUnAccomplishmentJobOrder extends  AsyncTask<String,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            JobOrderListDetails.clear();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Downloading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
                HttpHandler sh = new HttpHandler();
                String JobOrderId = params[0];
                mGETUMSAPI = new Liquid.GETUMSAPI("wms/php/api/DownloadUnAccomplishment.php","&client="+ Liquid.Client+"&job_id="+JobOrderId+"&service_type="+Liquid.ServiceType+"&sysid="+Liquid.User);
                switch(ServiceType){
                    case "TRACKING":
                        LiquidColumns = CokeCustomerColumns;
                        break;
                    case "MESSENGER":
                        LiquidColumns = CustomerDeliveryColumns;
                        break;
                    case "READ AND BILL":
                        LiquidColumns = CustomerReadingColumns;
                        break;
                    case "DISCONNECTION":
                        LiquidColumns = CustomerDisconnectionDownloadColumns;
                        break;
                    default:
                }
                String jsonStr = sh.makeServiceCall(mGETUMSAPI.API_Link);
                Log.i(TAG,jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONObject entries = jsonObj.optJSONObject("payload");
                        String encodedData = entries.getString("entries");
                        String decodedData = doDecode64(encodedData);
                        JSONArray decodejsonArray = new JSONArray();
                        switch (Liquid.ServiceType) {
                            case "TRACKING":
                                // Getting JSON Array node
                                /*decodejsonArray = new JSONArray(decodedData);
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {

                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for (int a = 0; a < LiquidColumns.length; a++) {
                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }*/
                                break;

                            case "MESSENGER":
                                /*// Getting JSON Array node
                                //------FOR JSON OBJECT
                                JSONObject decodejsonObj = new JSONObject(decodedData);
                                //JSONObject decodejsonObjDetails = new JSONObject(decodejsonObj.getString("details"));
                                //decodejsonObjDetails.getString("job_id");
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodejsonObj.getString("items"));
                                decodejsonObj.getString("items");
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {
                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    data.put("code", c.getString("code"));
                                    data.put("type", c.getString("type"));
                                    data.put("quantity", c.getString("quantity"));

                                    JobOrderListDetails.add(data);
                                }*/


                                break;
                            case "READ AND BILL":
                                // Getting JSON Array node
                                //------FOR JSON OBJECT
                                JSONObject decodejson = new JSONObject(decodedData);
                                //JSONObject decodejsonObjDetails = new JSONObject(decodejsonObj.getString("details"));
                                //decodejsonObjDetails.getString("job_id");
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodejson.getString("items"));

                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {

                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for (int a = 0; a < LiquidColumns.length; a++) {

                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }

                                break;
                            case "DISCONNECTION":
                                // Getting JSON Array node
                                //------FOR JSON OBJECT
                                /*JSONObject decodeDisconnectionjson = new JSONObject(decodedData);
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodeDisconnectionjson.getString("items"));
                                decodeDisconnectionjson.getString("items");

                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {
                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for (int a = 0; a < LiquidColumns.length; a++) {

                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }*/
                                break;
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Error : ", e);
                    } catch (Exception e) {
                        Log.e(TAG, "Error ", e);
                    }
                }else {
                        Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG ).show();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(JobOrderListDetails.size() == 0){
                Liquid.showDialogError(getActivity(),"Information","There is no data available.");
            }else{
                doDownloadJobOrderDetails(JobOrderListDetails);
            }

            if (pDialog.isShowing())
                pDialog.dismiss();
        }
        public String doDecode64(String encodeValue) {
                    byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
                    return new String(decodeValue);
                }
    }

    public class GetInvalidJobOrder extends  AsyncTask<String,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            JobOrderListDetails.clear();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Downloading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String JobOrderId = params[0];
            mGETUMSAPI = new Liquid.GETUMSAPI("wms/php/api/MeterNotInListInvalid.php","&client="+ Liquid.Client+"&job_id="+JobOrderId+"&service_type="+Liquid.ServiceType+"&sysid="+Liquid.User);
            switch(ServiceType){
                case "TRACKING":
                    LiquidColumns = CokeCustomerColumns;
                    break;
                case "MESSENGER":
                    LiquidColumns = CustomerDeliveryColumns;
                    break;
                case "READ AND BILL":
                    LiquidColumns = CustomerReadingColumns;
                    break;
                case "DISCONNECTION":
                    LiquidColumns = CustomerDisconnectionDownloadColumns;
                    break;
                default:
            }
            String jsonStr = sh.makeServiceCall(mGETUMSAPI.API_Link);
            Log.i(TAG,jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject entries = jsonObj.optJSONObject("payload");
                    String encodedData = entries.getString("entries");
                    String decodedData = doDecode64(encodedData);
                    JSONArray decodejsonArray = new JSONArray();
                    switch (Liquid.ServiceType) {
                        case "TRACKING":
                            // Getting JSON Array node
                                /*decodejsonArray = new JSONArray(decodedData);
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {

                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for (int a = 0; a < LiquidColumns.length; a++) {
                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }*/
                            break;

                        case "MESSENGER":
                                /*// Getting JSON Array node
                                //------FOR JSON OBJECT
                                JSONObject decodejsonObj = new JSONObject(decodedData);
                                //JSONObject decodejsonObjDetails = new JSONObject(decodejsonObj.getString("details"));
                                //decodejsonObjDetails.getString("job_id");
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodejsonObj.getString("items"));
                                decodejsonObj.getString("items");
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {
                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    data.put("code", c.getString("code"));
                                    data.put("type", c.getString("type"));
                                    data.put("quantity", c.getString("quantity"));

                                    JobOrderListDetails.add(data);
                                }*/
                            break;

                        case "READ AND BILL":
                            // Getting JSON Array node
                            //------FOR JSON OBJECT
                            JSONObject decodejson = new JSONObject(decodedData);
                            //JSONObject decodejsonObjDetails = new JSONObject(decodejsonObj.getString("details"));
                            //decodejsonObjDetails.getString("job_id");
                            //------FOR JSON ARRAY
                            decodejsonArray = new JSONArray(decodejson.getString("items"));

                            // looping through All Data
                            for (int i = 0; i < decodejsonArray.length(); i++) {

                                JSONObject c = decodejsonArray.getJSONObject(i);
                                HashMap<String, String> data = new HashMap<>();
                                for (int a = 0; a < LiquidColumns.length; a++) {

                                    data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                }
                                JobOrderListDetails.add(data);
                            }
                            break;

                        case "DISCONNECTION":
                            // Getting JSON Array node
                            //------FOR JSON OBJECT
                                /*JSONObject decodeDisconnectionjson = new JSONObject(decodedData);
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodeDisconnectionjson.getString("items"));
                                decodeDisconnectionjson.getString("items");

                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {
                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for (int a = 0; a < LiquidColumns.length; a++) {

                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }*/
                            break;
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Error : ", e);
                } catch (Exception e) {
                    Log.e(TAG, "Error ", e);
                }
            }else {
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG ).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(JobOrderListDetails.size() == 0){
                Liquid.showDialogError(getActivity(),"Information","There is no data available.");
            }else{
                doDownloadJobOrderDetails(JobOrderListDetails);
            }

            if (pDialog.isShowing())
                pDialog.dismiss();
        }
        public String doDecode64(String encodeValue) {
            byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
            return new String(decodeValue);
        }
    }

    public class GetJobOrderListDetails extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            JobOrderListDetails.clear();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Downloading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String JobOrderId = params[0];

            mApiData = new ApiData("work-route.php","workController","get_job_details","client="+ Liquid.Client+"&job_id="+JobOrderId+"&service_type="+Liquid.ServiceType+"&sysid="+Liquid.User);
            switch(ServiceType){
                case "TRACKING":
                    LiquidColumns = CokeCustomerColumns;
                    break;
                case "MESSENGER":
                    LiquidColumns = CustomerDeliveryColumns;
                    break;
                case "READ AND BILL":
                    LiquidColumns = CustomerReadingColumns;
                    break;
                case "DISCONNECTION":
                    LiquidColumns = CustomerDisconnectionDownloadColumns;
                    break;
                default:
            }

            String jsonStr = sh.makeServiceCall(mApiData.API_Link);
            Log.i(TAG,jsonStr);
            // Making a request to url and getting response
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONObject entries = jsonObj.optJSONObject("payload");
                        String encodedData = entries.getString("entries");
                        String decodedData = doDecode64(encodedData);
                        switch(Liquid.ServiceType){
                            case "TRACKING":
                                // Getting JSON Array node
                                JSONArray decodejsonArray = new JSONArray(decodedData);
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {

                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for(int a = 0; a < LiquidColumns.length; a++){
                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }
                                break;

                            case "MESSENGER":
                                // Getting JSON Array node
                                //------FOR JSON OBJECT
                                JSONObject decodejsonObj = new JSONObject(decodedData);
                                //JSONObject decodejsonObjDetails = new JSONObject(decodejsonObj.getString("details"));
                                //decodejsonObjDetails.getString("job_id");
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodejsonObj.getString("items"));
                                decodejsonObj.getString("items");
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {
                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    data.put( "code",c.getString("code"));
                                    data.put("type",c.getString("type"));
                                    data.put("quantity",c.getString("quantity"));

                                    JobOrderListDetails.add(data);
                                }
                                break;

                            case "READ AND BILL":
                                // Getting JSON Array node
                                //------FOR JSON OBJECT
                                JSONObject decodejson = new JSONObject(decodedData);
                                //JSONObject decodejsonObjDetails = new JSONObject(decodejsonObj.getString("details"));
                                //decodejsonObjDetails.getString("job_id");
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodejson.getString("items"));
                                decodejson.getString("items");
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {

                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for(int a = 0; a < LiquidColumns.length; a++){

                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }
                                break;

                            case "DISCONNECTION":
                                // Getting JSON Array node
                                //------FOR JSON OBJECT
                                JSONObject decodeDisconnectionjson = new JSONObject(decodedData);
                                //------FOR JSON ARRAY
                                decodejsonArray = new JSONArray(decodeDisconnectionjson.getString("items"));
                                decodeDisconnectionjson.getString("items");
                                // looping through All Data
                                for (int i = 0; i < decodejsonArray.length(); i++) {
                                    JSONObject c = decodejsonArray.getJSONObject(i);
                                    HashMap<String, String> data = new HashMap<>();
                                    for(int a = 0; a < LiquidColumns.length; a++){

                                        data.put(LiquidColumns[a], c.getString(LiquidColumns[a]));
                                    }
                                    JobOrderListDetails.add(data);
                                }
                                break;
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG,"Error : ",e);
                    } catch (Exception e){
                        Log.e(TAG,"Error : ",e);
                    }
                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG ).show();
                }
                return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                //Toast.makeText(getActivity(), "Successfully Downloaded", Toast.LENGTH_SHORT).show();
                doDownloadJobOrderDetails(JobOrderListDetails);

                if (pDialog.isShowing())
                    pDialog.dismiss();

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
