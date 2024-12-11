package controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.LocalDateTimeAdapter;
import entity.Employee;
import entity.Student;
import payload.response.Response;
import service.EmployeeService;
import service.StudentService;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name="LoginController" , urlPatterns={"/login","/logout","/loginStudent","/loginEmployee"})
public class LoginController extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final EmployeeService employeeService = new EmployeeService();
    private Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String json = "";
        JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();
        switch (path){
            case "/loginStudent":
                // Lấy thông tin đăng nhập từ yêu cầu
                String studentemail = jsonObject.get("email").getAsString();
                String studentpassword = jsonObject.get("password").getAsString();
                Response studentres = studentService.authenticateStudentByEmailAndPassword(studentemail,studentpassword);
                if(studentres.getStatusCode() == 200){
                    // Lấy hoặc tạo session
                    HttpSession session = req.getSession(false);
//                    // Nếu có session hiện tại, hủy nó
                    if (session != null) {
                        // Xóa thuộc tính MajorID nếu tồn tại
                        session.removeAttribute("MajorID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("StudentID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("EmployeeID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("RoleID");
                    }
                    Student student =  ((Student) studentres.getData());
                    // Đặt các thuộc tính trong session
                    session.setAttribute("StudentID",student.getStudentID());
                    session.setAttribute("MajorID", student.getMajorID());
                }
                json = gson.toJson(studentres);
                break;
            case "/loginEmployee":
                // Lấy thông tin đăng nhập từ yêu cầu
                String employeeemail = jsonObject.get("email").getAsString();
                String employeepassword = jsonObject.get("password").getAsString();
                Response employeeres = employeeService.authenticateEmployeeByEmailAndPassword(employeeemail,employeepassword);
                if(employeeres.getStatusCode() == 200){
                    // Lấy hoặc tạo session
                    HttpSession session = req.getSession();
//                    // Nếu có session hiện tại, hủy nó
                    if (session != null) {
                        // Xóa thuộc tính MajorID nếu tồn tại
                        session.removeAttribute("MajorID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("StudentID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("EmployeeID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("RoleID");
                    }
                    Employee employee =  ((Employee) employeeres.getData());

                    // Đặt các thuộc tính trong session
                    session.setAttribute("EmployeeID",employee.getEmployeeID());
                    session.setAttribute("RoleID",employee.getRoleID());
                    if(employee.getRoleID() == 1){
                        session.setAttribute("MajorID", employee.getMajorID());
                    }
                }
                json = gson.toJson(employeeres);
                break;
            case "/logout":
                HttpSession session = req.getSession();
                System.out.println("akjfjaskjfhakjshfkjashfkj");
                if (session != null) {
                        // Xóa thuộc tính MajorID nếu tồn tại
                        session.removeAttribute("MajorID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("StudentID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("EmployeeID");
                        // Xóa thuộc tính StudentID nếu tồn tại
                        session.removeAttribute("RoleID");
                }
                resp.sendRedirect(req.getContextPath() + "/login");
                break;
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
