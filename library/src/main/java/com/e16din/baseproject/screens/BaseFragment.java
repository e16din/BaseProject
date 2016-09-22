package com.e16din.baseproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e16din.baseproject.pattern.DataContainer;
import com.e16din.baseproject.pattern.LogicContainer;
import com.e16din.baseproject.pattern.ViewInterface;
import com.e16din.baseproject.pattern.logic.Logic;


/**
 * Created by e16din on 08.12.15.
 */
public abstract class BaseFragment<MODEL> extends Fragment
        implements ViewInterface<MODEL>, LogicContainer<MODEL>, DataContainer<MODEL> {

    private Logic<MODEL> mLogic;

    @Override
    public void onInit(View v) {
    }

    @Override
    public void onBind(MODEL data) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vRoot = inflater.inflate(getLayoutId(), container, false);

        mLogic = onCreateLogic(savedInstanceState);
        onLogicCreated(mLogic);

        logic().init(this, vRoot);

        return vRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        logic().bind(this);
    }

    @Override
    public void onLogicCreated(Logic logic) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logic().onActivityResult(getBaseActivity());
    }

    public Toolbar getToolbar() {
        final Toolbar result = logic().getToolbar();

        if (result == null) {
            return getBaseActivity().getToolbar();
        }

        return result;
    }

    @Override
    public abstract Logic<MODEL> onCreateLogic(Bundle savedInstanceState);

    @Override
    public Logic<MODEL> logic() {
        return mLogic;
    }

    @Override
    public Bundle bundle() {
        return getArguments();
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
    public void onStart() {
        super.onStart();
        mLogic.onStart(getBaseActivity());
    }

    @Override
    public void onStop() {
        mLogic.onStop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        logic().onSaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    //- other

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getActivity() != null && isVisibleToUser) {
            onSelected();
        }
    }

    public void onSelected() {
        //override for view pagers
    }
}
