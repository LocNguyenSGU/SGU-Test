// label: EXCEL EXPORT
export const createDataForExcel = () => {
    const data = [];
    
    const firstRow = [];
    document.querySelectorAll(".th").forEach(th => {
        firstRow.push(th.textContent);
    });
    data.push(firstRow);
    
    document.querySelectorAll(".tr").forEach(tr => {
        const row = [];
        tr.querySelectorAll(".td").forEach(td => {
            row.push(td.textContent);
        });
        data.push(row);
    });
    
    return data;
}

export const exportToExcel = (data, fileName) => {
    // Create a new workbook
    const wb = XLSX.utils.book_new();
    wb.Props = {
        Title: fileName,
        Subject: fileName,
    };
    
    // Create a new worksheet from the data
    const ws = XLSX.utils.aoa_to_sheet(data);
    
    // Add the worksheet to the workbook
    XLSX.utils.book_append_sheet(wb, ws, fileName);
    
    // Write the workbook to a file and trigger a download
    // The write function takes a workbook and an options object
    // The download option triggers a download when set to true
    // XLSX.write(wb, {type: 'binary', bookType: 'xlsx', download: true});
    XLSX.writeFile(wb, `${fileName}.xlsx`);
}


// label: GET methods

export const convertTimeToMinutes = (time) => {
    const [hour, minute] = time.split(":");
    const totalMinutes = parseInt(hour) * 60 + parseInt(minute);
    return totalMinutes + " phút";
}

export const convertMinutesToTime = (minutes) => {
    const hour = Math.floor(minutes / 60);
    const minute = minutes % 60;
    return `${hour < 10 ? '0' + hour : hour}:${minute < 10 ? '0' + minute : minute}:00`;
}

export const getResultsByStudentID = (studentID) => {
    return fetch(`http://localhost:9999/Project/api/get-results-by-student-id?studentID=${studentID}`)
        .then(response => response.json());
}

export const getSubjectNameByID = (subjectID) => {
    return fetch(`http://localhost:9999/Project/api/get-subject-name?subjectID=${subjectID}`)
        .then(response => response.text());
}

export const getExamNameByID = (examID) => {
    return fetch(`http://localhost:9999/Project/api/get-exam-name?examID=${examID}`)
        .then(response => response.text());
}

export const getMajorNameByID = (majorID) => {
    return fetch(`http://localhost:9999/Project/api/get-major-name?majorID=${majorID}`)
        .then(response => response.text());
    
}

export const getAllSubjects = () => {
    return fetch("http://localhost:9999/Project/api/get-all-subjects")
        .then(response => response.json());
}

export const getSubjectByID = (subjectID) => {
    return fetch(`http://localhost:9999/Project/api/get-subject-by-id?subjectID=${subjectID}`)
        .then(response => response.json());
}

export const getTestByID = (testID) => {
    return fetch(`http://localhost:9999/Project/api/get-test-by-id?testID=${testID}`)
        .then(response => response.json());
}

export const getStudentByID = (studentID) => {
    return fetch(`http://localhost:9999/Project/api/get-student-by-id?studentID=${studentID}`)
        .then(response => response.json());
}

export const getAllExamsNotStarted = () => {
    return fetch("http://localhost:9999/Project/api/get-all-exams-not-started")
        .then(response => response.json());
}

export const getQuestionsBySubjectID = (subjectID) => {
    return fetch(
        `http://localhost:9999/Project/api/get-questions-by-subject-id?subjectID=${subjectID}`)
        .then(response => response.json());
}

export const getOptionsByQuestionID = (questionID) => {
    return fetch(
        `http://localhost:9999/Project/api/get-options-by-question-id?questionID=${questionID}`)
        .then(response => response.json());
}

export const getFormattedDateTime = (dateTimeStr) => {
    return fetch(
        `http://localhost:9999/Project/api/get-formatted-datetime?dateTime=${dateTimeStr}`)
        .then(response => response.text());
}

export const getFormattedDate = (dateStr) => {
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // JavaScript months are 0-based
    const day = date.getDate();
    const formattedDateStr = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day
                                                                                     : day}`;
    return fetch(`http://localhost:9999/Project/api/get-formatted-date?date=${formattedDateStr}`)
        .then(response => response.text());
}


// label: POST methods

export const createTestDetails = async (testDetails) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/test/create-details`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(testDetails),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        return statusCode === 200 ? data : message;
    } else {
        return "Cannot create test details";
    }
    
}

export const createTest = async (test) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/test/create`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(test),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create test"; // return a default error message
    
}

export const createBackupQuestion = async (question) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/question/create-backup`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(question),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create test"; // return a default error message
    
}

export const updateBackupQuestion = async (question) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/question/update-backup`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(question),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create test"; // return a default error message
}

export const createQuestion = async (question) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/question/create`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(
            {
                title: question.title,
                subject: question.subject,
                level: question.level,
                point: question.point,
            },
        ),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create question"; // return a default error message
}

export const createBackupOption = async (option) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/option/create-backup`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(option),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create test"; // return a default error message
}

export const createOption = async ({ title, questionID }) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/option/create`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(
            {
                title,
                questionID,
            },
        ),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create question"; // return a default error message
}

export const updateQuestion = async ({
                                         questionID,
                                         subjectID,
                                         title,
                                         level,
                                         point,
                                         correctAnswer,
                                     }) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/question/update`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(
            {
                questionID,
                subjectID,
                title,
                level,
                point,
                correctAnswer,
            },
        ),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot create question"; // return a default error message
}

export const deleteQuestion = async (questionID) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/question/delete`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(
            {
                questionID,
            },
        ),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        console.log(data, statusCode, message)
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot delete question"; // return a default error message
}

export const updateOption = async ({ optionID, title }) => {
    const response = await fetch(`http://localhost:9999/Project/instructor/option/update`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
        },
        body: JSON.stringify(
            {
                title,
                optionID,
            },
        ),
    });
    
    if (response.ok) {
        const { data, statusCode, message } = await response.json();
        if (statusCode === 200) return data;
        else return message; // return the error message
    }
    else
        return "Cannot update option"; // return a default error message
}


// label: Helper functions

export const createQuestionForm = (numQuestions, allSubjects) => {
    // create form
    const form = document.createElement("form");
    form.classList.add("form", "form--question");
    form.id = `q${numQuestions}`;
    form.addEventListener("submit", function (event) {
        event.preventDefault();
    });
    
    // create close button
    const closeBtn = document.createElement("button");
    closeBtn.classList.add("form__close");
    closeBtn.id = `q${numQuestions}-close`;
    closeBtn.innerHTML = "&times;";
    form.appendChild(closeBtn);
    closeBtn.addEventListener("click", function () {
        form.remove();
    });
    
    // create collapse button
    const collapseBtn = document.createElement("button");
    collapseBtn.classList.add("form__collapse");
    collapseBtn.id = `q${numQuestions}-collapse`;
    collapseBtn.innerHTML = "<i class='bx bx-chevron-up'></i>";
    form.appendChild(collapseBtn);
    collapseBtn.addEventListener("click", function () {
        this.querySelector("i").classList.toggle("bx-chevron-up");
        this.querySelector("i").classList.toggle("bx-chevron-down");
        
        form.classList.toggle("form--collapsed");
        
        // hide all groups
        const groups = form.querySelectorAll(".form__group");
        groups.forEach(group => {
            group.style.visibility = group.style.visibility === "hidden" ? "visible" : "hidden";
        });
        
        // hide all buttons
        const buttons = form.querySelectorAll("button.form__add-option");
        buttons.forEach(button => {
            button.style.visibility = button.style.visibility === "hidden" ? "visible" : "hidden";
        });
    })
    
    // create header
    const header = document.createElement("h3");
    header.classList.add("form__header");
    header.innerHTML = `Câu hỏi ${numQuestions}`;
    form.appendChild(header);
    
    // create question title
    const group1 = document.createElement("div");
    group1.classList.add("form__group");
    form.appendChild(group1);
    
    const label1 = document.createElement("label");
    label1.htmlFor = `q${numQuestions}-title`;
    label1.classList.add("form__label");
    label1.innerHTML = "<i class='bx bx-question-mark form__icon bigger'></i>&nbsp;";
    group1.appendChild(label1);
    
    const textarea1 = document.createElement("textarea");
    // textarea1.type = "text";
    textarea1.id = `q${numQuestions}-title`;
    textarea1.classList.add("form__textarea");
    textarea1.rows = 3;
    textarea1.required = true;
    textarea1.placeholder = "Nội dung câu hỏi";
    group1.appendChild(textarea1);
    
    
    // create point
    const group6 = document.createElement("div");
    group6.classList.add("form__group", "flex-align-items-center");
    form.appendChild(group6);
    
    const label6 = document.createElement("label");
    label6.htmlFor = `q${numQuestions}-point`;
    label6.classList.add("form__label");
    label6.innerHTML
        = "<i class='bx bx-edit-alt form__icon'></i>"
        + "<span class='des-span'>Điểm số (<span id='q" + numQuestions + "-point-value' "
        + "class='u-font-weight-500'>1.0</span>):</span>";
    group6.appendChild(label6);
    
    const input6 = document.createElement("input");
    input6.type = "range";
    input6.id = `q${numQuestions}-point`;
    input6.classList.add("form__input");
    input6.min = ".5";
    input6.max = "10.0";
    input6.step = ".5";
    input6.value = "1.0"; // default point is 1.0
    input6.required = true;
    input6.addEventListener("input", function () {
        document.querySelector(`#q${numQuestions}-point-value`).textContent = this.value;
    });
    group6.appendChild(input6);
    
    
    // create level of difficulty
    const group8 = document.createElement("div");
    group8.classList.add("form__group", "flex-align-items-center");
    form.appendChild(group8);
    
    const label8 = document.createElement("label");
    label8.htmlFor = `q${numQuestions}-level`;
    label8.classList.add("form__label");
    label8.innerHTML = "<i class='bx bx-star form__icon'></i>"
        + "<span class='des-span'>Độ khó:</span>";
    group8.appendChild(label8);
    
    const select8 = document.createElement("select");
    select8.id = `q${numQuestions}-level`;
    select8.classList.add("form__select");
    select8.required = true;
    select8.innerHTML = `
        <option value="EASY">Dễ</option>
        <option value="MEDIUM">Trung bình</option>
        <option value="HARD">Khó</option>
    `;
    group8.appendChild(select8);
    
    // create subject
    const group9 = document.createElement("div");
    group9.classList.add("form__group", "flex-align-items-center");
    form.appendChild(group9);
    
    const label9 = document.createElement("label");
    label9.htmlFor = `q${numQuestions}-subject`;
    label9.classList.add("form__label");
    label9.innerHTML = "<i class='bx bx-book-open form__icon'></i>"
        + "<span class='des-span'>Môn học:</span>";
    group9.appendChild(label9);
    
    const select9 = document.createElement("select");
    select9.id = `q${numQuestions}-subject`;
    select9.classList.add("form__select");
    select9.required = true;
    allSubjects.forEach(subject => {
        const option = document.createElement("option");
        option.value = subject.SubjectID;
        option.textContent = subject.Name;
        select9.appendChild(option);
    });
    group9.appendChild(select9);
    
    
    // create correct option
    const group7 = document.createElement("div");
    group7.style.display = "none";
    form.appendChild(group7);
    
    const input7 = document.createElement("input");
    input7.type = "text";
    input7.value = "1"; // default correct option is 1
    input7.id = `q${numQuestions}-correct`;
    group7.appendChild(input7);
    
    
    // create options
    let numOptions = 0;
    const allOptionLabels = []
    for (let i = 1; i <= 4; ++i) {
        const optionDiv = createOptionDiv(++numOptions, numQuestions, input7);
        allOptionLabels.push(optionDiv.querySelector('label'));
        form.appendChild(optionDiv);
    }
    
    addEventListenersToLabels(allOptionLabels, input7);
    
    // default correct option is 1
    allOptionLabels[0].innerHTML
        = "<i class='bx bx-radio-circle-marked form__icon bigger'></i>&nbsp;";
    input7.value = "1";
    
    // create add option button
    const addOptionBtn = document.createElement("button");
    addOptionBtn.classList.add("form__add-option", "u-margin-right-small");
    addOptionBtn.id = `q${numQuestions}-add-option`;
    addOptionBtn.innerHTML = "<i class='bx bx-plus'></i>&nbsp;Thêm phương án";
    form.appendChild(addOptionBtn);
    addOptionBtn.addEventListener("click", function (event) {
        event.preventDefault();
        const optionDiv = createOptionDiv(++numOptions, numQuestions, input7);
        const newLabel = optionDiv.querySelector('label');
        allOptionLabels.push(newLabel);
        form.insertBefore(optionDiv, this);
        addEventListenersToLabels(allOptionLabels, input7);
    });
    
    // create subtract option button
    const subtractOptionBtn = document.createElement("button");
    subtractOptionBtn.classList.add("form__add-option");
    subtractOptionBtn.id = `q${numQuestions}-subtract-option`;
    subtractOptionBtn.innerHTML = "<i class='bx bx-minus'></i>&nbsp;Bớt phương án";
    form.appendChild(subtractOptionBtn);
    subtractOptionBtn.addEventListener("click", function (event) {
        event.preventDefault();
        if (numOptions > 2) {
            // select the last option div and remove it
            const lastOptionDiv = form.querySelector('.form__group:last-of-type');
            if (lastOptionDiv) {
                form.removeChild(lastOptionDiv);
            }
            
            allOptionLabels.pop();
            --numOptions;
        }
        else alert("Phải có ít nhất 2 phương án!");
    });
    
    return form;
};


const createOptionDiv = function (numOptions, numQuestions) {
    const group = document.createElement("div");
    group.classList.add("form__group");
    
    const label = document.createElement("label");
    label.htmlFor = `q${numQuestions}-opt${numOptions}`;
    label.classList.add("form__label");
    label.innerHTML = "<i class='bx bx-radio-circle form__icon bigger'></i>&nbsp;";
    group.appendChild(label);
    
    const input = document.createElement("input");
    input.type = "text";
    input.id = `q${numQuestions}-opt${numOptions}`;
    input.classList.add("form__input");
    input.required = true;
    input.placeholder = `Phương án ${String.fromCharCode(64 + numOptions)}`; // Convert 1 to A, 2
                                                                             // to B, etc.
    group.appendChild(input);
    
    return group;
}

const addEventListenersToLabels = function (allOptionLabels, input7) {
    for (let [i, label] of allOptionLabels.entries()) {
        label.addEventListener("click", function (e) {
            e.preventDefault();
            input7.value = (i + 1).toString();
            allOptionLabels.forEach(label => {
                label.innerHTML = "<i class='bx bx-radio-circle form__icon bigger'></i>&nbsp;"
            })
            label.innerHTML = "<i class='bx bx-radio-circle-marked form__icon bigger'></i>&nbsp;";
        });
    }
}
