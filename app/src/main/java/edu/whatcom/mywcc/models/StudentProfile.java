package edu.whatcom.mywcc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentProfile {
    public int orcaCardFreePrints = 1480;
    public int orcaCardDiningDollars = 430;
    public int orcaCardBookstore = 1000000;
    public int orcaCardBonusBucks = 1337;
    public String orcaStudentName = "Mister Potato";

    public List<CanvasAssignment> canvasAssignments = new ArrayList<>();
    public List<Email> recentEmails = new ArrayList<>();
    public Map<AcademicQuarter, List<Course>> quarterlyEnrollments = new HashMap<>();
}
