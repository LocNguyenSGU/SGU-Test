package repository;

import config.MysqlConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestDetailsRepository {
    private final Connection connection = MysqlConfig.getConnection();
    public void insertTestIntoTestDetail(int testId, int backupQuestionId) {
        String sql = "insert into testDetails(testID, backUpQuestionId) values(?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, testId);
            preparedStatement.setInt(2, backupQuestionId);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Loi ham insertTestIntoTestDetail repository");
            System.out.println(e);
        }
    }
}
