package com.e16din.baseproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.e16din.lightutils.utils.U;


public abstract class BaseProjectApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        init(this);
    }

    public abstract boolean isDebug();

    public void init(Context context) {
        BaseProject.init(context, isDebug());
    }

    @NonNull
    public String getUserAgent(String agent) {
        return userAgent(agent);
    }

    @NonNull
    public static String userAgent(String agent) {
        return agent + " "
                + BuildConfig.VERSION_NAME + " "
                + U.getDeviceName() + " "
                + "Android(" + U.getAndroidVersionNumbers() + ")";
    }

//todo: move to independent module
//    public void onRequest(@Nullable final BaseProjectLogic logic, final boolean withProgress) {
//        if (logic == null) return;
//
//        if (withProgress) {
//            logic.showProgress();
//        }
//
//        if (!U.isOnline()) {
//            logic.hideProgress();
//
//            AlertManager.manager(logic.getActivity())
//                    .showErrorAlert(U.getString(R.string.base_no_internet_connection));
//        }
//    }
}
