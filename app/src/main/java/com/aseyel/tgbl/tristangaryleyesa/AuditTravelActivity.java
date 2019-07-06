package com.aseyel.tgbl.tristangaryleyesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.aseyel.tgbl.tristangaryleyesa.adapter.AuditTravelRideAdapater;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.AuditModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;

import java.util.ArrayList;
import java.util.HashMap;

public class AuditTravelActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = AuditTravelActivity.class.getSimpleName();
    MaterialSearchView search_view;
    FloatingActionButton floatBtnAdd;
    AuditTravelRideAdapater Adapter;
    Button btnStart;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MenuItem searchMenuItem;
    LiquidGPS mLiquidGPS;
    RecyclerView rvList;
    String Fare;
    String Comment;
    String Vehicle;
    String JobOrderId;
    String Client;
    String AccountNumber;
    String Latitude;
    String Longitude;
    String JobOrderDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_travel);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Liquid.Status = "START";
        setup();
        GetData(true, Liquid.SelectedId,Liquid.SelectedAccountNumber,"","");
        btnStart.setText(Liquid.Status);
    }
    private void setup(){
        floatBtnAdd = (FloatingActionButton) findViewById(R.id.floatBtnAdd);
        search_view =(MaterialSearchView) findViewById(R.id.search_view);
        btnStart = (Button) findViewById(R.id.btnStart);
        Adapter = new AuditTravelRideAdapater(this);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(llm);
        rvList.setAdapter(Adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeReload);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        floatBtnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(Liquid.Status.equals("START")){
                   Liquid.showDialogInfo(AuditTravelActivity.this,"Invalid","Please start audit first!");
                }else{
                    Liquid.SelectedCode = "";
                    Intent i = new Intent(getApplicationContext(), AuditTravelRideActivity.class);
                    startActivity(i);
                }

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Status = btnStart.getText().toString();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(Liquid.Status.equals("START")){
                                    btnStart.setText("STOP");
                                }else{
                                    btnStart.setText("START");
                                }
                                boolean result = false;
                                Liquid.Status = btnStart.getText().toString();
                                mLiquidGPS = new LiquidGPS(AuditTravelActivity.this);
                                Fare = "";
                                Comment = Liquid.Status+" POINT";
                                JobOrderId = Liquid.SelectedId;
                                Client = Liquid.Client;
                                AccountNumber = Liquid.SelectedAccountNumber;
                                Latitude = String.valueOf(Liquid.Latitude);
                                Longitude = String.valueOf(Liquid.Longitude);
                                JobOrderDate = Liquid.SelectedJobOrderDate;
                                Vehicle = "";

                                result = AuditModel.doSubmitAuditTravelRide(
                                        JobOrderId,
                                        Client,
                                        AccountNumber,
                                        Vehicle,
                                        Fare,
                                        Comment,
                                        Latitude,
                                        Longitude,
                                        JobOrderDate,
                                        Liquid.Status
                                );

                                if(!result){
                                    Liquid.showDialogError(AuditTravelActivity.this,"Invald",Liquid.DefaultErrorMessage);
                                }else{
                                    result = AuditModel.doSubmitUpdateAuditStatus(Liquid.SelectedAccountNumber,Liquid.Status);

                                    if(!result){
                                        Liquid.showDialogError(AuditTravelActivity.this,"Error",Liquid.DefaultErrorMessage);
                                    }
                                }


                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                if(Status.equals("START")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuditTravelActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuditTravelActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }



            }
        });


    }

    public void DeleteData(final String JobOrderType, final String JobOrderId,final String AccountNumber,final int position){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        boolean result = false;
                        result = TrackingModel.DeleteAudit(JobOrderType,JobOrderId,AccountNumber);
                        if (result == true) {
                            Liquid.showDialogInfo(AuditTravelActivity.this, "Valid", "Successfully Deleted!");
                            Adapter.DeleteItem(position);
                        } else {
                            Liquid.showDialogError(AuditTravelActivity.this, "Invalid", "Unsuccessfully Deleted!");
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(AuditTravelActivity.this);
        builder.setMessage("Are you sure you want to DELETE?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetData(false, Liquid.SelectedId,Liquid.SelectedAccountNumber,  "",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );

        return true;
    }
    public void GetData(boolean animated,String JobOrderId,String AccountNumber, String AuditId,String Search){
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Log.i(TAG,JobOrderId+" "+AccountNumber);
        Cursor result = AuditModel.GetAuditTravelRides(JobOrderId,AccountNumber,AuditId,Search);
        try
        {
            if(result.getCount() == 0){
                Liquid.Status = "START";
                return;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                if(!result.getString(3).equals("")){
                    String Id = result.getString(11);
                    String Title = result.getString(3);
                    String Fare = result.getString(4);
                    String Comment = result.getString(5);
                    String Vehicle = result.getString(3);
                    String Details = "Vehicle : "+Vehicle+"\n"+
                            "Fare : "+Fare+"\n"+
                            "Details : "+Comment+"\n";
                    String Date = result.getString(9);
                    data.put("Id", Id);
                    data.put("Title", Title);
                    data.put("Details", Details);
                    data.put("Date", Date);
                    data.put("Filepath", "");

                    Liquid.Status = result.getString(12);;
                    final_result.add(data);
                }
            }


            Adapter.updateItems(animated,final_result);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetData(false, Liquid.SelectedId,Liquid.SelectedAccountNumber,"","");
    }

    @Override
    public void onRefresh() {
        GetData(false, Liquid.SelectedId,Liquid.SelectedAccountNumber,"","");
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
