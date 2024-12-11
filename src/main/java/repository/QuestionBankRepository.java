package repository;

import DTO.ExamDTO;
import DTO.QuestionBankDTO;
import config.MysqlConfig;
import entity.*;
import entity.Enum.ExaminationStatus;
import entity.Enum.QuestionLevel;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionBankRepository {

    public QuestionBankDTO getAllQuestionBank() {
        Connection connection = MysqlConfig.getConnection();
        QuestionBankDTO questionBankDTO = new QuestionBankDTO();
        String sql = "SELECT " +
                "    q.QuestionID, " +
                "    q.Title AS QuestionTitle, " +
                "    q.EmployeeID, " +
                "    q.SubjectID, " +
                "    q.CorrectAnswer, " +
                "    q.Level, " +
                "    q.Point, " +
                "    o.OptionID, " +
                "    o.QuestionID AS OptionQuestionID, " +
                "    o.Title AS OptionTitle, " +
                "    s.SubjectID AS SubjectID, " +
                "    s.Name AS SubjectName, " +
                "    e.EmployeeID AS EmployeeID, " +
                "    e.FirstName, " +
                "    e.LastName " +
                "FROM " +
                "    Question q " +
                "    JOIN `Option` o ON q.QuestionID = o.QuestionID " +
                "    JOIN Subject s ON q.SubjectID = s.SubjectID " +
                "    JOIN Employee e ON q.EmployeeID = e.EmployeeID " +
                "WHERE " +
                "    q.Isdeleted = 0";

        List<Question> questions = new ArrayList<>();
        Map<Integer, Question> questionMap = new HashMap<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int questionID = resultSet.getInt("QuestionID");
                Question question;

                if (!questionMap.containsKey(questionID)) {
                    question = new Question();
                    question.setQuestionID(questionID);
                    question.setTitle(resultSet.getString("QuestionTitle"));
                    question.setPoint(resultSet.getDouble("Point"));
                    question.setEmployeeID(resultSet.getInt("EmployeeID"));
                    question.setSubjectID(resultSet.getShort("SubjectID"));
                    question.setCorrectAnswer(resultSet.getInt("CorrectAnswer"));
                    question.setLevel(QuestionLevel.valueOf(resultSet.getString("Level")));
                    Subject subject = new Subject();
                    subject.setName(resultSet.getString("SubjectName"));
                    subject.setSubjectID(resultSet.getShort("SubjectID"));
                    Employee employee = new Employee();
                    employee.setFirstName(resultSet.getString("FirstName"));
                    employee.setLastName(resultSet.getString("LastName"));
                    question.setSubject(subject);
                    question.setEmployee(employee);
                    question.setOptions(new ArrayList<>());

                    questionMap.put(questionID, question);
                    questions.add(question);
                } else {
                    question = questionMap.get(questionID);
                }

                Option option = new Option();
                option.setOptionID(resultSet.getInt("OptionID"));
                option.setTitle(resultSet.getString("OptionTitle"));
                question.getOptions().add(option);
            }
        } catch (SQLException e) {
            System.err.println("Error: Query data unsuccessfully, function (getAllQuestionBank), Details: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(
                        "Error: Unable to close connection, function (getAllQuestionBank), Details: " + e.getMessage());
            }
        }

        questionBankDTO.setQuestions(questions);

        return questionBankDTO;
    }

    public void createQuestion(Question question) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String createQuestion = "INSERT INTO question (title, point, employeeId, subjectId, correctAnswer, level) VALUES (?,?, ?, ?, ?, ?)";

            PreparedStatement create_question_statement = connection.prepareStatement(createQuestion);

            create_question_statement.setString(1, question.getTitle());
            create_question_statement.setDouble(2, question.getPoint());
            create_question_statement.setInt(3, question.getEmployeeID());
            create_question_statement.setShort(4, question.getSubjectID());

            create_question_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( createQuestion ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Question", "Không thể tạo question")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( createQuestion ) , Details : " + e.getMessage());
            }
        }
    }

    public void deleteQuestion(int id) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String deleteExamSql = "UPDATE question SET Isdeleted = 1 WHERE questionID = ?";

            PreparedStatement remove_role_statement = connection.prepareStatement(deleteExamSql);

            remove_role_statement.setInt(1, id);

            remove_role_statement.executeUpdate();

        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( deletequestion ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Kif thi", "Không thể xóa question")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( deleteExam ) , Details : " + e.getMessage());
            }
        }
    }

    public QuestionBankDTO getDataQuestionPieChart() {
        Connection connection = MysqlConfig.getConnection();
        QuestionBankDTO questionBankDTO = new QuestionBankDTO();
        String sql = "SELECT COUNT(Level) as Number, Level FROM `question` GROUP BY Level";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DataToDrawPieChart> dataToDrawPieCharts = new ArrayList<>();
            while (resultSet.next()) {
                DataToDrawPieChart dataToDrawPieChart = new DataToDrawPieChart();
                dataToDrawPieChart.setNumber(resultSet.getInt("Number"));
                dataToDrawPieChart.setLevel(QuestionLevel.valueOf(resultSet.getString("Level")));
                dataToDrawPieCharts.add(dataToDrawPieChart);
            }
            questionBankDTO.setDataToDrawPieCharts(dataToDrawPieCharts);
        } catch (SQLException e) {
            System.err.println(
                    "Error: Query data unsuccessfully, function (getDataQuestion), Details: " + e.getMessage());
            throw new RetrieveException((Map.of("Question", "Không thể tìm kiếm question")));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(
                        "Error: Unable to close connection, function (getDataQuestion), Details: " + e.getMessage());
            }
        }
        return questionBankDTO;
    }

    public QuestionBankDTO getQuestionById(int id) throws SQLException {
        QuestionBankDTO questionBankDTO = new QuestionBankDTO();
        Connection connection = MysqlConfig.getConnection();
        String sql = "SELECT " +
                "    q.QuestionID, " +
                "    q.Title AS QuestionTitle, " +
                "    q.EmployeeID, " +
                "    q.SubjectID, " +
                "    q.CorrectAnswer, " +
                "    q.Level, " +
                "    q.Point, " +
                "    o.OptionID, " +
                "    o.QuestionID AS OptionQuestionID, " +
                "    o.Title AS OptionTitle, " +
                "    s.SubjectID AS SubjectID, " +
                "    s.Name AS SubjectName, " +
                "    e.EmployeeID AS EmployeeID, " +
                "    e.FirstName, " +
                "    e.LastName " +
                "FROM " +
                "    Question q " +
                "    JOIN `Option` o ON q.QuestionID = o.QuestionID " +
                "    JOIN Subject s ON q.SubjectID = s.SubjectID " +
                "    JOIN Employee e ON q.EmployeeID = e.EmployeeID " +
                "WHERE " +
                "    q.Isdeleted = 0 and o.Isdeleted = 0 and q.QuestionID = ?";
        List<Question> questions = new ArrayList<>();
        Map<Integer, Question> questionMap = new HashMap<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int questionID = resultSet.getInt("QuestionID");
                Question question;

                if (!questionMap.containsKey(questionID)) {
                    question = new Question();
                    question.setQuestionID(questionID);
                    question.setTitle(resultSet.getString("QuestionTitle"));
                    question.setPoint(resultSet.getDouble("Point"));
                    question.setEmployeeID(resultSet.getInt("EmployeeID"));
                    question.setSubjectID(resultSet.getShort("SubjectID"));
                    question.setCorrectAnswer(resultSet.getInt("CorrectAnswer"));
                    question.setLevel(QuestionLevel.valueOf(resultSet.getString("Level")));
                    Subject subject = new Subject();
                    subject.setName(resultSet.getString("SubjectName"));
                    subject.setSubjectID(resultSet.getShort("SubjectID"));
                    Employee employee = new Employee();
                    employee.setFirstName(resultSet.getString("FirstName"));
                    employee.setLastName(resultSet.getString("LastName"));
                    question.setSubject(subject);
                    question.setEmployee(employee);
                    question.setOptions(new ArrayList<>());

                    questionMap.put(questionID, question);
                    questions.add(question);
                } else {
                    question = questionMap.get(questionID);
                }

                Option option = new Option();
                option.setOptionID(resultSet.getInt("OptionID"));
                option.setTitle(resultSet.getString("OptionTitle"));
                question.getOptions().add(option);
            }
        } catch (SQLException e) {
                    System.err.println("Error: Query data unsuccessfully, function (getQuestionBankById), Details: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(
                        "Error: Unable to close connection, function (getAllQuestionBank), Details: " + e.getMessage());
            }
        }
        questionBankDTO.setQuestions(questions);
        return questionBankDTO;
    }

    public void updateQuestion(Question question) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateQuestion = "UPDATE question SET title = ?, point = ?, employeeId = ?, subjectId = ?, correctAnswer = ?, level = ? WHERE questionID = ?";

            PreparedStatement update_question_statement = connection.prepareStatement(updateQuestion);

            update_question_statement.setString(1, question.getTitle());
            update_question_statement.setDouble(2, question.getPoint());
            update_question_statement.setInt(3, question.getEmployeeID());
            update_question_statement.setShort(4, question.getSubjectID());
            update_question_statement.setInt(5, question.getCorrectAnswer());
            update_question_statement.setString(6, question.getLevel().toString());
            update_question_statement.setInt(7, question.getQuestionID());

            update_question_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( updateQuestion ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Question", "Không thể cập nhật question")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( updateQuestion ) , Details : " + e.getMessage());
            }
        }
    }

    public int getIDQuestionMax() {
        int max = 0;
        try {
            String sql = "select max(questionid) from question";
            PreparedStatement preparedStatement = MysqlConfig.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            max =  resultSet.getInt(1);
            System.out.println("Max getIDQuestionMax repo: " + max);
            return max;
        }
        catch(Exception e) {
            System.out.println("Loi o ham getIDQuestionMax quesTionBAnk repo" + e);
        }
        return max;
    }
    public void updateCorrectAnswer(int correctAnswer, int questionID) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateQuestion = "UPDATE question SET correctAnswer = ? WHERE questionID = ?";

            PreparedStatement update_question_statement = connection.prepareStatement(updateQuestion);

            update_question_statement.setInt(1, correctAnswer);
            update_question_statement.setInt(2, questionID);

            update_question_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( updateQuestion ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Question", "Không thể cập nhật question")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( updateQuestion ) , Details : " + e.getMessage());
            }
        }
    }
    
}
