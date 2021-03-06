<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
if(session.getAttribute("user") != null){

%>
<html>
    <head>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="estilo.css">
    </head>
    <body>

        <header class="main-header">
            <div class="container container--flex">
                <h1 class="main-title"><span class="color-span">DETALLITOS</span>YCD</h1>
                <nav class="main-nav">
                    <span class="icon-menu" id="btn-menu"><i class="fas fa-bars"></i></span>
                    <ul class="menu" id="menu">
                        <li class="menu__item"><a href="" class="menu__link menu__link--select"><span>PERFIL</span></a></li>
                        <li class="menu__item"><a href="" class="menu__link"><span>CARRITO</span></a></li>
                        <li class="menu__item"><a href="srvUser?action=logout" class="menu__link"><span>CERRAR SESIÓN</span></a></li>
                    </ul>
                </nav>
            </div>
        </header>
        <div class="banner">
            <div class="banner__content">
                <div class="container">
                    <h2 class="banner__title"></h2>
                    <p class="banner__txt"><b>Regalitos De Amor Para Toda Ocasión</b></p>
                </div>
            </div>
        </div>

        <main class="main">
            <section class="welcome">
                <br>
                <br>
                <br>
                <br>
                <h2 class=""design__item><b>¡BIENVENIDO ${user.getUserName()}!</b></h2>
                <p>A Continuación Encontrarás Información De Nosotros Como Microempresa</p>
                <br>
                <br>
                <br>
                <br>
            </section>
            <section class="container-design">
                <div class="design__item">
                    <h3 class="design__title">Productos Destacados</h3>
                    <img src="https://www.zankyou.com.pe/images/card-main/429/c4c0/550/475/w/627304/-/1518547326.png" alt="" class="design__img">
                </div>
                <div class="design__item">
                    <h3 class="design__title">CatÃ¡logo de Productos</h3>
                    <img src="https://i.pinimg.com/originals/89/df/f5/89dff56dd598ff1b2a55d3c4f2b42346.jpg" alt="" class="design__img">
                </div>
                <div class="design__item">
                    <h3 class="design__title">Opciones de Productos</h3>
                    <img src="https://http2.mlstatic.com/D_NQ_NP_722553-MPE26480979989_122017-O.jpg" alt="" class="design__img">
                </div>
                <div class="design__item">
                    <h3 class="design__title">Â¿QuiÃ©nes Somos?</h3>
                    <img src="logoDetallitosYCD.jpeg" alt="" class="design__img">
                </div>
            </section>
            <br>
            <br>
            <br>
            <br>
            <section class="container-testimonials">
                <h3 class="section__title">Â¡Quejas y Reclamos!</h3>
                <img src="http://www.pqrs.dsierra.com/images/pqrsLogo.png" alt="" class="testimonials__img">
            </section>
            <section class="newsletter">
                <h2 class="section__title">EscrÃ­benos</h2>
                <form action="" method="post" class="form">
                    <input type="email" placeholder="" class="form__mail">
                    <input type="submit" class="form__submit" value="ENVIAR">
                </form>
            </section>
            <br>
            <br>
        </main>
        <footer class="main-footer">
            <div class="container">
                <div class="column column--50-25">
                    <h2 class="footer__title">DETALLITOSYCD</h2>
                    <p class="footer__txt">Somos DetallitosYCD una microempresa que brinda a la sociedad cuadros, cojines y mugs personalizados para fechas especiales. Nos ubicamos en la ciudad de MedellÃ­n. Y puedes encontrar nuestros productos en la actual pÃ¡gina.</p>
                </div>
                <div class="column column--50-25">
                    <h2 class="footer__title">REDES SOCIALES</h2>
                    <div class="footer__socials">
                        <div class="social-icon">
                            <img src="facebook.png" width="45" height="45">
                            <a class="social__link" href="">Facebook</a>
                        </div>
                        <div class="social-icon">
                            <img src="instagram.png" width="45" height="45">
                            <a class="social__link" href="">Instagram</a>
                        </div>
                        <div class="social-icon">
                            <img src="whatsapp.png" width="45" height="45">
                            <a class="social__link" href="">WhatsApp</a>
                        </div>
                    </div>
                </div>
                <div class="column column--50-25">
                    <h2 class="footer__title">CONTÃCTANOS</h2>
                    <div class="contact-icon">
                        <i class="fas fa-map-marker-alt"></i>
                        <p class="contact__txt">DetallitosYCD</p>
                    </div>
                    <div class="contact-icon">
                        <i class="fas fa-phone-alt"></i>
                        <p class="contact__txt">3000587390</p>
                    </div>
                    <div class="contact-icon">
                        <i class="fas fa-envelope"></i>
                        <p class="contact__txt">detallitosYCD@gmail.com</p>
                    </div>
                </div>
            </div>
            <p class="copy"><b>Â© 2021 PROYECTO DETALLITOSYCD</b><span class="color-span"></span></p>
        </footer>
    </body>
</html>

<% 
    } else {
    response.sendRedirect("./view/index.jsp")
%>