package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingCommentActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingAvailabilityAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingAvailabilityFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TrackingAvailabilityFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    private TextView mTextMessage;
    @BindView (R.id.floatBtnComment)
    FloatingActionButton floatBtnComment;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    TrackingAvailabilityAdapter Adapter;
    Liquid.ApiData mApiData;
    View mView;
    @BindView(R.id.rvTrackingAvailabilityList)
    RecyclerView rvList;
    int numberOfColumns = 3;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            mView = inflater.inflate(R.layout.fragment_tab_tracking_availability, container, false);
            //setup(mView);
            setupV2(mView);
            GetData(true,"Coke");
            return mView;

        }catch(Exception e){
            Log.e(TAG,"Error :",e);
            return null;
        }
    }

    private void setup(View view) {
        try {
            //Initialization
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingAvailabilityAdapter(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingAvailabilityList);
            floatBtnComment = (FloatingActionButton) view.findViewById(R.id.floatBtnComment);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);

            GridLayoutManager glm = new GridLayoutManager(getActivity(),numberOfColumns);
            //Setting up
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(glm);
            rvList.setAdapter(Adapter);

            floatBtnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = "Store Status";
                    Intent i = new Intent(mView.getContext(), TrackingCommentActivity.class);
                    mView.getContext().startActivity(i);
                }
            });
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    private void setupV2(View view) {
        try {
            //Initialization
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingAvailabilityAdapter(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingAvailabilityList);
            floatBtnComment = (FloatingActionButton) view.findViewById(R.id.floatBtnComment);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());

            //Setting up
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(llm);
            rvList.setAdapter(Adapter);

            floatBtnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = "Store Status";
                    Intent i = new Intent(mView.getContext(), TrackingCommentActivity.class);
                    mView.getContext().startActivity(i);
                }
            });
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    public void DeleteData(final String OutletNumber, final String ProductCode, final String Period){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = false;
                        result = TrackingModel.DeleteAvailability(OutletNumber,ProductCode, Period);

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

    public void GetData(boolean animated,String Search){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = TrackingModel.GetProductList(Search);
        try
        {
            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                data.put("productCode", result.getString(0));
                data.put("productName", result.getString(1));
                data.put("productType", result.getString(2));
                data.put("productPrice", "");
                data.put("productComment", "");
                data.put("productTimeStamp", "");
                data.put("productPicturePath", "");
                data.put("productModifiedby", "");
                final_result.add(data);
            }
            Adapter.updateItems(animated,final_result);
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }

    @Override
    public void onRefresh() {
        GetData(false,"Coke");
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
