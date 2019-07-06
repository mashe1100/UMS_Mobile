package com.aseyel.tgbl.tristangaryleyesa;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aseyel.tgbl.tristangaryleyesa.data.Liquid;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Miroslaw Stanek on 19.01.15.
 */
public class BaseLogisticActivity extends AppCompatActivity {
    private static final String TAG = BaseLogisticActivity.class.getSimpleName();
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
            switch(Liquid.Client){
                case "ngc_express":
                    toolbar.setNavigationIcon(R.drawable.ic_menu);

                    break;
                    default:
                        toolbar.setNavigationIcon(R.drawable.ic_profile);
            }

        }
    }



    public Toolbar getToolbar() {
        return toolbar;
    }




}