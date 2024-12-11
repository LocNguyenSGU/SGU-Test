<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>Dashboard</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>" />
    <link rel="stylesheet" href="<c:url value="/assets/css/student-tests.css"/>" />
  </head>

  <body>
    <div class="container">
    <div class="navigation">
            <ul>
              <li>
                <a href="/Project/student">
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
                <a href="#" id="logout">
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
                <img src="../assets/img/lecture.jpg" alt=""/>
              </div>
              <p>Xin chào, Lai Tấn Tài</p>
         </div>

        </div>

        <!-- ======================= Cards ================== -->
        <div class="details">
           <h1 class="headerText">Bài thi</h1>
           <div id="content" style="width:78vw;height:80vh;">

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
    <script src="../assets/js/student-tests.js"></script>
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