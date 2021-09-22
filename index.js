var modalLogin = document.getElementById("modal-login-container-home");
var modalRegister= document.getElementById("modal-register-container-home");
var modalAudio = document.getElementById("modal-audio-container-home");

var btnLogin = document.getElementById("login");
var btnRegister = document.getElementById("register");

var  buttonAudio = document.getElementsByClassName('button-audio');

var span = document.getElementsByClassName("close")[0];
var spanRegister = document.getElementsByClassName("closeRegister")[0];

btnLogin.onclick = function() {
  modalLogin.style.display = "block";
}
btnRegister.onclick = function() {
    modalRegister.style.display = "block";
}

buttonAudio[0].onclick = function(){
  modalAudio.style.display = "none";
}
buttonAudio[1].onclick = function(){
  modalAudio.style.display = "none";
}

span.onclick = function() {
  modalLogin.style.display = "none";
}
spanRegister.onclick = function() {
    modalRegister.style.display = "none";
}
  
window.onclick = function(event) {
  if (event.target == modalLogin) {
    modalLogin.style.display = "none";
  }
  if (event.target == modalRegister) {
    modalRegister.style.display = "none";
  }
}
