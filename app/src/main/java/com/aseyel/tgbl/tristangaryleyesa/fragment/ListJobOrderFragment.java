package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aseyel.tgbl.tristangaryleyesa.DeliveryActivity;
import com.aseyel.tgbl.tristangaryleyesa.DeliveryActivityV2;
import com.aseyel.tgbl.tristangaryleyesa.MeterNotInListActivity;
import com.aseyel.tgbl.tristangaryleyesa.NewMeterNotInListActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.adapter.JobOrderDetailsAdapater;
import com.aseyel.tgbl.tristangaryleyesa.NewJobOrderDetailsActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.DisconnectionModel;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidAudit;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class ListJobOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = ListJobOrderFragment.class.getSimpleName();

    private String[] GetColumns;
    private int job_id;
    private int CountAll = 0;
    private int CountRead = 0;
    private int CountUnread = 0;
    private int CountPrint = 0;
    private int CountUnprint = 0;
    private String SearchBy = "All";
    private ArrayList<HashMap<String, String>> List;
    private ArrayList<HashMap<String, String>> ListDetails;
    private JobOrderDetailsAdapater Adapter;
    private Liquid.ApiData mApiData;
    private View view;
    private FloatingActionButton floatBtnAdd;
    @BindView(R.id.rvJobOrderDetailsList)
     RecyclerView rvList;
    private Liquid.POSTAuditApiData mAuditApiData;
    private Liquid.CokeApiData mCokeApiData;
    private ProgressBar progressBar;
    private JSONObject mpostData;
    private ProgressDialog mProgressDialog;
    private AHBottomNavigation mBottomNavigationView;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_listjoborder, container, false);
        setup(view);
        GetClientData(true,Liquid.SelectedJobType,true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetClientData(false,Liquid.SelectedJobType,true);
    }

    private void setup(final View view) {
        try {
            //Initialization
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new JobOrderDetailsAdapater(this);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvJobOrderDetailsList);
            floatBtnAdd = (FloatingActionButton) view.findViewById(R.id.floatBtnAdd);
            mBottomNavigationView = (AHBottomNavigation) view.findViewById(R.id.mBottomNavigationView);

            job_id = 0;

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            //Setting up
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(llm);
            rvList.setAdapter(Adapter);
            rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(!rvList.canScrollVertically(1)){
                        Liquid.UpdateRecyclerItemLimit = Liquid.UpdateRecyclerItemLimit + 50;
                        GetClientData(true,Liquid.SelectedJobType,false);

                    }
                }
            });
            floatBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent e;
                switch(Liquid.SelectedJobType){
                    case "METER READER":
                            e = new Intent(view.getContext(), MeterNotInListActivity.class);
                            view.getContext().startActivity(e);
                        break;
                    case "Messengerial":
                    case "MESSENGERIAL":
                        AddDelivery();
                        break;
                        default:
                            Intent i = new Intent(view.getContext(), NewJobOrderDetailsActivity.class);
                            view.getContext().startActivity(i);
                }

                }
            });



            //floatBtnAdd.setVisibility(View.GONE);


            switch (Liquid.ServiceType){
                case "READ AND BILL":
                    ReadBillBottomNavigation();
                    break;
                case "MESSENGER":
                    MessengerialNavigation();
                    break;
                case "DISCONNECTION":
                    DisconnectionBottomNavigation();
                    break;
            }



            /*mBottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mAll:
                                    SearchBy = "All";
                                    GetReadAndBillData(false,Liquid.SelectedId,"");
                                    break;
                                case R.id.mRead:
                                    SearchBy = "Read";
                                    GetReadAndBillDataReaded(false,SearchBy,Liquid.SelectedId,"" );
                                    break;
                                case R.id.mUnread:
                                    SearchBy = "Unread";
                                    GetReadAndBillDataReaded(false,SearchBy,Liquid.SelectedId,"" );
                                    break;
                                case R.id.mPrint:
                                    SearchBy = "Print";
                                    GetReadAndBillDataReaded(false,SearchBy,Liquid.SelectedId,"" );
                                    break;

                                case R.id.mUnprint:
                                    SearchBy = "Unprint";
                                    GetReadAndBillDataReaded(false,SearchBy,Liquid.SelectedId,"" );
                                    break;

                            }
                            return true;
                        }
                    });*/

        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }
    public void DeliveryDetails(){
        try
        {
            String Details = "";
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = DeliveryModel.GetDeliveryDetails(Liquid.SelectedId);

            if(result.getCount() == 0){
                //Adapter.updateItems(animated,final_result);
            }
            while(result.moveToNext()){
                Details+= result.getString(0) + "\n";
            }

            Liquid.showDialogInfo(getContext(),"Details",Details);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);

        }
    }


    public void AddDelivery(){
        Intent e;
        Liquid.TrackingNumber = "";
        Liquid.RemarksCode = "";
        Liquid.Remarks = "";
        Liquid.DeliveryItemTypeCode = "";
        Liquid.DeliveryItemTypeDescription = "";
        Liquid.ReaderComment = "";
        e = new Intent(view.getContext(), DeliveryActivityV2.class);
        view.getContext().startActivity(e);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetClientData(false,Liquid.SelectedJobType,true);
    }
    public void MessengerialNavigation(){
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Details", R.drawable.ic_action_all, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Add", R.drawable.ic_action_accomplishment, R.color.colorAccent);


        mBottomNavigationView.addItem(item1);
        mBottomNavigationView.addItem(item2);


        mBottomNavigationView.setAccentColor(R.color.colorAccent);
        mBottomNavigationView.setInactiveColor(R.color.colorAccent);

        mBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position)
                {
                    case 0:

                        DeliveryDetails();
                        break;
                    case 1:

                        AddDelivery();
                        break;

                }
                return true;
            }
        });
    }
    public void ReadBillBottomNavigation(){
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("All", R.drawable.ic_action_all, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Read", R.drawable.ic_action_accomplishment, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Unread", R.drawable.ic_action_unaccomplish, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Print", R.drawable.ic_action_printed, R.color.colorAccent);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Unprint", R.drawable.ic_action_unprinted, R.color.colorAccent);

        mBottomNavigationView.addItem(item1);
        mBottomNavigationView.addItem(item2);
        mBottomNavigationView.addItem(item3);
        mBottomNavigationView.addItem(item4);
        mBottomNavigationView.addItem(item5);

        mBottomNavigationView.setAccentColor(R.color.colorAccent);
        mBottomNavigationView.setInactiveColor(R.color.colorAccent);

        mBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position)
                {
                    case 0:
                        SearchBy = "All";
                        GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                    case 1:
                        SearchBy = "Read";
                        GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                    case 2:
                        SearchBy = "Unread";
                        GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                    case 3:
                        SearchBy = "Print";
                        GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                    case 4:
                        SearchBy = "Unprint";
                        GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                }
                return true;
            }
        });
    }

    public void DisconnectionBottomNavigation(){
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("All", R.drawable.ic_action_all, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Disco", R.drawable.ic_action_accomplishment, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Coll", R.drawable.ic_action_collection, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Undone", R.drawable.ic_action_unprinted, R.color.colorAccent);

        mBottomNavigationView.addItem(item1);
        mBottomNavigationView.addItem(item2);
        mBottomNavigationView.addItem(item3);
        mBottomNavigationView.addItem(item4);

        mBottomNavigationView.setAccentColor(R.color.colorAccent);
        mBottomNavigationView.setInactiveColor(R.color.colorAccent);

        mBottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position)
                {
                    case 0:
                        SearchBy = "All";
                        GetDisconnectionDownload(false,Liquid.SelectedId,"");
                        break;
                    case 1:
                        SearchBy = "Disco";
                        GetDisconnectioAccomplishement("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                    case 2:
                        SearchBy = "Coll";
                        GetDisconnectioAccomplishement("",false,SearchBy,Liquid.SelectedId,"" );
                        break;
                    case 3:
                        SearchBy = "Undone";
                        GetDisconnectioAccomplishement("",false,SearchBy,Liquid.SelectedId,"" );
                        break;

                }
                return true;
            }
        });
    }
    public void DoUploadAudit(JSONObject postData) {
        try {

            mpostData = postData;

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
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

    public void DeleteData(final String JobOrderType, final String JobOrderId,final String AccountNumber,final int position){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        boolean result = false;
                        //boolean result_details = false;
                        result = TrackingModel.DeleteJobDetails(JobOrderType,JobOrderId,AccountNumber);
                        //result_details = TrackingModel.DeleteJobOrderDetails(JobOrderType,JobOrderId,AccountNumber);
                        if (result == true) {
                            Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Deleted!");
                            Adapter.DeleteItem(position);
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

    public void GetClientData(boolean animated,String JobType,boolean getCount){

        mBottomNavigationView.setVisibility(View.GONE);
        switch(JobType){
            case "TRACKING":
                GetData(animated,Liquid.SelectedId,"");
                break;
            case "AUDIT":
                GetAuditDownload(animated,Liquid.SelectedId,"");
                break;
            case "Messengerial":
            case "MESSENGERIAL":
                mBottomNavigationView.setVisibility(View.VISIBLE);
                floatBtnAdd.setVisibility(View.GONE);
               // SearchBy = "All";
                GetMessengerial(false,Liquid.SelectedId,"");
//                if(getCount){
//                    getCountItem();
//                }

                break;
            case "METER READER":
                mBottomNavigationView.setVisibility(View.VISIBLE);
                SearchBy = "All";
                GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );
                if(getCount){
                    getCountItem();
                }

                break;
            case "DISCONNECTOR":
                mBottomNavigationView.setVisibility(View.VISIBLE);
                CountAll = GetDisconnectionDownload(animated,Liquid.SelectedId,"");
                if(getCount){
                    getDisconnectionCountItem();
                }

                break;
        }

    }
    public void GetAuditDownload(boolean animated,String job_id,String AccountNumber){
        try{
            Adapter.updateItems(animated, AuditModel.GetAuditDownload(job_id,AccountNumber));
        }catch (Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }


    public int GetDisconnectionDownload(boolean animated,String job_id,String AccountNumber){
        try{
            ArrayList<HashMap<String, String>> data = new ArrayList<>();
            data = DisconnectionModel.GetDisconnectionDownload(job_id,AccountNumber);
            Adapter.updateItems(animated,data );
            return data.size();
        }catch (Exception e){
            Log.e(TAG,"Error : ",e);
            return 0;
        }

    }

    public void GetDisconnectionSearchDownload(boolean animated,String job_id,String AccountNumber){
        try{
            Adapter.updateItems(animated, DisconnectionModel.GetDisconnectionSearchDownload(job_id,AccountNumber));
        }catch (Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }


    public int GetDisconnectioAccomplishement(String ToGet,boolean animated,String SearchBy,String job_id,String AccountNumber){
        try
        {

            String Details = "";
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = workModel.GetDisconnectioAccomplishement(SearchBy,job_id,AccountNumber);

            if(ToGet.equals("Count")){
                return result.getCount();
            }

            if(result.getCount() == 0){
                //Adapter.updateItems(animated,final_result);
                return 0;
            }



            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String AccountName = result.getString(10);
                String CAccountNumber = result.getString(9);
                String MeterNumber = result.getString(36);
                String DisconenctionDate = result.getString(42);
                String Latitude = result.getString(31);
                String Longitude = result.getString(32);
                String JobOrderDate = result.getString(52);
                String Address = result.getString(29);
                String Remarks = result.getString(48);


                data.put("JobOrderId", result.getString(2));
                data.put("AccountNumber", CAccountNumber);
                data.put("AccountName", AccountName);
                data.put("MeterNumber", MeterNumber);
                data.put("Title", MeterNumber);
                Details = "Account Number : "+CAccountNumber+"\n"+
                        "Name : "+AccountName+"\n"+
                        "Address : "+Address+"\n"+
                        "Remarks : "+Remarks+"\n"+
                        "Discon Date : "+DisconenctionDate;
                data.put("Details", Details);
                data.put("Date", JobOrderDate);
                data.put("Latitude", Latitude);
                data.put("Longitude", Longitude);
                data.put("JobOrderDate",JobOrderDate);
                data.put("Accomplishment","");
                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);
            return result.getCount();
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return 0;
        }

    }

    public int GetMessengerial(boolean animated,String job_id,String TrackingNumber){
        try
        {

            String Details = "";
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = null;
            result = DeliveryModel.GetMessengerial(job_id,TrackingNumber);
            if(result.getCount() == 0){
                Adapter.updateItems(animated,final_result);
                return 0;
            }

            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String Status = result.getString(6);
                TrackingNumber = result.getString(3);
                String ItemTypeCode = result.getString(4);
                String ItemType = result.getString(5);
                String RemarksCode = result.getString(7);
                String Remarks = result.getString(8);
                String TimeStamp = result.getString(16);
                String Date = result.getString(16);
                data.put("JobOrderId", job_id);
                data.put("AccountNumber",TrackingNumber);
                data.put("AccountName", ItemType);
                data.put("MeterNumber",ItemTypeCode );
                data.put("Title",TrackingNumber);
                Details ="Item Type : " +  ItemTypeCode + " - " + ItemType + "\n"+
                        "Remarks: " +  RemarksCode + " - " + Remarks + "\n";

                data.put("Details", Details);
                data.put("Date", TimeStamp);
                data.put("Latitude", "0");
                data.put("Longitude", "0");
                data.put("JobOrderDate",Date);
                data.put("Status", Status);

                data.put("Accomplishment","0");
                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);
            return result.getCount();
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return 0;
        }

    }
    public int GetReadAndBillDataReaded(String ToGet,boolean animated,String SearchBy,String job_id,String AccountNumber){
        try
        {

            String Details = "";
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = null;
            result = workModel.GetReadAndBillDataReaded(ToGet,SearchBy,job_id,AccountNumber);
            if(ToGet.equals("Count")){
                while(result.moveToNext()) {
                    return Integer.parseInt(result.getString(0));
                }

            }
            result = workModel.GetReadAndBillDataReaded(ToGet,SearchBy,job_id,AccountNumber);
            if(result.getCount() == 0){
                Adapter.updateItems(animated,final_result);
                return 0;
            }

            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String Status = result.getString(9);
                AccountNumber = result.getString(30);
                String AccountName = result.getString(17);
                String MeterNumber = result.getString(37);
                String AccountType = result.getString(7);
                String Complete_Address = result.getString(29);
                String Sequence = result.getString(35);
                String Date = result.getString(71);
                String ReadingDate = result.getString(54);
                String Accomplishment = result.getString(84);
                String TotalAmountDue = result.getString(85);
                data.put("JobOrderId", result.getString(2));
                data.put("AccountNumber",AccountNumber);
                data.put("AccountName", AccountName);
                data.put("MeterNumber",MeterNumber );
                data.put("Title",AccountName);
                Details = "Account No.: " + AccountNumber + "\n"+
                        "Meter Number.: " + MeterNumber + "\n"+
                        "Serial : " + result.getString(63) + "\n"+
                        "Sequence : " + Sequence +"\n"+
                        "Address : " + Complete_Address + "\n"+
                        "Type : " + AccountType + "\n" +
                        "Status : " + Status + "\n" +
                        "Consumption : " + Accomplishment  +"\n"+
                        "Amount Due : P " + TotalAmountDue;
                data.put("Details", Details);
                data.put("Date", ReadingDate);
                data.put("Latitude", "0");
                data.put("Longitude", "0");
                data.put("JobOrderDate",Date);
                data.put("Status", Status);

                data.put("Accomplishment",Accomplishment);
                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);
            return result.getCount();
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return 0;
        }

    }


    public void getDisconnectionCountItem(){


        SearchBy = "Disco";
        CountRead = GetDisconnectioAccomplishement("Count",false,SearchBy,Liquid.SelectedId,"" );
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(CountRead))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 1);

        SearchBy = "Coll";
        CountUnread = GetDisconnectioAccomplishement("Count",false,SearchBy,Liquid.SelectedId,"" );
        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountUnread))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 2);

        SearchBy = "Undone";
        CountPrint = GetDisconnectioAccomplishement("Count",false,SearchBy,Liquid.SelectedId,"" );
        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountPrint))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 3);



        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountAll))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 0);
    }
    public void getCountItem(){
        SearchBy = "All";
        CountAll = GetReadAndBillDataReaded("Count",false,SearchBy,Liquid.SelectedId,"" );
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(CountAll))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 1);
        SearchBy = "Read";
        CountRead = GetReadAndBillDataReaded("Count",false,SearchBy,Liquid.SelectedId,"" );
        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountRead))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 1);

        SearchBy = "Unread";
        CountUnread = GetReadAndBillDataReaded("Count",false,SearchBy,Liquid.SelectedId,"" );
        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountUnread))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 2);

        SearchBy = "Print";
        CountPrint = GetReadAndBillDataReaded("Count",false,SearchBy,Liquid.SelectedId,"" );
        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountPrint))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 3);

        SearchBy = "Unprint";
        CountUnprint = GetReadAndBillDataReaded("Count",false,SearchBy,Liquid.SelectedId,"" );
        notification = new AHNotification.Builder()
                .setText(String.valueOf(CountUnprint))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 4);

         notification = new AHNotification.Builder()
                .setText(String.valueOf(CountAll))
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .build();
        mBottomNavigationView.setNotification(notification, 0);
    }
    public int GetReadAndBillData(boolean animated,String job_id,String AccountNumber){
        try
        {

            String Details = "";
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = workModel.GetReadAndBillJobOrderDetails(job_id,AccountNumber);

                if(result.getCount() == 0){
                    return 0;
                }

                while(result.moveToNext()){
                    HashMap<String, String> data = new HashMap<>();
                    String Status = result.getString(9);
                    AccountNumber = result.getString(30);
                    String AccountName = result.getString(17);
                    String MeterNumber = result.getString(37);
                    String AccountType = result.getString(7);
                    String Complete_Address = result.getString(29);
                    String Sequence = result.getString(35);
                    String Date = result.getString(71);
                    String rowid = result.getString(84);
                    data.put("JobOrderId", result.getString(2));
                    data.put("AccountNumber",AccountNumber);
                    data.put("AccountName", AccountName);
                    data.put("MeterNumber",MeterNumber );
                    data.put("Title",AccountName);
                    Details = "Account No.: " + AccountNumber + "\n"+
                            "Meter Number.: " + MeterNumber + "\n"+
                            "Sequence : " + Sequence +"\n"+
                            "Address : " + Complete_Address + "\n"+
                            "Type : " + AccountType + "\n" +
                            "Status : " + Status ;

                    data.put("Details", Details);
                    data.put("Date", Date);
                    data.put("Latitude", "0");
                    data.put("Longitude", "0");
                    data.put("JobOrderDate",Date);
                    data.put("Status", Status);
                    data.put("RowId",rowid);
                    final_result.add(data);
                }

                Adapter.updateItems(animated,final_result);
                return result.getCount();
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return 0;
        }

    }


    public void GetByAMNReadAndBillData(boolean animated,String job_id,String Name){
        try
        {
            String Details = "";
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = workModel.GetByAMNReadAndBillData(job_id,Name);


            if(result.getCount() == 0){
                Adapter.updateItems(animated,final_result);
                return;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String Status = result.getString(9);
                String AccountNumber = result.getString(30);
                String AccountName = result.getString(17);
                String MeterNumber = result.getString(37);
                String AccountType = result.getString(8);
                String Complete_Address = result.getString(29);
                String Sequence = result.getString(35);
                String Date = result.getString(71);
                String ReadingDate = result.getString(54);
                String Accomplishment = result.getString(84);
                String TotalAmountDue = result.getString(85);
                data.put("JobOrderId", result.getString(2));
                data.put("AccountNumber",AccountNumber);
                data.put("AccountName", AccountName);
                data.put("MeterNumber",MeterNumber );
                data.put("Title",AccountName);
                Details = "Account No.: " + AccountNumber + "\n"+
                        "Meter Number.: " + MeterNumber +"\n"+
                        "Serial : " + result.getString(63) + "\n"+
                        "Sequence : " + Sequence +"\n"+
                        "Address : " + Complete_Address + "\n"+
                        "Type : " + AccountType + "\n" +
                        "Status : " + Status + "\n" +
                        "Consumption : " + Accomplishment +"\n"+
                        "Amount Due : P " + TotalAmountDue;
                data.put("Details", Details);
                data.put("Date", ReadingDate);
                data.put("Latitude", "0");
                data.put("Longitude", "0");
                data.put("JobOrderDate",Date);
                data.put("Status", Status);
                data.put("Accomplishment",Accomplishment);
                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    public void GetData(boolean animated,String job_id,String AccountNumber){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = workModel.GetCokeLocalJobOrderDetails(job_id,AccountNumber);
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                String Status = "Pending";
                if(result.getString(11) == null){
                    Status = "Pending";
                }else{
                    Status = result.getString(11);
                }
                data.put("JobOrderId", result.getString(0));
                data.put("AccountNumber", result.getString(3));
                data.put("AccountName", result.getString(1));
                data.put("MeterNumber", "");
                data.put("Title", result.getString(1));
                Details = "Account No.: " + result.getString(3) + "\n"+
                          "Owner: " + result.getString(2) + "\n"+
                          "Type : " + result.getString(4) + "\n" +
                          "Address : " + result.getString(6) + ", " + result.getString(7) + "\n"+
                          "Channel : " + result.getString(5) +"\n"+
                          "Status : " + Status ;

                data.put("Details", Details);
                data.put("Date", result.getString(8));
                data.put("Latitude", result.getString(9));
                data.put("Longitude", result.getString(10));
                data.put("JobOrderDate","");
                data.put("Status", Status);
                data.put("Accomplishment","");
                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    @Override
    public void onRefresh() {
        switch(SearchBy){
            case "All":
                GetClientData(false,Liquid.SelectedJobType,true);
                break;
                default:
                    GetReadAndBillDataReaded("",false,SearchBy,Liquid.SelectedId,"" );

        }


        mSwipeRefreshLayout.setRefreshing(false);
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
                mAuditApiData = new Liquid.POSTAuditApiData("AuditTravelRide.php");
                if(dataArray.length() == 0){
                    return 2;
                }
                //Making a request to url and getting response
                for(int a = 0; a < dataArray.length();a++){
                    JSONObject dataObject = new JSONObject(dataArray.getString(a));

                    String jsonStr = sh.makeServicePostCall(mAuditApiData.API_Link,dataObject);

                    JSONObject response = Liquid.StringToJsonObject(jsonStr);
                    if(response.getString("result").equals("false")){
                        Liquid.ErrorUpload.put(dataObject);
                    }else{
                        progress = progress+1;
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
                        Log.i(TAG,"Unsuccessfully Uploaded");
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
                                mProgressDialog.setMax(listFile.length -1);
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
                                    Log.i(TAG, String.valueOf(jsonStr));
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
            mProgressDialog.setProgress(values[0]);
            //progressBar.setProgress(values[0]);
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
                if(dataArray.length() == 0){
                    //No Data
                    return 2;
                }
                mCokeApiData = new Liquid.CokeApiData("audit-route.php","auditController","TrackingPosting","");
                //Making a request to url and getting response
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
                            if(listFile.length == 0){
                                return 3;
                            }
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




}
