import { getFormattedDate, getMajorNameByID } from "./util.js";


const majorSelect = document.querySelector("#major-select");
const searchInput = document.querySelector("#search");

searchInput.addEventListener("change", studentFiltering);
majorSelect.addEventListener("change", studentFiltering);

async function studentFiltering() {
    const response = await
        fetch("http://localhost:9999/Project/instructor/student/filter", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                                     size: 1000,
                                     page: 1,
                                     majorId: majorSelect.value,
                                     search: searchInput.value || "",
                                 }),
        });
    
    const { data, statusCode, message } = await response.json();
    
    if (statusCode !== 200) {
        console.error("Failed to fetch data: ", message);
        return;
    }
    
    const { students } = data;
    
    const tbody = document.querySelector(".student-mana__content .tbody");
    tbody.innerHTML = "";
    
    for (const { StudentID, FirstName, LastName, Email, Phone, MajorID, Gender, DateOfBirth }
        of students)
    {
        const [majorName, formattedDate] =
                  await Promise.all([
                                        getMajorNameByID(MajorID),
                                        getFormattedDate(DateOfBirth),
                                    ]);
        
        const row = document.createElement("div");
        row.classList.add("tr");
        row.innerHTML = `
        <div class="td">${StudentID}</div>
        <div class="td">${LastName} ${FirstName}</div>
        <div class="td">${Phone}</div>
        <div class="td">${Email}</div>
        <div class="td">${formattedDate}</div>
        <div class="td">${Gender ? "Nam" : "Nữ"}</div>
        <div class="td">${majorName}</div>
        <div class="td">
            <a class="show"
                href="${contextPath}/instructor/student/results?id=${StudentID}">
            <i class="bx bx-show"></i>
            </a>
        </div>
        `;
        
        tbody.append(row);
    }
}
