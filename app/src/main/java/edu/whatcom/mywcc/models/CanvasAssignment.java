package edu.whatcom.mywcc.models;

import java.util.Calendar;
import java.util.Date;

public class CanvasAssignment {
    public String className;
    public String assignmentName;
    public Date dueDate;

    public CanvasAssignment(String className, String assignmentName, Date dueDate) {
        this.className = className;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
    }
}
