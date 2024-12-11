package DTO;

import com.google.gson.annotations.Expose;
import entity.BackupQuestion;

import java.util.List;

public class BackupQuestionDTO {
    @Expose
    private int totalPages;
    @Expose
    private List<BackupQuestion> backupQuestions;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<BackupQuestion> getBackupQuestions() {
        return backupQuestions;
    }

    public void setBackupQuestions(List<BackupQuestion> backupQuestions) {
        this.backupQuestions = backupQuestions;
    }
}
