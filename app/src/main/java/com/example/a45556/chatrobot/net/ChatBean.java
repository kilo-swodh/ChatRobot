package com.example.a45556.chatrobot.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 45556 on 2017-2-22.
 */

public class ChatBean implements Parcelable{
    public static final int TYPE_SEND = 1;
    public static final int TYPE_RECEIVER = 2;
    private int flag;
    private int code;
    private String text;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flag);
        dest.writeInt(code);
        dest.writeString(text);
        dest.writeString(time);
    }

    public static final Parcelable.Creator<ChatBean> CREATOR = new Parcelable.Creator<ChatBean>(){
        @Override
        public ChatBean createFromParcel(Parcel source) {
            ChatBean bean = new ChatBean();
            bean.flag = source.readInt();
            bean.code = source.readInt();
            bean.text = source.readString();
            bean.time = source.readString();
            return bean;
        }

        @Override
        public ChatBean[] newArray(int size) {
            return new ChatBean[size];
        }
    };
}
