/*
 * Copyright Txus Ballesteros 2015 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.truedigital.vhealth.custom;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class BubbleLayout extends FrameLayout {
    private static final int TOUCH_TIME_THRESHOLD = 150;
    private float initialTouchX;
    private float initialTouchY;
    private float margin = 0.02f;
    private float marginXtoRight = 0.7f;
    private float marginYTop = 0.1f;
    private float marginYtoBottom = 0.40f; //0.62f;
    private int initialX;
    private int initialY;
    private int width;
    private int height;
    private long lastTouchDown;
    private RelativeLayout rootView;

    private OnBubbleClickListener onBubbleClickListener;
    private MoveAnimator animator;
    private WindowManager windowManager;

    private boolean shouldStickToWall = true;
    private RelativeLayout.LayoutParams params;

    public void setOnBubbleClickListener(OnBubbleClickListener listener) {
        onBubbleClickListener = listener;
    }

    public BubbleLayout(Context context) {
        super(context);
        animator = new MoveAnimator();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setLayoutParams(buildLayoutParamsForBubble());
        initializeView();
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        animator = new MoveAnimator();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setLayoutParams(buildLayoutParamsForBubble());
        initializeView();
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        animator = new MoveAnimator();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setLayoutParams(buildLayoutParamsForBubble());
        initializeView();
    }

    public void setRootView(RelativeLayout rootView) {
        this.rootView = rootView;
    }

    public void setLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        this.params = layoutParams;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return params;
    }

    private RelativeLayout.LayoutParams buildLayoutParamsForBubble() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) (getFrameWidth() * margin), (int) (getFrameHeight() * marginYtoBottom), 0, (int) (getFrameWidth() * margin));
        return layoutParams;
    }

    private void initializeView() {
        setClickable(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = params.leftMargin;
                    initialY = params.topMargin;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    lastTouchDown = System.currentTimeMillis();
                    updateSize();
                    animator.stop();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int x = initialX + (int) (event.getRawX() - initialTouchX);
                    int y = initialY + (int) (event.getRawY() - initialTouchY);
                    params.setMargins(x, y, 0, 0);
                    rootView.updateViewLayout(this, params);
                    break;
                case MotionEvent.ACTION_UP:
                    goToWall();
                    if (System.currentTimeMillis() - lastTouchDown < TOUCH_TIME_THRESHOLD) {
                        if (onBubbleClickListener != null) {
                            onBubbleClickListener.onBubbleClick(this);
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void updateSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = (size.x - this.getWidth());
        height = (size.y - this.getHeight());
    }

    public int getFrameWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getFrameHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public interface OnBubbleClickListener {
        void onBubbleClick(BubbleLayout bubble);
    }

    public void goToWall() {
        if (shouldStickToWall) {
            int middle = width / 2;
            int middleHeight = height / 2;
            int destinationLeft = 0;
            int destinationTop = 0;
            int destinationRight = 0;
            int destinationBottom = 0;

            if (params.leftMargin <= middle && params.topMargin <= middleHeight) {
                destinationLeft = (int) (getFrameWidth() * margin);
                destinationTop = (int) (getFrameHeight() * marginYTop);
                destinationRight = 0;
                destinationBottom = 0;
            } else if (params.leftMargin > middle && params.topMargin <= middleHeight) {
                destinationLeft = (int) (getFrameWidth() * marginXtoRight);
                destinationTop = (int) (getFrameHeight() * marginYTop);
                destinationRight = (int) (getFrameWidth() * margin);
                destinationBottom = 0;
            } else if (params.leftMargin <= middle && params.topMargin > middleHeight) {
                destinationLeft = (int) (getFrameWidth() * margin);
                destinationTop = (int) (getFrameHeight() * marginYtoBottom);
                destinationRight = 0;
                destinationBottom = (int) (getHeight() * margin);
            } else if (params.leftMargin > middle && params.topMargin > middleHeight) {
                destinationLeft = (int) (getFrameWidth() * marginXtoRight);
                destinationTop = (int) (getFrameHeight() * marginYtoBottom);
                destinationRight = (int) (getFrameWidth() * margin);
                destinationBottom = (int) (getHeight() * margin);
            }

            animator.start(destinationLeft, destinationTop, destinationRight, destinationBottom);
        }
    }

    private void move(int deltaX, int deltaY, int right, int bottom) {
        params.setMargins((deltaX), deltaY, right, bottom);
        rootView.updateViewLayout(this, params);
    }

    private class MoveAnimator implements Runnable {
        private Handler handler = new Handler(Looper.getMainLooper());
        private int destinationLeft;
        private int destinationTop;
        private int destinationRight;
        private int destinationBottom;
        private long startingTime;

        private void start(int left, int top, int right, int bottom) {
            this.destinationLeft = left;
            this.destinationTop = top;
            this.destinationRight = right;
            this.destinationBottom = bottom;

            startingTime = System.currentTimeMillis();
            handler.post(this);
        }

        @Override
        public void run() {
            if (getRootView() != null && getRootView().getParent() != null) {
                float progress = Math.min(1, (System.currentTimeMillis() - startingTime) / 400f);
                move(destinationLeft, destinationTop, destinationRight, destinationBottom);
                if (progress < 1) {
                    handler.post(this);
                }
            }
        }

        private void stop() {
            handler.removeCallbacks(this);
        }
    }
}
