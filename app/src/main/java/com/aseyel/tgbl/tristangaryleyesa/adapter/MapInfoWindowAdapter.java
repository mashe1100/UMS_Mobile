package com.aseyel.tgbl.tristangaryleyesa.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.aseyel.tgbl.tristangaryleyesa.R;

/**
 * Created by Romeo on 2018-01-13.
 */

public class MapInfoWindowAdapter  implements InfoWindowAdapter {
    Activity mActivity;
    View mView;
    public MapInfoWindowAdapter(Activity activity,View view){
        mActivity = activity;
        mView = view;
        mView = mActivity.getLayoutInflater().inflate(R.layout.map_mark_info_content, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView tvTitle = ((TextView) mView.findViewById(R.id.map_title));
        tvTitle.setText(marker.getTitle());
        TextView tvSnippet = ((TextView) mView.findViewById(R.id.map_snippet));
        tvSnippet.setText(marker.getSnippet());

        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
