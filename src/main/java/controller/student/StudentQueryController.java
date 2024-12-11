package controller.student;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.LocalDateTimeAdapter;
import entity.Exam;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;

@WebServlet(name = "StudentQueryController",urlPatterns = {"/studentSearch/exam","/studentSearch/test","/studentSearch/question","/studentSearch/result","/studentSearch/resultdetails","/studentSearch/timeline","/studentSearch/resultDoc","/studentSearch/resultTest", "/studentSearch/resultStatus","/studentSearch/updateResult"})
public class StudentQueryController extends HttpServlet {
    private final ExamService examService = new ExamService();
    private final BackupQuestionService backupQuestionService = new BackupQuestionService();
    private final ResultService resultService = new ResultService();
    private final ResultDetailsService resultDetailsService = new ResultDetailsService();
    private Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String json = "";
        JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();
        switch (path){
            case "/studentSearch/exam":
                // Get data from request
                int size = jsonObject.get("size").getAsInt();
                int page = jsonObject.get("page").getAsInt();
                String search = URLDecoder.decode(jsonObject.get("search").getAsString(), "UTF-8");
                String status = jsonObject.get("status").getAsString();
                json = gson.toJson(examService.getAllExam(page,size,search,status));
                break;
            case "/studentSearch/result":
                int ExamID = jsonObject.get("ExamID").getAsInt();
                int StudentID = jsonObject.get("StudentID").getAsInt();
                json = gson.toJson(resultService.getResultByStudentIDAndExamID(StudentID,ExamID));
                break;
            case "/studentSearch/resultTest":
                int TestID = jsonObject.get("TestID").getAsInt();
                int StudentTestID = jsonObject.get("StudentID").getAsInt();
                json = gson.toJson(resultService.getResultByStudentIDAndTestID(StudentTestID,TestID));
                break;
            case "/studentSearch/resultdetails":
                int ResultID = jsonObject.get("ResultID").getAsInt();
                json = gson.toJson(resultDetailsService.getAllResultDetailsByResultID(ResultID));
                break;
            case "/studentSearch/resultStatus":
                int ResultStatusID = jsonObject.get("ResultID").getAsInt();
                String ResultStatus = jsonObject.get("Status").getAsString();
                json = gson.toJson(resultService.updateResultStatus(ResultStatusID,ResultStatus));
                break;
            case "/studentSearch/updateResult":
                int ResultUpdateID = jsonObject.get("ResultID").getAsInt();
                int QuestionID = jsonObject.get("QuestionID").getAsInt();
                int ChooseOption = jsonObject.get("ChooseOption").getAsInt();
                json = gson.toJson(resultDetailsService.updateResultDetails(ResultUpdateID,QuestionID,ChooseOption));
                break;
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

}
