package com.aseyel.tgbl.tristangaryleyesa;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Miroslaw Stanek on 19.01.15.
 */
public class BaseFormActivity extends AppCompatActivity {
    private static final String TAG = BaseFormActivity.class.getSimpleName();
    @Nullable
    @BindView(R.id.formtoolbar)
    Toolbar toolbar;

    private MenuItem formSubmitMenu;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }


    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        formSubmitMenu  = menu.findItem(R.id.action_form_submit);
        return true;
    }






}