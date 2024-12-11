<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="payload.response.Response" %>
<%@ page import="entity.Student"%>
<%@ page import="DTO.ExamDTO"%>
<%@ page import="entity.Exam"%>
<%
    Response resStudent = (Response) request.getAttribute("res");
    Response resExam = (Response) request.getAttribute("resExam");
    Student student = (Student) resStudent.getData();
    ExamDTO examDTO = (ExamDTO) resExam.getData();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>Dashboard</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="./assets/css/style.css"/>
    <link rel="stylesheet" href="./assets/css/student-dashboard.css"/>
  </head>

  <body>
    <div class="container">
    <div class="navigation">
            <ul>
              <li>
                <a href="/thitracnghiemsgu/student">
                  <span class="icon">
                    <ion-icon name="logo-apple"></ion-icon>
                  </span>
                  <span class="title">Examination management</span>
                </a>
              </li>

              <li>
                <a href="/Project/student">
                  <span class="icon">
                    <ion-icon name="home-outline"></ion-icon>
                  </span>
                  <span class="title">Tổng quan</span>
                </a>
              </li>


              <li>
                <a href="/Project/student/point">
                    <span class="icon">
                       <ion-icon name="podium-outline"></ion-icon>
                    </span>
                    <span class="title">Điểm thi</span>
                </a>
              </li>

              <li>
                <a href="/Project/student/tests">
                  <span class="icon">
                    <ion-icon name="calendar-outline"></ion-icon>
                  </span>
                  <span class="title">Thi</span>
                </a>
              </li>

              <li>
                <a id="logout">
                  <span class="icon">
                    <ion-icon name="log-out-outline"></ion-icon>
                  </span>
                  <span class="title">Đăng xuất</span>
                </a>
              </li>
            </ul>
          </div>

      <!-- ========================= Main ==================== -->
      <div class="main">
        <div class="topbar">
          <div class="toggle">
            <ion-icon name="menu-outline"></ion-icon>
          </div>

          <!-- <div class="search"> -- đừng xoá nhen, có thể dùng ở phần sau
                    <label>
                        <input type="text" placeholder="Search here">
                        <ion-icon name="search-outline"></ion-icon>
                    </label>
            </div> -->
         <div class="hello">
            <div class="user">
                <img src="./assets/img/lecture.jpg" alt=""/>
              </div>
              <p>Xin chào, Lai Tấn Tài</p>
         </div>

        </div>

        <!-- ======================= Cards ================== -->
        <div class="cardDashboard">
          <div class="cardProfile">
            <div>
              <p class="cardProfileName">Thông tin sinh viên</p>
              <div class="cardProfileDetail" style="display:flex;justify-content:space-between;align-items:top;">
                 <div class="cardProfileDetailText">
                    <div style="width:100%;display:flex;">
                        <div style="line-height:2.0;font-size:18px;width:50%;">
                            <p>MSSV</p>
                            <p>Họ và Tên</p>
                            <p>Ngày sinh</p>
                            <p>Giới tính</p>
                            <p>Số điện thoại</p>
                            <p>Email</p>
                            <p>Chuyên ngành</p>
                        </div>
                        <div style="line-height:2.0;font-size:18px;width:50%;">
                            <p><%= student.getStudentID()%></p>
                            <p><%= student.getLastName() + " " + student.getFirstName() %></p>
                            <p><%= student.getDateOfBirth()%></p>
                            <p><%= student.getGender() ? "Nam" : "Nữ"%></p>
                            <p><%= student.getPhone()%></p>
                            <p><%= student.getEmail()%></p>
                            <p><%= student.getMajorName()%></p>
                        </div>
                        <div></div>
                    </div>
                 </div>
                 <div class="cardProfileDetailImage" style="width:20%;">
                    <img src="./assets/img/lecture.jpg" alt="" style="width:100%;border-radius:50%;"/>
                 </div>
              </div>
            </div>

          </div>


          <div class="cardAnalyst">
            <div>
              <div class="cardAnalystName">Kết quả thi</div>
            </div>
            <div>
                <select id="selectExam" style="width:100%;height:40px;margin-top:10px;margin-bottom:10px;padding:3px;font-size:16px;">
                    <% for (Exam exam : examDTO.getExams()) { %>
                       <option value="<%= exam.getExamID() %>">
                           <%= exam.getName() %>
                       </option>
                    <% } %>
                </select>
            </div>
            <div style="margin-bottom:20px;display:flex;justify-content:flex-end;">
                <button class="export" id="png">Xuất PNG</button>
                <button class="export" id="excel" style="margin-left:5px;">Xuất Excel</button>
            </div>
            <div>
              <canvas id="myChart"></canvas>
            </div>
          </div>
        </div>
      </div>
    </div>
    
   
    </div>
    
    <!-- =========== Scripts =========  -->
    <script src="<c:url value="/assets/js/main.js"/>"></script>
    
    <!-- ====== ionicons ======= -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.1/chart.min.js" integrity="sha512-L0Shl7nXXzIlBSUUPpxrokqq4ojqgZFQczTYlGjzONGTDAcLremjwaWv5A+EDLnxhQzY5xUZPWLOLqYRkY0Cbw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script lang="javascript" src="https://cdn.sheetjs.com/xlsx-0.20.2/package/dist/xlsx.full.min.js"></script>
    <script src="./assets/js/student-dashboard.js"></script>
        <script>
         document.getElementById("logout").addEventListener("click", function(event){
                if(window.confirm("Bạn có muốn đăng xuất ?")){
                    localStorage.removeItem('info');
                    fetch('http://localhost:9999/Project/logout',{
                       method:'POST',
                       headers:{
                         'Content-Type': 'application/json'
                     },
                     body: JSON.stringify({
                         'email': 'fkahsfkj',
                     })
                    })
                    window.location.href = "http://localhost:9999/Project/login"
                }
            })
        </script>
    <script
        type="module"
        src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"
    ></script>
    <script
        nomodule
        src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"
    ></script>
</body>
</html>
