package repository;

import DTO.EmployeeDTO;
import config.MysqlConfig;
import entity.Employee;
import entity.Student;
import exception.RetrieveException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {
    public EmployeeDTO getAllEmployee(int page, int size, String search){
        Connection connection = MysqlConfig.getConnection();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        try {
            String total_pages = "SELECT COUNT(*) FROM Employee ";

            String getEmployees = "SELECT * FROM Employee LIMIT ?,? ";

            if(search!=null){
                total_pages += "WHERE FirstName = ? or LastName = ? AND Isdeleted = 0";
                getEmployees += "WHERE FirstName = ? or LastName = ? AND Isdeleted = 0";
            }
            PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);

            total_pages_statement.setString(1,search);
            total_pages_statement.setString(2,search);

            ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

//            employeeDTO.setTotalPages(Integer.parseInt(Math.ceil(total_pages_resultSet.getInt(1)/size)));

            List<Employee> employees = new ArrayList<>();


            PreparedStatement employees_statement = connection.prepareStatement(getEmployees);

            employees_statement.setInt(1,(page-1)*size);
            employees_statement.setInt(2,size);
            employees_statement.setString(3,search);
            employees_statement.setString(4,search);

            ResultSet employees_resultSet = employees_statement.executeQuery();

            while(employees_resultSet.next()){
                Employee employee = new Employee();
                employee.setEmployeeID(employees_resultSet.getShort("RoleID"));
                employee.setFirstName(employees_resultSet.getString("FirstName"));
                employee.setLastName(employees_resultSet.getString("LastName"));
                employee.setEmail(employees_resultSet.getString("Email"));
                employee.setGender(employees_resultSet.getBoolean("Gender"));
                employee.setDateOfBirth(employees_resultSet.getDate("DateOfBirth"));
                employee.setRoleID(employees_resultSet.getShort("RoleID"));
                employee.setPhone(employees_resultSet.getString("Phone"));
                employees.add(employee);
            }

            employeeDTO.setEmployees(employees);
            return employeeDTO;
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( getAllEmployee ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getAllEmployee ) , Details : "+e.getMessage());
            }
        }
    }
    public EmployeeDTO getAllEmployeeWithoutPagination(){
        Connection connection = MysqlConfig.getConnection();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        try {
            String getEmployees = "SELECT * FROM Employee Join Role on Role.roleid = Employee.roleid join Major on Major.majorId = employee.majorid where Employee.isdeleted=0 order by employeeID";

//            employeeDTO.setTotalPages(Integer.parseInt(Math.ceil(total_pages_resultSet.getInt(1)/size)));
            List<Employee> employees = new ArrayList<>();
            PreparedStatement employees_statement = connection.prepareStatement(getEmployees);
            ResultSet employees_resultSet = employees_statement.executeQuery();

            while(employees_resultSet.next()){
                Employee employee = new Employee();
                employee.setEmployeeID(employees_resultSet.getShort("EmployeeID"));
                employee.setFirstName(employees_resultSet.getString("FirstName"));
                employee.setLastName(employees_resultSet.getString("LastName"));
                employee.setEmail(employees_resultSet.getString("Email"));
                employee.setGender(employees_resultSet.getBoolean("Gender"));
                employee.setDateOfBirth(employees_resultSet.getDate("DateOfBirth"));
                employee.setRoleID(employees_resultSet.getShort("RoleID"));
                employee.setMajorID(employees_resultSet.getShort("MajorID"));
                employee.setPhone(employees_resultSet.getString("Phone"));
                employee.setRoleName(employees_resultSet.getString("Role.name"));
                employee.setMajorName(employees_resultSet.getString("Major.name"));
                employees.add(employee);
            }

            employeeDTO.setEmployees(employees);
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( getAllEmployee ), Details : "+e.getMessage());
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getAllEmployee ) , Details : "+e.getMessage());
            }
        }
        return employeeDTO;
    }
    public Employee getEmployeeById(int id){
        Connection connection = MysqlConfig.getConnection();
        Employee employee = new Employee();
        try {
            String getEmployee = "SELECT * FROM Employee WHERE EmployeeID = ?";

            PreparedStatement employee_statement = connection.prepareStatement(getEmployee);

            employee_statement.setInt(1,id);

            ResultSet employee_resultSet = employee_statement.executeQuery();
            if(employee_resultSet.next()) {
                employee.setEmployeeID(employee_resultSet.getInt("EmployeeID"));
                employee.setEmail(employee_resultSet.getString("Email"));
                System.out.println("email la "+employee_resultSet.getString("Email"));
                employee.setGender(employee_resultSet.getBoolean("Gender"));
                employee.setMajorID(employee_resultSet.getShort("MajorID"));
                System.out.println("major trong repository la "+employee_resultSet.getShort("MajorID"));
                employee.setFirstName(employee_resultSet.getString("FirstName"));
                employee.setLastName(employee_resultSet.getString("LastName"));
                employee.setRoleID(employee_resultSet.getShort("RoleID"));
                employee.setDateOfBirth(employee_resultSet.getDate("DateOfBirth"));
                employee.setPhone(employee_resultSet.getString("Phone"));
            }
            return employee;
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( getEmployeeById ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getEmployeeById ) , Details : "+e.getMessage());
            }
        }
    }
    public void createEmployee(Employee employee){
        Connection connection = MysqlConfig.getConnection();
        try {
            String createEmployee = "INSERT INTO Employee (FirstName, LastName, Phone, Gender, Email, DateOfBirth, MajorID, RoleID, Password) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement create_employee_statement = connection.prepareStatement(createEmployee);

            create_employee_statement.setString(1,employee.getFirstName());
            create_employee_statement.setString(2,employee.getLastName());
            create_employee_statement.setString(3,employee.getPhone());
            create_employee_statement.setBoolean(4,employee.getGender());
            create_employee_statement.setString(5,employee.getEmail());
            create_employee_statement.setDate(6,employee.getDateOfBirth());
            create_employee_statement.setShort(7,employee.getMajorID());
            create_employee_statement.setShort(8,employee.getRoleID());
            create_employee_statement.setString(9,employee.getPassword());

            create_employee_statement.executeUpdate();
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( createEmployee ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự","Không thể tạo chuyên ngành")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( createEmployee ) , Details : "+e.getMessage());
            }
        }
    }
    public void updateEmployee(Employee employee){
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateRole = "UPDATE Employee set FirstName = ?, LastName = ?, Phone = ?, Gender = ?, DateOfBirth = ?, MajorID = ?, RoleID = ? WHERE EmployeeID = ?";

            PreparedStatement update_employee_statement = connection.prepareStatement(updateRole);

            update_employee_statement.setString(1,employee.getFirstName());
            update_employee_statement.setString(2,employee.getLastName());
            update_employee_statement.setString(3,employee.getPhone());
            update_employee_statement.setBoolean(4,employee.getGender());
            update_employee_statement.setDate(5,employee.getDateOfBirth());
            update_employee_statement.setShort(6,employee.getMajorID());
            update_employee_statement.setShort(7,employee.getRoleID());
            update_employee_statement.setInt(8,employee.getEmployeeID());

            update_employee_statement.executeUpdate();
        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( updateEmployee ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự","Không thể cập nhật nhân sự")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( updateEmployee ) , Details : "+e.getMessage());
            }
        }
    }
    public void deleteEmployee(int id){
        Connection connection = MysqlConfig.getConnection();
        try {
            String updateRole = "UPDATE Employee SET Isdeleted = 1 WHERE EmployeeID = ?";

            PreparedStatement remove_role_statement = connection.prepareStatement(updateRole);

            remove_role_statement.setInt(1,id);

            remove_role_statement.executeUpdate();

        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( deleteEmployee ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Nhân sự","Không thể xóa nhân sự")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( deleteEmployee ) , Details : "+e.getMessage());
            }
        }
    }
    public void deleteRoleFromEmployee(short id){
        Connection connection = MysqlConfig.getConnection();
        try {
//             Remove all of users who have specific role before being deleted
            String removeEmployeeFromRole = "UPDATE Employee SET RoleID = NULL WHERE RoleID = ?";

            PreparedStatement remove_employee_from_role_statement = connection.prepareStatement(removeEmployeeFromRole);
            remove_employee_from_role_statement.setShort(1,id);
            remove_employee_from_role_statement.executeUpdate();

        }catch (Exception e){
            System.err.println("Error : query data unsuccessfully ,function ( deleteRoleFromEmployee ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể xóa quyền")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( deleteRoleFromEmployee ) , Details : "+e.getMessage());
            }
        }
    }
    public EmployeeDTO getNumberEmployee() throws SQLException {
        Connection connection = MysqlConfig.getConnection();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        String sql = "SELECT COUNT(*) as number FROM Employee where isDeleted = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int numberEmployee = resultSet.getInt("number");
            employeeDTO.setNumberEmployee(numberEmployee);
        }
        return employeeDTO;
    }
    public int checkEmailAndPassword(String Email,String Password) {
        Connection connection = MysqlConfig.getConnection();
        try {
            String sql1 = "SELECT * FROM employee WHERE Email = ? AND Isdeleted = 0";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, Email);
            ResultSet resultSet1 = statement1.executeQuery();
            if(resultSet1.next()){
                String sql2 = "SELECT * FROM employee WHERE Email = ? AND Password = ?";
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                statement2.setString(1, Email);
                statement2.setString(2, Password);
                ResultSet resultSet2 = statement2.executeQuery();
                if(resultSet2.next()){
                    return 2;
                }else{
                    return 1;
                }
            }else{
                return 0;
            }

        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( checkEmailAndPassword ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Email", "Không thể tạo Email")));
        }
    }
    public Employee getEmployeeByEmailAndPassword(String email,String password){
        Connection connection = MysqlConfig.getConnection();
        Employee employee = new Employee();
        try {
            String sql1 = "SELECT * FROM employee s LEFT JOIN major m ON m.MajorID = s.MajorID WHERE s.Email = ? AND s.Password = ? LIMIT 1";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, email);
            statement1.setString(2, password);
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();
            if(resultSet1.getShort("RoleID") == 1){
                employee.setMajorID(resultSet1.getShort("MajorID"));
                employee.setMajorName(resultSet1.getString("Name"));
            }
            employee.setRoleID(resultSet1.getShort("RoleID"));
            employee.setEmployeeID(resultSet1.getInt("EmployeeID"));
            employee.setEmail(resultSet1.getString("Email"));
            employee.setPhone(resultSet1.getString("Phone"));
            employee.setFirstName(resultSet1.getString("FirstName"));
            employee.setLastName(resultSet1.getString("LastName"));
            employee.setGender(resultSet1.getBoolean("Gender"));
            employee.setDateOfBirth(resultSet1.getDate("DateOfBirth"));
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getStudentByEmailAndPassword ), Details : " + e.getMessage());
            throw new RetrieveException((Map.of("Email", "Không thể tạo Email")));
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.err.println("Error : can't close connection ,function ( getAllStudent ) , Details : " + e.getMessage());
            }
        }
        return employee;
    }
}
