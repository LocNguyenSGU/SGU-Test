package DTO;


import com.google.gson.annotations.Expose;
import entity.Major;

import java.util.List;

public class MajorDTO {
    @Expose
    private int totalPages;
    @Expose
    private List<Major> majors;

    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<Major> getMajors() {
        return majors;
    }

    public void setMajors(List<Major> majors) {
        this.majors = majors;
    }
}
