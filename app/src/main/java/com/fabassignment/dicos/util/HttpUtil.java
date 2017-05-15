package com.fabassignment.dicos.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.fabassignment.dicos.model.http.HttpRequestTypes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;

/**
 * Created by benifabrice on 5/12/17.
 */

public final class HttpUtil {
    private static final int BUFFER_SIZE = 1024;
    public static final String CONTENT_TYPE_HEADER = "Content-type";
    public static final String SPECIAL_ERROR = "Special error";
    private static final String NOCONNECTION = "Unable to establish a connection. Please check your network settings.";
    public static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse(APPLICATION_JSON_CONTENT_TYPE);

    private HttpUtil() {
        // hide constructor
    }

    public static boolean isSuccessStatus(int statusCode) {
        boolean isSuccess = false;
        switch (statusCode) {
            case HttpURLConnection.HTTP_OK:
            case HttpURLConnection.HTTP_CREATED:
            case HttpURLConnection.HTTP_ACCEPTED:
            case HttpURLConnection.HTTP_NO_CONTENT:
                isSuccess = true;
                break;
            default:
                break;
        }
        return isSuccess;
    }

    public static Request buildGetRequest(String url, Map<String, String> headerMap) {
        return buildBaseRequestBuilder(url, headerMap).build();
    }

    public static Request buildPostOrPutRequest(String url,
                                                Map<String, String> headerMap,
                                                String json,
                                                @HttpRequestTypes.Interface int reqType) {
        final Request.Builder builder = buildBaseRequestBuilder(url, headerMap);
        if (!TextUtils.isEmpty(json)) {
            if (reqType == HttpRequestTypes.POST_REQ) {
                builder.post(RequestBody.create(JSON_MEDIA_TYPE, json));
            } else {
                builder.put(RequestBody.create(JSON_MEDIA_TYPE, json));
            }
        }
        return builder.build();
    }


    public static Request.Builder buildBaseRequestBuilder(String url, Map<String, String> headerMap) {
        final Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (headerMap != null && !headerMap.isEmpty()) {
            builder.headers(Headers.of(headerMap));
        }
        return builder;
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager conectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = conectMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static String getResponseBodyAndClose(Response response) {
        String responseString = null;
        if (response != null) {
            try {
                final ResponseBody body = response.body();
                responseString = getStringFromInputStream(response.body().byteStream());
                body.close();
            } catch (Exception e) {
                Log.e(TAG, "Exception getting response body:", e);
                responseString = null;
            }
        }
        return responseString;
    }

    private static String getStringFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader br = null;
        final StringBuilder sb = new StringBuilder();
        int charsRead;
        final char[] buffer = new char[BUFFER_SIZE];
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((charsRead = br.read(buffer, 0, BUFFER_SIZE)) != -1) {
                sb.append(buffer, 0, charsRead);
            }
        } catch (EOFException eof) {
            //eof - no error in this case
            Log.e(TAG, "Encounters the end of a file or stream", eof);
        } catch (IOException e) {
            Log.e(TAG, "Exception reading from input stream reader", e);
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing input stream", e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing buffer reader", e);
                }
            }
        }
        return sb.toString();
    }

    public static void populateExceptionResponse(final com.fabassignment.dicos.model.Response response,
                                                 final String optionalMsg) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("errorCode", SPECIAL_ERROR);
            jsonObject.put("errorMessage", NOCONNECTION);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        response.setResponseBody(jsonObject.toString());
        response.setOptionalMessage(optionalMsg);
    }
}
