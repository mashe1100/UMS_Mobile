package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.FCViewPager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.aseyel.tgbl.tristangaryleyesa.adapter.SectionsPageAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.DatabaseHelper;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingActivationFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingAvailabilityFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingCDEFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingCommentFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingCoolerPlanogramFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingShelfPlanogramFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingSignatureFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingSoviFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingSoviLocationFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TrackingStoreStatusFragment;

import butterknife.BindView;


public class TrackingActivity extends BaseActivity {
    private static final String TAG = TrackingActivity.class.getSimpleName();
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private MenuItem searchMenuItem;
    private Fragment Availability,Sovi,Activation,CDE,CoolerPlanogram,ShelfPlanogram,SoviLocation,StoreStatus,Comment,Signature,Summary;
    public static DatabaseHelper mDatabaseHelper;
    private int SelectedTab = 0;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    private String Status = "";
    private  TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_tracking);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            GetData(Liquid.SelectedAccountNumber,Liquid.SelectedPeriod);
            initViews();
            setupViewPager(mViewPager);
            tabLayout = (TabLayout) findViewById(R.id.trackings_tabs);
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    SelectedTab = tab.getPosition();
                    switch(SelectedTab) {
                        case 0:

                            break;
                        case 1:
                            //selectPage(SelectedTab);
                            break;
                        case 2:
                            //selectPage(SelectedTab);
                            break;
                        case 3:
                            //selectPage(SelectedTab);
                            break;
                        case 4:
                            //selectPage(SelectedTab);
                            break;
                        case 5:
                            //selectPage(SelectedTab);
                            break;
                        case 6:
                            //selectPage(SelectedTab);
                            break;
                        case 7:
                            //selectPage(SelectedTab);
                            break;
                         }
                    }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    void selectPage(int pageIndex){
        if(Status == ""){
            pageIndex = 0;
        }
        tabLayout.setScrollPosition(pageIndex,0f,true);
        mViewPager.setCurrentItem(pageIndex,true);
    }

    private int ValidateStoreStatus(int selectedTab){
        if(Status == ""){
            return 0;
        }
        return selectedTab;
    }
    private void initViews(){
        mDatabaseHelper = new DatabaseHelper(this);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //Set up the ViewPager with the sections adapters
        mViewPager = (ViewPager) findViewById(R.id.trackings_container);
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

                        break;
                    case 1:

                        ((TrackingAvailabilityFragment) Availability).GetData(false,query);
                        break;
                    case 2:
                        ((TrackingSoviFragment) Sovi).GetData(false,query);
                        break;
                    case 3:
                        ((TrackingSoviLocationFragment) SoviLocation).GetData(false,query);
                        break;
                    case 4:
                        ((TrackingActivationFragment) Activation).GetData(false,Liquid.SelectedAccountNumber,query,"",Liquid.SelectedPeriod);

                        break;
                    case 5:
                        ((TrackingCDEFragment) CDE).GetData(false,Liquid.SelectedAccountNumber,"",query,Liquid.SelectedPeriod);
                        break;
                    case 6:
                        ((TrackingShelfPlanogramFragment) ShelfPlanogram).GetData(false,query);
                        break;
                    case 7:
                        //((TrackingCoolerPlanogramFragment) CoolerPlanogram).GetData(false,query);
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
    private void setupViewPager(ViewPager viewPager){

        StoreStatus =  new TrackingStoreStatusFragment();
        Availability =  new TrackingAvailabilityFragment();
        Sovi =  new TrackingSoviFragment();
        Activation =  new TrackingActivationFragment();
        CDE =  new TrackingCDEFragment();
       // CoolerPlanogram =  new TrackingCoolerPlanogramFragment();

        SoviLocation =  new TrackingSoviLocationFragment();
        Comment =  new TrackingCommentFragment();
        Signature =  new TrackingSignatureFragment();
        //Summary =  new TrackingSummaryFragment();

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragement(StoreStatus, "Store Status");

        if(Status.equals("")){

        }
        else if(Status.equals("Refused") || Status.equals("Closed")){
            adapter.addFragement(Comment, "Comment");
        }
        else{
            adapter.addFragement(Availability, "Availability");
            adapter.addFragement(Sovi, "Sovi");
            adapter.addFragement(SoviLocation, "Sovi Location");
            adapter.addFragement(Activation, "Activation");
            adapter.addFragement(CDE, "CDE");
            //adapter.addFragement(CoolerPlanogram, "Cooler Planogram");

            if(     Liquid.SelectedAccountName.toUpperCase().contains("RUSTANS") ||
                    Liquid.SelectedAccountName.toUpperCase().contains("SHOPWISE") ||
                    Liquid.SelectedAccountName.toUpperCase().contains("MARKETPLACE") ||
                    Liquid.SelectedAccountName.toUpperCase().contains("WELLCOME")
                    ){
                ShelfPlanogram =  new TrackingShelfPlanogramFragment();
                adapter.addFragement(ShelfPlanogram, "Shelf Planogram");
            }


            adapter.addFragement(Comment, "Comment");
            adapter.addFragement(Signature, "Signature");
            //adapter.addFragement(Summary, "Summary");
        }



        viewPager.setAdapter(adapter);

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

    public void GetData(String AccountNumber,
                        String Period){

        Cursor result = TrackingModel.GetStoreStatus(AccountNumber,
                Period);
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                Status = result.getString(0);
            }

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }
}
