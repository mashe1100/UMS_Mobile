package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.aseyel.tgbl.tristangaryleyesa.model.AccountModel;

public class ProfileActivity extends BaseActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private MenuItem searchMenuItem;
    private TextView txtFullname;
    private TextView txtUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_profile);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setup();
        }catch (Exception e){
            Log.e(TAG, "Error : ",e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setup(){
        txtFullname = (TextView) findViewById(R.id.txtFullname);
        txtUserId = (TextView) findViewById(R.id.txtUserId);
        GetUserDetails();
    }

    private void GetUserDetails(){
        Cursor result = AccountModel.GetLoginAccount();
        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                txtFullname.setText(result.getString(2).toString().toUpperCase());
                txtUserId.setText("@" + result.getString(0).toString());
            }
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }
}
