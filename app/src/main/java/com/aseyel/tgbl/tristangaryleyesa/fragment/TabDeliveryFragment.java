package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aseyel.tgbl.tristangaryleyesa.DeliveryReceived;
import com.aseyel.tgbl.tristangaryleyesa.JobOrderActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.adapter.LocalJobOrderAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TabDeliveryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = TabDeliveryFragment.class.getSimpleName();
    private static final int DELIVERY_RECEIVED_FORM = 1;
    private static final int LIST_DELIVERY = 2;
    // TODO: Rename and change types of parameters
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AHBottomNavigation mBottomNavigationView;
    private ProgressDialog mProgressDialog;
    private RecyclerView rvList;
    private FloatingActionButton BtnAddForDelivery;
    private LocalJobOrderAdapter mAdapter;
    private Liquid.POSTApiData mPOSTApiData;

    public TabDeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case DELIVERY_RECEIVED_FORM:
                GetStockIn(false,"");
                break;
            case LIST_DELIVERY:
                GetStockIn(false,"");
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_delivery, container, false);
        //trigger the variables power
        init(mView);
        GetStockIn(true,"");
        return mView;
    }

    @Override
    public void onRefresh() {
        GetStockIn(false,"");
        mSwipeRefreshLayout.setRefreshing(false);

    }

    public void GetStockIn(boolean animated,String Query){
        try
        {
            mAdapter.updateItems(animated, GetStockIn(Query));
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }

    public class DeliveryFilePostingToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        JSONObject response;
        JSONArray dataArray;
        JSONArray imageArray;
        JSONArray signatureArray;
        int toUploadCount = 0;
        int progress = 0;
        int total = 0;
        String data;
        String Category;
        String jsonStr;
        String imageData;
        String AccountNumber = "";
        String Period;

        // This is a constructor that allows you to pass in the JSON body
        public DeliveryFilePostingToServer(JSONObject postData) {
            if (postData != null) {
                this.postData = postData;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UploadImage
            //showUploadProgressBar(true);
            try {
                Liquid.ErrorFileUpload = new JSONObject();
                Liquid.ErrorUpload = new JSONArray();
                data = postData.getString("all_data");
                dataArray = new JSONArray(data);

                total = dataArray.length();
                //progressBar.setMax(0);
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading File");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Integer doInBackground(Void... strings) {
            try {
                HttpHandler sh = new HttpHandler();
                //Making a request to url and getting response

                if(dataArray.length() == 0){
                    return 2;
                }
                for(int a = 0; a < dataArray.length();a++){
                    JSONObject dataObject = new JSONObject(dataArray.getString(a));

                    if(!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))){
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;
                        mProgressDialog.setMax(0);
                        mImages = Liquid.getDiscSignature(AccountNumber);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(mView.getContext(), "Can't create directory to save image");
                        } else {
                            listFile = mImages.listFiles();
                            toUploadCount = toUploadCount + listFile.length;
                            mProgressDialog.setMax(listFile.length);

                            for (int e = 0; e < listFile.length; e++) {
                                JSONArray final_image_result = new JSONArray();
                                JSONObject final_image_response = new JSONObject();
                                JSONObject data = new JSONObject();

                                data.put("Client", Liquid.Client);
                                data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                data.put("Filename", listFile[e].getName());
                                data.put("service_type", "Messengerial");
                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);
                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadSignature.php");
                                jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, imageArray.getJSONObject(0));
                                Log.i(TAG,jsonStr);
                                response = Liquid.StringToJsonObject(jsonStr);

                                if(response.getString("result").equals("false")){
                                    Liquid.ErrorUpload.put(dataObject);
                                }else{
                                    Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));
                                    imageUploaded = imageUploaded+1;
                                    progress = progress+1;
                                    publishProgress(imageUploaded);
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG,"Error :",e);
                //JSON Problem
                return 1;
            }
            catch (Exception e){
                //An error has occured
                Log.e(TAG,"Error :",e);
                return 0;
            }
            return 29;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                switch(result){
                    case 29:
                        Liquid.showDialogInfo(mView.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(mView.getContext(),"Valid","There is no data to be upload!");

                        break;
                    case 3:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","There is no available picture to upload!");
                        break;
                    case 4:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","There is no available signature to upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","An error has occured!");
                }
            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(mView.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }

    public class DataMessengerialToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        String data;
        String dataPicture;
        String dataLogs;
        String dataMeterNotInList;
        JSONArray dataArray;
        JSONArray dataArrayPicture;
        JSONArray dataArrayLogs;
        JSONArray dataArrayMeterNotInList;
        boolean result_status = false;
        int progress = 0;
        int total = 0;

        // This is a constructor that allows you to pass in the JSON body
        public DataMessengerialToServer(JSONObject postData) {
            if (postData != null) {
                this.postData = postData;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UploadImage
            //showUploadProgressBar(true);
            try {
                Liquid.ErrorDataUpload = new JSONObject();
                Liquid.ErrorUpload = new JSONArray();
                data = postData.getString("data");
                dataArray = new JSONArray(data);
                total = dataArray.length();
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading Data");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMax(0);
                mProgressDialog.setMax(total);
                mProgressDialog.show();
                //progressBar.setMax(total);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Integer doInBackground(Void... strings) {
            try {
                HttpHandler sh = new HttpHandler();
                int limit = 50;
                if(dataArray.length() == 0){
                    //No Data
                    //return 2;
                }else{
                    List<String> arrayList = new ArrayList<String>();
                    for(int i = 0; i < dataArray.length(); i++){
                        arrayList.add(dataArray.getString(i));
                    }

                    for(int j = 0; j < arrayList.size(); j+=limit) {
                        int k = Math.min(j + limit, arrayList.size());
                        List<String> subList = arrayList.subList(j,k);
                        JSONArray newDataArray = new JSONArray(subList);
                        JSONObject reading = new JSONObject();
                        reading.put("sysid",Liquid.User);
                        reading.put("username",Liquid.Username);
                        reading.put("password",Liquid.Password);
                        reading.put("data",newDataArray);
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Delivery.php");
                        //Log.i(TAG, String.valueOf(test));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, reading);
                        Log.i(TAG,"Tristan "+jsonStr);
                        //Log.i(TAG, "ALEX:" + jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);

                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(reading);
                        } else {
                            progress = progress + limit;
                            publishProgress(progress);
                            JSONArray newReadingArray = reading.getJSONArray("data");
                            for(int i=0; i < newReadingArray.length(); i++){
                                JSONObject dataObject = new JSONObject(newReadingArray.getString(i));
                                Log.i(TAG,"Tristan "+ dataObject);
                                result_status = DeliveryModel.UpdateUploadStatus(dataObject.getString("job_id").toString(),dataObject.getString("trackingNumber").toString());
                            }
                        }
                    }
                }

                if (Liquid.ErrorUpload.length() != 0) {
                    Liquid.ErrorDataUpload.put("data", Liquid.ErrorUpload);
                }
            } catch (JSONException e) {
                Log.e(TAG,"Error :",e);
                //JSON Problem
                return 1;
            }
            catch (Exception e){
                //An error has occured
                Log.e(TAG,"Error :",e);
                return 0;
            }
            return 29;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressBar.setProgress(values[0]);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                switch(result){
                    case 29:
                        Liquid.showDialogInfo(mView.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(mView.getContext(),"Valid","There is no data to be upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","An error has occured!");
                }
            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(mView.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }

    public class AllDataMessengerialToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        String data;
        String dataPicture;
        String dataLogs;
        String dataMeterNotInList;
        JSONArray dataArray;
        JSONArray dataArrayPicture;
        JSONArray dataArrayLogs;
        JSONArray dataArrayMeterNotInList;
        boolean result_status = false;
        int progress = 0;
        int total = 0;

        // This is a constructor that allows you to pass in the JSON body
        public AllDataMessengerialToServer(JSONObject postData) {
            if (postData != null) {
                this.postData = postData;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UploadImage
            //showUploadProgressBar(true);
            try {
                Liquid.ErrorDataUpload = new JSONObject();
                Liquid.ErrorUpload = new JSONArray();
                data = postData.getString("all_data");
                dataArray = new JSONArray(data);
                total = dataArray.length();
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading Data");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMax(0);
                mProgressDialog.setMax(total);
                mProgressDialog.show();
                //progressBar.setMax(total);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Integer doInBackground(Void... strings) {
            try {
                HttpHandler sh = new HttpHandler();
                int limit = 50;
                if(dataArray.length() == 0){
                    //No Data
                    //return 2;
                }else{
                    List<String> arrayList = new ArrayList<String>();
                    for(int i = 0; i < dataArray.length(); i++){
                        arrayList.add(dataArray.getString(i));
                    }
                    for(int j = 0; j < arrayList.size(); j+=limit) {
                        int k = Math.min(j + limit, arrayList.size());
                        List<String> subList = arrayList.subList(j,k);
                        JSONArray newDataArray = new JSONArray(subList);
                        JSONObject reading = new JSONObject();
                        reading.put("sysid",Liquid.User);
                        reading.put("username",Liquid.Username);
                        reading.put("password",Liquid.Password);
                        reading.put("data",newDataArray);
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Delivery.php");
                        //Log.i(TAG, String.valueOf(test));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, reading);
                        Log.i(TAG,"Tristan "+jsonStr);
                        //Log.i(TAG, "ALEX:" + jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);

                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(reading);
                        } else {
                            progress = progress + limit;
                            publishProgress(progress);
                            JSONArray newReadingArray = reading.getJSONArray("data");
                            for(int i=0; i < newReadingArray.length(); i++){
                                JSONObject dataObject = new JSONObject(newReadingArray.getString(i));
                                Log.i(TAG,"Tristan "+ dataObject);
                                result_status = DeliveryModel.UpdateUploadStatus(dataObject.getString("job_id").toString(),dataObject.getString("trackingNumber").toString());
                            }
                        }
                    }
                }

                if (Liquid.ErrorUpload.length() != 0) {
                    Liquid.ErrorDataUpload.put("data", Liquid.ErrorUpload);
                }
            } catch (JSONException e) {
                Log.e(TAG,"Error :",e);
                //JSON Problem
                return 1;
            }
            catch (Exception e){
                //An error has occured
                Log.e(TAG,"Error :",e);
                return 0;
            }
            return 29;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressBar.setProgress(values[0]);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                switch(result){
                    case 29:
                        Liquid.showDialogInfo(mView.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(mView.getContext(),"Valid","There is no data to be upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(mView.getContext(),"Invalid","An error has occured!");
                }
            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(mView.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }

    public void DoUploadMessengerial(final JSONObject postData){
        try {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new DataMessengerialToServer(postData).execute();
                            //new DataReadingNoLoadingPostingToServer(mpostData).execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            new DeliveryFilePostingToServer(postData).execute();
                            break;
                        case DialogInterface.BUTTON_NEUTRAL:
                            new AllDataMessengerialToServer(postData).execute();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Please select what you want to upload.").setPositiveButton("Pending Data", dialogClickListener)
                    .setNegativeButton("File", dialogClickListener).setNeutralButton("All Data", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GoToList(){
        Intent i = new Intent(getContext(), JobOrderActivity.class);
        startActivityForResult(i,DELIVERY_RECEIVED_FORM);
    }

    public static ArrayList<HashMap<String, String>> GetStockIn(String Query){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = DeliveryModel.GetStockIn(Query);
        try
        {
            if(result.getCount() == 0){
                return final_result;
            }
            while(result.moveToNext()){
                int total_upload = 0;
                int total_download = 0;
                int total_pending = 0;
                String stockInId = result.getString(result.getColumnIndex("stockInId"));
                String stockInTitle = result.getString(result.getColumnIndex("stockInTitle"));
                String stockInDate = result.getString(result.getColumnIndex("stockInDate"));
                Cursor result_jo = DeliveryModel.GetDataDownload(stockInId);
                Cursor result_job_upload = DeliveryModel.GetDataUploaded(result.getString(result.getColumnIndex("stockInId")),"Uploaded");
                Cursor result_job_Pending = DeliveryModel.GetDataUploaded(result.getString(result.getColumnIndex("stockInId")),"Pending");

                if(result_job_Pending.getCount() == 0){

                }
                while(result_job_Pending.moveToNext()){
                    total_pending = Integer.parseInt(result_job_Pending.getString(0));
                }
                if(result_jo.getCount() == 0){
                    total_download = Integer.parseInt(result.getString(10));
                }
                while(result_jo.moveToNext()){
                    total_download = Integer.parseInt(result_jo.getString(0));
                }
                if(result_job_upload.getCount() == 0){

                }
                while(result_job_upload.moveToNext()){
                    total_upload = Integer.parseInt(result_job_upload.getString(0));
                }
                HashMap<String, String> data = new HashMap<>();
                if(total_download == 0){
                    total_download = Integer.parseInt(result.getString(10));
                }
                data.put("id", stockInId);
                data.put("title", stockInTitle);
                data.put("details", " Job Order No: "+stockInId + "" +
                        "\n Date : " +stockInDate+
                        "\n Download : " +total_download+
                        "\n Uploaded : "+total_upload +
                        "\n Pending  : " +total_pending
                );
                data.put("date", stockInDate);

                final_result.add(data);
            }
            return final_result;
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return final_result;
        }
    }

    private void init(View mView){
        mProgressDialog = new ProgressDialog(getContext());
        //swipe function for reload
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeReload);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mBottomNavigationView = (AHBottomNavigation) mView.findViewById(R.id.mBottomNavigationView);
        BtnAddForDelivery = (FloatingActionButton) mView.findViewById(R.id.BtnAddForDelivery);
        //this is for recycler
        mAdapter = new LocalJobOrderAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvList = (RecyclerView) mView.findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(llm);
        rvList.setAdapter(mAdapter);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!rvList.canScrollVertically(1)){
                    Liquid.UpdateRecyclerItemLimit = Liquid.UpdateRecyclerItemLimit + 50;
                    GetStockIn(false,"");
                }
            }
        });

        BtnAddForDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeliveryReceived.class);
                startActivityForResult(i,DELIVERY_RECEIVED_FORM);
            }
        });
    }
}
