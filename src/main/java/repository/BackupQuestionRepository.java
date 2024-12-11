package repository;

import DTO.BackupQuestionDTO;
import DTO.QuestionDTO;
import config.MysqlConfig;
import entity.BackupQuestion;
import entity.Question;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BackupQuestionRepository {
    public BackupQuestionDTO getAllBackupQuestionByTestID(int TestID){
        Connection connection = MysqlConfig.getConnection();
        BackupQuestionDTO backupQuestionDTO   = new BackupQuestionDTO();
        try {
            String getQuestions = "SELECT * FROM BackupQuestion JOIN TestDetails ON TestDetails.BackupQuestionID = BackupQuestion.BackupQuestionID WHERE TestDetails.TestID = ? ORDER BY BackupQuestion.BackupQuestionID ASC";
            List<BackupQuestion> backupQuestions = new ArrayList<>();
            PreparedStatement questions_statement = connection.prepareStatement(getQuestions);
            questions_statement.setInt(1,TestID);
            ResultSet questions_resultSet = questions_statement.executeQuery();
            while(questions_resultSet.next()){
                BackupQuestion backupQuestion = new BackupQuestion();
                backupQuestion.setCorrectAnswer(questions_resultSet.getInt("CorrectAnswer"));
                backupQuestion.setQuestionID(questions_resultSet.getInt("QuestionID"));
                backupQuestion.setBackupQuestionID(questions_resultSet.getInt("BackupQuestionID"));
                backupQuestion.setTitle(questions_resultSet.getString("Title"));
                backupQuestion.setPoint(questions_resultSet.getInt("Point"));
                backupQuestions.add(backupQuestion);
            };
            backupQuestionDTO.setBackupQuestions(backupQuestions);
            return backupQuestionDTO;
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getAllQuestionByTestID ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getAllQuestionByTestID ) , Details : "+e.getMessage());
            }
        }
    }
    public BackupQuestion getBackupQuestionByID(int BackupQuestionID){
        Connection connection = MysqlConfig.getConnection();
        BackupQuestion backupQuestion = new BackupQuestion();
        try {
            String sql = "SELECT * FROM BackupQuestion WHERE BackupQuestionID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,BackupQuestionID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            backupQuestion.setBackupQuestionID(resultSet.getInt("BackupQuestionID"));
            backupQuestion.setPoint(resultSet.getInt("Point"));
            backupQuestion.setCorrectAnswer(resultSet.getInt("CorrectAnswer"));
            return backupQuestion;
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( getAllQuestionByTestID ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }
    }
}
