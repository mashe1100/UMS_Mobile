package com.aseyel.tgbl.tristangaryleyesa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aseyel.tgbl.tristangaryleyesa.adapter.LiquidSearchAdapater;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.http.HttpHandler;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.miguelcatalan.materialsearchview.SearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends BaseActivity  implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = SearchActivity.class.getSimpleName();
    private MenuItem searchMenuItem;
    private LiquidSearchAdapater mLiquidSearchAdapater;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private String Search = "";
    private ArrayList<HashMap<String, String>> List;
    private boolean animation = true;
    private SwipeRefreshLayout swipeReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        List = new ArrayList<>();
        swipeReload = (SwipeRefreshLayout) findViewById(R.id.swipeReload);
        swipeReload.setOnRefreshListener(this);
        mLiquidSearchAdapater = new LiquidSearchAdapater(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        //Setting up
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mLiquidSearchAdapater);
        animation = true;
        new GetSearch().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search = query;
                animation = false;
                List.clear();
                new GetSearch().execute();
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
    public void onRefresh() {
        List.clear();
        animation = false;
        new GetSearch().execute();
        swipeReload.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_form_submit:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String GetDriver(String Search){
        HttpHandler sh = new HttpHandler();
        Liquid.GETUMSAPI mGETUMSAPI;
        mGETUMSAPI = new Liquid.GETUMSAPI("lms/php/api/TripTicket.php",
                "&request_type=GetEmployees&"+
                        "client=ngc_express&"+
                        "search="+Search+"&"+
                        "page=1"
                );
        String jsonStr  = sh.makeServiceCall(mGETUMSAPI.API_Link);
        return jsonStr;
    }

    private class GetSearch extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SearchActivity.this);
            mProgressDialog.setMessage("Searching...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
            String jsonStr = GetDriver(Search);
            Log.i(TAG, jsonStr);
            JSONObject response = Liquid.StringToJsonObject(jsonStr);
            response = new JSONObject(response.getString("Employees"));
            JSONArray decodejsonObj = new JSONArray(response.getString("result"));

            for (int i = 0; i < decodejsonObj.length(); i++) {
                JSONObject c = decodejsonObj.getJSONObject(i);
                HashMap<String, String> data = new HashMap<>();
                // adding each child node to HashMap key => value
                String Details = "Position : "+ c.getString("position") + "\n"+
                                 "Mobile No : " + c.getString("mobileno") + "\n";
                data.put("Id", c.getString("id"));
                data.put("Title", c.getString("lastname") +", "+ c.getString("firstname") + c.getString("middlename"));
                data.put("Details", Details);
                data.put("Date", "");
                data.put("Filepath", "");
                List.add(data);
            }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            mLiquidSearchAdapater.updateItems(animation,List);
        }
    }
}
