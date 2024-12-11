package DTO;

import com.google.gson.annotations.Expose;
import entity.Subject;

import java.util.List;

public class SubjectDTO {
    @Expose
    private int totalPages;
    @Expose
    private List<Subject> subjects;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
