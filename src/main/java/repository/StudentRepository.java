package repository;

import DTO.EmployeeDTO;
import DTO.StudentDTO;
import config.MysqlConfig;
import entity.Employee;
import entity.ResultStudent;
import entity.Result;
import entity.Student;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentRepository {
    private final Connection connection = MysqlConfig.getConnection();
    private static final Connection connectionKiet = MysqlConfig.getConnection();

    public StudentDTO getAllStudent(int page, int size, String search) {
        Connection connection = MysqlConfig.getConnection();
        StudentDTO studentDTO = new StudentDTO();
        try {
            String total_pages = "SELECT COUNT(*) FROM Student ";

            String getStudents = "SELECT * FROM Student ";

            if (search != null) {
                total_pages += "WHERE FirstName = ? or LastName = ? AND Isdeleted = 0";
                getStudents += "WHERE FirstName = ? or LastName = ? AND Isdeleted = 0";
            }

            String condition = "JOIN Major ON Major.MajorID = Student.MajorID LIMIT ?,?";
            getStudents += condition;

            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);
            if (search != null) {
                total_pages_statement.setString(1, search);
                total_pages_statement.setString(2, search);
            }
            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

            total_pages_resultSet.next();

            studentDTO.setTotalPages((int) Math.ceil(total_pages_resultSet.getInt(1) / size));

            List<Student> students = new ArrayList<>();

            PreparedStatement students_statement = connection.prepareStatement(getStudents);

            if (search != null) {
                students_statement.setString(1, search);
                students_statement.setString(2, search);
                students_statement.setInt(3, (page - 1) * size);
                students_statement.setInt(4, size);
            } else {
                students_statement.setInt(1, (page - 1) * size);
                students_statement.setInt(2, size);
            }

            ResultSet students_resultSet = students_statement.executeQuery();

            while (students_resultSet.next()) {
                Student student = new Student();
                student.setStudentID(students_resultSet.getInt("StudentID"));
                student.setFirstName(students_resultSet.getString("FirstName"));
                student.setLastName(students_resultSet.getString("LastName"));
                student.setEmail(students_resultSet.getString("Email"));
                student.setGender(students_resultSet.getBoolean("Gender"));
                student.setDateOfBirth(students_resultSet.getDate("DateOfBirth"));
                student.setMajorName(students_resultSet.getString("Name"));
                student.setPhone(students_resultSet.getString("Phone"));
                students.add(student);
            }

            studentDTO.setStudents(students);
            return studentDTO;
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( getAllStudent ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( getAllStudent ) , Details : " + e.getMessage());
            }
        }
    }
    // cua Kiet
    public StudentDTO getAllStudentForSettingInitResult() {
        Connection connection = MysqlConfig.getConnection();
        StudentDTO studentDTO = new StudentDTO();
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT s.StudentID, s.MajorID FROM Student s";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentID(resultSet.getInt("StudentID"));
                student.setMajorID(resultSet.getShort("MajorID"));
                students.add(student);
            }
            studentDTO.setStudents(students);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Lỗi đóng kết nối cơ sở dữ liệu: " + e.getMessage());
            }
        }
        return studentDTO;
    }
    // cua Truong Huu Nghia
    public Student getStudentById(int id) {
        Connection connection = MysqlConfig.getConnection();
        Student student = new Student();
        try {
            String getStudent = "SELECT Student.*, Major.* \n" +
                    "FROM Student \n" +
                    "JOIN Major ON Major.MajorID = Student.MajorID \n" +
                    "WHERE Student.StudentID = ?";

            PreparedStatement student_statement = connection.prepareStatement(getStudent);

            student_statement.setInt(1, id);

            ResultSet student_resultSet = student_statement.executeQuery();
            student_resultSet.next();
            student.setStudentID(student_resultSet.getInt("StudentID"));
            student.setFirstName(student_resultSet.getString("FirstName"));
            student.setLastName(student_resultSet.getString("LastName"));
            student.setEmail(student_resultSet.getString("Email"));
            student.setGender(student_resultSet.getBoolean("Gender"));
            student.setDateOfBirth(student_resultSet.getDate("DateOfBirth"));
            student.setMajorName(student_resultSet.getString("Name"));
            student.setPhone(student_resultSet.getString("Phone"));

            return student;
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( getStudentById ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( getStudentById ) , Details : " + e.getMessage());
            }
        }
    }
    // cua Truong Huu Nghia
    public void createStudent(Student student) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String createStudent = "INSERT INTO Student (FirstName, LastName, Phone, Gender, Email, DateOfBirth, MajorID, Password) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement create_student_statement = connection.prepareStatement(createStudent);

            create_student_statement.setString(1, student.getFirstName());
            create_student_statement.setString(2, student.getLastName());
            create_student_statement.setString(3, student.getPhone());
            create_student_statement.setBoolean(4, student.getGender());
            create_student_statement.setString(5, student.getEmail());
            create_student_statement.setDate(6, student.getDateOfBirth());
            create_student_statement.setShort(7, student.getMajorID());
            create_student_statement.setString(8, student.getPassword());

            create_student_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( createStudent ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Học sinh", "Không thể tạo Học sinh")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( createStudent ) , Details : " + e.getMessage());
            }
        }
    }
    // cua Truong Huu Nghia
    public void updateStudent(Student student) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateRole = "UPDATE Student SET FirstName = ?, LastName = ?, Phone = ?, Gender = ?, DateOfBirth = ?, MajorID = ? WHERE StudentID = ?";

            PreparedStatement update_employee_statement = connection.prepareStatement(updateRole);

            update_employee_statement.setString(1, student.getFirstName());
            update_employee_statement.setString(2, student.getLastName());
            update_employee_statement.setString(3, student.getPhone());
            update_employee_statement.setBoolean(4, student.getGender());
            update_employee_statement.setDate(5, student.getDateOfBirth());
            update_employee_statement.setShort(6, student.getMajorID());
            update_employee_statement.setInt(7, student.getStudentID());

            update_employee_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( updateStudent ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự", "Không thể cập nhật nhân sự")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( updateStudent ) , Details : " + e.getMessage());
            }
        }
    }
    // cua Truong Huu Nghia
    public void deleteStudent(int id) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateRole = "UPDATE Student SET Isdeleted = 1 WHERE StudentID = ?";

            PreparedStatement remove_role_statement = connection.prepareStatement(updateRole);

            remove_role_statement.setInt(1, id);

            remove_role_statement.executeUpdate();

        } catch (Exception e) {
            System.err.println(
                    "Error : query data unsuccessfully ,function ( deleteStudent ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự", "Không thể xóa nhân sự")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( deleteStudent ) , Details : " + e.getMessage());
            }
        }
    }
// cua mai tran tuan kiet
    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentID(resultSet.getInt("StudentID"));
        student.setFirstName(resultSet.getString("FirstName"));
        student.setLastName(resultSet.getString("LastName"));
        student.setGender(resultSet.getBoolean("Gender"));
        student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
        student.setPhone(resultSet.getString("Phone"));
        student.setEmail(resultSet.getString("Email"));
        student.setMajorID(resultSet.getShort("MajorID"));

        return student;
    }
// cua kiet
    public StudentDTO getStudents(int page, int size, String majorID, String search) {
        StudentDTO studentDTO = new StudentDTO();

        StringBuilder getNumPages = new StringBuilder("select count(*) from student ");
        StringBuilder getStudents = new StringBuilder("select * from student ");

        int index = 1;

        if (majorID != null && !majorID.isEmpty()) {
            getNumPages.append("join major on student.majorid = major.majorid where major.majorid = ? ");
            getStudents.append("join major on student.majorid = major.majorid where major.majorid = ? ");
            index++;
        }

        if (search != null && !search.isEmpty()) {
            if (getStudents.toString().contains("where")) {
                getNumPages
                        .append("and (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
                getStudents
                        .append("and (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
            } else {
                getNumPages.append(
                        "where (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
                getStudents.append(
                        "where (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
            }
            index += 3;
        }

        getStudents.append("limit ?, ?");
        index += 2;

        try (PreparedStatement getNumPagesStm = connection.prepareStatement(getNumPages.toString());
                PreparedStatement getStudentsStm = connection.prepareStatement(getStudents.toString())) {

            index = 1;
            if (majorID != null && !majorID.isEmpty()) {
                getNumPagesStm.setString(index, majorID);
                getStudentsStm.setString(index++, majorID);
            }

            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search.toLowerCase() + "%";
                getNumPagesStm.setString(index, searchParam);
                getStudentsStm.setString(index++, searchParam);
                getNumPagesStm.setString(index, searchParam);
                getStudentsStm.setString(index++, searchParam);
                getNumPagesStm.setString(index, searchParam);
                getStudentsStm.setString(index++, searchParam);
            }

            getStudentsStm.setInt(index++, (page - 1) * size);
            getStudentsStm.setInt(index, size);

            try (ResultSet getNumPagesRs = getNumPagesStm.executeQuery();
                    ResultSet getStudentsRs = getStudentsStm.executeQuery()) {

                if (getNumPagesRs.next()) {
                    studentDTO.setTotalPages((int) Math.ceil((double) getNumPagesRs
                            .getInt(1) / size));
                }

                List<Student> students = new ArrayList<>();
                while (getStudentsRs.next()) {
                    students.add(createStudentFromResultSet(getStudentsRs));
                }

                studentDTO.setStudents(students);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return studentDTO;
    }

    public StudentDTO getAllStudents(int page, int size) {
        return getStudents(page, size, null, null);
    }
// cua nghia
    public StudentDTO getAllStudentWithoutPagination() {
        Connection connection = MysqlConfig.getConnection();
        StudentDTO studentDTO = new StudentDTO();
        try {

            String getStudents = "SELECT * FROM Student JOIN Major ON Major.MajorID = Student.MajorID WHERE Student.Isdeleted = 0";

            List<Student> students = new ArrayList<>();

            PreparedStatement students_statement = connection.prepareStatement(getStudents);

            ResultSet students_resultSet = students_statement.executeQuery();

            while (students_resultSet.next()) {
                Student student = new Student();
                student.setStudentID(students_resultSet.getInt("StudentID"));
                student.setFirstName(students_resultSet.getString("FirstName"));
                student.setLastName(students_resultSet.getString("LastName"));
                student.setEmail(students_resultSet.getString("Email"));
                student.setGender(students_resultSet.getBoolean("Gender"));
                student.setMajorID(students_resultSet.getShort("Student.MajorID"));
                student.setDateOfBirth(students_resultSet.getDate("DateOfBirth"));
                student.setMajorName(students_resultSet.getString("Major.Name"));
                student.setPhone(students_resultSet.getString("Phone"));
                students.add(student);
            }

            studentDTO.setStudents(students);
            return studentDTO;
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getAllStudentWithout ), Details : "
                    + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( getAllStudent ) , Details : " + e.getMessage());
            }
        }
    }

    public StudentDTO getFilteredStudents(int page, int size,
            String majorID, String search) {
        return getStudents(page, size, majorID, search);
    }

    public StudentDTO getNumberStudent() throws SQLException {
        StudentDTO studentDTO = new StudentDTO();
        String sql = "select Count(*) as number from student where isDeleted = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int numberStudent = resultSet.getInt("number");
            studentDTO.setNumberStudent(numberStudent);
        }
        return studentDTO;
    }

    public List<ResultStudent> getResultStudentFormId(short id) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql = "SELECT subject.Name as TenMonHoc ,Major.name TenChuyenNganh,Exam.Name as TenKyThi,result.TotalPoint from student join result on student.StudentID = result.StudentID join test on result.TestID = test.TestID join exam on test.examID = exam.examID join subject on subject.SubjectID = test.SubjectID join Major on Major.MajorID = student.MajorID  where Student.studentID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            List<ResultStudent> ListResult = new ArrayList<>();
            while (result.next()) {
                ResultStudent resultStudent = new ResultStudent();
                resultStudent.setExamName(result.getString("TenKyThi"));
                resultStudent.setMajorName(result.getString("TenChuyenNganh"));
                resultStudent.setSubjectName(result.getString("TenMonHoc"));
                resultStudent.setPoint(result.getDouble("TotalPoint"));
                ListResult.add(resultStudent);
            }
            return ListResult;

        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getResultStudentFormId ), Details : "
                    + e.getMessage());
            throw new RetrieveException((Map.of("Học sinh", "Không thể tạo Học sinh")));
        }
    }

    public int checkEmailHasAlready(String Email) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql = "SELECT COUNT(*) as count FROM student, employee WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "Email");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( checkEmailHasAlready ), Details : "
                    + e.getMessage());
            throw new RetrieveException((Map.of("Email", "Không thể tạo Email")));
        }
        return 0;
    }

    public int checkEmailAndPassword(String Email, String Password) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql1 = "SELECT * FROM student WHERE Email = ? AND Isdeleted = 0 LIMIT 1";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, Email);
            ResultSet resultSet1 = statement1.executeQuery();
            if (resultSet1.next()) {
                String sql2 = "SELECT * FROM student WHERE Email = ? AND Password = ? LIMIT 1";
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                statement2.setString(1, Email);
                statement2.setString(2, Password);
                ResultSet resultSet2 = statement2.executeQuery();
                if (resultSet2.next()) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( checkEmailAndPassword ), Details : "
                    + e.getMessage());
            throw new RetrieveException((Map.of("Email", "Không thể tạo Email")));
        }
    }

    public Student getStudentByEmailAndPassword(String email, String password) {
        Connection connection = MysqlConfig.getConnection();
        Student student = new Student();
        try {
            String sql1 = "SELECT * FROM student s JOIN major m ON m.MajorID = s.MajorID WHERE s.Email = ? AND s.Password = ? LIMIT 1";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, email);
            statement1.setString(2, password);
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();
            student.setMajorID(resultSet1.getShort("MajorID"));
            student.setStudentID(resultSet1.getInt("StudentID"));
            student.setEmail(resultSet1.getString("Email"));
            student.setFirstName(resultSet1.getString("FirstName"));
            student.setPhone(resultSet1.getString("Phone"));
            student.setLastName(resultSet1.getString("LastName"));
            student.setGender(resultSet1.getBoolean("Gender"));
            student.setDateOfBirth(resultSet1.getDate("DateOfBirth"));
            student.setMajorName(resultSet1.getString("Name"));
        } catch (Exception e) {
            System.err
                    .println("Error : query data unsuccessfully ,function ( getStudentByEmailAndPassword ), Details : "
                            + e.getMessage());
            throw new RetrieveException((Map.of("Email", "Không thể tạo Email")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println(
                        "Error : can't close connection ,function ( getAllStudent ) , Details : " + e.getMessage());
            }
        }
        return student;
    }

    public static List<Result> getResultsByStudentIdKiet(int studentId) {
        List<Result> results = new ArrayList<>();
        String sql = "select * from `result` where studentid = ?";

        try (PreparedStatement stm = connectionKiet.prepareStatement(sql)) {
            stm.setInt(1, studentId);

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

    static Result createResultFromResultSetKiet(ResultSet rs) throws SQLException {
        Result result = new Result();

        result.setResultID(rs.getInt("ResultID"));
        result.setStudentID(rs.getInt("StudentID"));
        result.setTestID(rs.getInt("TestID"));
        result.setTotalCorrect(rs.getByte("TotalCorrect"));
        result.setTotalIncorrect(rs.getByte("TotalIncorrect"));
        result.setTotalPoint(rs.getInt("TotalPoint"));

        return result;
    }

    public static StudentDTO getAllStudentsKiet(int page, int size, String majorID) {
        return getStudentsKiet(page, size, majorID, null);
    }

    public static StudentDTO getFilteredStudentsKiet(int page, int size,
            String majorID, String search) {
        return getStudentsKiet(page, size, majorID, search);
    }

    private static Student createStudentFromResultSetKiet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentID(resultSet.getInt("StudentID"));
        student.setFirstName(resultSet.getString("FirstName"));
        student.setLastName(resultSet.getString("LastName"));
        student.setGender(resultSet.getBoolean("Gender"));
        student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
        student.setPhone(resultSet.getString("Phone"));
        student.setEmail(resultSet.getString("Email"));
        student.setMajorID(resultSet.getShort("MajorID"));

        return student;
    }

    public static StudentDTO getStudentsKiet(int page, int size, String majorID, String search) {
        StudentDTO studentDTO = new StudentDTO();

        StringBuilder getNumPages = new StringBuilder("select count(*) from student ");
        StringBuilder getStudents = new StringBuilder("select * from student ");

        int index = 1;

        if (majorID != null && !majorID.isEmpty()) {
            getNumPages.append("join major on student.majorid = major.majorid where major.majorid = ? ");
            getStudents.append("join major on student.majorid = major.majorid where major.majorid = ? ");
            index++;
        }

        if (search != null && !search.isEmpty()) {
            if (getStudents.toString().contains("where")) {
                getNumPages
                        .append("and (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
                getStudents
                        .append("and (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
            } else {
                getNumPages.append(
                        "where (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
                getStudents.append(
                        "where (LOWER(studentid) like ? or LOWER(FirstName) like ? or LOWER(LastName) like ?) ");
            }
            index += 3;
        }

        getStudents.append("limit ?, ?");
        index += 2;

        try (PreparedStatement getNumPagesStm = connectionKiet.prepareStatement(getNumPages.toString());
                PreparedStatement getStudentsStm = connectionKiet.prepareStatement(getStudents.toString())) {

            index = 1;
            if (majorID != null && !majorID.isEmpty()) {
                getNumPagesStm.setString(index, majorID);
                getStudentsStm.setString(index++, majorID);
            }

            if (search != null && !search.isEmpty()) {
                String searchParam = "%" + search.toLowerCase() + "%";
                getNumPagesStm.setString(index, searchParam);
                getStudentsStm.setString(index++, searchParam);
                getNumPagesStm.setString(index, searchParam);
                getStudentsStm.setString(index++, searchParam);
                getNumPagesStm.setString(index, searchParam);
                getStudentsStm.setString(index++, searchParam);
            }

            getStudentsStm.setInt(index++, (page - 1) * size);
            getStudentsStm.setInt(index, size);

            try (ResultSet getNumPagesRs = getNumPagesStm.executeQuery();
                    ResultSet getStudentsRs = getStudentsStm.executeQuery()) {

                if (getNumPagesRs.next()) {
                    studentDTO.setTotalPages((int) Math.ceil((double) getNumPagesRs
                            .getInt(1) / size));
                }

                List<Student> students = new ArrayList<>();
                while (getStudentsRs.next()) {
                    students.add(createStudentFromResultSetKiet(getStudentsRs));
                }

                studentDTO.setStudents(students);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return studentDTO;
    }

    public static Student getStudentByIdKiet(short id) {
        String sql = "select * from student where studentid = ?";

        try (PreparedStatement stm = connectionKiet.prepareStatement(sql)) {
            stm.setShort(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return createStudentFromResultSetKiet(rs);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static void createStudentKiet(Student student) {
        String sql = "insert into student(FirstName, LastName) values(?, ?)";

        try (PreparedStatement stm = connectionKiet.prepareStatement(sql)) {
            stm.setString(1, student.getFirstName());
            stm.setString(2, student.getLastName());

            stm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateStudentKiet(Student student) {
        String sql = "update student set FirstName = ?, LastName = ? where studentid = ?";

        try (PreparedStatement stm = connectionKiet.prepareStatement(sql)) {
            stm.setString(1, student.getFirstName());
            stm.setString(2, student.getLastName());
            stm.setInt(3, student.getStudentID());

            stm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteStudentKiet(short id) {
        String sql = "delete from student where studentid = ?";

        try (PreparedStatement stm = connectionKiet.prepareStatement(sql)) {
            stm.setShort(1, id);

            stm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static StudentDTO getNumberStudentKiet() throws SQLException {
        StudentDTO studentDTO = new StudentDTO();
        String sql = "select Count(*) as number from student where isDeleted = 0";
        PreparedStatement preparedStatement = connectionKiet.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int numberStudent = resultSet.getInt("number");
            studentDTO.setNumberStudent(numberStudent);
        }
        return studentDTO;
    }
}
