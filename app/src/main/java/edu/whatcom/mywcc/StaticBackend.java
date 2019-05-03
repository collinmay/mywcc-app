package edu.whatcom.mywcc;

import edu.whatcom.mywcc.models.*;

import java.util.Arrays;
import java.util.Date;

public class StaticBackend implements Backend {
    @Override
    public StudentProfile getStudentProfile() {
        StudentProfile profile = new StudentProfile();
        profile.canvasAssignments.add(new CanvasAssignment("ENGR240", "Worksheet 3.1",
                new Date(200, 11, 31)));

        AcademicQuarter spring19 = new AcademicQuarter(
                2019,
                Season.SPRING);

        Room bkr106 = Building.getById("BKR").getRoom("0106");
        Room cas123a = Building.getById("CAS").getRoom("0123A");

        profile.quarterlyEnrollments.put(spring19, Arrays.asList(
                new Course.Builder(spring19)
                    .setCourseId("CS 240", "A")
                    .setTitle("DATA STRUCTURE/ALGORITHM")
                    .setItem(2230)
                    .setInstructor("PARSONS, R")
                    .addSchedule("MWF", bkr106, 15, 30, 16, 60)
                    .addSchedule("TTh", bkr106, 15, 30, 17, 20)
                    .build(),
                new Course.Builder(spring19)
                    .setCourseId("ENGR 240", "HY1")
                    .setTitle("APPLIED NUMERIC METHODS")
                    .setItem(3640)
                    .setInstructor("DAVISHAHL, E")
                    .addSchedule("TTh", cas123a, 11, 30, 13, 20)
                    .build()
                ));
        return new StudentProfile();
    }
}
