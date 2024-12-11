import { createOption, updateOption, updateQuestion } from "./util.js";


const numOldOptions = optionIDs.length; // Number of old options
const getElem = id => document.querySelector(id);
const [qTitle, qSubject, qLevel, qPoint, qPointValue, qCorrect, btnUpdate]
          = ["#q-title", "#q-subject", "#q-level", "#q-point", "#q-point-value", "#q-correct",
             "#btn-update"].map(getElem);

// Display the value of qPoint in qPointValue
qPointValue.textContent = qPoint.value;
// Set the value of qCorrect to the index of correctOption + 1
qCorrect.value = optionIDs.indexOf(correctOption) + 1;

// Add event listener for qPoint
qPoint.addEventListener("input", function () {
    qPointValue.textContent = qPoint.value;
});

const addEventForOptions = function () {
    const options = document.querySelectorAll("label[for*='-opt']");
    
    options.forEach((option) => {
        option.addEventListener("click", function () {
            const order = option.getAttribute("for")
                                .replaceAll("q-opt", "");
            
            // all options are unchecked
            options.forEach((option) => {
                option.innerHTML = "<i class='bx bx-radio-circle form__icon bigger'></i>&nbsp;"
            });
            
            // the clicked option is checked
            option.innerHTML = "<i class='bx bx-radio-circle-marked form__icon bigger'></i>&nbsp;"
            
            // update qCorrect value
            qCorrect.value = order;
        });
    });
}
addEventForOptions()

btnUpdate.addEventListener("click", async function (e) {
    e.preventDefault();
    
    // first update all options
    const options = document.querySelectorAll("input[id*='-opt']");
    for (const option of options) {
        if (option.value.trim().length === 0) {
            alert("Phương án không được để trống!");
            option.focus();
            return;
        }
        
        const order = option.id.replaceAll("q-opt", "");
        const title = option.value.trim();
        
        // if order is greater than the number of options, means that's a new option then add a new
        // option
        if (order > optionIDs.length) {
            const optionID = await createOption({
                                                    questionID,
                                                    title,
                                                });
            
            if (!optionID) {
                alert("Thêm câu trả lời thất bại!");
                return;
            }
            
            optionIDs.push(optionID);
            continue;
        }
        
        const isSuccess = await updateOption({
                                                 optionID: optionIDs[order - 1],
                                                 title,
                                             });
        
        if (!isSuccess) {
            alert("Cập nhật câu trả lời thất bại!");
            return;
        }
    }
    
    
    // then update the question
    const isSuccess = await updateQuestion({
                                               questionID,
                                               title: qTitle.value,
                                               subjectID: qSubject.value,
                                               level: qLevel.value,
                                               point: qPoint.value,
                                               correctAnswer: optionIDs[qCorrect.value - 1],
                                           });
    
    if (isSuccess) {
        alert("Cập nhật câu hỏi thành công!");
    }
    else {
        alert("Cập nhật câu hỏi thất bại!");
    }
    
});


// ------------------------------------------------------------------------- //

const btnAddOption = getElem("#btn-add-option");
const btnDeleteOption = getElem("#btn-delete-option");

btnAddOption.addEventListener("click", function (e) {
    e.preventDefault();
    
    const options = document.querySelectorAll("input[id*='-opt']");
    const order = options.length + 1;
    
    const newOption = createOptionDiv(order);
    
    const optionContainer = getElem(".form");
    
    // insert right after the last option
    optionContainer.insertBefore(newOption, this);
    
    addEventForOptions();
});

btnDeleteOption.addEventListener("click", function (e) {
    e.preventDefault();
    
    const options = document.querySelectorAll("input[id*='-opt']");
    if (options.length <= numOldOptions) {
        alert(`Cần ít nhất ${numOldOptions} phương án!`);
        return;
    }
    
    const lastOption = options[options.length - 1];
    lastOption.parentElement.remove();
});

const createOptionDiv = function (numOptions) {
    const group = document.createElement("div");
    group.classList.add("form__group");
    
    const label = document.createElement("label");
    label.htmlFor = `q-opt${numOptions}`;
    label.classList.add("form__label");
    label.innerHTML = "<i class='bx bx-radio-circle form__icon bigger'></i>&nbsp;";
    group.appendChild(label);
    
    const input = document.createElement("input");
    input.type = "text";
    input.id = `q-opt${numOptions}`;
    input.classList.add("form__input");
    input.required = true;
    input.placeholder = `Phương án ${String.fromCharCode(64 + numOptions)}`; // Convert 1 to A, 2
                                                                             // to B, etc.
    group.appendChild(input);
    
    return group;
}
