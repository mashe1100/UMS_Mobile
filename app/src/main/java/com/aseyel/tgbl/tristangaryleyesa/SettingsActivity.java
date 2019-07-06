package com.aseyel.tgbl.tristangaryleyesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.aseyel.tgbl.tristangaryleyesa.model.ReadingModel;
import com.aseyel.tgbl.tristangaryleyesa.model.SettingModel;

import java.util.Set;

public class SettingsActivity extends BaseActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private Switch switchReverse;
    private Switch switchHideKeyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Liquid.GetSettings();
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void init(){
        switchHideKeyboard = (Switch) findViewById(R.id.switchHideKeyboard);
        switchReverse = (Switch) findViewById(R.id.switchReverse);
        if(Liquid.ReverseInput == 1){
            switchReverse.setChecked(true);
        }
        if(Liquid.HideKeyboard == 1){
            switchHideKeyboard.setChecked(true);
        }

        switchReverse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchReverse.isChecked()){
                    SettingModel.UpdateSettings("ReverseInput","1");
                }else{
                    SettingModel.UpdateSettings("ReverseInput","0");
                }
                Liquid.GetSettings();
            }
        });

        switchHideKeyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchHideKeyboard.isChecked()){
                    SettingModel.UpdateSettings("HideKeyboard","1");
                }else{
                    SettingModel.UpdateSettings("HideKeyboard","0");
                }
                Liquid.GetSettings();
            }
        });


    }
}
