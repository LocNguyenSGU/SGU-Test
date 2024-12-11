import {
    convertTimeToMinutes,
    getExamNameByID,
    getFormattedDateTime,
    getSubjectNameByID,
} from "./util.js";


const examSelect = document.querySelector("#exam-select");
const searchInput = document.querySelector("#search");

searchInput.addEventListener("change", testFiltering);
examSelect.addEventListener("change", testFiltering);

async function testFiltering() {
    const response = await fetch("http://localhost:9999/Project/instructor/test/filter", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
                                 size: 1000,
                                 page: 1,
                                 examId: examSelect.value,
                                 search: searchInput.value || "",
                             }),
    });
    
    const { statusCode, data: { tests } } = await response.json();
    if (statusCode !== 200) {
        console.error("Failed to fetch data");
        return;
    }
    
    const tbody = document.querySelector(".test-mana__content .tbody");
    tbody.innerHTML = "";
    
    for (const { TestID, SubjectID, ExamID, DateStart, DateEnd, Duration } of tests) {
        const [subjectName, examName, dateStart, dateEnd] = await Promise.all([
                                                                                  getSubjectNameByID(
                                                                                      SubjectID),
                                                                                  getExamNameByID(ExamID),
                                                                                  getFormattedDateTime(
                                                                                      DateStart),
                                                                                  getFormattedDateTime(
                                                                                      DateEnd)
                                                                              ]);
        
        const row = document.createElement("div");
        row.classList.add("tr");
        row.innerHTML = `
        <div class="td">${TestID}</div>
        <div class="td">${subjectName}</div>
        <div class="td">${examName}</div>
        <div class="td">${dateStart}</div>
        <div class="td">${dateEnd}</div>
        <div class="td">${convertTimeToMinutes(Duration)}</div>
        <div class="td">
            <a id="${TestID}" class="show" href="#"><i class="bx bx-show"></i></a>
        </div>
        `;
        
        tbody.append(row);
    }
}
