function alertLoginError() {
    Swal.fire({
        title: "¡Credenciales incorrectas!",
        text: "El correo electrónico o contraseña ingresada son incorrectos. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error'
    })
}

function alertLogout() {
    Swal.fire({
        title: "¡Has cerrado sesión!",
        text: "Se ha cerrado la sesión exitosamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#3fc3ee ',
        icon: 'info'
    })
}

function alertRegisterSuccess() {
    Swal.fire({
        title: "¡Usuario registrado exitosamente!",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#a5dc86 ',
        icon: 'success'
    })
}

function alertRegisterIdExist() {
    Swal.fire({
        title: "¡Este usuario ya existe!",
        text: "El documento ingresado ya pertenece a otro usuario. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error'
    })
}

function alertRegisterEmailExist() {
    Swal.fire({
        title: "¡Este correo electrónico ya existe!",
        text: "El correo electrónico ingresado ya pertenece a otro usuario. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error'
    })
}

function alertProductImageError() {
    Swal.fire({
        title: "¡ERROR!",
        text: "La imagen ingresada para el producto, supera el tamaño máximo (10MB). Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error'
    })
}

function alertProductSuccess() {
    Swal.fire({
        title: "¡Producto creado!",
        text: "El producto se ha creado exitosamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#a5dc86',
        icon: 'success'
    })
}

function alertProductUpdated() {
    Swal.fire({
        title: "¡Producto actualizado!",
        text: "El producto se ha actualizado exitosamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#a5dc86',
        icon: 'success'
    })
}

function alertSupportLoginRequired(){
    Swal.fire({
        title: "¡Inicie sesión!",
        text: "Para enviar un mensaje a nuestro equipo de soporte, debe iniciar sesion",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#3fc3ee',
        icon: 'info'
    })
}

const alertAccessDenied = () => {
    Swal.fire({
        title: "¡Acceso denegado!",
        text: "Se le ha denegado el acceso a esta página. Si se trata de un error comuniquese con el Administrador de la aplicación",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        width: "50%"
    }).then((result) => {
        if(result.isConfirmed){
            window.location.assign('/');
        }
    })
}

const alertProductExistInBill = () => {
    Swal.fire({
            title: "¡El producto ya existe!",
            text: "El producto agregado ya existe en la factura",
            confirmButtonText: "¡Aceptar!",
            confirmButtonColor: '#f27474',
            icon: 'error',
            allowOutsideClick: false,
            allowEscapeKey: false,
            allowEnterKey: false,
        });
}

const alertAskEspecificationsInBill = () => {
    Swal.fire({
                title: "¡Ingrese la información!",
                text: "¿Qué especificaciones quieres añadir al producto que deseas? (opcional)",
                confirmButtonText: "¡Aceptar!",
                confirmButtonColor: '#3fc3ee',
                icon: 'info',
                allowOutsideClick: false,
                allowEscapeKey: false,
                allowEnterKey: false,

            });
}