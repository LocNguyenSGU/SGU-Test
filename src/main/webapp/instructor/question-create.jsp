<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


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

<body class="form__body">
  <header class="fixed-header">
    <a href="${pageContext.request.contextPath}/instructor/question" class="btn btn--close">
      <i class='bx bx-arrow-back fixed-header__icon'></i>
    </a>
    <h2 class="fixed-header__title">Tạo câu hỏi mới</h2>
  </header>
  
  <main>
    <div class="form__tools">
      <a href="#" id="add-question" class="btn-normal btn-normal--sub">Thêm câu hỏi</a>
      <a href="#" id="creating" class="btn-normal btn-normal--main">Hoàn thành ngay
                                                                    &check;</a>
    </div>
  </main>
  
  <script>
      const contextPath = "<%= request.getContextPath() %>";
  </script>
  <script
      type="module"
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"
  ></script>
  <script
      nomodule
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"
  ></script>
  
  <script type="module" src="../../assets/js/instructor/question-create-helper.js"></script>
</body>
</html>
