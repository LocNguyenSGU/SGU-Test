package controller.instructor;

import DTO.InfoEmployeeAndMajorDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.LocalDateTimeAdapter;
import entity.Employee;
import repository.ExamRepository;
import repository.MajorRepository;
import repository.SubjectRepository;
import service.StudentService;
import service.SubjectService;
import service.instructor.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Kiet Mai Tran Tuan
 * Created: 24/04/2024 10:37 SA
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


@WebServlet(name = "HelperServlet", urlPatterns = {"/api/get-exam-name", "/api/get-major-name",
        "/api/get-subject-name", "/api/get-formatted-date", "/api/get-formatted-datetime",
        "/api/get-all-majors", "/api/get-all-subjects", "/api/get-all-exams-not-started",
        "/api/get-questions-by-subject-id", "/api/get-options-by-question-id", "/api/get-student-by-id",
        "/api/get-results-by-student-id", "/api/get-test-by-id", "/api/get-subject-by-id"})

public class HelperServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // first get the user logged in
        // get from session
        String _majorID = request.getSession().getAttribute("MajorID").toString();

        String path = request.getServletPath();
        switch (path) {
            case "/api/get-exam-name":
                int examID = Integer.parseInt(request.getParameter("examID"));
                String examName = ExamRepository.getExamNameByID(examID);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(examName);
                break;

            case "/api/get-all-exams-not-started":
                String jsonExams = gson.toJson(
                        ExamService.getAllExams(1, 1000, null, "NOT_STARTED"));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonExams);
                break;

            case "/api/get-major-name":
                int majorID = Integer.parseInt(request.getParameter("majorID"));
                String majorName = MajorRepository.getMajorNameByID(majorID);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(majorName);
                break;

            case "/api/get-subject-name":
                int subjectID = Integer.parseInt(request.getParameter("subjectID"));
                String subjectName = SubjectRepository.getSubjectNameByID(subjectID);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(subjectName);
                break;

            case "/api/get-all-subjects":
                String jsonSubjects = gson.toJson(
                        SubjectService.getAllSubjects(1, 1000, null, Short.parseShort(_majorID)));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonSubjects);
                break;

            case "/api/get-questions-by-subject-id":
                int subjectId = Integer.parseInt(request.getParameter("subjectID"));
                String jsonQuestions = gson.toJson(QuestionService
                        .getQuestionsBySubjectId(subjectId).getData()
                    );

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonQuestions);
                break;

            case "/api/get-options-by-question-id":
                String questionId = request.getParameter("questionID");
                String jsonOptions = gson.toJson(OptionService
                        .getOptionsByQuestionID(questionId).getData()
                    );

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonOptions);
                break;

            case "/api/get-formatted-date":
                String dateStr = request.getParameter("date");
                Date date = Date.valueOf(dateStr);
                String formattedDate = Utilities.formatDate(date);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(formattedDate);
                break;

            case "/api/get-formatted-datetime":
                String dateTimeStr = request.getParameter("dateTime");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                String formattedDateTime = Utilities.formatDateTime(dateTime);

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(formattedDateTime);
                break;

            case "/api/get-results-by-student-id":
                // Get data from request
                int studentId = Integer.parseInt(request.getParameter("studentID"));

                // Get data from service
                String jsonResults = gson.toJson(StudentService
                        .getResultsByStudentId(studentId).getData()
                    );

                // Response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResults);
                break;

            case "/api/get-test-by-id":
                // Get data from request
                String testId = request.getParameter("testID");

                // Get data from service
                String jsonTest = gson.toJson(TestService
                        .getTestByID(testId)
                    );

                // Response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonTest);
                break;

            case "/api/get-subject-by-id":
                // Get data from request
                subjectID = Integer.parseInt(request.getParameter("subjectID"));

                // Get data from service
                String jsonSubject = gson.toJson(SubjectService
                        .getSubjectByID(subjectID)
                    );

                // Response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonSubject);
                break;

            case "/api/get-student-by-id":
                // Get data from request
                short studentID = Short.parseShort(request.getParameter("studentID"));

                // Get data from service
                String jsonStudent = gson.toJson(StudentService
                        .getStudentById(studentID));

                // Response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonStudent);
                break;
        }
    }
}
