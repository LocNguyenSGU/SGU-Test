package entity;

import com.google.gson.annotations.Expose;

public class ResultStudent {
    @Expose
    private String ExamName;
    @Expose
    private String SubjectName;
    @Expose
    private String MajorName;
    private double Point;

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public double getPoint() {
        return Point;
    }

    public void setPoint(double point) {
        Point = point;
    }
}
