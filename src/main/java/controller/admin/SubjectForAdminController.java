package controller.admin;
import DTO.QuestionBankDTO;
import entity.Subject;
import DTO.SubjectDTO;
import DTO.MajorDTO;
import com.google.gson.Gson;
import entity.Major;
import payload.response.Response;
import repository.MajorRepository;
import service.MajorService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import repository.SubjectRepository;
import service.StudentService;
import service.SubjectService;
@WebServlet(name="SubjectForAdminController",urlPatterns = {"/admin/subject","/admin/addsubject","/admin/editsubject"})
public class SubjectForAdminController extends HttpServlet {
    private SubjectService subjectService = new SubjectService();
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        String path = req.getServletPath();
        switch(path){
            case "/admin/subject":
                getAllsubject(req,resp);
                 break;
            case "/admin/addsubject":
                addSubjectPage(req,resp);
                break;
            case "/admin/editsubject":
                editSubjectPage(req,resp);
                break;
         }
    }
    public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        System.out.println(path);
        String action = req.getParameter("action");
        switch (path) {
            case "/admin/subject":
                if (action != null) {
                    switch (action) {
                        case "create":
                            System.out.println("Đã vào hàm tạo subject");
                            String name = req.getParameter("namesubject");
                            String descrpition = req.getParameter("descrpition");
                            Short MajorID = Short.valueOf(req.getParameter("MajorID"));
                            Response response = subjectService.createSubject(name, descrpition, MajorID);
                            System.out.println(response.getData());
//                            resp.setContentType("text/html;charset=UTF-8"); // Sửa lỗi kiểu content
//                            resp.sendRedirect("/Project/admin/subject");
                            break;
                        case "update":
                            System.out.println("Đã vào hàm cập nhật subject");
                            Short subjectID = Short.valueOf(req.getParameter("subjectID"));
                            System.out.println("subject "+subjectID);
                            name = req.getParameter("namesubject");
                            descrpition = req.getParameter("descrpition");
                            MajorID = Short.valueOf(req.getParameter("MajorID"));
                            response = subjectService.updateSubject(subjectID,name, descrpition, MajorID);
                            System.out.println(response.getData());
                            resp.setContentType("text/html;charset=UTF-8"); // Sửa lỗi kiểu content
                            resp.sendRedirect("/Project/admin/subject");
                            break;
                    }
                }
        }
    }
    private void getAllsubject(HttpServletRequest req,HttpServletResponse resp){
        try{
            Response response = subjectService.getAllSubjectWithoutPagination();
            SubjectDTO data =  (SubjectDTO) response.getData();
            List<Subject> listSubject = data.getSubjects();
            MajorService majorService = new MajorService();
            Response majorResponse = majorService.getAllMajorWithoutPaging();
            MajorDTO majorData = (MajorDTO) majorResponse.getData();
            List<Major> listMajor = majorData.getMajors();
            req.setAttribute("listSubject",listSubject);
            req.setAttribute("listMajor",listMajor);
            req.getRequestDispatcher("subject.jsp").forward(req,resp);

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Lỗi");
        }

    }
    public void addSubjectPage(HttpServletRequest req, HttpServletResponse resp){
        try{
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            req.setAttribute("Majors",listMajors);
            req.getRequestDispatcher("subjectAdd.jsp").forward(req, resp);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
    public void editSubjectPage(HttpServletRequest req, HttpServletResponse resp){
        try{
            // Major
            MajorService majorService = new MajorService();
            Response dataMajor = majorService.getAllMajorWithoutPaging();
            MajorDTO majorDTO = (MajorDTO) dataMajor.getData();
            List<Major> listMajors = majorDTO.getMajors();
            Short SubjectID = Short.valueOf(req.getParameter("id"));
            System.out.println("id subject la "+SubjectID);
            // get Subject
            Response dataSubject = subjectService.getSubjectById(SubjectID);
            Subject subject = (Subject) dataSubject.getData();
            System.out.println("mo ta la"+subject.getDescription());
            req.setAttribute("Subject",subject);
            req.setAttribute("Majors",listMajors);
            req.getRequestDispatcher("subjectEdit.jsp").forward(req, resp);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

    }
}
