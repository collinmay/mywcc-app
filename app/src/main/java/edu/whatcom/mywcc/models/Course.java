package edu.whatcom.mywcc.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class Course implements Parcelable {
    public AcademicQuarter quarter;
    public String courseId;
    public String title;
    public String section;
    public int item;
    public float units = 5.0f;
    public String instructor;

    public List<Schedule> schedule;
    public Date startDate;
    public Date endDate;

    public static class Schedule implements Parcelable {
        public Set<Weekday> days;
        public Room room;
        public int startHour;
        public int startMinute;
        public int endHour;
        public int endMinute;

        public Schedule(Set<Weekday> days, Room room, int startHour, int startMinute, int endHour, int endMinute) {
            this.days = days;
            this.room = room;
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
        }

        protected Schedule(Parcel in) {
            List<Weekday> days = new ArrayList<>();
            in.readTypedList(days, Weekday.CREATOR);

            this.days = new TreeSet<>(days);
            this.room = Building.getById(in.readString()).getRoom(in.readString());
            startHour = in.readInt();
            startMinute = in.readInt();
            endHour = in.readInt();
            endMinute = in.readInt();
        }

        public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
            @Override
            public Schedule createFromParcel(Parcel in) {
                return new Schedule(in);
            }

            @Override
            public Schedule[] newArray(int size) {
                return new Schedule[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(new ArrayList<>(days));
            dest.writeString(room.building.getId());
            dest.writeString(room.number);
            dest.writeInt(startHour);
            dest.writeInt(startMinute);
            dest.writeInt(endHour);
            dest.writeInt(endMinute);
        }
    }

    public static class Builder {
        private Course course;

        public Builder(AcademicQuarter quarter) {
            course = new Course();
            course.quarter = quarter;
            course.schedule = new ArrayList<>();
        }

        public Builder setCourseId(String courseId, String section) {
            course.courseId = courseId;
            course.section = section;
            return this;
        }

        public Builder setTitle(String title) {
            course.title = title;
            return this;
        }

        public Builder setItem(int item) {
            course.item = item;
            return this;
        }

        public Builder setUnits(float units) {
            course.units = units;
            return this;
        }

        public Builder setInstructor(String instructor) {
            course.instructor = instructor;
            return this;
        }

        public Builder addSchedule(Schedule sched) {
            course.schedule.add(sched);
            return this;
        }

        public Builder addSchedule(String weekdays, Room room, int startHour, int startMinute, int endHour, int endMinute) {
            Set<Weekday> days = new TreeSet<>();
            if(weekdays.contains("M")) { days.add(Weekday.MONDAY); }
            if(weekdays.contains("T")) { days.add(Weekday.TUESDAY); }
            if(weekdays.contains("W")) { days.add(Weekday.WEDNESDAY); }
            if(weekdays.contains("Th")) { days.add(Weekday.THURSDAY); }
            if(weekdays.contains("F")) { days.add(Weekday.FRIDAY); }
            if(weekdays.contains("Sa")) { days.add(Weekday.SATURDAY); }
            if(weekdays.contains("Su")) { days.add(Weekday.SUNDAY); }
            course.schedule.add(new Schedule(days, room, startHour, startMinute, endHour, endMinute));
            return this;
        }

        public Course build() {
            return course;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseId);
        dest.writeString(section);
        dest.writeString(title);
        dest.writeInt(item);
        dest.writeFloat(units);
        dest.writeString(instructor);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeTypedList(schedule);
    }

    private static class Creator implements Parcelable.Creator<Course> {
        private AcademicQuarter qtr;

        public Creator() {
            this(null);
        }

        public Creator(AcademicQuarter qtr) {
            this.qtr = qtr;
        }

        @Override
        public Course createFromParcel(Parcel source) {
            Course c = new Course.Builder(qtr)
                        .setCourseId(source.readString(), source.readString())
                        .setTitle(source.readString())
                        .setItem(source.readInt())
                        .setUnits(source.readFloat())
                        .setInstructor(source.readString())
                        .build();
            c.startDate = new Date(source.readLong());
            c.endDate = new Date(source.readLong());
            c.schedule = new ArrayList<>();
            source.readTypedList(c.schedule, Schedule.CREATOR);
            return null;
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    }

    public static final Parcelable.Creator<Course> CREATOR
            = new Creator();

    public static Parcelable.Creator<Course> createCreator(AcademicQuarter qtr) {
        return new Creator(qtr);
    }
}
