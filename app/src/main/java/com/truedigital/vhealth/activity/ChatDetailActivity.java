package com.truedigital.vhealth.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.truedigital.vhealth.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ChatDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        final String attachmentUrl = getIntent().getStringExtra("attachImage");
        final PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        final ImageView imgClose = (ImageView) findViewById(R.id.imageClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(attachmentUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView);
    }
}


