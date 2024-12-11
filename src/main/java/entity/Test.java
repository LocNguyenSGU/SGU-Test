package entity;

import com.google.gson.annotations.Expose;
import entity.Enum.ExaminationStatus;
import exception.ValidateException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class Test {
    @Expose
    private int TestID;
    @Expose
    private int NumberOfQuestion;
    @Expose
    private double TotalPoint;
    @Expose
    private String Duration;
    @Expose
    private LocalDateTime DateStart;
    @Expose
    private LocalDateTime DateEnd;
    @Expose
    private ExaminationStatus Status;
    @Expose
    private String Description;
    @Expose
    private int EmployeeID;
    @Expose
    private int ExamID;
    @Expose
    private short SubjectID;
    @Expose
    private int FromTestID;
    @Expose
    private List<Question> questions;
    @Expose
    private String SubjectName;
    public void validate(){
        validateExamID();
        validateTotalPoint();
        validateSubjectID();
        validateNumberOfQuestion();
        validateStatus();
        validateDateStart();
        validateDateEnd();
        countingDuration(this.DateStart,this.DateEnd);
    }
    private void countingDuration(LocalDateTime startDate, LocalDateTime endDate){
// Calculate the difference between dates
        long years = ChronoUnit.YEARS.between(startDate, endDate);
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        long hours = ChronoUnit.HOURS.between(startDate, endDate);
        long minutes = ChronoUnit.MINUTES.between(startDate, endDate);
        long seconds = ChronoUnit.SECONDS.between(startDate, endDate);

        // Adjust for difference in months and years
        days -= months * 30;
        months -= years * 12;

        // Construct the result string
        StringBuilder result = new StringBuilder();
        if (years != 0) {
            result.append(years).append(" năm ");
        }
        if (months != 0) {
            result.append(months).append(" tháng ");
        }
        if (days != 0) {
            result.append(days).append(" ngày ");
        }
        if (hours != 0) {
            result.append(hours).append(" giờ ");
        }
        if (minutes != 0) {
            result.append(minutes).append(" phút ");
        }
        if (seconds != 0) {
            result.append(seconds).append(" giây ");
        }

        this.Duration = result.toString();
    }
    private void validateTotalPoint(){
        if(this.TotalPoint == 0){
            throw new ValidateException(Map.of("Tổng điểm", "Tổng điểm không bằng 0"));
        }
    }
    private void validateNumberOfQuestion(){
        if(this.NumberOfQuestion == 0){
            throw new ValidateException(Map.of("Tổng câu hỏi", "Số lượng câu hỏi không bằng 0"));
        }
    }
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
    private void validateExamID(){
        if(this.ExamID == 0){
            throw new ValidateException(Map.of("Mã kỳ thi","Mã kỳ thi không được để trống"));
        }
    }
    private void validateSubjectID(){
        if(this.SubjectID == 0){
            throw new ValidateException(Map.of("Mã môn học","Mã môn học không được để trống"));
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

    public int getTestID() {
        return TestID;
    }

    public void setTestID(int testID) {
        TestID = testID;
    }

    public int getNumberOfQuestion() {
        return NumberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        NumberOfQuestion = numberOfQuestion;
    }

    public double getTotalPoint() {
        return TotalPoint;
    }

    public void setTotalPoint(double totalPoint) {
        TotalPoint = totalPoint;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public short getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(short subjectID) {
        SubjectID = subjectID;
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
    public int getFromTestID() {
        return FromTestID;
    }

    public void setFromTestID(int fromTestID) {
        FromTestID = fromTestID;
    }
    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
