
package co.com.detallitosycd.app.infraestructure.controller;


import co.com.detallitosycd.app.domain.dto.UserDTO;
import co.com.detallitosycd.app.domain.entity.User;
import co.com.detallitosycd.app.domain.model.user.UserModel;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@WebServlet(name="srvUser", urlPatterns = {"/srvUser"})
@Controller
public class UserController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.infraestructure.controller");

    public UserModel userModel;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("login")
    public String loginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("home")
    public String homePage(Model model){
        return "home";
    }

    @RequestMapping("register")
    public String registerPage(Model model){
        model.addAttribute("userRegister", new User());
        return "register";
    }

    @GetMapping("validate")
    public String validateUser(User userDTO, Model model) throws Exception {
        userModel = new UserModel();
        System.out.println("\n\n"+userDTO.getEmail()+"\n"+userDTO.getPassword());
        User user = userModel.login(userDTO);
        //System.out.println("User: \n\n"+user.getUserName());
        if(user == null){
            return "redirect:/login?error";
        }
        System.out.println("\n\n"+user.getUserName());
        model.addAttribute("username", user.getUserName());
        return "redirect:/home";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user) throws Exception {
        System.out.println("El usuario "+user.getUserName()+"\n\n"+user.getEmail());
        userModel = new UserModel();
        System.out.println("Bien por aquí");
        userModel.register(user);
        return "redirect:/register?success";
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
