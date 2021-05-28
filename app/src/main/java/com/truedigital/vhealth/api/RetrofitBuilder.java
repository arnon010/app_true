package com.truedigital.vhealth.api;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.api.interceptor.HttpHeader;
import com.truedigital.vhealth.api.interceptor.HttpLogger;
import com.truedigital.vhealth.api.otp.OtpService;
import com.truedigital.vhealth.api.pin.PinService;
import com.truedigital.vhealth.api.services.AppointmentServiceApi;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.truedigital.vhealth.utils.FileUtils.getRealPathFromURI;

/**
 * Created by songkrit on 12/25/2017 AD.
 */

public class RetrofitBuilder {
    private static String currentPage = null;

    public static void setCurrentPage(String name) {
        if (name != null && !name.equals("") && name.contains(".")) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        currentPage = name;
    }

    interface InternetConnectionListener {
        void onInternetLost();
    }

    public static OtpService getOtpService(String accessToken) {
        int timeout = 30;
        OkHttpClient client = getClient(accessToken, timeout);
        return getRetrofit(client, BuildConfig.SERVER_AUTHEN).create(OtpService.class);
    }

    public static PinService getPinService(String accessToken) {
        int timeout = 30;
        OkHttpClient client = getClient(accessToken, timeout);
        return getRetrofit(client, BuildConfig.SERVER_BASE).create(PinService.class);
    }

    public static RetrofitService getRetrofitService() {
        int timeout = 15;
        OkHttpClient client = getClient("", timeout);
        return getRetrofit(client, BuildConfig.SERVER_BASE).create(RetrofitService.class);
    }

    public static RetrofitService getRetrofitServiceAuthen() {
        int timeout = 15;
        OkHttpClient client = getClient("", timeout);
        return getRetrofit(client, BuildConfig.SERVER_AUTHEN).create(RetrofitService.class);
    }

    public static RetrofitService getRetrofitServiceOneSignal() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_ONE_SIGNAL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitService.class);
    }

    public static RetrofitService getRetrofitToken(final String access_token) {
        int timeout = 30;
        OkHttpClient client = getClient(access_token, timeout);
        return getRetrofit(client, BuildConfig.SERVER_BASE).create(RetrofitService.class);
    }

    public static RetrofitService getRetrofitToken(final String access_token, int timeout) {
        OkHttpClient client = getClient(access_token, timeout);
        return getRetrofit(client, BuildConfig.SERVER_BASE).create(RetrofitService.class);
    }

    public static AppointmentServiceApi getAppointmentService(final String access_token) {
        int timeout = 60 * 3;
        OkHttpClient client = getClient(access_token, timeout);
        return getRetrofit(client, BuildConfig.SERVER_BASE).create(AppointmentServiceApi.class);
    }

    private static Retrofit getRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    private static OkHttpClient getClient(final String accessToken, int timeout) {
        return new OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(new HttpHeader(accessToken, currentPage))
                .addNetworkInterceptor(getDefaultHttpLogging())
                .build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static HttpLoggingInterceptor getDefaultHttpLogging() {
        if (BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor(new HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
    }

    public static MultipartBody.Part addMultipartBody(Activity activity, String name, Uri uri) {
        if (uri == null) return null;

        MultipartBody.Part body;
        File file = null;

        MediaType mediaType = MediaType.parse(activity.getContentResolver().getType(uri));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            try {
                AssetFileDescriptor parcelFileDescriptor = activity.getContentResolver().openAssetFileDescriptor(uri, "r");
                FileInputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                String fileName = getFileName(activity.getContentResolver(), uri);
                file = new File(activity.getCacheDir(), fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                IOUtils.copy(inputStream, outputStream);
            } catch (Exception e) {
                Timber.e("addMultipartBody error => %s", e.getMessage());
                file = new File(getRealPathFromURI(activity, uri));
            }
        } else {
            file = new File(getRealPathFromURI(activity, uri));
        }

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(mediaType, file);

        // MultipartBody.Part is used to send also the actual file name
        body = MultipartBody.Part.createFormData(name, file.getName(), requestFile);

        return body;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getFileName(ContentResolver contentResolver, Uri fileUri) {
        String name = "";
        Cursor cursor = contentResolver.query(fileUri, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            name = cursor.getString(nameIndex);
            cursor.close();
        }

        return name;
    }

    public static RequestBody addRequestBody(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }
}
