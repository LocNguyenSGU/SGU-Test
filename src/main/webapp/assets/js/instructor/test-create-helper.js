import {
    convertMinutesToTime,
    createQuestionForm, createTest, createTestDetails,
    getAllSubjects,
    
} from "./util.js";
import { createBackup, createManyQuestionsForTest } from "./util2.js";


export let numQuestions = 0;
export function incrementNumQuestions() {
    numQuestions++;
}

const allSubjects = (await getAllSubjects()).data.subjects;
const createTestBtn = document.querySelector("#create-test");
const addQuestionBtn = document.querySelector("#add-question");
const startingDatetimeInput = document.querySelector("#test-starting-datetime");
const endingDatetimeInput = document.querySelector("#test-ending-datetime");

function setupInput(enableBtn, enableLabel, input, defaultValue) {
    input.value = defaultValue;
    input.readOnly = true;
    
    enableBtn.addEventListener("change", function () {
        if (this.checked) {
            enableLabel.innerHTML = "<i class='bx bx-checkbox-checked form__icon bigger'></i>";
            input.readOnly = false;
        }
        else {
            enableLabel.innerHTML = "<i class='bx bx-checkbox form__icon bigger'></i>";
            input.readOnly = true;
        }
    });
}

function adjustTimeZone(date) {
    // Add 7 hours to the date
    date.setHours(date.getHours() + 7);
    
    return date;
}

startingDatetimeInput.addEventListener("change", function () {
    const startingDatetime = new Date(document.querySelector("#test-starting-datetime").value);
    const endingDatetime = new Date(document.querySelector("#test-ending-datetime").value);
    const duration = Number(document.querySelector("#test-duration").value) * 60 * 1000; // convert duration from minutes to milliseconds
    
    if (startingDatetime.getTime() + duration >= endingDatetime.getTime()) {
        alert("Thời gian bắt đầu cộng thêm thời lượng phải nhỏ hơn thời gian kết thúc!");
        this.value = defaultStartingDatetime.toISOString().slice(0, 16);
    }
});

endingDatetimeInput.addEventListener("change", function () {
    const startingDatetime = new Date(document.querySelector("#test-starting-datetime").value);
    const endingDatetime = new Date(document.querySelector("#test-ending-datetime").value);
    const duration = Number(document.querySelector("#test-duration").value) * 60 * 1000; // convert duration from minutes to milliseconds
    
    if (startingDatetime.getTime() + duration >= endingDatetime.getTime()) {
        alert("Thời gian bắt đầu cộng thêm thời lượng phải nhỏ hơn thời gian kết thúc!");
        this.value = defaultEndingDatetime.toISOString().slice(0, 16);
    }
});

const defaultStartingDatetime = adjustTimeZone(new Date());
defaultStartingDatetime.setDate(defaultStartingDatetime.getDate() + 1);

const defaultEndingDatetime = adjustTimeZone(new Date());
defaultEndingDatetime.setDate(defaultEndingDatetime.getDate() + 4);

setupInput(
    document.querySelector("#enable-duration"),
    document.querySelector("#enable-duration-label"),
    document.querySelector("#test-duration"),
    "45",
);

setupInput(
    document.querySelector("#enable-starting-datetime"),
    document.querySelector("#enable-starting-datetime-label"),
    document.querySelector("#test-starting-datetime"),
    defaultStartingDatetime.toISOString().slice(0, 16),
);

setupInput(
    document.querySelector("#enable-ending-datetime"),
    document.querySelector("#enable-ending-datetime-label"),
    document.querySelector("#test-ending-datetime"),
    defaultEndingDatetime.toISOString().slice(0, 16),
);


// add question button's event
addQuestionBtn.addEventListener("click", function (event) {
    event.preventDefault();
    const form = createQuestionForm(++numQuestions, allSubjects);
    
    // no need set the subject for the question
    // because the subject is already set in the test form
    form.querySelectorAll(".form__group.flex-align-items-center")[2].remove();
    
    // add form to the end of the list
    document.querySelector("main").append(form);
    
    // target to the form
    form.scrollIntoView({ behavior: "smooth" });
});


// when click the create test button
createTestBtn.addEventListener("click", async function (event) {
    event.preventDefault();
    
    // step 0: check if no .form--question founded
    const allFQ = document.querySelectorAll(".form--question")
    if (allFQ.length === 0) {
        alert("Không tìm thấy câu hỏi nào. Xin vui lòng thử lại!");
        return;
    }
    
    const backupQuestionIDs = [];
    
    // step 1: create questions and options, store the IDs
    const questionsDOM = document.querySelectorAll(".form--question");
    const allIDs = await createManyQuestionsForTest(questionsDOM);
    console.log(allIDs);
    
    // step 2: create backup questions
    for (const [i, form] of questionsDOM.entries()) {
        backupQuestionIDs.push(await createBackup(form, allIDs[i]));
    }
    console.log(backupQuestionIDs);
    
    // step 3: get all the test information and create the test
    const testDes = document.querySelector("#test-des").value;
    const testSubject = document.querySelector("#test-subject").value;
    const testExam = document.querySelector("#test-exam").value;
    const testDuration = document.querySelector("#test-duration").value;
    const testStartingDatetime = document.querySelector("#test-starting-datetime").value;
    const testEndingDatetime = document.querySelector("#test-ending-datetime").value;
    const totalPoint = Array.from(document.querySelectorAll("[id$='point']")).reduce(
        (acc, input) => acc + Number(input.value.trim()),
        0,
    );
    const numberOfQuestions = allIDs.length;
    
    // create the test
    const test = {
        testDes,
        testSubject,
        testExam,
        // convert duration from format minutes to hh:mm:ss
        testDuration: convertMinutesToTime(testDuration),
        // convert datetime to ISO string
        testStartingDatetime: new Date(testStartingDatetime).toISOString(),
        testEndingDatetime: new Date(testEndingDatetime).toISOString(),
        totalPoint,
        numberOfQuestions,
    }
    const testID = await createTest(test);
    
    console.log(testID);
    
    // step 4: create test-backup-question relationships
    for (const backupQuestionID of backupQuestionIDs) {
        const isSuccess = await createTestDetails({
                                                      testID,
                                                      backupQuestionID,
                                                  });
        
        if (!isSuccess) {
            alert("Tạo bài thi thất bại!");
            return;
        }
    }
    
    console.log("Created the test details");
    console.log("Created the test successfully!")
});
