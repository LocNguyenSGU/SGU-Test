import { deleteQuestion, getSubjectNameByID } from "./util.js"


const subjectSelect = document.querySelector("#subject-select")
const levelSelect = document.querySelector("#level-select")
const searchInput = document.querySelector("#search")

subjectSelect.addEventListener("change", questionFiltering)
levelSelect.addEventListener("change", questionFiltering)
searchInput.addEventListener("change", questionFiltering)

async function questionFiltering() {
    const response =
              await fetch("http://localhost:9999/Project/instructor/question/filter", {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({
                                           size: 1000,
                                           page: 1,
                                           subjectId: subjectSelect.value,
                                           level: levelSelect.value,
                                           search: searchInput.value || "",
                                       }),
              })
    
    const { data, statusCode, message } = await response.json()
    
    if (statusCode !== 200) {
        console.error("Failed to fetch data: ", message)
        return
    }
    
    const { questions, totalPages } = data;
    
    const tbody = document.querySelector(".question-mana__content .tbody")
    tbody.innerHTML = ""
    
    for (const { QuestionID, Title, SubjectID, Level, Point } of questions) {
        const subjectName = await getSubjectNameByID(SubjectID)
        
        const row = document.createElement("div")
        row.classList.add("tr")
        
        row.innerHTML = `
        <div class="td">${QuestionID}</div>
        <div class="td">${Title}</div>
        <div class="td">${Point}</div>
        <div class="td">${subjectName}</div>
        <div class="td">${Level === "EASY" ? "Dễ" : Level === "MEDIUM" ? "Trung bình" : "Khó"}</div>
        <div class="td">
            <a class="show"
               href="${contextPath}/instructor/question/details?id=${QuestionID}">
                <i class='bx bx-show'></i>
            </a>
            <a class="edit"
                href="${contextPath}/instructor/question/edit?id=${QuestionID}">
                <i class='bx bx-edit'></i>
            </a>
            <a class="btn-delete-question" href="#"><i class='bx bx-trash'></i></a></td>
        </div>
        `
        
        tbody.append(row) // add row to table
        
        
        // add event listener to delete button
        const btnDeleteQuestion = row.querySelector('.btn-delete-question');
        btnDeleteQuestion.addEventListener('click', async function (e) {
            e.preventDefault();
            const questionId = this.id;
            
            const isDelete = confirm('Bạn có chắc chắn muốn xóa câu hỏi này?');
            
            if (!isDelete) return;
            
            const isDeleted = await deleteQuestion(questionId);
            if (isDeleted) {
                alert('Xóa câu hỏi thành công!');
                // location.reload();
            }
            else {
                console.log(isDeleted);
                alert('Xóa câu hỏi thất bại!');
            }
        });
    }
}
