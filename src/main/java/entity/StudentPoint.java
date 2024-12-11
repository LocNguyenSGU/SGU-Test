package entity;

import com.google.gson.annotations.Expose;

import java.util.List;

public class StudentPoint {
    @Expose
    private int BackupQuestionID;
    @Expose
    private String Title;
    @Expose
    private double Point;
    @Expose
    private int CorrectAnswer;
    @Expose
    private int ChooseOption;
    @Expose
    private List<BackupOption> options;

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

    public int getChooseOption() {
        return ChooseOption;
    }

    public void setChooseOption(int chooseOption) {
        ChooseOption = chooseOption;
    }

    public void setOptions(List<BackupOption> options) {
        this.options = options;
    }
}
