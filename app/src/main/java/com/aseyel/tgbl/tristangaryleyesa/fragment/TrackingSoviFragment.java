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
import android.widget.LinearLayout;

import com.aseyel.tgbl.tristangaryleyesa.GalleryActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingCommentActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingSoviAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingSoviFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = TrackingSoviFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    TrackingSoviAdapter Adapter;
    Liquid.ApiData mApiData;
    View mView;
    @BindView(R.id.rvTrackingSoviList)
    RecyclerView rvList;
    FloatingActionButton floatBtnGallery;
    String Category = "SOVI";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView (R.id.floatBtnComment)
    FloatingActionButton floatBtnComment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_tracking_sovi, container, false);
        //setup(mView);
        setupV2(mView);
        GetData(true,"Coke");
        return mView;
    }
    private void setup(View view) {
        try {
            //Initialization
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingSoviAdapter(this);
            floatBtnComment = (FloatingActionButton) view.findViewById(R.id.floatBtnComment);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingSoviList);
            int numberOfColumns = 3;
            GridLayoutManager glm = new GridLayoutManager(getActivity(),numberOfColumns);
            //Setting up
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(glm);
            rvList.setAdapter(Adapter);
            floatBtnGallery = (FloatingActionButton) view.findViewById(R.id.floatBtnGallery);
            floatBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = Category;
                    Intent i = new Intent(mView.getContext(), GalleryActivity.class);
                    mView.getContext().startActivity(i);
                }
            });

            floatBtnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = Category;
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
            Adapter = new TrackingSoviAdapter(this);
            floatBtnComment = (FloatingActionButton) view.findViewById(R.id.floatBtnComment);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingSoviList);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            //Setting up
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(llm);
            rvList.setAdapter(Adapter);
            floatBtnGallery = (FloatingActionButton) view.findViewById(R.id.floatBtnGallery);
            floatBtnGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = Category;
                    Intent i = new Intent(mView.getContext(), GalleryActivity.class);
                    mView.getContext().startActivity(i);
                }
            });

            floatBtnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = Category;
                    Intent i = new Intent(mView.getContext(), TrackingCommentActivity.class);
                    mView.getContext().startActivity(i);
                }
            });
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    public void DeleteData(final String OutletNumber, final String ProductCode, final String Category, final String Period){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = false;
                        result = TrackingModel.DeleteSOVI(OutletNumber,ProductCode,Category, Period);

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
        Cursor result = TrackingModel.GetSoviList(Search);
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
                data.put("productCategory", result.getString(3));
                data.put("productNumKof", "0");
                data.put("productNumNonKof", "0");
                data.put("productNumCans", "0");
                data.put("productNumSspet", "0");
                data.put("productNumMspet", "0");
                data.put("productNumSsdoy", "0");
                data.put("productNumSsbrick", "0");
                data.put("productNumMsbrick", "0");
                data.put("productNumSswedge", "0");
                data.put("productNumBox", "0");
                data.put("productNumLitro", "0");
                data.put("productNumPounch", "0");
                data.put("productComment", "");
                data.put("productTimeStamp", "");
                data.put("productPicturePath", "");
                data.put("productModifiedby", "");

                final_result.add(data);
            }

            Log.i(TAG,final_result.toString());

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
