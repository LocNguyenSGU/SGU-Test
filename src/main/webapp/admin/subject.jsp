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
                <div class="search">
                    <label>
                       <input value="" type="text" placeholder="Search name subject" name="search" />
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
              <h2>Môn Học</h2>

              <a href="/Project/admin/addsubject">
                <button class="btn btn-add btn-add-exam">Thêm</button>
              </a>

            </div>
            <div class="filter_status">
              <h3>Chuyên Ngành</h1>
                          <select id="selectValue" style="background-color: white;padding:5px;height:40px;border:1px solid rgb(181, 185, 222)    ;font-family: Ubuntu, sans-serif;">
                                         <option style="padding:5px;height:40px" name="" id="" value="" selected>Tất cả</option>
                                         <c:forEach var="major" items="${listMajor}">
                                         <option style="padding:5px;height:40px" value="${major.getMajorID()}" id="">${major.getName()}</option>
                                           </c:forEach>
                                       </select>
                <button class="btn btn_filter">Lọc</button>
                 <p><span class="total-result">9</span> kết quả</p>
                                <table>
                                  <thead>
                                    <tr>
                                      <td>Mã Môn Học</td>
                                      <td> Tên </td>
                                      <td>Mô Tả</td>
                                      <td>Chuyên Ngành</td>
                                      <td colspan="2">Thao Tác</td>
                                    </tr>
                                  </thead>
                                  <tbody class="container-show-exam" id="subject-container">
                                 </tbody>
                               </tr>

              </div>
            </div>


              </tbody>
            </table>
          </div>
          <!-- ----- -->
          <div class="pagination"></div>
          <!-- ------ -->
        </div>
      </div>
    </div>
    <body>
    <!-- ================ script ================= -->
     <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
      let perPage = 4;
      let currentPage = 1;
      let start = 0;
      let end = perPage;
      let array = [];
      let arrayorigin = [];
      let filtervalue = "";
      let search = "";
      var totalResultEle = document.querySelector(".total-result");
      function changeNumberTotalResult(array) {
              totalResultEle.innerHTML = " " + array.length;
            }
      document.querySelector(".btn_filter").addEventListener("click", filterSubject);
                            function filterSubject() {
                                array = arrayorigin
                                console.log(array);
                                var Value = document.querySelector("#selectValue").value;
                                console.log(Value);
                                filterValue = Value;
                                if (filterValue === "") {
                                console.log(search);
                                    if(search){
                                        var filteredList = array.filter(function(subject) {
                                        if(subject.Name.toLowerCase().includes(search)){
                                            return true;
                                        }
                                        });
                                        console.log(filteredList);
                                        renderSubject(filteredList)
                                        renderPagination(filteredList);
                                        array = filteredList;
                                        changeNumberTotalResult(array)
                                        return;
                                    }
                                    else{
                                    array = arrayorigin;
                                    console.log(array);
                                    renderSubject(array);
                                    renderPagination(array);
                                    changePage();
                                    changeNumberTotalResult(array)
                                    return;
                                    }
                                }
                                array = arrayorigin;
                                console.log(array);
                                var filteredList = array.filter(function(subject) {
                                    if (filterValue && search) {
                                        if (subject.Name.toLowerCase().includes(search.toLowerCase()) && subject.MajorID === parseInt(filterValue)) {
                                            return true;
                                        }
                                    } else if (filterValue) {
                                        if (subject.MajorID === parseInt(filterValue)) {
                                            return true;
                                        }
                                    }
                                });

                                renderSubject(filteredList);
                                renderPagination(filteredList);
                                array = filteredList;
                                changeNumberTotalResult(array)
                            }
        function searchSubject() {
            var searchValue = document.querySelector("input[name='search']").value.toLowerCase();
            console.log(searchValue);
            search = searchValue;
            array = arrayorigin;
            if (searchValue.trim() === "") {
                // Nếu searchValue rỗng, hiển thị toàn bộ danh sách
                array =  arrayorigin;
                renderSubject(array);
                renderPagination(array);
                changeNumberTotalResult(array)
                changePage();
                return;
            }

            var filteredList = array.filter(function(subject) {
                if(subject.Name.toLowerCase().includes(searchValue)){
                console.log("search là "+searchValue);
                return true;
                }
            });
            array = filteredList;
            console.log(filteredList);

            // Hiển thị kết quả lọc ra trên giao diện
            renderSubject(filteredList);
            renderPagination(filteredList);
            changeNumberTotalResult(array)

        }
        document.querySelector("input[name='search']").addEventListener("input", searchSubject);
       let pagination = document.getElementsByClassName("pagination")[0];
       document.addEventListener("DOMContentLoaded", async function() {
                                            await loadSubjectList();
                                            console.log(array);
                                            renderSubject(array);
                                            renderPagination(array);
                              });
      async function loadSubjectList() {
          try {
              const response = await fetch('/Project/admin/queryController/subject');
              if (!response.ok) {
                  throw new Error('Không thể tải dữ liệu');
              }
              const data = await response.json();
              array = data;
              arrayorigin = data;
                console.log(data);
            changeNumberTotalResult(array)
              // Hiển thị dữ liệu trên trang web

          } catch (error) {
              // Xử lý lỗi ở đây
              console.error('Có lỗi xảy ra khi tải dữ liệu:', error);
          }
      }
       function renderSubject(array){

                                                     let row = '';
                                                            const content = array.map((e, index) => {
                                                                if(index >= start && index < end) {
                                                                  row += '<tr id="row-student">' +
                                                                                         '<td>' + e.SubjectID + '</td>' +
                                                                                         '<td>' + e.Name + '</td>' +
                                                                                         '<td>' + e.Description + '</td>' +
                                                                                         '<td>' + e.MajorName + '</td>' +
                                                                                        '<td style="display: flex; gap: 10px;">' +
                                                                                        '<a href="/Project/admin/editsubject?id=' +  e.SubjectID + '">' +
                                                                                        '<button class="btn btn-edit">' +
                                                                                        '<i class="fa-regular fa-pen-to-square"></i>Sửa' +
                                                                                        '</button>' +
                                                                                        '</a>' +
                                                                                        '<button class="btn btn-delete" onclick="deleteSubject('+e.SubjectID+ ')">' +
                                                                                        '<i class="fa-regular fa-trash-can"></i>Xoá' +
                                                                                        '</button>' +
                                                                                        '</td>' +
                                                                                        '</tr>';
                                                              }
                                                            });
                                                            // Thêm các chuỗi HTML đã tạo vào containerShow
                                                            document.getElementById("subject-container").innerHTML=row;

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
                                          renderSubject(array);
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
        function deleteSubject(subjectId){
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
                                            deleteSubjectProcess(subjectId);
                                          }
                                        });

       }
       async function deleteSubjectProcess(subjectId) {
           try {
               const url = "/Project/admin/queryController/deleteSubject";
               const postData = {
                   id: subjectId
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

               console.log("Xóa sinh viên thành công");
                await loadSubjectList();
                currentPage =  1;
                getCurrentPage(currentPage);
                changeActive();
                console.log(array);
                renderSubject(array);
                renderPagination(array);
           } catch (error) {
               console.error('Có lỗi xảy ra khi xóa sinh viên:', error);
           }
       }


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