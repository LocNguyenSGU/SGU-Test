package controller.instructor;

import DTO.InfoEmployeeAndMajorDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import config.LocalDateTimeAdapter;
import entity.Employee;
import payload.response.Response;
import service.SubjectService;
import service.instructor.InfoEmployeeService;
import service.instructor.OptionService;
import service.instructor.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Author: Kiet Mai Tran Tuan
 * Created: 24/04/2024 8:42 SA
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


@WebServlet(urlPatterns = {"/instructor/question", "/instructor/question/details",
        "/instructor/question/edit", "/instructor/question/filter", "/instructor/question/delete",
        "/instructor/question/update", "/instructor/question/create", "/instructor/question/add",
        "/instructor/question/create-backup", "/instructor/question/update-backup"},
        name = "QuestionServlet")
public class QuestionServlet extends HttpServlet {
    private final QuestionService questionService = new QuestionService();
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
        switch (path) {
            case "/instructor/question":
                req.setAttribute("allSubjects",
                        SubjectService.getAllSubjects(1, 1000, null,
                                Short.parseShort(_majorID)));
                req.setAttribute("allQuestions",
                        questionService.getAllQuestions(1, 1000, Integer.parseInt(_majorID)));
                req.getRequestDispatcher("/instructor/question.jsp").forward(req, resp);
                break;

            case "/instructor/question/details":
                String questionID = req.getParameter("id");
                req.setAttribute("question",
                        questionService.getQuestionByID(questionID));
                req.setAttribute("options",
                        OptionService.getOptionsByQuestionID(questionID));
                req.getRequestDispatcher("/instructor/question-details.jsp")
                        .forward(req, resp);
                break;

            case "/instructor/question/edit":
                questionID = req.getParameter("id");
                req.setAttribute("question",
                        questionService.getQuestionByID(questionID));
                req.setAttribute("options",
                        OptionService.getOptionsByQuestionID(questionID));
                req.setAttribute("allSubjects",
                        SubjectService.getAllSubjects(1, 1000, null, Short.parseShort(_majorID)));
                req.getRequestDispatcher("/instructor/question-edit.jsp")
                        .forward(req, resp);
                break;

            case "/instructor/question/add":
                // just forward to the page, no need to get data
                req.getRequestDispatcher("/instructor/question-create.jsp")
                        .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // first get the user logged in
        // get from session
        String _majorID = req.getSession().getAttribute("MajorID").toString();


        String path = req.getServletPath();
        switch (path) {
            case "/instructor/question/filter":
                var jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                int size = jsonObject.get("size").getAsInt();
                int page = jsonObject.get("page").getAsInt();
                String subjectID = jsonObject.get("subjectId").getAsString();
                String level = jsonObject.get("level").getAsString();
                String search = jsonObject.get("search").getAsString();

                // Get data from service
                String json = gson.toJson(questionService
                        .getFilteredQuestions(page, size, subjectID, level, search,
                                Integer.parseInt(_majorID)));

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
                break;

            case "/instructor/question/update":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                String questionID = jsonObject.get("questionID").getAsString();
                String title = jsonObject.get("title").getAsString();
                subjectID = jsonObject.get("subjectID").getAsString();
                level = jsonObject.get("level").getAsString();
                double point = jsonObject.get("point").getAsDouble();
                int correctAnswer = jsonObject.get("correctAnswer").getAsInt();

                // Update question
                QuestionService questionService = new QuestionService();
                Response response = questionService
                        .updateQuestion(questionID, title, subjectID, level, point, correctAnswer);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/question/create":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                title = jsonObject.get("title").getAsString();
                subjectID = jsonObject.get("subject").getAsString();
                level = jsonObject.get("level").getAsString();
                point = jsonObject.get("point").getAsDouble();

                // Create question
                response = QuestionService.createQuestion(title, Integer.parseInt(subjectID),
                        level, point);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/question/delete":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                questionID = jsonObject.get("questionID").getAsString();


                // Delete question: set isDeleted = 1
                response = QuestionService.deleteQuestion(questionID);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/question/create-backup":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                questionID = jsonObject.get("questionID").getAsString();
                point = jsonObject.get("point").getAsDouble();
                title = jsonObject.get("title").getAsString();

                // Create backup
                response = QuestionService.createBackup(questionID, point, title);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/question/update-backup":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                int backupQuestionID = jsonObject.get("backupQuestionID").getAsInt();
                correctAnswer = jsonObject.get("correctAnswer").getAsInt();

                // Update backup
                response = QuestionService.updateBackup(backupQuestionID, correctAnswer);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;
        }
    }
}
