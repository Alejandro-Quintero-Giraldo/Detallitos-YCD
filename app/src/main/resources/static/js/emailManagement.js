
var emailMessage = '';


function handleInputChange() {
    var event = document.getElementById('input-support').value;
    emailMessage = event;
}

function sendEmail(user, email) {
    if (emailMessage != null && emailMessage != '' && emailMessage.length > 20 && emailMessage != undefined) {
        emailjs.send("service_rn2q32c", "template_iwudxl5", {
            to_name: "soporteejemplo",
            from_name: user,
            message: emailMessage,
            reply_to: email,
            from_email: email
        }).then(() => {
            Swal.fire({
                title: "¡Mensaje enviado!",
                text: "El mensaje se ha enviado con éxito a nuestro equipo de soporte",
                confirmButtonText: "¡Aceptar!",
                confirmButtonColor: '#a5dc86',
                icon: 'success',
                showLoaderOnConfirm: true,
                allowOutsideClick: false,
                allowEscapeKey: false,
                allowEnterKey: false,
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.reload();
                }
            })
        }, () => {
            Swal.fire({
                title: "¡ERROR!",
                text: "Ha ocurrido un error al enviar el mensaje a nuestro equipo de soporte. Por favor inténtelo nuevamente",
                confirmButtonText: "¡Aceptar!",
                confirmButtonColor: '#f27474 ',
                icon: 'error',
                showLoaderOnConfirm: true,
                allowOutsideClick: false,
                allowEscapeKey: false,
                allowEnterKey: false,
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.reload();
                }
            })
        });
        emailMessage = "";
    } else {
        Swal.fire({
            title: "¡ERROR!",
            text: "El mensaje a soporte debe contener como mínimo 20 caracteres. Por favor inténtelo nuevamente",
            confirmButtonText: "¡Aceptar!",
            confirmButtonColor: '#f27474 ',
            icon: 'error',
            showLoaderOnConfirm: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
            allowEnterKey: false,
        })
    }
}