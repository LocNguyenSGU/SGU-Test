package service;

import DTO.BackupQuestionDTO;
import entity.BackupOption;
import entity.BackupQuestion;
import entity.Option;
import payload.response.Response;
import repository.BackupOptionRepository;
import repository.BackupQuestionRepository;

import java.util.List;

public class BackupQuestionService {
    private BackupQuestionRepository backupQuestionRepository = new BackupQuestionRepository();
    private BackupOptionRepository backupOptionRepository = new BackupOptionRepository();
    public Response getAllBackupQuestionByTestID(int TestID){
        Response response = new Response();
        try {
            BackupQuestionDTO backupQuestionDTO = backupQuestionRepository.getAllBackupQuestionByTestID(TestID);
            for(BackupQuestion backupQuestion : backupQuestionDTO.getBackupQuestions()) {
                List<BackupOption> backupOptions = backupOptionRepository.getAllOptionsByBackupQuestionID(backupQuestion.getBackupQuestionID());
                backupQuestion.setOptions(backupOptions);
            }
            response.setData(backupQuestionDTO);
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
}
