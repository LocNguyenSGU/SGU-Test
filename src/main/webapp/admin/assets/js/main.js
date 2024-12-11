let listItems = document.querySelectorAll(".navigation li");
let main = document.querySelector(".main");

function activeLink() {
  listItems.forEach((item) => {
    item.classList.remove("hovered");
  });
  this.classList.add("hovered");
}

function removeHovered() {
  listItems.forEach((item) => {
    item.classList.remove("hovered");
  });
}


main.addEventListener("mouseover", removeHovered);

listItems.forEach((item) => item.addEventListener("mouseover", activeLink));

// Menu Toggle
let toggle = document.querySelector(".toggle");
let navigation = document.querySelector(".navigation");

toggle.onclick = function () {
  navigation.classList.toggle("active");
  main.classList.toggle("active");
};


// function toggleActive(className) {
//   const btnToggle = document.querySelector(`.${className}`);
//   if (btnToggle) {
//     btnToggle.classList.toggle("active");
//     document.querySelector(".layer-black").classList.toggle("none");
//   } else {
//     console.error(`Could not find element with class name '${className}'`);
//     // You can also provide feedback to the user here, such as displaying an alert
//     // alert(`Could not find element with class name '${className}'`);
//   }
// }

