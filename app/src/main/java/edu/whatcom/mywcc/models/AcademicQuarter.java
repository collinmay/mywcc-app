package edu.whatcom.mywcc.models;

import java.util.Date;

public class AcademicQuarter {
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
}
