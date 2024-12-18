const observe = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        // console.log(entry);
        if(entry.isIntersecting) {
            entry.target.classList.add("show");
        } else {
            entry.target.classList.remove("show");
        }
    })
})
const hiddenElements_left = document.querySelectorAll(".hidden_left");
const hiddenElements_right = document.querySelectorAll(".hidden_right");
const hiddenElements_bottom = document.querySelectorAll(".hidden_bottom");


hiddenElements_left.forEach((e) => observe.observe(e))
hiddenElements_right.forEach((e) => observe.observe(e))
hiddenElements_bottom.forEach((e) => observe.observe(e))

let slider = document.querySelector('.slider');
let block = document.querySelectorAll('.block');
let btnLeft = document.querySelector(".btn-left")
let btnRight = document.querySelector(".btn-right")

const length = block.length;
let currentIndex = 0;


function handleChangeBlock() {
    if(currentIndex == length - 1) {
        currentIndex = 0;
        slider.style.transform = `translateX(0px)`;
        document.querySelector('.active').classList.remove("active");
        document.querySelector('.index-item-' +currentIndex).classList.add("active");

    } else {
    currentIndex++
    let width = block[0].offsetWidth;
    slider.style.transform = `translateX(${width * -1 * currentIndex}px)`;
    document.querySelector('.active').classList.remove("active");
    document.querySelector('.index-item-' +currentIndex).classList.add("active");
    }
}
let handleChangeInterval = setInterval(handleChangeBlock, 4000);

btnRight.addEventListener("click", () => {
    clearInterval(handleChangeInterval);
    handleChangeBlock();
    handleChangeInterval = setInterval(handleChangeBlock, 4000);

})
btnLeft.addEventListener("click", () => {
    clearInterval(handleChangeInterval);
    let width = block[0].offsetWidth; // Define width here
    if(currentIndex == 0) {
        currentIndex = length - 1;
        slider.style.transform = `translateX(${width * -1 * currentIndex}px)`;
        document.querySelector('.active').classList.remove("active");
        document.querySelector('.index-item-' +currentIndex).classList.add("active");
    } else {
        currentIndex--;
        slider.style.transform = `translateX(${width * -1 * currentIndex}px)`;
        document.querySelector('.active').classList.remove("active");
        document.querySelector('.index-item-' +currentIndex).classList.add("active");
    }
    handleChangeInterval = setInterval(handleChangeBlock, 4000);
});


