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