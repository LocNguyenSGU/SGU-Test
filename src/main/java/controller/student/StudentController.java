package controller.student;

import payload.response.Response;
import service.ExamService;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentController",urlPatterns = {"/student","/student/point","/student/timeline","/student/form","/student/tests"})
public class StudentController extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final ExamService examService = new ExamService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path){
            case "/student":
                Response studentResponse = studentService.getStudentById(1);
                Response examResponse = examService.getAllExamWithoutPageable("COMPLETED");
                req.setAttribute("res",studentResponse);
                req.setAttribute("resExam",examResponse);
                req.getRequestDispatcher("student/dashboard.jsp").forward(req,resp);
                break;
            case "/student/point":
                req.getRequestDispatcher("/student/point.jsp").forward(req,resp);
                break;
            case "/student/tests":
                req.getRequestDispatcher("/student/tests.jsp").forward(req,resp);
                break;
            case "/student/form":
                req.getRequestDispatcher("/student/form.jsp").forward(req,resp);
                break;
        }
    }
}
