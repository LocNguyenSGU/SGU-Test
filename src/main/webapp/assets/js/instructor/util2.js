import {
    createBackupOption,
    createBackupQuestion,
    createOption,
    createQuestion,
    getOptionsByQuestionID, updateBackupQuestion,
    updateQuestion,
} from "./util.js";

async function processForm(form) {
    // debug
    console.log("form", form);
    
    const ids = []; // Array to store the IDs
    
    const titleElement = form.querySelector(`[id$='-title']`);
    const title = titleElement.value.trim();
    if (title.length === 0) {
        alert("Không được bỏ trống câu hỏi!");
        titleElement.focus();
        return { isSuccess: false, ids };
    }
    
    const optionElements = Array.from(form.querySelectorAll(`input[id*='-opt']`));
    const options = optionElements.map(option => option.value.trim());
    if (options.some(option => option.length === 0)) {
        alert(`Không được bỏ trống phương án!`);
        optionElements.find(option => option.value.trim().length === 0).focus();
        return { isSuccess: false, ids };
    }
    
    const question = {
        title,
        subject: form.querySelector(`[id$='-subject']`)?.value.trim() ??
            document.querySelector(`[id$='-subject']`)?.value.trim(),
        level: form.querySelector(`[id$='-level']`).value.trim(),
        point: form.querySelector(`[id$='-point']`).value.trim(),
        correct: form.querySelector(`[id$='-correct']`).value.trim(),
    };
    
    
    // debug
    console.log("question", question);
    
    // Step 1: Create the question
    const questionID = await createQuestion(question);
    if (typeof questionID !== "number") {
        alert("Tạo câu hỏi thất bại! (from util2 48)");
        return { isSuccess: false, ids };
    }
    
    ids.push(questionID); // Add the question ID to the array
    
    
    // Step 2: Create the options
    for (let i = 0; i < options.length; i++) {
        const option = { questionID, title: options[i] };

        // debug
        console.log("option", option)


        const optionID = await createOption(option);

        ids.push(optionID); // Add the option ID to the array

        // Update the correct answer
        if (Number(question.correct) === i + 1) {
            const isSuccess = await updateQuestion({
                                                       questionID,
                                                       subjectID: question.subject,
                                                       title: question.title,
                                                       level: question.level,
                                                       point: question.point,
                                                       correctAnswer: optionID,
                                                   });
            if (!isSuccess) {
                alert("Tạo câu hỏi thất bại!");
                return false;
            }
        }
    }
    
    return { isSuccess: true, ids };
}

export async function createManyQuestions(allQuestionForms) {
    for (const form of allQuestionForms) {
        const { isSuccess } = await processForm(form);
        if (!isSuccess) {
            alert("Tạo câu hỏi thất bại!");
            return;
        }
    }
    
    alert("Tạo câu hỏi thành công!");
}

export async function createManyQuestionsForTest(allQuestionForms) {
    const allIDs = [];
    
    for (const form of allQuestionForms) {
        // if form has class "added" then only get the IDs
        if (form.classList.contains("added")) {
            const questionID = Number(form.id.trim());
            const optionIDs = (await getOptionsByQuestionID(questionID)).options
                                                                        .map(option => option.OptionID);
            allIDs.push([questionID, ...optionIDs]);
            continue;
        }
        
        const { isSuccess, ids } = await processForm(form);
        if (isSuccess) {
            allIDs.push(ids);
        } else {
            alert("Tạo câu hỏi thất bại!");
            return;
        }
    }
    
    alert("Tạo câu hỏi thành công!");
    
    return allIDs;
}

export async function createBackup(form, ids) {
    const optionElements = Array.from(form.querySelectorAll(`input[id*='-opt']`));
    const options = optionElements.map(option => option.value.trim());
    const correctOptionNum = form.querySelector(`[id$='-correct']`).value.trim();
    
    // Create a 2-dimensional array
    const optionsArray = options.map((option, index) => {
        const isCorrect = (index + 1) === Number(correctOptionNum);
        return [option, isCorrect ? 1 : 0];
    });
    
    
    // Step 1: Create the question
    const question = {
        title: form.querySelector(`[id$='-title']`).value.trim(),
        point: form.querySelector(`[id$='-point']`).value.trim(),
        questionID: ids[0],
    };
    const backupQuestionID = await createBackupQuestion(question);
    
    // Step 2: Create the options
    for (const [title, isCorrect] of optionsArray) {
        const backupOptionID = await createBackupOption({ title, backupQuestionID });
        
        if (isCorrect) {
            const isSuccess = await updateBackupQuestion({
                                                             backupQuestionID,
                                                             correctAnswer: backupOptionID,
                                                         });
            if (!isSuccess) {
                alert("Tạo câu hỏi phụ thất bại!");
                return;
            }
            
            console.log("Updated the correct answer for the backup question");
        }
    }
    
    return backupQuestionID;
}
