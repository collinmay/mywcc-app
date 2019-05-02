package edu.whatcom.mywcc;

import edu.whatcom.mywcc.models.CanvasAssignment;
import edu.whatcom.mywcc.models.StudentProfile;

import java.util.Date;

public class StaticBackend implements Backend {
    @Override
    public StudentProfile getStudentProfile() {
        StudentProfile profile = new StudentProfile();
        profile.canvasAssignments.add(new CanvasAssignment("ENGR240", "Worksheet 3.1",
                new Date(200, 11, 31)));
        return new StudentProfile();
    }
}
