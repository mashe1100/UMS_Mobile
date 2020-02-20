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
import com.aseyel.tgbl.tristangaryleyesa.TrackingActivationActivity;
import com.aseyel.tgbl.tristangaryleyesa.TrackingCommentActivity;
import com.aseyel.tgbl.tristangaryleyesa.adapter.TrackingActivationAdapter;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingActivationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = TrackingActivationFragment.class.getSimpleName();
    private String[] GetColumns;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    TrackingActivationAdapter Adapter;
    Liquid.ApiData mApiData;
    View mView;
    File mFile;
    File[] listFile;
    File mImages;
    String[] Subfolder;
    FloatingActionButton floatBtnGallery;
    @BindView(R.id.rvTrackingActivationList)
    RecyclerView rvList;
    @BindView(R.id.fabTrackingActivationAdd)
    FloatingActionButton fabTrackingActivationAdd;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    String Category = "Merchandising Materials";
    @BindView (R.id.floatBtnComment)
    FloatingActionButton floatBtnComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fr
        // agment
        mView = inflater.inflate(R.layout.fragment_tab_tracking_activation, container, false);
        setup(mView);
        GetData(true,Liquid.SelectedAccountNumber,"","",Liquid.SelectedPeriod);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetData(false,Liquid.SelectedAccountNumber,"","",Liquid.SelectedPeriod);
    }

    private void setup(View view) {
        try {
            //Initialization
            Subfolder = new String[1];
            Subfolder[0] = Category;
            List = new ArrayList<>();
            ListDetails = new ArrayList<>();
            Adapter = new TrackingActivationAdapter(this);
            fabTrackingActivationAdd = (FloatingActionButton) view.findViewById(R.id.fabTrackingActivationAdd);
            floatBtnComment = (FloatingActionButton) view.findViewById(R.id.floatBtnComment);
            rvList = (RecyclerView) view.findViewById(R.id.rvTrackingActivationList);
            mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeReload);
            mSwipeRefreshLayout.setOnRefreshListener(this);
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

            fabTrackingActivationAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), TrackingActivationActivity.class);
                    v.getContext().startActivity(i);
                }
            });


            floatBtnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Liquid.SelectedCategory = "Merchandising Materials";
                    Intent i = new Intent(mView.getContext(), TrackingCommentActivity.class);
                    mView.getContext().startActivity(i);
                }
            });

        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
    }

    public void DeleteData(final String OutletNumber, final String Activation,final String Category, final String Period, final int position){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = false;
                        result = TrackingModel.DeleteActivation(OutletNumber,Activation,Category, Period);

                        if (result == true) {
                            Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Deleted!");
                            Adapter.DeleteItem(position);
                            //GetData(false,Liquid.SelectedAccountNumber,"","",Liquid.SelectedPeriod);

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

    public void GetData(boolean animated,
                        String AccountNumber,
                        String Description,
                        String Category,
                        String Period){
        String Details = "";
        String Id = "";
        String Title = "";
        String Date = "";
        ArrayList<HashMap<String, String>> final_result = new ArrayList<>();
        Cursor result = TrackingModel.GetTrackingActivation(AccountNumber,
                                                            Description,
                                                            Category,
                                                            Period);
        try
        {

            if(result.getCount() == 0){
                return;
            }

            while(result.moveToNext()){
                HashMap<String, String> data = new HashMap<>();
                Title = result.getString(1);
                Details = "Available : " + result.getString(2) + "\n"+
                          "Category : "+ result.getString(4) + "\n"+
                          "Count : "  + result.getString(3) ;
                Id = Title +"-"+ result.getString(4);
                Date = result.getString(7);
                data.put("Id", Id);
                data.put("Title", Title);
                data.put("Details", Details);
                data.put("Category", result.getString(4));
                data.put("Date", Date);
                data.put("Filepath",GetImagesThumbnail(Title,result.getString(4)));
                final_result.add(data);
            }


            Adapter.updateItems(animated,final_result);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }
    }

    private String GetImagesThumbnail(String value, String value2){
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

                    if(separated[2].equals(Liquid.RemoveSpecialCharacter((value))) && separated[3].equals(Liquid.RemoveSpecialCharacter(value2))){
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
