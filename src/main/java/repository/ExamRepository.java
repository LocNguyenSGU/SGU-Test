package repository;

import DTO.ExamDTO;
import DTO.MajorDTO;
import config.MysqlConfig;
import entity.Enum.ExaminationStatus;
import entity.Exam;
import exception.RetrieveException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExamRepository {

    public ExamDTO getAllExamsWithoutPaging() {
        Connection connection = MysqlConfig.getConnection();
        ExamDTO examDTO = new ExamDTO();
        try {
            String getMajors_query = "SELECT * FROM exam where Isdeleted = 0";
            PreparedStatement exams_statement = connection.prepareStatement(getMajors_query);
            System.out.println("Cau lenh truy van lay tat ca exam la: " + exams_statement);
            ResultSet exam_resultSet = exams_statement.executeQuery();

            List<Exam> exams = new ArrayList<>();

            while (exam_resultSet.next()) {
                Exam exam = new Exam();
                exam.setExamID(exam_resultSet.getInt("ExamID"));
                exam.setName(exam_resultSet.getString("Name"));
                exam.setDateStart(exam_resultSet.getTimestamp("DateStart").toLocalDateTime());
                exam.setDateEnd(exam_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                exam.setStatus(ExaminationStatus.valueOf(exam_resultSet.getString("Status")));
                exams.add(exam);
            }
            examDTO.setExams(exams);
            return examDTO;
        } catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllExam. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(
                        "Error: Unable to close connection in function getAllExam. Details: " + e.getMessage());
            }
        }
    }

    // public ExamDTO getAllExams(int page, int size, String search, String status){
    // Connection connection = MysqlConfig.getConnection();
    // ExamDTO examDTO = new ExamDTO();
    // try {
    // String total_pages = "SELECT COUNT(*) FROM Exam ";
    // String getExams = "SELECT * FROM Exam ";
    //
    // if(search!=null){
    // total_pages += "WHERE Name = ?";
    // getExams += "WHERE Name = ?";
    // }
    // if(status!=null){
    // total_pages += search != null ? " AND Status = ?" : " WHERE Status = ?";
    // getExams += search != null ? " AND Status = ?" : " WHERE Status = ?";
    // }
    //
    // getExams += " LIMIT ?,?";
    //
    // PreparedStatement total_pages_statement =
    // connection.prepareStatement(total_pages);
    // PreparedStatement exams_statement = connection.prepareStatement(getExams);
    //
    // int paramIndex = 1;
    // if(search != null){
    // total_pages_statement.setString(paramIndex, search);
    // exams_statement.setString(paramIndex++, search);
    // }
    // if(status != null){
    // total_pages_statement.setString(paramIndex, status);
    // exams_statement.setString(paramIndex++, status);
    // }
    //
    // exams_statement.setInt(paramIndex++, (page-1)*size);
    // exams_statement.setInt(paramIndex, size);
    //
    // ResultSet total_pages_resultSet = total_pages_statement.executeQuery();
    // if (total_pages_resultSet.next()) {
    // examDTO.setTotalPages(total_pages_resultSet.getInt(1)/size);
    // }
    //
    // List<Exam> exams = new ArrayList<>();
    // ResultSet exams_resultSet = exams_statement.executeQuery();
    //
    // while(exams_resultSet.next()){
    // Exam exam = new Exam();
    // exam.setExamID(exams_resultSet.getInt("ExamID"));
    // exam.setName(exams_resultSet.getString("Name"));
    // exam.setDateStart(exams_resultSet.getTimestamp("DateStart").toLocalDateTime());
    // exam.setDateEnd(exams_resultSet.getTimestamp("DateEnd").toLocalDateTime());
    // exam.setStatus(ExaminationStatus.valueOf(exams_resultSet.getString("Status")));
    // exams.add(exam);
    // }
    // examDTO.setExams(exams);
    // return examDTO;
    // } catch (SQLException e) {
    // System.err.println("Error: Unable to query data in function getAllExam.
    // Details: " + e.getMessage());
    // throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông
    // tin"));
    // } finally {
    // try {
    // if (connection != null) {
    // connection.close();
    // }
    // } catch (SQLException e) {
    // System.err.println("Error: Unable to close connection in function getAllExam.
    // Details: " + e.getMessage());
    // }
    // }Connection connection = MysqlConfig.getConnection();
    // ExamDTO examDTO = new ExamDTO();
    // try {
    // String total_pages = "SELECT COUNT(*) FROM Exam ";
    // String getExams = "SELECT * FROM Exam ";
    //
    // if(search!=null){
    // total_pages += "WHERE Name = ?";
    // getExams += "WHERE Name = ?";
    // }
    // if(status!=null){
    // total_pages += search != null ? " AND Status = ?" : " WHERE Status = ?";
    // getExams += search != null ? " AND Status = ?" : " WHERE Status = ?";
    // }
    //
    // getExams += " LIMIT ?,?";
    //
    // PreparedStatement total_pages_statement =
    // connection.prepareStatement(total_pages);
    // PreparedStatement exams_statement = connection.prepareStatement(getExams);
    //
    // int paramIndex = 1;
    // if(search != null){
    // total_pages_statement.setString(paramIndex, search);
    // exams_statement.setString(paramIndex++, search);
    // }
    // if(status != null){
    // total_pages_statement.setString(paramIndex, status);
    // exams_statement.setString(paramIndex++, status);
    // }
    //
    // exams_statement.setInt(paramIndex++, (page-1)*size);
    // exams_statement.setInt(paramIndex, size);
    //
    // ResultSet total_pages_resultSet = total_pages_statement.executeQuery();
    // if (total_pages_resultSet.next()) {
    // examDTO.setTotalPages(total_pages_resultSet.getInt(1)/size);
    // }
    //
    // List<Exam> exams = new ArrayList<>();
    // ResultSet exams_resultSet = exams_statement.executeQuery();
    //
    // while(exams_resultSet.next()){
    // Exam exam = new Exam();
    // exam.setExamID(exams_resultSet.getInt("ExamID"));
    // exam.setName(exams_resultSet.getString("Name"));
    // exam.setDateStart(exams_resultSet.getTimestamp("DateStart").toLocalDateTime());
    // exam.setDateEnd(exams_resultSet.getTimestamp("DateEnd").toLocalDateTime());
    // exam.setStatus(ExaminationStatus.valueOf(exams_resultSet.getString("Status")));
    // exams.add(exam);
    // }
    // examDTO.setExams(exams);
    // return examDTO;
    // } catch (SQLException e) {
    // System.err.println("Error: Unable to query data in function getAllExam.
    // Details: " + e.getMessage());
    // throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông
    // tin"));
    // } finally {
    // try {
    // if (connection != null) {
    // connection.close();
    // }
    // } catch (SQLException e) {
    // System.err.println("Error: Unable to close connection in function getAllExam.
    // Details: " + e.getMessage());
    // }
    // }
    // }
    public static ExamDTO getAllExams(int page, int size, String search, String status) {
        Connection connection = MysqlConfig.getConnection();
        ExamDTO examDTO = new ExamDTO();
        try {
            String total_pages = "SELECT COUNT(*) FROM Exam ";
            String getExams = "SELECT * FROM Exam ";

            if (search != null) {
                total_pages += "WHERE Name = ?";
                getExams += "WHERE Name = ?";
            }
            if (status != null) {
                total_pages += search != null ? " AND Status = ?" : " WHERE Status = ?";
                getExams += search != null ? " AND Status = ?" : " WHERE Status = ?";
            }

            getExams += " LIMIT ?,?";

            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);
            PreparedStatement exams_statement = connection.prepareStatement(getExams);

            int paramIndex = 1;
            if (search != null) {
                total_pages_statement.setString(paramIndex, search);
                exams_statement.setString(paramIndex++, search);
            }
            if (status != null) {
                total_pages_statement.setString(paramIndex, status);
                exams_statement.setString(paramIndex++, status);
            }

            exams_statement.setInt(paramIndex++, (page - 1) * size);
            exams_statement.setInt(paramIndex, size);

            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();
            if (total_pages_resultSet.next()) {
                examDTO.setTotalPages(total_pages_resultSet.getInt(1) / size);
            }

            List<Exam> exams = new ArrayList<>();
            ResultSet exams_resultSet = exams_statement.executeQuery();

            while (exams_resultSet.next()) {
                Exam exam = new Exam();
                exam.setExamID(exams_resultSet.getInt("ExamID"));
                exam.setName(exams_resultSet.getString("Name"));
                exam.setDateStart(exams_resultSet.getTimestamp("DateStart").toLocalDateTime());
                exam.setDateEnd(exams_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                exam.setStatus(ExaminationStatus.valueOf(exams_resultSet.getString("Status")));
                exams.add(exam);
            }
            examDTO.setExams(exams);
            return examDTO;
        } catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllExam. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(
                        "Error: Unable to close connection in function getAllExam. Details: " + e.getMessage());
            }
        }
    }

    public ExamDTO getAllExam(int page, int size, String search, String status) {
        Connection connection = MysqlConfig.getConnection();
        ExamDTO examDTO = new ExamDTO();
        try {
            String total_pages_query = "SELECT COUNT(*) FROM Exam";
            String getExams_query = "SELECT * FROM Exam";
            String whereClause = "";

            if (search != null) {
                whereClause = " WHERE Name LIKE ?";
            }

            if (status != null) {
                if (!whereClause.isEmpty()) {
                    whereClause += " AND";
                } else {
                    whereClause = " WHERE";
                }
                whereClause += " Status = ?";
            }

            total_pages_query += whereClause;
            getExams_query += whereClause + " LIMIT ?, ?";

            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages_query);

            int parameterIndex = 1;

            if (search != null) {
                total_pages_statement.setString(parameterIndex++, "%" + search + "%");
            }
            if (status != null) {
                total_pages_statement.setString(parameterIndex++, status);
            }

            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

            int totalRecords = 0;
            if (total_pages_resultSet.next()) {
                totalRecords = total_pages_resultSet.getInt(1);
            }

            examDTO.setTotalPages((totalRecords + size - 1) / size);

            List<Exam> exams = new ArrayList<>();

            PreparedStatement exams_statement = connection.prepareStatement(getExams_query);
            parameterIndex = 1;
            if (search != null) {
                exams_statement.setString(parameterIndex++, "%" + search + "%");
            }
            if (status != null) {
                exams_statement.setString(parameterIndex++, status);
            }
            exams_statement.setInt(parameterIndex++, (page - 1) * size);
            exams_statement.setInt(parameterIndex, size);
            System.out.println("Cau lenh thu thi exam la: " + exams_statement);

            ResultSet exams_resultSet = exams_statement.executeQuery();
            while (exams_resultSet.next()) {
                try {
                    Exam exam = new Exam();
                    exam.setExamID(exams_resultSet.getInt("ExamId"));
                    System.out.println("Gia tri id la: " + exams_resultSet.getInt("ExamId"));

                    exam.setName(exams_resultSet.getString("Name"));
                    System.out.println("Name: " + exam.getName());
                    exam.setDateStart(exams_resultSet.getTimestamp("DateStart").toLocalDateTime());
                    System.out.println("Datestart: " + exam.getDateStart());

                    exam.setDateEnd(exams_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                    exam.setStatus(ExaminationStatus.valueOf(exams_resultSet.getString("Status")));
                    System.out.println("status :" + exam.getStatus());

                    exams.add(exam);
                } catch (SQLException e) {
                    // Xử lý ngoại lệ ở đây
                    System.err.println("Error occurred while processing a record: " + e.getMessage());
                    e.printStackTrace();
                    // Hoặc thực hiện các hành động khác, như ghi vào log hoặc bỏ qua bản ghi đó
                }
            }

            System.out.println("Số lượng bản ghi từ ResultSet: " + exams.size());
            examDTO.setExams(exams);
            System.out.println("Da them exams vao examDTO. Số lượng bản ghi trong examDTO: " + examDTO.getExams().size());

            return examDTO;
        } catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllExam. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Exam", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: Unable to close connection in function getAllExam. Details: " + e.getMessage());
            }
        }
    }

    public ExamDTO getAllExamWithoutPageable(String status){
        Connection connection = MysqlConfig.getConnection();
        ExamDTO examDTO = new ExamDTO();
        try {
            String getExams_query = "SELECT * FROM Exam WHERE Status = ? ORDER BY ExamID DESC";
            PreparedStatement exams_statement = connection.prepareStatement(getExams_query);
            exams_statement.setString(1,status);
            ResultSet exams_resultSet = exams_statement.executeQuery();
            List<Exam> exams = new ArrayList<>();
            while(exams_resultSet.next()){
                Exam exam = new Exam();
                exam.setExamID(exams_resultSet.getInt("ExamId"));
                System.out.println("Gia tri id la: " + exams_resultSet.getInt("ExamId"));

                exam.setName(exams_resultSet.getString("Name"));
                System.out.println("Name: " + exam.getName());
                exam.setDateStart(exams_resultSet.getTimestamp("DateStart").toLocalDateTime());
                System.out.println("Datestart: " + exam.getDateStart());

                exam.setDateEnd(exams_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                exam.setStatus(ExaminationStatus.valueOf(exams_resultSet.getString("Status")));
                System.out.println("status :" + exam.getStatus());
                exams.add(exam);
            }
            examDTO.setExams(exams);
            return examDTO;
        } catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllExam. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: Unable to close connection in function getAllExam. Details: " + e.getMessage());
            }
        }
    }

    public Exam getExamWithLessOfEqualThanTwoWeeks() {
        Connection connection = MysqlConfig.getConnection();
        Exam exam = new Exam();
        try {
            String getExam = "SELECT * FROM Exam WHERE DateStart >= CURRENT_DATE() AND DateStart <= DATE_ADD(CURRENT_DATE(),INTERVAL 3 WEEK) AND Status = 'NOT_STARTED' LIMIT 1";

            PreparedStatement exam_statement = connection.prepareStatement(getExam);

            ResultSet exam_resultSet = exam_statement.executeQuery();
            exam_resultSet.next();
            exam.setExamID(exam_resultSet.getInt("ExamID"));
            exam.setName(exam_resultSet.getString("Name"));
            exam.setDateStart(exam_resultSet.getTimestamp("DateStart").toLocalDateTime());
            exam.setDateEnd(exam_resultSet.getTimestamp("DateEnd").toLocalDateTime());
            exam.setStatus(ExaminationStatus.valueOf(exam_resultSet.getString("Status")));
            return exam;
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( getExamById ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kì thi", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( getExamById ) , Details : " + e.getMessage());
            }
        }
    }

    public Exam getExamById(int id) {
        Connection connection = MysqlConfig.getConnection();
        Exam exam = new Exam();
        try {
            String getExam = "SELECT * FROM Exam WHERE ExamID = ?";

            PreparedStatement exam_statement = connection.prepareStatement(getExam);

            exam_statement.setInt(1, id);

            ResultSet exam_resultSet = exam_statement.executeQuery();
            while (exam_resultSet.next()) {
                exam.setExamID(exam_resultSet.getInt("ExamID"));
                exam.setName(exam_resultSet.getString("Name"));
                exam.setDateStart(exam_resultSet.getTimestamp("DateStart").toLocalDateTime());
                exam.setDateEnd(exam_resultSet.getTimestamp("DateEnd").toLocalDateTime());
                exam.setStatus(ExaminationStatus.valueOf(exam_resultSet.getString("Status")));
            }
            return exam;
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( getExamById ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kì thi", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( getExamById ) , Details : " + e.getMessage());
            }
        }
    }

    public void createExam(Exam exam) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String createExam = "INSERT INTO Exam (Name, DateStart, DateEnd, Status) VALUES (?,?,?,?)";

            PreparedStatement create_exam_statement = connection.prepareStatement(createExam);

            create_exam_statement.setString(1, exam.getName());
            create_exam_statement.setTimestamp(2, Timestamp.valueOf(exam.getDateStart()));
            create_exam_statement.setTimestamp(3, Timestamp.valueOf(exam.getDateEnd()));
            create_exam_statement.setString(4, exam.getStatus().name());

            System.out.println("Cau lenh insert exam la: " + create_exam_statement);
            create_exam_statement.executeUpdate();
        } catch (Exception e) {
            System.err
                    .println("Error : query data unsuccessfully ,function ( createExam ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kì thi", "Không thể tạo kì thi")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( createExam ) , Details : " + e.getMessage());
            }
        }
    }

    public void updateExam(Exam exam) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateExam = "UPDATE Exam SET Name = ? , DateStart = ? , DateEnd = ? , Status = ? WHERE ExamID = ?";

            PreparedStatement update_exam_statement = connection.prepareStatement(updateExam);

            update_exam_statement.setString(1, exam.getName());
            update_exam_statement.setTimestamp(2, Timestamp.valueOf(exam.getDateStart()));
            update_exam_statement.setTimestamp(3, Timestamp.valueOf(exam.getDateEnd()));
            update_exam_statement.setString(4, String.valueOf(exam.getStatus()));
            update_exam_statement.setInt(5, exam.getExamID());
            System.out.println("cau lenh truy can update exam la: " + update_exam_statement);

            update_exam_statement.executeUpdate();
        } catch (Exception e) {
            System.err
                    .println("Error : query data unsuccessfully ,function ( updateExam ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kì thi", "Không thể cập nhật Kì thi")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( updateExam ) , Details : " + e.getMessage());
            }
        }
    }

    public void deleteExam(int id) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String deleteExamSql = "UPDATE exam SET Isdeleted = 1 WHERE examID = ?";

            PreparedStatement remove_role_statement = connection.prepareStatement(deleteExamSql);

            remove_role_statement.setInt(1, id);

            remove_role_statement.executeUpdate();

        } catch (Exception e) {
            System.err
                    .println("Error : query data unsuccessfully ,function ( deleteExam ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kif thi", "Không thể xóa kif thi")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( deleteExam ) , Details : " + e.getMessage());
            }
        }
    }

    public static String getExamNameByID(int examID) {
        String examName = null;
        String query = "SELECT Name FROM Exam WHERE ExamID = ?";

        try (Connection connection = MysqlConfig.getConnection();

                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, examID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                examName = resultSet.getString("Name");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return examName;
    }

    public String checkCurrentInTwoWeeks() {
        Connection connection = MysqlConfig.getConnection();
        try {
            String checkExam = "SELECT * FROM Exam WHERE Status = IN_PROGRESS LIMIT 1";

            PreparedStatement checkExamStatement = connection.prepareStatement(checkExam);
            ResultSet resultSet = checkExamStatement.executeQuery();
            if (resultSet.next()) {
                return "Không thể cập nhật độ khó câu hỏi khi đang có kì thi diễn ra!";
            } else {
                String checkExamIsTwoWeeks = "SELECT * FROM Exam WHERE DateEnd <= NOW() AND NOW() <= DATE_ADD(DateEnd, INTERVAL 2 WEEK) ORDER BY DateEnd ASC LIMIT 1";
                PreparedStatement checkExamIsTwoWeeksStatement = connection.prepareStatement(checkExamIsTwoWeeks);
                ResultSet resultSetIsTwoWeeks = checkExamStatement.executeQuery();
                if (resultSetIsTwoWeeks.next()) {
                    return null;
                } else {
                    return "Quá thời gian thực hiện thao tác! (Thời gian thực hiện cập nhật là trong 2 tuần sau khi kì thi kết thúc)";
                }
            }
        } catch (Exception e) {
            System.err
                    .println("Error : query data unsuccessfully ,function ( deleteExam ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kì thi", "Không thể xóa Kì thi")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( deleteExam ) , Details : " + e.getMessage());
            }
        }
    }
    public Exam findExamNearestCompleted() {
        Connection connection = MysqlConfig.getConnection();
        Exam exam = new Exam();
        try {
            String checkExam = "SELECT *\n" +
                    "FROM Exam\n" +
                    "WHERE DateEnd < CURRENT_TIMESTAMP\n" +
                    "AND Status = 'COMPLETED'\n" +
                    "ORDER BY DateEnd DESC\n" +
                    "LIMIT 1;";

            PreparedStatement checkExamStatement = connection.prepareStatement(checkExam);
            ResultSet resultSet = checkExamStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RetrieveException((Map.of("Kì thi", "Không thể xóa Kì thi")));
            } else {
                exam.setExamID(resultSet.getInt("ExamID"));
            }
            return exam;
        } catch (Exception e) {
            System.err
                    .println("Error : query data unsuccessfully ,function ( deleteExam ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kì thi", "Không thể xóa Kì thi")));
        }
    }
    
}