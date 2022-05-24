
const regExNumber = /[^.,]/ ///[0-9^.,$%&/()=!¡?¿"}{][]/;
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
        : validateMinLength(inputId.length, 8) ? errorMessage = 'El identificador debe tener mínimo 9 dígitos'
            : validateMaxLength(inputId.length, 11) ? errorMessage = 'El identificador debe tener máximo 10 dígitos'
                : validateInputNotNull(inputname) ? errorMessage = 'El nombre no puede estar vacío'
                    : validateRegExp(inputname, regExName) ? errorMessage = 'El nombre tiene caracteres que no son permitidos'
                        : validateMinLength(inputname.length, 9) ? errorMessage = 'El nombre debe tener mínimo 10 caracteres'
                            : inputCellphone.length !== 10 ? errorMessage = 'El celular debe tener 10 caracteres'
                                : validateRegExp(inputCellphone, regExNumber) ? errorMessage = 'El celular tiene caracteres que no son permitidos'
                                    : validateMinLength(inputAddress.length, 7) ? errorMessage = 'La dirección debe tener mínimo 7 caracteres'
                                        : validateRegExp(inputAddress, regExAddress) ? errorMessage = 'La dirección tiene caracteres que no son permitidos'
                                            : validateMaxLength(inputAddress.length, 100) ? errorMessage = 'La dirección debe tener máximo 100 caracteres'
                                                : validateInputNotNull(inputEmail) ? errorMessage = 'El correo no puede estar vacío'
                                                    : validateRegExp(inputEmail, regExEmail) ? errorMessage = 'El email no cumple con el formato de un correo electrónico'
                                                        : validateMinLength(inputPassword.length, 7) ? errorMessage = 'La contraseña debe tener mínimo 8 caracteres'
                                                            : isValid = true
    // if(isValid){
    //     inputSubmit.removeAttribute('disabled');
    //     console.log('Pasa expresiones regulares');
    // } else {
    //     inputSubmit.hasAttribute('disabled') ? null : inputSubmit.setAttribute('disabled', 'true');
    //     console.log('inputId ', inputId.length, ' a', inputId)
    // }
    console.log('errorMessage', errorMessage)
}

const validateLength = (value, lengthCondition, operator) => {
    return operator === 'm' ? value < lengthCondition : operator === 'M' ? value > lengthCondition : null;
}


const validateMinLength = (value, lengthCondition) => {
    console.log('min', value, ' ', lengthCondition)
    return !(value > lengthCondition);
}

const validateMaxLength = (value, lengthCondition) => {
    console.log('max', value, ' ', lengthCondition)
    return !(value < lengthCondition);
}


const validateRegExp = (input, regExp) => {
    return !(input.match(regExp));
}

const validateInputNotNull = (input) => {
    return input === null || input === undefined || input === '';
}

const alertAnyError = () => {

    formRegisterValidators();
    if (!isValid) {
        return alertRegisterFailed(errorMessage);
    } else {
        axios.post('localhost:8080/saveUser', {
            userId: document.getElementById('userId').value,
            userName: document.getElementById('userName').value,
            cellphone: document.getElementById('cellphone').value,
            address: document.getElementById('address').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        })
    }
}

const validateInputNumberNoPointComa = (event, input) => {
    var code = (event.which) ? event.which : event.keyCode;
    console.log('keypress', code);
    if (code >= 48 && code <= 57) {
        errorMessage = '';
        formRegisterValidators();
        return true;
    } else {
        errorMessage = 'Ha añadido un caracter inválido en el ' + input;
        isValid = false;
        console.log('key ', errorMessage);
        return false;
    }
}