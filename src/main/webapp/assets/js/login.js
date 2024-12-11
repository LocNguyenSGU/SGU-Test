const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    document.getElementById("emailErrorStudent").style.display = "none";
    document.getElementById("passwordErrorStudent").style.display = "none";
    document.getElementById("emailErrorEmployee").style.display = "none";
    document.getElementById("passwordErrorEmployee").style.display = "none";
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    document.getElementById("emailErrorStudent").style.display = "none";
    document.getElementById("passwordErrorStudent").style.display = "none";
    document.getElementById("emailErrorEmployee").style.display = "none";
    document.getElementById("passwordErrorEmployee").style.display = "none";
    container.classList.remove("active");
});
document.getElementById('Student').addEventListener('submit', function(event) {
            event.preventDefault();
            var email = document.getElementById('emailStudent').value;
            var password = document.getElementById('passwordStudent').value;

            fetch('http://localhost:9999/Project/loginStudent', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    'email': email,
                    'password': password
                })
            })
            .then(response => response.json())
            .then(data => {
                document.getElementById("emailErrorStudent").innerHTML = "";
                document.getElementById("passwordErrorStudent").innerHTML = "";
                if (data.statusCode === 400) {
                    if(data.data === 0){
                    document.getElementById("emailErrorStudent").textContent = `*Email không tồn tại*`
                    document.getElementById("emailErrorStudent").style.display = "block"
                    }else if(data.data === 1){
                       document.getElementById("passwordErrorStudent").textContent = `*Mật khẩu không chính xác*`
                       document.getElementById("passwordErrorStudent").style.display = "block"
                    }
                } else if(data.statusCode === 200){
                    if(data.data.StudentID){
                            localStorage.setItem('info', JSON.stringify(data.data));
                            alert('Đăng nhập thành công!');
                            window.location.href = "http://localhost:9999/Project/student"
                            // Chuyển hướng đến trang khác nếu cần
                    }else{
                        alert('Tài khoản của bạn đã bị khóa!');
                    }
                }
            });
        });

document.getElementById('Employee').addEventListener('submit', function(event) {
            event.preventDefault();
            var email = document.getElementById('emailEmployee').value;
            var password = document.getElementById('passwordEmployee').value;

            fetch('http://localhost:9999/Project/loginEmployee', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    'email': email,
                    'password': password
                })
            })
            .then(response => response.json())
            .then(data => {
                 console.log(data)
                 document.getElementById("emailErrorEmployee").innerHTML = "";
                 document.getElementById("passwordErrorEmployee").innerHTML = "";
                if (data.statusCode === 400) {
                    if(data.data === 0){
                    document.getElementById("emailErrorEmployee").textContent = `*Email không tồn tại*`
                    document.getElementById("emailErrorEmployee").style.display = "block"
                    }else if(data.data === 1){
                       document.getElementById("passwordErrorEmployee").textContent = `*Mật khẩu không chính xác*`
                       document.getElementById("passwordErrorEmployee").style.display = "block"
                       }
                } else if(data.statusCode === 200){
                    if(data.data.EmployeeID){
                            localStorage.setItem('info', JSON.stringify(data.data));
                            alert('Đăng nhập thành công!');
                            if(data.data.RoleID == 1){
                               window.location.href = "http://localhost:9999/Project/instructor/info";
                            }else{
                               window.location.href = "http://localhost:9999/Project/admin/dashboard";
                            }
                            // Chuyển hướng đến trang khác nếu cần
                    }else{
                        alert('Tài khoản của bạn đã bị khóa!');
                    }
                }
            });
        });