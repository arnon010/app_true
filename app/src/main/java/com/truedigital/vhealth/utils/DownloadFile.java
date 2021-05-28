package com.truedigital.vhealth.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.truedigital.vhealth.manager.AppManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public final class DownloadFile {

    private boolean noDeleteFile;

    //https://stackoverflow.com/questions/30106405/download-file-from-a-webserver-into-android-external-storage
    //require check permission to write
    public static long download(Context context, String url, String fileNameWithExt) {
        File env = getDirectory(context, Environment.DIRECTORY_DOWNLOADS);
        String path = env.getPath();
        String fileNameFull = fileNameWithExt;

        if (fileNameWithExt.lastIndexOf('/') > -1) {
            fileNameFull = fileNameWithExt.substring(fileNameWithExt.lastIndexOf('/') + 1, fileNameWithExt.length());
        }

        if (fileNameFull.lastIndexOf('.') > -1) {
            String fileName = fileNameFull.substring(0, fileNameFull.lastIndexOf('.'));
            String fileExt = fileNameFull.substring(fileNameFull.lastIndexOf('.'), fileNameFull.length());
        }

        String access_token = AppManager.getDataManager().getAccess_token();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.addRequestHeader("Authorization", "Bearer " + access_token);
        request.setDescription("Download file from Chiiwii LIVE");
        request.setTitle(fileNameFull);
        request.setDestinationInExternalFilesDir(context, path, fileNameFull);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileNameFull);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(request);

        return downloadId;
    }

    private static File getDirectory(Context context, String type) {
        return context.getExternalFilesDir(type);
    }

    public static void cancelDownload(Context context, long idFile, String dstPath) {
        try {

            // move file to dstPath
            if (dstPath != null && !dstPath.equals("")) {
                Uri uri = getDownloadUri(context, idFile);
                File fileIn = new File(uri.getPath());
                File fileOut = new File(dstPath);
                if (fileOut.exists()) {
                    fileOut.delete();
                }
                FileChannel inChannel = new FileInputStream(fileIn).getChannel();
                FileChannel outChannel = new FileOutputStream(fileOut).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                fileIn.delete();

                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            }

            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.remove(idFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getProgressPercentage(Context context, long idFile) {

        int fileByteDownloaded = 0, fileByteTotal = 0, percentage = 0;

        try {
            final DownloadManager mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(idFile);
            Cursor c = mDownloadManager.query(query);

            if (c.moveToFirst()) {
                fileByteDownloaded = (int) c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                fileByteTotal = (int) c.getLong(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            }

            percentage = (fileByteDownloaded * 100 / fileByteTotal);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return percentage;
    }

    public static int getDownloadStatus(Context context, long idFile) {

        try {
            final DownloadManager mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(idFile);
            Cursor c = mDownloadManager.query(query);

            int status = DownloadManager.STATUS_FAILED;
            if (c.moveToFirst()) {
                status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
            c.close();
            return status;

        } catch (Exception e) {
            e.printStackTrace();
            return DownloadManager.STATUS_FAILED;
        }
    }

    public static Uri getDownloadUri(Context context, long idFile) {

        try {
            final DownloadManager mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(idFile);
            Cursor c = mDownloadManager.query(query);

            Uri uri = null;
            if (c.moveToFirst()) {
                String path = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                uri = Uri.parse(path);
            }

            c.close();

            return uri;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}