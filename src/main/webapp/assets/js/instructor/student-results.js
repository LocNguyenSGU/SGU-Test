import {
    createDataForExcel,
    exportToExcel,
    getExamNameByID,
    getStudentByID,
    getSubjectByID,
    getTestByID,
} from "./util.js";

const numRow = testIDs.length;
const student = (await getStudentByID(studentID)).data;
const exportBtn = document.querySelector("#export-student-results");

document.querySelector(".add-student-name").textContent += ` ${student.LastName} ${student.FirstName}`
document.querySelectorAll(".student-name")
        .forEach(e => e.textContent = `${student.LastName} ${student.FirstName}`);

for (let i = 0; i < numRow; i++) {
    const testID = testIDs[i];
    const { SubjectID, ExamID, TotalPoint } =  await getTestByID(testID);
    const point = parseFloat(TotalPoint).toFixed(1);
    const subjectName = (await getSubjectByID(SubjectID)).Name;
    const examName = await getExamNameByID(ExamID);
    
    document.querySelector(`#r${i} .subject-name`).textContent = subjectName;
    document.querySelector(`#r${i} .exam-name`).textContent = examName;
    document.querySelector(`#r${i} .point`).textContent += `/${point}`;
}

exportBtn.addEventListener("click", () => {
    exportToExcel(createDataForExcel(),
                  `Kết quả của ${student.LastName} ${student.FirstName}`)
});
