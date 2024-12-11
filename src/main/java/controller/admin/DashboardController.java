package controller.admin;

import DTO.*;
import com.google.gson.GsonBuilder;
import config.LocalDateAdapter;
import config.LocalDateTimeAdapter;
import entity.DataToDrawBarChart;
import entity.Question;
import entity.Student;
import payload.response.Response;
import service.EmployeeService;
import service.QuestionBankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.DataToDrawPieChart;
import service.StudentService;
import service.instructor.TestService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name="DashboardController" , urlPatterns={"/admin/dashboard"})
public class DashboardController extends HttpServlet {
    private QuestionBankService questionBankService = new QuestionBankService();
    private TestService testService = new TestService();
    private StudentService studentService = new StudentService();
    private EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getDashboard(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
    public void getDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getPieChart(req, resp);
        getNumberQuestion(req, resp);
        getBarChart(req, resp);
        getNumberTest(req, resp);
        getNumberStudent(req, resp);
        getNumberEmployee(req, resp);
        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
    private void getPieChart(HttpServletRequest req, HttpServletResponse resp) {
        Response responsePieChart = questionBankService.getDataQuestionPieChart();
        Object data = responsePieChart.getData();
        QuestionBankDTO questionBankDTO = (QuestionBankDTO) data;
        List<DataToDrawPieChart> dataToDrawPieCharts = questionBankDTO.getDataToDrawPieCharts();
        Gson gsonPieChart = new Gson();
        String jsonDataPieChart = gsonPieChart.toJson(dataToDrawPieCharts);
        System.out.println("dataPie:" + jsonDataPieChart);
        req.setAttribute("jsonDataPieChart", jsonDataPieChart);
    }
    public static String convertDataToJSON(List<DataToDrawBarChart> dataToDrawBarCharts) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < dataToDrawBarCharts.size(); i++) {
            DataToDrawBarChart data = dataToDrawBarCharts.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"number\":").append(data.getNumber()).append(",");
            jsonBuilder.append("\"dateStart\":\"").append(data.getDateStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\"");
            jsonBuilder.append("}");
            if (i < dataToDrawBarCharts.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
    private void getBarChart(HttpServletRequest req, HttpServletResponse resp) {
        Response responseBarChart = testService.getDataToDrawBarChart();
        System.out.println("code tra ve cua bar chart: " + responseBarChart.getStatusCode());
        System.out.println("data tra ve cua bar chart: " + responseBarChart.getData());
        Object data = responseBarChart.getData();
        System.out.println("data tra VE 0");
        TestDTO testDTO = (TestDTO) data;
        System.out.println("data tra VE 1");
        List<DataToDrawBarChart> dataToDrawBarCharts = testDTO.getDataToDrawBarCharts();
        System.out.println("data tra VE 2");

        for (DataToDrawBarChart d : dataToDrawBarCharts) {
            // Xử lý hoặc xuất ra thông tin của phần tử 'data' trong danh sách
            System.out.println("idnum" + d.getNumber());
            System.out.println(d.getDateStart()); // Ví dụ: In thông tin của phần tử 'data'
        }
        String jsonDataBarChart =  convertDataToJSON(dataToDrawBarCharts);
        req.setAttribute("jsonDataBarChart", jsonDataBarChart);
        System.out.println(jsonDataBarChart);
    }



    private void getNumberQuestion(HttpServletRequest req, HttpServletResponse resp) {
        Response responseNumberQuestion = questionBankService.getAllQuestion();
        Object numberQuestion = responseNumberQuestion.getData();
        QuestionBankDTO questionBankDTONumber = (QuestionBankDTO) numberQuestion;
        req.setAttribute("numberQuestion", questionBankDTONumber.getNumberQuestion());
    }
    private void getNumberTest(HttpServletRequest req, HttpServletResponse resp) {
        Response responseNumberTest = testService.getNumberTest();
        System.out.println("Code tra ve cua responeNUmverTesr" + responseNumberTest.getStatusCode());
        Object numberTest = responseNumberTest.getData();
        TestDTO testDTO = (TestDTO) numberTest;
        req.setAttribute("numberTest", testDTO.getNumberTest());
    }
    private void getNumberStudent(HttpServletRequest req, HttpServletResponse resp) {
        Response responseNumberStudent = studentService.getNumberStudent();
        System.out.println("Code tra ve cua responeNumberStudent" + responseNumberStudent.getStatusCode());
        Object numberTest = responseNumberStudent.getData();
        StudentDTO studentDTO = (StudentDTO) numberTest;
        req.setAttribute("numberStudent", studentDTO.getNumberStudent());
    }
    private void getNumberEmployee(HttpServletRequest req, HttpServletResponse resp) {
        Response responseNumberEmployee = employeeService.getNumberEmployee();
        System.out.println("Code tra ve cua responeNumberEmployee" + responseNumberEmployee.getStatusCode());
        Object numberEmployee = responseNumberEmployee.getData();
        EmployeeDTO employeeDTO = (EmployeeDTO) numberEmployee;
        req.setAttribute("numberEmployee", employeeDTO.getNumberEmployee());
    }
}
