const navigation = document.querySelector(".navigation");
const navigationToggle = document.querySelector(".navigation__btn-toggle");
const main = document.querySelector(".main");

// Check if there's a saved state in localStorage and apply it
if (localStorage.getItem('sidebarState') === 'collapsed') {
    main.classList.add("main__toggle");
    navigation.classList.add("navigation__toggle");
}

navigationToggle.addEventListener("click", function () {
    main.classList.toggle("main__toggle");
    navigation.classList.toggle("navigation__toggle");
    
    // Save the state in localStorage
    if (main.classList.contains("main__toggle")) {
        localStorage.setItem('sidebarState', 'collapsed');
    }
    else {
        localStorage.removeItem('sidebarState');
    }
});
