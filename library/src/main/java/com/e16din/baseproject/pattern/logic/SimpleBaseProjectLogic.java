package com.e16din.baseproject.pattern.logic;


import android.os.Bundle;

import com.e16din.baseproject.screens.activity.BaseProjectActivity;
import com.e16din.requestmanager.Result;

public class SimpleBaseProjectLogic<MODEL> extends BaseProjectLogic<MODEL> {

    public SimpleBaseProjectLogic(BaseProjectActivity activity, Bundle savedInstanceState) {
        super(activity, savedInstanceState);
    }

    @Override
    public void onErrorFromServer(Result result) {
    }

    @Override
    public void onHttpError(int code, String message, String body, Runnable onErrorOkListener) {
    }
}
