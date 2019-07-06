package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingCommentActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingShelfPlanogramAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingShelfPlanogramFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TrackingAvailabilityFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    private TextView mTextMessage;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    TrackingShelfPlanogramAdapter Adapter;
    Liquid.ApiData mApiData;
    View mView;
    @BindView(R.id.rvTrackingShelfPlanogramList)
    RecyclerView rvList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView (R.id.floatBtnComment)
    FloatingActionButton floatBtnComment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            mView = inflater.inflate(R.layout.fragment_tab_tracking_shelfplanogram, container, false);
            setup(mView);
            GetData(true,"");
        }catch(Exception e){
            Log.e(TAG,"Error :",e);
        }
        return mView;
    }
    private void setup(View view) {
        try {
            //Initialization
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingShelfPlanogramAdapter(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingShelfPlanogramList);
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
                    Liquid.SelectedCategory = "Shelf Planogram";
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
                        result = TrackingModel.DeleteShelfPlanogram(OutletNumber,ProductCode, Period);

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
    public void GetData(boolean animated,String search){
        String Details = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = TrackingModel.GetShelfPlanogramList(search);
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

    private int GetImagesThumbnail(String value){
        int imageURL = R.drawable.img_ums;
        switch(value){
            case "Sparkling":
                imageURL = R.drawable.img_coke_product_sparkling;
                break;
            case "Water":
                imageURL = R.drawable.img_coke_product_water;
                break;
            case "RTD Juice":
                imageURL = R.drawable.img_coke_product_rtdjuice;
                break;
            case "RTD Tea":
                imageURL = R.drawable.img_coke_product_rtdtea;
                break;
            case "Sports":
                imageURL = R.drawable.img_coke_product_sports;
                break;
            case "Powdered Juice":
                imageURL = R.drawable.img_coke_product_powderedjuice;
                break;
            case "Powdered Tea":
                imageURL = R.drawable.img_ums;
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
