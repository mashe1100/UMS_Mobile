package com.aseyel.tgbl.tristangaryleyesa;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.aseyel.tgbl.tristangaryleyesa.adapter.SectionsPageAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.DatabaseHelper;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.ListJobOrderFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.MapListFragment;

/**
 * Created by Romeo on 2018-01-10.
 */

public class JobOrderActivity extends BaseActivity {
    private static final String TAG = JobOrderActivity.class.getSimpleName();
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private MenuItem searchMenuItem;
    private Fragment List,MapList;
    public static DatabaseHelper mDatabaseHelper;
    private int SelectedTab = 0;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_joborder);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            initViews();
            setupViewPager(mViewPager);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.joborder_tabs);
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_list);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        }catch (Exception e){
            Liquid.ShowMessage(JobOrderActivity.this,"Please allow UMS Mobile to access the device GPS.");
            Log.e(TAG,"Error : ",e);
        }
    }

    private void initViews(){
        mDatabaseHelper = new DatabaseHelper(this);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //Set up the ViewPager with the sections adapters
        mViewPager = (ViewPager) findViewById(R.id.joborder_container);

    }

    private void setupViewPager(ViewPager viewPager){
        try{
            List =  new ListJobOrderFragment();
            MapList =  new MapListFragment();
            SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
            adapter.addFragement(List, "");
            adapter.addFragement(MapList, "");
            viewPager.setAdapter(adapter);
        }catch(Exception e){
            Liquid.ShowMessage(JobOrderActivity.this,"Please allow UMS Mobile to access the device GPS.");
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
                switch(SelectedTab){
                    case 0:
                        switch(Liquid.SelectedJobType) {
                            case "TRACKING":
                             ((ListJobOrderFragment) List).GetData(false, Liquid.SelectedId, query);
                            break;
                            case "AUDIT":
                                ((ListJobOrderFragment) List).GetAuditDownload(false, Liquid.SelectedId, query);
                            break;
                            case "METER READER":
                                ((ListJobOrderFragment) List).GetByAMNReadAndBillData(false, Liquid.SelectedId, query);
                            break;
                            case "DISCONNECTOR":
                                ((ListJobOrderFragment) List).GetDisconnectionSearchDownload(false, Liquid.SelectedId, query);
                                break;
                        }
                        break;
                    case 1:

                        break;

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        } );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
