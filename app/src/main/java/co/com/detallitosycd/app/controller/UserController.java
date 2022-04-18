package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.service.UserService;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.util.annotation.Nullable;

@Controller
public class UserController {

	private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.controller");

	@Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model){
        UserDetails userCredentials = checkSession();
        if(userCredentials != null){
            User user = userService.findUserByUserId(userCredentials.getUsername());
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        if(checkSession() != null){
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("register")
    public String registerPage(){
        return "register";
    }

    @ModelAttribute("userRegister")
    public User returnNewUser(){
        return new User();
    }

    @GetMapping("validate")
    public String validateUser(User userDTO) {
        UserDetails user = userService.loadUserByUsername(userDTO.getEmail());
        if (user == null) {
            return "redirect:/login?error";
        }
        return "redirect:/";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user) {
        System.out.println("\nEste es el controller\n");
        User verifyUserId = userService.findUserByUserId(user.getUserId());
        if(verifyUserId != null) return "redirect:/register?idExists";
        User verifyUserEmail = userService.findByEmail(user.getEmail());
        if(verifyUserEmail != null) return "redirect:/register?emailExist";
        userService.save(user);
        return "redirect:/register?success";
    }

    @GetMapping("/withoutAdmin")
    public String getUsersWithoutAdmin(Model model){
        List<User> userList = userService.findUserWithoutAdmin();
        model.addAttribute("userList", userList);

        //TODO: PONER NOMBRE HTML EN EL CONTROLADOR DE USUARIO SIN ADMIN, AGREGAR ID DE EMPRESA EN UN INPUT VACÍO
        //TODO: AGREGAR DOCUMENTACIÓN DEL PROYECTO AL GIT
        return "";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage(){
        return "accessDenied";
    }

    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }
    
}
