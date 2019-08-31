package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.aseyel.tgbl.tristangaryleyesa.adapter.AuditTravelRideAdapater;
import com.aseyel.tgbl.tristangaryleyesa.adapter.ReadingRemarksAdapater;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadingRemarksActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public final String TAG = ReadingRemarksActivity.class.getSimpleName();
    private MaterialSearchView search_view;
    private MenuItem searchMenuItem;
    private ReadingRemarksAdapater Adapter;
    private RecyclerView rvList;
    public static EditText txtComment;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reading_remarks);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            init();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error :", e);
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                getRemarks(query);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                getRemarks(newText);

                return false;
            }
        } );

        return true;
    }
    private void init(){
        //initialization of the needed component and objects
        Liquid.DeliveryStep = "Remarks";
        txtComment = (EditText) findViewById(R.id.txtComment);
        Adapter = new ReadingRemarksAdapater(this);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(llm);
        rvList.setAdapter(Adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeReload);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        getRemarks("");
        txtComment.setText(Liquid.ReaderComment);

    }

    @Override
    public void onRefresh() {
        //Refresh the function here
        getRemarks("");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void getRemarks(String Query){
        switch(Liquid.SelectedJobType){
            case "METER READER":
                GetReadingRemarks(Query);
                break;
            case "Messengerial":
            case "MESSENGERIAL":
                GetDeliveryRemarks(Query);
                break;
        }
    }
    public void GetDeliveryRemarks(String Remarks) {
        try {
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = DeliveryModel.getRemarks(Remarks);
            if (result.getCount() == 0) {
                return;
            }

            while (result.moveToNext()) {
                HashMap<String, String> data = new HashMap<>();
                String Id = result.getString(0);
                String Title = result.getString(1);
                String Details = result.getString(1);
                String Date = Liquid.currentDateTime();

                    data.put("Id", Id);
                    data.put("Title", Title);
                    data.put("Details", Id+"-"+Details);
                    data.put("Date", Date);
                    data.put("Filepath", "");
                    final_result.add(data);


            }
            Adapter.updateItems(false,final_result);
        } catch (Exception e) {

            Log.e(TAG, "Error : ", e);
            return;
        }
    }

    public void GetReadingRemarks(String Remarks) {
        try {
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            Cursor result = workModel.GetReadingRemarks(Remarks);
            if (result.getCount() == 0) {
                return;
            }

            while (result.moveToNext()) {
                HashMap<String, String> data = new HashMap<>();
                String Id = result.getString(0);
                String Title = result.getString(1);
                String Details = result.getString(1);
                String Abbreviation = result.getString(2);
                String Date = Liquid.currentDateTime();
                if(Liquid.Reading.equals("")){
                    switch (Id){
                        case "0":

                            break;
                            default:
                                data.put("Id", Id);
                                data.put("Title", Title);
                                data.put("Details", Id+"-"+Details);
                                data.put("Date", Date);
                                data.put("Filepath", Abbreviation);
                                final_result.add(data);
                    }
                }else{
                    data.put("Id", Id);
                    data.put("Title", Title);
                    data.put("Details", Id+"-"+Details);
                    data.put("Date", Date);
                    data.put("Filepath", Abbreviation);
                    final_result.add(data);
                }

            }
            Adapter.updateItems(false,final_result);
        } catch (Exception e) {

            Log.e(TAG, "Error : ", e);
            return;
        }
    }
}
