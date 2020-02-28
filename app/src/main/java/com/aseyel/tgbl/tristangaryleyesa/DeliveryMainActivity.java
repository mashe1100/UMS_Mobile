package com.aseyel.tgbl.tristangaryleyesa;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aseyel.tgbl.tristangaryleyesa.adapter.LocalJobOrderAdapter;
import com.aseyel.tgbl.tristangaryleyesa.adapter.MeterNotInListAdapter;
import com.aseyel.tgbl.tristangaryleyesa.adapter.SectionsPageAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabCloudFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabDeliveryFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabLocalFragment;
import com.aseyel.tgbl.tristangaryleyesa.fragment.TabMenuFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;

public class DeliveryMainActivity extends BaseActivity {
    private final String TAG = DeliveryMainActivity.class.getSimpleName();
    private static final String DEVICE_DEFAULT_SMS_PACKAGE_KEY = "com.aseyel.tgbl.tristangaryleyesa.deviceDefaultSmsPackage";
    private static final int SMS_PERMISSION_CODE = 0;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ViewPager mViewPager;
    private MaterialSearchView search_view;
    private Fragment Local,MenuSettings;
    private MenuItem searchMenuItem;
    private MaterialSearchView searchView;
    private TabLayout tabLayout;
    private int SelectedTab = 0;
    private DisplayMetrics displaymetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_main);
        init();
        initSMS();
    }

    private void init(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Liquid.verifyStoragePermissions(this);
        checkLocationPermission();
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPager(mViewPager);
        tabController(tabLayout);
        getScreenSize();
        Liquid.GetSettings();
        Liquid.GetUserDetails();
        //GetMeterNotInList(true,Liquid.SelectedId);
    }

    private void getScreenSize(){
        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Liquid.screenHeight = displaymetrics.heightPixels;
        Liquid.screenWidth = displaymetrics.widthPixels;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(DeliveryMainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(DeliveryMainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(DeliveryMainActivity.this)
                        .setTitle("Location Permision")
                        .setMessage("Using UMS Mobile, We need to access the GPS function.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(DeliveryMainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(DeliveryMainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void setupViewPager(ViewPager viewPager){
        Local =  new TabDeliveryFragment();
        MenuSettings =  new TabMenuFragment();
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragement(Local, "");
        adapter.addFragement(MenuSettings,  "");
        viewPager.setAdapter(adapter);
    }

    private void tabController(TabLayout tabLayout){
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_global_menu_direct_blue);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SelectedTab = tab.getPosition();
                switch(SelectedTab){
                    case 0:

                        break;
                    case 1:

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
    }

    private void setDeviceDefaultSmsPackage(String packageName) {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
        startActivity(intent);
    }

    private void saveDeviceDefaultSmsPackage() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (hasNoPreviousSmsDefaultPackage(preferences)) {
            String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(this);
            preferences.edit().putString(DEVICE_DEFAULT_SMS_PACKAGE_KEY, defaultSmsPackage).apply();
        }
    }

    private boolean hasNoPreviousSmsDefaultPackage(SharedPreferences preferences) {
        return !preferences.contains(DEVICE_DEFAULT_SMS_PACKAGE_KEY);
    }

    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(DeliveryMainActivity.this,
                android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(DeliveryMainActivity.this,
                        android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DeliveryMainActivity.this, android.Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }

        ActivityCompat.requestPermissions(DeliveryMainActivity.this, new String[]{android.Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);

    }

    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    private void initSMS(){
        setDeviceDefaultSmsPackage(getPackageName());
        saveDeviceDefaultSmsPackage();

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }
        // this is for tracking the device always point to point
        /*final Handler mHandler = new Handler();
        getDeviceLocation();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = 0;
                while (x == 0) {
                    try {
                        Thread.sleep(3600000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                getDeviceLocation();

                                // TODO Auto-generated method stub
                                //new ReceiverActivity.GPSPosting().execute();
                                // Write your code here to update the UI.
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(TAG,"Tristan Gary Leyesa",e);
                    }
                }
            }
        }).start();*/

        final Handler mHandler2 = new Handler();

        /*if(isNetworkAvailable()){
            UploadBackground();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int x = 0;
                while (x == 0) {
                    try {
                        Thread.sleep(30000);
                        mHandler2.post(new Runnable() {
                            @Override
                            public void run() {
                                if(isNetworkAvailable()){
                                    UploadBackground();
                                }
                                // TODO Auto-generated method stub
                                //new ReceiverActivity.GPSPosting().execute();
                                // Write your code here to update the UI.
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e(TAG,"Tristan Gary Leyesa",e);
                    }
                }
            }
        }).start();*/
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DeliveryMainActivity.this, ProfileActivity.class);
                    DeliveryMainActivity.this.startActivity(i);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchView =(MaterialSearchView) findViewById(R.id.search_view);
        searchMenuItem  = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchMenuItem);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                switch(SelectedTab){
                    case 0:
                        ((TabDeliveryFragment) Local).GetStockIn(false,query);
                        break;
                    case 1:

                        break;
                    case 2:

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
