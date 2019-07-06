package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.aseyel.tgbl.tristangaryleyesa.NewJobOrderActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;


import com.aseyel.tgbl.tristangaryleyesa.adapter.LocalJobOrderAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid.ApiData;

import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.LiquidModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidReading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class TabLocalFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "LocalFragment";
    private String[] GetColumns;
    private int job_id;
    private ArrayList<HashMap<String, String>> LocalJobOrderList;
    private  ArrayList<HashMap<String, String>> LocalJobOrderListDetails;
    private LocalJobOrderAdapter mLocalJobOrderAdapter;
    private Liquid.POSTAuditApiData mAuditApiData;
    private Liquid.POSTApiData mPOSTApiData;
    private ApiData mApiData;
    private Liquid.CokeApiData mCokeApiData;
    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rvLocalJobOrderList)
    RecyclerView rvLocalJobOrderList;
    @BindView(R.id.cvProgressBar)
    CardView cvProgressBar;
    @BindView(R.id.floatBtnAdd)
    FloatingActionButton floatBtnAdd;

    private ProgressBar progressBar;
    private JSONObject mpostData;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_local, container, false);
        setupLocalJobOrder(view);
        GetLocalJobOrder(true,"");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetLocalJobOrder(false,"");

    }

    public void showUploadProgressBar(boolean visible){
        if(visible){
            cvProgressBar.setVisibility(view.VISIBLE);
        }else{
            cvProgressBar.setVisibility(view.GONE);
        }
    }

    public void prograssBarStatus(int percent){

        progressBar.setProgress(percent);
        if(percent == 100){
            //showUploadProgressBar(false);
            if(Liquid.ErrorUpload.length() == 0){
                Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded");
            }else{
                Liquid.showDialogInfo(view.getContext(),"Invalid","Some data is not uploaded");
            }
        }

    }

    private void setupLocalJobOrder(final View view) {
        try {
            //Initialization
            LocalJobOrderList = new ArrayList<>();
            LocalJobOrderListDetails = new ArrayList<>();
            mLocalJobOrderAdapter = new LocalJobOrderAdapter(this);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            cvProgressBar = (CardView) view.findViewById(R.id.cvProgressBar);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            rvLocalJobOrderList = (RecyclerView) view.findViewById(R.id.rvLocalJobOrderList);
            floatBtnAdd = (FloatingActionButton) view.findViewById(R.id.floatBtnAdd);
            job_id = 0;
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            //Setting up
            rvLocalJobOrderList.setHasFixedSize(true);
            rvLocalJobOrderList.setLayoutManager(llm);
            rvLocalJobOrderList.setAdapter(mLocalJobOrderAdapter);
            cvProgressBar.setVisibility(view.GONE);

            floatBtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(view.getContext(), NewJobOrderActivity.class);
                        view.getContext().startActivity(i);
                    }
            });

            floatBtnAdd.setVisibility(View.GONE);

        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }

    }

    public void GetLocalJobOrder(boolean animated,String job_id){

        try
        {
            mLocalJobOrderAdapter.updateItems(animated, LiquidModel.GetLocalJobOrder(job_id));
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    public void DeleteData(final String JobOrderId,final String JobType,final int position){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        boolean result = false;
                        boolean result_details = false;
                        result = TrackingModel.DeleteLocalJobOrder(JobOrderId);
                        result_details = TrackingModel.DeleteLocalJobOrderDetails(JobOrderId,JobType);
                        if (result == true) {
                            Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Deleted!");
                            mLocalJobOrderAdapter.DeleteItem(position);
                        } else {
                            Liquid.showDialogError(getActivity(), "Invalid", "Unsuccessfully Deleted!");
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to DELETE?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }


    public void DoUploadAudit(final JSONObject postData){
        try {

            mpostData = postData;

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new DataAuditPostingToServer(mpostData).execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            new AuditFilePostingToServer(mpostData).execute();
                            break;

                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Please select what you want to upload.").setPositiveButton("Data", dialogClickListener)
                    .setNegativeButton("File", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoUploadDisconnection(final JSONObject postData){
        try {

            mpostData = postData;

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new DataDisconnectionPostingToServer(mpostData).execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            new DisconnectionFilePostingToServer(mpostData).execute();
                            break;

                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Please select what you want to upload.").setPositiveButton("Data", dialogClickListener)
                    .setNegativeButton("File", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DoUploadReading(final JSONObject postData){
        try {

            mpostData = postData;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new DataReadingPostingToServer(mpostData).execute();
                            //new DataReadingNoLoadingPostingToServer(mpostData).execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            new ReadingFilePostingToServer(mpostData).execute();
                            //new ReadingFileNoLoadingPostingToServer(mpostData).execute();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Please select what you want to upload.").setPositiveButton("Data", dialogClickListener)
                    .setNegativeButton("File", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void DoUploadCoke(JSONObject postData){
        try {

            mpostData = postData;

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                                new DataPostingToServer(mpostData).execute();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                                new CokeFilePostingToServer(mpostData).execute();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Please select what you want to upload.").setPositiveButton("Data", dialogClickListener)
                    .setNegativeButton("File", dialogClickListener).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRefresh() {
        GetLocalJobOrder(false,"");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void RetryErrorUpload(){
        try{

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Some data are not uploaded, Do you want to retry?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }

    }

    public class DataAuditPostingToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        String data;
        String dataPicture;
        JSONArray dataArray;
        JSONArray dataArrayPicture;
        int progress = 0;
        int total = 0;

        // This is a constructor that allows you to pass in the JSON body
        public DataAuditPostingToServer(JSONObject postData) {
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
                dataPicture = postData.getString("picture");
                dataArray = new JSONArray(data);
                dataArrayPicture = new JSONArray(dataPicture);
                total = dataArray.length();
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading Data");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                mProgressDialog.setMax(0);
                mProgressDialog.setMax(total);
                //progressBar.setMax(total);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Integer doInBackground(Void... strings) {
            try {
                HttpHandler sh = new HttpHandler();

                if(dataArray.length() == 0){
                    //No Data
                    return 2;
                }
                //Making a request to url and getting response
                for (int a = 0; a < dataArray.length(); a++) {
                    mAuditApiData = new Liquid.POSTAuditApiData("AuditTravelRide.php");
                    JSONObject dataObject = new JSONObject(dataArray.getString(a));
                    //Log.i(TAG, String.valueOf(dataObject));
                    String jsonStr = sh.makeServicePostCall(mAuditApiData.API_Link, dataObject);

                    JSONObject response = Liquid.StringToJsonObject(jsonStr);


                    if (response.getString("result").equals("false")) {
                        Liquid.ErrorUpload.put(dataObject);
                    } else {
                        progress = progress + 1;
                        publishProgress(progress);
                    }
                }

                mProgressDialog.setMax(0);
                mProgressDialog.setMax(dataArrayPicture.length());
                progress = 0;
                for (int a = 0; a < dataArrayPicture.length(); a++) {
                    mAuditApiData = new Liquid.POSTAuditApiData("AuditPicture.php");
                    JSONObject dataObject = new JSONObject(dataArrayPicture.getString(a));
                    Log.i(TAG, String.valueOf(dataObject));
                    String jsonStr = sh.makeServicePostCall(mAuditApiData.API_Link, dataObject);

                    JSONObject response = Liquid.StringToJsonObject(jsonStr);
                    Log.i(TAG, String.valueOf(jsonStr));
                    if (response.getString("result").equals("false")) {
                        Liquid.ErrorUpload.put(dataObject);
                    } else {
                        progress = progress + 1;
                        publishProgress(progress);
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }


    }

    public class DataPostingToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        String data;
        String AccountNumber;
        JSONArray dataArray;
        int progress = 0;
        int total = 0;

        // This is a constructor that allows you to pass in the JSON body
        public DataPostingToServer(JSONObject postData) {
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
                mProgressDialog.show();
                mProgressDialog.setMax(0);
                mProgressDialog.setMax(total);
                //progressBar.setMax(total);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Integer doInBackground(Void... strings) {
            try {
                HttpHandler sh = new HttpHandler();
                Liquid.ApiData mApiData;
                mCokeApiData = new Liquid.CokeApiData("audit-route.php","auditController","TrackingPosting","");
                //Making a request to url and getting response

                if(dataArray.length() == 0){
                    return 2;
                }
                for(int a = 0; a < dataArray.length();a++){
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("data",dataArray.getJSONObject(a));
                    String jsonStr = sh.makeServicePostCall(mCokeApiData.API_Link,dataObject);
                    JSONObject response = Liquid.StringToJsonObject(jsonStr);

                    if(response.getString("result").equals("false")){
                        Liquid.ErrorUpload.put(dataObject);
                    }else{
                        progress = progress+1;
                        publishProgress(progress);
                    }

                }
                if(Liquid.ErrorUpload.length() != 0){
                    Liquid.ErrorDataUpload.put("data",Liquid.ErrorUpload);
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }


    }


    public class CokeFilePostingToServer extends AsyncTask<Void, Integer, Integer> {
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
        String signatureData;
        String AccountNumber;
        String Period;

        // This is a constructor that allows you to pass in the JSON body
        public CokeFilePostingToServer(JSONObject postData) {
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
                data = postData.getString("data");
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
                Liquid.CokePOSTUMSApiData mCokePOSTUMSApiData;
                //Making a request to url and getting response
                if(dataArray.length() == 0){
                    return 2;
                }
                for(int a = 0; a < dataArray.length();a++){
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("data",dataArray.getJSONObject(a));
                    AccountNumber = String.valueOf(dataArray.getJSONObject(a).getJSONArray("store_status").getJSONObject(0).get("customer_no"));
                    Period = String.valueOf(dataArray.getJSONObject(a).getJSONArray("store_status").getJSONObject(0).get("period"));
                    Cursor picture = TrackingModel.GetGroupPicture(AccountNumber,Period);

                    //progressBar.setMax(picture.getCount());
                    while(picture.moveToNext()){

                            Category = picture.getString(2);
                            File mImages;
                            File[] listFile;
                            String[] Subfolder = new String[1];
                            Subfolder[0] = Category;
                            int imageUploaded = 0;

                            mProgressDialog.setMax(0);
                            mImages = Liquid.getDiscPicture(AccountNumber, Subfolder);
                            if (!mImages.exists() && !mImages.mkdirs()) {
                                Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
                            } else {
                                listFile = mImages.listFiles();
                                toUploadCount = toUploadCount + listFile.length;

                                mProgressDialog.setMax(listFile.length );
                                for (int e = 0; e < listFile.length; e++) {
                                    JSONArray final_image_result = new JSONArray();
                                    JSONObject final_image_response = new JSONObject();
                                    JSONObject data = new JSONObject();

                                    data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                    data.put("Filename", listFile[e].getName());
                                    //combine all data for image
                                    final_image_result.put(data);
                                    final_image_response.put("image", final_image_result);
                                    imageData = final_image_response.getString("image");
                                    imageArray = new JSONArray(imageData);
                                    mCokePOSTUMSApiData = new Liquid.CokePOSTUMSApiData("tgblUploadImage.php");

                                    jsonStr = sh.makeServicePostCall(mCokePOSTUMSApiData.API_Link, imageArray.getJSONObject(0));
                                    response = Liquid.StringToJsonObject(jsonStr);
                                    Log.i(TAG, String.valueOf(jsonStr));
                                    if(response.getString("result").equals("false")){
                                        Liquid.ErrorUpload.put(dataObject);
                                    }else{
                                        imageUploaded = imageUploaded+1;
                                        progress = progress+1;
                                        Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));
                                        publishProgress(imageUploaded);
                                    }
                                }
                            }


                    }

                    //Signature
                        File mImages;
                        File[] listFile;
                        mProgressDialog.setMax(0);
                        mImages = Liquid.getDiscSignature(AccountNumber);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
                        } else {
                            listFile = mImages.listFiles();
                            if(listFile.length == 0){
                                return 4;
                            }
                            mProgressDialog.setMax(listFile.length);
                            for (int e = 0; e < listFile.length; e++) {
                                JSONArray final_image_result = new JSONArray();
                                JSONObject final_image_response = new JSONObject();
                                JSONObject data = new JSONObject();
                                data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                data.put("Filename", listFile[e].getName());
                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("signature",final_image_result);
                                signatureData =  final_image_response.getString("signature");
                                signatureArray = new JSONArray(signatureData);
                                mCokePOSTUMSApiData = new Liquid.CokePOSTUMSApiData("tgblUploadSignature.php");
                                jsonStr = sh.makeServicePostCall(mCokePOSTUMSApiData.API_Link, signatureArray.getJSONObject(0));
                                Log.i(TAG, String.valueOf(response));
                                if(response.getString("result").equals("false")){
                                    Liquid.ErrorUpload.put(dataObject);
                                }else{
                                    Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));
                                    publishProgress(1);
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    case 3:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available picture to upload!");
                        break;
                    case 4:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available signature to upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();

        }


    }



    public class AuditFilePostingToServer extends AsyncTask<Void, Integer, Integer> {
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
        String signatureData;
        String AccountNumber = "";
        String Period;

        // This is a constructor that allows you to pass in the JSON body
        public AuditFilePostingToServer(JSONObject postData) {
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
                data = postData.getString("data");
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
                Liquid.POSTAuditApiData mPOSTAuditApiData;
                //Making a request to url and getting response
                if(dataArray.length() == 0){
                    return 2;
                }
                for(int a = 0; a < dataArray.length();a++){
                    JSONObject dataObject = new JSONObject(dataArray.getString(a));
                    if(!AccountNumber.equals(String.valueOf(dataObject.get("AccountNumber")))){
                        AccountNumber = String.valueOf(dataObject.get("AccountNumber"));
                            Category = "";
                            File mImages;
                            File[] listFile;
                            String[] Subfolder = new String[1];
                            Subfolder[0] = Category;
                            int imageUploaded = 0;
                            mProgressDialog.setMax(0);
                            mImages = Liquid.getDiscPicture(AccountNumber, Subfolder);
                            if (!mImages.exists() && !mImages.mkdirs()) {
                                Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
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
                                    //combine all data for image
                                    final_image_result.put(data);
                                    final_image_response.put("image", final_image_result);
                                    imageData = final_image_response.getString("image");
                                    imageArray = new JSONArray(imageData);
                                    mPOSTAuditApiData = new Liquid.POSTAuditApiData("tgblUploadImage.php");

                                    jsonStr = sh.makeServicePostCall(mPOSTAuditApiData.API_Link, imageArray.getJSONObject(0));
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    case 3:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available picture to upload!");
                        break;
                    case 4:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available signature to upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();

        }


    }



    public class DisconnectionFilePostingToServer extends AsyncTask<Void, Integer, Integer> {
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
        String signatureData;
        String AccountNumber = " ";
        String Period;

        // This is a constructor that allows you to pass in the JSON body
        public DisconnectionFilePostingToServer(JSONObject postData) {
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
                data = postData.getString("data");
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
                Liquid.POSTAuditApiData mPOSTAuditApiData;
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
                        mImages = Liquid.getDiscPicture(AccountNumber,Subfolder);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
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
                                data.put("service_type", "Disconnection");
                                data.put("date", String.valueOf(dataObject.get("disconnection_date")));
                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    case 3:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available picture to upload!");
                        break;
                    case 4:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available signature to upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();

        }


    }
    public class DataDisconnectionPostingToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        String data;
        String dataPicture;
        JSONArray dataArray;
        JSONArray dataArrayPicture;
        int progress = 0;
        int total = 0;

        // This is a constructor that allows you to pass in the JSON body
        public DataDisconnectionPostingToServer(JSONObject postData) {
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
                dataPicture = postData.getString("picture");
                dataArray = new JSONArray(data);
                dataArrayPicture = new JSONArray(dataPicture);
                total = dataArray.length();
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading Data");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                mProgressDialog.setMax(0);
                mProgressDialog.setMax(total);
                //progressBar.setMax(total);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Integer doInBackground(Void... strings) {
            try {
                HttpHandler sh = new HttpHandler();

                if(dataArray.length() == 0){
                    //No Data
                    return 2;
                }
                //Making a request to url and getting response
                for (int a = 0; a < dataArray.length(); a++) {
                    mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Disconnection.php");
                    JSONObject dataObject = new JSONObject(dataArray.getString(a));
                    //Log.i(TAG, String.valueOf(dataObject));
                    String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                    Log.i(TAG,jsonStr);
                    JSONObject response = Liquid.StringToJsonObject(jsonStr);


                    if (response.getString("result").equals("false")) {
                        Liquid.ErrorUpload.put(dataObject);
                    } else {
                        progress = progress + 1;
                        publishProgress(progress);
                    }
                }


                mProgressDialog.setMax(0);
                mProgressDialog.setMax(dataArrayPicture.length());
                progress = 0;
                for (int a = 0; a < dataArrayPicture.length(); a++) {
                    mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/DisconnectionPicture.php");
                    JSONObject dataObject = new JSONObject(dataArrayPicture.getString(a));

                    String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                    JSONObject response = Liquid.StringToJsonObject(jsonStr);

                    if (response.getString("result").equals("false")) {
                        Liquid.ErrorUpload.put(dataObject);
                    } else {
                        progress = progress + 1;
                        publishProgress(progress);
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }


    }
    public class ReadingFilePostingToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        JSONObject response;
        JSONArray dataArray;
        JSONArray dataLogsArray;
        JSONArray dataArrayPicture;
        JSONArray dataArrayMeterNotInList;
        JSONArray imageArray;
        JSONArray signatureArray;

        int toUploadCount = 0;
        int progress = 0;
        int total = 0;

        String data;
        String Category;
        String jsonStr;
        String imageData;
        String signatureData;
        String AccountNumber = "";
        String Period;
        String dataMeterNotInList;
        String dataLogs;
        String dataPicture;

        // This is a constructor that allows you to pass in the JSON body
        public ReadingFilePostingToServer(JSONObject postData) {
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
                dataLogs = postData.getString("get_picture_data");
                dataMeterNotInList = postData.getString("meter_not_in_list");
                dataLogsArray = new JSONArray(dataLogs);
                dataArrayMeterNotInList = new JSONArray(dataMeterNotInList);
                total = dataLogsArray.length();
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
                Liquid.POSTAuditApiData mPOSTAuditApiData;
                //Making a request to url and getting response

                //if(dataArray.length() == 0){
                //return 2;
                //}
                for(int a = 0; a < dataLogsArray.length();a++){
                    JSONObject dataObject = new JSONObject(dataLogsArray.getString(a));
                    if(!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))){
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;
                        mProgressDialog.setMax(0);
                        mImages = Liquid.getDiscPicture(AccountNumber,Subfolder);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
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

                                if(Liquid.Client == "more_power"){
                                    data.put("service_type", "Audit");
//                                    data.put("service_type", "MeterReading");
                                }
                                else{
                                    data.put("service_type", "MeterReading");
                                }
                                data.put("date", String.valueOf(dataObject.get("BillMonth"))+String.valueOf(dataObject.get("BillYear")));


                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

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


                for(int a = 0; a < dataArrayMeterNotInList.length();a++){
                    JSONObject dataObject = new JSONObject(dataArrayMeterNotInList.getString(a));

                    if(!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))){
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;
                        mProgressDialog.setMax(0);
                        mImages = Liquid.getDiscPicture(AccountNumber+"_audit",Subfolder);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
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
                                data.put("service_type", "Audit");
                                data.put("date", Liquid.dateChangeFormat(Liquid.currentDate(),"yyyy-MM-dd","MMyyyy"));

                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    case 3:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available picture to upload!");
                        break;
                    case 4:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available signature to upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();

        }


    }
    public class ReadingFileNoLoadingPostingToServer extends AsyncTask<Void, Integer, Integer> {
        // This is the JSON body of the post
        JSONObject postData;
        JSONObject response;
        JSONArray dataArray;
        JSONArray dataLogsArray;
        JSONArray dataArrayPicture;
        JSONArray dataArrayMeterNotInList;
        JSONArray imageArray;
        JSONArray signatureArray;

        int toUploadCount = 0;
        int progress = 0;
        int total = 0;

        String data;
        String Category;
        String jsonStr;
        String imageData;
        String signatureData;
        String AccountNumber = "";
        String Period;
        String dataMeterNotInList;
        String dataLogs;
        String dataPicture;

        // This is a constructor that allows you to pass in the JSON body
        public ReadingFileNoLoadingPostingToServer(JSONObject postData) {
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
                dataLogs = postData.getString("get_picture_data");
                dataMeterNotInList = postData.getString("meter_not_in_list");
                dataLogsArray = new JSONArray(dataLogs);
                dataArrayMeterNotInList = new JSONArray(dataMeterNotInList);
                total = dataLogsArray.length();
                //progressBar.setMax(0);
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading File");
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
                Liquid.POSTAuditApiData mPOSTAuditApiData;
                //Making a request to url and getting response

                //if(dataArray.length() == 0){
                    //return 2;
                //}
                for(int a = 0; a < dataLogsArray.length();a++){
                    JSONObject dataObject = new JSONObject(dataLogsArray.getString(a));
                    if(!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))){
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;
                        mImages = Liquid.getDiscPicture(AccountNumber,Subfolder);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
                        } else {
                            listFile = mImages.listFiles();
                            toUploadCount = toUploadCount + listFile.length;

                            for (int e = 0; e < listFile.length; e++) {
                                JSONArray final_image_result = new JSONArray();
                                JSONObject final_image_response = new JSONObject();
                                JSONObject data = new JSONObject();

                                data.put("Client", Liquid.Client);
                                data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                data.put("Filename", listFile[e].getName());
                                data.put("service_type", "MeterReading");
                                data.put("date", String.valueOf(dataObject.get("BillMonth"))+String.valueOf(dataObject.get("BillYear")));

                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

                                jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, imageArray.getJSONObject(0));
                                Log.i(TAG,jsonStr);
                                response = Liquid.StringToJsonObject(jsonStr);

                                if(response.getString("result").equals("false")){
                                    Liquid.ErrorUpload.put(dataObject);
                                }else{
                                    Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));
                                    imageUploaded = imageUploaded+1;
                                    progress = progress+1;
                                    Log.i(TAG, "File"+String.valueOf(progress));


                                }
                            }
                        }
                    }
                }


                for(int a = 0; a < dataArrayMeterNotInList.length();a++){
                    JSONObject dataObject = new JSONObject(dataArrayMeterNotInList.getString(a));

                    if(!AccountNumber.equals(String.valueOf(dataObject.get("job_id")))){
                        AccountNumber = String.valueOf(dataObject.get("job_id"));
                        Category = "";
                        File mImages;
                        File[] listFile;
                        String[] Subfolder = new String[1];
                        Subfolder[0] = Category;
                        int imageUploaded = 0;

                        mImages = Liquid.getDiscPicture(AccountNumber+"_audit",Subfolder);
                        if (!mImages.exists() && !mImages.mkdirs()) {
                            Liquid.ShowMessage(view.getContext(), "Can't create directory to save image");
                        } else {
                            listFile = mImages.listFiles();
                            toUploadCount = toUploadCount + listFile.length;

                            for (int e = 0; e < listFile.length; e++) {
                                JSONArray final_image_result = new JSONArray();
                                JSONObject final_image_response = new JSONObject();
                                JSONObject data = new JSONObject();

                                data.put("Client", Liquid.Client);
                                data.put("FileData", Liquid.imageToString(listFile[e].getAbsolutePath()));
                                data.put("Filename", listFile[e].getName());
                                data.put("service_type", "Audit");
                                data.put("date", Liquid.dateChangeFormat(Liquid.currentDate(),"yyyy-MM-dd","MMyyyy"));

                                //combine all data for image
                                final_image_result.put(data);
                                final_image_response.put("image", final_image_result);
                                imageData = final_image_response.getString("image");
                                imageArray = new JSONArray(imageData);

                                mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/tgblUploadImage.php");

                                jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, imageArray.getJSONObject(0));
                                Log.i(TAG,jsonStr);
                                response = Liquid.StringToJsonObject(jsonStr);

                                if(response.getString("result").equals("false")){
                                    Liquid.ErrorUpload.put(dataObject);
                                }else{
                                    Liquid.deleteRecursive(new File(listFile[e].getAbsolutePath()));
                                    imageUploaded = imageUploaded+1;
                                    progress = progress+1;
                                    Log.i(TAG, "File"+String.valueOf(progress));

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
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            try {
                switch(result){
                    case 29:
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");

                        break;
                    case 3:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available picture to upload!");
                        break;
                    case 4:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","There is no available signature to upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();

        }


    }

   /* public class DataReadingPostingToServer extends AsyncTask<Void, Integer, Integer> {
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
        public DataReadingPostingToServer(JSONObject postData) {
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
                dataPicture = postData.getString("picture");
                dataLogs = postData.getString("logs");
                dataMeterNotInList = postData.getString("meter_not_in_list");
                dataArray = new JSONArray(data);
                dataArrayPicture = new JSONArray(dataPicture);
                dataArrayLogs = new JSONArray(dataLogs);
                dataArrayMeterNotInList =  new JSONArray(dataMeterNotInList);
                total = dataArray.length();

                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading Data");
                mProgressDialog.setCancelable(false);
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
                if(dataArray.length() == 0){
                    //No Data
                    //return 2;
                }else{
                    Log.i(TAG,data);
                    //Making a request to url and getting response
                    for (int a = 0; a < dataArray.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Reading.php");
                        JSONObject dataObject = new JSONObject(dataArray.getString(a));
                        //Log.i(TAG, String.valueOf(dataObject));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                        Log.i(TAG,jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {
                            result_status = ReadingModel.UpdateUploadStatus(dataObject.getString("job_id").toString(),dataObject.getString("AccountNumber").toString());
                        }
                    }
                }
                if(dataArrayLogs.length() == 0){
                    //No Data
                    //return 2;
                }else {
                    progress = 0;
                    for (int a = 0; a < dataArrayLogs.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingLogs.php");
                        JSONObject dataObject = new JSONObject(dataArrayLogs.getString(a));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);

                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        Log.i(TAG,jsonStr);
                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {
                            result_status = ReadingModel.UpdateUploadStatusLogs(dataObject.getString("row_id").toString());
                        }
                    }
                }

                if(dataArrayPicture.length() == 0){
                    //No Data
                    //return 2;
                }else {
                    progress = 0;
                    for (int a = 0; a < dataArrayPicture.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingPicture.php");
                        JSONObject dataObject = new JSONObject(dataArrayPicture.getString(a));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                        Log.i(TAG, jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {
                            result_status = ReadingModel.UpdateUploadStatusPicture(dataObject.getString("row_id").toString());
                        }
                    }
                }

                if(dataArrayMeterNotInList.length() == 0){
                    //No Data
                    //return 2;
                }else {
                    progress = 0;
                    for (int a = 0; a < dataArrayMeterNotInList.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/NewMeterNotInList.php");
                        JSONObject dataObject = new JSONObject(dataArrayMeterNotInList.getString(a));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                        Log.i(TAG, jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        JSONObject response2 = new JSONObject(response.getString("Data"));
                        if (response2.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {
                            result_status = ReadingModel.UpdateUploadStatusMeterNotInList(dataObject.getString("row_id").toString());
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }*/

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
                        //Log.i(TAG, "TRISTAN:" + jsonStr);
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
                                result_status = ReadingModel.UpdateUploadStatus(dataObject.getString("job_id").toString(),dataObject.getString("AccountNumber").toString());
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }

    public class DataReadingPostingToServer extends AsyncTask<Void, Integer, Integer> {
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
        public DataReadingPostingToServer(JSONObject postData) {
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
                dataPicture = postData.getString("picture");
                dataLogs = postData.getString("logs");
                dataMeterNotInList = postData.getString("meter_not_in_list");
                dataArray = new JSONArray(data);
                dataArrayPicture = new JSONArray(dataPicture);
                dataArrayLogs = new JSONArray(dataLogs);
                dataArrayMeterNotInList =  new JSONArray(dataMeterNotInList);
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
                            mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingTest.php");

                            //Log.i(TAG, String.valueOf(test));
                            String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, reading);
                            Log.i(TAG,"TRISTAN:"+jsonStr);
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
                                    result_status = ReadingModel.UpdateUploadStatus(dataObject.getString("job_id").toString(),dataObject.getString("AccountNumber").toString());
                                }
                            }


                        }

                    }
                    if(dataArrayLogs.length() == 0){
                        //No Data
                        //return 2;
                    }else {

                        mProgressDialog.setMax(0);
                        mProgressDialog.setMax(dataArrayLogs.length());
                        progress = 0;

                        List<String> arrayListLogs = new ArrayList<String>();
                        for(int i = 0; i < dataArrayLogs.length(); i++){
                            arrayListLogs.add(dataArrayLogs.getString(i));
                        }
                        for(int j = 0; j < arrayListLogs.size(); j+=limit) {
                            int k = Math.min(j + limit, arrayListLogs.size());
                            List<String> subList = arrayListLogs.subList(j,k);

                            JSONArray newDataArrayLogs = new JSONArray(subList);
                            JSONObject readlogs = new JSONObject();
                            readlogs.put("sysid",Liquid.User);
                            readlogs.put("username",Liquid.Username);
                            readlogs.put("password",Liquid.Password);
                            readlogs.put("data",newDataArrayLogs);

                            mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingLogsTest.php");
                            //Log.i(TAG, String.valueOf(testlogs));
                            String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, readlogs);
                            //Log.i(TAG, "ALEX:" + jsonStr);
                            Log.i(TAG, "Tristan:" + jsonStr);
                            JSONObject response = Liquid.StringToJsonObject(jsonStr);
                            if (response.getString("result").equals("false")) {
                                Liquid.ErrorUpload.put(readlogs);
                            } else {
                                progress = progress + limit;
                                publishProgress(progress);
                                JSONArray newReadLogsArray = readlogs.getJSONArray("data");
                                for(int i=0; i < newReadLogsArray.length(); i++){
                                    JSONObject dataObject = new JSONObject(newReadLogsArray.getString(i));
                                    result_status = ReadingModel.UpdateUploadStatusLogs(dataObject.getString("row_id").toString());
                                }
                            }


                        }

                }

                if(dataArrayPicture.length() == 0){
                    //No Data
                    //return 2;
                }else {
                    mProgressDialog.setMax(0);
                    mProgressDialog.setMax(dataArrayPicture.length());
                    progress = 0;

                    List<String> arrayListPics = new ArrayList<String>();
                    for(int i = 0; i < dataArrayPicture.length(); i++){
                        arrayListPics.add(dataArrayPicture.getString(i));
                    }
                    for(int j = 0; j < arrayListPics.size(); j+=limit) {
                        int k = Math.min(j + limit, arrayListPics.size());
                        List<String> subList = arrayListPics.subList(j,k);

                        JSONArray newDataArrayPics = new JSONArray(subList);
                        JSONObject meterpics = new JSONObject();
                        meterpics.put("sysid",Liquid.User);
                        meterpics.put("username",Liquid.Username);
                        meterpics.put("password",Liquid.Password);
                        meterpics.put("data",newDataArrayPics);

                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingPictureTest.php");
                        //Log.i(TAG, String.valueOf(testpics));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, meterpics);
                        Log.i(TAG,"TristanTrista"+ jsonStr);
                        //Log.i(TAG, "ALEX:" + jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);

                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(meterpics);

                        } else {
                            progress = progress + limit;
                            publishProgress(progress);
                            JSONArray newMeterPicsArray = meterpics.getJSONArray("data");
                            for(int i=0; i < newMeterPicsArray.length(); i++){
                                JSONObject dataObject = new JSONObject(newMeterPicsArray.getString(i));
                                result_status = ReadingModel.UpdateUploadStatusPicture(dataObject.getString("row_id").toString());
                            }
                        }


                    }
                }

                if(dataArrayMeterNotInList.length() == 0){
                    //No Data
                    //return 2;
                }else {
                    mProgressDialog.setMax(0);
                    mProgressDialog.setMax(dataArrayMeterNotInList.length());
                    progress = 0;

                    List<String> arrayListMNL = new ArrayList<String>();
                    for(int i = 0; i < dataArrayMeterNotInList.length(); i++){
                        arrayListMNL.add(dataArrayMeterNotInList.getString(i));
                    }
                    for(int j = 0; j < arrayListMNL.size(); j+=limit) {
                        int k = Math.min(j + limit, arrayListMNL.size());
                        List<String> subList = arrayListMNL.subList(j,k);

                        JSONArray newDataArrayMNL = new JSONArray(subList);
                        JSONObject notinlist = new JSONObject();
                        notinlist.put("sysid",Liquid.User);
                        notinlist.put("username",Liquid.Username);
                        notinlist.put("password",Liquid.Password);
                        notinlist.put("data",newDataArrayMNL);

                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/NewMeterNotInListTest.php");
                        //Log.i(TAG, String.valueOf(testmnl));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, notinlist);
                        //Log.i(TAG, "TRISTAN:" + jsonStr);
                        //Log.i(TAG, "ALEX:" + jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        //JSONObject response2 = new JSONObject(response.getString("Data"));

                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(notinlist);
                        } else {
                            progress = progress + limit;
                            publishProgress(progress);
                            JSONArray newNotInListArray = notinlist.getJSONArray("data");
                            for(int i=0; i < newNotInListArray.length(); i++){
                                JSONObject dataObject = new JSONObject(newNotInListArray.getString(i));
                                result_status = ReadingModel.UpdateUploadStatusMeterNotInList(dataObject.getString("row_id").toString());
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }


    public class DataReadingNoLoadingPostingToServer extends AsyncTask<Void, Integer, Integer> {
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
        public DataReadingNoLoadingPostingToServer(JSONObject postData) {
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
                dataPicture = postData.getString("picture");
                dataLogs = postData.getString("logs");
                dataMeterNotInList = postData.getString("meter_not_in_list");
                dataArray = new JSONArray(data);
                dataArrayPicture = new JSONArray(dataPicture);
                dataArrayLogs = new JSONArray(dataLogs);
                dataArrayMeterNotInList =  new JSONArray(dataMeterNotInList);
                total = dataArray.length();

                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Processing....");
                mProgressDialog.setTitle("Uploading Data");
                mProgressDialog.setCancelable(false);
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
                if(dataArray.length() == 0){
                    //No Data
                    //return 2;
                }else{
                    Log.i(TAG,data);
                    //Making a request to url and getting response
                    progress = 0;
                    for (int a = 0; a < dataArray.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/Reading.php");
                        JSONObject dataObject = new JSONObject(dataArray.getString(a));
                        //Log.i(TAG, String.valueOf(dataObject));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);

                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        Log.i(TAG, String.valueOf(response));
                        Toast.makeText(getContext(),(String) "Uploaded "+progress+"/"+dataArray.length()+"",Toast.LENGTH_LONG);
                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {
                            progress++;
                            result_status = ReadingModel.UpdateUploadStatus(dataObject.getString("job_id").toString(),dataObject.getString("AccountNumber").toString());
                        }
                    }
                }
                if(dataArrayLogs.length() == 0){
                    //No Data
                    //return 2;
                }else {


                    for (int a = 0; a < dataArrayLogs.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingLogs.php");
                        JSONObject dataObject = new JSONObject(dataArrayLogs.getString(a));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);

                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        Log.i(TAG, String.valueOf(response));
                        Toast.makeText(getContext(),(String) "Uploaded "+progress+"/"+dataArrayLogs.length()+"",Toast.LENGTH_LONG).show();
                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {

                            result_status = ReadingModel.UpdateUploadStatusLogs(dataObject.getString("row_id").toString());
                        }
                    }
                }

                if(dataArrayPicture.length() == 0){
                    //No Data
                    //return 2;
                }else {

                    for (int a = 0; a < dataArrayPicture.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/ReadingPicture.php");
                        JSONObject dataObject = new JSONObject(dataArrayPicture.getString(a));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                        Log.i(TAG, jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        Log.i(TAG, String.valueOf(response));
                        Toast.makeText(getContext(),(String) "Uploaded "+progress+"/"+dataArrayPicture.length()+"",Toast.LENGTH_LONG).show();
                        if (response.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {
                            progress = progress + 1;

                            result_status = ReadingModel.UpdateUploadStatusPicture(dataObject.getString("row_id").toString());
                        }
                    }
                }

                if(dataArrayMeterNotInList.length() == 0){
                    //No Data
                    //return 2;
                }else {

                    progress = 0;
                    for (int a = 0; a < dataArrayMeterNotInList.length(); a++) {
                        mPOSTApiData = new Liquid.POSTApiData("fmts/php/api/NewMeterNotInList.php");
                        JSONObject dataObject = new JSONObject(dataArrayMeterNotInList.getString(a));
                        String jsonStr = sh.makeServicePostCall(mPOSTApiData.API_Link, dataObject);
                        Log.i(TAG, jsonStr);
                        JSONObject response = Liquid.StringToJsonObject(jsonStr);
                        JSONObject response2 = new JSONObject(response.getString("Data"));
                        Log.i(TAG, String.valueOf(response2));
                        Toast.makeText(getContext(),(String) "Uploaded "+progress+"/"+dataArrayMeterNotInList.length()+"",Toast.LENGTH_LONG).show();
                        if (response2.getString("result").equals("false")) {
                            Liquid.ErrorUpload.put(dataObject);
                        } else {

                            result_status = ReadingModel.UpdateUploadStatusMeterNotInList(dataObject.getString("row_id").toString());
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
                        Liquid.showDialogInfo(view.getContext(),"Valid","Successfully Uploaded!");
                        Log.i(TAG,"Successfully Uploaded");
                        break;
                    case 0:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Please check your Internet Connection!");
                        break;
                    case 1:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","Unsuccessfully Uploaded / Some data is not uploaded!");
                        break;
                    case 2:
                        Liquid.showDialogInfo(view.getContext(),"Valid","There is no data to be upload!");
                        break;
                    default:
                        Log.i(TAG,"Unsuccessfully Uploaded");
                        Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");

                }


            } catch (Exception e){
                Log.e(TAG,"Error :",e);
                Liquid.showDialogInfo(view.getContext(),"Invalid","An error has occured!");
            }
            mProgressDialog.dismiss();
        }
    }



}
