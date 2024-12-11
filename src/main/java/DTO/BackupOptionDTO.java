package DTO;

import com.google.gson.annotations.Expose;
import entity.BackupOption;

import java.util.List;

public class BackupOptionDTO {
    @Expose
    private int totalPages;
    @Expose
    private List<BackupOption> backupOptions;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<BackupOption> getBackupOptions() {
        return backupOptions;
    }

    public void setBackupOptions(List<BackupOption> backupOptions) {
        this.backupOptions = backupOptions;
    }
}
