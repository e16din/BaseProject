package com.e16din.baseproject.pattern.logic;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.e16din.alertmanager.AlertManager;
import com.e16din.baseproject.screens.activity.BaseProjectActivity;
import com.e16din.requestmanager.ListResult;
import com.e16din.requestmanager.Result;

public abstract class OnCallListenerLogic<MODEL> extends ScreenLogic<MODEL> {

    public OnCallListenerLogic(BaseProjectActivity activity, Bundle savedInstanceState) {
        super(activity, savedInstanceState);
    }

    public void onCancel() {
        hideProgress();
    }

    public void afterResult() {
        hideProgress();
    }

    public <T extends Result> void onSuccess(T result, int statusCode) {
        if (result instanceof ListResult) {
            Log.i("RequestM_Size", "Size: " + ((ListResult) result).size());
        }
    }

    public abstract void onErrorFromServer(Result result);

    public abstract void onHttpError(int code, String message, String body, final Runnable onErrorOkListener);

    public void showErrorDialog(String error, @Nullable final Runnable onErrorOkListener) {
        AlertManager.manager(getActivity()).showAlert(error,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onErrorOkListener != null) {
                            onErrorOkListener.run();
                        }
                    }
                });
    }

    public boolean needCancel() {
        return isCanceled();
    }
}
