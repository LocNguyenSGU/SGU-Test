<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ page import="entity.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="payload.response.Response" %>
<%@ page import="entity.Enum.QuestionLevel" %>
<%@ page import="repository.SubjectRepository" %>
<%@ page import="DTO.OptionDTO" %>
<%@ page import="entity.Option" %>
<%@ page import="entity.Employee" %>
<%@ page import="service.instructor.InfoEmployeeService" %>
<%@ page import="DTO.InfoEmployeeAndMajorDTO" %>

<%
  InfoEmployeeService infoEmployeeService = new InfoEmployeeService();
  InfoEmployeeAndMajorDTO info = (InfoEmployeeAndMajorDTO)
      infoEmployeeService.getInfoEmployeeAndMajorDTOByIDEmployee(1).getData();
  Employee instructor = info.getEmployee();
  
  Question question = (Question) request.getAttribute("question");
  int correctOption = question.getCorrectAnswer();
  String level = question.getLevel() == QuestionLevel.EASY ? "Dễ" :
      question.getLevel() == QuestionLevel.MEDIUM ? "Trung bình" : "Khó";
  String subjectName = SubjectRepository.getSubjectNameByID(question.getSubjectID());
  double point = question.getPoint();
  
  Response optionsRep = (Response) request.getAttribute("options");
  OptionDTO optionDTO = (OptionDTO) optionsRep.getData();
  List<Option> options = optionDTO.getOptions();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Chi tiết Câu hỏi</title>
  
  
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  
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
      
      <li class="navigation__item navigation__item--hovered">
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
            <img src="../../assets/img/lecture.jpg" alt="face" class="welcome__img">
            <span class="welcome__text">
              <%= instructor.getLastName() + " " + instructor.getFirstName() %>
            </span>
        </span>
    </section>
    
    <section class="question">
      <div class="question__header">
        <h2 class="heading-secondary">Chi tiết câu hỏi</h2>
      </div>
      
      <div class="question__content">
        <div class="question__content--bg"></div>
        
        <div class="question__content--left">
          <h3 class="question__content--left--header">
            Nội dung câu hỏi
          </h3>
          
          <div class="question__content--left--body">
            <p class="question__content--left--body--text">
              <%=question.getTitle()%>
            </p>
          </div>
          
          <div class="question__content--left--footer">
            <span class="question__content--left--info">
                mức độ: <%= level %>
            </span>
            <span class="question__content--left--info">
                điểm: <%= point %>
            </span>
            <span class="question__content--left--info">
                môn học: <%= subjectName %>
            </span>
          </div>
        </div>
        
        <div class="question__content--right">
          <h3 class="question__content--right--header">
            Đáp án
          </h3>
          
          <div class="question__content--right--body">
            <ul class="question__option-list">
              <% for (Option option : options) { %>
              <li class="question__option-item">
                <input
                    type="radio" name="option" id="<%= option.getOptionID() %>"
                    disabled
                  <% if (option.getOptionID() == correctOption) { %>
                    checked
                  <% } %>
                >
                <label for="<%= option.getOptionID() %>">
                  <%= option.getTitle() %>
                </label>
              </li>
              <% } %>
            </ul>
          </div>
        </div>
      </div>
      
      <div class="u-center-text">
        <a
            href="${pageContext.request.contextPath}/instructor/question"
            class="btn-normal btn-normal--sub u-margin-right-small">
          Quay lại &larr;
        </a><a
          href="${pageContext.request.contextPath}/instructor/question/edit?id=<%=
                question.getQuestionID() %>"
          class="btn-normal btn-normal--main">
        Sửa câu hỏi này &rarr;
      </a>
      </div>
    
    </section>
  </main>
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
  
  <script src="../../assets/js/instructor/instructor.js"></script>
</body>
</html>