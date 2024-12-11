package entity;

import com.google.gson.annotations.Expose;
import entity.Enum.ExaminationStatus;
import exception.ValidateException;
import java.sql.Date;
import entity.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Exam {
    @Expose
    private int ExamID;
    @Expose
    private String Name;
    @Expose
    private LocalDateTime DateStart;
    @Expose
    private LocalDateTime DateEnd;
    @Expose
    private List<Test> tests;
    @Expose
    private ExaminationStatus Status;
//    public void validate(){
//        validateName();
//        validateDateStart();
//        validateDateEnd();
//        validateStatus();
//    }
//    private void validateName(){
//        if(this.Name == null || this.Name.isBlank()){
//            throw new ValidateException(Map.of("Tên", "Tên kỳ thi không được để trống"));
//        }
//    }
    private void validateDateStart(){
        if(this.DateStart == null){
            throw new ValidateException(Map.of("Ngày bắt đầu", "Ngày bắt đầu không được để trống"));
        }
    }
    private void validateDateEnd(){
        if(this.DateEnd == null){
            throw new ValidateException(Map.of("Ngày kết thúc", "Ngày kết thúc không được để trống"));
        }
    }
    private void validateStatus(){
        if(this.Status == null){
            throw new ValidateException(Map.of("Trạng thái", "Trạng thái must be not blank"));
        }
    }
    public int getExamID() {
        return ExamID;
    }

    public void setExamID(int examID) {
        ExamID = examID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDateTime getDateStart() {
        return DateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        DateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        DateEnd = dateEnd;
    }

    public ExaminationStatus getStatus() {
        return Status;
    }

    public void setStatus(ExaminationStatus status) {
        Status = status;
    }
    public Date getDateStartNotTime() {
        return Date.valueOf(DateStart.toLocalDate());
    }
    public Date getDateEndNotTime() {
        return Date.valueOf(DateEnd.toLocalDate());
    }
    public void setTests(List<Test> tests){
        tests = tests;
    }
    public List<Test> getTests(){
        return tests;
    }
}
