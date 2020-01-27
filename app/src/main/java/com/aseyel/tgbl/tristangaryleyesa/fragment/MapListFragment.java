package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aseyel.tgbl.tristangaryleyesa.ReadingGalleryActivity;
import com.aseyel.tgbl.tristangaryleyesa.model.DeliveryModel;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.workModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidGPS;

import java.util.ArrayList;
import java.util.HashMap;

public class MapListFragment extends Fragment implements OnMapReadyCallback, OnMarkerClickListener{
    private static final String TAG = LiquidGPS.class.getSimpleName();
    private GoogleMap mGoogleMap;
    private  MapView mMapView;
    private View mView;
    private  LiquidGPS mLiquidGPS;
    private Marker[] myMarker;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_maplist, container, false);
        mLiquidGPS = new LiquidGPS(mView.getContext());
        setup();

        return mView;
    }

    public void setup(){
        try{
            mLiquidGPS = new LiquidGPS(mView.getContext());
        }catch(Exception e){
            Liquid.ShowMessage(getActivity(),"Please allow UMS Mobile to access the device GPS.");
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);
            mMapView = (MapView) mView.findViewById(R.id.jobOrderListMap);
            if(mMapView != null){
                mMapView.onCreate(null);
                mMapView.onResume();
                mMapView.getMapAsync(this);
            }


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
            //
            //MyLocation
             if((ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(mView.getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                mGoogleMap.setMyLocationEnabled(true);
            } else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
                }
            }

        //settings
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mygoogleMapMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        //mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
            //mGoogleMap.setTrafficEnabled(true);
            //mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
        //mGoogleMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getActivity(),mView));
        ArrayList<HashMap<String, String>> data = GetData(Liquid.SelectedId,"");
        String latitude = "";
        String longitude = "";
        float markericon = 0;

        myMarker = new Marker[data.size()];
        for(int i = 0 ; i < data.size() ; i++ ) {
            latitude = "0";
            longitude = "0";
            markericon = 0;

           if(data.get(i).get("Latitude").isEmpty() && data.get(i).get("Longitude").isEmpty()){
               latitude = "0";
               longitude = "0";
           }
           else{
               latitude = data.get(i).get("Latitude");
               longitude = data.get(i).get("Longitude");
           }
//            if(data.get(i).get("Status") == null){
//                markericon = BitmapDescriptorFactory.HUE_RED;
//            }else{
//                latitude = data.get(i).get("R_Latitude");
//                longitude = data.get(i).get("R_Longitude");
//                markericon = BitmapDescriptorFactory.HUE_AZURE;
//            }

           myMarker[i] = createMarker(
                         Double.parseDouble(latitude),
                         Double.parseDouble(longitude),
                         data.get(i).get("Title"),
                         data.get(i).get("Details"),
                         markericon,
                    mGoogleMap);
        }
            double Latitude;
            double Longitude;
            if(data.size() == 0){
                Latitude = mLiquidGPS.getLatitude();
                Longitude = mLiquidGPS.getLongitude();
            }else{
                Latitude = Double.parseDouble(data.get(data.size()-1).get("Latitude").isEmpty() ? "0.0" : data.get(data.size()-1).get("Latitude"));
                Longitude = Double.parseDouble(data.get(data.size()-1).get("Longitude").isEmpty() ? "0.0" : data.get(data.size()-1).get("Longitude"));
            }

        CameraPosition LastPosition =  CameraPosition.builder().target(new LatLng(Latitude,Longitude)).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(LastPosition));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(40.6892447,-74.044502)).title("Batangas City").snippet("Tristan Gary Leyesa"));

        }catch(Exception e){
            Liquid.ShowMessage(getActivity(),"Please allow UMS Mobile to access the device GPS.");

            Log.e(TAG,"Error : ",e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case MY_PERMISSION_FINE_LOCATION:

                try{
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        if((ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(mView.getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                            mGoogleMap.setMyLocationEnabled(true);
                        }
                    }else{
                        Toast.makeText(mView.getContext(),"This app requires location permission to be granted", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){

                   Log.e(TAG,"Error: ",e);
                }

                break;
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, float iconResID,GoogleMap googleMap){
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
//                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(iconResID)));

    }

    public ArrayList<HashMap<String, String>> GetData(String job_id,String AccountNumber){
        String Details = "";

        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = null;
        switch (Liquid.ServiceType){
            case "READ AND BILL":
                result = ReadingModel.GetMapData(job_id);
                break;
            case "MESSENGER":
                result = DeliveryModel.GetMapData(job_id);
                break;
            default:
                //from coke
                result = workModel.GetCokeLocalJobOrderDetails(job_id,AccountNumber);
        }
        try
        {
            if(result.getCount() == 0){
                return final_result;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                data.put("JobOrderId", result.getString(0));
                data.put("AccountNumber", result.getString(3));
                data.put("AccountName", result.getString(1));
                data.put("MeterNumber", "");
                data.put("Status", "");
                switch (Liquid.ServiceType){
                    case "READ AND BILL":
                        data.put("MeterNumber", result.getString(12));
                        data.put("Status", result.getString(14));
                        data.put("Title", result.getString(2));
                        data.put("R_Latitude", result.getString(15));
                        data.put("R_Longitude", result.getString(16));
                        Details = "Account #: " + result.getString(13) + "\n"+
                                "Meter #: " + result.getString(12) + "\n" +
                                "Type: " + result.getString(4) + "\n" +
                                "Sequence: " + result.getString(7) + "\n" +
                                "Address: " + result.getString(6) + "\n" +
                                "Status: " + result.getString(11);
                        break;
                    default:
                        //from coke
                        data.put("Title", result.getString(3));
                        Details = "Owner: " + result.getString(2) + "\n"+
                                "Type : " + result.getString(4) + "\n" +
                                "Address : " + result.getString(6) + ", " + result.getString(7) + "\n"+
                                "Channel : " + result.getString(5);
                }
                data.put("Details", Details);
                data.put("Date", result.getString(8));
                data.put("Latitude", result.getString(9));
                data.put("Longitude", result.getString(10));
                final_result.add(data);
            }
            return final_result;
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return final_result;
        }

    }

    public void showInformation(String title, final String Message){
        final String SelectedId = title;
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton("Next",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                Intent i;
                switch (Liquid.ServiceType){
                    case "READ AND BILL":
                        String[] details = Message.split("\n");
                        String accountnumber = details[0].replace("Account #: ","");
                        String meternumber = details[0].replace("Meter #: ","");

                        Liquid.SelectedAccountNumber = accountnumber;
                        Liquid.AccountNumber = accountnumber;
                        Liquid.TrackingNumber = accountnumber;
                        Liquid.SelectedMeterNumber = meternumber;
                        Liquid.MeterNumber = meternumber;
                        Liquid.SelectedAccountName = SelectedId;
//                        Liquid.SelectedJobOrderDate = ListItems.get(adapterPosition).getJobOrderDate();
//                        Liquid.ReadingDate = ListItems.get(adapterPosition).getDate();


                        i = new Intent(getActivity(), ReadingGalleryActivity.class);
                        break;
                    default:
                        //from coke
                        Liquid.SelectedAccountNumber = SelectedId;
                        i = new Intent(mView.getContext(), TrackingActivity.class);
                }

                mView.getContext().startActivity(i);
            }
        });
        builder.setNegativeButton("Close",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for(int a = 0; a < myMarker.length; a++){
            if (marker.equals(myMarker[a]))
            {
                showInformation(myMarker[a].getTitle(), myMarker[a].getSnippet());
            }
        }

        return false;
    }
}
