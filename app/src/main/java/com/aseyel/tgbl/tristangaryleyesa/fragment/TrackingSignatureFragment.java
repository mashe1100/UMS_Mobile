package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.TrackingSignatureActivity;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingSignatureFragment extends Fragment {
    private static final String TAG = TrackingSignatureFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    Liquid.ApiData mApiData;
    View mView;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.ivSignature)
    ImageView ivSignature;
    File[] listFile;
    File mImages;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            mView = inflater.inflate(R.layout.fragment_tab_tracking_signature, container, false);
            setup(mView);
            GetImages();
        }catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetImages();
    }
    private void setup(View view) {
        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        ivSignature = (ImageView) view.findViewById(R.id.ivSignature);
        txtQuestion.setText("Please get a signature of any authorize person of the Store. This can help to validate our tracking for the Store.");
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), TrackingSignatureActivity.class);
                v.getContext().startActivity(i);
            }
        });
    }

    private void GetImages(){
        try{
            mImages = Liquid.getDiscSignature(Liquid.SelectedAccountNumber);
            if(!mImages.exists() && !mImages.mkdirs()){
                Liquid.ShowMessage(mView.getContext(),"Can't create directory to save image");
            }
            else{
                listFile = mImages.listFiles();
                Bitmap bmp = BitmapFactory.decodeFile(listFile[0].getAbsolutePath());
                ivSignature.setImageBitmap(bmp);
            }
        }catch(Exception e){
            Log.e(TAG,"Error ",e);
        }
    }
}
