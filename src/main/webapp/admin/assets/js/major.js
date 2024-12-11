 function toggleActive(className) {
          const btnToggle = document.querySelector(`.${className}`);
          if (btnToggle) {
            btnToggle.classList.toggle("active");
          } else {
            console.error(`Could not find element with class name '${className}'`);
            // You can also provide feedback to the user here, such as displaying an alert
            // alert(`Could not find element with class name '${className}'`);
          }
 }