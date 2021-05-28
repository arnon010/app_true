package com.truedigital.vhealth.utils;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.truedigital.vhealth.manager.AppManager;

public class GlideConfig {

    public static GlideUrl getGlideHeader(String url) {
        if (url.equals("") || url == null) {
            return null;
        }
        String access_token = AppManager.getDataManager().getAccess_token();
        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + access_token)
                .build());

        return glideUrl;
    }

}
