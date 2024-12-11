package controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.ExamDTO;
import DTO.SpectrumDTO;
import com.google.gson.Gson;
import entity.Exam;
import payload.response.Response;
import service.ExamService;
import service.SubjectService;

import java.io.IOException;
import java.util.List;

@WebServlet( urlPatterns={"/admin/thongketheophodiem"} )
public class ThongKeTheoPhoDiemController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        Short id = null;

        if (idString != null) {
            try {
                id = Short.valueOf(idString);
            } catch (NumberFormatException e) {
                // Handle the error, e.g., log it or set an error message
                System.out.println("Invalid id format: " + idString);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid id format\"}");
                return;
            }
        }

        if (id != null) {
            getThongKeTheoPhoDiem(req, resp, id);
        } else {
            getPage(req, resp);
        }
    }
    public void getPage(HttpServletRequest req, HttpServletResponse resp){
        try {
            ExamService examService = new ExamService();
            Response response = examService.getAllExamsWithoutPaging();
            Object data = response.getData();
            System.out.println("Ket qua tra ve cua ham getAllExam la: " + response.getStatusCode());
            ExamDTO examDTO = (ExamDTO) data;
            List<Exam> examList = examDTO.getExams();
            req.setAttribute("ExamList", examList);
            req.getRequestDispatcher("/admin/thongke.jsp").forward(req, resp);
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Lỗi");
        }
    }
    public void getThongKeTheoPhoDiem(HttpServletRequest req, HttpServletResponse resp,short id){
        try {
            SubjectService subjectService = new SubjectService();
            Response response = subjectService.getSpectrum(id);
            Object data = response.getData();
            System.out.println("Ket qua tra ve cua ham thongke la: " + response.getData());
            SpectrumDTO spectrumDTO = (SpectrumDTO) data;
//            List<String> subjectNameList = spectrumDTO.getSubject();
//            List<Double> scorceList = spectrumDTO.getpoint();
            Gson gson = new Gson();
            String spectrumJson = gson.toJson(spectrumDTO);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // Gửi chuỗi JSON về client
            resp.getWriter().write(spectrumJson);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Lỗi");
        }
    }
}
