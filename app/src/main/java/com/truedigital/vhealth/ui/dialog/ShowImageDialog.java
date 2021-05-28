package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.URLUtil;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.GlideConfig;
import com.jsibbold.zoomage.ZoomageView;

public class ShowImageDialog extends Dialog {

    private Context context;
    private ZoomageView zvImage;

    private String imageUrl;

    public ShowImageDialog(@NonNull Context context, String imageUrl) {
        super(context);
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_show_image, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view) {
        zvImage = (ZoomageView) view.findViewById(R.id.zvImage);

        if (URLUtil.isNetworkUrl(imageUrl)) {
            Glide.with(context)
                    .load(GlideConfig.getGlideHeader(imageUrl)).asBitmap()
                    .error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default)
                    .into(zvImage);
        } else {
            Glide.with(context)
                    .load(imageUrl).asBitmap()
                    .error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default)
                    .into(zvImage);
        }
    }



}