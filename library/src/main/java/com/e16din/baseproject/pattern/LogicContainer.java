package com.e16din.baseproject.pattern;


import android.os.Bundle;

import com.e16din.baseproject.pattern.logic.Logic;


public interface LogicContainer<MODEL> {
    /**
     * Call it on create logic, after view created
     */
    Logic<MODEL> onCreateLogic(Bundle savedInstanceState);

    /**
     * Call it after onCreateLogic()
     *
     * @param logic a created Logic
     */
    void onLogicCreated(Logic<MODEL> logic);

    /**
     * @return the Logic object
     */
    Logic<MODEL> logic();
}
