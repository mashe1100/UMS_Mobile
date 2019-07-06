package com.aseyel.tgbl.tristangaryleyesa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.adapter.PlaceAutocompleteAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogisticsActivity extends BaseLogisticActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,GoogleApiClient.OnConnectionFailedListener,PlaceSelectionListener {
    private static final String TAG = LogisticsActivity.class.getSimpleName();
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mGoogleMap;
    private MapView mMapView;;
    private LiquidGPS mLiquidGPS;
    private Marker[] myMarker;
    //private AutoCompleteTextView mSearchingLocation;
    //private AutoCompleteTextView mGoingtoLocation;
    //private PlaceAutocompleteFragment mSearchingLocation;
   // private PlaceAutocompleteFragment mGoingtoLocation;
    private TextView mSearchingLocation;
    private TextView mGoingtoLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton btnMyLocation;
    private AutocompleteFilter filter;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private int searchLocation = 0;
    private Button btnTripTicket;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        init();
    }


    public  void clearData(){
        Liquid.GoingToLocation = "";
        Liquid.GoingToLocationLatitude = "";
        Liquid.GoingToLocationLongtitude = "";
        Liquid.SearchLocation = "";
        Liquid.SearchLocationLatitude = "";
        Liquid.SearchLocationLongtitude = "";
        mSearchingLocation.setText("");
        mGoingtoLocation.setText("");
    }

    private void init(){

        mLiquidGPS = new LiquidGPS(this);
        mMapView = (MapView) findViewById(R.id.mMap);
       //mSearchingLocation = (AutoCompleteTextView) findViewById(R.id.mSearchingLocation);
        //mGoingtoLocation = (AutoCompleteTextView) findViewById(R.id.mGoingtoLocation);
        mSearchingLocation = (TextView) findViewById(R.id.mSearchingLocation);
        mGoingtoLocation = (TextView) findViewById(R.id.mGoingtoLocation);
        btnMyLocation = (FloatingActionButton) findViewById(R.id.btnMyLocation);
        btnTripTicket = (Button) findViewById(R.id.btnTripTicket);

        mLocationPermissionsGranted = true;

        filter = new AutocompleteFilter.Builder().setCountry("PH").build();

        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        mSearchingLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                searchLocation = 0;

                openAutocompleteActivity();
            }
        });

        mGoingtoLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                searchLocation = 1;
                openAutocompleteActivity();
            }
        });


       /*  mSearchingLocation = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.mSearchingLocation);
         mGoingtoLocation = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.mGoingtoLocation);

        mSearchingLocation.setOnPlaceSelectedListener(this);
        mGoingtoLocation.setOnPlaceSelectedListener(this);*/


        /*
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, filter);

        mSearchingLocation.setAdapter(mPlaceAutocompleteAdapter);
        mGoingtoLocation.setAdapter(mPlaceAutocompleteAdapter);

        mSearchingLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                  geoSearchLocate();
                }
            }


        });
        mGoingtoLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                geoGoingToLocate();
            }
        });*/
       /* mGoingtoLocation.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching

                    geoGoingToLocate();
                }

                return false;
            }
        }); */


        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        btnTripTicket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(Liquid.SearchLocation.equals("") && Liquid.GoingToLocation.equals("")){
                    Liquid.showDialogInfo(LogisticsActivity.this,"Invalid","Please select locations!");
                }else{
                    Intent i = new Intent(LogisticsActivity.this, NewTripActivity.class);
                    startActivity(i);
                }

            }
        });


    }


    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(filter)
                    .build(this);

            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LogisticsActivity.this, MenuActivity.class);
                    LogisticsActivity.this.startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.

                switch(searchLocation){
                    case 0:
                        /*mSearchingLocation.setText(formatPlaceDetails(getResources(), place.getName(),
                                place.getId(), place.getAddress(), place.getPhoneNumber(),
                                place.getWebsiteUri()));*/
                        mSearchingLocation.setText(formatPlaceDetails(getResources(), place.getName(),
                                "", place.getAddress(), place.getPhoneNumber(),
                                place.getWebsiteUri()));
                        Liquid.SearchLocation = String.valueOf(place.getName());
                        Liquid.SearchLocationLatitude = String.valueOf(place.getLatLng().latitude);
                        Liquid.SearchLocationLongtitude = String.valueOf(place.getLatLng().longitude);


                        break;
                    case 1:
                        mGoingtoLocation.setText(formatPlaceDetails(getResources(), place.getName(),
                                "", place.getAddress(), place.getPhoneNumber(),
                                place.getWebsiteUri()));

                        Liquid.GoingToLocation = String.valueOf(place.getName());
                        Liquid.GoingToLocationLatitude = String.valueOf(place.getLatLng().latitude);
                        Liquid.GoingToLocationLongtitude = String.valueOf(place.getLatLng().longitude);

                        break;
                }

                moveCamera(place.getLatLng(),DEFAULT_ZOOM, String.valueOf(place.getAddress()));
                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    //mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                   //mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }



    private void geoSearchLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        /*String searchString = mSearchingLocation.getText().toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }*/
    }

    private void geoGoingToLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        /*String searchString = mSearchingLocation.getText().toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }*/
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mGoogleMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{

            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            Log.i(TAG, String.valueOf(currentLocation.getLatitude()));
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(LogisticsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }

        //CameraPosition LastPosition =  CameraPosition.builder().target(new LatLng(Double.parseDouble(String.valueOf(mLiquidGPS.getLatitude())),Double.parseDouble(String.valueOf(mLiquidGPS.getLongitude())))).zoom(30).bearing(0).tilt(45).build();
        //mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(LastPosition));
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
            MapsInitializer.initialize(this);
            mGoogleMap = googleMap;
            //
            //MyLocation
            if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                mGoogleMap.setMyLocationEnabled(true);
            } else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
                }
            }

            //settings
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            //mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            //mGoogleMap.getUiSettings().setCompassEnabled(true);
            //mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

            mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
            mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
            mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

            mGoogleMap.setOnMarkerClickListener(this);


            CameraPosition LastPosition =  CameraPosition.builder().target(new LatLng(Double.parseDouble(String.valueOf(mLiquidGPS.getLatitude())),Double.parseDouble(String.valueOf(mLiquidGPS.getLongitude())))).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(LastPosition));
            //googleMap.addMarker(new MarkerOptions().position(new LatLng(40.6892447,-74.044502)).title("Batangas City").snippet("Tristan Gary Leyesa"));
            //getDeviceLocation();
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case MY_PERMISSION_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){


                    if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                        mGoogleMap.setMyLocationEnabled(true);

                    }
                }else{
                    Toast.makeText(this,"This app requires location permission to be granted", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        // Format the returned place's details and display them in the TextView.

    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details_2, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details_2, name, id, address, phoneNumber,
                websiteUri));

    }
}
