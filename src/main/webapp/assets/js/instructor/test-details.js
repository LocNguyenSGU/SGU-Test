import { getAllExamsNotStarted, getAllSubjects } from "./util.js";


const allSubjects = (await getAllSubjects()).data.subjects;
const allExams = (await getAllExamsNotStarted()).data.exams;

const examSelect = document.querySelector("#test-exam");
const subjectSelect = document.querySelector("#test-subject");

// add <option> elements to the exam select
for (const exam of allExams) {
    const option = document.createElement("option");
    option.value = exam.ExamID;
    option.innerHTML = exam.Name;
    
    if (exam.ExamID === examID) {
        option.selected = true;
    }
    
    examSelect.append(option);
}

// add <option> elements to the subject select
for (const subject of allSubjects) {
    const option = document.createElement("option");
    option.value = subject.SubjectID;
    option.innerHTML = subject.Name;
    
    if (subject.SubjectID === subjectID) {
        option.selected = true;
    }
    
    subjectSelect.append(option);
}
