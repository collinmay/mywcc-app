package edu.whatcom.mywcc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Email implements Parcelable {
    public static final Parcelable.Creator<Email> CREATOR
            = new Parcelable.Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
