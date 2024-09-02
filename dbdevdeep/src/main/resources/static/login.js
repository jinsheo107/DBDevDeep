// Chnage Label Position

// call when focus on input
function labelUp(input) {
  input.parentElement.children[0].setAttribute("class", "change_label");
}

// call when focus out on input
function labelDown(input) {
  if (input.value.length === 0) {
    input.parentElement.children[0].classList.remove("change_label");
  }

}

// show & hide password
var eye_icon_login = document.getElementById('eye_icon_login');
var login_password = document.getElementById("pwd");

eye_icon_login.addEventListener('click', () => {
  hideAndShowPass(eye_icon_login, login_password);
});

const hideAndShowPass = (eye_icon, password) => {
  if (eye_icon.classList.contains("fa-eye-slash")) {
    eye_icon.classList.remove('fa-eye-slash');
    eye_icon.classList.add('fa-eye');
    password.setAttribute('type', 'text');

  }
  else {
    eye_icon.classList.remove('fa-eye');
    eye_icon.classList.add('fa-eye-slash');
    password.setAttribute('type', 'password');
  }
};