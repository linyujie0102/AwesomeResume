package com.jiuzhang.guojing.awesomeresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jiuzhang.guojing.awesomeresume.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by linyujie on 6/26/17.
 */

public class Experience implements Parcelable {


    public String id;
    public String company;
    public String title;
    public Date startDate;
    public Date endDate;
    public List<String> details;

    public Experience() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(company);
        dest.writeString(title);
        dest.writeString(DateUtils.dateToString(startDate));
        dest.writeString(DateUtils.dateToString(endDate));
        dest.writeStringList(details);
    }



    protected Experience(Parcel in) {
        id = in.readString();
        company = in.readString();
        title = in.readString();
        startDate = DateUtils.stringToDate(in.readString());
        endDate = DateUtils.stringToDate(in.readString());
        details = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Experience> CREATOR = new Parcelable.Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel source) {
            return new Experience(source);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };
}
