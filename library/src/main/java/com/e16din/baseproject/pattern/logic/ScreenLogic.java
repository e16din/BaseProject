package com.e16din.baseproject.pattern.logic;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.e16din.baseproject.R;
import com.e16din.baseproject.screens.activity.BaseProjectActivity;
import com.e16din.lightutils.utils.U;

import java.io.Serializable;

/**
 * Contains common logic for all screens (activities and fragments)
 */
public class ScreenLogic<MODEL> extends DataLogic<MODEL> {

    public static final int PROGRESS_HIDE_COUNT = 3;

    
    private Toolbar vToolbar;

    private boolean mCanceled;

    private BaseProjectActivity<MODEL> mActivity;

    private MaterialDialog mLoadingDialog;

    private int mOnBackPressedCounter = 0;

    private Bundle mSavedInstanceStateBundle;

    ///

    public ScreenLogic(BaseProjectActivity<MODEL> activity, Bundle savedInstanceState) {
        mActivity = activity;
        mSavedInstanceStateBundle = savedInstanceState;
    }

    public void onActivityResult(BaseProjectActivity<MODEL> activity) {
        start(activity);
    }

    protected void start(BaseProjectActivity<MODEL> activity) {
        mActivity = activity;
        setCanceled(false);
    }

    public void onStart(BaseProjectActivity<MODEL> activity) {
        start(activity);
    }

    public void onStop() {
        saveDataOnStop();

        mActivity = null;
        setCanceled(true);
    }

    protected void saveDataOnStop() {
        MODEL data = getData(getActivity());
        if (data instanceof Serializable) {
            saveData(getActivity(), (Serializable) data);
        } else {
            //todo: save parcelable
        }
    }

    /// inflate

    public View inflate(@LayoutRes int layoutId, @Nullable ViewGroup vParent) {
        return getActivity().getLayoutInflater().inflate(layoutId, vParent);
    }

    public View inflate(@LayoutRes int layoutId, @Nullable ViewGroup vParent, boolean attachToRoot) {
        return getActivity().getLayoutInflater().inflate(layoutId, vParent, attachToRoot);
    }

    public void inflate(int menuRes, Menu menu) {
        getActivity().getMenuInflater().inflate(menuRes, menu);
    }

    /// get

    public BaseProjectActivity<MODEL> getActivity() {
        return mActivity;
    }

    public Toolbar getToolbar() {
        return vToolbar;
    }

    public void setToolbar(Toolbar view) {
        vToolbar = view;
    }

    /// progress dialog

    public void hideProgress() {
        if (mLoadingDialog != null) {
            U.tryThis(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.dismiss();
                }
            });

            setLoadingDialog(null);
        }
    }

    public void showProgress() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        }
        U.tryThis(new Runnable() {
            @Override
            public void run() {
                MaterialDialog dialog = new MaterialDialog.Builder(mActivity)
                        .content(R.string.base_loading)
                        .progress(true, 0)
                        .widgetColorRes(R.color.baseProgressColor)
                        .cancelable(false)
                        .show();

                setLoadingDialog(dialog);
                mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                                if (mOnBackPressedCounter < PROGRESS_HIDE_COUNT) {
                                    mOnBackPressedCounter += 1;
                                } else {
                                    mOnBackPressedCounter = 0;
                                    hideProgress();
                                }
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    /// keyboard

    public void showKeyboard(@NonNull final EditText editText) {
        U.tryThis(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();

                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public void hideKeyboard() {
        U.tryThis(new Runnable() {
            @Override
            public void run() {
                View view = mActivity.getCurrentFocus();

                if (view != null) {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    /// set

    public void setCanceled(boolean canceled) {
        mCanceled = canceled;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public void setLoadingDialog(MaterialDialog loadingDialog) {
        mLoadingDialog = loadingDialog;
    }

    public Bundle getSavedInstanceStateBundle() {
        return mSavedInstanceStateBundle;
    }
}
