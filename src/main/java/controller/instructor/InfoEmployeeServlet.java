package controller.instructor;

import DTO.InfoEmployeeAndMajorDTO;
import service.instructor.InfoEmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="InfoEmployeeServlet", urlPatterns = {"/instructor/info"})
public class InfoEmployeeServlet extends HelperServlet{
    private final InfoEmployeeService infoEmployeeService = new InfoEmployeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        InfoEmployeeAndMajorDTO info = (InfoEmployeeAndMajorDTO) infoEmployeeService.getInfoEmployeeAndMajorDTOByIDEmployee(Integer.parseInt(req.getSession().getAttribute("EmployeeID").toString())).getData();
        req.setAttribute("instructor", info.getEmployee());
        req.setAttribute("majorName", info.getMajor().getName());
        req.getRequestDispatcher("/instructor/instructor-information.jsp").forward(req, resp);
    }
}
