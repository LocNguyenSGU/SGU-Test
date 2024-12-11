<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="entity.Employee" %>
<%@ page import="entity.Major" %>
<%@ page import="java.io.PrintWriter" %>
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
<li class="active">
                                                    <a href="/Project/admin/employee">
                                                      <span class="icon">
                                                        <i class="fa-solid fa-chalkboard-user" style="font-size: 25px"></i>
                                                      </span>
                                                      <span class="title">Giảng Viên</span>
                                                    </a>
                                         </li>
                              <li>
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
              <h2>Cập nhật và xem thông tin giảng viên</h2>
            </div>

            <div class="form-add-exam">
                <form action="/Project/admin/employee?action=update" id="myForm" method="post" >
                  <div class="form-body">
                                                              <input type="hidden" name="employeeID" value='${employee.getEmployeeID()}'>
                                                              <label for="email">FirstName</label><br>
                                                              <input class="create_student_input" type="text" name="firstname" id="name" value="${employee.getFirstName()}"><br>

 </div>
                    <div class="form-body">
                                                               <label for="name">LastName</label><br>
                                                              <input class="create_student_input" type="text" name="lastname"  id="name"  value="${employee.getLastName()}"><br>

                    </div>
                     <div class="form-body">
                                                                                   <label for="">phone</label><br>
                                                                                  <input class="create_student_input" type="text" name="phone"  id="name"  value="${employee.getPhone()}"><br>
                        </div>
                        <div class="form-body">
                                                                                       <label for="name">Email</label><br>
                                                                                      <input class="create_student_input" type="text" readonly value="${employee.getEmail()}"><p style="color:red">Không thể chỉnh sửa</p><br>

                                            </div>
                    <div class="form-body">
                        <label for="email">Gender</label><br>
                        <label for="Nam">Man</label>
                        <% Employee employee = (Employee) request.getAttribute("employee"); %>
                        <input class="create_student" type="radio" name="gender" value="true"<%= employee.getGender() ? "checked" : "" %> id="Nam">
                        <label for="Nu">Women</label>
                        <input class="create_student" type="radio" name="gender" value="false" <%= !employee.getGender() ? "checked" : "" %> id="Nu"><br>
                    </div>

                     <div class="form-group">
                                          <label for="birthday">BirthDay</label><br>
                                          <input type="date" id="birthday" name = "birthday" value='${employee.getDateOfBirth()}'><br>

                    </div>
                    <div class="form-group">
                      <label for="MajorID">Chuyên ngành</label><br>
                       <select id="MajorID" class="create_student_input" name="MajorID" class="Major_select" style="background-color: white;padding:5px;height:40px   ;font-family: Ubuntu, sans-serif;">
                       <c:forEach var="major" items="${majors}">
                    <option style="padding:5px;height:40px" ${employee.getMajorID() == major.getMajorID() ? 'selected' : ''} value="${major.getMajorID()}">${major.getName()}</option>
                         </c:forEach>
                        </select>
                     </div>

                    <div class="group-btn">
                                  <a href="/Project/admin/employee">
                                    <button class="btn" type="button">Quay về</button>
                                  </a>
                                  <button class="btn btn-add btn-edit-exam">Cập nhật</button>
                    </div>
                  </div>
                </form>
              </div>


          </div>
        </div>
      </div>
    </div>

    <!-- =========== Scripts =========  -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                let EmployeeID = document.querySelector('input[name="employeeID"]');
                let firstname = document.querySelector('input[name="firstname"]');
                let lastname = document.querySelector('input[name="lastname"]');
                let phone = document.querySelector('input[name="phone"]');
                let birthday = document.querySelector('input[name="birthday"]');
                let major = document.querySelector('#MajorID');
                 let gender = document.querySelector('input[name="gender"]:checked');

                if(!phone.value.trim()){
                    alert("Vui lòng nhập số điện thoại");
                    sdt.focus();
                    return;
                }
                if(!firstname.value.trim()){
                    alert("Vui lòng nhập họ ");
                    Ho.focus();
                    return;
                }
                if(!lastname.value.trim()){
                    alert("Vui lòng nhập tên");
                    Ten.focus();
                    return;
                }
                if(birthday.value){
                 var dateInput = birthday.value;
                            var hiddenInput = document.createElement("input");
                            hiddenInput.setAttribute("type", "hidden");
                            hiddenInput.setAttribute("name", "isoDate");
                            hiddenInput.setAttribute("value",dateInput);
                            document.getElementById("myForm").appendChild(hiddenInput);
                }


                              // Lấy dữ liệu từ biểu mẫu
                              var formData = {
                                  employeeID: EmployeeID.value,
                                  isoDate: dateInput,
                                  gender:gender.value,
                                  firstname : firstname.value,
                                  lastname : lastname.value,
                                  MajorID: major.value,
                                  phone:phone.value,
                              };
                              console.log(EmployeeID.value, dateInput, gender.value,  firstname.value,lastname.value,major.value,phone.value)

                              $.ajax({
                                  type: 'POST',
                                  url: '/Project/admin/employee?action=update',
                                  data: formData,
                                  success:function(data) {
                                       alert(data)
                                   }
                              })
                              .done(function(data) {

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
