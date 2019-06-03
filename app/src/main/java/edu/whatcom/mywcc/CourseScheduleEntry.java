package edu.whatcom.mywcc;

import edu.whatcom.mywcc.models.Course;

class CourseScheduleEntry implements Comparable<CourseScheduleEntry> {
    public Course course;
    public Course.Schedule schedule;

    public CourseScheduleEntry(Course c, Course.Schedule s) {
        course = c;
        schedule = s;
    }

    @Override
    public int compareTo(CourseScheduleEntry o) {
        return schedule.compareTo(o.schedule);
    }
}
