package repository;

import DTO.InfoEmployeeAndMajorDTO;
import config.MysqlConfig;
import entity.Employee;
import entity.Major;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class InfoEmployeeRepository {
    public InfoEmployeeAndMajorDTO getInfoEmployeeAndMajorDTOByIDEmployee(int e_id) {
        Connection connection = MysqlConfig.getConnection();
        InfoEmployeeAndMajorDTO infoEmployeeAndMajorDTO = new InfoEmployeeAndMajorDTO();
        String sql = "select * from employee e, major m where e.majorID = m.majorID and e.employeeID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, e_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            Major major = new Major();
            while (resultSet.next()) {
                employee.setEmployeeID(resultSet.getShort("EmployeeID"));
                employee.setFirstName(resultSet.getString("FirstName"));
                employee.setLastName(resultSet.getString("LastName"));
                employee.setEmail(resultSet.getString("Email"));
                employee.setGender(resultSet.getBoolean("Gender"));
                employee.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                employee.setRoleID(resultSet.getShort("RoleID"));
                employee.setMajorID(resultSet.getShort("MajorID"));
                employee.setPhone(resultSet.getString("Phone"));
                employee.setMajorName(resultSet.getString("name"));
                major.setName(resultSet.getString("name"));
            }
            infoEmployeeAndMajorDTO.setEmployee(employee);
            infoEmployeeAndMajorDTO.setMajor(major);
        }catch (Exception e) {
            System.out.println("Loi ham InfoEmployeeRepository" + e);
        }
        return infoEmployeeAndMajorDTO;
    }
}
