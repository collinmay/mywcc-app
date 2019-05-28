package edu.whatcom.mywcc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentProfile {
    public String orcaCardFreePrints = "$14.80";
    public String orcaCardDiningDollars = "$5.50";
    public String orcaCardBookstore = "$12.50";
    public String orcaCardBonusBucks = "$20.00";
    public String orcaCash = "$4.00";
    public String orcaStudentName = "Mister Potato";

    public List<CanvasAssignment> canvasAssignments = new ArrayList<>();
    public List<Email> recentEmails = new ArrayList<>();
    public Map<AcademicQuarter, List<Course>> quarterlyEnrollments = new HashMap<>();
}
