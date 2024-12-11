<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@
taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>
<!DOCTYPE html>
<% String contextPath = request.getContextPath();%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="assets/css/fixCss.css" />
  <link rel="stylesheet" href="assets/css/questionBank.css" />
  <link rel="stylesheet" href="assets/css/major.css" />
  <link rel="stylesheet" href="assets/css/label.css" />
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
                              <li>
                                <a href="/Project/admin/exam">
                                  <span class="icon">
                                    <ion-icon name="chatbubble-outline"></ion-icon>
                                  </span>
                                  <span class="title">Kỳ thi</span>
                                </a>
                              </li>
                              <li class="active">
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

            <div class="search">
              <label>
                <input
                  value=""
                  type="text"
                  placeholder="Search name major"
                  name="search"
                />
                <ion-icon name="search-outline"></ion-icon>
              </label>
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
              <h2>Bài thi</h2>
            </div>
            <p><span class="total-result">99999</span> kết quả</p>
            <table>
              <thead>
                <tr>
                  <td>Id</td>
                  <td>Môn học</td>
                  <td>Kì thi</td>
                  <td>Bắt đầu</td>
                  <td>Thời lượng</td>
                  <td>Trạng thái</td>
                  <td colspan="3">Hành động</td>
                </tr>
              </thead>
              <tbody class="container-show-major"></tbody>
            </table>
          </div>
          <div class="pagination"></div>
        </div>
      </div>
    </div>

    <!-- =========== Scripts =========  -->
    <script>
      var jsonMajorList = '${jsonMajorList}'
      var jsonExamList = '${jsonExamList}'
      var jsonSubjectList = '${jsonSubjectList}'
      var jsonTestList = '${jsonTestList}'
      var majorList = JSON.parse(jsonTestList); // major duoc hieu nhu la test list. tui luoi doi lai ten :)) 00:27 20/05/2024
      var subjectList = JSON.parse(jsonSubjectList);
      var examList = JSON.parse(jsonExamList);
      var containerShow = document.querySelector(".container-show-major");
      var pagination = document.querySelector(".pagination");
      var totalResultEle = document.querySelector(".total-result");
      var filterMajor = [];
      function changMajorList() {
        majorList.forEach(e => {
          e.ExamID = getNameExam(e.ExamID);
          e.SubjectID = getNameSubject(e.SubjectID);
        });
        return majorList;
      }
      majorList = changMajorList();
      console.log("majorList changed", majorList)
     
      
      let perPage = 2;
      let currentPage = 1;
      let start = 0;
      let end = perPage;

      function changeNumberTotalResult(array) {
        totalResultEle.innerHTML = " " + array.length;
      }
      
      function renderMajor(array) {
        let row = '';
        const content = array.map((e, index) => {
            if(index >= start && index < end) {
              row += '<tr id="row-major-' + index + '">' +
                '<td>' + e.TestID + '</td>' +
                '<td>' + e.SubjectID + '</td>' +
                '<td>' + e.ExamID + '</td>' +
                '<td>' + e.DateStart + '</td>' +
                <!-- '<td>' + e.DateEnd + '</td>' + -->
                '<td>' + e.Duration + '</td>' +
                '<td><span class="lb-' + e.Status + '">' +
                changeEnToVi(e.Status) +
                '</span></td>' +                
                '<td style="display: flex; gap: 10px;">' +
                '<button class="btn btn-delete" onclick="deleteMajor(' + e.TestID + ', ' + index + ')">' +
                '<i class="fa-regular fa-trash-can"></i>Xoá' +
                '</button>' +
                '<button class="btn btn-excel" id="btn-export-excel" type="button" value="' + e.TestID + '">' +
                  '<i class="fa-regular fa-file-excel"></i> Excel' +
                '</button>' +
                '<button class="btn btn-pdf" id="btn-export-pdf" type="button">' +
                  '<i class="fa-regular fa-file-pdf"></i>PDF' +
                '</button>' +
                '</td>' +
                '</tr>';
            }
        });
        // Thêm các chuỗi HTML đã tạo vào containerShow
        containerShow.innerHTML = row;
    }
    function getNameSubject(subID) {
      let name = '';
      subjectList.forEach(e => {
        if(e.SubjectID == subID) {
          name = e.Name;
        }
      });
      return name;
    }
    function getNameExam(examID) {
      let name = '';
      examList.forEach(e => {
        if(e.ExamID == examID) {
          name = e.Name;
        }
      });
      return name;
    }
    function changeEnToVi(en) {
      if (en == "NOT_STARTED") {
        return "Chưa bắt đầu";
      } else if (en == "IN_PROGRESS") {
        return "Đang diễn ra";
      } else {
        return "Đã hoàn thành";
      }
    }
    function getCurrentPage(currentPage) {
      start = (currentPage - 1) * perPage;
      end = currentPage * perPage;
      console.log(start, end);
    }

    function renderPagination(array) {
      let numPage = Math.ceil(array.length / perPage); // Tính toán số trang
      let page = '<li class="page-item active" data-page="1">1</li>';
      let checkDisplayPageItem = 1;
      for(let i = 2; i <= numPage; i++) {
          page += '<li class="page-item" data-page="' + i +'">' + i + '</li>'; // Thêm số trang vào biến page
          checkDisplayPageItem = i;
      }
      // Thêm các thẻ <li> đã tạo vào một phần tử HTML có sẵn (ví dụ: một danh sách ul)
      if (pagination && checkDisplayPageItem >= 2) {
          pagination.innerHTML = page;
      } else {
        pagination.innerHTML = '';
      }
  }
  

    function changePage(array) {
      let pageItems = document.querySelectorAll(".page-item");
      for(let i = 0; i < pageItems.length; i++) {
        pageItems[i].addEventListener("click", (e)=> {
          let value = i + 1;
          currentPage = value;
          getCurrentPage(currentPage);
          renderMajor(array);
          changeActive();
        })
      }
    }
  
      function changeActive() {
        let pageItems = document.querySelectorAll(".page-item");
        pageItems.forEach((e) => {
            if (e.getAttribute("data-page") == currentPage) {
                e.classList.add("active");
            } else {
                e.classList.remove("active");
            }
        });
      }
      function searchMajor() {
        // Lấy giá trị từ trường nhập liệu tìm kiếm
        var searchTerm = document.querySelector("input[name='search']").value.toLowerCase();
        console.log(searchTerm);
        
        // Lọc danh sách các mục dựa trên giá trị tìm kiếm
        filterMajor = majorList.filter(function(major) {
            return major.SubjectID.toLowerCase().includes(searchTerm);
        });
        console.log(filterMajor);
    
        // Hiển thị kết quả lọc ra trên giao diện
        currentPage = 1;
        renderMajor(filterMajor);
        changeNumberTotalResult(filterMajor);
        renderPagination(filterMajor);
        changePage(filterMajor);
        return filterMajor;
    }
    
    // Lắng nghe sự kiện khi người dùng nhập vào trường tìm kiếm
    document.querySelector("input[name='search']").addEventListener("input", searchMajor);
    
  
    renderPagination(majorList);
    changeNumberTotalResult(majorList);
    document.addEventListener("DOMContentLoaded", renderMajor(majorList));
    changePage(majorList);

    function deleteMajorInArrayList(array ,index) {
      array.forEach(e => {
          if(e.TestID == index) {
              array.splice(array.indexOf(e), 1);
          }
      });
    }

    
    function deleteMajor(majorId, rowIndex) {
      let confirmDelete = confirm("Bạn có chắc chắn muốn xoá không?");
      if (confirmDelete) {
        $.ajax({
          url: '/Project/admin/test?action=delete',  // Update with your server URL
          type: 'POST',
          data: { testId: majorId },
          success: function() {
            currentPage = 1;
            // Update the majorList with the filtered list
            deleteMajorInArrayList(majorList, majorId);
            // Re-render UI based on the filtered list
            renderMajor(majorList);
            changeNumberTotalResult(majorList);
            renderPagination(majorList);
            changePage(majorList);
          }
        });
      }
    } 
    document.addEventListener("DOMContentLoaded", function() {
        var btnExportExcel = document.querySelector("#btn-export-excel");
        if (btnExportExcel) {
            console.log("Export button found:", btnExportExcel);
            btnExportExcel.addEventListener("click", function(e) {
                var testId = e.target.value;
                console.log("Test ID:", testId);

                $.ajax({
                    url: '/Project/admin/test?action=export-result',
                    type: 'POST',
                    data: { testId: testId },
                    success: function(response) {
                        // Assuming the response contains a URL to the exported Excel file
                        if (response.fileUrl) {
                            // Create a temporary link to download the file
                            var link = document.createElement('a');
                            link.href = response.fileUrl;
                            link.download = 'exportResultTest.xlsx';
                            document.body.appendChild(link);
                            link.click();
                            document.body.removeChild(link);

                            alert("Xuất excel thành công");
                        } else {
                            alert("Không tìm thấy URL của tệp.");
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        alert('Failed to export Excel file');
                    }
                });
            });
        } else {
            console.error("Export button not found");
        }
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
