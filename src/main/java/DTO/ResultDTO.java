package DTO;

import entity.Result;
import entity.Student;
import entity.Test;

import java.util.List;

public class ResultDTO {
    List<Student> studentList;
    List<Result> resultList;

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }
}
