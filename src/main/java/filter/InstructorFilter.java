package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/instructor/*","/api/*"})
public class InstructorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false); // Không tạo session mới nếu không tồn tại

        Object roleIDObj = session.getAttribute("RoleID");
        Object employeeIDObj = session.getAttribute("EmployeeID");

        if (session == null || session.getAttribute("RoleID") == null || !session.getAttribute("RoleID").toString().equals("1") || session.getAttribute("EmployeeID") == null || session.getAttribute("MajorID") == null) {
            // Session không tồn tại hoặc không có thuộc tính studentID -> chuyển hướng đến trang đăng nhập
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            // Session tồn tại và có thuộc tính studentID -> tiếp tục xử lý yêu cầu
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}