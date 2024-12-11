package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.util.List;
import java.util.Map;

public class ResultDetails {
    @Expose
    private int ResultID;
    @Expose
    private int QuestionID;
    @Expose
    private int CorrectAnswer;
    @Expose
    private String CorrectAnswerTitle;
    @Expose
    private int ChooseOption;
    @Expose
    private String QuestionName;
    @Expose
    private String ChooseOptionTitle;
    @Expose
    private double Point;
    @Expose
    private double QuestionPoint;
    @Expose
    private List<BackupOption> options;
    public void validate(){
        validateQuestionID();
        validateResultID();
    }

    public double getQuestionPoint() {
        return QuestionPoint;
    }

    public void setQuestionPoint(double questionPoint) {
        QuestionPoint = questionPoint;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String questionName) {
        QuestionName = questionName;
    }

    public List<BackupOption> getOptions() {
        return options;
    }

    public void setOptions(List<BackupOption> options) {
        this.options = options;
    }

    private void validateResultID(){
        if(this.ResultID == 0){
            throw new ValidateException(Map.of("Mã kết quả","Mã kết quả không được để trống"));
        }
    }
    private void validateQuestionID(){
        if(this.QuestionID == 0){
            throw new ValidateException(Map.of("Mã câu hỏi","Mã câu hỏi không được để trống"));
        }
    }
    public int getResultID() {
        return ResultID;
    }

    public void setResultID(int resultID) {
        ResultID = resultID;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCorrectAnswerTitle() {
        return CorrectAnswerTitle;
    }

    public void setCorrectAnswerTitle(String correctAnswerTitle) {
        CorrectAnswerTitle = correctAnswerTitle;
    }

    public int getChooseOption() {
        return ChooseOption;
    }

    public void setChooseOption(int chooseOption) {
        ChooseOption = chooseOption;
    }

    public String getChooseOptionTitle() {
        return ChooseOptionTitle;
    }

    public void setChooseOptionTitle(String chooseOptionTitle) {
        ChooseOptionTitle = chooseOptionTitle;
    }

    public double getPoint() {
        return Point;
    }

    public void setPoint(double point) {
        Point = point;
    }
}
