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
    <!-- Import font từ Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <title>Điểm thi - Quản lý thi trắc nghiệm - SGU</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/student-point.css"/>"/>
  </head>

  <body>
    <div class="container">
      <!-- =============== Navigation ================ -->
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
                <img src="../assets/img/lecture.jpg" alt="" />
              </div>
              <p>Xin chào, Lai Tấn Tài</p>
         </div>

        </div>

        <!-- ======================= Cards ================== -->
        <!-- ================ Test List && Diagram ================= -->
        <div class="details">
        <h1 class="headerText">Điểm Thi</h1>
          <div class="optionsBox">
            <div class="inputSelectModel">
                <p>Kỳ thi</p>
                <div class="inputSelect">
                     <ion-icon class="searchInsideSelect" name="search-outline"></ion-icon>
                     <input class="inputInsideSelect" placeholder="Tìm kiếm thông tin kì thi"/>
                     <ion-icon class="chevronInsideSelect" name="chevron-down-outline"></ion-icon>
                     <div id="searchExamResult" style="display:none">
                     </div>
                </div>
            </div>
          </div>
          <div style="background:white;overflow:hidden;min-height:100px;max-height:500px;box-shadow:0 7px 25px rgba(0, 0, 0, 0.08);padding:1rem;border-radius:20px;">
            <div style="width: 100%;height:10px;margin-bottom:20px">
              <h2 style="color:#2a2185">Môn thi</h2>
            </div>
            <table style="overflow-y:scroll;">
              <thead style="height:40px;">
                <tr>
                  <td style="text-align:left;padding:10px">STT</td>
                  <td style="text-align:center;padding:10px">Bài thi môn</td>
                  <td style="text-align:right;padding:10px">Thời gian</td>
                  <td style="text-align:center;padding:10px">Ngày bắt đầu</td>
                  <td style="text-align:center;padding:10px">Điểm</td>
                  <td style="text-align:right;padding:10px">Thao tác</td>
                </tr>
              </thead>
              <tbody id="table">
                <tr style="padding:0px 10px 0px 10px">
                   <td colspan="6" id="noData" style="padding-top:20px">Không tìm thấy bài thi</td>
                </tr>
              </tbody>
            </table>
          </div>
      </div>
       <div id="overlay"></div>
         <div id="modal">
           <div id="modalHeader">
                 <h2 id="title">Modal Title</h2>
                 <span class="close">&times;</span>
           </div>
           <div id="modalContent">
             <div id="note">
                    <div style="display:flex;align-items:center;margin-bottom:5px;justify-content:space-between;">
                        <div style="display:flex;align-items:center;margin-bottom:5px">
                            <span style="font-size:14px;">Thời lượng : </span>
                            <span style="font-size:14px;" id="duration"></span>
                        </div>
                        <div style="display:flex;align-items:center:">
                           <span style="font-size:14px;">Tổng điểm : </span>
                           <span style="font-size:14px;font-weight:700" id="totalPoint"></span>
                        </div>
                    </div>
                    <span style="color:red">Lưu ý: </span>
                    <span id="description">
                    </span>
             </div>
             <div style="margin-top:10px;display:flex;justify-content:space-between;align-items:center;">
                <div>
                    <span>Tổng số câu hỏi : </span>
                    <span id="totalQuestion"></span>
                </div>
                <div>
                    <span>Mã đề : </span>
                    <span id="TestID"></span>
                </div>
             </div>
             <div id="resultDetailsOfTest" style="display:flex;align-items: center;justify-content:space-between;margin-top:10px;">
                <div style="display:flex;gap:5px;">
                             <div style="display:flex;background-color:#0080008a;border-radius:5%;justify-content:center;align-items:center;padding:4px;">
                                 <ion-icon name="checkmark" style="color: green;font-size:25px;font-weight:800;"></ion-icon>
                                 <p>Số câu đúng : </p>
                                 <p id="correct"></p>
                             </div>
                             <div style="display:flex;background-color:#ff000085;border-radius:5%;justify-content:center;align-items:center;padding:4px;">
                                 <ion-icon name="close" style="color: red;font-size:25px;font-weight:800;"></ion-icon>
                                 <p>Số câu sai : </p>
                                 <p id="incorrect"></p>
                             </div>
                          </div>
                <div style="display:flex;border-radius:5%;justify-content:center;align-items:center;padding:4px;font-weight:700">
                    <p>Tổng điểm : </p>
                    <p id="point"></p>
                </div>
             </div>
             <div id="studentResult">

             </div>
           </div>
         </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js" integrity="sha512-qZvrmS2ekKPF2mSznTQsxqPgnpkI4DNTlrdUmTzrDgektczlKNRRhy5X5AAOnx5S09ydFYWWNSfcEqDTTHgtNA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
    <!-- =========== Scripts =========  -->
    <script src="<c:url value="/assets/js/main.js"/>"></script>
    <script src="<c:url value="/assets/js/student-point.js"/>"></script>
    <!-- ====== ionicons ======= -->
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