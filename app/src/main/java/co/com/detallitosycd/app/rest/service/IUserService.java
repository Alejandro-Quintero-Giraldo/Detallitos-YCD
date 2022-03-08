package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService  extends UserDetailsService {

    User save(User userDTO);

}
