package com.truedigital.vhealth.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by songkrit on 5/24/2018 AD.
 */

public class ImageUtils {

    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }


    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }
    }

    public static Builder init(Context context) {
        return new Builder(context);
    }

    public static void show(Context context,ImageView imageView,String imageUrl) {
        Glide.with(context)
                .load(imageUrl).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(imageView);
    }

    public static void show(Context context,ImageView imageView,int resId) {
        Glide.with(context)
                .load(resId).asBitmap()
                .error(resId)
                .into(imageView);
    }

    public static Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
