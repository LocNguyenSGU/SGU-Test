package DTO;

import com.google.gson.annotations.Expose;
import entity.Exam;

import java.util.List;

public class ExamDTO {
    @Expose
    private int totalPages;
    @Expose
    private List<Exam> exams;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
