import {
    createQuestionForm,
    getAllExamsNotStarted,
    getAllSubjects, getOptionsByQuestionID,
    getQuestionsBySubjectID,
}                                              from "./util.js";
import { incrementNumQuestions, numQuestions } from "./test-create-helper.js";

const allSubjects = (await getAllSubjects()).data.subjects;
const allExams = (await getAllExamsNotStarted()).data.exams;
let currentQuestionsToBeAdded = [];
const testNumcopInput = document.querySelector("#test-numcop");
const testNumcopLabel = document.querySelector("#test-numcop-value");

testNumcopLabel.textContent = testNumcopInput.value;
testNumcopInput.addEventListener("input", function () {
    testNumcopLabel.textContent = this.value;
});


// export let allChosenIDs = []; // Array to store all added question IDs and its option IDs, one
// question ID followed by its option IDs

// get the elements from the DOM
const testSubjectSelect = document.querySelector("#test-subject");
const testExamSelect = document.querySelector("#test-exam");

const popupContent = document.querySelector(".popup__content");
const popupCloseBtn = document.querySelector(".popup__close");


// add options for the subject select
allSubjects.forEach(subject => {
    const option = document.createElement("option");
    option.value = subject.SubjectID;
    option.text = subject.Name;
    testSubjectSelect.add(option);
});


// add options for the exam select
allExams.forEach(exam => {
    const option = document.createElement("option");
    option.value = exam.ExamID;
    option.text = exam.Name;
    testExamSelect.add(option);
});


// when subject is changed, change the popup content
const renderPopupContent = async () => {
    // first remove all questions if exists
    while (popupContent.children.length > 2) {
        popupContent.removeChild(popupContent.lastChild);
    }
    
    // update the current questions to be added
    currentQuestionsToBeAdded = (await getQuestionsBySubjectID(testSubjectSelect.value)).questions;
    
    // next, add new ones
    currentQuestionsToBeAdded.forEach(({QuestionID, Title}) => {
        const group = createQuestionBox(QuestionID, Title);
        popupContent.appendChild(group);
    })
}


// add options to the question
const addOptionsToQuestion = async function (form, QuestionID, correctAnswer) {
    const options = (await getOptionsByQuestionID(QuestionID)).options;
    
    options.forEach((option, i) => {
        const isMarked = correctAnswer === option.OptionID ? "-marked" : "";
        
        // Mark the correct option number
        if (correctAnswer === option.OptionID)
            form.querySelector("[id*='-correct']").value = i + 1;
        
        const group = document.createElement("div");
        group.innerHTML = `
            <div class="form__group u-margin-bottom-small">
            <label for="q${QuestionID}-opt${i}" class="form__label">
              <i class="bx bx-radio-circle${isMarked} form__icon bigger"></i>&nbsp;
            </label>
            <input type="text" id="q${QuestionID}-opt${i}" class="form__input"
              value="${option.Title}" readonly>
            </div>
        `;
        
        form.querySelector(".form__add-option").before(group);
    });
}

// close the popup
popupCloseBtn.addEventListener("click", function () {
    // allChosenIDs = []; // reset the allChosenIDs array
    
    // step 1: get all the question ids that are checked
    const checkedQuestions = Array.from(popupContent.querySelectorAll("input:checked"));
    const questionIds = checkedQuestions.map(input => input.id.split("-")[1]);
    
    // step 2: remove all questions that are already added
    document.querySelectorAll(".added").forEach(form => form.remove());
    
    // step 3: add the checked questions to the test
    currentQuestionsToBeAdded.filter(question => questionIds.includes(question.QuestionID.toString()))
                             .forEach(question => {
                                 let ids = [question.QuestionID];
                                 
                                 // push the option ids to the ids array
                                 getOptionsByQuestionID(question.QuestionID).then(({options}) => {
                                     ids = ids.push(...options.map(option => option.OptionID));
                                 });
                                 
                                 // push the question id and option ids to the allAddedIDs array
                                 // allChosenIDs.push(ids);
                                 
                                 // increment the number of questions
                                 incrementNumQuestions();
                                 
                                 
                                 // STARTS HERE
                                 const form = createQuestionForm(numQuestions, allSubjects);
                                 form.classList.add("added");
                                 form.id = question.QuestionID;
                                 
                                 form.querySelector(".form__header").append(" (chỉ đọc)");
                                 
                                 
                                 form.querySelector("[id$='-title']").value = question.Title;
                                 form.querySelector("[id$='-title']").readOnly = true;
                                 
                                 form.querySelector("[id$='-point']").value = question.Point;
                                 form.querySelector("[id$='-point']").disabled = true;
                                 form.querySelector("[id$='-point-value']").textContent = question.Point;
                                 
                                 form.querySelector("[id$='-level']").value =  question.Level;
                                 form.querySelector("[id$='-level']").disabled =  true;
                                 
                                 // no need set the subject for the question
                                 // because the subject is already set in the test form
                                 form.querySelectorAll(".form__group.flex-align-items-center")[2].remove();
                                 
                                 // remove last 4 form__group elements
                                 form.querySelectorAll(".form__group").forEach((group, index) => {
                                     if (index >= 3) {
                                         group.remove();
                                     }
                                 });
                                 
                                 // add all options for the question
                                 addOptionsToQuestion(form, question.QuestionID, question.CorrectAnswer);
                                 
                                 // hide all buttons
                                 form.querySelectorAll(".form__add-option")
                                     .forEach(button => button.style.display = "none");
                                 
                                 
                                 document.querySelector("main").appendChild(form);
                             });
})

// create a question box
const createQuestionBox = (questionId, title) => {
    const group = document.createElement("div");
    group.classList.add("form__group", "flex-align-items-center");
    
    const input = document.createElement("input");
    input.type = "checkbox";
    input.id = `question-${questionId}`;
    input.classList.add("form__input");
    
    const label = document.createElement("label");
    label.htmlFor = `question-${questionId}`;
    label.classList.add("form__label");
    label.innerHTML = title;
    
    group.appendChild(label);
    group.appendChild(input);
    
    return group;
}

renderPopupContent()
testSubjectSelect.addEventListener("change", function () {
    document.querySelectorAll(".added").forEach(form => form.remove());
    renderPopupContent();
})
