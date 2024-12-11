package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.util.Map;

public class Option {
    @Expose
    private int OptionID;
    @Expose
    private String Title;
    @Expose
    private int QuestionID;
    public void validate(){
        validateQuestion();
        validateTitle();
    }
    private void validateTitle(){
        if(this.Title == null || this.Title.isBlank()){
            throw new ValidateException(Map.of("Nội dung","Nội dung câu trả lời không được để trống"));
        }
    }
    private void validateQuestion(){
        if(this.QuestionID == 0){
            throw new ValidateException(Map.of("Mã câu hỏi","Mã câu hỏi không được để trống"));
        }
    }
    public int getOptionID() {
        return OptionID;
    }

    public void setOptionID(int optionID) {
        OptionID = optionID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }
}
