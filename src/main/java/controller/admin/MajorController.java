package controller.admin;

import DTO.MajorDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.LocalDateTimeAdapter;
import entity.Major;
import payload.response.Response;
import service.MajorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name="MajorController" , urlPatterns={"/admin/major"})
public class MajorController extends HttpServlet {

    private MajorService majorService;
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            System.out.println("Da vo null");

            getAllMajor(req, resp);
            return; // Dừng việc thực hiện các câu lệnh sau khi gọi getAllMajor()
        }
        switch (action) {
            case "getMajor":
                System.out.println("Da vo getMajor");
                getMajorById(req, resp);
                break; // Dừng việc thực hiện các trường hợp khác sau khi gọi getMajorById()
            case "addmajor":
                req.getRequestDispatcher("/admin/majorAdd.jsp").forward(req, resp);
                break;
            default:
                getAllMajor(req, resp);
                System.out.println("Da vo mac dinh");
                break; // Dừng việc thực hiện switch-case
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "delete":
                System.out.println("da vo controller delete Major");
                try {
                    deleteMajor(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "add":
                    createMajor(req, resp);
                    break;
            case "update":
                try {
                    updateMajor(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }

    public void getAllMajor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        majorService = new MajorService();

        Response response = majorService.getAllMajorWithoutPaging();
        System.out.println("Ket qua  tra ve cau getAllMajor: " + response.getData());
        Object data = response.getData();
        MajorDTO majorDTO = (MajorDTO) data;
        List<Major> majorList = majorDTO.getMajors();

        String jsonMajorList = gson.toJson(majorList);
        System.out.println("Json la: " + jsonMajorList);

        req.setAttribute("jsonMajorList", jsonMajorList);
        req.getRequestDispatcher("/admin/major.jsp").forward(req, resp);
        System.out.println("da qua khoi ham controler");
    }

    public void createMajor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String name = req.getParameter("name");
        System.out.println("bien name: " + name);
        String description = req.getParameter("description");
        System.out.flush();
        System.out.println("bien description: " + description);
        System.out.flush();

        // Initialize MajorService if not already initialized
        if (majorService == null) {
            majorService = new MajorService();
        }

        // Call the service method to create the Major
       Response response =  majorService.createMajor(name, description);
        System.out.println("Ket qua tra ve cua ham create major la: " + response.getStatusCode());

        // Redirect back to the same URL to refresh the major list
        resp.sendRedirect(req.getContextPath() + "/admin/major");
    }
    public void getMajorById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        majorService = new MajorService();
        short majorId = Short.parseShort(req.getParameter("majorId"));
        Response response =  majorService.getMajorById(majorId);
        Object data = response.getData();
        System.out.println("code tra ve la: " + response.getStatusCode());
        Major major = new Major();
        if (data instanceof Major) {
            major = (Major) data;
            // Tiếp tục xử lý dữ liệu
        } else {
            System.out.println("datta khong phai la Major");
            // Xử lý trường hợp dữ liệu không phải là Major, chẳng hạn hiển thị thông báo lỗi hoặc xử lý dữ liệu khác
        }
        req.setAttribute("major", major);
        System.out.println("major desc la: " + major.getDescription());
        req.getRequestDispatcher("/admin/majorEdit.jsp").forward(req, resp);
    }

    public void updateMajor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        majorService = new MajorService();
        short majorId = Short.parseShort(req.getParameter("idMajor"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        majorService.updateMajor(majorId, name, description);
        resp.sendRedirect(req.getContextPath() + "/admin/major");
    }
    public void deleteMajor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        majorService = new MajorService();
        short majorId = Short.parseShort(req.getParameter("majorId"));
        majorService.deleteMajor(majorId);
    }


}
