package entity;

import exception.ValidateException;
import java.sql.Date;
import java.util.Map;
import java.util.List;
public class StudentVer2 {
   List<Major> Majors;
   List<Student> Students;

    public StudentVer2(List<Major> majors, List<Student> studens) {
        Majors = majors;
        Students = studens;
    }

    public List<Major> getMajors() {
        return Majors;
    }

    public void setMajors(List<Major> majors) {
        Majors = majors;
    }

    public List<Student> getStudents() {
        return Students;
    }

    public void setStudens(List<Student> students) {
        Students = students;
    }
}
