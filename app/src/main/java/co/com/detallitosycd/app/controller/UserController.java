
package co.com.detallitosycd.app.controller;


import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.model.user.UserModel;

import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@WebServlet(name="srvUser", urlPatterns = {"/srvUser"})
@Controller
public class UserController extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.controller");

    public UserModel userModel;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /*
    @GetMapping("login")
    public String loginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }*/

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
