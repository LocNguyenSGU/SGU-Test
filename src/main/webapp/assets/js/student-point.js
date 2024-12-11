// -*- coding: utf-8 -*-
var searchExam = document.querySelector('.inputInsideSelect');
var chevronExam = document.querySelector('.chevronInsideSelect');
document.querySelector('.close').addEventListener('click',()=>{
    document.getElementById("modal").style.display = "none";
    document.getElementById("overlay").style.display = "none";
})
searchExam.addEventListener('input', function(event) {
    clearTimeout(this.timeout);
    this.timeout = setTimeout(function(){
        loadSearchExam();
    },500)
});
chevronExam.addEventListener('click',function(event){
    loadSearchExam();
})

function loadSearchExam() {
      var url = "http://localhost:9999/Project/studentSearch/exam";
      var size = 10;
      var page = 1;
      var search = document.querySelector(".inputInsideSelect").value
      const params = {
        size,
        page,
        search:encodeURIComponent(search),
        status:"COMPLETED",
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
        // Xóa nội dung cũ của thẻ div trước khi thêm dữ liệu mới
        var resultDiv = document.getElementById("searchExamResult");
        resultDiv.style.display = "block"
        resultDiv.innerHTML = '';
        if(data.data.exams.length === 0){
            var newItem = document.createElement('div');
            newItem.className = "examResult";
            newItem.textContent = "Không tìm thấy dữ liệu"; // Thay 'item' bằng thuộc tính bạn muốn hiển thị từ mảng data
            // Define the onclick event handler
            resultDiv.appendChild(newItem);
        }else{
        // Duyệt qua mảng dữ liệu và thêm từng phần tử vào thẻ div
                data.data.exams.forEach(item => {
                    var newItem = document.createElement('div');
                    newItem.className = "examResult";
                    newItem.textContent = item.Name; // Thay 'item' bằng thuộc tính bạn muốn hiển thị từ mảng data
                    // Define the onclick event handler
                    newItem.onclick = function() {
                        document.querySelector('.inputInsideSelect').value = item.Name;
                        resultDiv.style.display = "none";
                        loadSearchResult(item.ExamID)
                    };
                    resultDiv.appendChild(newItem);
                });
        }
      });
}
function loadSearchResult (ExamID) {
        var url = "http://localhost:9999/Project/studentSearch/result";
        const params = {
            ExamID,
            StudentID:(JSON.parse(localStorage.getItem("info"))).StudentID
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
            console.log(data)
            var resultTable = document.getElementById("table");
            resultTable.innerHTML = '';
            if(data.data.length === 0){
                var emptyRow = document.createElement("tr");
                var emptyValue = document.createElement("td");
                emptyValue.textContent = "Không tìm thấy bài thi"
                emptyValue.colSpan = "6"
                emptyValue.id = "noData"
                emptyValue.style.padding = "20px 0px 0px 0px"
                emptyRow.appendChild(emptyValue);
                resultTable.appendChild(emptyRow);
            }else{
                data.data.forEach(item => {
                                var newRow = document.createElement("tr");
                                                            newRow.className = "row"

                                                          var id = document.createElement("td");
                                                          id.className = "value"
                                                          var name = document.createElement("td");
                                                          name.className = "value"
                                                          name.style.textAlign = "center"
                                                          var time = document.createElement("td");
                                                          time.className = "value"
                                                          time.style.textAlign = "right"
                                                          var start = document.createElement("td");
                                                          start.className = "value"
                                                          start.style.textAlign = "center"
                                                          var point = document.createElement("td");
                                                          point.className = "value"
                                                          point.style.textAlign = "center"
                                                          var status = document.createElement("td");
                                                          status.className = "value"
                                                          status.style.textAlign = "right"
                                                          var tdbutton = document.createElement("td");
                                                          tdbutton.style.textAlign = "right"
                                                          var buttondetails = document.createElement("button");
                                                          buttondetails.textContent = "Chi tiết"
                                                          buttondetails.className = "export"
                                                          var buttonexport = document.createElement("button");
                                                          buttonexport.textContent = "Xuất điểm"
                                                          buttonexport.className = "export"
                                                          buttonexport.style.marginLeft = "5px";
                                                           buttondetails.addEventListener("click",function(){
                                                                document.getElementById("modal").style.display = "block";
                                                                document.getElementById("overlay").style.display = "block";
                                                                fillResultDetails(item.ResultID,item.TotalCorrect,item.TotalIncorrect,item.TotalPoint,item.SubjectName,item.Description, item.Duration, item.NumberOfQuestion,item.TestID);
                                                            });
                                                          buttonexport.addEventListener('click',()=>{
                                                              downLoadTestInfo(item.SubjectName, item.Description, item.Duration, item.TotalPoint, item.NumberOfQuestion, item.TestID,item.DateStart,item.ResultID)
                                                          })
                                                          tdbutton.appendChild(buttondetails);
                                                          tdbutton.appendChild(buttonexport);
                                                          // Set the text content of the table cells
                                                          id.textContent = item.TestID;
                                                          name.textContent = item.SubjectName;
                                                          time.textContent = item.Duration;
                                                          point.textContent = item.TotalPoint + "đ";
                                                          start.textContent = item.DateStart;
                                                          newRow.appendChild(id);
                                                          newRow.appendChild(name);
                                                          newRow.appendChild(time);
                                                          newRow.appendChild(start);
                                                          newRow.appendChild(point);
                                                          newRow.appendChild(tdbutton);
                                                          // Define the onclick event handler
                                                          resultTable.appendChild(newRow);
                                                })
            }
    });
}
function fillResultDetails(resultID,TotalCorrect,TotalIncorrect,TotalPoint,subjectName, description, duration, numberOfQuestion, testId){
    document.getElementById("title").textContent = subjectName;
    document.getElementById("description").textContent = description;
    document.getElementById("duration").textContent = duration;
    document.getElementById("totalPoint").textContent = TotalPoint + "đ";
    document.getElementById("totalQuestion").textContent = numberOfQuestion + " câu";
    document.getElementById("correct").textContent = TotalCorrect;
    document.getElementById("incorrect").textContent = TotalIncorrect;
    document.getElementById("point").textContent = TotalPoint + "đ";
    document.getElementById("TestID").textContent = testId;
    var url = "http://localhost:9999/Project/studentSearch/resultdetails";
        const params = {
            ResultID:resultID,
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
                  var resultQuestion = document.getElementById("studentResult");
                     resultQuestion.innerHTML = '';
                    data.data.resultDetails.forEach(item => {
                        var resultDetailFrame = document.createElement("div")
                        resultDetailFrame.className = "resultFrame";
                        var questionFrame = document.createElement("div")
                        questionFrame.className = "QuestionFrame";
                        questionFrame.innerHTML = `
                            <p>${item.QuestionName} (${item.QuestionPoint}đ)</p>
                            <p>${item.Point}đ</p>
                        `;
                        var optionFrame = document.createElement("div")
                        optionFrame.className = "OptionFrame";
                        if(item.ChooseOption === 0){
                        item.options.forEach(option => {
                            const optionDiv = document.createElement('div');
                            optionDiv.style.display = "flex";
                            optionDiv.style.marginTop = "5px";
                            if(option.BackupOptionID === item.CorrectAnswer){
                                optionDiv.style.backgroundColor = "#0080008a";
                                optionDiv.style.padding = "5px"
                                optionDiv.style.borderRadius = "10px";
                            }
                                optionDiv.innerHTML = `
                                    <input type="radio" class="options" id="Option${option.BackupOptionID}" disabled value="${option.BackupOptionID}">
                                    <p class="optionsTitle">${option.Title}</p>
                                `;
                                optionFrame.appendChild(optionDiv)
                            })
                        }else if(item.ChooseOption === item.CorrectAnswer){
                            item.options.forEach(option => {
                            const optionDiv = document.createElement('div');
                            optionDiv.style.display = "flex";
                            optionDiv.style.marginTop = "5px";
                            if(item.ChooseOption === option.BackupOptionID){
                                optionDiv.style.backgroundColor = "#0080008a";
                                optionDiv.style.padding = "5px";
                                optionDiv.style.display = "flex";
                                optionDiv.style.borderRadius = "10px";
                                optionDiv.innerHTML = `
                                    <input type="radio" class="options" disabled checked="true"/>
                                    <p class="optionsTitle">${option.Title}</p>
                                `;
                            }else{
                                optionDiv.innerHTML = `
                                    <input type="radio" class="options" disabled/>
                                    <p class="optionsTitle">${option.Title}</p>
                                `;
                            }
                                optionFrame.appendChild(optionDiv)
                            })
                        }else{
                            item.options.forEach(option => {
                            const optionDiv = document.createElement('div');
                            optionDiv.style.display = "flex";
                            optionDiv.style.marginTop = "5px";
                            if(item.ChooseOption === option.BackupOptionID){
                                optionDiv.style.backgroundColor = "#ff000085";
                                optionDiv.style.padding = "5px";
                                optionDiv.style.borderRadius = "10px";
                                optionDiv.innerHTML = `
                                    <input type="radio" class="options" disabled checked="true">
                                    <p class="optionsTitle">${option.Title}</p>
                                `;
                            }else if(item.CorrectAnswer === option.BackupOptionID){
                                optionDiv.style.backgroundColor = "#0080008a";
                                optionDiv.style.padding = "5px";
                                optionDiv.style.borderRadius = "10px";
                                optionDiv.innerHTML = `
                                    <input type="radio" class="options" disabled>
                                    <p class="optionsTitle">${option.Title}</p>
                                `;
                            }else{
                                optionDiv.innerHTML = `
                                    <input type="radio" class="options" disabled>
                                    <p class="optionsTitle">${option.Title}</p>
                                `;
                            }
                            optionFrame.appendChild(optionDiv)
                            })
                        }
                        resultDetailFrame.appendChild(questionFrame);
                        resultDetailFrame.appendChild(optionFrame);
                        resultQuestion.appendChild(resultDetailFrame)
                    })
                  });
}
const downLoadTestInfo = (subjectName, description, duration, totalPoint, numberOfQuestion, testId,dateStart,resultID) => {
    var body = `
                             <!DOCTYPE html>
                             <html lang="vi">
                             <head>
                                 <meta charset="UTF-8">
                                 <style>
                                     body {
                                         font-family: Arial, sans-serif;
                                         font-size: 12px;
                                     }

                                     #header {
                                         text-align: center;
                                         margin-bottom: 20px;
                                     }

                                     #header h1, #header h2 {
                                         margin: 0;
                                     }

                                     #header h2 {
                                         font-size: 14px;
                                     }

                                     #info {
                                         text-align: center;
                                         margin-bottom: 20px;
                                     }

                                     #info table {
                                         border-collapse: collapse;
                                         width: 100%;
                                     }

                                     #info th, #info td {
                                         border: 1px solid #ccc;
                                         padding: 5px;
                                     }

                                     #questions {
                                         margin-bottom: 20px;
                                     }

                                     #questions h3 {
                                         margin-top: 0;
                                     }

                                     #questions .question {
                                         margin-bottom: 10px;
                                     }

                                     #questions .question p {
                                         margin: 0;
                                     }

                                     #questions .answer {
                                         margin-left: 20px;
                                     }
                                     .correct{
                                         background-color:gray;
                                     }
                                 </style>
                             </head>
                             <body>
                                 <div id="header">
                                     <h2>SỞ GIÁO DỤC VÀ ĐÀO TẠO</h1>
                                     <h2>TRƯỜNG ĐẠI HỌC SÀI GÒN</h2>
                                     <h2>TPHCM</h2>
                                 </div>

                                 <div id="info">
                                     <table>
                                         <tr>
                                             <th>Bài thi môn:</th>
                                             <td>${subjectName}</td>
                                         </tr>
                                         <tr>
                                             <th>Ngày thi:</th>
                                             <td>${dateStart.split(" ")[0]}</td>
                                         </tr>
                                         <tr>
                                             <th>Thời gian làm bài:</th>
                                             <td>${duration}</td>
                                         </tr>
                                         <tr>
                                             <th>Mã đề thi:</th>
                                             <td>${testId}</td>
                                         </tr>
                                         <tr>
                                             <th>Tổng điểm:</th>
                                             <td>${totalPoint}đ</td>
                                         </tr>
                                     </table>
                                 </div>

                                 <div id="questions">`;
                 var url = "http://localhost:9999/Project/studentSearch/resultdetails";
                 const params = {
                     ResultID:resultID,
                 }
                 fetch(url,{
                             method: 'POST',
                             headers: {
                                 'Content-Type': 'application/json'
                             },
                             body: JSON.stringify(params)
                 })
                 .then(response => response.json())
                 .then(data => {
                    data.data.resultDetails.forEach((question,index) =>{
                        body +=`<h4>Câu ${index+1}: ${question.QuestionName}</h4>
                        <div class="question">`
                             question.options.forEach((option,index) => {
                                if(option.BackupOptionID === question.ChooseOption){
                                   body+=`<p class="correct">${index+1}. ${option.Title}.</p>`
                                }else{
                                   body+=`<p>${index}. ${option.Title}.</p>`
                                }
                             })
                        body += `</div>`
                    })
                    body += `</div></body></html>`
                 const blob = new Blob([body], { type: 'application/msword' });
                 // Tạo một đường dẫn URL cho Blob
                 const url = window.URL.createObjectURL(blob);

                 // Tạo một phần tử a để tải tệp Word
                 const a = document.createElement('a');
                 a.href = url;
                 a.download = 'ketquathi.doc';

                 // Thêm phần tử a vào body và nhấp vào nó để tải tệp
                 document.body.appendChild(a);
                 a.click();

                 // Loại bỏ đường dẫn URL sau khi đã sử dụng
                 window.URL.revokeObjectURL(url);

                 })
}


