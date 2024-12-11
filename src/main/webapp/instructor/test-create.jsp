<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Tạo bài thi mới</title>
  
  
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  
  <link rel="stylesheet" href="../../assets/css/xyz.css" />
</head>

<body class="form__body">
  <header class="fixed-header">
    <a href="${pageContext.request.contextPath}/instructor/test" class="btn btn--close">
      <i class='bx bx-arrow-back fixed-header__icon'></i>
    </a>
    <h2 class="fixed-header__title">Tạo bài thi mới</h2>
  </header>
  
  <main>
    <form id="test-form" class="form">
      <h3 class="form__header">Thông tin chung</h3>
      
      <div class="form__group">
        <label for="test-des" class="form__label"><i class='bx bx-comment-edit form__icon'></i>
          &nbsp;
        </label>
        <textarea
            type="text" id="test-des" class="form__textarea" rows="3" required
            placeholder="Mô tả ngắn gọn"></textarea>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label
            for="test-subject" class="form__label"><i
            class="bx bx-book-open form__icon"></i><span
            class="des-span">Môn học:</span>
        </label>
        <select
            id="test-subject" class="form__select">
        </select>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label
            for="test-exam" class="form__label"><i
            class="bx bx-book form__icon"></i><span
            class="des-span">Kì thi:</span>
        </label>
        <select
            id="test-exam" class="form__select">
        </select>
      </div>
      
      <div class="form__group flex-align-items-center">
        <label for="test-numcop" class="form__label">
          <i class="bx bx-edit-alt form__icon"></i>
          <span class="des-span">Số mã đề (<span id="test-numcop-value"
                                                 class="u-font-weight-500"></span>):</span>
        </label>
        <input
            type="range" id="test-numcop" class="form__input" value="1" max="24" min="1" step="1"
            required />
      </div>
      
      <h3 class="form__header u-margin-top-medium">Cài đặt thời gian</h3>
      
      <div class="form__group">
        <label for="enable-duration" class="form__label" id="enable-duration-label">
          <i class='bx bx-checkbox form__icon bigger'></i>
        </label>
        <input type="checkbox" id="enable-duration">
        
        <label for="test-duration" class="form__text-helper flex-1">
          <span class="u-font-weight-500">Thời lượng bài thi:</span>
          Thời gian tối đa để sinh viên làm bài kiểm tra.
        </label>
        <input
            id="test-duration" type="number" class="form__input" step="1" min="5" max="600"
            value="45">
      </div>
      
      <div class="form__group">
        <label
            for="enable-starting-datetime" class="form__label"
            id="enable-starting-datetime-label">
          <i class='bx bx-checkbox form__icon bigger'></i>
        </label>
        <input type="checkbox" id="enable-starting-datetime">
        
        <label for="test-starting-datetime" class="form__text-helper flex-1">
          <span class="u-font-weight-500">Thời gian bắt đầu làm bài:</span>
          Sinh viên có thể bắt đầu làm bài thi từ thời điểm này.
        </label>
        <input
            id="test-starting-datetime" type="datetime-local" class="form__input">
      </div>
      
      <div class="form__group">
        <label
            for="enable-ending-datetime" class="form__label"
            id="enable-ending-datetime-label">
          <i class='bx bx-checkbox form__icon bigger'></i>
        </label>
        <input type="checkbox" id="enable-ending-datetime">
        
        <label for="test-ending-datetime" class="form__text-helper flex-1">
          <span class="u-font-weight-500">Thời gian kết thúc làm bài:</span>
          Sinh viên không thể tiếp tục làm bài thi từ thời điểm này.
        </label>
        <input
            id="test-ending-datetime" type="datetime-local" class="form__input">
      </div>
    </form>
    
    <div class="form__tools">
      <a href="#popup--choose-question" id="choose-question" class="btn-normal btn-normal--sub">Chọn câu hỏi</a>
      <a href="#" id="add-question" class="btn-animated">Thêm câu hỏi</a>
      <a href="#" id="create-test" class="btn-normal btn-normal--main">Hoàn thành ngay
                                                                       &check;</a>
    </div>
    
    <div class="popup" id="popup--choose-question">
      <div class="popup__background">&nbsp;</div>
      
      <div class="popup__content">
        <a href="#" class="popup__close">&times;</a>
        <h4 class="popup__header">Chọn câu hỏi mà bạn muốn thêm. Lưu ý rằng bạn không thể
                                  trực tiếp chỉnh sửa câu hỏi được chọn trên trang này. Để làm vậy,
                                  vui lòng quay lại trang "Sửa chi tiết Câu hỏi".</h4>
      </div>
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
  
  <script type="module" src="../../assets/js/instructor/test-create-helper.js"></script>
  <script type="module" src="../../assets/js/instructor/test-create-helper-2.js"></script>
</body>
</html>
