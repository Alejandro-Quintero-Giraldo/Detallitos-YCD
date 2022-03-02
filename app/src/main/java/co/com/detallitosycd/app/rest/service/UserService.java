package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.dto.UserDTO;
import co.com.detallitosycd.app.entity.Administrator;
import co.com.detallitosycd.app.entity.User;
import co.com.detallitosycd.app.rest.repository.AdministratorRepository;
import co.com.detallitosycd.app.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        System.out.println("Entra al save");
        passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(userDTO.getUserId(),userDTO.getUserName(), userDTO.getCellphone(), userDTO.getAddress(),
                userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
        System.out.println("user save");
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //System.out.println("antes del query");
        User user = userRepository.findUserByEmail(email);
       // System.out.println("login "+user.getUserId());
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
            System.out.println("role user");
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            System.out.println("role administrator");
            return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
        }
    }
}
