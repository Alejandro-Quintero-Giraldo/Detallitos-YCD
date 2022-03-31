package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService  extends UserDetailsService {

    User save(User userDTO);

    User findUserByUserId(String id);

    User findByEmail(String email);

    List<User> findUserWithoutAdmin();

}
