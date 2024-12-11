package controller.admin;
import DTO.ResultStudentDTO;
import entity.Student;
import entity.ResultStudent;
import entity.StudentVer2;
import DTO.MajorDTO;
import DTO.StudentDTO;
import com.google.gson.Gson;
import entity.Major;
import payload.response.Response;
import service.ExamService;
import service.MajorService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import service.StudentService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
@WebServlet(name="StudentForAdminController", urlPatterns = {"/admin/student","/admin/addstudent","/admin/editstudent"})
public class StudentForAdminController extends HttpServlet {
    private StudentService studentService = new StudentService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getServletPath();
        System.out.println(path);
        switch (path) {
                case "/admin/student":
                try {
                getAllStudent(req,  resp);

                } catch (Exception e) {
                    System.out.println("Khong the truy cap");
                }
                break;
                case "/admin/addstudent":
                addStudentPage(req,resp);
                break;
                case "/admin/editstudent":
                short id =Short.parseShort(req.getParameter("id"));
                editStudentPage(req,resp,id);
                break;

        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getServletPath();
        System.out.println(path);
        String action = req.getParameter("action");
        switch (path) {
            case "/admin/student":
                if(action != null) {
                    switch(action) {
                        case "create":
                            String firstname =  req.getParameter("firstname");
                            String lastname = req.getParameter("lastname");
                            System.out.println( lastname);
                            String phone = req.getParameter("phone");
                            String password = req.getParameter("password");
                            Boolean gender = Boolean.valueOf(req.getParameter("gender"));
                            System.out.println( password);
                            String email = req.getParameter("email");
                            System.out.println(email);
                            String isoDate = req.getParameter("isoDate");
                            System.out.println(isoDate);
                            Short MajorId = Short.valueOf(req.getParameter("MajorID"));
                            System.out.println(MajorId);
                            try {
                                Date BirthDay = null;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                BirthDay = dateFormat.parse(isoDate);
                                java.sql.Date sqlDateEnd = new java.sql.Date(BirthDay.getTime());
                                Response response =  studentService.createStudent(firstname,lastname,phone,password,email,gender,sqlDateEnd,MajorId);
                                System.out.println("Ket qua Student la: " + response.getStatusCode());
                                Gson gson = new Gson();
                                String message = gson.toJson(response.getMessage());
                                resp.setContentType("application/json");
                                resp.setCharacterEncoding("UTF-8");
                                resp.getWriter().write(message);
                                System.out.println("Ket qua cua viec cap nhat la: " + message);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            catch(Exception e){
                                System.err.println(e);
                            }
                            break;
                        case "update":
                            Short StudentID = Short.valueOf(req.getParameter("studentID"));
                            System.out.println("Studenid la "+StudentID);
                            firstname = req.getParameter("firstname");
                            lastname = req.getParameter("lastname");
                            System.out.println(lastname);
                            gender = Boolean.valueOf(req.getParameter("gender"));
                            isoDate = req.getParameter("isoDate");
                            System.out.println(isoDate+"la");
                            MajorId = Short.valueOf(req.getParameter("MajorID"));
                            phone = req.getParameter("phone");
                            System.out.println(MajorId);
                            try {
                                Date BirthDay = null;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                BirthDay = dateFormat.parse(isoDate);
                                java.sql.Date sqlDateEnd = new java.sql.Date(BirthDay.getTime());
                                Response response =  studentService.updateStudent(StudentID,firstname,lastname,phone,gender,sqlDateEnd,MajorId);
                                Gson gson = new Gson();
                                String message = gson.toJson(response.getData());
                                resp.setContentType("application/json");
                                resp.setCharacterEncoding("UTF-8");
                                resp.getWriter().write(message);
                                System.out.println("Ket qua cua viec cap nhat la: " + message);
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
    public String generateRandomNumber(int min, int max) {
        Random random = new Random();
        return "" +(random.nextInt(max - min + 1) + min);
    }
    public void getAllStudent(HttpServletRequest req, HttpServletResponse resp) {
        try {
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            Response dataRes = studentService.getAllStudentWithoutPagination();
            System.out.println(dataMajor.getData());
            System.out.println(dataRes.getData());
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            StudentDTO studentDTO = (StudentDTO) dataRes.getData();
            List<Major> listMajors = majorDTO.getMajors();
            List<Student> listStudent = studentDTO.getStudents();
            StudentVer2 packages = new StudentVer2(listMajors,listStudent);
            req.setAttribute("Result", packages);
            resp.setCharacterEncoding("UTF-8");
            req.getRequestDispatcher("student.jsp").forward(req, resp);
        }catch (Exception e){
           System.err.println(e.getMessage());
        }
    }
    public void addStudentPage(HttpServletRequest req, HttpServletResponse resp){
        try{
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            req.setAttribute("Majors",listMajors);
            req.getRequestDispatcher("studentAdd.jsp").forward(req, resp);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    public void editStudentPage(HttpServletRequest req, HttpServletResponse resp,short id){
        try{
            Response response = studentService.getStudentById(id);
            Student student = (Student) response.getData();
            System.out.println(id);
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            Response dulieutrave = studentService.getResultStudentFormId(id);
            List<ResultStudent> dataStudent = (List<ResultStudent>) dulieutrave.getData();
            if (dataStudent == null) {
                System.out.println("ArrayList is null");
            } else {
                System.out.println(dataStudent);
            }
            req.setAttribute("resultstudent",dataStudent);
            req.setAttribute("student",student);
            req.setAttribute("majors",listMajors);
            System.out.println(student.getMajorID());
            req.getRequestDispatcher("studentEdit.jsp").forward(req,resp);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

}
