package repository;

import DTO.MajorDTO;
import DTO.TestDTO;
import config.MysqlConfig;
import entity.Enum.ExaminationStatus;
import entity.Major;
import entity.Test;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestRepo2Loc {
    public TestDTO getAllTestWithoutPaging(){
        Connection connection = MysqlConfig.getConnection();
        TestDTO testDTO = new TestDTO();
        try {
            String getMajors_query = "SELECT * FROM test WHERE isDeleted = 0 AND fromTestID = testId";
            PreparedStatement majors_statement = connection.prepareStatement(getMajors_query);
            ResultSet resultSet = majors_statement.executeQuery();

            List<Test> tests = new ArrayList<>();

            while (resultSet.next()) {
                Test test = new Test();
                test.setTestID(resultSet.getInt("testId"));
                test.setExamID(resultSet.getInt("examId"));
                test.setSubjectID(resultSet.getShort("subjectID"));
                test.setEmployeeID(resultSet.getInt("employeeId"));
                test.setDescription(resultSet.getString("description"));
                test.setTotalPoint(resultSet.getDouble("totalPoint"));
                test.setNumberOfQuestion(resultSet.getInt("numberOfQuestion"));
                test.setDuration(resultSet.getString("Duration"));
                test.setDateStart(resultSet.getTimestamp("DateStart").toLocalDateTime());
                test.setDateEnd(resultSet.getTimestamp("DateEnd").toLocalDateTime());
                test.setStatus(ExaminationStatus.valueOf(resultSet.getString("Status")));
                tests.add(test);
            }
            testDTO.setTests(tests);
        }catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllTest testRepo_2_Loc. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Test", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: Unable to close connection in function getAllMajor. Details: " + e.getMessage());
            }
        }
        return testDTO;
    }
    public void deleteTestByTestID(int testID) {
        try {
            String sql = "UPDATE test SET Isdeleted = 1 WHERE testId = ?";
            Connection connection = MysqlConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            System.err.println("Error: Unable to delete test by testID in function deleteTestByTestID testRepo_2_Loc. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Test", "Không thể xóa thông tin"));
        }
    }
    public Test getTestById(int id){
        Connection connection = MysqlConfig.getConnection();
        Test test = new Test();
        try {
            String getMajor = "SELECT * FROM test WHERE testID = ?";
            PreparedStatement major_statement = connection.prepareStatement(getMajor);
            major_statement.setInt(1,id);
            ResultSet resultSet = major_statement.executeQuery();

            if (resultSet.next()) { // Bỏ qua kiểm tra nếu bạn chắc chắn truy vấn sẽ trả về duy nhất một kết quả
                test.setTestID(resultSet.getInt("TestID"));
                test.setNumberOfQuestion(resultSet.getByte("NumberOfQuestion"));
                test.setTotalPoint(resultSet.getDouble("TotalPoint"));
                test.setDuration(resultSet.getString("Duration"));
                test.setDateStart(resultSet.getTimestamp("DateStart").toLocalDateTime());
                test.setDateEnd(resultSet.getTimestamp("DateEnd").toLocalDateTime());
                test.setStatus(ExaminationStatus.valueOf(resultSet.getString("Status")));
                test.setDescription(resultSet.getString("Description"));
                test.setEmployeeID(resultSet.getInt("EmployeeID"));
                test.setExamID(resultSet.getInt("ExamID"));
                test.setSubjectID(resultSet.getShort("SubjectID"));
            } else {
                // Xử lý trường hợp không tìm thấy dữ liệu
                System.out.println("Không tìm thấy dữ liệu cho TestID: " + id);
            }
            return test;
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( getTestById ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("test","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getTestById ) , Details : "+e.getMessage());
            }
        }
    }

}
