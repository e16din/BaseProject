package com.e16din.baseproject.pattern;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

public interface ViewInterface<MODEL> {

    Context getContext();

    @LayoutRes
    int getLayoutId();

    /**
     * The view created, start your views here.
     */
    void onInit(View v);

    /**
     * Bind the data to views.
     */
    void onBind(MODEL data);
}
