package com.fabassignment.dicos.model.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by benifabrice on 5/12/17.
 */

public class HttpRequestTypes {
    public static final int GET_REQ = 0;
    public static final int POST_REQ = 1;
    public static final int PATCH_REQ = 2;
    public static final int PUT_REQ = 3;
    public static final int DELETE_REQ = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GET_REQ, POST_REQ, PATCH_REQ, PUT_REQ, DELETE_REQ})
    public @interface Interface {
    }
}
