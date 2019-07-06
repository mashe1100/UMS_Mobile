package com.aseyel.tgbl.tristangaryleyesa;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Miroslaw Stanek on 19.01.15.
 */
public class BaseDeliveryActivity extends AppCompatActivity {
    private static final String TAG = BaseDeliveryActivity.class.getSimpleName();
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    private MenuItem searchMenuItem;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }
    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }
    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_profile);
        }
    }



    public Toolbar getToolbar() {
        return toolbar;
    }




}