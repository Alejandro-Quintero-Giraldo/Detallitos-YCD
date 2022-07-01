package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.service.UserService;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.controller");

	@Autowired
    private UserService userService;

	//metodo que muestra la pagina de inicio
    @RequestMapping("/")
    public String index(Model model){
        UserDetails userCredentials = checkSession();
        //Si el usuario está logueado
        if(userCredentials != null){
            //Busca al usuario
            User user = userService.findUserByUserId(userCredentials.getUsername());
            //Y lo agrega a la interfaz con el nombre "user"
            model.addAttribute("user", user);
        }
        //Muestra el archivo index.html
        return "index";
    }

    //muestra la pagina del login
    @GetMapping("/login")
    public String loginPage(Model model){
        //Si ya está logueado redirige a la pagina de inicio
        if(checkSession() != null){
            return "redirect:/";
        }
        //sino, añade una nueva instancia del usuario a la interfaz
        model.addAttribute("user", new User());
        //muestra el archivo login.html
        return "login";
    }

    //Muestra la pagina de registro
    @RequestMapping("register")
    public String registerPage(){
        return "register";
    }

    //Crea una nueva instancia del usuario en la interfaz con el nombre "userRegister"
    @ModelAttribute("userRegister")
    public User returnNewUser(){
        return new User();
    }

    //Metodo para loguear
    @GetMapping("validate")
    public String validateUser(@RequestBody User userDTO) {
        //Ejecuta el servicio del logueo del usuario
        UserDetails user = userService.loadUserByUsername(userDTO.getEmail());
        //Si el usuario no existe o las credenciales no coinciden
        if (user == null) {
            //muestra alerta de error
            return "redirect:/login?error";
        }
        //Sino, redirige a la página de inicio
        return "redirect:/";
    }

    //metodo para registrar un usuario
    @PostMapping("saveUser")
    public String saveUserAction(User user) {
        //Verifica si el usuario ya existe
        User verifyUserId = userService.findUserByUserId(user.getUserId());
        if(verifyUserId != null) return "redirect:/register?idExists";
        //Verifica si el correo ya está en uso
        User verifyUserEmail = userService.findByEmail(user.getEmail());
        if(verifyUserEmail != null) return "redirect:/register?emailExist";
        //Sino, guarda el usuario
        userService.save(user);
        //Muestra alerta de registro exitoso
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
    //Pagina de acceso denegado
    @GetMapping("/accessDenied")
    public String accessDeniedPage(){
        return "accessDenied";
    }
    //Pagina de olvide la contraseña
    @GetMapping("forgotPass")
    public String forgotPass(){
        return "";
    }
    //metodo para cambiar una contraseña
    @PutMapping("changePassword")
    public String changePassword(@RequestParam("userId") String userId, @RequestParam("cellphone") String cellphone,
                                 @RequestParam("email") String email){
        User user = userService.findByEmail(email);
        if(user == null){
            return "redirect:/forgotPass?notFound";
        }
        if(user.getUserId().equals(userId) && user.getCellphone().equals(cellphone)){
            return "redirect:/newPassword";
        } else {
            return "redirect:/forgotPass?badCredentials";
        }
    }
    //Pagina para crear nueva contraseña
    @GetMapping("newPassword")
    public String newPassword(){
        return "";
    }
    //Retorna la informacion del usuario logueado
    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }
    
}
