package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.entity.Administrator;
import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.repository.AdministratorRepository;
import co.com.detallitosycd.app.rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(UserService.class)
class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AdministratorRepository administratorRepository;

    @Autowired
    private UserService userService;

    private User userMocked  = new User("1234567890", "randomName",
            "randomCellphone", "randomAddress","randomMail",
            "randomPass");

    @Test
    void shouldSaveUser(){
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(userMocked.getPassword());
        Mockito.when(userRepository.save(any(User.class))).thenReturn(userMocked);
        User newUser = userService.save(userMocked);
        Assertions.assertEquals(userMocked.getPassword(),newUser.getPassword());
    }


    @Test
    void shouldFindById(){

        Mockito.when(userRepository.findUserByUserId("1234567890")).thenReturn(userMocked);
        User userFind = userService.findUserByUserId(userMocked.getUserId());

        Assertions.assertEquals(userFind.getUserId(),userMocked.getUserId());
    }

    @Test
    void shouldFindByEmail(){
        Mockito.when(userRepository.findUserByEmail("randomMail")).thenReturn(userMocked);
        User userFind = userService.findByEmail(userMocked.getEmail());
        Assertions.assertEquals(userFind.getEmail(), userMocked.getEmail());
    }

    @Test
    void shouldLoginUser(){

        Administrator administratorMock = new Administrator("1234567890", "randomMail","123");

        Mockito.when(userRepository.findUserByEmail(userMocked.getEmail())).thenReturn(userMocked);
        Mockito.when(administratorRepository.findByEmail(userMocked.getEmail())).thenReturn(administratorMock);

        UserDetails userDetails = userService.loadUserByUsername(userMocked.getEmail());
        Assertions.assertEquals(userDetails.getUsername(),userMocked.getUserId());
    }
}