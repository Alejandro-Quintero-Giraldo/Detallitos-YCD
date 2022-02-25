package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw  new UsernameNotFoundException("Correo electrónico o contraseña incorrecto");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),);
    }

}
