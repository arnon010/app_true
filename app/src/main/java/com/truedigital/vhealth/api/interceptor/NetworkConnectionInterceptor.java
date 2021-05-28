package com.truedigital.vhealth.api.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract class NetworkConnectionInterceptor implements Interceptor {
    public abstract boolean isInternetAvailable();
    public abstract void onInternetLost();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isInternetAvailable()) {
            onInternetLost();
        }
        return chain.proceed(request);
    }
}
