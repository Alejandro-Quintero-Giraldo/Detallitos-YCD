package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.service.UserService;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.controller");

	@Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
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

    @GetMapping("home")
    public String homePage(Model model){
        return "home";
    }

    @RequestMapping("register")
    public String registerPage(){
        if(checkSession() != null){
            return "redirect:/";
        }
        return "register";
    }

    @ModelAttribute("userRegister")
    public User returnNewUser(){
        return new User();
    }

    @GetMapping("validate")
    public String validateUser(User userDTO) {
        //userModel = new UserModel();
        System.out.println("\n\n"+userDTO.getEmail()+"\n"+userDTO.getPassword());
        //User user = userModel.login(userDTO);
        UserDetails user = userService.loadUserByUsername(userDTO.getEmail());
        //System.out.println("User: \n\n"+user.getUserName());
        if(user == null){
            return "redirect:/login?error";
        }
        //System.out.println("\n\n"+user.getUserName());
        //model.addAttribute("username", user.getUserName());
        return "redirect:/home";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user) throws Exception {
        System.out.println("El usuario "+user.getUserName()+"\n\n"+user.getEmail()+"\n"+user.getUserId()+"\n"+user.getPassword()+"\n"+user.getCellphone()+"\n"+user.getAddress());
        //userModel = new UserModel();
        User verifyUserId = userService.findUserByUserId(user.getUserId());
        if(verifyUserId != null) return "redirect:/register?idExists";
        User verifyUserEmail = userService.findByEmail(user.getEmail());
        if(verifyUserEmail != null) return "redirect:/register?emailExist";
        User newUser = userService.save(user);
        System.out.println("Bien por aquí");
        //userModel.register(user);
        return "redirect:/register?success";
    }

    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }
    /*
	@RequestMapping("login")
    protected void proccesLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        try {
            if(action != null){
                switch(action) {
                    case "login":
                        login(request, response);
                        break;
                    case "logout":
                        logout(request, response);
                        break;
                    default: 
                        response.sendRedirect("./view/index.html");
                }
            } else {
                response.sendRedirect("./view/index.html");
            }
        } catch(Exception e){
            try {
                this.getServletConfig().getServletContext().getRequestDispatcher("/index.html").forward(request, response);
            } catch(Exception exception){
                LOGGER.warning("Error: "+ exception.getMessage());
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session;
        UserDTO userDTO = getUser(request);
        UserModel userModel = new UserModel();
        User user = userModel.login(userDTO);
        if(user != null && user.getUserName() == "Admin"){
            session = request.getSession();
            session.setAttribute("user: ",user);
            request.setAttribute("message", "");
            this.getServletConfig().getServletContext().getRequestDispatcher("./view/home.html").forward(request, response);
        } else if(user != null){
            session = request.getSession();
            session.setAttribute("user",user);
            this.getServletConfig().getServletContext().getRequestDispatcher("./view/home.html").forward(request, response);
        } else {
            request.setAttribute("message", "Credenciales no válidas");
            request.getRequestDispatcher("./view/index.html").forward(request,response);
        }
    }
    */
    
}
