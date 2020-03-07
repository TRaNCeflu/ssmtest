// 学生登录
function loginStudent() {
    let name = document.getElementById("name").value;
    let password = document.getElementById("password").value;
    axios.post(`http://127.0.0.1:8080/ssmtest/api/validatestudentlogin`,
        {
            studentId:name,
            password: password
        }
    )
        .then(res => {
            if (res.data.code === 0) {
                alert("用户不存在");
            } else if(res.data.code === 1){
                alert("密码不正确");
                // 登录成功后，将后端返回的token转换为cookie，实现免登录
                // 跳转到studentManage.html
            }else if(res.data.code === 2) {
                //document.cookie = res.data.token;
                window.sessionStorage.setItem("id",res.data.login.studentId);
                window.sessionStorage.setItem("name",res.data.login.studentName);
                window.location.href = "static/html/studentMain.html";
            }
        })
        .catch(res => {
        })
}

// 老师登录
function loginTeacher() {
    let name = document.getElementById("name").value;
    let password = document.getElementById("password").value;
    axios.post(`http://127.0.0.1:8080/ssmtest/api/validateteacherlogin`,
        {
            teacherId:name,
            password: password
        }
    )
        .then(res => {
            if (res.data.code === 0) {
                alert("用户不存在");
            } else if(res.data.code === 1){
                alert("密码不正确");
                // 登录成功后，将后端返回的token转换为cookie，实现免登录
                // 跳转到studentManage.html
            }else if(res.data.code === 2) {
                //document.cookie = res.data.token;
                window.sessionStorage.setItem("id",res.data.login.teacherId);
                window.sessionStorage.setItem("name",res.data.login.name);
                window.location.href = "static/html/TeacherMain.html";
            }
        })
        .catch(res => {
        })
}
// 跳转到注册页面
// function jumpAdmin() {
//     window.location.href = "static/html/";
// }
// 验证浏览器中是否存在cookie
// function checkWhetherExistCookie() {
//     var token =document.cookie.split(";")[0];
//     // 如果存在token，则跳转首页
//     if (token) {
//         window.location.href = "static/html/studentManage.html";
//     }
// }
