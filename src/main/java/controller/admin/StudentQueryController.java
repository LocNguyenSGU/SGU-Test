package controller.admin;
import entity.Student;
import entity.StudentVer2;
import DTO.MajorDTO;
import DTO.StudentDTO;
import com.google.gson.Gson;
import entity.Major;
import payload.response.Response;
import service.MajorService;
import service.StudentService;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="StudentQuery", urlPatterns = {"/admin/queryController/student","/admin/queryController/deleteStudent"})
public class StudentQueryController extends HttpServlet{
    private StudentService studentService = new StudentService();
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        String path = req.getServletPath();
        System.out.println(path);
        switch (path) {
                case "/admin/queryController/student":
                try {
                    getAllStudent(req,  resp);
                } catch (Exception e) {
                    System.out.println("Khong the truy cap");
                }
                break;
//                case "/admin/queryController/deleteStudent":
//                    deleteStudent(req, resp);
//                break;
//            case "/admin/editstudent":
//                short id = Short.parseShort(req.getParameter("id"));
//                editStudentPage(req,resp,id);
//                break;

        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/admin/queryController/deleteStudent":
                deleteStudent(req, resp);
                break;
        }
    }
    public void deleteStudent(HttpServletRequest req,HttpServletResponse resp){
        try {
            // Read data from URL-encoded form data
            JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();
            // Access JSON data
            int studentId = jsonObject.get("id").getAsInt();
            // Perform deletion
            Response response = studentService.deleteStudent(studentId);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void getAllStudent(HttpServletRequest req, HttpServletResponse resp) {
        try {
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            Response dataRes = studentService.getAllStudentWithoutPagination();

            StudentDTO studentDTO = (StudentDTO) dataRes.getData();
            List<Student> listStudent = studentDTO.getStudents();

            // Chuyển đổi danh sách sinh viên thành chuỗi JSON
            Gson gson = new Gson();
            String listStudentJson = gson.toJson(listStudent);

            // Đặt loại nội dung của phản hồi là "application/json"
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            // Gửi chuỗi JSON về client
            resp.getWriter().write(listStudentJson);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}

