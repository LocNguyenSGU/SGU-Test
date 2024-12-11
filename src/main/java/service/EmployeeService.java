package service;

import entity.Employee;
import payload.response.Response;
import repository.EmployeeRepository;

import java.sql.Date;

public class EmployeeService {
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    public Response getAllEmployee(int page, int size, String search){
        Response response = new Response();
        try {
            response.setData(employeeRepository.getAllEmployee(page,size,search));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getAllEmployeeWithoutPagination(){
        Response response = new Response();
        try {
            response.setData(employeeRepository.getAllEmployeeWithoutPagination());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getEmployeeById(short id){
        Response response = new Response();
        try {
            response.setData(employeeRepository.getEmployeeById(id));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response createEmployee(String firstname, String lastname, String phone, String password, String email, boolean gender, Date birthdate, short majorid, short roleid){
        Response response = new Response();
        try {
            Employee employee = new Employee();
            employee.setPhone(phone);
            employee.setGender(gender);
            employee.setPassword(password);
            employee.setFirstName(firstname);
            employee.setLastName(lastname);
            employee.setEmail(email);
            employee.setRoleID(roleid);
            employee.setMajorID(majorid);
            employee.setDateOfBirth(birthdate);
//            employee.validate();

            employeeRepository.createEmployee(employee);
            response.setData("Tạo thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Email bị trùng");
        }
        return response;
    }
    public Response updateEmployee(int employeeid,String firstname, String lastname, String phone, boolean gender, Date birthdate, short majorid, short roleid){
        Response response = new Response();
        try {
            Employee employee = new Employee();
            employee.setEmployeeID(employeeid);
            employee.setPhone(phone);
            employee.setGender(gender);
            employee.setFirstName(firstname);
            employee.setLastName(lastname);
            employee.setRoleID(roleid);
            employee.setMajorID(majorid);
            employee.setDateOfBirth(birthdate);
//            employee.validate();

            employeeRepository.updateEmployee(employee);
            response.setData("Cập nhật thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response deleteEmployee(int id){
        Response response = new Response();
        try {
            employeeRepository.deleteEmployee(id);
            response.setData("Cập nhật thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getNumberEmployee(){
        employeeRepository = new EmployeeRepository();
        Response response = new Response();
        try {
            response.setData(employeeRepository.getNumberEmployee());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response authenticateEmployeeByEmailAndPassword(String email, String password){
        Response response = new Response();
        int num = employeeRepository.checkEmailAndPassword(email,password);
        System.out.println(num);
        if(num == 0){
            response.setData(0);
            response.setMessage("Email không tồn tại!");
            response.setStatusCode(400);
        }
        if(num == 1){
            response.setData(1);
            response.setMessage("Mật khẩu không chính xác!");
            response.setStatusCode(400);
        }
        if(num == 2){
            response.setData(employeeRepository.getEmployeeByEmailAndPassword(email,password));
            System.out.println("gia tri cua employee trong service"+employeeRepository.getEmployeeByEmailAndPassword(email,password).getFirstName());
            response.setStatusCode(200);
            response.setMessage("Đăng nhập thành công!");
        }
        return response;
    }
}