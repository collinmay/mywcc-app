package edu.whatcom.mywcc.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AcademicQuarter implements Parcelable {
    public int year;
    public Season season;
    private Date startDate;
    private Date endDate;

    public AcademicQuarter(int year, Season season, Date startDate, Date endDate) {
        this.year = year;
        this.season = season;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AcademicQuarter(int year, Season season) {
        this(year, season, null, null);
    }

    @Override
    public int hashCode() {
        return year * Season.values().length + season.ordinal();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof AcademicQuarter) {
            AcademicQuarter qtr = (AcademicQuarter) other;
            return qtr.year == this.year && qtr.season == this.season;
        } else {
            return false;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(season.ordinal());
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
    }

    public static final Parcelable.Creator<AcademicQuarter> CREATOR
            = new Parcelable.Creator<AcademicQuarter>() {
        @Override
        public AcademicQuarter createFromParcel(Parcel source) {
            int year = source.readInt();
            Season season = Season.values()[source.readInt()];
            Date startDate = new Date(source.readLong());
            Date endDate = new Date(source.readLong());
            return new AcademicQuarter(year, season, startDate, endDate);
        }

        @Override
        public AcademicQuarter[] newArray(int size) {
            return new AcademicQuarter[size];
        }
    };
}
