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
   <link rel="stylesheet" href="assets/css/questionBankSecond.css" />
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
                               <li>
                                 <a href="/Project/admin/thongketheophodiem">
                                   <span class="icon">
                                     <i class="fa-solid fa-chart-simple" style="font-size: 25px;"></i>
                                   </span>
                                   <span class="title">Phổ điểm</span>
                                 </a>
                               </li>
                              <li class="active">
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
                  <input type="text" placeholder="Tìm kiếm câu hỏi" name="search" />
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
              <h2>Ngân hàng câu hỏi</h2>
              <a href="/Project/admin/questionbank?action=addquestion">
                <button class="btn btn-add btn-add-questionBank" type="button">Thêm</button>
              </a>

              <button class="btn btn-excel" id="btn-export-excel" type="button">
                <i class="fa-regular fa-file-excel"></i>
                Xuất excel
              </button>

              <button class="btn btn-reset-level" id="btn-reset-level" type="button">
                <i class="fa-solid fa-wand-magic-sparkles"></i>
                Tính lại độ khó
              </button>

              <div class="filter" onclick="toggleActive('form-filter-advance')">
                <img src="assets/icon/Filter.svg" alt="" class="icon-filter" />
                <span>Lọc</span>
              </div>
              <div class="form-filter-advance">
                <div class="container">
                  <h2 class="title">Lọc nâng cao</h2>
                  <div class="icon-kill" onclick="toggleActive('form-filter-advance')">
                    <i class="fa-solid fa-xmark"></i>
                  </div>
                 <div class="form-group form-group-1">
                    <label for="">Nội dung</label>
                    <input placeholder="Nhập nội dung tìm kiếm ..." type="text" id="search-filter-advance"/>
                 </div>
                  <div class="form-group">
                    <label for="subject-filter-advance">Môn học</label>
                    <select name="subject" id="subject-filter-advance">
                      <option value="">Tất cả</option>
                      <c:forEach var="subject" items="${subjectListNameAndId}">
                        <option value="${subject.getName()}">
                          ${subject.getName()}
                        </option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="form-group">
                    <label for="level-filter-advance">Độ khó</label>
                    <select name="level" id="level-filter-advance">
                      <option value="">Tất cả</option>
                      <option value="EASY">Dễ</option>
                      <option value="MEDIUM">Trung bình</option>
                      <option value="HARD">Khó</option>
                    </select>
                  </div>
                  
                </div>
                <div class="form-group-last">
                  <span class="btn-clear">Xoá</span>
                  <button class="btn-filter-advance">Xem kết quả</button>
                </div>
              </div>
            </div>
            <p><span class="total-result">99999</span> kết quả</p>
            <table>
              <thead>
                <tr>
                  <td>Id</td>
                  <td>Nội dung</td>
                  <td>Môn học</td>
                  <td>Độ khó</td>
                  <td colspan="3">Action</td>
                </tr>
              </thead>

              <tbody class="container-show-questionBank">
                   
              </tbody>
            </table>
          </div>
          <!-- ----- -->
          <div class="pagination"></div>
          <div class="show-detailQuestion">
            <div class="detailQuestion">
              <div class="detailQuestion-header">
                <h2>Chi tiết câu hỏi <span class="id-detail">#1</span></h2>
                <div class="icon-kill" onclick="toggleActive('show-detailQuestion')">
                  <i class="fa-solid fa-xmark"></i>
                </div>
              </div>
              <div class="detailQuestion-body">
                <div class="detailQuestion-head">
                  <div class="title"></div>
                </div>
                <div class="detailQuestion-body-option">
                  <div class="list-option-detail">
                    <div class="option-detail">
                      <input type="radio" name="option" id="option1" />
                      <label for="option1" class="option1">Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quos.</label>
                    </div>
                    <div class="option-detail">
                      <input type="radio" name="option" id="option2" />
                      <label for="option2" class="option2">Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quos.</label>
                    </div>
                    <div class="option-detail">
                      <input type="radio" name="option" id="option3" />
                      <label for="option3" class="option3">Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quos.</label>
                    </div>
                    <div class="option-detail">
                      <input type="radio" name="option" id="option4" />
                      <label for="option4" class="option4">Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, quos.</label>
                    </div>
                  </div>
                </div>
              </div>
              <div class="detailQuestion_subbody">
                <div class="subject">Môn học: <span class="subject-name">Lí thuyết đồ thị</span></div>
                <div class="level">Độ khó: <span class="level-name">Trung bình</span></div>
              </div>
              <div class="edit-question">
              <a href="/Project/admin/questionbank?action=editquestion&questionid=">
                <button class="btn-edit-question">
                  Sửa câu hỏi này
                  <i class="fa-solid fa-arrow-right"></i>
                </button>
              </a>
              </div>
            </div>
          </div>
          <!-- ------ -->
          </div>


          </div>
        </div>
      </div>
    </div>
    <div class="layer-dark none" onclick="destroy()"></div>
    <script>
      var jsonQuestionBankList = '${questionBankListJson}';
      console.log("data hôm nay la: ", jsonQuestionBankList);
      var questionBankList = JSON.parse(jsonQuestionBankList);
      console.log(questionBankList);
      var arrayFilter = [];
      var containerShow = document.querySelector(".container-show-questionBank");
      var pagination = document.querySelector(".pagination");
      var totalResultEle = document.querySelector(".total-result");
     
      
      let perPage = 8;
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
                '<td>' + e.QuestionID + '</td>' +
                '<td class="cut-title">' + e.Title + '</td>' +
                '<td>' + e.subject.Name + '</td>' +
                '<td>' + changeEnToVi(e.Level) + '</td>' +
                '<td style="display: flex; gap: 10px;">' +
                '<a href="/Project/admin/questionbank?action=editquestion&questionid=' + e.QuestionID + '">' +
                '<button class="btn btn-edit">' +
                '<i class="fa-regular fa-pen-to-square"></i>Sửa' +
                '</button>' +
                '</a>' +
                '<button class="btn btn-detail" onclick ="detailQuesiton('+ e.QuestionID  +')">Chi tiết</button>' +
                '<button class="btn btn-delete" onclick="deleteMajor(' + e.QuestionID + ', ' + index + ')">' +
                '<i class="fa-regular fa-trash-can"></i>Xoá' +
                '</button>' +
                '</td>' +
                '</tr>';
            }
        });
        // Thêm các chuỗi HTML đã tạo vào containerShow
        containerShow.innerHTML = row;
        cutText();
    }
// Rút gọn tiêu đề nếu quá dài
function cutText() {
  textContainers = document.querySelectorAll(".cut-title");
  textContainers.forEach((element) => {
    var text = element.innerHTML;
    if (text.length > 50) {
        var shortenedText = text.substring(0, 50) + "...";
        // Hiển thị văn bản đã được rút gọn
        element.innerHTML = shortenedText;
    }
});
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
    console.log("array de thay doi: ", array)
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
        currentPage = 1;
        // Lấy giá trị từ trường nhập liệu tìm kiếm
        var searchTerm = document.querySelector("input[name='search']").value.toLowerCase();
        console.log(searchTerm);
        
        // Lọc danh sách các mục dựa trên giá trị tìm kiếm
        arrayFilter.splice(0, arrayFilter.length)
        arrayFilter = questionBankList.filter(function(major) {
            return major.Title.toLowerCase().includes(searchTerm);
        });
        console.log(arrayFilter);
    
        // Hiển thị kết quả lọc ra trên giao diện
        renderMajor(arrayFilter);
        changeNumberTotalResult(arrayFilter);
        renderPagination(arrayFilter);
        changePage(arrayFilter);
        return arrayFilter;
    }
    
    // Lắng nghe sự kiện khi người dùng nhập vào trường tìm kiếm
    document.querySelector("input[name='search']").addEventListener("input", searchMajor);
    
  
    renderPagination(questionBankList);
    changeNumberTotalResult(questionBankList);
    document.addEventListener("DOMContentLoaded", renderMajor(questionBankList));
    changePage(questionBankList);

    function deleteMajorInArrayList(array ,index) {
      array.forEach(e => {
          if(e.QuestionID == index) {
              array.splice(array.indexOf(e), 1);
          }
      });
      renderMajor(array);
      changeNumberTotalResult(array);
      renderPagination(array);
      changePage(arrayFilter);
    }

    
    function deleteMajor(majorId, rowIndex) {
      let confirmDelete = confirm("Bạn có chắc chắn muốn xoá không?");
      if (confirmDelete) {
        $.ajax({
          url: '/Project/admin/questionbank?action=delete',  // Update with your server URL
          type: 'POST',
          data: { questionId: majorId },
          success: function() {
            // Filter out the deleted major from the search result
            var filteredList = searchMajor().filter(function(major) {
              return major.QuestionID !== majorId;
            });
    
            // Update the jsonQuestionBankList with the filtered list
            deleteMajorInArrayList(questionBankList, majorId);
            // Re-render UI based on the filtered list
            renderMajor(filteredList);
            changeNumberTotalResult(filteredList);
            renderPagination(filteredList);
            changePage(arrayFilter);
          }
        });
      }
    }

    function changeEnToVi(en) {
      if(en == "HARD") {
        return "Khó";
      } else if(en == "MEDIUM") {
        return "Trung bình";
      } else if(en == "EASY") {
        return "Dễ";
      }
    }
    function detailQuesiton(idQuestion) {
      document.querySelector(".show-detailQuestion").classList.toggle("active");
      document.querySelector(".layer-dark").classList.toggle("none");
      console.log("deltail: " + idQuestion);
      let listOption = document.querySelector(".list-option-detail");
      questionBankList.forEach(e => {
        if(e.QuestionID == idQuestion) {
          document.querySelector(".id-detail").innerHTML = "#" + e.QuestionID;
          document.querySelector(".detailQuestion-head .title").innerHTML= e.Title;
          document.querySelector(".subject-name").innerHTML= e.subject.Name;
          document.querySelector(".level-name").innerHTML= changeEnToVi(e.Level);


          let html = ''; // Khởi tạo biến html để chứa các phần tử HTML mới

          e.options.forEach((option, index) => {
               html += '<div class="option-detail">' +
                '<input type="radio" name="option" id="option' + option.OptionID + '" />' +
                '<label for="option' + option.OptionID + '" class="option' + option.OptionID + '">' + option.Title + '</label>' +
            '</div>';
          });
          listOption.innerHTML = html;
          changeLinkId(e.QuestionID);
          document.getElementById("option" + e.CorrectAnswer).checked = true;
          document.querySelector(".option" + e.CorrectAnswer).classList.add("correct-answer")


        }
      });
    }
    function changeLinkId(newId) {
      var linkElement = document.querySelector('a[href="/Project/admin/questionbank?action=editquestion&questionid="]');
      var newHref = "/Project/admin/questionbank?action=editquestion&questionid=" + newId;
      if (linkElement) {
          linkElement.setAttribute('href', newHref);
      }
    }
    function toggleActive(className) {
      console.log("ok da active");
      console.log(document.querySelector(".layer-dark"))
      document.querySelector(".layer-dark").classList.toggle("none");
      document.querySelector("." + className).classList.toggle("active");
    }
    function destroy() {
      document.querySelector(".layer-dark").classList.add("none");
      document.querySelector(".show-detailQuestion").classList.remove("active");
      document.querySelector(".form-filter-advance").classList.remove("active");
    }
    var btnFilterAdvance = document.querySelector(".btn-filter-advance");
    var searchFilterAdvance = document.querySelector("#search-filter-advance");
    var subjectFilterAdvance = document.querySelector("#subject-filter-advance");
    var levelFilterAdvance = document.querySelector("#level-filter-advance");
    function filterAdvance() {
      var search = searchFilterAdvance.value.toLowerCase();
      var subject = subjectFilterAdvance.value;
      var level = levelFilterAdvance.value;

        arrayFilter.splice(0, arrayFilter.length)
        arrayFilter = questionBankList.filter(function(major) {
        var searchMatch = search === "" || major.Title.toLowerCase().includes(search);
        var subjectMatch = subject === "" || !major.subject.Name || major.subject.Name === subject;
        var levelMatch = level === "" || !major.Level || major.Level === level;
        
        return searchMatch && subjectMatch && levelMatch;
      });
    
      console.log(arrayFilter);
      searchFilterAdvance.value = "";
      return arrayFilter;
    }
    
    btnFilterAdvance.addEventListener("click", function() {
      currentPage = 1;
      toggleActive("form-filter-advance");
      filteredList = filterAdvance();
      renderMajor(arrayFilter);
      changeNumberTotalResult(arrayFilter);
      renderPagination(arrayFilter);
      changePage(arrayFilter);
      document.querySelector(".layer-dark").classList.add("none");
    });

    function clearFilter() {
      searchFilterAdvance.value = "";
      subjectFilterAdvance.value = "";
      levelFilterAdvance.value = "";
    }
    document.querySelector(".btn-clear").addEventListener("click", clearFilter);
    btnExportExcel = document.querySelector("#btn-export-excel")
    console.log("nut export: excel", btnExportExcel)
    btnExportExcel.addEventListener("click", function() {
        $.ajax({
          url: '/Project/admin/questionbank?action=export-question',
          type: 'POST',
          success: function(response) {
            // Tạo một liên kết tạm thời để tải file
            var link = document.createElement('a');
            link.href = response.fileUrl;
            link.download = 'exportQuestion.xlsx';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);

            alert("Xuất excel thành công");
          },
          error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('Failed to export Excel file');
        }
        });
    });
    </script>

    <!-- =========== Scripts =========  -->
    <script src="assets/js/main.js"></script>
    <!-- <script src="assets/js/questionBank.js"></script> -->
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
    <script>
        document.getElementById("btn-reset-level").addEventListener('click',function(){
            fetch('http://localhost:9999/Project/admin/countLevelQuestion',{
                                   method:'POST',
                                   headers:{
                                     'Content-Type': 'application/json'
                                 },
                                 body: JSON.stringify({
                                     'email': 'fkahsfkj',
                                 })
                                })

              alert("Đã cập nhật độ khó cho kì thi thành công!");
        })
    </script>
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
