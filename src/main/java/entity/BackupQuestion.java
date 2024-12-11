package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.util.List;
import java.util.Map;

public class BackupQuestion {
    @Expose
    private int BackupQuestionID;
    @Expose
    private String Title;
    @Expose
    private double Point;
    @Expose
    private int CorrectAnswer;
    @Expose
    private List<BackupOption> options;
    @Expose
    private int QuestionID;
    public void validate(){
        validateTitle();
        validatePoint();
        validateCorrectAnswer();
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

    public int getBackupQuestionID() {
        return BackupQuestionID;
    }

    public void setBackupQuestionID(int backupQuestionID) {
        BackupQuestionID = backupQuestionID;
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

    public List<BackupOption> getOptions() {
        return options;
    }

    public void setOptions(List<BackupOption> options) {
        this.options = options;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }
}
