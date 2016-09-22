package com.e16din.baseproject;

import com.e16din.baseproject.pattern.logic.Logic;
import com.e16din.requestmanager.Result;
import com.e16din.requestmanager.retrofit.RetrofitCallback;


public abstract class BaseOnCallListener<T extends Result> extends RetrofitCallback<T> {

    public abstract Logic logic();

    @Override
    public void onSuccess(T result, int statusCode) {
        logic().onSuccess(result, statusCode);
    }


    @Override
    public void afterResult(boolean withError) {
        logic().afterResult();
    }

    @Override
    public void onErrorFromServer(final Result result) {//no way, see onHttpError
        logic().onErrorFromServer(result);
    }

    @Override
    public boolean needCancel() {
        return logic().needCancel();
    }

    @Override
    public void onHttpError(final int statusCode, final String message, final String body) {
        logic().onHttpError(statusCode, message, body, null);
    }

    @Override
    public void onCancel() {
        logic().onCancel();
    }

    @Override
    public boolean ignoreExceptions() {
        return true;
    }
}