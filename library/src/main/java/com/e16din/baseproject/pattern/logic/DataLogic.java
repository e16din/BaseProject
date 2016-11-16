package com.e16din.baseproject.pattern.logic;


import android.support.annotation.Nullable;

import com.e16din.baseproject.pattern.DataContainer;
import com.e16din.datamanager.DataManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DataLogic<MODEL> extends BaseLogic {

    public void saveData(DataContainer owner, Serializable data) {
        DataManager.save(owner.getClass().getSimpleName() + "_data", data);
    }

    @Nullable
    public MODEL getData(DataContainer d) {
        if (isEmpty(d)) {
            return getSavedData(d);
        }

        return (MODEL) d.bundle().get(d.bundle().keySet().iterator().next());
    }

    @Nullable
    public MODEL getData(DataContainer d, String key) {
        if (isEmpty(d)) {
            return getSavedData(d);
        }

        return (MODEL) d.bundle().get(key);
    }

    @Nullable
    protected MODEL getSavedData(DataContainer d) {
        List<MODEL> typedList = new ArrayList<>();

        Type genericSuperclass = typedList.getClass().getGenericSuperclass();
        Type modelType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];

        MODEL savedData = DataManager.load(d.getClass().getSimpleName() + "_data", modelType);
        return savedData == null ? null : savedData;
    }

    private boolean isEmpty(DataContainer d) {
        return d.bundle() == null || d.bundle().size() == 0;
    }
}
