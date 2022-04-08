
var emailMessage = '';


function handleInputChange() {
    var event = document.getElementById('input-support').value;
    emailMessage = event;
}

function sendEmail(user, email) {
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
            icon: 'success'
        }).then((result) => {
            if(result.isConfirmed) {
                window.location.reload();
            }
        })
    }, (error) => {
        Swal.fire({
            title: "¡ERROR!",
            text: "Ha ocurrido un error al enviar el mensaje a nuestro equipo de soporte. Por favor inténtelo nuevamente",
            confirmButtonText: "¡Aceptar!",
            confirmButtonColor: '#f27474 ',
            icon: 'error'
        }).then((result) => {
            if(result.isConfirmed){
                window.location.reload();
            }
        })
    });
    emailMessage = "";
}