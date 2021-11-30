<!DOCTYPE html>
<html lang="es">
    <head>
    	<meta charset="UTF-8">
    	<title></title> 
    	<meta name="viewport" content="width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, minimum-scale=1.0">
    	<link rel="stylesheet" href="estilo.css">
    </head>  
    <body>
        <form class="formulario" action="srvUsuario?action=login" method="POST">
            <h1>Iniciar Sesi�n</h1>
             <div class="contenedor">
                <div class="input-contenedor">
                    <i class="fas fa-user icon"></i>
                    <input type="text" name="inputEmail" placeholder="Correo Electr�nico">
                </div>
                <div class="input-contenedor">
                    <i class="fas fa-key icon"></i>
                    <input type="password" name="inputPassword" placeholder="Contrase�a">
                </div>
                <input type="submit" value="Acceder" class="button">
                <p>Al enviar tus datos estar�n seguros y no ser�n revelados.</p>
                <p>�Ya ingresastes tus datos? <a class="link" href="/register.jsp">Registrarse</a></p>
             </div>
        </form>
    </body>
</html>