import {
    createDataForExcel, exportToExcel,
    getExamNameByID,
    getStudentByID,
    getSubjectByID,
    getTestByID,
} from "./util.js";


const numRow = studentIDs.length;
const exportBtn = document.querySelector("#export-test-results");

const { SubjectID, ExamID, TotalPoint } =  await getTestByID(testID);
const point = parseFloat(TotalPoint).toFixed(1);
const subjectName = (await getSubjectByID(SubjectID)).Name;
const examName = await getExamNameByID(ExamID);

for (let i = 0; i < numRow; i++) {
    const studentID = studentIDs[i]
    
    const student = (await getStudentByID(studentID)).data;
    const studentName = `${student.LastName} ${student.FirstName}`
    
    document.querySelector(`#r${i} .subject-name`).textContent = subjectName;
    document.querySelector(`#r${i} .exam-name`).textContent = examName;
    document.querySelector(`#r${i} .point`).textContent += `/${point}`;
    document.querySelector(`#r${i} .student-name`).textContent = studentName;
}


exportBtn.addEventListener("click", () => {
    exportToExcel(createDataForExcel(),
                  `Kết quả bài thi`)
});
