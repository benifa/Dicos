package com.fabassignment.dicos.retrofit;

import android.support.annotation.NonNull;

import com.fabassignment.dicos.Dicos;
import com.fabassignment.dicos.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by benifabrice on 5/13/17.
 */

public final class ApiClient {
    private static Map<String, Retrofit> instances;

    //Lets keep retrofit clients as singletons and save in  a thread safe map

    static {
        instances = new ConcurrentHashMap<>();
    }

    public static Retrofit getInstance(final String baseUrl) {
        return getInstanceForBase(baseUrl);
    }

    @NonNull
    private static Retrofit getInstanceForBase(final String url) {
        Retrofit retrofit = instances.get(url);
        if (retrofit == null) {
            retrofit = createInstance(url);
        }
        return retrofit;
    }

    private static Retrofit createInstance(String url) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //Logging
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
//
        //Caching
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(Dicos.getInstance().getApplicationContext().getCacheDir(), "http"), SIZE_OF_CACHE);
        httpClient.cache(cache);
        httpClient.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);
        httpClient.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                //only use 5000s old cache
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!HttpUtil.isNetworkAvailable(Dicos.getInstance().getApplicationContext())) {
                //use cache if network is not available
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };
}
