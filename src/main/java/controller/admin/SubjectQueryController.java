package controller.admin;
import DTO.QuestionBankDTO;
import entity.Subject;
import DTO.SubjectDTO;
import DTO.MajorDTO;
import com.google.gson.Gson;
import entity.Major;
import payload.response.Response;
import repository.MajorRepository;
import service.MajorService;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import repository.SubjectRepository;
import service.StudentService;
import service.SubjectService;
@WebServlet(name="SubjectQuery", urlPatterns = {"/admin/queryController/subject","/admin/queryController/deleteSubject"})
public class SubjectQueryController extends HttpServlet {
    private SubjectService subjectService = new SubjectService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getServletPath();
        System.out.println(path);
        switch (path) {
            case "/admin/queryController/subject":
                try {
                    getAllSubject(req, resp);
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
            case "/admin/queryController/deleteSubject":
                deleteSubject(req, resp);
                break;
        }
    }

    public void deleteSubject(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Read data from URL-encoded form data
            JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();
            // Access JSON data
            int subjectId = jsonObject.get("id").getAsInt();
            // Perform deletion
            Response response = subjectService.deleteSubject((short) subjectId);
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

    private void getAllSubject(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Response response = subjectService.getAllSubjectWithoutPagination();
            System.out.println("get all subject");
            SubjectDTO data = (SubjectDTO) response.getData();
            List<Subject> listSubject = data.getSubjects();
            System.out.println(response.getData());
            Gson gson = new Gson();
            String listSubjectJson = gson.toJson(listSubject);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // Gửi chuỗi JSON về client
            resp.getWriter().write(listSubjectJson);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Lỗi");
        }
    }
}
