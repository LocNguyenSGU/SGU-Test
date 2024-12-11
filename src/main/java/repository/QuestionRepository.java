package repository;

import DTO.QuestionDTO;
import config.MysqlConfig;
import entity.Enum.QuestionLevel;
import entity.Question;
import exception.RetrieveException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpSession;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class QuestionRepository {
    private static final Connection connection = MysqlConfig.getConnection();

    public static boolean deleteQuestion(String questionID) {
        // Simply set isDeleted to true
        String setDeletedQuery = "UPDATE question SET isDeleted = true WHERE questionid = ?";

        try (PreparedStatement setDeletedStm = connection.prepareStatement(setDeletedQuery)) {
            setDeletedStm.setInt(1, Integer.parseInt(questionID));

            return setDeletedStm.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static QuestionDTO getAllQuestions(int page, int size, int majorID) {
        return getQuestions(page, size, null, null, null, majorID);
    }

    public static QuestionDTO getFilteredQuestions(int page, int size, String subjectID,
            String level, String search, int majorID) {
        return getQuestions(page, size, subjectID, level, search, majorID);
    }

    public static QuestionDTO getQuestions(int page, int size, String subjectID, String level,
            String search, int majorID) {
        QuestionDTO questionDTO = new QuestionDTO();

        String getQuestionsQuery = "SELECT question.* FROM question JOIN subject ON " +
                "question.subjectid = subject.subjectid WHERE question.isDeleted = false AND " +
                "subject.majorid = " + majorID + " ";
        String countQuestionsQuery = "SELECT COUNT(*) FROM question JOIN subject ON " +
                "question.subjectid = subject.subjectid WHERE question.isDeleted = false AND " +
                "subject.majorid = " + majorID + " ";

        List<Object> parameters = new ArrayList<>();
        parameters.add(majorID); // Ensure majorID is not null

        if (subjectID != null && !subjectID.isEmpty()) {
            getQuestionsQuery += "AND question.subjectid = ? ";
            countQuestionsQuery += "AND question.subjectid = ? ";
            parameters.add(subjectID);
        }

        if (level != null && !level.isEmpty()) {
            getQuestionsQuery += "AND question.level = ? ";
            countQuestionsQuery += "AND question.level = ? ";
            parameters.add(QuestionLevel.valueOf(level).toString());
        }

        if (search != null && !search.isEmpty()) {
            getQuestionsQuery += "AND (question.title LIKE ? OR question.questionid LIKE ?) ";
            countQuestionsQuery += "AND (question.title LIKE ? OR question.questionid LIKE ?) ";
            parameters.add("%" + search + "%");
            parameters.add("%" + search + "%");
        }

        getQuestionsQuery += "LIMIT ?, ? ";
        parameters.add((page - 1) * size);
        parameters.add(size);


        try (PreparedStatement countQstStm = connection.prepareStatement(countQuestionsQuery);
                PreparedStatement getQstStm = connection.prepareStatement(getQuestionsQuery)) {
            int index = 1;

            if (subjectID != null && !subjectID.isEmpty()) {
                countQstStm.setString(index, subjectID);
                getQstStm.setString(index++, subjectID); // Ensure subjectID is not null
            }

            if (level != null && !level.isEmpty()) {
                countQstStm.setString(index, QuestionLevel.valueOf(level).toString());
                getQstStm.setString(index++, QuestionLevel.valueOf(level).toString()); // Ensure level is not null
            }

            if (search != null && !search.isEmpty()) {
                countQstStm.setString(index, "%" + search + "%");
                getQstStm.setString(index++, "%" + search + "%");
                countQstStm.setString(index, "%" + search + "%");
                getQstStm.setString(index++, "%" + search + "%"); // Ensure search is not null
            }

            getQstStm.setInt(index++, (page - 1) * size);
            getQstStm.setInt(index, size); // Ensure page and size are not null

            try (ResultSet countQstRs = countQstStm.executeQuery();
                    ResultSet getQstRs = getQstStm.executeQuery()) {

                if (countQstRs.next()) {
                    questionDTO.setTotalQuestions(
                            (int) Math.ceil((double) countQstRs.getInt(1) / size));
                }

                List<Question> questions = new ArrayList<>();
                while (getQstRs.next()) {
                    questions.add(createQuestionFromResultSet(getQstRs));
                }

                questionDTO.setQuestions(questions);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return questionDTO;
    }

    private static Question createQuestionFromResultSet(ResultSet resultSet) throws SQLException {
        Question question = new Question();

        question.setQuestionID(resultSet.getInt("QuestionID"));
        question.setTitle(resultSet.getString("Title"));
        question.setSubjectID(resultSet.getShort("SubjectID"));
        question.setLevel(QuestionLevel.valueOf(resultSet.getString("Level")));
        question.setPoint(resultSet.getDouble("Point"));
        question.setCorrectAnswer(resultSet.getInt("CorrectAnswer"));
        question.setEmployeeID(resultSet.getInt("EmployeeID"));

        return question;
    }

    public static Question getQuestionByID(String questionID) {
        String getQuestionQuery = "SELECT * FROM question WHERE questionid = ?";

        try (PreparedStatement getQstStm = connection.prepareStatement(getQuestionQuery)) {
            getQstStm.setInt(1, Integer.parseInt(questionID));

            try (ResultSet getQstRs = getQstStm.executeQuery()) {
                if (getQstRs.next()) {
                    return createQuestionFromResultSet(getQstRs);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RetrieveException(Map.of("Câu hỏi", "Không tìm thấy câu hỏi"));
    }

    public static boolean updateQuestion(String questionID, String title, String subjectID, String level,
            double point, int correctAnswer) {
        String updateQuestionQuery = "UPDATE question SET " +
                "title = ?, subjectid = ?, level = ?, point = ?, correctanswer = ? WHERE questionid = ?";

        try (PreparedStatement updateQstStm = connection.prepareStatement(updateQuestionQuery)) {
            updateQstStm.setString(1, title);
            updateQstStm.setInt(2, Integer.parseInt(subjectID));
            updateQstStm.setString(3, level);
            updateQstStm.setDouble(4, point);
            updateQstStm.setInt(5, correctAnswer);
            updateQstStm.setInt(6, Integer.parseInt(questionID));

            updateQstStm.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int createQuestion(String title, int subjectID, String level, double point) {
        String createQuestionQuery = "INSERT INTO question (title, subjectid, level, point) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement createQstStm = connection.prepareStatement(createQuestionQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            createQstStm.setString(1, title);
            createQstStm.setInt(2, subjectID);
            createQstStm.setString(3, level);
            createQstStm.setDouble(4, point);

            createQstStm.executeUpdate();

            // return the id of the created question
            try (ResultSet rs = createQstStm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public static QuestionDTO getQuestionsByTestID(String testID) {
        QuestionDTO questionDTO = new QuestionDTO();

        String checkTestIDExistsQuery = "SELECT COUNT(*) FROM testdetails WHERE testid = ?";
        String getBackupQuestionsQuery = "SELECT backupquestionid FROM testdetails WHERE testid = ?";
        String countBackupQuestionsQuery = "SELECT COUNT(*) FROM testdetails WHERE testid = ?";

        try (PreparedStatement checkTestIDExistsStm = connection.prepareStatement(checkTestIDExistsQuery);
                PreparedStatement getBackupQstStm = connection.prepareStatement(getBackupQuestionsQuery);
                PreparedStatement countBackupQstStm = connection.prepareStatement(countBackupQuestionsQuery)) {

            checkTestIDExistsStm.setInt(1, Integer.parseInt(testID));
            try (ResultSet checkTestIDExistsRs = checkTestIDExistsStm.executeQuery()) {
                if (!checkTestIDExistsRs.next() || checkTestIDExistsRs.getInt(1) == 0) {
                    throw new RetrieveException(Map.of("Bài thi", "Không tồn tại bài thi này"));
                }
            }

            countBackupQstStm.setInt(1, Integer.parseInt(testID));
            getBackupQstStm.setInt(1, Integer.parseInt(testID));

            try (ResultSet countBackupQstRs = countBackupQstStm.executeQuery();
                    ResultSet getBackupQstRs = getBackupQstStm.executeQuery()) {

                if (countBackupQstRs.next()) {
                    questionDTO.setTotalQuestions(countBackupQstRs.getInt(1));
                }

                List<Question> questions = new ArrayList<>();
                while (getBackupQstRs.next()) {
                    questions.add(getBackupQuestionByID(
                            String.valueOf(getBackupQstRs.getInt("backupquestionid"))));
                }

                questionDTO.setQuestions(questions);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return questionDTO;
    }

    public static QuestionDTO getQuestionsBySubjectId(int subjectId) {
        QuestionDTO questionDTO = new QuestionDTO();

        String getQuestionsQuery = "SELECT * FROM question WHERE subjectid = ?";
        String countQuestionsQuery = "SELECT COUNT(*) FROM question WHERE subjectid = ?";

        try (PreparedStatement countQstStm = connection.prepareStatement(countQuestionsQuery);
                PreparedStatement getQstStm = connection.prepareStatement(getQuestionsQuery)) {

            countQstStm.setInt(1, subjectId);
            getQstStm.setInt(1, subjectId);

            try (ResultSet countQstRs = countQstStm.executeQuery();
                    ResultSet getQstRs = getQstStm.executeQuery()) {

                if (countQstRs.next()) {
                    questionDTO.setTotalQuestions(countQstRs.getInt(1));
                }

                List<Question> questions = new ArrayList<>();
                while (getQstRs.next()) {
                    questions.add(createQuestionFromResultSet(getQstRs));
                }

                questionDTO.setQuestions(questions);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return questionDTO;
    }

    public static Question getBackupQuestionByID(String backupQuestion) {
        String getBackupQuestionQuery = "SELECT * FROM backupquestion WHERE backupquestionid = ?";
        String getOriginalQuestionQuery = "SELECT * FROM question WHERE questionid = ?";

        try (PreparedStatement getBackupQstStm = connection.prepareStatement(getBackupQuestionQuery);
                PreparedStatement getOriginalQstStm = connection.prepareStatement(getOriginalQuestionQuery)) {

            getBackupQstStm.setInt(1, Integer.parseInt(backupQuestion));

            try (ResultSet getBackupQstRs = getBackupQstStm.executeQuery()) {
                if (getBackupQstRs.next()) {
                    int originalQuestionID = getBackupQstRs.getInt("questionid");
                    String title = getBackupQstRs.getString("title");
                    double point = getBackupQstRs.getDouble("point");

                    getOriginalQstStm.setInt(1, originalQuestionID);
                    try (ResultSet getOriginalQstRs = getOriginalQstStm.executeQuery()) {
                        if (getOriginalQstRs.next()) {
                            short subjectID = getOriginalQstRs.getShort("subjectid");
                            String level = getOriginalQstRs.getString("level");
                            int correctAnswer = getOriginalQstRs.getInt("correctanswer");
                            int employeeID = getOriginalQstRs.getInt("employeeid");

                            Question question = new Question();
                            question.setQuestionID(originalQuestionID);
                            question.setTitle(title);
                            question.setSubjectID(subjectID);
                            question.setLevel(QuestionLevel.valueOf(level));
                            question.setPoint(point);
                            question.setCorrectAnswer(correctAnswer);
                            question.setEmployeeID(employeeID);

                            return question;
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RetrieveException(Map.of("Câu hỏi", "Không tìm thấy câu hỏi"));
    }

    public static int createBackup(String questionID, double point, String title) {
        String createBackupQuery = "INSERT INTO backupquestion (questionid, point, title) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement createBackupStm = connection.prepareStatement(createBackupQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            createBackupStm.setInt(1, Integer.parseInt(questionID));
            createBackupStm.setDouble(2, point);
            createBackupStm.setString(3, title);

            createBackupStm.executeUpdate();

            try (ResultSet rs = createBackupStm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public static boolean updateBackup(int backupQuestionID, int correctAnswer) {
        String updateBackupQuery = "UPDATE backupquestion SET correctanswer = ? WHERE backupquestionid = ?";

        try (PreparedStatement updateBackupStm = connection.prepareStatement(updateBackupQuery)) {
            updateBackupStm.setInt(1, correctAnswer);
            updateBackupStm.setInt(2, backupQuestionID);

            return updateBackupStm.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createQuestion(Question question) throws SQLException {
        try {
            String sql = "insert into question (title, point, employeeId, subjectId, correctAnswer, level) values(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, question.getTitle());
            preparedStatement.setDouble(2, question.getPoint());
            preparedStatement.setInt(3, question.getEmployeeID());
            preparedStatement.setInt(4, question.getSubjectID());
            preparedStatement.setInt(5, question.getCorrectAnswer());
            preparedStatement.setString(6, String.valueOf(question.getLevel()));
//            preparedStatement.setInt(7, question.getQuestionID());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean isCellColored(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        FillPatternType fillPatternType = cellStyle.getFillPattern();
        return fillPatternType == FillPatternType.SOLID_FOREGROUND;
    }
    public void createQuestionExcel(String excelFilePath, int employeeId) throws IOException, SQLException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assume the data is in the first sheet

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip the header row

        String sqlInsertQuestion = "insert into question (title, employeeId, subjectId, level) values (?, ?, ?, ?)";
        String sqlInsertOption = "insert into option (questionid, title) values (?, ?)";
        String sqlUpdateidCorrectAnswer = "UPDATE question SET correctAnswer = ? WHERE questionid = ?";

        PreparedStatement preparedStatementQuestion = connection.prepareStatement(sqlInsertQuestion,
                Statement.RETURN_GENERATED_KEYS);
        PreparedStatement preparedStatementOption = connection.prepareStatement(sqlInsertOption,
                Statement.RETURN_GENERATED_KEYS);
        PreparedStatement preparedStatementUpdateCorrectAnswer = connection.prepareStatement(sqlUpdateidCorrectAnswer);
        int count = 0;
        int batchSize = 1;

        while (rowIterator.hasNext()) {
            System.out.println("Vo vong while");
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Iterator<Cell> cellIteratorForOption = row.cellIterator();

            // Get information for creating question
            String title = cellIterator.next().getStringCellValue();
            System.out.println("title: " + title);
            int subjectId = (int) cellIterator.next().getNumericCellValue();
            System.out.println("subjectID: " + subjectId);
            String level = cellIterator.next().getStringCellValue();
            System.out.println("Level: " + level);

            // Set parameters for question PreparedStatement
            preparedStatementQuestion.setString(1, title);
            preparedStatementQuestion.setInt(2, employeeId);
            preparedStatementQuestion.setInt(3, subjectId);
            preparedStatementQuestion.setString(4, level);
            System.out.println("prepare: " + preparedStatementQuestion);
            preparedStatementQuestion.addBatch();

            // Execute batch if necessary
            if (++count % batchSize == 0) {
                System.out.println("Vo neu");
                int[] updateCounts = preparedStatementQuestion.executeBatch();
                try (ResultSet generatedKeys = preparedStatementQuestion.getGeneratedKeys()) {
                    while (generatedKeys.next()) {
                        int questionId = generatedKeys.getInt(1);
                        System.out.println("id cua cau hoi vua them la: " + questionId);

                        // Move cellIterator to column 5
                        for (int i = 0; i < 3; i++) {
                            cellIteratorForOption.next();
                        }
                        // Insert options for this question
                        while (cellIteratorForOption.hasNext()) {
                            Cell cell = cellIteratorForOption.next();
                            preparedStatementOption.setInt(1, questionId);
                            String optionTitle = null;
                            if (cell.getCellType() == CellType.NUMERIC) {
                                optionTitle = String.valueOf(cell.getNumericCellValue());
                            } else {
                                optionTitle = cell.getStringCellValue();
                            }
                            if (optionTitle != null && !optionTitle.trim().isEmpty()) { // Kiểm tra nếu optionTitle
                                // không rỗng
                                System.out.println("Option title: " + optionTitle);
                                preparedStatementOption.setString(2, optionTitle);
                                preparedStatementOption.addBatch();
                            }
                            preparedStatementOption.executeBatch(); // Execute batch for options
                            try (ResultSet generatedKeysOption = preparedStatementOption.getGeneratedKeys()) {
                                if (generatedKeysOption.next()) {
                                    int optionId = generatedKeysOption.getInt(1);
                                    System.out.println("id cua option vua them la: " + optionId);

                                    // If the cell is colored, update the correct answer for the question
                                    if (isCellColored(cell)) {
                                        System.out.println("id cua option co to mau la: " + optionId);
                                        preparedStatementUpdateCorrectAnswer.setInt(1, optionId);
                                        preparedStatementUpdateCorrectAnswer.setInt(2, questionId);
                                        preparedStatementUpdateCorrectAnswer.executeUpdate();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("K in dc id option " + e);
                            }
                        }
                    }
                }
            } else {
                System.out.println("Khong du 20 cau hoi");
            }
        }

        // Execute remaining batch
        int[] updateCounts = preparedStatementQuestion.executeBatch();

        // Close resources
        workbook.close();
        File file = new File(excelFilePath);
        file.delete();
    }

    public void updateLevelQuestion(int QuestionID,int Percentage){
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateQuestions = "UPDATE Question SET Level = ? WHERE QuestionID = ?";
            PreparedStatement questions_statement = connection.prepareStatement(updateQuestions);
            if(Percentage <= 30){
                questions_statement.setString(1,"HARD");
            }else if (Percentage >= 80){
                questions_statement.setString(1,"EASY");
            }else{
                questions_statement.setString(1,"MEDIUM");
            }
            questions_statement.setInt(2,QuestionID);
            questions_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( updateLevelQuestion ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( updateLevelQuestion ) , Details : "+e.getMessage());
            }
        }
    }
}
