package com.e16din.baseproject.pattern;


import android.os.Bundle;

import com.e16din.baseproject.pattern.logic.BaseProjectLogic;


public interface LogicContainer<MODEL> {
    /**
     * Call it on create logic, after view created
     */
    BaseProjectLogic<MODEL> onCreateLogic(Bundle savedInstanceState);

    /**
     * Call it after onCreateLogic()
     *
     * @param logic a created Logic
     */
    void onLogicCreated(BaseProjectLogic<MODEL> logic);

    /**
     * @return the Logic object
     */
    BaseProjectLogic<MODEL> logic();
}
