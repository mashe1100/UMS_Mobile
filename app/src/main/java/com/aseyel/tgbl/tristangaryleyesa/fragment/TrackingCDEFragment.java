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

import com.aseyel.tgbl.tristangaryleyesa.GalleryActivity;
import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingCDEActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingCDEAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingCDEFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TrackingCDEFragment.class.getSimpleName();
    private String[] GetColumns;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    TrackingCDEAdapter Adapter;
    Liquid.ApiData mApiData;
    View mView;
    String Count = "0";

    File mFile;
    File[] listFile;
    File mImages;
    String[] Subfolder;
    @BindView(R.id.rvTrackingCDEList)
    RecyclerView rvList;

    @BindView(R.id.fabTrackingCDEAdd)
    FloatingActionButton fabTrackingCDEAdd;
    FloatingActionButton floatBtnGallery;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String Category = "CDE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_tracking_cde, container, false);

        setup(mView);
        GetData(true,Liquid.SelectedAccountNumber,"","",Liquid.SelectedPeriod);
        return mView;
    }

    public void DeleteData(final String OutletNumber, final String ProductCode, final String Period,final int postion){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = false;
                        result = TrackingModel.DeleteCDE(OutletNumber,ProductCode, Period);

                        if (result == true) {
                            Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Deleted!");
                            Adapter.DeleteItem(postion);
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

    private void setup(View view) {
        try {
            //Initialization
            Subfolder = new String[1];
            Subfolder[0] = Category;
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingCDEAdapter(this);
            fabTrackingCDEAdd = (FloatingActionButton) view.findViewById(R.id.fabTrackingCDEAdd);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingCDEList);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());

            //Settingup
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

            fabTrackingCDEAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCode = Count + "|CDE Compliance";
                    Intent i = new Intent(v.getContext(), TrackingCDEActivity.class);
                    v.getContext().startActivity(i);
                }
            });

        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        GetData(false,Liquid.SelectedAccountNumber,"","",Liquid.SelectedPeriod);
    }



    public void GetData(boolean animated,
                        String AccountNumber,
                        String Area,
                        String Cooler,
                        String Period){
        String Details = "";
        String Id = "";
        String Title = "";
        String Date = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = TrackingModel.GetTrackingCDE(
                AccountNumber,
                Area,
                Cooler,
                Period);
        try
        {

            if(result.getCount() == 0){
                Count = String.valueOf(1);
                return;
            }
            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                Title = result.getString(2);
                Details = "Area : " + result.getString(1);
                Id = result.getString(2);
                Date = result.getString(7);
                data.put("Id", Id);
                data.put("Title", Title);
                data.put("Details", Details);
                data.put("Date", Date);
                data.put("Filepath",GetImagesThumbnail(Title));
                final_result.add(data);
            }

            Count = String.valueOf(final_result.size()+1);
            Adapter.updateItems(animated,final_result);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

    private String GetImagesThumbnail(String value){
        mImages = Liquid.getDiscPicture(Liquid.SelectedAccountNumber,Subfolder);
        if(!mImages.exists() && !mImages.mkdirs()){
            Liquid.ShowMessage(mView.getContext(),"Can't create directory to save image");
        }
        else{
            listFile = mImages.listFiles();
            ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
            if(listFile.length != 0){

                for(int a = 0; a < listFile.length; a++){
                    String[] separated  = listFile[a].getName().split("_");
                    if(separated[3].equals(Liquid.RemoveSpecialCharacter(value))){
                        return listFile[a].getAbsolutePath();
                    }
                }
            }
        }
        return "";
    }


    @Override
    public void onRefresh() {
        GetData(false,Liquid.SelectedAccountNumber,"","",Liquid.SelectedPeriod);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
