package edu.whatcom.mywcc.models;

import android.os.Parcel;
import android.os.Parcelable;

public enum Weekday implements Parcelable {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static final Parcelable.Creator<Weekday> CREATOR = new Parcelable.Creator<Weekday>() {
        @Override
        public Weekday createFromParcel(Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public Weekday[] newArray(int size) {
            return new Weekday[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }
}
