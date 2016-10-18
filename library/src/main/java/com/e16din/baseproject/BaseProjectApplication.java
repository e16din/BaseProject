package com.e16din.baseproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.e16din.alertmanager.AlertManager;
import com.e16din.baseproject.pattern.logic.BaseProjectLogic;
import com.e16din.datamanager.DataManager;
import com.e16din.intentmaster.IntentMaster;
import com.e16din.lightutils.LightUtils;
import com.e16din.lightutils.utils.U;


public abstract class BaseProjectApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        init(this);
    }

    public abstract boolean isDebug();

    public void init(Context context) {
        LightUtils.init(context, isDebug());
        DataManager.init(context);
        IntentMaster.setNeedIgnoreExceptions(true);
    }

    @NonNull
    public String getUserAgent(String agent) {
        return agent + " "
                + BuildConfig.VERSION_NAME + " "
                + U.getDeviceName() + " "
                + "Android(" + U.getAndroidVersionNumbers() + ")";
    }

    public void onRequest(@Nullable final BaseProjectLogic logic, final boolean withProgress) {
        if (logic == null) return;

        if (withProgress) {
            logic.showProgress();
        }

        if (!U.isOnline()) {
            logic.hideProgress();

            AlertManager.manager(logic.getActivity())
                    .showErrorAlert(U.getString(R.string.base_no_internet_connection));
        }
    }
}
