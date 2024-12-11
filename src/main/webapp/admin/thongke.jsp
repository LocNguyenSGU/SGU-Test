<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="assets/css/style.css" />
    <link rel="stylesheet" href="assets/css/fixCss.css" />
      <style>
            .option-exam {
                padding: 10px;
                background-color: #f9f9f9;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
                color: #333;
                width: 100%;
                max-width: 400px;
                transition: all 0.3s ease;
            }

            .option-exam:focus {
                border-color: #66afe9;
                box-shadow: 0 0 5px rgba(102, 175, 233, 0.6);
                outline: none;
            }

            .option-exam option {
                padding: 10px;
                background-color: #fff;
                color: #333;
            }

            .option-exam option:hover {
                background-color: #f1f1f1;
            }
        </style>
  </head>

  <body>
    <!-- <a href="testdb">testdb</a>
  <a href="instructor/student">Kiet test Student</a>
  <a href="instructor/question">Kiet test Question</a>
  <a href="instructor/test">Kiet test Test</a> -->
    <!-- <div id="searchQuery"></div> -->
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
                               <li class="active">
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

          <!-- <div class="search"> -- đừng xoá nhen, có thể dùng ở phần sau
                    <label>
                        <input type="text" placeholder="Search here">
                        <ion-icon name="search-outline"></ion-icon>
                    </label>
            </div> -->

          <div class="hello">
            <div class="user">
              <img src="assets/imgs/lecture.jpg" alt="" />
            </div>
            <p>Hi, lecture</p>
          </div>
        </div>

        <!-- ================ Test List && Diagram ================= -->
        <div class="details">
          <div class="recentTests hidden">
            <div class="cardHeader">
              <h2>Recent Test</h2>
              <a href="#" class="btn">View All</a>
            </div>
          </div>

          <!-- ================= Diagram ================ -->
          <div class="recentCustomers">
            <!-- <div class="cardHeader">
              <h2>Diagram result of test</h2>
            </div>
            <p>Diagram here</p> -->

            <div class="diagram" id="bieudo">
              <h1 >Phổ điểm</h1>
                <canvas id="myBarChart" width="220" height="100"></canvas>
              <span class="desc-diagram"
                >Biểu đồ phổ điểm</span
              >
              <div class="div-date">
              <p>Chọn kỳ thi</p>
                <select style="padding:10px;background-color:white,border:1px solid black" class="option-exam" onchange="loadPhoDiem(this.value)">
                   <c:forEach var="exam" items="${ExamList}">
                     <option style="padding:20px" value="${exam.getExamID()}">${exam.getName()}</option>
                   </c:forEach>
                 </select>
            </div>


          </div>
        </div>
      </div>
    </div>

    <!-- =========== Scripts =========  -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
 let myBarChart; // Khai báo biến global để lưu trữ biểu đồ

 async function loadPhoDiem(value) {
     try {
         const response = await fetch('/Project/admin/thongketheophodiem?id=' + value);
         if (!response.ok) {
             throw new Error('Không thể tải dữ liệu');
         }
         const data = await response.json();
         const labelss = data.Subject;
         const dataScore = data.point;
         console.log(labelss);
         console.log(dataScore);

             loadthongke(labelss, dataScore); // Gọi loadthongke sau khi dữ liệu được tải thành công

     } catch (error) {
         // Xử lý lỗi ở đây
         console.error('Có lỗi xảy ra khi tải dữ liệu:', error);
     }
 }

 function loadthongke(labels, dataScore) {
     // Kiểm tra xem biểu đồ đã được khởi tạo chưa
     if (myBarChart) {
         myBarChart.destroy();
     }

     const data = {
         labels: labels,
         datasets: [{
             label: 'Điểm trung bình',
             data: dataScore,
             backgroundColor: [
                 'rgba(255, 99, 132, 0.2)',

             ],
             borderColor: [
                 'rgba(255, 99, 132, 1)',

             ],
             borderWidth: 0.5,
             barThickness: 20
         }]
     };

     const config = {
         type: 'bar',
         data: data,
         options: {
             scales: {
                 y: {
                     max:10,
                     beginAtZero: true
                 }
             }
         }
     };

     // Khởi tạo biểu đồ và lưu trữ vào biến global
     myBarChart = new Chart(
         document.getElementById('myBarChart'),
         config
     );
 }

 document.addEventListener("DOMContentLoaded", function () {

     let value = document.querySelector(".option-exam").value;
      loadPhoDiem(value);
 });
    </script>

    <script src="assets/js/main.js"></script>
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
