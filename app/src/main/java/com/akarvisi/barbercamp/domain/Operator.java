package com.akarvisi.barbercamp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hizry on 4/1/2018.
 */

public class Operator implements Parcelable
{
    private final String name;
    public  Operator(String initialName){
        name = initialName;
    }

    public String getInitialName(){
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Operator> CREATOR = new Parcelable.Creator<Operator>() {
        public Operator createFromParcel(Parcel in) {
            return new Operator(in);
        }

        public Operator[] newArray(int size) {
            return new Operator[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Operator(Parcel in) {
        name = in.readString();
    }
}
