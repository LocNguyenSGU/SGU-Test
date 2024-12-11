package repository;

import DTO.ResultDTO;
import DTO.TestDTO;
import config.MysqlConfig;
import entity.Enum.ExaminationStatus;
import entity.Enum.ResultStatus;
import entity.Result;
import entity.Test;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultRepository {
    public Result getResultByStudentID(int StudentID){
        Connection connection = MysqlConfig.getConnection();
        Result result    = new Result();

        try {
            String getResult = "SELECT * FROM Result WHERE StudentID = ?";
            PreparedStatement result_statement = connection.prepareStatement(getResult);
            result_statement.setInt(1,StudentID);
            ResultSet result_resultSet = result_statement.executeQuery();
            result_resultSet.next();
            result.setResultID(result_resultSet.getInt("ResultID"));
            result.setTotalCorrect(result_resultSet.getByte("TotalCorrect"));
            result.setTotalIncorrect(result_resultSet.getByte("TotalIncorrect"));
            result.setTotalPoint(result_resultSet.getDouble("TotalPoint"));
            return result;
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getTestsByExamID ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getTestsByExamID ) , Details : "+e.getMessage());
            }
        }
    }
    public void updateResultStatus(int ResultID,String status){
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql = "UPDATE Result SET Status = ? WHERE ResultID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,status);
            statement.setInt(2,ResultID);
            statement.executeUpdate();
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( updateResultStatus ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể cập nhật thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( updateResultStatus ) , Details : "+e.getMessage());
            }
        }
    }
    public Result getResultByStudentIDAndTestID(int StudentID,int TestID){
        Connection connection = MysqlConfig.getConnection();
        Result results = new Result();
        try {
            String getResult = "SELECT r.ResultID, r.TotalCorrect, r.TotalIncorrect, r.TotalPoint, r.Status ResultStatus,t.TestID,t.SubjectID,t.Duration,t.DateStart,t.DateEnd,s.Name,t.NumberOfQuestion,t.Status TestStatus,t.Description,t.TotalPoint TestPoint FROM Result r JOIN Test t ON t.TestID = r.TestID JOIN Subject s ON s.SubjectID = t.SubjectID WHERE r.StudentID = ? AND r.TestID = ? LIMIT 1";
            PreparedStatement result_statement = connection.prepareStatement(getResult);
            result_statement.setInt(1,StudentID);
            result_statement.setInt(2,TestID);
            ResultSet result_resultSet = result_statement.executeQuery();
            result_resultSet.next();
                Result result = new Result();
                result.setResultID(result_resultSet.getInt("ResultID"));
                result.setTotalCorrect(result_resultSet.getByte("TotalCorrect"));
                result.setTotalIncorrect(result_resultSet.getByte("TotalIncorrect"));
                result.setTotalPoint(result_resultSet.getDouble("TotalPoint"));
                result.setStatus(ResultStatus.valueOf(result_resultSet.getString("ResultStatus")));
                result.setTestID(result_resultSet.getInt("TestID"));
                result.setSubjectID(result_resultSet.getInt("SubjectID"));
                result.setDuration(result_resultSet.getString("Duration"));
                result.setTestPoint(result_resultSet.getDouble("TestPoint"));
                result.setDateStart(result_resultSet.getTimestamp("DateStart").toLocalDateTime());
                result.setDateEnd(result_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                result.setSubjectName(result_resultSet.getString("Name"));
                result.setTestStatus(ExaminationStatus.valueOf(result_resultSet.getString("TestStatus")));
                result.setNumberOfQuestion(result_resultSet.getInt("NumberOfQuestion"));
                result.setDescription(result_resultSet.getString("Description"));
            return result;
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
    public List<Result> getResultByStudentIDAndExamID(int StudentID,int ExamID){
        Connection connection = MysqlConfig.getConnection();
        List<Result> results = new ArrayList<>();
        try {
            String getResult = "SELECT r.ResultID, r.TotalCorrect, r.TotalIncorrect, r.TotalPoint, r.Status ResultStatus,t.TestID,t.SubjectID,t.Duration,t.DateStart,t.DateEnd,s.Name,t.NumberOfQuestion,t.Status TestStatus,t.Description FROM Result r JOIN Test t ON t.TestID = r.TestID JOIN Subject s ON s.SubjectID = t.SubjectID WHERE r.StudentID = ? AND r.ExamID = ?";
            PreparedStatement result_statement = connection.prepareStatement(getResult);
            result_statement.setInt(1,StudentID);
            result_statement.setInt(2,ExamID);
            ResultSet result_resultSet = result_statement.executeQuery();
            while(result_resultSet.next()){
                Result result = new Result();
                result.setResultID(result_resultSet.getInt("ResultID"));
                result.setTotalCorrect(result_resultSet.getByte("TotalCorrect"));
                result.setTotalIncorrect(result_resultSet.getByte("TotalIncorrect"));
                result.setTotalPoint(result_resultSet.getDouble("TotalPoint"));
                result.setStatus(ResultStatus.valueOf(result_resultSet.getString("ResultStatus")));
                result.setTestID(result_resultSet.getInt("TestID"));
                result.setSubjectID(result_resultSet.getInt("SubjectID"));
                result.setDuration(result_resultSet.getString("Duration"));
                result.setDateStart(result_resultSet.getTimestamp("DateStart").toLocalDateTime());
                result.setDateEnd(result_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                result.setSubjectName(result_resultSet.getString("Name"));
                result.setTestStatus(ExaminationStatus.valueOf(result_resultSet.getString("TestStatus")));
                result.setNumberOfQuestion(result_resultSet.getInt("NumberOfQuestion"));
                result.setDescription(result_resultSet.getString("Description"));
                results.add(result);
            }
            return results;
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
}
