package com.e16din.baseproject.screens.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.e16din.baseproject.pattern.logic.BaseProjectLogic;
import com.e16din.baseproject.pattern.logic.SimpleBaseProjectLogic;

public abstract class SimpleBaseProjectDialogFragment<MODEL> extends GemsDialogFragment<MODEL> {

    @NonNull
    @Override
    public BaseProjectLogic<MODEL> onCreateLogic(Bundle savedInstanceState) {
        return new SimpleBaseProjectLogic<>(getBaseActivity(), savedInstanceState);
    }

}
