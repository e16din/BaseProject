package com.e16din.baseproject.screens.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.e16din.baseproject.R;
import com.e16din.baseproject.pattern.logic.BaseProjectLogic;
import com.e16din.baseproject.pattern.logic.SimpleBaseProjectLogic;

public abstract class SimpleBaseProjectActivity<MODEL> extends GemsActivity<MODEL> {

    @NonNull
    @Override
    public BaseProjectLogic<MODEL> onCreateLogic(Bundle savedInstanceState) {
        return new SimpleBaseProjectLogic<>(this, savedInstanceState);
    }

    @Override
    protected int getBackDrawableId() {
        return isTaskRoot()
                ? super.getBackDrawableId()
                : R.drawable.ic_arrow_left_white;
    }
}
