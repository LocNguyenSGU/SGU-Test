package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.util.Map;

public class BackupOption {
    @Expose
    private int BackupOptionID;
    @Expose
    private int BackupQuestionID;
    @Expose
    private String Title;
    public void validate(){
        validateBackupQuestion();
        validateTitle();
    }
    private void validateTitle(){
        if(this.Title == null || this.Title.isBlank()){
            throw new ValidateException(Map.of("Nội dung","Nội dung câu trả lời không được để trống"));
        }
    }
    private void validateBackupQuestion(){
        if(this.BackupQuestionID == 0){
            throw new ValidateException(Map.of("Mã câu hỏi","Mã câu hỏi không được để trống"));
        }
    }

    public int getBackupOptionID() {
        return BackupOptionID;
    }

    public void setBackupOptionID(int backupOptionID) {
        BackupOptionID = backupOptionID;
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
}
