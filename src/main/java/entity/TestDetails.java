package entity;

import exception.ValidateException;

import java.util.Map;

public class TestDetails {
    private int TestID;
    private int QuestionID;
    public void validate(){
        validateQuestionID();
        validateTestID();
    }
    public void validateQuestionID(){
        if(this.QuestionID == 0){
            throw new ValidateException(Map.of("Mã câu hỏi","Mã câu hỏi không được để trống"));
        }
    }
    private void validateTestID(){
        if(this.TestID == 0){
            throw new ValidateException(Map.of("Mã bài thi","Mã bài thi không được để trống"));
        }
    }
    public int getTestID() {
        return TestID;
    }

    public void setTestID(int testID) {
        TestID = testID;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }
}
