package repository;

import DTO.Estimate;
import config.MysqlConfig;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionEstimateLevelRepository {
    public List<Estimate> getAnalystLevelQuestions(int ExamID){
        Connection connection = MysqlConfig.getConnection();
        List<Estimate> estimates = new ArrayList<>();
        try {
            String sql = "CALL updateLevelByCounting(?)";
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1,ExamID);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                Estimate estimate = new Estimate();
                estimate.setQuestionID(resultSet.getInt("QuestionID"));
                estimate.setTotalCorrect(resultSet.getInt("total_correct"));
                estimate.setTotalAnswer(resultSet.getInt("total_answer"));
                estimate.setPercentage(resultSet.getInt("correct_percentage"));
                estimates.add(estimate);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch(Exception e){
                System.err.println("Lỗi đóng kết nối cơ sở dữ liệu:"+e.getMessage());
            }
        }
        return estimates;
    }
}
