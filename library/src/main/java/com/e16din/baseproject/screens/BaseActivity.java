package com.e16din.baseproject.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.e16din.baseproject.pattern.DataContainer;
import com.e16din.baseproject.pattern.LogicContainer;
import com.e16din.baseproject.pattern.ViewInterface;
import com.e16din.baseproject.pattern.logic.Logic;
import com.e16din.datamanager.DataManager;

public abstract class BaseActivity<MODEL> extends AppCompatActivity
        implements ViewInterface<MODEL>, LogicContainer<MODEL>, DataContainer<MODEL> {

    public static final String KEY_WAS_LAUNCHED = "wasLaunched";
    public static final String KEY_WAS_AUTHORIZED = "wasAuthorized";

    public static final String KEY_APP_IS_RUNNING = "AppIsRunning";

    private static final String TAG = BaseActivity.class.getSimpleName();

    private Logic<MODEL> mLogic;


    @Override
    public void onInit(View v) {
        Toolbar toolbar = getToolbar();
        if (toolbar == null) {
            Log.w(TAG, "setContentView: toolbar is null, see the getToolbar() method");
            return;
        }

        setSupportActionBar(toolbar);

        updateTitle(title());

        toolbar.setNavigationIcon(getBackDrawableId());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
    public void onLogicCreated(Logic logic) {
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
    public abstract Logic<MODEL> onCreateLogic(Bundle savedInstanceState);

    @Override
    public Logic<MODEL> logic() {
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
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        logic().init(this, getWindow().getDecorView());
    }

    public void updateTitle(CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title);
    }

    public CharSequence title() {
        return getTitle();
    }

    protected abstract int getBackDrawableId();

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
        //[Workaround] Override this method in the activity that hosts the Fragment and call super
        // in order to receive the result inside onActivityResult from the fragment.
        super.onActivityResult(requestCode, resultCode, data);
        logic().onActivityResult(this);
    }

    @Override
    public void finish() {
        logic().hideProgress();
        super.finish();
    }
}
