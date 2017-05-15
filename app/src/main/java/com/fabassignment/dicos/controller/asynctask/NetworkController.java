package com.fabassignment.dicos.controller.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.fabassignment.dicos.listener.OnTaskCompletedListener;
import com.fabassignment.dicos.model.http.HttpRequestTypes;
import com.fabassignment.dicos.model.http.RequestTypes;
import com.fabassignment.dicos.model.Response;
import com.fabassignment.dicos.util.HttpUtil;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;


/**
 * Created by benifabrice on 5/12/17.
 */

public class NetworkController extends AsyncTask<String, Void, Response> {
    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_SOCKET_TIMEOUT = 60;
    private static final int BUFFER_SIZE = 1024;
    private static String TAG = NetworkController.class.getName();
    private static OkHttpClient mOkHttpClient = null;
    private
    @HttpRequestTypes.Interface
    int mRequestType = HttpRequestTypes.GET_REQ;
    private WeakReference<Context> mContext = new WeakReference<Context>(null);
    private String mRequestUrl;
    private boolean mProgressDialogDisabled;
    private OnTaskCompletedListener listener;
    private Call call;
    private
    @RequestTypes.Interface
    int reqType;
    private String contentType;
    private Map<String, String> mRequestHeaderParams;
    private Map<String, String> mRequestBodyParams;
    private Map<String, String> requestBodyParams;


    public NetworkController(Context context, OnTaskCompletedListener listener) {
        this.mContext = new WeakReference<>(context);
        this.listener = listener;
        this.call = null;
    }


    public static synchronized OkHttpClient getHttpClient() {
        if (mOkHttpClient != null) {
            return mOkHttpClient;
        }
        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.readTimeout(DEFAULT_SOCKET_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(DEFAULT_SOCKET_TIMEOUT, TimeUnit.SECONDS);
        final List<Protocol> protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_1_1);
        httpBuilder.protocols(protocols);
        httpBuilder.followRedirects(false);
        return httpBuilder.build();
    }

    public void disableProgressDialog(boolean disabled) {
        this.mProgressDialogDisabled = disabled;
    }

    @Override
    protected Response doInBackground(String... strings) {
        final Response response = new Response();
        try {
            okhttp3.Response httpResponse = null;
            Request httpRequest = null;
            response.setRequestType(reqType);
            response.setStatusCode(-1);
            final OkHttpClient client = getHttpClient();

            mRequestUrl = mRequestUrl.replaceAll(" ", "%20");
            Log.d(TAG, "URL : " + mRequestUrl);

            switch (mRequestType) {
                case HttpRequestTypes.GET_REQ:
                    Log.d(TAG, "HTTP RequestType: GET");
                    httpRequest = getHttpGet(mRequestUrl);
                    break;
                case HttpRequestTypes.POST_REQ:
                    Log.d(TAG, "HTTP RequestType: POST");
                    httpRequest = getHttpPostOrPut(mRequestUrl);
                    break;
                case HttpRequestTypes.PUT_REQ:
                    Log.d(TAG, "HTTP RequestType: PUT");
                    httpRequest = getHttpPostOrPut(mRequestUrl);
                    break;
                case HttpRequestTypes.DELETE_REQ:
                    Log.d(TAG, "HTTP RequestType: DELETE");
                    httpRequest = getHttpDelete(mRequestUrl);
                    break;
            }


            call = client.newCall(httpRequest);
            httpResponse = call.execute();

            if (httpResponse != null) {
                final int status = httpResponse.code();
                final String responseString = HttpUtil.getResponseBodyAndClose(httpResponse);
                Log.d(TAG, "Response : " + responseString);
                Log.d(TAG, "Status Code: " + status);

                response.setRequestType(reqType);
                response.setStatusCode(status);
                response.setResponseBody(responseString);
                response.setOptionalMessage(httpResponse.message());
                response.setHeaders(httpResponse.headers().toMultimap());
            } else {
                HttpUtil.populateExceptionResponse(response, null);
            }
        } catch (Exception e) {
            HttpUtil.populateExceptionResponse(response, e.getMessage());
        }
        return response;
    }

    private Request getHttpDelete(String reqUrl) {
        return HttpUtil.buildBaseRequestBuilder(reqUrl, null).delete().build();
    }

    public String getContentType() {
        if (contentType != null) {
            return contentType;
        }
        // default value
        return HttpUtil.APPLICATION_JSON_CONTENT_TYPE;
    }

    /**
     * Perform a HTTP GET request, without any parameters.
     *
     * @param reqUrl the URL to send the request to.
     */
    private Request getHttpGet(String reqUrl) {
        final Map<String, String> headerMap = new ArrayMap<>();
        headerMap.put(HttpUtil.CONTENT_TYPE_HEADER, getContentType());
        addExtraNeededHeaders(headerMap);
        return HttpUtil.buildGetRequest(reqUrl, headerMap);
    }

    /**
     * Perform a HTTP POST request, without any parameters.
     *
     * @param reqUrl the URL to send the request to.
     */
    private Request getHttpPostOrPut(String reqUrl) {
        Request request = null;
        try {
            final Map<String, String> headerMap = new ArrayMap<>();
            headerMap.put(HttpUtil.CONTENT_TYPE_HEADER, getContentType());
            addExtraNeededHeaders(headerMap);

            String json = null;
            if (mRequestBodyParams != null) {
                json = new JSONObject(mRequestBodyParams).toString();
            }
            Log.d(TAG, "HTTP POST request params :" + json);
            request = HttpUtil.buildPostOrPutRequest(reqUrl, headerMap, json, mRequestType);


            return request;
        } catch (Exception e) {
            Log.e(TAG, "Exception building post request:", e);
            e.printStackTrace();
        }
        return null;
    }


    public
    @RequestTypes.Interface
    int getReqType() {
        return reqType;
    }

    public void setReqType(@RequestTypes.Interface int reqType) {
        this.reqType = reqType;
    }

    public void setHttpReqMethodType(@RequestTypes.Interface int httppReqMethodType) {
        this.mRequestType = httppReqMethodType;
    }

    public String getUrl() {
        return mRequestUrl;
    }

    public void setUrl(String url) {
        this.mRequestUrl = url;
    }


    public void setParameters(Map<String, String> parameters) {
        this.mRequestBodyParams = parameters;
    }

    private void addExtraNeededHeaders(Map<String, String> headerMap) {
        if (!mRequestHeaderParams.isEmpty()) {
            headerMap.putAll(mRequestHeaderParams);
        }
    }

    public void setRequestHeaderParams(Map<String, String> mRequestHeaderParams) {
        this.mRequestHeaderParams = mRequestHeaderParams;
    }


    public void setRequestBodyParams(Map<String, String> requestBodyParams) {
        this.requestBodyParams = requestBodyParams;
    }
}
