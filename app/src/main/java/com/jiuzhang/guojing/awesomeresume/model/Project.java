package com.jiuzhang.guojing.awesomeresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jiuzhang.guojing.awesomeresume.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by linyujie on 7/3/17.
 */

public class Project implements Parcelable {

    public String id;

    public String name;

    public Date startDate;

    public Date endDate;

    public List<String> details;

    public Project() {
        id = UUID.randomUUID().toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(DateUtils.dateToString(startDate));
        dest.writeString(DateUtils.dateToString(endDate));
        dest.writeStringList(details);
    }



    protected Project(Parcel in) {
        id = in.readString();
        name = in.readString();
        startDate = DateUtils.stringToDate(in.readString());
        endDate = DateUtils.stringToDate(in.readString());
        details = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
