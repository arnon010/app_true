package com.truedigital.vhealth.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import com.truedigital.vhealth.manager.AppManager;

import java.util.HashMap;

public class DownloadVideoThumbnailAsync extends AsyncTask<String, Void, Bitmap> {

    private ImageView bmImage;
    private int imageError;

    public DownloadVideoThumbnailAsync(ImageView bmImage, int imagePlaceholder, int imageError) {
        this.bmImage = (ImageView) bmImage;
        this.bmImage.setImageResource(imagePlaceholder);
        this.imageError = imageError;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap myBitmap = null;
        MediaMetadataRetriever mMRetriever = null;

        try {
            mMRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14) {
                String access_token = AppManager.getDataManager().getAccess_token();
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + access_token);

                mMRetriever.setDataSource(urls[0], headers);
            } else {
                mMRetriever.setDataSource(urls[0]);
            }

            myBitmap = mMRetriever.getFrameAtTime(1000);
        } catch (Exception e) {

            return null;
        } finally {

            if (mMRetriever != null) {
                mMRetriever.release();
            } else {
                return null;
            }
        }
        return myBitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if (result == null) {
            this.bmImage.setImageResource(imageError);
        } else {
            bmImage.setImageBitmap(result);
        }

        this.cancel(true);
    }


}