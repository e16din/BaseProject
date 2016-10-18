package com.e16din.baseproject.screens.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.e16din.baseproject.BaseProjectConstants;
import com.e16din.baseproject.pattern.DataContainer;
import com.e16din.baseproject.pattern.LogicContainer;
import com.e16din.baseproject.pattern.ViewInterface;
import com.e16din.baseproject.pattern.logic.BaseProjectLogic;
import com.e16din.datamanager.DataManager;
import com.e16din.intentmaster.Extra;

public abstract class BaseProjectActivity<MODEL> extends AppCompatActivity
        implements ViewInterface<MODEL>, LogicContainer<MODEL>, DataContainer<MODEL>, BaseProjectConstants {

    public static final String KEY_WAS_LAUNCHED = "wasLaunched";
    public static final String KEY_WAS_AUTHORIZED = "wasAuthorized";

    public static final String KEY_APP_IS_RUNNING = "AppIsRunning";

    public static final String TAG = BaseProjectActivity.class.getSimpleName();


    private BaseProjectLogic<MODEL> mLogic;

    private Intent mActivityResult;
    private boolean mHasActivityResult;


    @Override
    public void onInit(View v) {
        initToolbar();
    }

    protected void initToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar == null) {
            updateTitle(title());

            final ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                if (getBackDrawableId() != BaseProjectConstants.WRONG_VALUE) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeButtonEnabled(true);
                    actionBar.setHomeAsUpIndicator(getBackDrawableId());
                }
            }
            return;
        }

        setSupportActionBar(toolbar);

        updateTitle(title());

        if (getBackDrawableId() != BaseProjectConstants.WRONG_VALUE) {
            toolbar.setNavigationIcon(getBackDrawableId());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() == WRONG_VALUE) {
            return super.onCreateOptionsMenu(menu);
        }

        logic().inflate(getMenuRes(), menu);
        return true;
    }

    protected int getMenuRes() {
        return WRONG_VALUE;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBind(MODEL data) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogic = onCreateLogic(savedInstanceState);
        onLogicCreated(mLogic);

        setContentView(getLayoutId());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        logic().bind(this);
    }

    @Override
    public void onLogicCreated(BaseProjectLogic logic) {
    }

    @Override
    public Context getContext() {
        return this;
    }

    public Toolbar getToolbar() {
        return logic().getToolbar();
    }

    @NonNull
    @Override
    public abstract BaseProjectLogic<MODEL> onCreateLogic(Bundle savedInstanceState);

    @Override
    public BaseProjectLogic<MODEL> logic() {
        return mLogic;
    }

    @Override
    public Bundle bundle() {
        return getIntent().getExtras();
    }

    @Override
    public MODEL getData() {
        return logic().getData(this);
    }

    @Override
    public MODEL getData(String key) {
        return logic().getData(this, key);
    }


    @Override
    protected void onStop() {
        mLogic.onStop();
        logic().hideProgress();
        DataManager.getInstance().save(KEY_APP_IS_RUNNING, false);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataManager.getInstance().save(KEY_APP_IS_RUNNING, true);
        mLogic.onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mHasActivityResult) {
            if (mActivityResult != null) {
                MODEL d = (MODEL) Extra.get(mActivityResult);

                if (d == null) {
                    onActivityResult(mActivityResult);
                } else {
                    onActivityResult(d);
                }
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        logic().init(this, getWindow().getDecorView());
    }

    public void updateTitle(CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public CharSequence title() {
        return getTitle();
    }

    protected int getBackDrawableId() {
        return BaseProjectConstants.WRONG_VALUE;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        logic().onSaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    protected void replaceFragment(int containerId, Fragment fragment) {
        if (fragment == null) {
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logic().onActivityResult(this);

        mHasActivityResult = resultCode == RESULT_OK && Extra.has(data);
        mActivityResult = data;
    }

    /**
     * Called after onResume (to before onResume use Activity.onActivityResult())
     */
    public void onActivityResult(@Nullable MODEL data) {
        //override it
    }

    /**
     * Called after onResume (to before onResume use Activity.onActivityResult())
     */
    public void onActivityResult(Intent data) {
        //override it
    }

    @Override
    public void finish() {
        logic().hideProgress();
        super.finish();
    }
}
