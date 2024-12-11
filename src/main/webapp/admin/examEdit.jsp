<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="assets/css/exam.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

          <li>
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
                              <li class="active">
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
              <h2>Chỉnh sửa kì thi</h2>
            </div>

            <div class="form-add-exam">
                <form action="/Project/admin/exam?action=update" method="post">
                  <div class="form-body">
                      <input type="text" name="examId" id = "examId" value="${exam.getExamID()}" hidden><br>

                    <div class="form-group">
                      <label for="name">Tên</label><br>
                      <input type="text" name="name" id="name" value="${exam.getName()}"><br>
                    </div>
                    <div class="form-group">
                      <label for="dateStart">Ngày bắt đầu</label><br>
                      <input type="datetime-local" id="dateStart" name = "dateStart" value="${exam.getDateStart()}"><br>
                    </div>
                    <div class="form-group">
                      <label for="dateEnd">Ngày kết thúc</label><br>
                      <input type="datetime-local" id="dateEnd" name = "dateEnd" value="${exam.getDateEnd()}"><br>
                    </div>
                    <div class="form-group">
                      <label for="status">Trạng thái</label><br>
                      <select name="status" id="status">
                        <option value="NOT_STARTED" class="${exam.getStatus()}">Chưa bắt đầu</option>
                        <option value="IN_PROGRESS" class="${exam.getStatus()}">Đang diễn ra</option>
                        <option value="COMPLETED" class="${exam.getStatus()}">Đã hoàn thành</option>
                    </select>
                    </div>
                    <div class="group-btn">
                                  <a href="/Project/admin/exam">
                                    <button class="btn" type="button">Quay về</button>
                                  </a>
                                  <button class="btn btn-add btn-edit-exam">Chỉnh sửa</button>
                    </div>
                  </div>
                </form>
              </div>


          </div>
        </div>
      </div>
    </div>

    <!-- =========== Scripts =========  -->
    <script>
      let optionStatus = document.querySelectorAll("#status option");
      let status = "${exam.getStatus()}"; // Assuming this is how you get the status in your template engine
  
      optionStatus.forEach((option) => {
          if (option.value === status) {
              option.selected = true;
          }
      });
  </script>
    <script src="assets/js/main.js"></script>
    <script src="assets/js/major.js"></script>
    <script>
          $(document).ready(function() {
                  $('form').submit(function(e) {
                      e.preventDefault(); // Ngăn chặn việc gửi biểu mẫu một cách thông thường
                      if($('#name').val() == '' || $('#dateStart').val() == '' || $('#dateEnd').val() == '' || $('#status').val() == '') {
                        alert('Vui lòng nhập đầy đủ thông tin');
                        return;
                      }
                      if($('#dateStart').val() > $('#dateEnd').val()) {
                        alert('Ngày bắt đầu không được lớn hơn ngày kết thúc');
                        return;
                      }
                      // Lấy dữ liệu từ biểu mẫu
                      var formData = {
                          examId: $('#examId').val(),
                          name: $('#name').val(),
                          dateStart: $('#dateStart').val(),
                          dateEnd: $('#dateEnd').val(),
                          status: $('#status').val()
                      };
                      console.log(formData.examId , formData.name, formData.dateStart, formData.dateEnd, formData.status)

                      $.ajax({
                          type: 'POST',
                          url: '/Project/admin/exam?action=update',
                          data: formData,
                      })
                      .done(function(data) {
                          alert("Chỉnh sửa thành công");
                          window.location.href = '/Project/admin/exam';
                          
                      })
                  });
              });
        </script>

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
