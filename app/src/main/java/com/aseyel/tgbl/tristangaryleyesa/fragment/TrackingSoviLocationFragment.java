package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingSoviLocationAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingSoviLocationFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TrackingSoviLocationFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    private ArrayList<HashMap<String, String>> List;
    private ArrayList<HashMap<String, String>> ListDetails;
    private TrackingSoviLocationAdapter Adapter;
    private Liquid.ApiData mApiData;
    private View mView;

    @BindView(R.id.rvList)
    RecyclerView rvList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_tracking_sovilocation, container, false);

        setup(mView);
        GetData(true,"");

        return mView;
    }

    private void setup(View view) {
        try {
            //Initialization
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingSoviLocationAdapter(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvList);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            //Setting up
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(llm);
            rvList.setAdapter(Adapter);

        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    public void GetData(boolean animated,String search){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = TrackingModel.GetSoviLocationList(search);
        try
        {

            if(result.getCount() == 0){
                return;
            }

            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                data.put("Id", result.getString(0));
                data.put("Title", result.getString(1));
                data.put("Details", "");
                data.put("Date", "");
                data.put("Filepath", String.valueOf(GetImagesThumbnail(result.getString(1))));

                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }


    public void DeleteData(final String OutletNumber, final String ProductCode, final String Period){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = false;
                        result = TrackingModel.DeleteSOVILocation(OutletNumber,ProductCode, Period);

                        if (result == true) {
                            Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Deleted!");
                        } else {
                            Liquid.showDialogError(getActivity(), "Invalid", "Unsuccessfully Deleted!");
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to DELETE?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }


    private int GetImagesThumbnail(String value){
        int imageURL = R.drawable.img_ums;
        switch(value){
            case "Shelves":
                imageURL = R.drawable.img_coke_location_shelves;
                break;
            case "Exhibits":
                imageURL = R.drawable.img_coke_location_exhibits;
                break;
            case "Common Cold Vault":
                imageURL = R.drawable.img_coke_location_companyownedcoolers;

                break;
            case "Company-Owned Coolers":
                imageURL = R.drawable.img_coke_location_commoncoldvault;
                break;
            default:
                imageURL = R.drawable.img_ums;
        }
        return imageURL;
    }

    @Override
    public void onRefresh() {
        GetData(false,"");
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
