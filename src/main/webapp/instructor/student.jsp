<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ page import="entity.Student" %>
<%@ page import="DTO.StudentDTO" %>

<%@ page import="entity.Major" %>
<%@ page import="DTO.MajorDTO" %>

<%@ page import="payload.response.Response" %>
<%@ page import="controller.instructor.Utilities" %>
<%@ page import="static repository.MajorRepository.getMajorNameByID" %>
<%@ page import="entity.Employee" %>
<%@ page import="DTO.InfoEmployeeAndMajorDTO" %>
<%@ page import="service.instructor.InfoEmployeeService" %>


<%
  InfoEmployeeService infoEmployeeService = new InfoEmployeeService();
  InfoEmployeeAndMajorDTO info = (InfoEmployeeAndMajorDTO)
      infoEmployeeService.getInfoEmployeeAndMajorDTOByIDEmployee(1).getData();
  Employee instructor = info.getEmployee();
  
  Response allStudents = (Response) request.getAttribute("allStudents");
  StudentDTO studentDTO = (StudentDTO) allStudents.getData();
  
  Response allMajors = (Response) request.getAttribute("allMajors");
  MajorDTO majorDTO = (MajorDTO) allMajors.getData();
  
  int _majorID = Integer.parseInt((String) request.getAttribute("_majorID"));
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Danh sách Sinh viên</title>
  
  
  <link
      href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
      rel="stylesheet"
  />
  
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
      
      <li class="navigation__item">
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
      
      <li class="navigation__item navigation__item--hovered">
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
        <ion-icon
            class="navigation__icon navigation__icon--menu"
            name="menu-outline"
        ></ion-icon>
      </a>
      
      <span class="welcome">
          <img src="../assets/img/lecture.jpg" alt="face" class="welcome__img" /><span
          class="welcome__text"
      ><%= instructor.getLastName() + " " + instructor.getFirstName() %></span
      >
        </span>
    </section>
    
    <section class="student-mana">
      <header class="student-mana__header">
        <h2 class="heading-secondary">Danh sách Sinh viên</h2>
      </header>
      
      <div class="student-mana__tool">
        <ul>
          <li class="u-bgc-blue">
            <a
                href="#" id="export-student-list"
            ><span
                class="student-mana__icon-box"
            ><i class="bx bxs-file-import"></i></span
            >Trích xuất</a
            >
          </li>
          <li class="u-bgc-pink">
            <a
                href="${pageContext.request.contextPath}/instructor/student"
            ><span
                class="student-mana__icon-box"
            ><ion-icon name="refresh-outline"></ion-icon></span
            >Làm mới</a
            >
          </li>
        </ul>
      </div>
      
      <div class="student-mana__tool-2">
        <div>
          <label for="major-select">Lọc theo Khoa:</label>
          <select name="major-select" id="major-select">
            <option value="">Tất cả Khoa</option>
            <% for (Major major : majorDTO.getMajors()) { %>
            <option value="<%= major.getMajorID() %>"
                <%= major.getMajorID() == _majorID ? "selected" : "" %>
            ><%= major.getName() %></option>
            <% } %>
          </select>
        </div>
        
        <div>
          <label for="search">Tìm kiếm:</label>
          <input
              type="text"
              id="search"
              placeholder="Nhập MSSV hoặc tên sinh viên và nhấn Enter"
          />
        </div>
      </div>
      
      <div
          class="student-mana__content table u-margin-bottom-small u-margin-top-small"
      >
        <div class="thead">
          <div class="tr">
            <div class="th">MSSV</div>
            <div class="th">Họ tên</div>
            <div class="th">SĐT</div>
            <div class="th">Email</div>
            <div class="th">Ngày sinh</div>
            <div class="th">GT</div>
            <div class="th">Khoa</div>
            <div class="th">&nbsp;</div>
          </div>
        </div>
        
        <div class="tbody">
          <% for (Student student : studentDTO.getStudents()) { %>
          <div class="tr">
            <div class="td"><%= student.getStudentID() %>
            </div>
            <div class="td"><%= student.getLastName() + " " + student.getFirstName() %></div>
            <div class="td"><%= student.getPhone() %>
            </div>
            <div class="td"><%= student.getEmail() %>
            </div>
            <div class="td"><%= Utilities.formatDate(student.getDateOfBirth()) %>
            </div>
            <div class="td"><%= student.getGender() ? "Nam" : "Nữ" %></div>
            <div class="td"><%= getMajorNameByID(student.getMajorID()) %></div>
            <div class="td">
              <a
                  class="show"
                  href="${pageContext.request.contextPath}/instructor/student/results?id=<%=
                  student.getStudentID()%>"
              ><i class="bx bx-show"></i
              ></a>
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
      
      document.querySelector("#export-student-list").addEventListener("click", () => {
          exportToExcel(createDataForExcel(), "Danh sách Sinh viên");
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
  <script type="module" src="../assets/js/instructor/student-filtering.js"></script>
</body>
</html>
