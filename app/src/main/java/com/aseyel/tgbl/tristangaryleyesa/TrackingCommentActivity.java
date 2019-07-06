package com.aseyel.tgbl.tristangaryleyesa;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidFile;

import butterknife.BindView;

public class TrackingCommentActivity extends BaseFormActivity {
    private static final String TAG = TrackingCommentActivity.class.getSimpleName();
    @BindView(R.id.txtComment)
    EditText txtComment;
    TextView txtNote;
    LiquidFile mLiquidFile;
    String Comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_comment);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        GetData(Liquid.SelectedAccountNumber,Liquid.SelectedCategory,Liquid.SelectedPeriod);
    }

    private void setup(){
        txtNote = (TextView) findViewById(R.id.txtNote);
        txtComment = (EditText) findViewById(R.id.txtComment);
        txtNote.setText("Put a category comment, this can help your tracked data validate.");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                boolean result = false;
                Comment = txtComment.getText().toString();
                result =  TrackingModel.doSubmitComment(Liquid.SelectedAccountNumber,
                        Liquid.SelectedCategory,
                        Comment,
                        Liquid.SelectedPeriod
                );
                if (result == true) {
                    Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
                } else {
                    Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetData(String AccountNumber,
                        String Category,
                        String Period){

        Cursor result = TrackingModel.GetCategoryComment(
                AccountNumber,
                Category,
                Period);
        try
        {

            if(result.getCount() == 0){
                return;
            }
            while(result.moveToNext()){
                Comment = result.getString(2);
            }

            txtComment.setText(Comment);
        }
        catch(Exception e){
            Log.e(TAG,"Error : ",e);
            return;
        }

    }
}
