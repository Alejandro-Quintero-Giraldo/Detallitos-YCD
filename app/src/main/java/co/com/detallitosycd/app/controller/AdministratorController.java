package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Administrator;
import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @PostMapping("/save")
    public String saveAdministrator(User user, String companyNIT){
        Administrator newAdministrator = new Administrator(user.getUserId(), user.getEmail(), companyNIT);
        administratorService.saveAdmin(newAdministrator);

        //TODO: PONER NOMBRE HTML EN EL CONTROLADOR DE GUARDAR ADMINISTRADOR
        return  "";
    }

}
