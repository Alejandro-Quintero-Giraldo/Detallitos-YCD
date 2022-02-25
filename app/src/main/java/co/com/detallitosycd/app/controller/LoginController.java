package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(User user){

    }
}
