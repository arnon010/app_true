package com.truedigital.vhealth.utils;

import android.view.View;
import android.widget.ImageView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemDoctorContactTypeDao;

import java.util.List;

/**
 * Created by songkrit on 8/23/2018 AD.
 */

public class ContactTypeUtil {
    public final static String CHANNEL_CHAT = "CH";
    public final static String CHANNEL_VOICE = "VO";
    public final static String CHANNEL_VIDEO = "VI";

    public final static String CHANNEL_CHAT_FULL = "Chat";
    public final static String CHANNEL_VOICE_FULL = "Voice";
    public final static String CHANNEL_VIDEO_FULL = "Video";

    private List<ItemDoctorContactTypeDao> listContactType;
    private boolean isChat;
    private boolean isVoice;
    private boolean isVideo;

    public ContactTypeUtil() {
    }

    public ContactTypeUtil(List<ItemDoctorContactTypeDao> listContactType) {
        this.listContactType = listContactType;
    }

    public void getContactType() {
        if (listContactType != null) {
            for (ItemDoctorContactTypeDao conTactType : listContactType) {
                if (conTactType.getTypeCode().equals(CHANNEL_CHAT))
                    isChat = true;
                if (conTactType.getTypeCode().equals(CHANNEL_VOICE))
                    isVoice = true;
                if (conTactType.getTypeCode().equals(CHANNEL_VIDEO))
                    isVideo = true;
            }
        }
    }

    private void hide(ImageView imgChat,ImageView imgVoice,ImageView imgVideo) {
        imgChat.setVisibility(View.GONE);
        imgVoice.setVisibility(View.GONE);
        imgVideo.setVisibility(View.GONE);
    }

    public void show(ImageView imgChat,ImageView imgVoice,ImageView imgVideo) {
        getContactType();
        imgChat.setVisibility( isChat() ? View.VISIBLE : View.GONE);
        imgVoice.setVisibility( isVoice() ? View.VISIBLE : View.GONE);
        imgVideo.setVisibility( isVideo() ? View.VISIBLE : View.GONE);
    }


    public boolean isChat() {
        return isChat;
    }

    public boolean isVoice() {
        return isVoice;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public int getIcon(String contactTypeCode) {
        if (isChat(contactTypeCode)) return R.drawable.ic_chat_green;
        if (isVoice(contactTypeCode)) return R.drawable.ic_voice_green;
        if (isVideo(contactTypeCode)) return R.drawable.ic_video_green;
        return R.drawable.ic_video_green;
    }

    public String getTypeName(String contactTypeCode) {
        if (isChat(contactTypeCode)) return AppConstants.CONTACT_CHAT;
        if (isVoice(contactTypeCode)) return AppConstants.CONTACT_VOICE;
        if (isVideo(contactTypeCode)) return AppConstants.CONTACT_VIDEO;
        return "";
    }

    public static boolean isChat(String contactTypeCode) {
        return contactTypeCode.equalsIgnoreCase(CHANNEL_CHAT) || contactTypeCode.equalsIgnoreCase(CHANNEL_CHAT_FULL);
    }
    public static boolean isVoice(String contactTypeCode) {
        return contactTypeCode.equalsIgnoreCase(CHANNEL_VOICE) || contactTypeCode.equalsIgnoreCase(CHANNEL_VOICE_FULL);
    }
    public static boolean isVideo(String contactTypeCode) {
        return contactTypeCode.equalsIgnoreCase(CHANNEL_VIDEO) || contactTypeCode.equalsIgnoreCase(CHANNEL_VIDEO_FULL);
    }
}
