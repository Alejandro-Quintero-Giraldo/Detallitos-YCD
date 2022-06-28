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
        icon: 'error',
        allowEnterKey: false,
        allowEscapeKey: false,
        allowOutsideClick: false
    }).then((res) => {
        if(res.isConfirmed){
            window.history.back();
        }
    })
}

function alertRegisterEmailExist() {
    Swal.fire({
        title: "¡Este correo electrónico ya existe!",
        text: "El correo electrónico ingresado ya pertenece a otro usuario. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowEnterKey: false,
        allowEscapeKey: false,
        allowOutsideClick: false
    }).then((res) => {
        if(res.isConfirmed){
            window.history.back();
        }
    })
}

function alertProductImageError() {
    Swal.fire({
        title: "¡ERROR!",
        text: "La imagen ingresada para el producto, supera el tamaño máximo (10MB). Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false
    }).then((res) => {
        if(res.isConfirmed){
            window.history.back();
        }
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

function alertSupportLoginRequired() {
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
        if (result.isConfirmed) {
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

const alertAskEspecificationsInProduct = () => {
    Swal.fire({
        title: "¡Ingrese la información!",
        text: "¿Qué especificaciones quieres añadir al producto que deseas? (opcional)",
        confirmButtonText: "Agregar",
        confirmButtonColor: '#3fc3ee',
        icon: 'info',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        input: 'text',
        inputLabel: 'Especificaciones',
        inputPlaceholder: 'Ingresa las especificaciones de tu producto',
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        cancelButtonColor: '#f27474',
        showLoaderOnConfirm: true,
        inputAttributes: {
            autocapitalize: 'on',
            autocorrect: 'off'
        },
        preConfirm: (value) => {
            if (value.length != 0 && value.length < 10) {
                Swal.showValidationMessage('Si vas a escribir especificaciones, tu respuesta debe tener al menos 10 caracteres')
            }
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const inputSubmit = document.getElementById('submit');
            inputSubmit.setAttribute('type', 'submit');
            const inputEspecifications = document.getElementById('especifications');
            inputEspecifications.value = result.value;
            document.querySelector('#submit').click();
        }
    });
}

const alertLoginRequired = () => {
    Swal.fire({
        title: "¡Inicie sesión!",
        text: "Para realizar una compra debes iniciar sesión",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#3fc3ee',
        icon: 'info'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.reload();
        }
    })
}

const alertRegisterFailed = (message) => {
    Swal.fire({
        title: '¡El formulario es inválido!',
        text: message,
        confirmButtonText: '¡Aceptar!',
        confirmButtonColor: '#f27474',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: true,
    })
}

const alertProductAddedInBill = () => {
    Swal.fire({
        title: '¡Operación exitosa!',
        text: '¡Se ha registrado el producto a tu compra!',
        confirmButtonText: '¡Aceptar!',
        confirmButtonColor: '#a5dc86',
        icon: 'success',
        llowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    })
}

const alertProductDeletedInBill = () => {
    Swal.fire({
        title: '¡Operación exitosa!',
        text: '¡Se ha eliminado el producto de tu compra!',
        confirmButtonText: '¡Aceptar!',
        confirmButtonColor: '#a5dc86',
        icon: 'success',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    })
}

const alertConfirmBill = () => {
    Swal.fire({
        title: 'Confirme la operación',
        text: '¿Está seguro de que desea realizar esta compra?',
        confirmButtonText: "Comprar",
        confirmButtonColor: '#3fc3ee',
        icon: 'info',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        cancelButtonColor: '#f27474',
    }).then((result) => {
        if (result.isConfirmed) {
            const inputSubmit = document.getElementById('submit');
            inputSubmit.setAttribute('type', 'submit');

            Swal.fire({
                title: '¡Se ha registrado tu compra!',
                confirmButtonText: '¡Aceptar!',
                confirmButtonColor: '#a5dc86',
                icon: 'success',
                allowOutsideClick: false,
                allowEscapeKey: false,
                allowEnterKey: false,
            }).then((res) => {
                if (res.isConfirmed) {
                    document.querySelector('#submit').click();
                }
            })
        } else {
            const inputSubmit = document.getElementById('submit');
            inputSubmit.setAttribute('type', 'button');
        }
    })
}

const alertExtensionImageError = () => {
    Swal.fire({
        title: "¡ERROR!",
        text: "La imagen ingresada no tiene una extensión de imagen. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false
    }).then((res) => {
        if(res.isConfirmed){
            window.history.back();
        }
    })

}

const alertDeleteProductError = () => {
    Swal.fire({
        title: "¡ERROR!",
        text: "La imagen ingresada no tiene una extensión de imagen. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false
    }).then((res) => {
        if(res.isConfirmed){
           
            window.history.back();
        }
    })

}

const alertDeleteCatalogueError = () => {
    Swal.fire({
        title: "¡ERROR!",
        text: "El id del catálogo no existe en la base de datos. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false
    });
}

const alertDeleteCatalogue = () => {
    Swal.fire({
        title: 'Confirme la operación',
        text: '¿Está seguro de que desea eliminar este catálogo?',
        confirmButtonText: "Eliminar",
        confirmButtonColor: '#3fc3ee',
        icon: 'info',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        cancelButtonColor: '#f27474',
    }).then((result) => {
        if (result.isConfirmed) {
            var inputSubmit = document.querySelector('#submit-delete');
            inputSubmit.setAttribute('type', 'submit');
            inputSubmit.click();
        }
    });
}

const alertDeleteCatalogueSuccess = () => {
    Swal.fire({
        title: '¡Se ha eliminado el catálogo exitosamente!',
        confirmButtonText: '¡Aceptar!',
        confirmButtonColor: '#a5dc86',
        icon: 'success',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    });
}

const alertNoProductsInActiveBill = () => {
    Swal.fire({
        title: 'Sin productos',
        text: 'Tienes una factura iniciada sin productos. Agrega productos para realizar la compra',
        confirmButtonText: "Aceptar",
        confirmButtonColor: '#3fc3ee',
        icon: 'info',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.assign('/product/');
        }
    })
}

const alertNoActiveBill = () => {
    Swal.fire({
        title: 'Sin facturas iniciadas',
        text: 'No tienes facturas iniciadas. Agrega un producto al carrito para iniciar una factura',
        confirmButtonText: "Aceptar",
        confirmButtonColor: '#3fc3ee',
        icon: 'info',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.assign('/product/');
        }
    })
}

const alertDeleteProductInCatalogueError = () => {
    Swal.fire({
        title: "¡ERROR!",
        text: "El producto no se pudo eliminar del catálogo. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false
    });
}

const alertDeleteProductInCatalogueSuccess = () => {
    Swal.fire({
        title: '¡Se ha eliminado el producto del catálogo exitosamente!',
        confirmButtonText: '¡Aceptar!',
        confirmButtonColor: '#a5dc86',
        icon: 'success',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    });
}

const alertSelectProductInCatalogue = (productList) => {
    console.log(productList);
    const productValue = productList.map(product => product.productName + ' $'+ product.productPrice);
    console.log(productValue);
    Swal.fire({
        title: "¡Seleccione el producto!",
        text: "Escoja el producto que desea añadir al catálogo",
        confirmButtonText: "Agregar",
        confirmButtonColor: '#3fc3ee',
        icon: 'info',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        input: 'select',
        inputLabel: 'Seleccione un producto',
        inputOptions: productValue,
        showCancelButton: true,
        cancelButtonText: 'Cancelar',
        cancelButtonColor: '#f27474',
        showLoaderOnConfirm: true,
        inputAttributes: {
            autocapitalize: 'on',
            autocorrect: 'off'
        },
        preConfirm: (value) => {
            if (value == '') {
                Swal.showValidationMessage('Debes escoger una opción');
            }
        }
    }).then((result) => {
        if (result.isConfirmed) {
            console.log(result.value);
            const productSelected =  productList[result.value];
            console.log(productSelected);
            const inputProductId = document.getElementById('productId');
            inputProductId.value = productSelected.productId;
            const inputSubmit = document.querySelector('#submit-product');
            inputSubmit.setAttribute('type', 'submit');
            inputSubmit.click();
        }
    });
}

const alertAddProductInCatalogueSuccess = () => {
    Swal.fire({
        title: '¡Se ha agregado el producto al catálogo exitosamente!',
        confirmButtonText: '¡Aceptar!',
        confirmButtonColor: '#a5dc86',
        icon: 'success',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
    })
}

const alertAddProductInCatalogueError = () => {
    Swal.fire({
        title: "¡ERROR!",
        text: "El producto no se pudo agregar al catálogo. Por favor inténtelo nuevamente",
        confirmButtonText: "¡Aceptar!",
        confirmButtonColor: '#f27474 ',
        icon: 'error',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false
    });
}
