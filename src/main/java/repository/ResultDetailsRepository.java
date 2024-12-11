package repository;

import DTO.ResultDetailsDTO;
import config.MysqlConfig;
import entity.BackupQuestion;
import entity.Result;
import entity.ResultDetails;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultDetailsRepository {
    private BackupOptionRepository backupOptionRepository = new BackupOptionRepository();
    private BackupQuestionRepository backupQuestionRepository = new BackupQuestionRepository();
    public ResultDetailsDTO getAllResultDetailsByResultID(int ResultID){
        Connection connection = MysqlConfig.getConnection();
        ResultDetailsDTO resultDetailsDTO = new ResultDetailsDTO();
        List<ResultDetails> resultDetails    = new ArrayList<>();
        try {
            String getResult = "SELECT rd.ResultID,rd.BackupQuestionID,rd.ChooseOption,rd.CorrectAnswer,rd.Point,bq.Title,bq.Point QuestionPoint FROM ResultDetails rd JOIN BackupQuestion bq ON bq.BackupQuestionID = rd.BackupQuestionID WHERE ResultID = ? ORDER BY bq.BackupQuestionID ASC";
            PreparedStatement result_statement = connection.prepareStatement(getResult);
            result_statement.setInt(1,ResultID);
            ResultSet resultDetails_resultSet = result_statement.executeQuery();
            while (resultDetails_resultSet.next()) {
                ResultDetails resultDetail = new ResultDetails();
                resultDetail.setResultID(resultDetails_resultSet.getInt("ResultID"));
                resultDetail.setQuestionID(resultDetails_resultSet.getInt("BackupQuestionID"));
                resultDetail.setChooseOption(resultDetails_resultSet.getInt("ChooseOption"));
                resultDetail.setCorrectAnswer(resultDetails_resultSet.getInt("CorrectAnswer"));
                resultDetail.setPoint(resultDetails_resultSet.getDouble("Point"));
                resultDetail.setQuestionName(resultDetails_resultSet.getString("Title"));
                resultDetail.setQuestionPoint(resultDetails_resultSet.getInt("QuestionPoint"));
                resultDetail.setOptions(backupOptionRepository.getAllOptionsByBackupQuestionID(resultDetails_resultSet.getInt("BackupQuestionID")));
                resultDetails.add(resultDetail);
            }
            resultDetailsDTO.setResultDetails(resultDetails);
            return resultDetailsDTO;
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getResultByStudentIDAndTestID ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getResultByStudentIDAndTestID ) , Details : "+e.getMessage());
            }
        }
    }
    public void updateResultDetails(int ResultID,int QuestionID,int ChooseOption){
        Connection connection = MysqlConfig.getConnection();
        BackupQuestion backupQuestion = backupQuestionRepository.getBackupQuestionByID(QuestionID);
        try {
            String resultsql = "UPDATE Result SET TotalCorrect = TotalCorrect + ?, TotalIncorrect = TotalIncorrect + ?, TotalPoint = TotalPoint + ? WHERE ResultID = ?";
            String sql = "UPDATE ResultDetails SET ChooseOption = ?, Point = ? WHERE ResultID = ? AND BackupQuestionID = ?";
            PreparedStatement statement1 = connection.prepareStatement(resultsql);
            PreparedStatement statement2 = connection.prepareStatement(sql);
            statement2.setInt(1,ChooseOption);
            if(backupQuestion.getCorrectAnswer() == ChooseOption){
                statement1.setInt(1,1);
                statement1.setInt(2,0);
                statement1.setDouble(3,backupQuestion.getPoint());
                statement2.setDouble(2,backupQuestion.getPoint());
            }else{
                statement1.setInt(1,0);
                statement1.setInt(2,1);
                statement1.setDouble(3,0);
                statement2.setDouble(2,0);
            }
            statement1.setInt(4,ResultID);
            statement2.setInt(3,ResultID);
            statement2.setInt(4,QuestionID);
            statement1.executeUpdate();
            statement2.executeUpdate();
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( updateResultDetails ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể cập nhật thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( updateResultDetails ) , Details : "+e.getMessage());
            }
        }
    }
}
