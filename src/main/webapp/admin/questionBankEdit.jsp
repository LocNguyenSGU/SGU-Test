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
    <link rel="stylesheet" href="assets/css/questionBankEdit.css" />
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
              <input
                type="text"
                placeholder="Search name question"
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
              <h2>Sửa câu hỏi #<span class="id-question">1</span></h2>
              <div class="body-edit">
                <div class="sub-body-edit">
                  <div class="question">
                    <label for="title" class="bold">Nội dung câu hỏi</label
                    ><br />
                    <textarea name="title" id="title" cols="30" rows="10">
Nội dung câu hỏi</textarea
                    >
                  </div>
                  <div class="list-option">
                    <div class="">
                        <span class="bold">Các lựa chọn</span>
                    <div class="block-option">
                      <div class="option">
                        <input type="radio" name="option" id="1" />
                        <label for="1" contenteditable="true"
                          >Nhập nội dung ở đây</label
                        >
                      </div>
                      <div class="option">
                        <div class="group">
                          <input type="radio" name="option" id="2" />
                          <label for="2" contenteditable="true"
                            >Nhập nội dung ở đây</label
                          >
                        </div>
                        <button class="btn-delete-edit" id="btn-delete-id-2">
                          <i class="fa-solid fa-x"></i>
                        </button>
                      </div>
                      <div class="option">
                        <input type="radio" name="option" id="3" />
                        <label for="3" contenteditable="true"
                          >Nhập nội dung ở đây</label
                        >
                      </div>
                      <div class="option">
                        <input type="radio" name="option" id="4" />
                        <label for="4" contenteditable="true"
                          >Nhập nội dung ở đây</label
                        >
                      </div>
                    </div>
                    </div>
                    <div class="add-option-edit">
                        <button class="btn-add-option" onclick="addOption()">
                            <i class="fa-solid fa-plus"></i>
                            <span>Thêm lựa chọn</span>
                         </button>
                    </div>

                  </div>

                  
                </div>
                <div class="detailQuestion_subbody">
                  <div class="subject-edit">
                    <label for="subject-name-edit" class="bold"> Môn học</label>
                    <select name="subject" id="subject-name-edit">
                      <c:forEach var="subject" items="${subjectListNameAndId}">
                        <option value="${subject.getName()}">
                          ${subject.getName()}
                        </option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="level-edit">
                    <label for="level-name-edit" class="bold">Mức độ</label>
                    <select name="level" id="level-name-edit">
                      <option value="EASY">Dễ</option>
                      <option value="MEDIUM">Trung bình</option>
                      <option value="HARD">Khó</option>
                    </select>
                  </div>
                  <!-- <div class="point-edit">
                    <label for="point-edit" class="bold"
                      >Điểm số
                      <span style="color: #2a2185" class="point"
                        >(7)</span
                      ></label
                    >
                    <input
                      type="range"
                      name="point"
                      id="point-edit"
                      max="10"
                      min="0"
                      step="0.5"
                      value="7"
                    />
                  </div> -->
                  <div class="edit-question">
                      <button type="button" class="btn-edit-question" onclick="updateQuestion()">
                        Hoàn thành chỉnh sửa
                        <i
                          class="fa-solid fa-arrow-right"
                          aria-hidden="true"
                        ></i>
                      </button>

                      <a href="/Project/admin/questionbank">Quay lại</a>

                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script>
      var jsonQuestionBankById = '${jsonGetQuestionById}';
      var questionBankById = JSON.parse(jsonQuestionBankById);
      console.log(questionBankById);
      let questionDetail = questionBankById[0];
      console.log(questionDetail);
      var subjectJson = '${subjectJson}';
        var subjectList = JSON.parse(subjectJson);
      var btnAddOption = document.querySelector(".btn-add-option");

      function renderOption() {
        let html = "";
        questionDetail.options.forEach((option, index) => {
          html +=
            '<div class="option">' +
            '<div class="group">' +
            '<input type="radio" name="option" id="option' +
            option.OptionID +
            '">' +
            '<label for="' +
            option.OptionID +
            '" contenteditable="true">' +
            option.Title +
            "</label>" +
            "</div>" +
            '<button class="btn-delete-edit" id="btn-delete-id-' +
            option.OptionID +
            '" onclick="deleteOption(\'' +
            option.OptionID +
            "')\">" + // Sửa đổi ở đây
            '<i class="fa-solid fa-x"></i>' +
            "</button>" +
            "</div>";
        });
        document.querySelector(".block-option").innerHTML = html;
        changeCorrectAnswer();
        changeTitleOption();
        console.log(questionDetail);
      }
      function checkCorrectAnswer() {
        if (
          document.querySelector("#option" + questionDetail.CorrectAnswer) !=
          null
        ) {
          document
            .querySelector("#option" + questionDetail.CorrectAnswer)
            .setAttribute("checked", "checked");
        }
      }

      function renderLayout() {
        document.querySelector(".id-question").textContent =
          questionDetail.QuestionID;
        document.querySelector("#title").textContent = questionDetail.Title;
        document.querySelector("#subject-name-edit").value =
          questionDetail.subject.Name;
        document.querySelector("#level-name-edit").value = questionDetail.Level;
        renderOption();
        checkCorrectAnswer();
        changeTitleOption();

      }
      renderLayout();

      function deleteOption(optionId) {
        if(questionDetail.options.length == 1) {
            alert("Không thể xóa hết các lựa chọn");
            return;
        }
        let optionIndex = questionDetail.options.findIndex(
          (option) => option.OptionID == optionId
        );
        questionDetail.options.splice(optionIndex, 1);
        console.log(questionDetail);
        renderOption();
        changeCorrectAnswer();
        changeSubject()
        changeTitleQuestion();
        checkCorrectAnswer();
        changeTitleOption();

      }

      function changeCorrectAnswer() {
        // Lấy danh sách tất cả các input có name là "option"
        const optionsAn = document.querySelectorAll('input[name="option"]');
        if(optionsAn.length == 1) {
            questionDetail.CorrectAnswer = optionsAn[0].id.replace("option", "");
            console.log("Đã chọn đáp án đúng là: if", questionDetail.CorrectAnswer);
            return;
        }
    
        // Duyệt qua từng phần tử input
        optionsAn.forEach(function(option) {
            // Lắng nghe sự kiện change cho từng input
            option.addEventListener('input', function() {
                // Nếu input này đã được chọn làm đáp án đúng
                if (option.checked) {
                    // Thay đổi đáp án đúng bằng id của input này
                    questionDetail.CorrectAnswer = option.id.replace("option", "");
                    // Hiển thị thông báo hoặc thực hiện hành động khác tùy thuộc vào ý đồ của bạn
                    console.log("Đã chọn đáp án đúng là: not if", questionDetail.CorrectAnswer);
                }
            });
        });
    }
    
    // Gọi hàm changeCorrectAnswer() khi trang web được tải xong
    window.addEventListener('DOMContentLoaded', changeCorrectAnswer);
    
    function changeTitleQuestion() {
        document.querySelector("#title").addEventListener('input', function() { 
            questionDetail.Title = document.querySelector("#title").value;
            console.log(questionDetail.Title);
        });
    }
    function returnIdByNameSubject(name) {
        const foundSubject = subjectList.find(subject => subject.Name == name);
        if (foundSubject) {
            console.log("id subject changed: ");
            console.log(foundSubject.SubjectID);
            return foundSubject.SubjectID;
        } else {
            return null; // hoặc giá trị mặc định khác nếu cần
        }
    }
    
    function changeSubject() {
        document.querySelector("#subject-name-edit").addEventListener('change', function() { 
            questionDetail.subject.Name = document.querySelector("#subject-name-edit").value;
            console.log("name changed: " + questionDetail.subject.Name);
            questionDetail.subject.SubjectID = returnIdByNameSubject(questionDetail.subject.Name);
            questionDetail.SubjectID = returnIdByNameSubject(questionDetail.subject.Name);
            console.log( "sub: ");
            console.log(questionDetail);
        });
    }
    function changeLevel() {
        document.querySelector("#level-name-edit").addEventListener("change", function() {
            console.log(document.querySelector("#level-name-edit").value)
            questionDetail.Level = document.querySelector("#level-name-edit").value;
        })
    }
    function changeTitleOption() {
        const optionLabels = document.querySelectorAll('label[contenteditable="true"]');
        optionLabels.forEach(label => {
            label.addEventListener("input" , ()=> {
                const optionId = label.getAttribute("for");
                const optionIndex = questionDetail.options.findIndex(option => option.OptionID == optionId);
                if (optionIndex !== -1) {
                    questionDetail.options[optionIndex].Title = label.textContent;
                    console.log(questionDetail.options[optionIndex].Title);
                }
            })
        }); 
    }
    function addOption() {
        const currentTime = new Date().getTime().toString();
        let idNewOption = currentTime.substring(currentTime.length - 6);
        const newOption = {
            OptionID: idNewOption,
            Title: "Nhập nội dung ở đây"
        }
        questionDetail.options.push(newOption);
        renderOption();
        changeSubject()
        changeTitleQuestion();
        checkCorrectAnswer();
        changeTitleOption();
    }
    function updateQuestion() {
        console.log(questionDetail);
         if(title == "") {
           alert("Cần phải nhập đầy đủ nội dung câu hỏi");
           return;
         }
        $.ajax({
            url: "/Project/admin/questionbank?action=update",
            type: "POST",
            data: {
                question: JSON.stringify(questionDetail)
            },
            success: function(response) {
                alert("Sửa câu hỏi thành công")
                console.log(response);
                window.location.href = "/Project/admin/questionbank";
            },
            error: function(error) {
                console.log(error);
            }
        })
    }
    console.log("okofjafkalnfL: ")
    console.log(JSON.stringify(questionDetail))
    changeTitleQuestion();    
    changeLevel();
    changeSubject();
    changeTitleOption();

    </script>

    <!-- =========== Scripts =========  -->
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
