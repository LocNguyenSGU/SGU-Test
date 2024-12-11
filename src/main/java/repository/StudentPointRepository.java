package repository;

import DTO.BackupQuestionDTO;
import DTO.SettingInitDTO;
import DTO.StudentPointDTO;
import config.MysqlConfig;
import entity.BackupQuestion;
import entity.Student;
import entity.StudentPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentPointRepository {
    private BackupQuestionRepository backupQuestionRepository = new BackupQuestionRepository();
    public StudentPointDTO getQuestionAndOptionAnswerOfStudentByResultID(int ResultID){
        Connection connection = MysqlConfig.getConnection();
        StudentPointDTO studentPointDTO = new StudentPointDTO();
        try {
            String sql = "SELECT * FROM ResultDetails JOIN BackupQuestion ON ResultDetails.BackupQuestionID = BackupQuestion.BackupQuestionID WHERE ResultID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,ResultID);
            ResultSet resultSet = statement.executeQuery();
            List<StudentPoint> studentPoints = new ArrayList<>();
            while(resultSet.next()){
                StudentPoint studentPoint = new StudentPoint();
                studentPoint.setCorrectAnswer(resultSet.getInt("CorrectAnswer"));
                studentPoint.setBackupQuestionID(resultSet.getInt("BackupQuestionID"));
                studentPoint.setChooseOption(resultSet.getInt("ChooseOption"));
                studentPoint.setTitle(resultSet.getString("Title"));
                studentPoint.setPoint(resultSet.getInt("Point"));
                studentPoints.add(studentPoint);
            }
            studentPointDTO.setStudentPoints(studentPoints);
        }catch(Exception e){
            System.err.println("Lỗi truy vấn getQuestionAndOptionAnswerOfStudentByResultID : "+e.getMessage());
        }finally {
            try {
                connection.close();
            }catch(Exception e){
                System.err.println("Lỗi đóng cơ sở dữ liệu getQuestionAndOptionAnswerOfStudentByResultID : "+e.getMessage());
            }
        }
        return studentPointDTO;
    }

    private void settingInitResultDetailsDefault(int ResultID,int TestID,int ExamID){
        Connection connection = MysqlConfig.getConnection();
        BackupQuestionDTO backupQuestionDTO = backupQuestionRepository.getAllBackupQuestionByTestID(TestID);
        for(BackupQuestion backupQuestion : backupQuestionDTO.getBackupQuestions()){
            try {
                String sql = "INSERT INTO ResultDetails (ResultID,BackupQuestionID,ExamID,CorrectAnswer,ChooseOption,Point) VALUES (?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,ResultID);
                statement.setInt(2,backupQuestion.getBackupQuestionID());
                statement.setInt(3,ExamID);
                statement.setInt(4, backupQuestion.getCorrectAnswer());
                statement.setInt(5,0);
                statement.setDouble(6,0);
                statement.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private void settingInitResultDefault(int TestID,int StudentID,int ExamID){
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql = "INSERT INTO Result (StudentID,TestID,ExamID,TotalCorrect,TotalIncorrect,TotalPoint,Status) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, StudentID);
            statement.setInt(2,TestID);
            statement.setInt(3,ExamID);
            statement.setShort(4, (short) 0);
            statement.setShort(5,(short) 0);
            statement.setDouble(6,0);
            statement.setString(7,"PENDING");
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            if(generatedKey.next()){
                int ResultID = generatedKey.getInt(1);
                settingInitResultDetailsDefault(ResultID,TestID,ExamID);
            }else{
                System.out.print("Lỗi thêm dữ liệu (không thể lấy khóa ResultID):");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void settingInitResultForStudentWithRandomTest(int ExamID,short MajorID,int StudentID){
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql = "CALL SettingInit(?,?)";
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1,ExamID);
            callableStatement.setShort(2,MajorID);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                settingInitResultDefault(resultSet.getInt("TestID"),StudentID,ExamID);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
