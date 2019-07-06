package com.aseyel.tgbl.tristangaryleyesa;



import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.FrameLayout;


import com.aseyel.tgbl.tristangaryleyesa.fragment.TabMenuFragment;

public class MenuActivity extends BaseActivity {
    private static final int CONTENT_VIEW_ID = 10101010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



    @Override
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
}
