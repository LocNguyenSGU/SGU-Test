package controller.instructor;


import DTO.OptionDTO;
import DTO.QuestionDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.LocalDateTimeAdapter;
import entity.Option;
import entity.Question;
import payload.response.Response;
import service.SubjectService;
import service.instructor.ExamService;
import service.instructor.OptionService;
import service.instructor.QuestionService;
import service.instructor.TestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 04/04/2024 3:39 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


@WebServlet(urlPatterns = {"/instructor/test", "/instructor/test/add", "/instructor/test/edit",
        "/instructor/test/delete", "/instructor/test/filter", "/instructor/test/details",
        "/instructor/test/create", "/instructor/test/create-details", "/instructor/test/results" })
public class TestServlet extends HttpServlet {
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
            case "/instructor/test":
                req.setAttribute("allTests", TestService.getAllTests(1, 1000, null,
                        Short.parseShort(_majorID)));
                req.setAttribute("allSubjects", SubjectService.getAllSubjects(1, 1000, null,
                        Short.parseShort(_majorID)));
                req.setAttribute("allExams",
                        ExamService.getAllExams(1, 1000, null, "NOT_STARTED"));
                req.getRequestDispatcher("/instructor/test.jsp").forward(req, resp);
                break;

            case "/instructor/test/add":
                // just forward to the page, no need to get data
                req.getRequestDispatcher("/instructor/test-create.jsp")
                        .forward(req, resp);
                break;

            case "/instructor/test/details":
                String testID = req.getParameter("id");
                req.setAttribute("test", TestService.getTestByID(testID));

                QuestionDTO questionDTO =
                        (QuestionDTO) QuestionService.getQuestionsByTestID(testID).getData();
                List<Question> questions = questionDTO.getQuestions();
                req.setAttribute("questions", questions);

                List<Option> options = new ArrayList<>();
                for (Question question : questions) {
                    String questionID = String.valueOf(question.getQuestionID());

                    Response optionsRes = OptionService.getOptionsByQuestionID(questionID);

                    options.addAll(((OptionDTO) optionsRes.getData()).getOptions());
                }

                req.setAttribute("options", options);

                req.getRequestDispatcher("/instructor/test-details.jsp")
                        .forward(req, resp);
                break;

            case "/instructor/test/results":
                testID = req.getParameter("id");
                req.setAttribute("results", TestService.getResultsByTestID(testID));

                req.getRequestDispatcher("/instructor/test-results.jsp")
                        .forward(req, resp);
                break;

            default:
                break;
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
            case "/instructor/test/filter":
                JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                int size = jsonObject.get("size").getAsInt();
                int page = jsonObject.get("page").getAsInt();
                String examId = jsonObject.get("examId").getAsString();
                String search = jsonObject.get("search").getAsString();

                // Get data from service
                String json = gson.toJson(TestService.getFilteredTests(page, size, examId,
                        search, Short.parseShort(_majorID)));

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);

                break;

            case "/instructor/test/create":
                JsonObject testJson = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                int subjectID = testJson.get("testSubject").getAsInt();
                int examID = testJson.get("testExam").getAsInt();
                String testDescription = testJson.get("testDes").getAsString();
                String testDuration = testJson.get("testDuration").getAsString();
                String testStartTime = testJson.get("testStartingDatetime").getAsString();
                String testEndTime = testJson.get("testEndingDatetime").getAsString();
                String testStatus = "NOT_STARTED";
                int numberOfQuestions = testJson.get("numberOfQuestions").getAsInt();
                double totalPoint = testJson.get("totalPoint").getAsDouble();

                // Create test
                Response response = TestService.createTest(subjectID, examID, testDescription,
                        testDuration, testStartTime, testEndTime, testStatus, numberOfQuestions,
                        totalPoint);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));

                break;

            case "/instructor/test/create-details":
                JsonObject testDetailsJson = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                int testID = testDetailsJson.get("testID").getAsInt();
                int backupQuestionID = testDetailsJson.get("backupQuestionID").getAsInt();

                // Create test details
                Response responseDetails = TestService.createTestDetails(testID, backupQuestionID);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(responseDetails));

                break;

            default:
                break;
        }
    }
}
