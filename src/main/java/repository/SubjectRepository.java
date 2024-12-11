package repository;
import DTO.SpectrumDTO;
import DTO.RoleDTO;
import DTO.SubjectDTO;
import config.MysqlConfig;
import entity.Role;
import entity.Subject;
import exception.RetrieveException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

public class SubjectRepository {
    public SubjectDTO getAllSubject(int page, int size, String search) {
        Connection connection = MysqlConfig.getConnection();
        SubjectDTO subjectDTO = new SubjectDTO();
        try {
            String total_pages = "SELECT COUNT(*) FROM Subject ";

            String getSubjects = "SELECT Subject.* , Major.MajorName AS MajorName FROM Subject LIMIT ?,? JOIN Major ON Major.MajorID = Subject.MajorID";

            if (search != null) {
                total_pages += "WHERE Name = ? AND Isdeleted = 0";
                getSubjects += "WHERE Name = ? AND Isdeleted = 0";
            }
            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);

            total_pages_statement.setString(1, search);

            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

            subjectDTO.setTotalPages(total_pages_resultSet.getInt(1) / size);

            List<Subject> subjects = new ArrayList<>();


            PreparedStatement subjects_statement = connection.prepareStatement(getSubjects);

            subjects_statement.setInt(1, (page - 1) * size);
            subjects_statement.setInt(2, size);
            subjects_statement.setString(3, search);

            ResultSet subjects_resultSet = subjects_statement.executeQuery();

            while (subjects_resultSet.next()) {
                Subject subject = new Subject();
                subject.setSubjectID(subjects_resultSet.getShort("SubjectID"));
                subject.setMajorID(subjects_resultSet.getShort("MajorID"));
                subject.setMajorName(subjects_resultSet.getString("MajorName"));
                subject.setName(subjects_resultSet.getString("Name"));
                subject.setDescription(subjects_resultSet.getString("Description"));
                subjects.add(subject);
            }

            subjectDTO.setSubjects(subjects);
            return subjectDTO;
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getAllSubject ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error : can't close connection ,function ( getAllSubject ) , Details : " + e.getMessage());
            }
        }
    }

    public SubjectDTO getAllSubjectWithoutPagination() {
        String query = "SELECT Subject.*, Major.Name AS MajorName FROM Subject JOIN Major ON Major.MajorID = Subject.MajorID Where Subject.Isdeleted = 0 ";
        List<Subject> subjects = new ArrayList<>();

        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectID(rs.getShort("SubjectID"));
                subject.setMajorID(rs.getShort("MajorID"));
                subject.setMajorName(rs.getString("MajorName"));
                subject.setName(rs.getString("Name"));
                subject.setDescription(rs.getString("Description"));
                subjects.add(subject);
            }

        } catch (Exception e) {
            System.err.println("Error: query data unsuccessfully, function (getAllSubjectWithoutPagination), Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Error", "Unable to retrieve data"));
        }

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjects(subjects);
        subjectDTO.setTotalPages(1);  // This may not be relevant since pagination is not used
        return subjectDTO;
    }

    public static Subject getSubjectByID(int subjectID) {
        Subject subject = new Subject();
        String sql = "SELECT * FROM Subject WHERE SubjectID = ?";

        try (PreparedStatement stm = new MysqlConfig().getConnection().prepareStatement(sql)) {
            stm.setInt(1, subjectID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                subject.setSubjectID(rs.getShort("SubjectID"));
                subject.setName(rs.getString("Name"));
                subject.setDescription(rs.getString("Description"));
                subject.setMajorID(rs.getShort("MajorID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể tìm kiếm môn học")));
        }

        return subject;
    }

    public Subject getSubjectById(short id) {
        String query = "SELECT DISTINCT Subject.*, Major.Name AS MajorName FROM Subject JOIN Major ON Major.MajorID = Subject.MajorID WHERE SubjectID = ?";
        try (Connection connection = MysqlConfig.getConnection();
             PreparedStatement subjectStatement = connection.prepareStatement(query)) {

            subjectStatement.setShort(1, id);
            try (ResultSet resultSet = subjectStatement.executeQuery()) {
                if (resultSet.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectID(resultSet.getShort("SubjectID"));
                    subject.setName(resultSet.getString("Name"));
                    subject.setMajorName(resultSet.getString("MajorName"));
                    subject.setDescription(resultSet.getString("Description"));
                    return subject;
                } else {
                    return null; // hoặc ném ngoại lệ nếu môn học không tồn tại
                }
            }
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully, function (getSubjectById), Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Môn học", "Không thể tìm kiếm thông tin môn học"));
        }
    }

    public void createSubject(Subject subject) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String createSubject = "INSERT INTO Subject (Name, Description, MajorID) VALUES (?,?,?)";

            PreparedStatement create_subject_statement = connection.prepareStatement(createSubject);

            create_subject_statement.setString(1, subject.getName());
            create_subject_statement.setString(2, subject.getDescription());
            create_subject_statement.setShort(3, subject.getMajorID());

            create_subject_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( createRole ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Môn học", "Không thể tạo môn học")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error : can't close connection ,function ( createRole ) , Details : " + e.getMessage());
            }
        }
    }

    public void updateSubject(Subject subject) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateRole = "UPDATE Subject SET Name = ? , Description = ? , MajorID = ? WHERE SubjectID = ?";

            PreparedStatement update_subject_statement = connection.prepareStatement(updateRole);

            update_subject_statement.setString(1, subject.getName());
            update_subject_statement.setString(2, subject.getDescription());
            update_subject_statement.setShort(3, subject.getMajorID());
            update_subject_statement.setInt(4, subject.getSubjectID());

            update_subject_statement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( updateSubject ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Môn học", "Không thể cập nhật môn học")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error : can't close connection ,function ( updateSubject ) , Details : " + e.getMessage());
            }
        }
    }

    public void deleteSubject(short id) {
        Connection connection = MysqlConfig.getConnection();
        try {
//             Remove role
            String deleteRole = "UPDATE Subject SET Isdeleted = 1 WHERE SubjectID = ?";

            PreparedStatement remove_subject_statement = connection.prepareStatement(deleteRole);
            remove_subject_statement.setShort(1, id);
            remove_subject_statement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( updateRole ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể xóa quyền")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error : can't close connection ,function ( updateRole ) , Details : " + e.getMessage());
            }
        }
    }

    public SubjectDTO getAllNameSubject() {
        SubjectDTO subjectDTO = new SubjectDTO();
        Connection connection = MysqlConfig.getConnection();
        String sql = "select name, subjectID from subject where isdeleted = 0";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Subject> listSubjects = new ArrayList<>();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setName(resultSet.getString("Name"));
                subject.setSubjectID(resultSet.getShort("subjectID"));
                listSubjects.add(subject);
            }
            subjectDTO.setSubjects(listSubjects);
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getAllNameSubject ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error : can't close connection ,function ( getAllNameSubject ) , Details : " + e.getMessage());
            }
        }
        return subjectDTO;
    }

    public static String getSubjectNameByID(int subjectID) {
        String subjectName = null;
        String query = "SELECT Name FROM Subject WHERE SubjectID = ?";

        try (Connection connection = MysqlConfig.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, subjectID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    subjectName = resultSet.getString("Name");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subjectName;
    }

    public static SpectrumDTO getSpectrum(short id) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String query = "SELECT \n" +
                    "    Subject.name AS tenMonHoc,\n" +
                    "    exam.name AS tenkythi,\n" +
                    "    AVG(result.totalPoint) AS diemtrungbinh\n" +
                    "FROM \n" +
                    "    test \n" +
                    "JOIN \n" +
                    "    exam ON exam.examid = test.examid \n" +
                    "JOIN \n" +
                    "    subject ON test.subjectid = subject.subjectid \n" +
                    "JOIN \n" +
                    "    result ON test.testid = result.testid \n" +
                    "WHERE \n" +
                    "    exam.examid = ? \n" +
                    "GROUP BY \n" +
                    "    Subject.name, exam.name";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setShort(1, id);
            ResultSet result = statement.executeQuery();
            SpectrumDTO spectrumDTO = new SpectrumDTO();
            List<String> SubjectNameList = new ArrayList<>();
            List<Double> PointList = new ArrayList<>();
            while(result.next()){
                String Subjectname = result.getString("tenMonHoc");
                Double point = result.getDouble("diemtrungbinh");
                System.out.println("tenmonhocla "+Subjectname);
                System.out.println("pointla "+ point);
                SubjectNameList.add(Subjectname);
                PointList.add(point);

            }
            spectrumDTO.setSubject(SubjectNameList);
            spectrumDTO.setPoint(PointList);
            return spectrumDTO;
        }catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getSpectrum ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể xóa quyền")));
        }

    }


    /////////////////////////////// KIET ///////////////////////////////
    public static SubjectDTO getAllSubjects(int page, int size, String search, short majorID) {
        Connection connection = MysqlConfig.getConnection();
        SubjectDTO subjectDTO = new SubjectDTO();
        try {
            String total_pages = "SELECT COUNT(*) FROM Subject JOIN Major ON Major.MajorID = Subject.MajorID WHERE Major.MajorID = ?";
            String getSubjects = "SELECT Subject.* , Major.Name AS MajorName FROM Subject JOIN Major ON Major.MajorID = Subject.MajorID WHERE Major.MajorID = ?";

            if (search != null) {
                total_pages += " AND Subject.Name = ? AND Subject.Isdeleted = 0";
                getSubjects += " AND Subject.Name = ? AND Subject.Isdeleted = 0";
            }

            getSubjects += " LIMIT ?, ?";

            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);
            total_pages_statement.setShort(1, majorID);

            if (search != null) {
                total_pages_statement.setString(2, search);
            }

            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

            if (total_pages_resultSet.next()) {
                subjectDTO.setTotalPages(total_pages_resultSet.getInt(1) / size);
            }

            List<Subject> subjects = new ArrayList<>();

            PreparedStatement subjects_statement = connection.prepareStatement(getSubjects);
            subjects_statement.setShort(1, majorID);

            if (search != null) {
                subjects_statement.setString(2, search);
                subjects_statement.setInt(3, (page - 1) * size);
                subjects_statement.setInt(4, size);
            } else {
                subjects_statement.setInt(2, (page - 1) * size);
                subjects_statement.setInt(3, size);
            }

            ResultSet subjects_resultSet = subjects_statement.executeQuery();

            while (subjects_resultSet.next()) {
                Subject subject = new Subject();
                subject.setSubjectID(subjects_resultSet.getShort("SubjectID"));
                subject.setMajorID(subjects_resultSet.getShort("MajorID"));
                subject.setMajorName(subjects_resultSet.getString("MajorName"));
                subject.setName(subjects_resultSet.getString("Name"));
                subject.setDescription(subjects_resultSet.getString("Description"));
                subjects.add(subject);
            }

            subjectDTO.setSubjects(subjects);
            return subjectDTO;
        } catch (Exception e) {
            e.getStackTrace();
            System.err.println("Error: query data unsuccessfully ,function ( getAllSubject ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ", "Không thể tìm kiếm thông tin")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.getStackTrace();
                System.err.println("Error: can't close connection, function ( getAllSubject ) , " +
                        "Details : " + e.getMessage());
            }
        }
    }
}
