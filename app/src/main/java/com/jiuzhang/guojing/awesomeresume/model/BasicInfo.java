package com.jiuzhang.guojing.awesomeresume.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class BasicInfo implements Parcelable {
    public String name;

    public String email;

    public Uri imageUri;

    public BasicInfo() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeParcelable(this.imageUri, flags);
    }

    protected BasicInfo(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Parcelable.Creator<BasicInfo> CREATOR = new Parcelable.Creator<BasicInfo>() {
        @Override
        public BasicInfo createFromParcel(Parcel source) {
            return new BasicInfo(source);
        }

        @Override
        public BasicInfo[] newArray(int size) {
            return new BasicInfo[size];
        }
    };
}
