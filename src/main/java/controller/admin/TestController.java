package controller.admin;

import DTO.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.LocalDateTimeAdapter;
import config.MysqlConfig;
import entity.*;
import exception.RetrieveException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import payload.response.Response;
import service.ExamService;
import service.MajorService;
import service.SubjectService;
import service.TestService2Loc;
import service.instructor.TestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name="TestController", urlPatterns = {"/admin/test"})
public class TestController extends HttpServlet {
    private TestService testService;
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            System.out.println("Da vo null");
            getAllTest(req, resp);
            return; // Dừng việc thực hiện các câu lệnh sau khi gọi getAllMajor()
        }
        switch (action) {
            case "getTest":
                System.out.println("Da vo getMajor");
                getTestById(req, resp);
                break; // Dừng việc thực hiện các trường hợp khác sau khi gọi getMajorById()
            default:
                getAllTest(req, resp);
                System.out.println("Da vo mac dinh get all test");
                break; // Dừng việc thực hiện switch-case
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "delete":
                System.out.println("da vo controller delete Test");
                deleteTestByTestID(req, resp);
                break;
            case "export-result":
                System.out.println("da vo controller export result Test");
                exportResultTestExcel(req, resp);
                downloadExcelFile(req, resp);
                break;
        }
    }
    public void getAllTest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TestService2Loc testService2Loc = new TestService2Loc();
            Response response = testService2Loc.getAllTestWithoutPaging();
            System.out.println("Response: " + response.getStatusCode());
            TestDTO testDTO = (TestDTO) (response.getData());
            List<Test> testList = testDTO.getTests();
            String testListJson = gson.toJson(testList);
            System.out.println("Test list Json: " + testListJson);
            req.setAttribute("jsonTestList", testListJson);
            getAllMajor(req, resp);
            getAllExam(req, resp);
            getAllSubject(req, resp);
            req.getRequestDispatcher("/admin/test.jsp").forward(req, resp);
        } catch (Exception e) {
            System.out.println("Loi o ham getAllTest Controller" + e);
        }
    }
    public void getAllMajor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MajorService majorService = new MajorService();
        Response response = majorService.getAllMajorWithoutPaging();
        System.out.println("Ket qua  tra ve cau getAllMajor: " + response.getData());
        Object data = response.getData();
        MajorDTO majorDTO = (MajorDTO) data;
        List<Major> majorList = majorDTO.getMajors();
        String jsonMajorList = gson.toJson(majorList);
        System.out.println("Json la: " + jsonMajorList);
        req.setAttribute("jsonMajorList", jsonMajorList);
    }
    public void getAllExam(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExamService examService = new ExamService();

        Response response = examService.getAllExamsWithoutPaging();
        Object data = response.getData();
        System.out.println("Ket qua tra ve cua ham getAllExam la: " + response.getStatusCode());
        ExamDTO examDTO = (ExamDTO) data;
        List<Exam> examList = examDTO.getExams();
        String jsonExamList = gson.toJson(examList);
        System.out.println(jsonExamList);
        req.setAttribute("jsonExamList", jsonExamList);
    }
    public void getAllSubject(HttpServletRequest req,HttpServletResponse resp){
        try{
            SubjectService subjectService = new SubjectService();
            Response response = subjectService.getAllSubjectWithoutPagination();
            SubjectDTO data =  (SubjectDTO) response.getData();
            List<Subject> listSubject = data.getSubjects();
            String jsonSubjectList = gson.toJson(listSubject);
            System.out.println(jsonSubjectList);
            req.setAttribute("jsonSubjectList",jsonSubjectList);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Lỗi o ham getAllSubject testController");
        }
    }
    public void deleteTestByTestID(HttpServletRequest req,HttpServletResponse resp) {
        try {
           TestService2Loc testService2Loc = new TestService2Loc();
           Response respTestDel = testService2Loc.deleteTestByTestID(Integer.parseInt(req.getParameter("testId")));
           System.out.println("Ket qua tra ve cua ham deleteTestByTestID la: " + respTestDel.getStatusCode());
           
        } catch (Exception e) {
            System.out.println("Loi o ham deleteTestByTestID controller" + e);
        }
    }
    public void exportResultTestExcel(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("1");
        testService = new TestService();
        System.out.println("2");
        int testId = Integer.parseInt(req.getParameter("testId"));
        System.out.println("IdTest can exportTest result la: " + testId);
        Response respResultTest = testService.getListResultOfStudentByTest(testId);
        System.out.println("resp respResultTest: " + respResultTest.getStatusCode());
        Object objectRespResultTest = respResultTest.getData();
        ResultDTO resultDTO = (ResultDTO) objectRespResultTest;
        List<Student> studentList = resultDTO.getStudentList();
        List<Result> resultList = resultDTO.getResultList();

        System.out.println("Ket qua tra ve cua export result test: " + respResultTest.getStatusCode());
        System.out.println(studentList);
        System.out.println(resultList);
        String exportExcelDir = getServletContext().getRealPath("/exportExcel"); // Lấy đường dẫn tuyệt đối từ folder tương đối trong dự án của mình
        String excelFilePath = exportExcelDir + "/exportResultTest.xlsx";

        try {
            System.out.println("Da vo try");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Diem thi");
            writeHeaderLineExportTestResult(sheet);
            writeDataExportTestResult(studentList, resultList, workbook, sheet);
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();

        }catch (Exception e) {
            System.out.println("Loi ham exportResultTestExcel controller");
            System.out.println(e);
        }
    }
    private void writeHeaderLineExportTestResult(XSSFSheet sheet) {
        Row titleRow = sheet.createRow(0);
        Cell title = titleRow.createCell(0);
        title.setCellValue("Điểm thi của sinh viên");
        Row headerRow = sheet.createRow(1);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("STT");
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("MSSV");
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Họ tên lót");
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Tên");
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Điểm");
    }
    private void writeDataExportTestResult(List<Student> studentList, List<Result> resultList, XSSFWorkbook workbook, XSSFSheet sheet) {
        int rowCount = 2;
        int count = 1;
        for (Student student : studentList) {
            int studentID = student.getStudentID();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(count++);
            cell = row.createCell(columnCount++);
            cell.setCellValue(studentID);
            cell = row.createCell(columnCount++);
            cell.setCellValue(firstName);
            cell = row.createCell(columnCount++);
            cell.setCellValue(lastName);

            // Tìm kết quả của sinh viên trong danh sách kết quả
            for (Result result : resultList) {
                if (result.getStudentID() == studentID) {
                    double totalPoint = result.getTotalPoint();
                    cell = row.createCell(columnCount++);
                    cell.setCellValue(totalPoint);
                    break; // Sau khi tìm thấy kết quả của sinh viên, thoát vòng lặp
                }
            }
        }
    }
    protected void downloadExcelFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String exportExcelDir = getServletContext().getRealPath("/exportExcel");
        String excelFilePath = exportExcelDir + "/exportResultTest.xlsx";
        System.out.println("Da vo download file ne: " + excelFilePath);

        File file = new File(excelFilePath);
        if (!file.exists()) {
            System.out.println("File khong ton tai");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // Trả về URL của file
        String fileUrl = request.getContextPath() + "/exportExcel/exportResultTest.xlsx";
        response.setContentType("application/json");
        response.getWriter().write("{\"fileUrl\": \"" + fileUrl + "\"}");
    }
    public void getTestById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TestService2Loc testService2Loc = new TestService2Loc();
        Response response =  testService2Loc.getTestById(Integer.parseInt(req.getParameter("testId")));
        Object data = response.getData();
        System.out.println("code tra ve la: " + response.getStatusCode());
        Test test = (Test) data;
        String jsonTest = gson.toJson(test);
        req.setAttribute("jsonTest", jsonTest);
        System.out.println("Ket qua cua json test la: " + jsonTest);
        getAllSubject(req, resp);
        getAllTest(req, resp);
        req.getRequestDispatcher("/admin/testEdit.jsp").forward(req, resp);
    }
    
}
