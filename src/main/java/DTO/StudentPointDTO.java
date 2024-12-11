package DTO;

import com.google.gson.annotations.Expose;
import entity.StudentPoint;

import java.util.List;

public class StudentPointDTO {
    @Expose
    private List<StudentPoint> studentPoints;

    public List<StudentPoint> getStudentPoints() {
        return studentPoints;
    }

    public void setStudentPoints(List<StudentPoint> studentPoints) {
        this.studentPoints = studentPoints;
    }
}
