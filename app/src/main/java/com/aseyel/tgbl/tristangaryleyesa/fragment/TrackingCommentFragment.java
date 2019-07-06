package com.aseyel.tgbl.tristangaryleyesa.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;
import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Romeo on 2018-01-13.
 */

public class TrackingCommentFragment extends Fragment {
    private static final String TAG = TrackingCommentFragment.class.getSimpleName();
    private String[] GetColumns;
    private int job_id;
    ArrayList<HashMap<String, String>> List;
    ArrayList<HashMap<String, String>> ListDetails;
    Liquid.ApiData mApiData;
    View mView;
    @BindView(R.id.txtComment)
    EditText txtComment;
    @BindView(R.id.btnClear)
    ImageButton btnClear;
    @BindView(R.id.floatBtnSubmit)
    FloatingActionButton floatBtnSubmit;
    @BindView(R.id.txtQuestion)
    TextView txtQuestion;
    String Comment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            mView = inflater.inflate(R.layout.fragment_tab_tracking_comment, container, false);
            setup(mView);
            GetData(Liquid.SelectedAccountNumber,Liquid.SelectedPeriod);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
        }
        return mView;
    }
    private void setup(View view) {
        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        txtQuestion.setText("Please leave any comment to help your tracking and our client to improve. Thank you.");
        floatBtnSubmit = (FloatingActionButton) view.findViewById(R.id.floatBtnSubmit);
        txtComment = (EditText) view.findViewById(R.id.txtComment);
        btnClear = (ImageButton) view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtComment.setText("");
            }
        });

        floatBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = false;
                Comment = String.valueOf(txtComment.getText());
                result =  TrackingModel.doSubmitComment(
                        Liquid.SelectedAccountNumber,
                        Comment,
                        Liquid.SelectedPeriod
                );
                if(result == true){
                    Liquid.showDialogInfo(getActivity(), "Valid", "Successfully Saved!");
                } else {
                    Liquid.showDialogError(mView.getContext(), "Invalid", "Unsuccessfully Saved!");
                }
            }
        });

    }

    public void GetData(String AccountNumber,
                        String Period){
        String Status = "";
        Cursor result = TrackingModel.GetComment(AccountNumber,
                Period);
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                Comment = result.getString(0);

            }
            txtComment.setText(Comment);

        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }

}
