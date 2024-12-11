package repository;

import DTO.MajorDTO;
import config.MysqlConfig;
import entity.Major;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MajorRepository {
    public MajorDTO getAllMajorWithoutPaging(){
        Connection connection = MysqlConfig.getConnection();
        MajorDTO majorDTO = new MajorDTO();
        try {
            String getMajors_query = "SELECT * FROM Major where Isdeleted = 0";
            PreparedStatement majors_statement = connection.prepareStatement(getMajors_query);
            ResultSet majors_resultSet = majors_statement.executeQuery();

            List<Major> majors = new ArrayList<>();

            while (majors_resultSet.next()) {
                Major major = new Major();
                major.setMajorID(majors_resultSet.getShort("MajorID"));
                System.out.println(major.getMajorID());
                major.setName(majors_resultSet.getString("Name"));
                System.out.println((major.getName()));
                major.setDescription(majors_resultSet.getString("description"));
                System.out.println(major.getDescription());
                majors.add(major);
            }
            majorDTO.setMajors(majors);
        }catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllMajor. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: Unable to close connection in function getAllMajor. Details: " + e.getMessage());
            }
        }
        return majorDTO;
    }
    public MajorDTO getAllMajor(int page, int size, String search){
        Connection connection = MysqlConfig.getConnection();
        MajorDTO majorDTO = new MajorDTO();
        try {
            String total_pages = "SELECT COUNT(*) FROM Major ";
            String getMajors = "SELECT * FROM Major ";

            if(search!=null){
                total_pages += "WHERE Name = ? AND Isdeleted = 0";
                getMajors += "WHERE Name = ? AND Isdeleted = 0 ";
            }

            getMajors += "LIMIT ?,?";

            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);

            if(search!=null){
                total_pages_statement.setString(1,search);
            }

            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

            if(total_pages_resultSet.next()){
                majorDTO.setTotalPages(total_pages_resultSet.getInt(1)/size);
            }

            List<Major> majors = new ArrayList<>();

            PreparedStatement majors_statement = connection.prepareStatement(getMajors);

            if(search!=null){
                majors_statement.setString(1,search);
                majors_statement.setInt(2,(page-1)*size);
                majors_statement.setInt(3,size);
            } else {
                majors_statement.setInt(1,(page-1)*size);
                majors_statement.setInt(2,size);
            }

            ResultSet roles_resultSet = majors_statement.executeQuery();

            while(roles_resultSet.next()){
                Major major = new Major();
                major.setMajorID(roles_resultSet.getShort("MajorID")); // Changed from "RoleID" to "MajorID"
                major.setName(roles_resultSet.getString("Name"));
                major.setDescription(roles_resultSet.getString("description"));
                majors.add(major);
            }
            majorDTO.setMajors(majors);
            return majorDTO;
        } catch (SQLException e) {
            System.err.println("Error: Unable to query data in function getAllMajor. Details: " + e.getMessage());
            throw new RetrieveException(Map.of("Chuyên ngành", "Không thể tìm kiếm thông tin"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: Unable to close connection in function getAllMajor. Details: " + e.getMessage());
            }
        }
    }


    public Major getMajorById(short id){
        Connection connection = MysqlConfig.getConnection();
        Major major = new Major();
        try {
            String getMajor = "SELECT * FROM Major WHERE MajorID = ?";

            PreparedStatement major_statement = connection.prepareStatement(getMajor);

            major_statement.setShort(1,id);

            ResultSet resultSet = major_statement.executeQuery();

            if (resultSet.next()) { // Bỏ qua kiểm tra nếu bạn chắc chắn truy vấn sẽ trả về duy nhất một kết quả
                major.setMajorID(resultSet.getShort("MajorID"));
                System.out.println("ID Major lay dc la: " + major.getMajorID());
                major.setName(resultSet.getString("Name"));
                System.out.println("Name Major lay dc la: " + major.getName());
                major.setDescription(resultSet.getString("Description"));
                System.out.println("Des Major lay dc la: " + major.getDescription());
            } else {
                // Xử lý trường hợp không tìm thấy dữ liệu
                System.out.println("Không tìm thấy dữ liệu cho MajorID: " + id);
            }



            return major;
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( getMajorById ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Chuyên ngành","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getMajorById ) , Details : "+e.getMessage());
            }
        }
    }
    public void createMajor(Major major){
        Connection connection = MysqlConfig.getConnection();
        try {
            String createRole = "INSERT INTO Major (Name, Description) VALUES (?,?)";

            PreparedStatement create_major_statement = connection.prepareStatement(createRole);

            create_major_statement.setString(1,major.getName());
            create_major_statement.setString(2,major.getDescription());

            System.out.println("Cau lenh insert major la: " + create_major_statement);
            create_major_statement.executeUpdate();
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( createMajor ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Chuyên ngành","Không thể tạo chuyên ngành")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( createMajor ) , Details : "+e.getMessage());
            }
        }
    }
    public void updateMajor(Major major){
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateRole = "UPDATE Major SET Name = ? , Description = ? WHERE MajorID = ?";

            PreparedStatement update_major_statement = connection.prepareStatement(updateRole);

            update_major_statement.setString(1,major.getName());
            update_major_statement.setString(2,major.getDescription());
            update_major_statement.setShort(3,major.getMajorID());

            update_major_statement.executeUpdate();
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( updateMajor ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Chuyên ngành","Không thể cập nhật Chuyên ngành")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( updateMajor ) , Details : "+e.getMessage());
            }
        }
    }
    public void deleteMajor(short id){
        Connection connection = MysqlConfig.getConnection();
        try {
            String deleteMajorSql = "UPDATE Major SET Isdeleted = 1 WHERE MajorID = ?";

            PreparedStatement remove_role_statement = connection.prepareStatement(deleteMajorSql);

            remove_role_statement.setShort(1,id);

            remove_role_statement.executeUpdate();

        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( deleteMajor ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Chuyên ngành","Không thể xóa Chuyên ngành")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( deleteMajor ) , Details : "+e.getMessage());
            }
        }
    }

    public static String getMajorNameByID(int majorID) {
        String majorName = null;
        String query = "SELECT Name FROM Major WHERE MajorID = ?";

        try (Connection connection = MysqlConfig.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, majorID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                majorName = resultSet.getString("Name");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return majorName;
    }
}