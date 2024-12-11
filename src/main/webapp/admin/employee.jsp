<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
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
    <link rel="stylesheet" href="assets/css/exam.css" />
    <link rel="stylesheet" href="assets/css/employee.css" />
    <style>
    .details {
        grid-template-columns: 1fr;
    }
    </style>
  </head>

  <body>
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
                <div class="search">
                    <label>
                       <input value="" type="text" placeholder="Search name Employee" name="search" />
                       <ion-icon name="search-outline"></ion-icon>
                    </label>
                    <input type="hidden" name="page" value="1">
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
              <h2>Giảng Viên</h2>
              <a href="/Project/admin/addemployee">
                <button class="btn btn-add btn-add-exam">Thêm</button>
              </a>

            </div>
            <div class="filter_status">
              <h3>Chuyên Ngành</h3>
                            <select id="selectValue" class="selection">
                                         <option style="padding:5px;height:40px" name="" id="" value="" selected>Tất cả</option>
                                         <c:forEach var="major" items="${listMajors}">
                                         <option style="padding:5px;height:40px" value="${major.getMajorID()}" id="">${major.getName()}</option>
                                       </c:forEach>
                                       </select>
                    <button class="btn btn_filter">Lọc</button>

              </div>
               <p><span class="total-result"></span> kết quả</p>
                          <table>
                            <thead>
                              <tr>
                                <td>Mã Giảng Viên</td>
                                <td>Tên</td>
                                <td>Họ</td>
                                <td>Chuyên Ngành</td>
                                <td colspan="2">Thao Tác</td>
                              </tr>
                            </thead>
                            <tbody class="container-show-exam" id="employee-container">
                            </tbody>
                          </table>
            </div>


          </div>
          <!-- ----- -->
          <div class="pagination"></div>
          <!-- ------ -->
        </div>
      </div>
    </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
                     let perPage = 3;
                     let currentPage = 1;
                     let start = 0;
                     let end = perPage;
                     array = [];
                     console.log(array);
                     var totalResultEle = document.querySelector(".total-result");
                     let arrayorigin = []
                     let search = "";
                     let filterValue = "";
                      function changeNumberTotalResult(array) {
                             totalResultEle.innerHTML = " " + array.length;
                      }
                     document.querySelector(".btn_filter").addEventListener("click", filterEmployee);
                            function filterEmployee(){
                                console.log("đã vào hàm lọc");
                                array = arrayorigin;
                                filterValue = document.querySelector("#selectValue").value;


                                if (filterValue === "") {
                                 console.log("đây là hàm lọc khi giá trị lọc bằng rỗng và giá trị search là"+search);
                                    if (search) {
                                        var filteredList = array.filter(function(employee) {
                                             if(employee.FirstName.toLowerCase().includes(search) || employee.LastName.toLowerCase().includes(search)) {
                                                return true;
                                             }
                                        });
                                        console.log(filteredList);
                                        renderEmployee(filteredList);
                                        renderPagination(filteredList);
                                        array = filteredList;
                                        changeNumberTotalResult(array);
                                        return;
                                    } else {
                                         array = arrayorigin
                                        renderEmployee(arrayorigin);
                                        renderPagination(arrayorigin);
                                        changePage();
                                        changeNumberTotalResult(arrayorigin);
                                        return;
                                    }
                                }
                                    array = arrayorigin;
                                    console.log("đây là hàm filter và giá trị của "+search)
                                    console.log("đây là hàm filter và giá trị của filter là "+filterValue)
                                    var filteredList = array.filter(function(employee) {
                                               if (employee.MajorID === parseInt(filterValue)) {
                                                   if (search) {
                                                       return employee.FirstName.toLowerCase().includes(search.toLowerCase()) || employee.LastName.toLowerCase().includes(search.toLowerCase());
                                                   }
                                                   return true;
                                               }
                                               return false;
                                           });
                                    console.log( filteredList);
                                    renderEmployee(filteredList);
                                    renderPagination(filteredList);
                                    changeNumberTotalResult(filteredList);
                                    array = filteredList;
                                }

                         function searchEmployee() {
                                   var searchValue = document.querySelector("input[name='search']").value.toLowerCase();
                                   search = searchValue;
                                   console.log(searchValue);
                                    array = arrayorigin;
                                   if (searchValue.trim() === "") {
                                       // Nếu searchValue rỗng, hiển thị toàn bộ danh sách
                                       array = arrayorigin;
                                       renderEmployee(array);
                                       renderPagination(array);
                                       changeNumberTotalResult(array);
                                       changePage();
                                       return;
                                   }

                                   var filteredList = array.filter(function(Employee) {
                                       return Employee.FirstName.toLowerCase().includes(searchValue);
                                   });

                                   console.log(filteredList);
                                   array = filteredList;
                                   changeNumberTotalResult(array);
                                   renderEmployee(filteredList);
                                   renderPagination(filteredList);

                               }
                               document.querySelector("input[name='search']").addEventListener("input", searchEmployee);
                      let pagination = document.getElementsByClassName("pagination")[0];
                      document.addEventListener("DOMContentLoaded", async function() {
                                     await loadEmployeeList();
                                     console.log("array sau khi load la "+array);
                                     renderEmployee(array);
                                     renderPagination(array);
                       });
      document.addEventListener("DOMContentLoaded",loadEmployeeList);

      async function loadEmployeeList() {
          try {
              const response = await fetch('/Project/admin/queryController/employee');
              if (!response.ok) {
                  throw new Error('Không thể tải dữ liệu');
              }
              const data = await response.json();
              array = data;
              console.log(data);
               arrayorigin = data;
               changeNumberTotalResult(arrayorigin);

              // Hiển thị dữ liệu trên trang web

          } catch (error) {
              // Xử lý lỗi ở đây
              console.error('Có lỗi xảy ra khi tải dữ liệu:', error);
          }
      }
                                        function renderEmployee(array){
console.log("dang trong ham render employy");
                                                     let row = '';
                                                            const content = array.map((e, index) => {
                                                                if(index >= start && index < end) {
                                                                  row += '<tr id="row-major-' + index + '">' +
                                                                       '<td>' + e.EmployeeID + '</td>' +
                                                                        '<td>' + e.FirstName + '</td>' +
                                                                       '<td>' + e.LastName + '</td>' +
                                                                        '<td>' + e.MajorName + '</td>' +


                                                                    '<td style="display: flex; gap: 10px;">' +
                                                                     '<a href="/Project/admin/editemployee?id=' + e.EmployeeID + '">' +
                                                                                          '<button class="btn btn-edit">' +
                                                                                          '<i class="fa-regular fa-pen-to-square"></i>Sửa' +
                                                                                          '</button>' +
                                                                                          '</a>' +
                                                                                          '<button class="btn btn-delete" onclick="deleteEmployee('+e.EmployeeID+ ')">' +
                                                                                          '<i class="fa-regular fa-trash-can"></i>Xoá' +
                                                                                          '</button>' +
                                                                                          '</td>' +
                                                                                          '</tr>';
                                                              }
                                                            });
                                                            // Thêm các chuỗi HTML đã tạo vào containerShow
                                                            document.getElementById('employee-container').innerHTML = row;

                                                    }
                                                    function renderPagination(array) {
                                                                               console.log("renderPagination");
                                                                               console.log(" Độ dài của " + array.length);
                                                                               let numPage = Math.ceil(array.length / perPage);
                                                                               console.log(" Số trang là " + numPage);
                                                                               let page = '';
                                                                               let checkDisplayPageItem = 0;

                                                                               if (numPage > 1) {
                                                                                   page += '<li class="page-item active" data-page="1">1</li>';
                                                                                   for (let i = 2; i <= numPage; i++) {
                                                                                       page += '<li class="page-item" data-page="' + i + '">' + i + '</li>';
                                                                                       checkDisplayPageItem = i;
                                                                                   }
                                                                               } else {
                                                                                   page += '<li class="page-item active" data-page="1">1</li>';
                                                                                   checkDisplayPageItem = 1;
                                                                               }

                                                                               if (pagination && checkDisplayPageItem >= 2) {
                                                                                   pagination.innerHTML = page;
                                                                               } else {
                                                                                   pagination.innerHTML = '';
                                                                               }
                                                                               changePage();
                                                                           }
                                                                           function changePage() {
                                                                           console.log("changePage");
                                                                               const pageItems = document.querySelectorAll(".page-item");
                                                                               pageItems.forEach((item, index) => {
                                                                                   item.addEventListener("click", () => {

                                                                                       currentPage = index + 1;
                                                                                       getCurrentPage(currentPage);
                                                                                        changeActive();
                                                                                        renderEmployee(array);
                                                                                       // Gọi hàm changeActive() sau khi thay đổi trang
                                                                                   });
                                                                               });
                                                                           }

                                                                            function changeActive() {
                                                                            console.log("changeActive");

                                                                                let pageItems = document.querySelectorAll(".page-item");
                                                                                pageItems.forEach((e) => {
                                                                                    if (parseInt(e.getAttribute("data-page")) === currentPage) {
                                                                                        e.classList.add("active");
                                                                                         console.log("them vao trang hien tai"+currentPage);
                                                                                    } else {
                                                                                        e.classList.remove("active");
                                                                                        console.log("xoa trang hien tai"+currentPage);
                                                                                    }
                                                                                });

                                                                                console.log("trang hien tai sau khi nhan"+currentPage);
                                                                            }
                                                                           function getCurrentPage(currentPage) {
                                                                                                     start = (currentPage - 1) * perPage;
                                                                                                      end = currentPage * perPage;
                                                                                                      console.log(start, end);
                                                                                                      }
                                                   function deleteEmployee(employeeId){
                                                       Swal.fire({
                                                               title: "Bạn có muốn xóa không?",
                                                               text: "Bạn sẽ không thể khôi phục được dữ liệu!",
                                                               icon: "warning",
                                                               showCancelButton: true,
                                                               confirmButtonColor: "#3085d6",
                                                               cancelButtonColor: "#d33",
                                                               confirmButtonText: "Xóa"
                                                               }).then((result) => {
                                                                   if (result.isConfirmed) {
                                                                             Swal.fire({
                                                                             title: "Xóa thành công!",
                                                                                icon: "success"
                                                                                           });
                                                                            deleteEmployeeProcess(employeeId);
                                                                                         }
                                                                                       });
                                                    }
                                                   async function deleteEmployeeProcess(employeeId) {
                                                       try {
                                                           const url = "/Project/admin/queryController/deleteEmployee";
                                                           const postData = {
                                                               id: employeeId
                                                           };

                                                           const response = await fetch(url, {
                                                               method: 'POST',
                                                               headers: {
                                                                   'Content-Type': 'application/json;charset=UTF-8'
                                                               },
                                                               body: JSON.stringify(postData)
                                                           });

                                                           if (!response.ok) {
                                                               const errorMessage = await response.text();
                                                               throw new Error(errorMessage);
                                                           }

                                                           console.log(response.text());
                                                          await loadEmployeeList();
                                                                            currentPage =  1;
                                                                            getCurrentPage(currentPage);
                                                                            changeActive()
                                                                          console.log(array);
                                                                          renderEmployee(array);
                                                                          renderPagination(array);
                                                       } catch (error) {
                                                           console.error('Có lỗi xảy ra khi xóa sinh viên:', error);
                                                       }
                                                   }
       </script>
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