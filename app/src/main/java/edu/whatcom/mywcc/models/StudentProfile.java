package edu.whatcom.mywcc.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentProfile implements Parcelable {
    public int orcaCardFreePrints = 1480;
    public int orcaCardDiningDollars = 430;
    public int orcaCardBookstore = 1000000;
    public int orcaCardBonusBucks = 1337;

    public List<CanvasAssignment> canvasAssignments = new ArrayList<>();
    public List<Email> recentEmails = new ArrayList<>();
    public Map<AcademicQuarter, List<Course>> quarterlyEnrollments = new HashMap<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orcaCardFreePrints);
        dest.writeInt(orcaCardDiningDollars);
        dest.writeInt(orcaCardBookstore);
        dest.writeInt(orcaCardBonusBucks);

        dest.writeTypedList(canvasAssignments);
        dest.writeTypedList(recentEmails);

        dest.writeInt(quarterlyEnrollments.size());
        for(Map.Entry<AcademicQuarter, List<Course>> e : quarterlyEnrollments.entrySet()) {
            dest.writeParcelable(e.getKey(), 0);
            dest.writeTypedList(e.getValue());
        }
    }

    public static final Parcelable.Creator<StudentProfile> CREATOR
            = new Parcelable.Creator<StudentProfile>() {
        @Override
        public StudentProfile createFromParcel(Parcel source) {
            StudentProfile p = new StudentProfile();
            p.orcaCardFreePrints = source.readInt();
            p.orcaCardDiningDollars = source.readInt();
            p.orcaCardBookstore = source.readInt();
            p.orcaCardBonusBucks = source.readInt();

            source.readTypedList(p.canvasAssignments, CanvasAssignment.CREATOR);
            source.readTypedList(p.recentEmails, Email.CREATOR);

            int numQuarters = source.readInt();
            for(int i = 0; i < numQuarters; i++) {
                AcademicQuarter qtr = AcademicQuarter.CREATOR.createFromParcel(source);
                List<Course> courses = new ArrayList<>();
                source.readTypedList(courses, Course.createCreator(qtr));
                p.quarterlyEnrollments.put(qtr, courses);
            }

            return p;
        }

        @Override
        public StudentProfile[] newArray(int size) {
            return new StudentProfile[size];
        }
    };
}
