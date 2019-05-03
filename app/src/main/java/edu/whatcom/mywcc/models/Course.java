package edu.whatcom.mywcc.models;

import java.util.*;

public class Course {
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

    public static class Schedule {
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
}
