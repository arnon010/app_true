package com.truedigital.vhealth.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.truedigital.vhealth.R;

public final class PickFile {

    public static void pickImageIntentWithPermission(Activity activity, Fragment fg, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {

            boolean isPermission = false;
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_PICK_IMAGE_IMAGE_WRITE_EXTERNAL_STORAGE);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_PICK_IMAGE_IMAGE_READ_EXTERNAL_STORAGE);
                isPermission = true;
            }

            if (!isPermission) {
                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* video/*");
                fg.startActivityForResult(android.content.Intent.createChooser(intent, activity.getResources().getString(R.string.select_file_from)), requestCode);
            }

        } else {
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/* video/*");
            fg.startActivityForResult(android.content.Intent.createChooser(intent, activity.getResources().getString(R.string.select_file_from)), requestCode);
        }
    }

    public static void pickImageIntentWithPermission(Activity activity,int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {

            boolean isPermission = false;
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_PICK_IMAGE_IMAGE_WRITE_EXTERNAL_STORAGE);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_PICK_IMAGE_IMAGE_READ_EXTERNAL_STORAGE);
                isPermission = true;
            }

            if (!isPermission) {
                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/* video/*");
                activity.startActivityForResult(android.content.Intent.createChooser(intent, activity.getResources().getString(R.string.select_file_from)), requestCode);
            }

        } else {
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/* video/*");
            activity.startActivityForResult(android.content.Intent.createChooser(intent, activity.getResources().getString(R.string.select_file_from)), requestCode);
        }
    }

}
