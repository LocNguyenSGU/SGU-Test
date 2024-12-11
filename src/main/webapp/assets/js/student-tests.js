function updateResultStatus(ResultID){
    var url = "http://localhost:9999/Project/studentSearch/resultStatus"
       const params = {
            ResultID: ResultID,
            Status:"IN_PROGRESS"
       }
       fetch(url,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
       })
}
function checkExamExist() {
      var url = "http://localhost:9999/Project/studentSearch/exam";
      var size = 1000;
      var page = 1;
      var search = "";
      const params = {
        size,
        page,
        search:encodeURIComponent(search),
        status:"IN_PROGRESS",
      }
      fetch(url, {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(params)
      })
      .then(response => response.json())
      .then(data => {
          if(data.data.exams.length === 0){
            document.getElementById("content").innerHTML = `
                <div style="width:100%;height:100%;display:flex;align-items:center;justify-content:center;">
                    <div>
                        <img src="../assets/img/th.jpg"/>
                        <p>Chưa có kỳ thi nào đang diễn ra</p>
                    </div>
                </div>
            `
          }else{
            getTestWithExamID(data.data.exams[0].ExamID,data.data.exams[0].Name);
          }
      });
}
function checkTestStatus(status){
    if(status === "COMPLETED"){
        return "Đã kết thúc"
    }else if(status === "IN_PROGRESS"){
        return "Đang diễn ra"
    }else{
        return "Chưa diễn ra"
    }
}
function checkResultStatus(status){
    if(status === "COMPLETED"){
        return "Đã nộp"
    }else if(status === "IN_PROGRESS"){
        return "Chưa nộp bài"
    }else{
        return "Chưa làm bài"
    }
}
function openInNewTab(id,ResultID) {
        if(window.confirm("Bạn có chắc chắn muốn tham gia ?")){
            const urlWithParams = new URL("http://localhost:9999/Project/student/form");
            urlWithParams.searchParams.append("testID", id);
            updateResultStatus(ResultID);
            var win = window.open(urlWithParams.toString(), '_blank');
            win.focus();
        }
}
function getTestWithExamID(ExamID,Name){
    let url = "http://localhost:9999/Project/studentSearch/result";
    const params = {
        ExamID:ExamID,
        StudentID:(JSON.parse(localStorage.getItem("info"))).StudentID,
      }
      fetch(url, {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(params)
      })
      .then(response => response.json())
      .then(data => {
                const content = document.getElementById("content")
                content.innerHTML +=`
                    <p style="font-size:20px;">Kỳ thi: ${Name}</p>
                `
                var notStartDiv = `
                    <h3 class="headerText" style="margin-bottom:10px;">Chưa diễn ra</h3>
                `
                var inProgressDiv = `
                    <h3 class="headerText" style="margin-bottom:10px;">Đang diễn ra</h3>
                `
                var completedDiv = `
                    <h3 class="headerText" style="margin-bottom:10px;">Đã kết thúc</h3>
                `
                data.data.forEach(item => {
                    if(item.TestStatus === "NOT_STARTED"){
                        notStartDiv += `
                            <div style="padding:10px;border-radius:20px;box-shadow: 0 7px 25px rgba(0, 0, 0, 0.08);height:60px;">
                                <div style="display:flex;align-items:center;justify-content:space-between;height:100%;width:100%;">
                                    <p style="font-weight:700;">${item.SubjectName}</p>
                                    <p>${item.DateStart}</p>
                                    <p>${item.Duration}</p>
                                    <p>${checkResultStatus(item.TestStatus)}</p>
                                </div>
                            </div>
                        `
                    }else if(item.TestStatus === "IN_PROGRESS"){
                        inProgressDiv += `
                            <div style="padding:10px;border-radius:20px;box-shadow: 0 7px 25px rgba(0, 0, 0, 0.08);height:80px;">
                                <div style="display:flex;align-items:center;justify-content:space-between;height:100%;width:100%;">
                                    <div>
                                        <p style="font-size:15px;color:gray;">Môn thi</p>
                                        <p style="font-weight:700;">${item.SubjectName}</p>
                                    </div>
                                    <div>
                                        <p style="font-size:15px;color:gray;">Thời gian bắt đầu</p>
                                        <p>${item.DateStart}</p>
                                    </div>
                                    <div>
                                        <p style="font-size:15px;color:gray;">Thời lượng</p>
                                        <p>${item.Duration}</p>
                                    </div>
                                    <div>
                                         <p style="font-size:15px;color:gray;">Trạng thái làm</p>
                                         <p>${checkResultStatus(item.Status)}</p>
                                    </div>
                                    <div>
                                        <p style="font-size:15px;color:gray;">Trạng thái bài thi</p>
                                        <p>${checkTestStatus(item.TestStatus)}</p>
                                    </div>
                                    ${
                                        item.Status !== "COMPLETED" ? `<div>
                                        <p style="font-size:15px;color:gray;">Thao tác</p>
                                        <button class="export" onclick='openInNewTab(${item.TestID},${data.data.ResultID})'>Vào thi</button>
                                        </div>`:
                                        `<div>
                                        </div>`
                                    }
                                </div>
                            </div>
                        `
                    }else{
                        completedDiv += `
                            <div style="padding:10px;border-radius:20px;box-shadow: 0 7px 25px rgba(0, 0, 0, 0.08);height:60px;">
                                <div style="display:flex;align-items:center;justify-content:space-between;width:100%;height:100%">
                                    <p style="font-weight:700;">${item.SubjectName}</p>
                                    <p>${item.DateStart}</p>
                                    <p>${item.Duration}</p>
                                    <p>${checkResultStatus(item.TestStatus)}</p>
                                </div>
                            </div>
                        `
                    }
                })
                content.innerHTML += inProgressDiv;
                content.innerHTML += notStartDiv;
                content.innerHTML += completedDiv;
                content.innerHTML += `</div>`
      })
}
checkExamExist();