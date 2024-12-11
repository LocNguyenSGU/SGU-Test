package controller.admin;
import DTO.MajorDTO;
import DTO.RoleDTO;
import entity.Employee;
import DTO.EmployeeDTO;
import DTO.StudentDTO;
import com.google.gson.Gson;
import entity.Major;
import entity.Role;
import entity.Student;
import payload.response.Response;
import service.MajorService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import service.EmployeeService;
import service.RoleService;

@WebServlet(name="EmployeeForAdminController" ,urlPatterns={"/admin/employee","/admin/addemployee","/admin/editemployee"})
public class EmployeeForAdminController extends HttpServlet{
    EmployeeService employeeService = new EmployeeService();
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse resp){
        String path = req.getServletPath();
        System.out.println(path);
        switch(path){
            case "/admin/employee":
                getAllEmployee(req,resp);
                break;
            case "/admin/addemployee":
                addEmployeePage(req,resp);
                break;
            case "/admin/editemployee":
                Short id = Short.valueOf(req.getParameter("id"));
                editEmployeePage(req,resp,id);
        }
    }
    public String generateRandomNumber(int min, int max) {
        Random random = new Random();
        return "" +(random.nextInt(max - min + 1) + min);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getServletPath();
        System.out.println(path);
        String action = req.getParameter("action");
        switch (path) {
            case "/admin/employee":
                if(action != null) {
                    switch(action) {
                        case "create":
                            String firstname = req.getParameter("firstname");
                            System.out.println(firstname);
                            String lastname = req.getParameter("lastname");
                            System.out.println( lastname);
                            String phone = req.getParameter("phone");;
                            String password = req.getParameter("password");
                            Boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
                            System.out.println( password);
                            String email = req.getParameter("email");
                            System.out.println(email);
                            String isoDate = req.getParameter("isoDate");
                            System.out.println(isoDate);
                            Short MajorId = Short.valueOf(req.getParameter("MajorID"));
                            System.out.println("majỏ id la "+MajorId);
                            try {
                                Date BirthDay = null;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                BirthDay = dateFormat.parse(isoDate);
                                java.sql.Date sqlDateEnd = new java.sql.Date(BirthDay.getTime());
                                Response response =  employeeService.createEmployee(firstname,lastname,phone,password,email,gender,sqlDateEnd,MajorId,(short)1);
                                System.out.println("Ket qua employee la: " + response.getStatusCode());
                                resp.setContentType("application/json");
                                resp.setCharacterEncoding("UTF-8");
                                Gson gson = new Gson();
                                String message = gson.toJson(response.getMessage());
                                resp.getWriter().write( message);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            catch(Exception e){
                                System.err.println(e);
                            }
                            break;
                        case "update":
                            Short employeeID = Short.valueOf(req.getParameter("employeeID"));
                            System.out.println("employeeID la "+employeeID);
                            firstname = req.getParameter("firstname");
                            lastname = req.getParameter("lastname");
                            System.out.println(lastname);
                            gender = Boolean.parseBoolean(req.getParameter("gender"));
                            isoDate = req.getParameter("isoDate");
                            System.out.println(isoDate+"la");
                            MajorId = Short.valueOf(req.getParameter("MajorID"));
                            phone = req.getParameter("phone");
                            System.out.println("major id la ");
                            System.out.println("major id la "+MajorId);
                            try {
                                Date BirthDay = null;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                BirthDay = dateFormat.parse(isoDate);
                                java.sql.Date sqlDateEnd = new java.sql.Date(BirthDay.getTime());
                                Response response =  employeeService.updateEmployee(employeeID,firstname,lastname,phone,gender,sqlDateEnd,MajorId,(short)1);
                                System.out.println("Ket qua employee la: " + response.getStatusCode());
                                resp.setContentType("application/json");
                                resp.setCharacterEncoding("UTF-8");
                                Gson gson = new Gson();
                                String message = gson.toJson(response.getMessage());
                                resp.getWriter().write(message);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            catch(Exception e){
                                System.err.println(e);
                            }
                            break;

                    }
                }
                break;

        }
    }
    private void getAllEmployee(HttpServletRequest req,HttpServletResponse resp){
            Response response = new Response();
            response = employeeService.getAllEmployeeWithoutPagination();
            EmployeeDTO employeeDTO = (EmployeeDTO) response.getData();
            List<Employee> listEmployee = employeeDTO.getEmployees();
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            try{
                req.setAttribute("ListEmployee",listEmployee);
                req.setAttribute("listMajors",listMajors);
                resp.setCharacterEncoding("UTF-8");
                req.getRequestDispatcher("employee.jsp").forward(req,resp);
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
    }
    public void addEmployeePage(HttpServletRequest req, HttpServletResponse resp){
        try{
            System.out.println("vao");
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            System.out.println(dataMajor.getData());
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            req.setAttribute("Majors",listMajors);
            req.getRequestDispatcher("employeeAdd.jsp").forward(req, resp);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    public void editEmployeePage(HttpServletRequest req, HttpServletResponse resp,short id){
        try{
            Response response = employeeService.getEmployeeById(id);
            Employee employee = (Employee) response.getData();
            System.out.println("id cua ban la "+employee.getMajorID());
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            req.setAttribute("employee",employee);
            req.setAttribute("majors",listMajors);
            System.out.println(employee.getMajorID());
            req.getRequestDispatcher("employeeEdit.jsp").forward(req,resp);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

}
