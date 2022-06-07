package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.SpringSecurityWebAuxTestConfig;
import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

 /*   @Test
    @WithAnonymousUser
    void shouldSaveUser() throws Exception {
        User userMock = new User("12345678901", "Ricardo", "123456", "calle 34",
                "ricardo@mail.com","123456");
        /*UserController userController  = new UserController() {
            @ModelAttribute
            public User userRegister() {
                return userMock;
            }
        };

        Mockito.when(userService.findUserByUserId(userMock.getUserId())).thenReturn(null);
        Mockito.when(userService.findByEmail(userMock.getEmail())).thenReturn(null);

        mockMvc.perform(post("/saveUser")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userId", userMock.getUserId())
                        .param("userName", userMock.getUserName())
                        .param("cellphone", userMock.getCellphone())
                        .param("address", userMock.getAddress())
                        .param("email", userMock.getEmail())
                        .param("password", userMock.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register?success"));

    }
*/
    @Test
    @WithAnonymousUser
    void shouldValidateLoginUserSuccess() throws Exception{
        User userMock = new User("12345678901", "Ricardo", "123456", "calle 34",
                "ricardo@mail.com","123456");
        org.springframework.security.core.userdetails.User userMocked =
                new org.springframework.security.core.userdetails.User("Ricardo", "123456", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        Mockito.when(userService.loadUserByUsername(userMock.getEmail())).thenReturn(userMocked);

        mockMvc.perform(get("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userMock)))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithAnonymousUser
    void shouldShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithUserDetails("Basic User")
    void shouldShowLoginPageWithBasicUser() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(redirectedUrl("/"));
    }




/*
    @Test
    @WithUserDetails("Administrator User")
    void getUserWithoutAdmin() throws Exception {
        List<User> userList = List.of(new User("12345678901", "Ricardo", "123456", "calle 34",
                "ricardo@mail.com","123456"));
        Mockito.when(userService.findUserWithoutAdmin()).thenReturn(userList);

        mockMvc.perform(get("/withoutAdmin")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
                //.andExpect(model().attribute("userList",userList))
                //.andExpect(redirectedUrl("/"));
    }

*/

}