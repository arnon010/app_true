package com.truedigital.vhealth.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by songkrit on 12/25/2017 AD.
 */

public class FileUtils {

    public static String getRealPathFromURI(Activity activity, Uri contentURI) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static int fileZize(File file) {
        int file_size = Integer.parseInt(String.valueOf(file.length() / 1024 / 1024));
        return file_size;
    }

    public static long fileZize(Activity activity, Uri uri) {
        long file_size = 0;

        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(OpenableColumns.SIZE);
            if (index >= 0) {
                file_size = cursor.getLong(index);
            }
        }

        return file_size;
    }

    public static File fileUpload(Activity activity, Uri uri, boolean isCompress) {
        File file = new File(getRealPathFromURI(activity, uri));

        if (isCompress) {
            File fileReduce = reduceImageSize(file);
            if (fileReduce != null) {
                file = fileReduce;
            }
        }

        return file;
    }

    public static File reduceImageSize(File file) {

        try {
            //.. if size < 1 mb no resize
            if (fileZize(file) < 1) {
                return null;
            }

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 7;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkFileExists(Context context, Uri fileUri) {
        File file = new File(fileUri.getPath());
        if (file.exists()) {
            return true;
        }

        ContentResolver cr = context.getContentResolver();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cur = cr.query(fileUri, projection, null, null, null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                String filePath = cur.getString(0);

                if (new File(filePath).exists()) {
                    return true;
                } else {
                    return false;
                }
            }
            cur.close();
        }

        return false;
    }

    public static String getFileExtension(Uri uri) {
        String reslt = uri.getPath().substring(uri.getPath().lastIndexOf("."));
        return reslt;
    }

    public static String getFileExtension(String path) {
        String reslt = path.substring(path.lastIndexOf("."));
        return reslt;
    }

    public static String getFileNameWithoutExtension(Uri uri) {
        String reslt = uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1, uri.getPath().lastIndexOf("."));
        return reslt;
    }

    public static String getFileNameWithoutExtension(String path) {
        String reslt = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        return reslt;
    }

    public static String getPathWithoutFileName(Uri uri) {
        String reslt = uri.getPath().substring(0, uri.getPath().lastIndexOf("/") + 1);
        return reslt;
    }

    public static String getPathWithoutFileName(String path) {
        String reslt = path.substring(0, path.lastIndexOf("/") + 1);
        return reslt;
    }




}
