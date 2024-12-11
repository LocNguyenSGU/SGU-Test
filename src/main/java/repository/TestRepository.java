package repository;

import DTO.ResultDTO;
import DTO.TestDTO;
import config.MysqlConfig;
import entity.*;
import entity.Enum.ExaminationStatus;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static repository.StudentRepository.createResultFromResultSetKiet;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 04/04/2024 3:39 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */
public class TestRepository {
    private static final Connection connection = MysqlConfig.getConnection();

    public TestDTO getAllTests(int page, int size, String search, short majorID) {
        // TODO: Implement getAllTests
        return getTests(page, size, null, search, majorID);
    }

    public TestDTO getFilteredTests(int page, int size, String examId, String search, short majorID) {
        // TODO: Implement getFilteredTests
        return getTests(page, size, examId, search, majorID);
    }

    private TestDTO getTests(int page, int size, String examId, String search, short majorID) {
        TestDTO testDTO = new TestDTO();

        String getNumPages = "SELECT COUNT(*) FROM test join subject on test.subjectid = subject" +
                ".subjectid WHERE test.isDeleted = false and subject.majorid = " + majorID + " ";
        String getTests = "SELECT * FROM test join subject on test.subjectid = subject" +
                ".subjectid WHERE test.isDeleted = false and subject.majorid = " + majorID + " ";

        if (examId != null && !examId.isEmpty()) {
            getNumPages += "AND exam.examID = ? ";
            getTests += "AND exam.examID = ? ";
        }

        if (search != null && !search.isEmpty()) {
            getNumPages += "AND TestID LIKE ?";
            getTests += "AND TestID LIKE ?";
        }

        getTests += "LIMIT ?,?";

        try (PreparedStatement getNumPagesStatement = connection.prepareStatement(getNumPages);
                PreparedStatement getTestsStatement = connection.prepareStatement(getTests)) {

            int index = 1;
            if (examId != null && !examId.isEmpty()) {
                getNumPagesStatement.setString(index, examId);
                getTestsStatement.setString(index++, examId);
            }

            if (search != null && !search.isEmpty()) {
                getNumPagesStatement.setString(index, "%" + search + "%");
                getTestsStatement.setString(index++, "%" + search + "%");
            }

            getTestsStatement.setInt(index++, (page - 1) * size);
            getTestsStatement.setInt(index, size);

            try (ResultSet getNumPagesResultSet = getNumPagesStatement.executeQuery();
                    ResultSet getTestsResultSet = getTestsStatement.executeQuery()) {

                if (getNumPagesResultSet.next()) {
                    testDTO.setTotalPages((int) Math.ceil((double) getNumPagesResultSet.getInt(1) / size));
                }

                List<Test> tests = new ArrayList<>();
                while (getTestsResultSet.next()) {
                    tests.add(createTestFromResultSet(getTestsResultSet));
                }
                testDTO.setTests(tests);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return testDTO;
    }

    private static Test createTestFromResultSet(ResultSet resultSet) throws SQLException {
        // TODO: Implement createTestFromResultSet
        Test test = new Test();
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
        return test;
    }

    public TestDTO getDataToDrawBarChart() {
        TestDTO testDTO = new TestDTO();
        String sql = "select COUNT(*) as num, DATE(DateStart) as dateStart from test Where isdeleted = 0 GROUP BY DATE(DateStart)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DataToDrawBarChart> dataToDrawBarCharts = new ArrayList<>();
            while (resultSet.next()) {
                DataToDrawBarChart dataToDrawBarChart = new DataToDrawBarChart();
                dataToDrawBarChart.setNumber(resultSet.getInt("num"));
                System.out.println("number =BarChart: " + dataToDrawBarChart.getNumber());
                dataToDrawBarChart.setDateStart(resultSet.getDate("dateStart").toLocalDate());
                System.out.println("dateStartBarChart: " + dataToDrawBarChart.getDateStart());
                dataToDrawBarCharts.add(dataToDrawBarChart);
            }
            testDTO.setDataToDrawBarCharts(dataToDrawBarCharts);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return testDTO;
    }

    public TestDTO getNumberTest() throws SQLException {
        TestDTO testDTO = new TestDTO();
        String sql = "select Count(*) as number from test where isDeleted = 0 and fromTestID is null";
        System.out.println("cau lenh truy van la: " + sql);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int numberTest = resultSet.getInt("number");
            testDTO.setNumberTest(numberTest);
        }
        System.out.println("So luong test trong respositry" + testDTO.getNumberTest());
        return testDTO;
    }

    public ResultDTO getListResultOfStudentByTest(int testID) {
        ResultDTO resultDTO = new ResultDTO();
        // String sql = "select s.FirstName as firstName, s.lastName as lastName, s.studentID as studentID, r.totalPoint as totalPoint, r.studentID as idStudentInResult from test t, student s, result r where t.testID = ? and r.studentID = s.studentID and t.testID = r.testID";
        String sql = "SELECT s.FirstName AS firstName, s.LastName AS lastName, s.StudentID AS studentID, r.TotalPoint AS totalPoint, r.StudentID AS idStudentInResult FROM Result r JOIN Student s ON r.StudentID = s.StudentID JOIN Test t ON t.TestID = r.TestID WHERE t.fromTestID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, testID);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Student> studentList = new ArrayList<>();
            List<Result> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentID(resultSet.getInt("studentID"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setLastName(resultSet.getString("lastName"));
                studentList.add(student);
                Result result = new Result();
                result.setTotalPoint(resultSet.getDouble("totalPoint"));
                result.setStudentID(resultSet.getInt("idStudentInResult"));
                resultList.add(result);
            }
            resultDTO.setStudentList(studentList);
            resultDTO.setResultList(resultList);
        } catch (Exception e) {
            System.out.println("loi ham getListResultOfStudentByTest repository");
            System.out.println(e);
        }
        return resultDTO;
    }

    public TestDTO getTestAndQuestionInTestByIdTest(int testID) {
        TestDTO testDTO = new TestDTO();
        String sql = "SELECT q.title AS q_title, q.correctAnswer AS idCorrect, q.backupQuestionID as q_idBackup, q.point as q_point, q.backupQuestionId, o.title AS o_title, t.testID, t.examID, t.FromTestID, t.subjectId, t.employeeID, t.description, t.numberOfQuestion, t.totalPoint, t.duration, t.DateStart, t.DateEnd, t.Status, t.description "
                +
                "FROM test t " +
                "JOIN testDetails td ON t.testId = td.testId " +
                "JOIN backupQuestion q ON td.backupQuestionId = q.backupQuestionId " +
                "JOIN backupOption o ON q.backupQuestionId = o.backupQuestionId " +
                "WHERE t.testId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, testID);
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, Question> questionMap = new HashMap<>();
            Test test = new Test();
            int flag = 1;
            while (resultSet.next()) {
                if (flag == 1) {
                    test.setTestID(resultSet.getInt("testId"));
                    test.setExamID(resultSet.getInt("examId"));
                    System.out.println("flag = 1: " + test.getExamID());
                    test.setSubjectID(resultSet.getShort("subjectID"));
                    test.setEmployeeID(resultSet.getInt("employeeId"));
                    test.setDescription(resultSet.getString("description"));
                    test.setTotalPoint(resultSet.getDouble("totalPoint"));
                    test.setNumberOfQuestion(resultSet.getInt("numberOfQuestion"));
                    test.setDuration(resultSet.getString("Duration"));
                    test.setDateStart(resultSet.getTimestamp("DateStart").toLocalDateTime());
                    test.setDateEnd(resultSet.getTimestamp("DateEnd").toLocalDateTime());
                    test.setStatus(ExaminationStatus.valueOf(resultSet.getString("Status")));
                    flag = 0;
                    // In ra các giá trị của các trường trong đối tượng Test
                    System.out.println("TestID: " + test.getTestID());
                    System.out.println("SubjectID: " + test.getSubjectID());
                    System.out.println("EmployeeID: " + test.getEmployeeID());
                    System.out.println("TotalPoint: " + test.getTotalPoint());
                    System.out.println("Duration: " + test.getDuration());
                    System.out.println("DateStart: " + test.getDateStart());
                    System.out.println("DateEnd: " + test.getDateEnd());
                    System.out.println("Status: " + test.getStatus());
                }
                int questionID = resultSet.getInt("backupQuestionId");
                Question question;
                if (!questionMap.containsKey(questionID)) {
                    question = new Question();
                    question.setTitle(resultSet.getString("q_title"));
                    question.setQuestionIdBackup(resultSet.getInt("q_idBackup"));
                    question.setCorrectAnswer(resultSet.getInt("idCorrect"));
                    question.setPoint(resultSet.getDouble("q_point"));
                    question.setQuestionID(questionID);
                    question.setOptions(new ArrayList<>());
                    questionMap.put(questionID, question);
                } else {
                    question = questionMap.get(questionID);
                }
                Option option = new Option();
                option.setTitle(resultSet.getString("o_title"));
                option.setQuestionID(questionID);
                question.getOptions().add(option);
            }

            List<Question> questionList = new ArrayList<>(questionMap.values());
            testDTO.setQuestionList(questionList);
            testDTO.setTest(test);
        } catch (Exception e) {
            System.out.println("Loi ham getTestAndQuestionInTestByIdTest repository");
            System.out.println(e);
        }
        return testDTO;
    }

    public void insertTestToTest(Test test, int fromTestID) {
        String sql = "insert into test (examId, subjectId, employeeId, numberOfQuestion, totalPoint, duration, dateStart, dateEnd, status, description, fromTestId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, test.getExamID());
            preparedStatement.setInt(2, test.getSubjectID());
            preparedStatement.setInt(3, test.getEmployeeID());
            preparedStatement.setInt(4, test.getNumberOfQuestion());
            preparedStatement.setDouble(5, test.getTotalPoint());
            preparedStatement.setString(6, test.getDuration());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(test.getDateStart()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(test.getDateEnd()));
            preparedStatement.setString(9, String.valueOf(test.getStatus()));
            preparedStatement.setString(10, test.getDescription());
            preparedStatement.setInt(11, fromTestID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Loi ham insertTestToTest repository");
            System.out.println(e);
        }
    }

    public int getIDTestMax() throws SQLException {
        String sql = "select max(testId)  from test";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        int max = 0;
        while (resultSet.next()) {
            max = resultSet.getInt("max(testId)");
        }
        return max;
    }

    public static Test getTestByID(String testID) {
        // TODO: Implement getTestByID

        // Check if testID is not null and not empty
        if (testID == null || testID.isEmpty()) {
            throw new IllegalArgumentException("Test ID cannot be null or empty");
        }

        String getTestQuery = "SELECT * FROM test WHERE testID = ?";

        try (PreparedStatement getTestStatement = connection.prepareStatement(getTestQuery)) {
            getTestStatement.setInt(1, Integer.parseInt(testID));

            try (ResultSet getTestResultSet = getTestStatement.executeQuery()) {
                if (getTestResultSet.next()) {
                    return createTestFromResultSet(getTestResultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Test not found");
    }

    public static int createTest(int subjectID, int examID, String testDescription,
            String testDuration, String testStartTime, String testEndTime,
            String testStatus, int numberOfQuestions, double totalPoint) {

        // TODO: Implement createTest
        String createTestQuery = "INSERT INTO test (SubjectID, ExamID, Description, Duration, " +
                "DateStart, DateEnd, Status, NumberOfQuestion, TotalPoint) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement createTestStatement = connection.prepareStatement(createTestQuery,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Parse the ISO 8601 datetime strings
            ZonedDateTime startDateTime = ZonedDateTime.parse(testStartTime).plusHours(7);
            ZonedDateTime endDateTime = ZonedDateTime.parse(testEndTime).plusHours(7);

            // Format the datetimes in the MySQL datetime format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String mysqlFormattedStartDatetime = startDateTime.format(formatter);
            String mysqlFormattedEndDatetime = endDateTime.format(formatter);

            // Set the parameters
            createTestStatement.setInt(1, subjectID);
            createTestStatement.setInt(2, examID);
            createTestStatement.setString(3, testDescription);
            createTestStatement.setString(4, testDuration);
            createTestStatement.setString(5, mysqlFormattedStartDatetime);
            createTestStatement.setString(6, mysqlFormattedEndDatetime);
            createTestStatement.setString(7, testStatus);
            createTestStatement.setInt(8, numberOfQuestions);
            createTestStatement.setDouble(9, totalPoint);

            // Execute the query
            createTestStatement.executeUpdate();
            updateFromTestId();

            // return testID
            try (ResultSet resultSet = createTestStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public static boolean createTestDetails(int testID, int backupQuestionID) {
        // TODO: Implement createTestDetails

        String createTestDetailsQuery = "INSERT INTO testdetails (TestID, BackupQuestionID) VALUES (?, ?)";

        try (PreparedStatement createTestDetailsStm = connection.prepareStatement(createTestDetailsQuery)) {
            createTestDetailsStm.setInt(1, testID);
            createTestDetailsStm.setInt(2, backupQuestionID);

            return createTestDetailsStm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Result> getResultsByTestID(String testID) {
        List<Result> results = new ArrayList<>();
        String sql = "select * from `result` where testid = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, Integer.parseInt(testID));

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    results.add(createResultFromResultSetKiet(rs));
                }
            } catch (Exception e) {
                e.getStackTrace();
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.getStackTrace();
            throw new RuntimeException();
        }

        return results;
    }
    public static int getIDTestMax_2() throws SQLException {
        String sql = "select max(testId)  from test";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        int max = 0;
        while (resultSet.next()) {
            max = resultSet.getInt("max(testId)");
        }
        return max;
    }
    public static void updateFromTestId() {
        try {
            int testId = getIDTestMax_2();
            String sql = "update test set fromTestId = ? where testId = ?";
            PreparedStatement preparedStatement = MysqlConfig.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, testId);
            preparedStatement.setInt(2, testId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Loi o ham updateFromTestId test repoTest" + e);
        }
    }
}
