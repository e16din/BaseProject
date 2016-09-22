package com.e16din.baseproject.pattern;


import android.os.Bundle;

public interface DataContainer<MODEL> {
    Bundle bundle();

    MODEL getData(String key);

    MODEL getData();
}
