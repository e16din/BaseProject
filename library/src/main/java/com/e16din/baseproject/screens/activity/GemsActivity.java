package com.e16din.baseproject.screens.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class GemsActivity<MODEL> extends BaseProjectActivity<MODEL> {

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

    @Override
    public void onActivityResult(@Nullable MODEL data) {
        super.onActivityResult(data);
        for (Gem<MODEL> gem : mGems) {
            gem.onActivityResult(data);
        }
    }

    @Override
    public void onActivityResult(Intent data) {
        super.onActivityResult(data);
        for (Gem<MODEL> gem : mGems) {
            gem.onActivityResult(data);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Gem<MODEL> gem : mGems) {
            gem.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (Gem<MODEL> gem : mGems) {
            gem.onCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for (Gem<MODEL> gem : mGems) {
            gem.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    // todo: add other methods

    //

    public interface Gem<MODEL> {

        void onInit(View v);

        void onBind(MODEL data);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onActivityResult(@Nullable MODEL data);

        void onActivityResult(Intent data);

        void onCreateOptionsMenu(Menu menu);

        void onOptionsItemSelected(MenuItem item);

        // todo: add other methods
    }

    public abstract class SimpleGem<MODEL> implements Gem<MODEL> {
        @Override
        public void onInit(View v) {
        }

        @Override
        public void onBind(MODEL data) {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        }

        @Override
        public void onActivityResult(@Nullable MODEL data) {
        }

        @Override
        public void onActivityResult(Intent data) {
        }

        @Override
        public void onCreateOptionsMenu(Menu menu) {
        }

        @Override
        public void onOptionsItemSelected(MenuItem item) {
        }

        // todo: add other methods
    }
}
