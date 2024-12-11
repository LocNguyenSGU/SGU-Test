package DTO;

import com.google.gson.annotations.Expose;
import entity.Student;

import java.util.List;

public class StudentDTO {
    @Expose
    private int numberStudent;
    @Expose
    private int totalPages;
    @Expose
    private List<Student> students;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getNumberStudent() {
        return numberStudent;
    }
    public void setNumberStudent(int numberStudent)
    {
        this.numberStudent = numberStudent;
    }

}
