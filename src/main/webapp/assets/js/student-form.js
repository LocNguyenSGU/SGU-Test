function updateResultStatus(ResultID){
    var url = "http://localhost:9999/Project/studentSearch/resultStatus"
       const params = {
            ResultID: ResultID,
            Status:"COMPLETED"
       }
       fetch(url,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
       })
    window.location.href = "http://localhost:9999/Project/student/tests"
}
            // Tính toán thời gian còn lại so với thời gian kết thúc cố định
            function calculateTimeRemaining(endTime) {
                const now = new Date().getTime();
                const timeRemaining = endTime - now;

                // Tính toán giờ, phút và giây còn lại
                const days = Math.floor(timeRemaining / (1000 * 60 * 60 * 24));
                const hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

                return { days, hours, minutes, seconds, timeRemaining };
            }

            // Cập nhật countdown mỗi giây
            function updateCountdown(endTime) {
                const { days, hours, minutes, seconds, timeRemaining } = calculateTimeRemaining(endTime);
                console.log(hours, " " , minutes);

                // Hiển thị kết quả
                document.getElementById('TestTime').textContent =
                    `Thời gian còn lại: ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;

                // Nếu thời gian đã hết, dừng countdown
                if (timeRemaining <= 0) {
                    // clearInterval(countdownInterval);
                    // Hiển thị thông báo
                    alert("Bài thi đã hoàn tất!");

                    // Đợi 3 giây trước khi thực hiện thao tác tiếp theo
                    setTimeout(function() {
                        updateResultStatus(data.data.ResultID)

                    }, 2000); // 3000 milliseconds = 3 giây
                }
            }
function updateResultDetails(ResultID,QuestionID,ChooseOption){
    var url = "http://localhost:9999/Project/studentSearch/updateResult"
       const params = {
            ResultID: ResultID,
            QuestionID:QuestionID,
            ChooseOption:ChooseOption
       }
       fetch(url,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(params)
       })
}
function getAllQuestionByTestIDAndStudentID(testID){
   var url = "http://localhost:9999/Project/studentSearch/resultTest"
   const params = {
        TestID: testID,
        StudentID: (JSON.parse(localStorage.getItem("info"))).StudentID
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
   console.log(data);
        document.getElementById("TestTitle").textContent = `Bài thi môn: ${data.data.SubjectName}`;
        document.getElementById("TestDescription").textContent = `Lưu ý: ${data.data.Description}`;
        document.getElementById("TestPoint").textContent = `Điểm thi: ${data.data.TestPoint} điểm`;
            // const endTime = new Date(data.data.DateEnd.replace(' ','T')).getTime();
            const endTime= new Date(data.data.DateEnd.replace(' ', 'T').replace(/(\d{2})-(\d{2})-(\d{4})/, '$3-$2-$1'));
            console.log("eT", endTime)
            // Khởi động countdown lần đầu
            updateCountdown(endTime);
            // Cập nhật countdown mỗi giây
            const countdownInterval = setInterval(()=>updateCountdown(endTime), 60000);
            data.data.resultDetails.forEach((question,index) => {
             document.getElementById("containerQuestions").innerHTML +=`<div class="containerFormInsideQuestion">
                <div class="question">
                    <p style="margin-bottom:10px;font-size:18px;font-weight:700;">Câu ${index + 1} : ${question.QuestionName}</p>
                    <div>
                        ${question.options.map(item => {
                            if(item.BackupOptionID === question.ChooseOption){
                               return `
                                   <div class="divOption">
                                       <input class="option" type="radio" name="option${question.QuestionID}" onchange="updateResultDetails(${data.data.ResultID},${question.QuestionID},${item.BackupOptionID})" checked/>
                                       <p class="text">${item.Title}</p>
                                   </div>
                               `;
                            }else{
                               return `
                                     <div class="divOption">
                                         <input class="option" type="radio" name="option${question.QuestionID}" onchange="updateResultDetails(${data.data.ResultID},${question.QuestionID},${item.BackupOptionID})"/>
                                           <p class="text">${item.Title}</p>
                                     </div>
                                    `;
                            }
                        }).join('')}
                    </div>
                </div>
             </div>`
        })
        document.getElementById("containerQuestions").innerHTML += `
            <div style="display:flex;justify-content:end;">
                <button onclick="updateResultStatus(${data.data.ResultID})" style="border-radius:10px;background-color:#2a2185;color:white;padding:5px;font-size:18px;font-weight:700;">Nộp bài</button>
            </div>
        `
   })
}
window.addEventListener('beforeunload', function (e) {
            // Tin nhắn cảnh báo (một số trình duyệt có thể bỏ qua hoặc thay đổi tin nhắn này)
            var confirmationMessage = "Bạn có chắc chắn muốn rời khỏi trang này không? Kết quả thi của bạn sẽ được lưu lại.";

            // Đặt thuộc tính `returnValue` của sự kiện để hiển thị cảnh báo
            (e || window.event).returnValue = confirmationMessage;

            // Trả về tin nhắn cảnh báo để hiển thị
            return confirmationMessage;
});
document.addEventListener("DOMContentLoaded", function () {
            const urlParams = new URLSearchParams(window.location.search);
            // Lấy giá trị của tham số 'testID'
            const testID = urlParams.get('testID');
            getAllQuestionByTestIDAndStudentID(testID)

});
