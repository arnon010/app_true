package com.truedigital.vhealth.api.interceptor;


import android.util.Log;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by songkrit on 1/25/2018 AD.
 */

public class HttpHeader implements Interceptor {

    private String token;
    private String acceptLanguage;
    private String timeZone;
    private String currentPage;

    private String getAcceptLanguage() {
        String lang = Locale.getDefault().getLanguage();
        Log.v("LANG", lang);
        if (lang.equals(AppConstants.LOCAL_LANG_THAI)){
            return "th-TH";
        }
        else {
            return "en-US";
        }
    }

    public HttpHeader(String token, String currentPage) {
        this.token = token;
        this.acceptLanguage = getAcceptLanguage();  //"th-TH"; //"en-US" // text search 400ms
        this.timeZone = CommonUtils.getTimeZone();  // "+7"
        this.currentPage = currentPage;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Authorization", "Bearer " + this.token)
                .addHeader("Accept-Language", this.acceptLanguage)
                .addHeader("Timezone", this.timeZone)
                .addHeader("Current-Program", this.currentPage != null ? this.currentPage : "")
                .addHeader("ehr_token", AppManager.getDataManager().getEhrToken())
                .build();

        Response response = chain.proceed(request);
        return response;
    }

}
