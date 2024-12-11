<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ page import="payload.response.Response" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Result" %>
<%@ page import="entity.Employee" %>
<%@ page import="service.instructor.InfoEmployeeService" %>
<%@ page import="DTO.InfoEmployeeAndMajorDTO" %>


<%
  InfoEmployeeService infoEmployeeService = new InfoEmployeeService();
  InfoEmployeeAndMajorDTO info = (InfoEmployeeAndMajorDTO)
      infoEmployeeService.getInfoEmployeeAndMajorDTOByIDEmployee(1).getData();
  Employee instructor = info.getEmployee();
  
  Response resp = (Response) request.getAttribute("results");
  List<Result> results = (List<Result>) resp.getData();
  
  boolean isEmpty = results.isEmpty();
  
  int[] testIDs = new int[results.size()];
  for (int i = 0; i < results.size(); i++) {
    testIDs[i] = results.get(i).getTestID();
  }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Kết quả của Sinh viên</title>
  
  
  <link
      href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"
      rel="stylesheet"
  />
  
  <link rel="stylesheet" href="../../assets/css/xyz.css" />
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
          <img src="../../assets/img/lecture.jpg" alt="face" class="welcome__img" />
        <span
          class="welcome__text"
      ><%= instructor.getLastName() + " " + instructor.getFirstName() %></span>
        </span>
    </section>
    
    <section class="student-mana">
      <header class="student-mana__header">
        <h2 class="heading-secondary add-student-name">Kết quả của </h2>
      </header>
      
      <% if (isEmpty) { %>
      <div class="student-mana__content u-margin-top-small">
        <p class="u-center-text">Không có kết quả nào</p>
      </div>
      <div class="u-center-text u-margin-top-very-large">
        <a
            href="${pageContext.request.contextPath}/instructor/student"
            class="btn-normal btn-normal--sub u-margin-right-small">
          Quay lại &larr;
        </a>
      </div>
      <% } else { %>
      <div
          class="student-mana__content table u-margin-bottom-small u-margin-top-small"
      >
        <div class="thead">
          <div class="tr">
            <div class="th" style="width: 6%">MSSV</div>
            <div class="th" style="width: 20%">Họ tên</div>
            <div class="th" style="width: 20%">Môn thi</div>
            <div class="th" style="width: 20%">Kì thi</div>
            <div class="th" style="width: 20%">Số câu Đúng/Sai</div>
            <div class="th" style="width: 14%">Điểm</div>
          </div>
        </div>
        
        <div class="tbody">
          <% int i = 0; for (Result result : results) { %>
          <div class="tr" id="r<%= i++ %>">
            <div class="td"><%= result.getStudentID() %></div>
            <div class="td student-name">&nbsp;</div>
            <div class="td subject-name">&nbsp;</div>
            <div class="td exam-name">&nbsp;</div>
            <div class="td"><%= result.getTotalCorrect()%>/<%= result.getTotalIncorrect() %></div>
            <div class="td point"><%= result.getTotalPoint() %></div>
          </div>
          <% } %>
        </div>
      </div>
      <div class="u-center-text u-margin-top-very-large">
        <a
            href="${pageContext.request.contextPath}/instructor/student"
            class="btn-normal btn-normal--sub u-margin-right-small">
          Quay lại &larr;
        </a>
        <a
            id="export-student-results" href="#"
            class="btn-normal btn-normal--main">
          Trích xuất dữ liệu này &rarr;
        </a>
      </div>
      <% } %>
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
  
  <script>
      let params = new URLSearchParams(window.location.search);
      let studentID = params.get('id');
      
      const contextPath = "${pageContext.request.contextPath}";
      testIDs = [<%= java.util.Arrays.toString(testIDs).replaceAll("\\[|\\]", "") %>];
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
   <script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>
   <script src="../../assets/js/instructor/instructor.js"></script>
   <script type="module" src="../../assets/js/instructor/student-results.js"></script>

</body>
</html>
