package controller.admin;

import DTO.*;
import com.google.gson.Gson;
import entity.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import payload.response.Response;
import service.QuestionBankService;
import service.QuestionEstimateLevelService;
import service.SubjectService;
import service.instructor.OptionService;
import service.instructor.QuestionService;
import service.instructor.TestDetailService;
import service.instructor.TestService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.plaf.synth.SynthStyle;

import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@MultipartConfig()
@WebServlet(name = "QuestionBankController", urlPatterns = { "/admin/questionbank","/admin/countLevelQuestion" })

public class QuestionBankController extends HttpServlet {
    private QuestionBankService questionBankService;
    private Response response;
    private SubjectService subjectService;
    private OptionService optionService;
    private QuestionService questionService;
    private TestService testService;
    private TestDetailService testDetailService;
    private QuestionEstimateLevelService questionEstimateLevelService = new QuestionEstimateLevelService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            System.out.println("Da vo null");

            getAllQuestion(req, resp);
            return; // Dừng việc thực hiện các câu lệnh sau khi gọi getAllMajor()
        }
        switch (action) {
            case "editquestion":
                getQuestionById(req, resp);
                break; // Dừng việc thực hiện các trường hợp khác sau khi gọi getMajorById()
            case "addquestion":
                getAllNameSubjectAndId(req, resp);
                req.getRequestDispatcher("/admin/questionBankAdd.jsp").forward(req, resp);
                break;
            default:
                getAllQuestion(req, resp);
                System.out.println("Da vo mac dinh");
                break; // Dừng việc thực hiện switch-case
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if(path.equals("/admin/countLevelQuestion")){
            questionEstimateLevelService.estimate();
        }else{
            String action = req.getParameter("action");
            switch (action) {
                case "delete":
                    System.out.println("da vo controller delete QuestionBank");
                    try {
                        deleteQuestion(req, resp);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "update":
                    System.out.println("da vo controller update QuestionBank");
                    updateQuestionBank(req, resp);
                    break;
                case "add":
                    System.out.println("da vo controller create QuestionBank");
                    createQuestion(req, resp);
                    break;
                case "addbyexcel":
                    System.out.println("đã vô add by excel");
                    String filepath = uploadFileExcel(req, resp);
                    addQuestionByExcel(req, resp, filepath);
                    break;
                case "export-question":
                    System.out.println("đã vô export question");
                    exportQuestion(req, resp);
                    downloadExcelFile(req, resp);
                    break;
                case "export-result":
                    System.out.println("da vo export result");
                    exportResultTestExcel(req, resp);
                    break;
                case "export-test":
                    System.out.println("da vo export test");
                    shuffleTest(req, resp);
                    break;
            }
        }
    }

    public void getAllQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        questionBankService = new QuestionBankService();
        Response response = questionBankService.getAllQuestion();
        System.out.println("Ket qua tra ve cua getAllQuestion la: " + response.getStatusCode());
        Object data = response.getData();
        List<Question> questionBankList = ((QuestionBankDTO) data).getQuestions();
        Gson gson = new Gson();
        String questionBankListJson = gson.toJson(questionBankList);
        req.setAttribute("questionBankListJson", questionBankListJson);
        System.out.println(questionBankListJson);
        getAllNameSubjectAndId(req, resp);
        req.getRequestDispatcher("/admin/questionBank.jsp").forward(req, resp);
    }

    public void deleteQuestion(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        questionBankService = new QuestionBankService();
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        Response response = questionBankService.deleteQuestion(questionId);
        System.out.println("Ket qua tra ve cua ham deleteQuestion la: " + response.getStatusCode());
    }

    private void getAllNameSubjectAndId(HttpServletRequest req, HttpServletResponse resp) {
        subjectService = new SubjectService();
        Response responseSubject = subjectService.getAllNameSubject();
        System.out.println("Ket qua tra ve cau subject name la: " + responseSubject.getStatusCode());
        Object dataSubject = responseSubject.getData();
        List<Subject> subjectListNameAndId = ((SubjectDTO) dataSubject).getSubjects();
        req.setAttribute("subjectListNameAndId", subjectListNameAndId);
        Gson gson = new Gson();
        req.setAttribute("subjectJson", gson.toJson(subjectListNameAndId));
    }

    public void getQuestionById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        questionBankService = new QuestionBankService();
        int questionId = Integer.parseInt(req.getParameter("questionid"));
        Response response = questionBankService.getQuestionById(questionId);
        System.out.println("Ket qua tra ve cua ham getQuestionById la: " + response.getStatusCode());
        Object data = response.getData();
        List<Question> list = ((QuestionBankDTO) data).getQuestions();
        Gson gson = new Gson();
        String jsonGetQuestionById = gson.toJson(list);
        System.out.println(jsonGetQuestionById);
        req.setAttribute("jsonGetQuestionById", jsonGetQuestionById);
        getAllNameSubjectAndId(req, resp);
        req.getRequestDispatcher("/admin/questionBankEdit.jsp").forward(req, resp);
    }

    public void updateQuestionBank(HttpServletRequest req, HttpServletResponse resp) {
        String questionDetail = req.getParameter("question");
        questionBankService = new QuestionBankService();

        System.out.println("Đã chạy vào hàm update question bank controller");
        System.out.println(questionDetail);

        // Sử dụng Gson để chuyển đổi chuỗi JSON thành đối tượng Question
        Gson gson = new Gson();
        Question question = gson.fromJson(questionDetail, Question.class);
        Response responseUpdate = questionBankService.updateQuestion(question);
        int questionId = question.getQuestionID();
        deleteAllOptionByQuestionId(questionId);
        List<Option> optionList = question.getOptions();
        createOption(optionList, questionId);
        System.out.println("Ket qua tra ve cua updateQuestion la: " + responseUpdate.getStatusCode());
    }
    private void createOption(List<Option> optionList, int questionId) {
        OptionService optionService = new OptionService();
        for (Option option : optionList) {
            // Gửi yêu cầu tạo tùy chọn đến dịch vụ
            Response response = optionService.createOption_loc(option.getOptionID(), questionId, option.getTitle());

            // Kiểm tra kết quả trả về từ dịch vụ
            if (response.getStatusCode() == 200) {
                System.out.println("Tùy chọn đã được tạo thành công");
            } else {
                System.out.println("Đã xảy ra lỗi khi tạo tùy chọn");
            }
        }
    }
    private void deleteAllOptionByQuestionId(int questionId) {
        optionService = new OptionService();
        Response responseDel = optionService.deleteAllOptionByQuestionId(questionId);
        System.out.println("Ket qua tra ve cua deletAll" + responseDel.getStatusCode());
    }

    private void createOption(List<Option> optionList, int questionIdMax, int idCorrectTemp) {
        OptionService optionService = new OptionService();
        questionBankService = new QuestionBankService();
        Response response = new Response();
        int optionMax = 0;
        for (Option option : optionList) {
            response = optionService.createOption_loc(questionIdMax, option.getTitle());
            if(option.getOptionID() == idCorrectTemp) {
                System.out.println("Co cap nhat");
                optionMax = (int) (optionService.getIDOptionMax().getData());
                System.out.println("ID correct answer se duoc cap nhat: " + optionMax);
                questionBankService.updateCorrectAnswer(optionMax, questionIdMax);
            } else {
                System.out.println("Khong cap nhat");
            }
            if(response.getStatusCode() == 200) {
                System.out.println("Them option thanh cong createOption QuesBa Control");
            } else  {
                System.out.println("Them option that bai QuesBa Control");
            }
        }
    }

    public void createQuestion(HttpServletRequest req, HttpServletResponse resp) {
        questionService = new QuestionService();
        questionBankService = new QuestionBankService();
        String questionDetail = req.getParameter("question");

        Gson gson = new Gson();
        Question question = gson.fromJson(questionDetail, Question.class);
        Response responseCreateQuestion = questionService.createQuestion(question);
        System.out.println("Ket qua tra ve cua createQuestion" + responseCreateQuestion.getData());
        int questionIdTemp = question.getQuestionID();
        System.out.println("Id cau hoi temp la: " + questionIdTemp);
        int idCorrectTemp = question.getCorrectAnswer();
        int questionIdMax = (int) questionBankService.getIDQuestionMax().getData();
        System.out.println("Id cau hoi moi nhat la: " + questionIdMax);
        List<Option> optionList = question.getOptions();
        createOption(optionList, questionIdMax, idCorrectTemp);
    }


    public void addQuestionByExcel(HttpServletRequest req, HttpServletResponse resp, String filePath) throws ServletException, IOException {
        try{
            questionService = new QuestionService();
            System.out.println("Duong dan de lay file insert la; " + filePath);
            System.out.println("realPath:" + filePath);
            HttpSession httpSession = req.getSession();
            int employeeId = (int) httpSession.getAttribute("EmployeeID");
            Response responseCreateQuestionByExcel = questionService.createQuestionExcel(filePath, employeeId);
            System.out.println("Ket qua tra ve cua createQuestionByExcel" + responseCreateQuestionByExcel.getStatusCode());
        } catch (Exception e) {
            System.out.println("Loi o ham addQuestionByExcel controller" + e);
        }
    }
    
    protected String uploadFileExcel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đường dẫn tới thư mục lưu file trên máy chủ
        System.out.println("1");
        String uploadDir = getServletContext().getRealPath("/uploads"); // Lấy đường dẫn tuyệt đối từ folder tương đối trong dự án của mình
        System.out.println("duong dan la: " + uploadDir);

        // Tạo thư mục lưu trữ file nếu nó không tồn tại
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Lấy file từ request
        Part filePart = req.getPart("file_excel");
        String fileName = getFileName(filePart);
        System.out.println("File name upload: " + fileName );
        String filePath = uploadDir + "/" + fileName;


        // Ghi file vào thư mục lưu trữ
        OutputStream out = null;
        InputStream fileContent = null;
        try {
            out = new FileOutputStream(new File(uploadDir + File.separator + fileName));
            fileContent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            System.out.println("File đã được ghi vào thư mục lưu trữ thành công.");
        } catch (FileNotFoundException e) {
            System.out.println("Xảy ra lỗi khi upload: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Xảy ra lỗi khi ghi file: " + e);
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    System.out.println("Đã đóng OutputStream.");
                } catch (IOException e) {
                    System.out.println("Xảy ra lỗi khi đóng OutputStream: " + e);
                    e.printStackTrace();
                }
            }
            if (fileContent != null) {
                try {
                    fileContent.close();
                    System.out.println("Đã đóng InputStream.");
                } catch (IOException e) {
                    System.out.println("Xảy ra lỗi khi đóng InputStream: " + e);
                    e.printStackTrace();
                }
            }
        }
        System.out.println("File đã được upload thành công.");
        resp.sendRedirect("/Project/admin/questionbank");
        return filePath;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public void exportQuestion(HttpServletRequest req, HttpServletResponse resp) {
        questionBankService = new QuestionBankService();
        Response respGetAllQuestion = questionBankService.getAllQuestion();
        Object objectQuestionBank = respGetAllQuestion.getData();
        List<Question> questionBankList = ((QuestionBankDTO) objectQuestionBank).getQuestions();
        System.out.println("Ket qua tra ve cua question TRong export: " + respGetAllQuestion.getStatusCode());
        System.out.println(questionBankList);
        String exportExcelDir = getServletContext().getRealPath("/exportExcel"); // Lấy đường dẫn tuyệt đối từ folder tương đối trong dự án của mình
        String excelFilePath = exportExcelDir + "/exportQuestion.xlsx";
        try {
            System.out.println("Da vo try");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reviews");
            writeHeaderLine(sheet);
            writeDataLines(questionBankList, workbook, sheet);
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            System.out.println("Loi export question bank excel");
            System.out.println(e);
        }
    }
    private void writeHeaderLine(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Nội dung câu hỏi");
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("ID giáo viên");
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Tên môn học");
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Đô khó");
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Lựa chọn");
   }
   private void writeDataLines(List<Question> questionBankList, XSSFWorkbook workbook, XSSFSheet sheet) {
        int rowCount = 1;
        for(Question question : questionBankList) {
            Subject subject = question.getSubject();
            int employeeId = question.getEmployeeID();
            String nameSubject = subject.getSubjectName();
            String title = question.getTitle();
            int correctAnswer = question.getCorrectAnswer();
            List<Option> optionList = question.getOptions();
            String level = String.valueOf(question.getLevel());

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(title);
            cell = row.createCell(columnCount++);
            cell.setCellValue(employeeId);
            cell = row.createCell(columnCount++);
            cell.setCellValue(nameSubject);
            cell = row.createCell(columnCount++);
            cell.setCellValue(level);
            for(Option option : optionList) {
                int optionId = option.getOptionID();
                String optionTitle = option.getTitle();
                cell = row.createCell(columnCount++);
                cell.setCellValue(optionTitle);
                if(optionId == correctAnswer) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cell.setCellStyle(cellStyle);
                    System.out.println("CO to mau");
                } else {
                    System.out.println("K to mau");
                }
            }

        }
   }
    protected void downloadExcelFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String exportExcelDir = getServletContext().getRealPath("/exportExcel");
        String excelFilePath = exportExcelDir + "/exportQuestion.xlsx";
        System.out.println("Da vo download file ne: " + excelFilePath);

        File file = new File(excelFilePath);
        if (!file.exists()) {
            System.out.println("File khong ton tai");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Trả về URL của file
        String fileUrl = request.getContextPath() + "/exportExcel/exportQuestion.xlsx";
        response.setContentType("application/json");
        response.getWriter().write("{\"fileUrl\": \"" + fileUrl + "\"}");
    }


    public void exportFullResultTestExcelByExam(HttpServletRequest req, HttpServletResponse resp, int idExam) {

    }
    public void exportResultTestExcel(HttpServletRequest req, HttpServletResponse resp) {
        testService = new TestService();
        Response respResultTest = testService.getListResultOfStudentByTest(1);
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

    private TestDTO getTestAndQuestionInTestByIdTest(int testID) {
        testService = new TestService();
        Response response = testService.getTestAndQuestionInTestByIdTest(testID);
        System.out.println("Ket qua tra ve cua getTestAndQuestionInTestByIdTest resp la: " + response.getStatusCode());
        TestDTO testDTO = (TestDTO) response.getData();
        return testDTO;
    }
    public void shuffleTest(HttpServletRequest req, HttpServletResponse resp) {
        Gson gson = new Gson();
        testDetailService = new TestDetailService();
        testService = new TestService();
        TestDTO originalTestDTO = getTestAndQuestionInTestByIdTest(1);
        System.out.println("TRc json test");
//        System.out.println("Json test la: " + gson.toJson(originalTestDTO.getTest()));
        List<Question> originalquestionList = originalTestDTO.getQuestionList();
        List<TestDTO> testDTOList = new ArrayList<>();
        int numShuffle = 4;
        for(int i = 1; i < numShuffle; i++) {
            List<Question> shuffleQuestionList = new ArrayList<>(originalquestionList);
            Collections.shuffle(shuffleQuestionList);
            for(Question question : shuffleQuestionList) {
                List<Option> shuffleOptionList = new ArrayList<>(question.getOptions());
                Collections.shuffle(shuffleOptionList);
            }
            TestDTO testDTO = new TestDTO();
            testDTO.setQuestionList(shuffleQuestionList);
            testDTOList.add(testDTO);
        }
        int count = 1;
        int idMax = 0;
        for(TestDTO testDTO : testDTOList) {
            System.out.println("Danh sách câu hỏi sau lần sắp xếp thứ " + count++ + ": ");
            System.out.println("ID cua bai thi duoc lay tu la: " + originalTestDTO.getTest().getTestID());
            Response response1 = testService.insertTestToTest(originalTestDTO.getTest(), originalTestDTO.getTest().getTestID());
            System.out.println("Ket qua tra ve cua insertTestToTest al: " + response1.getStatusCode());
            Response idResp = testService.getIDTestMax();
            idMax = (int)idResp.getData();
            System.out.println("id tang tu dong la: " + idMax);
            List<Question> questions = testDTO.getQuestionList();
            // lay ra cac id tang tu dong trong database vua moi them
            for(Question q: questions) {
                System.out.println("idMax: " + idMax + " - question: " + q.getQuestionIdBackup());
            testDetailService.insertTestIntoTestDetail(idMax, q.getQuestionIdBackup());
            }
        }

    }

}
