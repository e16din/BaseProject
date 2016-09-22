package com.e16din.baseproject.pattern.logic;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.e16din.alertmanager.AlertManager;
import com.e16din.baseproject.R;
import com.e16din.baseproject.pattern.DataContainer;
import com.e16din.baseproject.pattern.ViewInterface;
import com.e16din.baseproject.screens.BaseActivity;
import com.e16din.lightutils.utils.U;
import com.e16din.lightutils.utils.ViewUtils;

import java.io.Serializable;

public abstract class Logic<MODEL> extends OnCallListenerLogic<MODEL> implements Serializable {

    public Logic(BaseActivity activity, Bundle savedInstanceState) {
        super(activity, savedInstanceState);
    }

    public void developingStub() {
        AlertManager.manager(getActivity()).showAlert(U.getString(R.string.base_developing_stub));
    }

    public <T extends DataContainer<MODEL> & ViewInterface<MODEL>> void bind(T owner) {
        final Bundle savedInstanceStateBundle = getSavedInstanceStateBundle();
        //TODO: getData may be null
        MODEL data = savedInstanceStateBundle == null
                ? getData(owner)
                : (MODEL) savedInstanceStateBundle.get(KEY_DATA);

        owner.onBind(data);
    }

    public void init(ViewInterface owner, View v) {
        //first find a toolbar to get it onInit
        U.recursiveLoopChildren(getActivity().findViewById(android.R.id.content), new ViewUtils.LoopChildrenCallback() {
            @Override
            public void onChild(View view, ViewGroup viewGroup, int i) {
                if (viewGroup == null) return;

                if (viewGroup instanceof Toolbar) {
                    setToolbar((Toolbar) viewGroup);
                }
            }
        });

        owner.onInit(v);
    }

    public void onSaveInstanceState(DataContainer<MODEL> owner, Bundle outState) {
        //TODO: getData may be null
        MODEL data = owner.getData();

        if (data instanceof Parcelable) {
            outState.putParcelable(KEY_DATA, (Parcelable) data);
        } else {
            outState.putSerializable(KEY_DATA, (Serializable) data);
        }
    }
}
