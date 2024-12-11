package entity;

import com.google.gson.annotations.Expose;
import entity.Enum.QuestionLevel;
import exception.ValidateException;

import java.util.List;
import java.util.Map;

public class Question {
    @Expose
    private int QuestionID;
    @Expose
    private int QuestionIdBackup;
    @Expose
    private String Title;
    @Expose
    private double Point;
    @Expose
    private int CorrectAnswer;
    @Expose
    private QuestionLevel Level;
    @Expose
    private List<Option> options;
    @Expose
    private int EmployeeID;
    @Expose
    private short SubjectID;
    @Expose
    private Employee employee;
    @Expose
    private Subject subject;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void validate(){
        validateTitle();
        validateLevel();
        validatePoint();
        validateCorrectAnswer();
        validateSubjectID();
    }
    private void validateTitle(){
        if(this.Title == null || this.Title.isBlank()){
            throw new ValidateException(Map.of("Nội dung","Nội dung câu hỏi không được để trống"));
        }
    }
    private void validatePoint(){
        if(this.Point > 0){
            throw new ValidateException(Map.of("Điểm","Điểm của câu hỏi phải lớn hơn 0"));
        }
    }
    private void validateCorrectAnswer(){
        if(this.CorrectAnswer == 0){
            throw new ValidateException(Map.of("Câu trả lời đúng","Câu trả lời đúng không được để trống"));
        }
    }
    private void validateSubjectID(){
        if(this.SubjectID == 0){
            throw new ValidateException(Map.of("Mã môn học","Mã môn học không được để trống"));
        }
    }
    private void validateLevel(){
        if(this.Level == null){
            throw new ValidateException(Map.of("Mức độ","Mức độ câu hỏi không được để trống"));
        }
    }
    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getPoint() {
        return Point;
    }

    public void setPoint(double point) {
        Point = point;
    }
    
    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public QuestionLevel getLevel() {
        return Level;
    }

    public void setLevel(QuestionLevel level) {
        Level = level;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getQuestionIdBackup() {
        return QuestionIdBackup;
    }

    public void setQuestionIdBackup(int questionIdBackup) {
        QuestionIdBackup = questionIdBackup;
    }

}
