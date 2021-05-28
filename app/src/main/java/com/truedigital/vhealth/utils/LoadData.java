package com.truedigital.vhealth.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by songkrit on 5/12/2018 AD.
 */

public class LoadData {
    private Context context;

    public LoadData(Context context) {
        this.context = context;
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
