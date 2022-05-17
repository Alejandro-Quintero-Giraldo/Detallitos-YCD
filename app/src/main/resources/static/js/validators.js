
const regExNumber = /[0-9][^.,$%&/()=!¡?¿"}{]/;
const regExName = /[a-zA-Z][^.,$%&/()=!¡?¿"}{]/;
const regExAddress = /[A-Za-z\d# ]/;
const regExEmail = /([a-zA-Z0-9]([^ @&%$\\\/()=?¿!.,:;]|\d)+[a-zA-Z0-9][\.]){1,2}[a-zA-Z]{2,4}([\.][a-zA-Z]{2})?/;

var errorMessage = '';
var isValid = false;


const formRegisterValidators = () => {
    const inputname = document.getElementById('userName').value;
    const inputId = document.getElementById('userId').value;
    const inputCellphone = document.getElementById('cellphone').value;
    const inputAddress = document.getElementById('address').value;
    const inputEmail = document.getElementById('email').value;
    const inputPassword = document.getElementById('password').value;
    const inputSubmit = document.getElementById('submit');

    errorMessage = ''
    console.log('inputname ', inputname.length, ' a', inputname);
    
      validateInputNotNull(inputId) ? errorMessage = 'El identificador no puede estar vacío' 
    : validateLength(inputId, 8, 'M') ? errorMessage = 'El identificador debe tener mínimo 9 dígitos'
    : validateLength(inputId, 11, 'm') ? errorMessage = 'El identificador debe tener máximo 10 dígitos'
    : validateRegExp(inputId, regExNumber) ? errorMessage = 'El identificador tiene caracteres que no son permitidos'
    : validateInputNotNull(inputname) ? errorMessage = 'El nombre no puede estar vacío'
    : validateRegExp(inputname,regExName) ? errorMessage = 'El nombre tiene caracteres que no son permitidos'
    : validateLength(inputname, 9, 'M') ? errorMessage = 'El nombre debe tener mínimo 10 caracteres'
    : inputCellphone.length === 10 ? errorMessage = 'El celular debe tener 10 caracteres'
    : validateRegExp(inputCellphone,regExNumber) ? errorMessage = 'El celular tiene caracteres que no son permitidos'
    : validateLength(inputAddress, 7, 'M') ? errorMessage = 'La dirección debe tener mínimo 7 caracteres'
    : validateRegExp(inputAddress, regExAddress) ? errorMessage = 'La dirección tiene caracteres que no son permitidos'
    : validateLength(inputAddress, 100, 'm') ? errorMessage = 'La dirección debe tener máximo 100 caracteres'
    : validateRegExp(inputEmail, regExEmail) ? errorMessage = 'El email no cumple con el formato de un correo electrónico'
    : validateLength(inputPassword, 7, 'M') ? errorMessage = 'La contraseña debe tener mínimo 8 caracteres'
    : isValid = true 
    if(isValid){
        inputSubmit.removeAttribute('disabled');
        console.log('Pasa expresiones regulares');
    } else {
        inputSubmit.hasAttribute('disabled') ? null : inputSubmit.setAttribute('disabled', 'true');
        console.log('inputId ', inputId.length, ' a', inputId)
    }
    console.log('errorMessage', errorMessage)
}

const validateLength = (value, lengthCondition, operator) => {
    return operator === 'm' ? value < lengthCondition : operator === 'M' ?  value > lengthCondition : null;
}


const validateMinLength = (value, lengthCondition) => {
    return  value > lengthCondition;
}

const validateMaxLength = (value, lengthCondition) => {
    return  value < lengthCondition;
}


const validateRegExp = (input, regExp) => {
    return input.match(regExp);
}

const validateInputNotNull = (input) => {
    return input === null || input === undefined;
}