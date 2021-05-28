package com.truedigital.vhealth.ui.adapter;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ImageObject;
import com.truedigital.vhealth.utils.DownloadFile;
import com.truedigital.vhealth.utils.DownloadVideoThumbnailAsync;
import com.truedigital.vhealth.utils.FileUtils;
import com.truedigital.vhealth.utils.GlideConfig;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.File;
import java.util.ArrayList;

public class ListImageAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ItemType {
        ImageItem,
        AddItem
    }

    private int width;
    private int height;

    private ArrayList<T> dataArrayList;

    private Context context;
    private Fragment fg;
    private ListImageAdapter.OnClickListener<T> onClickListener;

    public ListImageAdapter(Context context, Fragment fg, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.fg = fg;
    }

    public void setListData(ArrayList<T> dataArrayList) {
        this.dataArrayList = dataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ImageObject item = (ImageObject) dataArrayList.get(position);
        if ((item.getImageUrl() != "" && item.getImageUrl() != null) || position < getItemCount() - 1) {
            return ItemType.ImageItem.ordinal();
        } else {
            return ItemType.AddItem.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType.ImageItem.ordinal()) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, parent, false);
            return new ListImageAdapter.MenuViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_add, parent, false);
            return new ListImageAdapter.AddViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        if (holder.getItemViewType() == ItemType.ImageItem.ordinal()) {
            this.initLayoutImage((ListImageAdapter.MenuViewHolder) holder, position);
        } else {
            this.initLayoutImageAdd((ListImageAdapter.AddViewHolder) holder, position);
        }
    }

    private void initLayoutImage(final ListImageAdapter.MenuViewHolder holder, final int position) {
        ImageObject data = (ImageObject) dataArrayList.get(position);

        ViewGroup.LayoutParams lp = holder.linImg.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setWidth(width);
            flexboxLp.setHeight(height);
        } else if (lp instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linLp = (LinearLayout.LayoutParams) lp;
            linLp.width = width;
            linLp.height = height;
        } else if (lp instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams linLp = (RelativeLayout.LayoutParams) lp;
            linLp.width = width;
            linLp.height = height;
        }

        if (!data.getIsVideo()) {
            if (data.getIsNew()) {
                Glide.with(context)
                        .load(data.getImageUrl()).asBitmap()
                        .error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default)
                        .into(holder.img);
            } else {
                Glide.with(context)
                        .load(GlideConfig.getGlideHeader(data.getImageUrl())).asBitmap()
                        .error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default)
                        .into(holder.img);
            }

        } else {

            // check file exists
            boolean fileExists = false;
            Uri uriDownload = null;
            if (data.getFileName() != null && !data.getFileName().equals("")) {
                File pathDownload = getDirectory(context, Environment.DIRECTORY_DOWNLOADS);
                String fileNameComplete = String.format("%s%s%s", FileUtils.getFileNameWithoutExtension(data.getFileName()), "-c", FileUtils.getFileExtension(data.getFileName()));
                uriDownload = Uri.withAppendedPath(Uri.fromFile(pathDownload), fileNameComplete);
                fileExists = FileUtils.checkFileExists(context, uriDownload);
            }

            holder.imgIc.setVisibility(View.VISIBLE);

            if (data.getIsNew() || fileExists) {

                if (!data.getIsNew() && fileExists && !data.getIsDownload() && !data.getIsDownloadComplete()) {
                    data.setIsDownload(true);
                    data.setIsDownloadComplete(true);
                    data.setImageUrlTemp(data.getImageUrl());
                    data.setImageUrl(uriDownload.getPath());
                }

                holder.img.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
                Glide.with(context)
                        .load(data.getImageUrl()).asBitmap()
                        .error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default)
                        .into(holder.img);

                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_play)
                        .into(holder.imgIc);
            } else {

                new DownloadVideoThumbnailAsync(holder.img, R.drawable.img_default, R.drawable.img_default).execute(data.getImageUrl());
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_download)
                        .into(holder.imgIc);
            }
        }

        holder.linImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageObject data = (ImageObject) dataArrayList.get(position);

                if (data.getIsVideo()) {
                    final String url = data.getImageUrl();
                    if ((data.getIsNew() || (data.getIsDownload() == true && data.getIsDownloadComplete() == true))) {

                        if (FileUtils.checkFileExists(context, Uri.parse(url))) {
                            // play
                            Intent intent = new Intent();
                            intent.setAction(android.content.Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(url), "video/*");
                            context.startActivity(intent);

                        } else if (!data.getIsNew()) {

                            // can play but file not exists do new download file
                            holder.img.setVisibility(View.GONE);
                            Glide.with(context)
                                    .load("")
                                    .placeholder(R.drawable.ic_download)
                                    .into(holder.imgIc);
                            data.setIsDownload(false);
                            data.setIsDownloadComplete(false);
                            data.setImageUrl(data.getImageUrlTemp());
                            data.setImageUrlTemp("");
                            downloadFileThread(holder, data, data.getImageUrl());
                        }

                    } else if (data.getIsDownload() == false) {

                        downloadFileThread(holder, data, url);
                    }
                } else {

                    T result = dataArrayList.get(position);
                    onClickListener.onViewClick(result);
                }
            }
        });

        holder.linImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                T data = dataArrayList.get(position);
                onClickListener.onDelete(data);
                return true;
            }
        });
    }

    private File getDirectory(Context context, String type) {
        return context.getExternalFilesDir(type);
    }

    private void downloadFileThread(final ListImageAdapter.MenuViewHolder holder, final ImageObject data, final String url) {
        if (Build.VERSION.SDK_INT >= 23) {

            boolean isPermission = false;
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 9999);
                isPermission = true;
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                fg.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9999);
                isPermission = true;
            }

            if (isPermission) {
                return;
            }
        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                data.setIsDownload(true);
                final long idFile = DownloadFile.download(context, url, data.getFileName());
                Handler handler = new Handler(Looper.getMainLooper());
                int status = DownloadFile.getDownloadStatus(context, idFile);

                while (data.getPercentage() <= 100) {

                    if (status != DownloadManager.STATUS_SUCCESSFUL && status != DownloadManager.STATUS_FAILED) {
                        data.setPercentage(DownloadFile.getProgressPercentage(context, idFile));
                        handler.post(new Runnable() {
                            public void run() {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                holder.progressBar.setProgress(data.getPercentage());
                            }
                        });

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            Uri uri = DownloadFile.getDownloadUri(context, idFile);
                            data.setImageUrlTemp(data.getImageUrl());

                            String pathComplete = String.format("%s%s%s%s", FileUtils.getPathWithoutFileName(uri), FileUtils.getFileNameWithoutExtension(uri), "-c", FileUtils.getFileExtension(uri));

                            data.setImageUrl(pathComplete);
                            data.setIsDownloadComplete(true);
                            DownloadFile.cancelDownload(context, idFile, data.getImageUrl());
                        } else {
                            DownloadFile.cancelDownload(context, idFile, null);
                        }

                        handler.post(new Runnable() {
                            public void run() {
                                holder.progressBar.setVisibility(View.INVISIBLE);

                                if (data.getIsDownloadComplete() == true) {

                                    holder.img.setVisibility(View.VISIBLE);
                                    Glide.with(context)
                                            .load(data.getImageUrl()).asBitmap()
                                            .error(R.drawable.img_default)
                                            .placeholder(R.drawable.img_default)
                                            .into(holder.img);

                                    Glide.with(context)
                                            .load("")
                                            .placeholder(R.drawable.ic_play)
                                            .into(holder.imgIc);
                                }
                            }
                        });

                        //Thread.currentThread().interrupt();
                        break;
                    }

                    status = DownloadFile.getDownloadStatus(context, idFile);
                }
            }
        });
        thread.start();
    }

    private void initLayoutImageAdd(ListImageAdapter.AddViewHolder holder, final int position) {
        ImageObject data = (ImageObject) dataArrayList.get(position);

        ViewGroup.LayoutParams lp = holder.linImgAdd.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setWidth(width);
            flexboxLp.setHeight(height);
        } else if (lp instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linLp = (LinearLayout.LayoutParams) lp;
            linLp.width = width;
            linLp.height = height;
        }

        holder.linImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onAddClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public interface OnClickListener<T> {
        void onAddClick();

        void onViewClick(T data);

        void onDelete(T data);
    }

    public void setOnClickListener(ListImageAdapter.OnClickListener<T> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView imgIc;
        RelativeLayout linImg;
        ProgressBar progressBar;

        public MenuViewHolder(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.img);
            imgIc = (ImageView) v.findViewById(R.id.imgIc);
            linImg = (RelativeLayout) v.findViewById(R.id.linImg);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public class AddViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAdd;
        LinearLayout linImgAdd;

        public AddViewHolder(View v) {
            super(v);
            imgAdd = (ImageView) v.findViewById(R.id.imgAdd);
            linImgAdd = (LinearLayout) v.findViewById(R.id.linImgAdd);
        }
    }
}
