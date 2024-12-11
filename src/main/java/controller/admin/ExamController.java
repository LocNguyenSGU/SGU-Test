package controller.admin;

import DTO.ExamDTO;
import DTO.MajorDTO;
import DTO.QuestionBankDTO;
import com.google.gson.GsonBuilder;
import config.LocalDateTimeAdapter;
import entity.Enum.ExaminationStatus;
import entity.Exam;
import entity.Major;
import entity.Question;
import payload.response.Response;
import service.ExamService;
import service.MajorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@WebServlet(name="ExamController" , urlPatterns={"/admin/exam"})

public class ExamController extends HttpServlet {
    private ExamService examService;
    private Response response;
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       String action = req.getParameter("action");
       if(action == null) {
           getAllExam(req, resp);
           return;
       }
       switch (action) {
           case "getExam":
               getExamById(req, resp);
               break;
           case "addexam":
                req.getRequestDispatcher("/admin/examAdd.jsp").forward(req, resp);
                break;
            default:
                getAllExam(req, resp);
                break;
       }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {
            return;
        }
        switch (action) {
            case "add" :
                createExam(req, resp);
                break;
            case "update":
                try {
                    updateExam(req, resp);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                try {
                    deleteExam(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }


    public void getAllExam(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        examService = new ExamService();

        Response response = examService.getAllExamsWithoutPaging();
        Object data = response.getData();
        System.out.println("Ket qua tra ve cua ham getAllExam la: " + response.getStatusCode());
        ExamDTO examDTO = (ExamDTO) data;
        List<Exam> examList = examDTO.getExams();

        String jsonExamList = gson.toJson(examList);
        System.out.println(jsonExamList);

        req.setAttribute("jsonExamList", jsonExamList);
        req.getRequestDispatcher("/admin/exam.jsp").forward(req, resp);
    }
    public void createExam(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        examService = new ExamService();
        String name = req.getParameter("name");
        System.out.println("Ten cua exam la: " + name);
        String dateStartString = req.getParameter("dateStart");
        System.out.println("Ngay bat dau cua exam string la: " + dateStartString);
        String dateEndString = req.getParameter("dateEnd");
        System.out.println("Ngay ket thuc cua exam string la: " + dateEndString);
        String statusString = req.getParameter("status");
        ExaminationStatus status = ExaminationStatus.valueOf(statusString);
        System.out.println("Status: " + status);
        String dateStartCorrect = dateStartString;
        String dateEndCorrect = dateEndString;

            try {
                LocalDateTime DateStart = LocalDateTime.parse(dateStartCorrect);
                System.out.println("LocalDate Time: " + DateStart);
                LocalDateTime DateEnd = LocalDateTime.parse(dateEndCorrect);
                System.out.println("Chay qua cai parse ngay trong examCOntroller hamf createExam");
                Response response =  examService.createExam(name, DateStart, DateEnd, status);
                System.out.println("Ket qua tra ve cua createExam la: " + response.getStatusCode());
            }catch (Exception e) {
                System.out.println(e);
            }
        resp.sendRedirect(req.getContextPath() + "/admin/exam");

    }
    public void getExamById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        examService = new ExamService();
        int examId = Integer.parseInt(req.getParameter("examId"));
        Response response = examService.getExamById(examId);
        Object data = response.getData();
        System.out.println("COde tra ve cua getExam: " + response.getStatusCode());
        Exam exam = (Exam) data;
        req.setAttribute("exam", exam);
        req.getRequestDispatcher("/admin/examEdit.jsp").forward(req, resp);
    }
    public void updateExam(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException {
        String name = req.getParameter("name");
        int examId = Integer.parseInt(req.getParameter("examId"));
        String dateStartString = req.getParameter("dateStart");
        String dateEndString = req.getParameter("dateEnd");
        String statusString = req.getParameter("status");
        ExaminationStatus status = ExaminationStatus.valueOf(statusString);

        String dateStartCorrect = dateStartString;
        String dateEndCorrect = dateEndString;
        LocalDateTime sqlDateStart = LocalDateTime.parse(dateStartCorrect);
        LocalDateTime sqlDateEnd = LocalDateTime.parse(dateEndCorrect);

        examService = new ExamService();
        Response response = examService.updateExam(examId ,name, sqlDateStart, sqlDateEnd, status);
        System.out.println("KEt qua tra ve cua update exam la: " + response.getStatusCode());
        resp.sendRedirect(req.getContextPath() + "/admin/exam");
    }
    public void deleteExam(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        examService = new ExamService();
        int examId = Integer.parseInt(req.getParameter("examId"));
        examService.deleteExam(examId);
    }
}
