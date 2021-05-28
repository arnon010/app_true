package com.truedigital.vhealth.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truedigital.vhealth.R;

public class ViewConsultItem extends RelativeLayout {

    private TextView tvName;
    private ImageView ivImage;
    private View overlayView;

    public ViewConsultItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ViewConsultItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public ViewConsultItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_article, this);
    }

    private void initInstances() {
        tvName = (TextView) findViewById(R.id.tvName);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        overlayView =(View) findViewById(R.id.overlayView);

        overlayView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(),"View Click", Toast.LENGTH_SHORT).show();
            }
        });

        ivImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(),"Image Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ivImage.getLayoutParams().height = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
