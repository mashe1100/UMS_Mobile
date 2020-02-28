package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingSummaryFragment extends Fragment {
    private static final String TAG = TrackingSummaryFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    Liquid.ApiData mApiData;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            mView = inflater.inflate(R.layout.fragment_tab_tracking_summary, container, false);
            setup(mView);
        }
            catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
        return mView;
    }
    private void setup(View view) {

    }
}
