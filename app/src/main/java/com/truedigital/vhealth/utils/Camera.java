package com.truedigital.vhealth.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.truedigital.vhealth.BuildConfig;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Camera {

    private static final String imageFileName = "Camera_Temp.jpg";

    public static void takePhotosIntentWithPermission(Activity activity, Fragment fg, int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {
            boolean isPermission = false;
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CAMERA_READ_WRITE_EXTERNAL_STORAGE);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_CAMERA_READ_READ_EXTERNAL_STORAGE);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.CAMERA}, AppConstants.REQUEST_CAMERA);
                isPermission = true;
            }

            if (!isPermission) {
                startActivity(activity, fg, requestCode);
            }

        } else {
            startActivity(activity, fg, requestCode);
        }
    }

    public static void takePhotosIntentWithPermission(Activity activity, int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {
            boolean isPermission = false;
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CAMERA_READ_WRITE_EXTERNAL_STORAGE);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_CAMERA_READ_READ_EXTERNAL_STORAGE);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, AppConstants.REQUEST_CAMERA);
                isPermission = true;
            }

            if (!isPermission) {
                startActivity(activity, requestCode);
            }

        } else {
            startActivity(activity, requestCode);
        }
    }

    // https://developer.android.com/training/camera/photobasics
    private static void startActivity(Activity activity, Fragment fg, int requestCode) {
        android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {

            File photoFile = createImageFile(activity);

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, BuildConfig.FILE_PROVIDER, photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fg.startActivityForResult(intent, requestCode);
            }
        }
    }

    private static void startActivity(Activity activity, int requestCode) {
        android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {

            File photoFile = createImageFile(activity);

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, BuildConfig.FILE_PROVIDER, photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(intent, requestCode);
            }
        }
    }

    private static File createImageFile(Context context) {
        File storageDir = getDirectory(context, Environment.DIRECTORY_PICTURES);
        Uri tempUri = Uri.parse(String.format("%s/%s", storageDir.getAbsolutePath(), imageFileName));

        File tempFile = new File(tempUri.getPath());
        if (FileUtils.checkFileExists(context, tempUri)) {
            tempFile.delete();
        }
        tempFile = new File(storageDir, imageFileName);

        return tempFile;
    }

    public static Uri moveFile(Context context) {
        File currentDir = getDirectory(context, Environment.DIRECTORY_PICTURES);
        Uri tempUri = Uri.parse(String.format("%s/%s", currentDir.getAbsolutePath(), imageFileName));
        File currentFile = new File(tempUri.getPath());

        File storageDir = getDirectory(context, Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Uri uri = Uri.parse(String.format("%s/%s", storageDir.getAbsolutePath(), timeStamp + ".jpg"));
        File file = new File(uri.getPath());

        currentFile.renameTo(file);

        Uri contentUri = getImageContentUri(context, file);

        return contentUri;
    }

    private static File getDirectory(Context context, String type) {
        return context.getExternalFilesDir(type);
    }

    // https://stackoverflow.com/questions/7305504/convert-file-uri-to-content-uri
    private static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);

        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);

                Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures");
                    values.put(MediaStore.Images.Media.IS_PENDING, 1);
                    collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                }

                ContentResolver contentResolver = context.getContentResolver();
                Uri galleryFileUri = context.getContentResolver().insert(collection, values);
                if (galleryFileUri != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    try {
                        OutputStream outputStream = contentResolver.openOutputStream(galleryFileUri);
                        FileInputStream fileInputStream = new FileInputStream(imageFile);
                        IOUtils.copy(fileInputStream, outputStream);

                        ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(galleryFileUri, "rw");
                        new ExifInterface(parcelFileDescriptor.getFileDescriptor()).saveAttributes();
                    } catch (IOException e) {
                        Log.e("getImageContentUri", "error => " + e.getMessage());
                    }

                    values.clear();
                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
                    contentResolver.update(galleryFileUri, values, null, null);
                }

                return galleryFileUri;
            } else {
                return null;
            }
        }
    }

}
