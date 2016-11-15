package com.e16din.baseproject;

import android.content.Context;

import com.e16din.datamanager.DataManager;
import com.e16din.intentmaster.IntentMaster;
import com.e16din.lightutils.LightUtils;

public final class BaseProject {

    private BaseProject() {
    }

    public static void init(Context context, boolean isDebug) {
        LightUtils.init(context, isDebug);
        DataManager.init(context);
        IntentMaster.setNeedIgnoreExceptions(true);
    }
}
