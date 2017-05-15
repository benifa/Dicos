package com.fabassignment.dicos.controller.asynctask;

import android.content.Context;
import android.util.Log;

import com.fabassignment.dicos.listener.OnTaskCompletedListener;
import com.fabassignment.dicos.model.http.HttpRequestTypes;
import com.fabassignment.dicos.model.http.RequestTypes;
import com.fabassignment.dicos.model.Response;
import com.fabassignment.dicos.util.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by benifabrice on 5/12/17.
 */

public class BaseController implements OnTaskCompletedListener {
    private static final String TAG = BaseController.class.getSimpleName();
    private WeakReference<Context> mContext = new WeakReference<Context>(null);
    @Override
    public void onTaskCompleted(Response response) {

    }

    protected Context getContext(){
        if (mContext == null) {
            throw new NullPointerException("BaseController is null");
        }
        return mContext.get();
    }

    public void setContext(final Context context) {
        this.mContext = new WeakReference<Context>(context);
    }

    protected void execute(@RequestTypes.Interface int reqType,
                           @HttpRequestTypes.Interface int hhtpReqType, final String url,
                           Map<String, String> headerParams, Map<String, String> bodyParams,
                           boolean disableProgressDialog) {
        final Context context = getContext();
        if (context != null && HttpUtil.isNetworkAvailable(context)) {
            final NetworkController controller = new NetworkController(context, this);
            controller.disableProgressDialog(disableProgressDialog);
            controller.setReqType(reqType);
            controller.setHttpReqMethodType(hhtpReqType);
            controller.setUrl(url);

            if (headerParams != null) {
                controller.setRequestHeaderParams(headerParams);
            }

            if (bodyParams != null) {
                controller.setRequestBodyParams(headerParams);
            }

            controller.execute();
        } else {
            Log.e(TAG, "Not executing network call because either context is null or network is unavailable");
        }

    }
}
