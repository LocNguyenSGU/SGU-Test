<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


<%@ page import="entity.Option" %>
<%@ page import="entity.Test" %>
<%@ page import="entity.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="static controller.instructor.Utilities.convertTimeToMinutes" %>
<%@ page import="entity.Enum.QuestionLevel" %>

<%
  Test test = (Test) request.getAttribute("test");
  List<Question> questions = (List<Question>) request.getAttribute("questions");
  List<Option> options = (List<Option>) request.getAttribute("options");
  
  int numQuestion = 0;
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Chi tiết Bài thi</title>
  
  
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  
  <link rel="stylesheet" href="../../assets/css/xyz.css" />
</head>

<body class="form__body">
  <header class="fixed-header">
    <a href="${pageContext.request.contextPath}/instructor/test" class="btn btn--close">
      <i class='bx bx-arrow-back fixed-header__icon'></i>
    </a>
    <h2 class="fixed-header__title">Chi tiết Bài thi</h2>
  </header>
  
  <main>
    <form class="form">
      <h3 class="form__header">Thông tin chung</h3>
      
      <div class="form__group">
        <label for="test-title" class="form__label"><i
            class='bx bx-edit form__icon'></i>
          &nbsp;
        </label>
        <input
            type="text" id="test-title" class="form__input" readonly
            value="<%= test.getDescription() %>"
        />
      </div>
      
      <div class="form__group">
        <label for="test-des" class="form__label"><i class='bx bx-comment-edit form__icon'></i>
          &nbsp;
        </label>
        <textarea
            type="text" id="test-des" class="form__textarea" rows="3"
            readonly><%= test.getDescription().trim() %>
        </textarea>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label
            for="test-subject" class="form__label"><i
            class="bx bx-book-open form__icon"></i><span
            class="des-span">Môn học:</span>
        </label>
        <select
            id="test-subject" class="form__select" disabled>
        </select>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label
            for="test-exam" class="form__label"><i
            class="bx bx-book form__icon"></i><span
            class="des-span">Kì thi:</span>
        </label>
        <select
            id="test-exam" class="form__select" disabled>
        </select>
      </div>
      
      <h3 class="form__header u-margin-top-medium">Cài đặt thời gian</h3>
      
      <div class="form__group">
        <label for="enable-duration" class="form__label" id="enable-duration-label">
          <i class='bx bx-checkbox-checked form__icon bigger'></i>
        </label>
        <input type="checkbox" id="enable-duration">
        
        <label for="test-duration" class="form__text-helper flex-1">
          <span class="u-font-weight-500">Thời lượng bài thi:</span>
          Thời gian tối đa để sinh viên làm bài kiểm tra.
        </label>
        <input
            id="test-duration" type="number" class="form__input" step="1" min="5" max="600"
            value="<%= convertTimeToMinutes(test.getDuration()) %>" readonly>
      </div>
      
      <div class="form__group">
        <label
            for="enable-starting-datetime" class="form__label"
            id="enable-starting-datetime-label">
          <i class='bx bx-checkbox-checked form__icon bigger'></i>
        </label>
        <input type="checkbox" id="enable-starting-datetime">
        
        <label for="test-starting-datetime" class="form__text-helper flex-1">
          <span class="u-font-weight-500">Thời gian bắt đầu làm bài:</span>
          Sinh viên có thể bắt đầu làm bài thi từ thời điểm này.
        </label>
        <input
            id="test-starting-datetime" type="datetime-local" class="form__input"
            value="<%= test.getDateStart().toString().replace("T", " ") %>" readonly
        >
      </div>
      
      <div class="form__group">
        <label
            for="enable-ending-datetime" class="form__label"
            id="enable-ending-datetime-label">
          <i class='bx bx-checkbox-checked form__icon bigger'></i>
        </label>
        <input type="checkbox" id="enable-ending-datetime">
        
        <label for="test-ending-datetime" class="form__text-helper flex-1">
          <span class="u-font-weight-500">Thời gian kết thúc làm bài:</span>
          Sinh viên không thể tiếp tục làm bài thi từ thời điểm này.
        </label>
        <input
            id="test-ending-datetime" type="datetime-local" class="form__input"
            value="<%= test.getDateEnd().toString().replace("T", " ") %>" readonly
        >
      </div>
    </form>
    
    <!-- Question list, a form per question -->
    <% for (Question question : questions) { %>
    <form class="form form--question">
      <h3 class="form__header">Câu hỏi <%= ++numQuestion %> (Chỉ đọc)
      </h3>
      
      <div class="form__group">
        <label for="q<%= question.getQuestionID() %>-title" class="form__label"><i
            class='bx bx-comment-edit form__icon'></i>
          &nbsp;
        </label>
        <textarea
            type="text" id="q<%= question.getQuestionID() %>-title" class="form__textarea" rows="3"
            readonly><%= question.getTitle().trim() %>
          </textarea>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label for="q<%= question.getQuestionID() %>-point" class="form__label">
          <i class="bx bx-edit-alt form__icon"></i>
          <span class="des-span">Điểm số (<span
              id="q<%= question.getQuestionID() %>-point-value"
              class="u-font-weight-500"><%= question.getPoint() %></span>):</span>
        </label>
        <input
            type="range" id="q<%= question.getQuestionID() %>-point" class="form__input" min=".5"
            max="10.0" step=".5"
            value="<%= question.getPoint() %>" disabled
        >
      </div>
      
      <div class="form__group flex-align-items-center">
        <label for="q1-level" class="form__label"><i
            class="bx bx-star form__icon"></i><span class="des-span">Độ khó:</span>
        </label>
        <select
            id="q1-level" class="form__select" disabled>
          <option
              value="EASY"
              <%= (question.getLevel() == QuestionLevel.EASY) ? "selected" : "" %>>
            Dễ
          </option>
          <option
              value="MEDIUM"
              <%= (question.getLevel() == QuestionLevel.MEDIUM) ? "selected" : "" %>>
            Trung bình
          </option>
          <option
              value="HARD"
              <%= (question.getLevel() == QuestionLevel.HARD) ? "selected" : "" %>>
            Khó
          </option>
        </select>
      </div>
      
      <!-- Options which has QuestionID = current QuestionID -->
      <% for (Option option : options) {
        if (option.getQuestionID() == question.getQuestionID()) { %>
      <div class="form__group">
        <label
            for="q<%= question.getQuestionID() %>-opt<%= option.getOptionID() %>"
            class="form__label"><i
            class="bx <%= option.getOptionID() == question.getCorrectAnswer() ?
            "bx-radio-circle-marked"
             : "bx-radio-circle" %> bx-radio-circle form__icon bigger"></i>&nbsp;
        </label>
        <input
            type="text" id="q<%= question.getQuestionID() %>-opt<%= option.getOptionID() %>"
            class="form__input" readonly
            value="<%= option.getTitle() %>">
      </div>
      <% } %>
      <% } %>
    
    </form>
    <% } %>
    
    <div class="form__tools">
      <a href="${pageContext.request.contextPath}/instructor/test"
         class="btn-normal btn-normal--sub">Quay lại &larr;</a>
    </div>
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
      const subjectID = <%= test.getSubjectID() %>;
      const examID = <%= test.getExamID() %>;
  </script>
  <script src="../../assets/js/instructor/test-details.js" type="module"></script>

</body>
</html>
