package edu.whatcom.mywcc.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class CanvasAssignment implements Parcelable {
    public String className;
    public String assignmentName;
    public Date dueDate;

    public CanvasAssignment(String className, String assignmentName, Date dueDate) {
        this.className = className;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(className);
        dest.writeString(assignmentName);
        dest.writeLong(dueDate.getTime());
    }

    public static final Parcelable.Creator<CanvasAssignment> CREATOR
            = new Parcelable.Creator<CanvasAssignment>() {
        @Override
        public CanvasAssignment createFromParcel(Parcel source) {
            String className = source.readString();
            String assignmentName = source.readString();
            Date dueDate = new Date(source.readLong());
            return new CanvasAssignment(className, assignmentName, dueDate);
        }

        @Override
        public CanvasAssignment[] newArray(int size) {
            return new CanvasAssignment[size];
        }
    };
}
