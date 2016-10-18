package com.e16din.baseproject.screens.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.e16din.baseproject.pattern.logic.BaseProjectLogic;
import com.e16din.baseproject.pattern.logic.SimpleBaseProjectLogic;

public abstract class SimpleBaseProjectFragment<MODEL> extends GemsFragment<MODEL> {

    @NonNull
    @Override
    public BaseProjectLogic<MODEL> onCreateLogic(Bundle savedInstanceState) {
        return new SimpleBaseProjectLogic<>(getBaseActivity(), savedInstanceState);
    }

}
