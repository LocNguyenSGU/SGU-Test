package DTO;

public class Estimate {
    private int QuestionID;
    private int TotalCorrect;
    private int TotalAnswer;
    private int Percentage;

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public int getTotalCorrect() {
        return TotalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        TotalCorrect = totalCorrect;
    }

    public int getTotalAnswer() {
        return TotalAnswer;
    }

    public void setTotalAnswer(int totalAnswer) {
        TotalAnswer = totalAnswer;
    }

    public int getPercentage() {
        return Percentage;
    }

    public void setPercentage(int percentage) {
        Percentage = percentage;
    }
}
