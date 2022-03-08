package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.entity.Administrator;
import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.repository.AdministratorRepository;
import co.com.detallitosycd.app.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService implements IUserService {

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public User save(User userDTO){
        passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(userDTO.getUserId(),userDTO.getUserName(), userDTO.getCellphone(), userDTO.getAddress(),
                userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw  new UsernameNotFoundException("Correo electrónico o contraseña incorrecto");
        }
        //System.out.println("paso");
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),
                mapAuthorities(user.getEmail()));
    }

    public Collection<GrantedAuthority> mapAuthorities(String email){
        Administrator administrator = administratorRepository.findByEmail(email);
        if (administrator == null) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
        }
    }
}
