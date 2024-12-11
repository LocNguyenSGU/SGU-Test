<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<% String contextPath = request.getContextPath();%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="assets/css/fixCss.css" />
    <link rel="stylesheet" href="assets/css/questionBank.css" />
    <link rel="stylesheet" href="assets/css/studentAdd.css" />
  </head>

  <body>
  <style>
        .details {
            grid-template-columns: 1fr;
        }
        </style>
    <div class="container">
      <!-- =============== Navigation ================ -->
      <div class="navigation">
        <ul>
          <li>
            <a href="#" style="display:flex;align-items:center;">
                <span style="width:45px;height:45px;margin-left:25%">
                    <img style="width:100%;" src="../assets/imgs/SGU-LOGO.png"/>
                </span>
              <span class="title" style="font-size:20px;">SGU</span>
            </a>
          </li>

          <li >
            <a href="/Project/admin/dashboard">
              <span class="icon">
                <ion-icon name="home-outline"></ion-icon>
              </span>
              <span class="title">Tổng quan</span>
            </a>
          </li>
<li>
                                                    <a href="/Project/admin/employee">
                                                      <span class="icon">
                                                        <i class="fa-solid fa-chalkboard-user" style="font-size: 25px"></i>
                                                      </span>
                                                      <span class="title">Giảng Viên</span>
                                                    </a>
                                         </li>
                              <li class="active">
                                              <a href="/Project/admin/subject">
                                                      <span class="icon">
                                                        <i class="fa-solid fa-book" style="font-size: 25px;"></i>
                                                      </span>
                                                      <span class="title">Môn học</span>
                                                    </a>
                                         </li>
                                        <li>
                                          <a href="/Project/admin/student">
                                            <span class="icon">
                                              <ion-icon name="people-outline"></ion-icon>
                                            </span>
                                            <span class="title">Học Sinh</span>
                                          </a>
                                        </li>
                              <li>
                                <a href="/Project/admin/exam">
                                  <span class="icon">
                                    <ion-icon name="chatbubble-outline"></ion-icon>
                                  </span>
                                  <span class="title">Kỳ thi</span>
                                </a>
                              </li>
                              <li>
                                <a href="/Project/admin/test">
                                  <span class="icon">
                                    <i class="fa-regular fa-file-lines" style="font-size: 25px"></i>
                                  </span>
                                  <span class="title">Bài thi</span>
                                </a>
                              </li>
                               <li>
                                 <a href="/Project/admin/thongketheophodiem">
                                   <span class="icon">
                                     <i class="fa-solid fa-chart-simple" style="font-size: 25px;"></i>
                                   </span>
                                   <span class="title">Phổ điểm</span>
                                 </a>
                               </li>
                              <li>
                                <a href="/Project/admin/questionbank">
                                  <span class="icon">
                                    <ion-icon name="help-outline"></ion-icon>
                                  </span>
                                  <span class="title">Ngân hàng câu hỏi</span>
                                </a>
                              </li>
                              <li>
                                  <a href="/Project/admin/major">
                                    <span class="icon">
                                    <i class="fa-solid fa-layer-group"></i>
                                     </span>
                                     <span class="title">Chuyên ngành</span>
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



          <div class="hello">
            <div class="user">
              <img src="assets/imgs/lecture.jpg" alt="" />
            </div>
            <p>Hi, lecture</p>
          </div>
        </div>

        <!-- ================ Test List && Diagram ================= -->
        <div class="details">
          <div class="recentTests">
            <div class="cardHeader">
              <h2>Thêm môn học</h2>
            </div>

            <div class="form-add-exam">
                <form action="/Project/admin/subject?action=create" id="myForm" method="post"">
                  <div class="form-body">

                                          <label for="name">Tên Môn Học</label><br>
                                          <input class="create_student_input" type="text" name="namesubject" id="name"><br>
                    </div>
                     <div class="form-body">

                                                              <label for="email">Mô Tả</label><br>
                                                              <textarea id="message" name="descrpition" rows="4" cols="78">

                                                                      </textarea><br>
                                        </div>
                    <div class="form-group">
                      <label for="MajorID">Chuyên Ngành </label><br>
                       <select id="MajorID" class="create_student_input" name="MajorID" class="Major_select" style="background-color: white;padding:5px;height:40px   ;font-family: Ubuntu, sans-serif;">
                       <c:forEach var="major" items="${Majors}">
                         <option style="padding:5px;height:40px" value="${major.getMajorID()}">${major.getName()}</option>
                        </c:forEach>
                        </select>
                     </div>
                    <div class="group-btn">
                                  <a href="/Project/admin/subject">
                                    <button class="btn" type="button">Quay về</button>
                                  </a>
                                  <button class="btn btn-add btn-edit-exam">Tạo</button>
                    </div>
                  </div>
                </form>
              </div>


          </div>
        </div>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- =========== Scripts =========  -->
    <script>
$(document).ready(function() {
                     $('#myForm').submit(function(e) {
                     e.preventDefault(); // Ngăn chặn việc gửi biểu mẫu một cách thông thường
                     let inputs = document.querySelectorAll(".create_student_input");
                    for (let input of inputs) {
                    if (!input.value.trim()) {
                        alert("Vui lòng nhập đầy đủ thông tin.");
                        input.focus();
                        return;
                    }
                }
                let namesubject = document.querySelector('input[name="namesubject"]');
                let descrption = document.getElementById("message");
                let MajorId = document.querySelector('#MajorID');
                if(!namesubject.value.trim()){
                    alert("Vui lòng tên môn học");
                    namesubject.focus();
                    return;
                }
                if(!descrption.value.trim()){
                    alert("Vui lòng nhập họ ");
                    descrption.focus();
                    return;
                }
                if(!MajorId.value.trim()){
                    alert("Vui lòng nhập tên");
                    MajorId.focus();
                    return;
                }             // Lấy dữ liệu từ biểu mẫu
                              var formData = {
                                  namesubject: namesubject.value,
                                  descrpition:  descrption.value,
                                  MajorID:MajorID.value
                              };
                              console.log(namesubject.value, descrption.value,MajorID.value);

                              $.ajax({
                                  type: 'POST',
                                  url: '/Project/admin/subject?action=create',
                                  data: formData,
                                  success:function(data) {
                                       console.log(data);
                                   }
                              })
                              .done(function(data) {
                                alert("Cập nhật thông tin thành công");
                              })
                          });
                      });


  </script>
    <script src="assets/js/main.js"></script>
    <script src="assets/js/major.js"></script>
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

    <!-- ====== ionicons ======= -->
    <script
      src="https://kit.fontawesome.com/0dfb1263b2.js"
      crossorigin="anonymous"
    ></script>
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
