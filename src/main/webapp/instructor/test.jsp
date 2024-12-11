<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


<%@ page import="entity.Test" %>
<%@ page import="DTO.TestDTO" %>

<%@ page import="entity.Exam" %>
<%@ page import="DTO.ExamDTO" %>

<%@ page import="payload.response.Response" %>

<%@ page import="repository.SubjectRepository" %>
<%@ page import="repository.ExamRepository" %>
<%@ page import="controller.instructor.Utilities" %>
<%@ page import="entity.Employee" %>
<%@ page import="service.instructor.InfoEmployeeService" %>
<%@ page import="DTO.InfoEmployeeAndMajorDTO" %>


<%
  InfoEmployeeService infoEmployeeService = new InfoEmployeeService();
  InfoEmployeeAndMajorDTO info = (InfoEmployeeAndMajorDTO)
      infoEmployeeService.getInfoEmployeeAndMajorDTOByIDEmployee(1).getData();
  Employee instructor = info.getEmployee();
  
  Response allTests = (Response) request.getAttribute("allTests");
  TestDTO testDTO = (TestDTO) allTests.getData();
  
  Response allExams = (Response) request.getAttribute("allExams");
  ExamDTO examDTO = (ExamDTO) allExams.getData();
%>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Danh sách Đề thi</title>
  
  
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  
  <link rel="stylesheet" href="../assets/css/xyz.css" />
</head>

<body>
  <nav class="navigation">
    <div class="u-center-text u-margin-top-small u-margin-bottom-small">
      <h1 class="heading-tertiary">Quản lý thi trực tuyến</h1>
    </div>
    <ul class="navigation__list">
      <li class="navigation__item">
        <a class="navigation__link" href="${pageContext.request.contextPath}/instructor/info">
              <span class="navigation__icon-box">
                <ion-icon
                    class="navigation__icon"
                    name="home-outline"
                ></ion-icon>
              </span>
          <span class="navigation__title">Thông tin</span>
        </a>
      </li>
      
      <li class="navigation__item">
        <a
            class="navigation__link"
            href="${pageContext.request.contextPath}/instructor/question">
              <span class="navigation__icon-box">
                <ion-icon
                    class="navigation__icon"
                    name="help-outline"
                ></ion-icon>
              </span>
          <span class="navigation__title">Câu hỏi</span>
        </a>
      </li>
      
      <li class="navigation__item navigation__item--hovered">
        <a class="navigation__link"
           href="${pageContext.request.contextPath}/instructor/test">
              <span class="navigation__icon-box">
                <ion-icon
                    class="navigation__icon"
                    name="document-text-outline"
                ></ion-icon>
              </span>
          <span class="navigation__title">Đề thi</span>
        </a>
      </li>
      
      <li class="navigation__item">
        <a
            class="navigation__link"
            href="${pageContext.request.contextPath}/instructor/student">
              <span class="navigation__icon-box">
                <ion-icon
                    class="navigation__icon"
                    name="people-outline"
                ></ion-icon>
              </span>
          <span class="navigation__title">Sinh viên</span>
        </a>
      </li>
      
      
      <li class="navigation__item">
        <a class="navigation__link" href="#" id="logout">
              <span class="navigation__icon-box">
                <ion-icon
                    class="navigation__icon"
                    name="log-out-outline"
                ></ion-icon>
              </span>
          <span class="navigation__title">Đăng xuất</span>
        </a>
      </li>
    </ul>
    
    <div class="navigation__info">
      <p class="navigation__info--author">Mai Trần Tuấn Kiệt</p>
      <ul class="navigation__info--list">
        <li class="navigation__info--item">
          <a href="https://github.com/mttk2004" target="_blank">
            <i class='bx bxl-github'></i>
          </a>
        </li>
        <li class="navigation__info--item">
          <a href="https://github.com/mttk2004" target="_blank">
            <i class='bx bxl-facebook-circle' ></i>
          </a>
        </li>
        <li class="navigation__info--item">
          <a href="https://github.com/mttk2004" target="_blank">
            <i class='bx bxl-reddit' ></i>
          </a>
        </li><li class="navigation__info--item">
        <a href="https://github.com/mttk2004" target="_blank">
          <i class='bx bxl-codepen' ></i>
        </a>
      </li><li class="navigation__info--item">
        <a href="https://github.com/mttk2004" target="_blank">
          <i class='bx bxs-envelope' ></i>
        </a>
      </li>
      </ul>
      <blockquote class="navigation__info--quote">“Family is where life begins and love never ends.”</blockquote>
    </div>
  </nav>
  
  <main class="main">
    <section class="header u-margin-bottom-medium">
      <a href="#" class="btn navigation__btn-toggle">
        <ion-icon class="navigation__icon navigation__icon--menu" name="menu-outline">
        </ion-icon>
      </a>
      
      <span class="welcome">
            <img src="../assets/img/lecture.jpg" alt="face" class="welcome__img">
            <span
                class="welcome__text"><%= instructor.getLastName() + " " + instructor.getFirstName() %>
            </span>
        </span>
    </section>
    
    <section class="test-mana">
      <header class="test-mana__header">
        <h2 class="heading-secondary">Danh sách Đề thi</h2>
      </header>
      
      <div class="student-mana__tool">
        <ul>
          <li class="u-bgc-green">
            <a href="${pageContext.request.contextPath}/instructor/test/add">
                        <span class="test-mana__icon-box">
                            <ion-icon name="add-circle-outline"></ion-icon>
                        </span>Thêm đề thi
            </a>
          </li>
          <li class="u-bgc-blue">
            <a href="#" id="export-test-list">
                        <span class="test-mana__icon-box">
                            <i class='bx bxs-file-import'></i>
                        </span>Trích xuất
            </a>
          </li>
          <li class="u-bgc-pink">
            <a href="${pageContext.request.contextPath}/instructor/test">
                        <span class="test-mana__icon-box">
                            <ion-icon name="refresh-outline"></ion-icon>
                        </span>Làm mới
            </a>
          </li>
        </ul>
      </div>
      
      <div class="student-mana__tool-2">
        <div>
          <label for="exam-select">Lọc kì thi:</label>
          <select name="exam-select" id="exam-select">
            <option value="">Tất cả kì thi</option>
            <% for (Exam exam : examDTO.getExams()) { %>
            <option value="<%= exam.getExamID() %>">
              <%= exam.getName() %>
            </option>
            <% } %>
          </select>
        </div>
        
        <div>
          <label for="search">Tìm kiếm:</label>
          <input
              type="text"
              id="search"
              placeholder="Nhập mã đề thi và nhấn Enter"
          />
        </div>
      </div>
      
      <div class="test-mana__content table u-margin-bottom-small u-margin-top-small">
        <div class="thead">
          <div class="tr">
            <div class="th">MĐT</div>
            <div class="th">Thuộc môn</div>
            <div class="th">Thuộc kì thi</div>
            <div class="th">Bắt đầu lúc</div>
            <div class="th">Kết thúc lúc</div>
            <div class="th">Thời lượng</div>
            <div class="th">&nbsp;</div>
          </div>
        </div>
        
        <div class="tbody">
          <% for (Test test : testDTO.getTests()) { %>
          <div class="tr">
            <div class="td"><%= test.getTestID() %></div>
            <div class="td"><%= SubjectRepository.getSubjectNameByID(test.getSubjectID()) %></div>
            <div class="td"><%= ExamRepository.getExamNameByID(test.getExamID()) %></div>
            <div class="td"><%= Utilities.formatDateTime(test.getDateStart())  %></div>
            <div class="td"><%= Utilities.formatDateTime(test.getDateEnd())  %></div>
            <% if (test.getDuration() != null) { %>
            <div class="td"><%= Utilities.convertTimeToMinutes(test.getDuration()) %> phút</div>
            <% } else { %>
            <div class="td">Duration is null</div>
            <% } %>
            <div class="td">
              <a
                  id="<%= test.getTestID() %>" class="show"
                  href="${pageContext.request.contextPath}/instructor/test/details?id=<%=
                  test.getTestID() %>"
              ><i class="bx bx-show"></i
              ></a>
              <a
                  id="<%= test.getTestID() %>" class="show"
                  href="${pageContext.request.contextPath}/instructor/test/results?id=<%=
                  test.getTestID() %>"
              ><i class='bx bx-stats'></i></a>
            </div>
          </div>
          <% } %>
        </div>
      </div>
    </section>
  </main>
  
  <script
      type="module"
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"
  ></script>
  <script
      nomodule
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"
  ></script>
  <script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>
  <script type="module">
      import { createDataForExcel, exportToExcel } from "../assets/js/instructor/util.js";
      
      document.querySelector("#export-test-list").addEventListener("click", () => {
          exportToExcel(createDataForExcel(), "Danh sách Đề thi");
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
  <script>
      const contextPath = "<%= request.getContextPath() %>";
  </script>
  <script src="../assets/js/instructor/instructor.js"></script>
  <script type="module" src="../assets/js/instructor/test-filtering.js"></script>
</body>
</html>
