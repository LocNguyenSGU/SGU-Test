<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html>
<head><title>Cú pháp cơ bản</title></head>
<body>
    <h1>Cú pháp cơ bản JSP</h1>
    <%! int day = 3; %>
    <%
        switch(day) {
            case 1:
                out.println("Chủ nhật");
                break;
            case 2:
                out.println("Thứ 2");
                break;
            case 3:
                out.println("Thứ 3");
                break;
            case 4:
                out.println("Thứ 4");
                break;
        }
    %>
</body>
</html>