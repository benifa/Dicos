package com.fabassignment.dicos.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by benifabrice on 5/14/17.
 */

public class DefinitionComparatorType {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({THUMBS_UP, THUMBS_DOWN, RESET})
    public @interface Interface {
    }

    public static final int THUMBS_UP = 0;
    public static final int THUMBS_DOWN = 1;
    public static final int RESET = 2;
}
