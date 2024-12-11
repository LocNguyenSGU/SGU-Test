<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"> -->
    <link rel="stylesheet" href="./assets/css/login.css">
    <title>Đăng nhập</title>
</head>

<body>

    <div class="container" id="container">
        <div class="form-container sign-up">
            <form id="Employee">
                <h1>Giảng Viên</h1>
                <input type="email" id="emailEmployee" placeholder="Email" value="hoang.psham@example.com">
                <p id="emailErrorEmployee" style="color:red;display:none;"></p>
                <input type="password" id="passwordEmployee" placeholder="Password"
                       value="hoang_password">
                <p id="passwordErrorEmployee" style="color:red;display:none;"></p>
                <button>Đăng nhập</button>
            </form>
        </div>
        <div class="form-container sign-in">
            <form id="Student">
                <h1>Sinh Viên</h1>
                <input type="email" id="emailStudent" placeholder="Email">
                <p id="emailErrorStudent" style="color:red;display:none;"></p>
                <input type="password" id="passwordStudent" placeholder="Password">
                <p id="passwordErrorStudent" style="color:red;display:none;"></p>
                <button>Đăng nhập</button>
            </form>
        </div>
        <div class="toggle-container">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    <h1>Welcome Back!</h1>
                    <button class="hidden btn-animation" id="login" ><span>Bạn là sinh viên</span></button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1 id="page-logo">Welcome to SGU test</h1>
                    <button class="hidden btn-animation" id="register"><span>Bạn là giảng viên</span></button>
                </div>
            </div>
        </div>
    </div>

    <script src="./assets/js/login.js"></script>
</body>

</html>
