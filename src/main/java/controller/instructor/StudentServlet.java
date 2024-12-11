package controller.instructor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import config.LocalDateTimeAdapter;
import service.MajorService;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 31/03/2024 1:08 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


@WebServlet(urlPatterns = {"/instructor/student", "/instructor/student/filter",
        "/instructor/student/results"},
        name = "StudentServlet")
public class StudentServlet extends HttpServlet {
    private final MajorService majorService = new MajorService();
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // first get the user logged in
        // get from session
        String _majorID = req.getSession().getAttribute("MajorID").toString();


        String path = req.getServletPath();
        if (path.equals("/instructor/student")) {
            req.setAttribute("allStudents",
                    StudentService.getAllStudents(1, 1000, _majorID));
            req.setAttribute("allMajors",
                    majorService.getAllMajor(1, 1000, null));
            req.setAttribute("_majorID", _majorID);

            req.getRequestDispatcher("/instructor/student.jsp").forward(req, resp);
        } else if (path.equals("/instructor/student/results")) {
            // Get data from request
            int studentID = Integer.parseInt(req.getParameter("id"));

            req.setAttribute("results",
                    StudentService.getResultsByStudentId(studentID));
            req.getRequestDispatcher("/instructor/student-results.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/instructor/student/filter")) {
            var jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

            // Get data from request
            int size = jsonObject.get("size").getAsInt();
            int page = jsonObject.get("page").getAsInt();
            String majorID = jsonObject.get("majorId").getAsString();
            String search = jsonObject.get("search").getAsString();

            // Get data from service
            String json = gson.toJson(StudentService
                    .getFilteredStudentsKiet(page, size, majorID, search));

            // Response
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
        }
    }
}
