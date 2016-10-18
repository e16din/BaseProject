package com.e16din.baseproject.screens.dialog;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class GemsDialogFragment<MODEL> extends BaseProjectDialogFragment<MODEL> {

    private List<Gem<MODEL>> mGems = new ArrayList<>();

    public void addGem(Gem<MODEL> gem) {
        mGems.add(gem);
    }

    public void removeGem(Gem<MODEL> gem) {
        mGems.remove(gem);
    }

    public void clearGems() {
        mGems.clear();
    }

    public List<Gem<MODEL>> getGems() {
        return mGems;
    }

    // interface methods

    @Override
    public void onInit(View v) {
        super.onInit(v);
        for (Gem<MODEL> gem : mGems) {
            gem.onInit(v);
        }
    }

    @Override
    public void onBind(MODEL data) {
        super.onBind(data);
        for (Gem<MODEL> gem : mGems) {
            gem.onBind(data);
        }
    }

    // todo: add other methods

    //

    public interface Gem<MODEL> {

        void onInit(View v);

        void onBind(MODEL data);

        // todo: add other methods
    }

    public abstract class SimpleGem<MODEL> implements Gem<MODEL> {
        @Override
        public void onInit(View v) {
        }

        @Override
        public void onBind(MODEL data) {
        }

        // todo: add other methods
    }
}
