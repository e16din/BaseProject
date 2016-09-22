package com.e16din.baseproject.pattern.logic;


import android.support.annotation.Nullable;

import com.e16din.baseproject.pattern.DataContainer;

import java.io.Serializable;


public class DataLogic<MODEL> extends BaseLogic implements Serializable {

    @Nullable
    public MODEL getData(DataContainer d) {
        return isEmpty(d) ? null : (MODEL) d.bundle().get(d.bundle().keySet().iterator().next());
    }

    @Nullable
    public MODEL getData(DataContainer d, String key) {
        return isEmpty(d) ? null : (MODEL) d.bundle().get(key);
    }

    private boolean isEmpty(DataContainer d) {
        return d.bundle() == null || d.bundle().size() == 0;
    }
}
