package controller.admin;
import DTO.EmployeeDTO;
import entity.Student;
import entity.Employee;
import entity.StudentVer2;
import DTO.MajorDTO;
import DTO.StudentDTO;
import com.google.gson.Gson;
import entity.Major;
import payload.response.Response;
import service.MajorService;
import service.StudentService;
import service.EmployeeService;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="EmployeeQuery", urlPatterns = {"/admin/queryController/employee","/admin/queryController/deleteEmployee"})
public class EmployeeQueryController  extends HttpServlet {
        private EmployeeService employeeService = new EmployeeService();
        public void doGet(HttpServletRequest req, HttpServletResponse resp){
            String path = req.getServletPath();
            System.out.println(path);
            switch (path) {
                case "/admin/queryController/employee":
                    try {
                        getAllEmployee(req, resp);
                    } catch (Exception e) {
                        System.out.println("Khong the truy cap");
                    }
                    break;
//                case "/admin/queryController/deleteEmployee":
//                    deleteEmployee(req, resp);
//                break;
//            case "/admin/editstudent":
//                short id = Short.parseShort(req.getParameter("id"));
//                editStudentPage(req,resp,id);
//                break;

            }
        }
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String path = req.getServletPath();
            switch (path) {
                case "/admin/queryController/deleteEmployee":
                    deleteEmployee(req, resp);
                    break;
            }
        }
    private void getAllEmployee(HttpServletRequest req,HttpServletResponse resp) {
        try {
            Response response = new Response();
            response = employeeService.getAllEmployeeWithoutPagination();
            EmployeeDTO employeeDTO = (EmployeeDTO) response.getData();
            List<Employee> listEmployee = employeeDTO.getEmployees();
            Gson gson = new Gson();
            String listEmployeeString = gson.toJson(listEmployee);
            // Đặt loại nội dung của phản hồi là "application/json"
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // Gửi chuỗi JSON về client
            resp.getWriter().write(listEmployeeString);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public void deleteEmployee(HttpServletRequest req, HttpServletResponse resp){
        try {
            // Đọc dữ liệu từ form data
            JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();
            // Truy cập dữ liệu JSON
            int employeeId = jsonObject.get("id").getAsInt();
            // Thực hiện xóa
            Response response = employeeService.deleteEmployee(employeeId);

            // Gửi phản hồi về client
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(new Gson().toJson(response)); // Gửi response về client
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                resp.getWriter().write("{\"message\": \"Invalid employee ID\"}"); // Gửi thông báo lỗi về client
            }
            catch (Exception xe) {
                System.out.println("Lỗi");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write("{\"message\": \"An error occurred while deleting employee\"}"); // Gửi thông báo lỗi về client
            }
            catch (Exception xe) {
                System.out.println("Lỗi");
            }

        }
    }
}
