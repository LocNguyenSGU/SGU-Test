import {
    createQuestionForm,
    getAllSubjects,
    
} from "./util.js";
import { createManyQuestions } from "./util2.js";


let numQuestions = 0;
const addQuestionBtn = document.querySelector("#add-question");
const createBtn = document.querySelector("#creating");

const allSubjects = (await getAllSubjects()).data.subjects;

document.querySelector("main").append(createQuestionForm(++numQuestions, allSubjects));


addQuestionBtn.addEventListener("click", event => {
    event.preventDefault();
    const form = createQuestionForm(++numQuestions, allSubjects);
    document.querySelector("main").append(form);
    form.scrollIntoView({ behavior: "smooth" });
});


createBtn.addEventListener("click", async event => {
    event.preventDefault();
    
    const allForms = document.querySelectorAll('form');
    await createManyQuestions(allForms);
});
