package com.fabassignment.dicos.model.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by benifabrice on 5/12/17.
 */

public abstract class RequestTypes {

    public static final int GET_RESULTS = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GET_RESULTS})
    public @interface Interface {
    }
}
