package com.aseyel.tgbl.tristangaryleyesa;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.TrackingModel;
import com.aseyel.tgbl.tristangaryleyesa.services.LiquidCanvas;

import butterknife.BindView;

public class TrackingSignatureActivity extends BaseFormActivity {
    private static final String TAG = TrackingSignatureActivity.class.getSimpleName();
    private LiquidCanvas mLiquidCanvas;
    @BindView(R.id.btnClear)
    ImageButton btnClear;
    String SignatureName;
    LinearLayout mContent;
    View mView;
    LiquidCanvas mSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new View(this);
        setContentView(R.layout.activity_tracking_signature);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
    }

    private void setup(){
        mContent = (LinearLayout) findViewById(R.id.rlSignature);
        btnClear = (ImageButton) findViewById(R.id.btnClear);
        mSignature = new LiquidCanvas(this, null,mContent);
        mContent.addView(mSignature);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clear();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_form_submit:
                boolean result = false;
                SignatureName = Liquid.SelectedAccountNumber;
                result = TrackingModel.doSubmitSignature(
                        Liquid.SelectedAccountNumber,
                        Liquid.SelectedAccountName,
                        Liquid.SelectedPeriod
                );

                if(result == true){
                    mSignature.save(SignatureName);
                    Liquid.showDialogNext(this, "Valid", "Successfully Saved!");
                }else{
                    Liquid.showDialogError(this, "Invalid", "Unsuccessfully Saved!");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
