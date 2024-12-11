import { deleteQuestion } from "./util.js";


const allBtnDeleteQuestions = document.querySelectorAll('.btn-delete-question');
const btnDeleteAllQuestions = document.getElementById('btn-delete-all-questions');

allBtnDeleteQuestions.forEach(btnDeleteQuestion => {
    btnDeleteQuestion.addEventListener('click', async function (e) {
        e.preventDefault();
        const questionId = this.id;
        
        const isDelete = confirm('Bạn có chắc chắn muốn xóa câu hỏi này?');
        
        if (!isDelete) return;
        
        const isDeleted = await deleteQuestion(questionId);
        if (isDeleted === true) {
            alert('Xóa câu hỏi thành công!');
            location.reload();
        }
        else if (isDeleted === false) {
            alert('Đã có lỗi ở đâu đó!');
        }
        else {
            alert('Không thể xóa câu hỏi này vì có ràng buộc khóa ngoại!');
        }
    });
});

btnDeleteAllQuestions.addEventListener('click', async function (e) {
    e.preventDefault();
    
    const isDelete = confirm('Bạn có chắc chắn muốn xóa tất cả câu hỏi?');
    
    if (!isDelete) return;
    
    
    for (const btnDeleteQuestion of allBtnDeleteQuestions) {
        const questionId = btnDeleteQuestion.id;
        
        if (!(await deleteQuestion(questionId)))
            return alert('Xóa tất cả câu hỏi thất bại!');
        
        alert('Xóa tất cả câu hỏi thành công!');
    }
});
