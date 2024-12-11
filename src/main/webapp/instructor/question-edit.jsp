<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


<%@ page import="entity.Question, entity.Option, java.util.List" %>
<%@ page import="payload.response.Response" %>
<%@ page import="DTO.OptionDTO" %>
<%@ page import="java.util.Objects" %>
<%@ page import="DTO.SubjectDTO" %>
<%@ page import="entity.Subject" %>

<%
  Question question = (Question) request.getAttribute("question");
  int correctOption = question.getCorrectAnswer();
  
  String level = String.valueOf(question.getLevel());
  double point = question.getPoint();
  
  Response optionsRep = (Response) request.getAttribute("options");
  OptionDTO optionDTO = (OptionDTO) optionsRep.getData();
  List<Option> options = optionDTO.getOptions();
  
  // create an array containing id of all options
  StringBuilder optionsStr = new StringBuilder("[");
  for (int i = 0; i < options.size(); i++) {
    optionsStr.append(options.get(i).getOptionID());
    if (i != options.size() - 1) {
      optionsStr.append(", ");
    }
  }
  optionsStr.append("]");
  
  
  final int numOptions = options.size();
  
  Response allSubjects = (Response) request.getAttribute("allSubjects");
  SubjectDTO subjectDTO = (SubjectDTO) allSubjects.getData();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Chỉnh sửa Câu hỏi</title>
  
  
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  
  <link rel="stylesheet" href="../../assets/css/xyz.css" />
</head>

<body class="form__body">
  <header class="fixed-header">
    <a href="${pageContext.request.contextPath}/instructor/question" class="btn btn--close">
      <i class="bx bx-arrow-back fixed-header__icon"></i>
    </a>
    <h2 class="fixed-header__title">Chỉnh sửa câu hỏi</h2>
  </header>
  
  <main>
    <div class="form__tools">
      <a
          href="${pageContext.request.contextPath}/instructor/question/details?id=<%= question.getQuestionID() %>"
          class="btn-normal btn-normal--sub">Quay lại chi tiết &larr;
      </a>
      <a href="#" id="btn-update" class="btn-normal btn-normal--main">Hoàn thành ngay
                                                                      &check;</a>
    </div>
    
    <form class="form form--question" id="q">
      <h3 class="form__header">Câu hỏi <%= question.getQuestionID() %>
      </h3>
      
      <div class="form__group">
        <label for="q-title" class="form__label"><i
            class="bx bx-question-mark form__icon bigger"></i>&nbsp;
        </label>
        <textarea
            id="q-title" class="form__textarea" rows="3" required=""
            placeholder="Nội dung câu hỏi"><%= question.getTitle() %></textarea>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label for="q-point" class="form__label"><i
            class="bx bx-edit-alt form__icon"></i><span class="des-span">Điểm số (<span
            id="q-point-value" class="u-font-weight-500"></span>):</span>
        </label>
        <input
            type="range" id="q-point" class="form__input" min=".5" max="10.0" step=".5" required
            value="<%= point %>">
      </div>
      
      <div class="form__group flex-align-items-center">
        <label for="q-level" class="form__label"><i
            class="bx bx-star form__icon"></i><span class="des-span">Độ khó:</span>
        </label>
        <select
            id="q-level" class="form__select" required>
          <option value="EASY" <%=Objects.equals(level, "EASY") ? "selected" : "" %> >Dễ</option>
          <option value="MEDIUM" <%=Objects.equals(level, "MEDIUM") ? "selected" : "" %>>Trung
                                                                                         bình
          </option>
          <option value="HARD" <%=Objects.equals(level, "HARD") ? "selected" : "" %>>Khó</option>
        </select>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label
            for="q-subject" class="form__label"><i
            class="bx bx-book-open form__icon"></i><span
            class="des-span">Môn học:</span>
        </label>
        <select
            id="q-subject" class="form__select" required="">
          <% for (Subject subject : subjectDTO.getSubjects()) { %>
          <option
              value="<%= subject.getSubjectID() %>"
              <%= subject.getSubjectID() == question.getSubjectID() ? "selected" : "" %>><%= subject.getName() %>
          </option>
          <% } %>
        </select>
      </div>
      
      <% for (int i = 0; i < numOptions; i++) { %>
      <div class="form__group">
        <label for="q-opt<%= i + 1 %>" class="form__label"><i
            class=<%= correctOption == options.get(i).getOptionID() ?
            "\"bx bx-radio-circle-marked form__icon bigger correct\"" :
            "\"bx bx-radio-circle form__icon bigger\"" %>></i>&nbsp;
        </label>
        <input
            type="text" id="q-opt<%= i + 1 %>" class="form__input" required=""
            placeholder="Phương án <%= (char) ('A' + i) %>"
            value="<%= options.get(i).getTitle() %>">
      </div>
      <% } %>
      
      <button class="form__add-option u-margin-right-very-small" id="btn-add-option">
        <i class="bx bx-plus"></i>&nbsp;Thêm phương án
      </button>
      
      <button class="form__add-option" id="btn-delete-option">
        <i class="bx bx-minus"></i>&nbsp;Bớt phương án
      </button>
      
      <div style="display: none;"><input type="text" id="q-correct" value=""></div>
    </form>
  </main>
  
  <script>
      const correctOption = <%= correctOption %>;
      const optionIDs = <%= optionsStr %>;
      const questionID = <%= question.getQuestionID() %>;
  </script>
  <script
      type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
  <script nomodule="" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
  
  <script type="module" src="../../assets/js/instructor/question-updating.js"></script>


</body>
</html>
