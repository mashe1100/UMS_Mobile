package com.aseyel.tgbl.tristangaryleyesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.aseyel.tgbl.tristangaryleyesa.adapter.AuditTravelRideAdapater;
import com.aseyel.tgbl.tristangaryleyesa.adapter.MeterNotInListAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.LiquidModel;
import com.aseyel.tgbl.tristangaryleyesa.model.MeterNotInListModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;

public class MeterNotInListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = MeterNotInListActivity.class.getSimpleName();
    private final int NEW_METER_NOT_IN_LIST_FROM = 1;
    private MaterialSearchView search_view;
    private FloatingActionButton floatBtnAdd;
    private MeterNotInListAdapter Adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MenuItem searchMenuItem;
    private  RecyclerView rvList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_not_in_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch(requestCode){
           case NEW_METER_NOT_IN_LIST_FROM:
               GetMeterNotInList(false,Liquid.SelectedId);
               break;
               default:
       }
    }

    private void init(){
        floatBtnAdd = (FloatingActionButton) findViewById(R.id.floatBtnAdd);
        search_view =(MaterialSearchView) findViewById(R.id.search_view);
        Adapter = new MeterNotInListAdapter(this);
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
                Liquid.SelectedMeterNumber = "";
                Liquid.SelectedCode = "";
                Intent i = new Intent(MeterNotInListActivity.this, NewMeterNotInListActivity.class);
                startActivityForResult(i,NEW_METER_NOT_IN_LIST_FROM);

            }
        });
        GetMeterNotInList(true,Liquid.SelectedId);
    }

    public void GetMeterNotInList(boolean animated,String job_id){

        try
        {
            Adapter.updateItems(animated, MeterNotInListModel.GetMeterNotInListData(job_id));
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    public void DeleteData(final String id, final int position){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        boolean result = false;
                        result = MeterNotInListModel.DeleteAudit(id);
                        if (result == true) {
                            Liquid.showDialogInfo(MeterNotInListActivity.this, "Valid", "Successfully Deleted!");
                            Adapter.DeleteItem(position);
                        } else {
                            Liquid.showDialogError(MeterNotInListActivity.this, "Invalid", "Unsuccessfully Deleted!");
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MeterNotInListActivity.this);
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

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );

        return true;
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
    @Override
    public void onRefresh() {
        GetMeterNotInList(false,Liquid.SelectedId);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
