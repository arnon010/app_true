package com.truedigital.vhealth.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.activity.ChatDetailActivity;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ChatMessageObject;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

/**
 * Created by nilecon on 10/25/2016 AD.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatMessageObject> chatMessageArrayList = new ArrayList<>();
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENDER = 1;

    private ChatMessageObject messageObject;

    private Activity activity;

    private int mPosition;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChatAdapter(Activity activity, ArrayList<ChatMessageObject> chatMessageArrayList) {
        this.activity = activity;
        this.chatMessageArrayList = chatMessageArrayList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_RECEIVED:
                View viewHeader = inflater.inflate(R.layout.list_item_chat_receiver, parent, false);
                viewHolder = new ViewHolderReceiver(viewHeader);
                break;
            case TYPE_SENDER:
                View viewBody = inflater.inflate(R.layout.list_item_chat_sender, parent, false);
                viewHolder = new ViewHolderSender(viewBody);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TYPE_RECEIVED:
                ViewHolderReceiver viewHolderReceiver = (ViewHolderReceiver) holder;
                configureViewHolderReceiver(viewHolderReceiver, position);
                break;
            case TYPE_SENDER:
                ViewHolderSender viewHolderSender = (ViewHolderSender) holder;
                configureViewHolderSender(viewHolderSender, position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolderReceiver(ViewHolderReceiver viewHolderReceiver, final int position) {
        ChatMessageObject messageObject = chatMessageArrayList.get(position);
        viewHolderReceiver.txtMessage.setText(messageObject.getMessageText());
        //viewHolderReceiver.txtTime.setText(ConvertDate.convertTimeToString(messageObject.getTimeMessage()));
        viewHolderReceiver.txtTime.setText(ConvertDate.StringDateServiceFormatFullTime(messageObject.getTimeMessage()));
        //viewHolderReceiver.imgProfile.setImageResource(R.drawable.img_iph_calendar_patient22x);
        //viewHolderReceiver.imgAttachFile.setImageResource(R.drawable.img_iph_chat_doctor2x);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        viewHolderReceiver.txtMessage.setMaxWidth((int) (width * .7));


        boolean isAttachFile = false;
        if (messageObject.getAttachmentUrl() != null && !messageObject.getAttachmentUrl().isEmpty()) {
            isAttachFile = true;
        }

        //boolean isAttachFile = messageObject.isAttachFile();
        if (isAttachFile) {
            viewHolderReceiver.imgAttachFile.setVisibility(View.VISIBLE);
            viewHolderReceiver.txtMessage.setVisibility(View.GONE);
            //viewHolderReceiver.imgAttachFile.setMaxWidth((int) (width * .5));
            //viewHolderReceiver.imgAttachFile.setMaxHeight((int) (dm.heightPixels * .5));
        }
        else {
            viewHolderReceiver.imgAttachFile.setVisibility(View.GONE);
            viewHolderReceiver.txtMessage.setVisibility(View.VISIBLE);
        }

        final String attachmentUrl = messageObject.getAttachmentUrl();
        viewHolderReceiver.imgAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ChatActivity.setSendFile(true);
                Intent intent = new Intent(activity, ChatDetailActivity.class);
                intent.putExtra("attachImage",attachmentUrl);
                activity.startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                Glide.with(activity)
                        .load(messageObject.getImageProfile()).asBitmap()
                        .placeholder(R.drawable.img_iph_defaultimg2x)
                        .into(viewHolderReceiver.imgProfile);

                if (isAttachFile) {
                    showAttachFile(messageObject, viewHolderReceiver);
                    /*
                    Glide.with(activity)
                            .load(messageObject.getAttachmentUrl())
                            .placeholder(R.drawable.img_iph_defaultimg2x)
                            //.centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(viewHolderReceiver.imgAttachFile);
                            */

                }

            }
        } else {
            Glide.with(activity)
                    .load(messageObject.getImageProfile()).asBitmap()
                    .placeholder(R.drawable.img_iph_defaultimg2x)
                    .into(viewHolderReceiver.imgProfile);


            if (isAttachFile) {
                showAttachFile(messageObject, viewHolderReceiver);
                /*
                Glide.with(activity)
                        .load(messageObject.getAttachmentUrl())
                        .placeholder(R.drawable.img_iph_defaultimg2x)
                        //.centerCrop()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(viewHolderReceiver.imgAttachFile);
                        */
            }

        }
    }

    private void configureViewHolderSender(final ViewHolderSender viewHolderSender, final int position) {
        ChatMessageObject messageObject = chatMessageArrayList.get(position);
        viewHolderSender.txtMessage.setText(messageObject.getMessageText());
        //viewHolderSender.txtTime.setText(ConvertDate.convertTimeToString(messageObject.getTimeMessage()));
        viewHolderSender.txtTime.setText(ConvertDate.StringDateServiceFormatFullTime(messageObject.getTimeMessage()));
        //viewHolderSender.imgProfile.setImageResource(R.drawable.img_iph_chat_doctor2x);
        //viewHolderSender.imgAttachFile.setImageResource(R.drawable.img_iph_chat_doctor2x);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        viewHolderSender.txtMessage.setMaxWidth((int) (width * .7));

        boolean isAttachFile = false;
        if (messageObject.getAttachmentUrl() != null && !messageObject.getAttachmentUrl().isEmpty()) {
            isAttachFile = true;
        }

        //boolean isAttachFile = messageObject.isAttachFile();
        if (isAttachFile) {
            viewHolderSender.imgAttachFile.setVisibility(View.VISIBLE);
            viewHolderSender.txtMessage.setVisibility(View.GONE);

            //viewHolderSender.imgAttachFile.setMaxWidth((int) (width * .5));
            //viewHolderSender.imgAttachFile.setMaxHeight((int) (dm.heightPixels * .5));
        }
        else {
            viewHolderSender.imgAttachFile.setVisibility(View.GONE);
            viewHolderSender.txtMessage.setVisibility(View.VISIBLE);
            viewHolderSender.txtTime.setVisibility(View.VISIBLE);
            viewHolderSender.imgRefresh.setVisibility(View.GONE);
            viewHolderSender.imgUploadFail.setVisibility(View.GONE);
            viewHolderSender.imgUploadFailbg.setVisibility(View.GONE);
            viewHolderSender.pgLoading.setVisibility(View.GONE);
            viewHolderSender.tvLoadingPercent.setVisibility(View.GONE);
        }

        final String attachmentUrl = messageObject.getAttachmentUrl();
        viewHolderSender.imgAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChatDetailActivity.class);
                intent.putExtra("attachImage",attachmentUrl);
                activity.startActivity(intent);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isDestroyed()) {
                Glide.with(activity)
                        .load(messageObject.getImageProfile()).asBitmap()
                        .placeholder(R.drawable.img_iph_defaultimg2x)
                        .into(viewHolderSender.imgProfile);

                if (isAttachFile) {
                    showAttachFile(messageObject, viewHolderSender);
                    /*
                    Glide.with(activity)
                            .load(messageObject.getAttachmentUrl())
                            .placeholder(R.drawable.img_iph_defaultimg2x)
                            //.centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(viewHolderSender.imgAttachFile);
                            */


                }
            }
        } else {
            Glide.with(activity)
                    .load(messageObject.getImageProfile()).asBitmap()
                    .placeholder(R.drawable.img_iph_defaultimg2x)
                    .into(viewHolderSender.imgProfile);

            if (isAttachFile) {
                showAttachFile(messageObject, viewHolderSender);
                /*
                Glide.with(activity)
                        .load(messageObject.getAttachmentUrl())
                        .placeholder(R.drawable.img_iph_defaultimg2x)
                        //.centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(viewHolderSender.imgAttachFile);
                        */

            }
        }

        viewHolderSender.imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(view,position);
            }
        });

        if (messageObject.isShowProgress()) {
            int percent = messageObject.getPercentLoading();
            viewHolderSender.pgLoading.setVisibility(View.VISIBLE);
            viewHolderSender.pgLoading.setProgress(percent);

            viewHolderSender.tvLoadingPercent.setVisibility(View.VISIBLE);
            viewHolderSender.tvLoadingPercent.setText("" + percent + " %");
        }
        else {
            viewHolderSender.pgLoading.setVisibility(View.GONE);
            viewHolderSender.tvLoadingPercent.setVisibility(View.GONE);
        }


    }

    private void showAttachFile(ChatMessageObject messageObject, ViewHolderReceiver viewHolder) {

        GlideUrl url = new GlideUrl(messageObject.getAttachmentUrl(), new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + AppManager.getDataManager().getAccess_token())
                .build());

        Glide.with(activity)
                .load(url).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .centerCrop()
                .override(180, 180)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.imgAttachFile);
        /*
        Glide.with(activity)
                .load(messageObject.getAttachmentUrl())
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .centerCrop()
                .override(180, 180)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new BitmapImageViewTarget(viewHolder.imgAttachFile) {
                    @Override
                    public void onResourceReady(Bitmap  drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }
                });
                */
    }

    private void showAttachFile(ChatMessageObject messageObject, final ViewHolderSender viewHolder) {
        /*
        Glide.with(activity)
                .load(messageObject.getAttachmentUrl())
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .centerCrop()
                .override(180, 180)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.imgAttachFile);
        */

        //..
        if (messageObject.isSending()) {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(activity)
                .load(messageObject.getAttachmentUrl()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .centerCrop()
                .override(180, 180)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.imgAttachFile);

            if (messageObject.isUploadFail()) {
                viewHolder.txtTime.setVisibility(View.GONE);
                //viewHolder.imgRefresh.setVisibility(View.VISIBLE);
                viewHolder.imgUploadFail.setVisibility(View.VISIBLE);
                viewHolder.imgUploadFailbg.setVisibility(View.VISIBLE);
                //viewHolder.imgAttachFile.setAlpha(0.9f);
            }
            else {
                viewHolder.txtTime.setVisibility(View.VISIBLE);
                //viewHolder.imgRefresh.setVisibility(View.GONE);
                viewHolder.imgUploadFail.setVisibility(View.GONE);
                viewHolder.imgUploadFailbg.setVisibility(View.GONE);
            }

        }
        else {
            GlideUrl url = new GlideUrl(messageObject.getAttachmentUrl(), new LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer " + AppManager.getDataManager().getAccess_token())
                    .build());
            Glide.with(activity)
                    .load(url).asBitmap()
                    .placeholder(R.drawable.img_iph_defaultimg2x)
                    .centerCrop()
                    .override(180, 180)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.imgAttachFile);

            viewHolder.imgUploadFail.setVisibility(View.GONE);
            viewHolder.imgUploadFailbg.setVisibility(View.GONE);
        }

        /*
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(activity)
                .load(messageObject.getAttachmentUrl()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .centerCrop()
                .override(180, 180)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.imgAttachFile);

            if (messageObject.isUploadFail()) {
                viewHolder.txtTime.setVisibility(View.GONE);
                //viewHolder.imgRefresh.setVisibility(View.VISIBLE);
                viewHolder.imgUploadFail.setVisibility(View.VISIBLE);
                viewHolder.imgUploadFailbg.setVisibility(View.VISIBLE);
                //viewHolder.imgAttachFile.setAlpha(0.9f);
            }
            else {
                viewHolder.txtTime.setVisibility(View.VISIBLE);
                //viewHolder.imgRefresh.setVisibility(View.GONE);
                viewHolder.imgUploadFail.setVisibility(View.GONE);
                viewHolder.imgUploadFailbg.setVisibility(View.GONE);
            }
            */
    }

    public ChatMessageObject getMessageObject(int position) {
        return chatMessageArrayList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessageArrayList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }

    public int getLastPosition() {
        return chatMessageArrayList.size() > 1 ?  chatMessageArrayList.size() - 1 : 0;
    }

    public class ViewHolderSender extends RecyclerView.ViewHolder {
        private final ProgressBar progressBar;
        private final ImageView imgRefresh;
        private final ProgressBar pgLoading;
        private final TextView tvLoadingPercent;


        private TextView txtMessage;
        private TextView txtTime;
        private final ImageView imgProfile;
        private final ImageView imgAttachFile;
        private final ImageView imgUploadFail;
        private final ImageView imgUploadFailbg;

        public ViewHolderSender(View v) {
            super(v);
            txtMessage = (TextView) v.findViewById(R.id.textviewMessage);
            txtTime = (TextView) v.findViewById(R.id.textviewTime);
            imgProfile = (ImageView) v.findViewById(R.id.imageviewIcon);
            imgAttachFile= (ImageView) v.findViewById(R.id.imageviewAttachImage);
            imgUploadFail = (ImageView) v.findViewById(R.id.imageUploadFail);
            imgUploadFailbg = (ImageView) v.findViewById(R.id.imageFailbg);
            imgRefresh = (ImageView) v.findViewById(R.id.imgRefresh);

            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            pgLoading = (ProgressBar) v.findViewById(R.id.pgLoading);
            tvLoadingPercent = (TextView) v.findViewById(R.id.tvLoadingPercent);
        }
    }

    public class ViewHolderReceiver extends RecyclerView.ViewHolder {
        private TextView txtMessage;
        private TextView txtTime;
        private ImageView imgProfile;
        private ImageView imgAttachFile;

        public ViewHolderReceiver(View v) {
            super(v);
            txtMessage = (TextView) v.findViewById(R.id.textviewMessage);
            txtTime = (TextView) v.findViewById(R.id.textviewTime);
            imgProfile = (ImageView) v.findViewById(R.id.imageviewIcon);
            imgAttachFile= (ImageView) v.findViewById(R.id.imageviewAttachImage);
        }
    }

    public void addMessage(ChatMessageObject message) {
        // add message in same box
//        if (chatMessageArrayList.size() != 0) {
//            ChatMessageObject messageBefore = chatMessageArrayList.get(chatMessageArrayList.size() - 1);
//            Date timeBefore = messageBefore.getTimeMessage();
//            Date timeAfter = message.getTimeMessage();
//            int typeBefore = messageBefore.getType();
//            int typeAfter = message.getType();
//
//            if (typeBefore == typeAfter) { // check send and receive type
//                if (timeBefore.equals(timeAfter)) { // check time
//                    String oldMessage = messageBefore.getMessageText();
//                    String newMessage = message.getMessageText();
//                    newMessage = oldMessage + "\n" + newMessage;
//                    chatMessageArrayList.get(chatMessageArrayList.size() - 1).setMessageText(newMessage);
//                } else {
//                    chatMessageArrayList.add(message);
//                }
//            } else {
//                chatMessageArrayList.add(message);
//            }
//        } else {
//            chatMessageArrayList.add(message);
//        }

        chatMessageArrayList.add(message);
        notifyDataSetChanged();
    }

    public void setChatMessageArrayList(ArrayList<ChatMessageObject> chatMessageArrayList) {
        this.chatMessageArrayList = chatMessageArrayList;
        notifyDataSetChanged();
    }

    public void showUploadSuccess(int position) {
        this.chatMessageArrayList.get(position).setUploadFail(false);
        notifyDataSetChanged();
    }

    public void showUploadFail(int position) {
        this.chatMessageArrayList.get(position).setUploadFail(true);
        notifyDataSetChanged();
    }

    public void showProgress(int position) {
        this.mPosition = position;
        this.chatMessageArrayList.get(position).setShowProgress(true);
        notifyDataSetChanged();
    }

    public void hideProgress(int position) {
        this.chatMessageArrayList.get(position).setShowProgress(false);
        notifyDataSetChanged();
    }

    public void showProgressLoading(int percent) {
        this.chatMessageArrayList.get( this.mPosition).setPercentLoading(percent);
        notifyDataSetChanged();
    }
}
