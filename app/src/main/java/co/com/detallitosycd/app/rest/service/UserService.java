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
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public User save(User userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(userDTO.getUserId(),userDTO.getUserName(), userDTO.getCellphone(), userDTO.getAddress(),
                userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserByUserId(String id) {
        return userRepository.findUserByUserId(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> findUserWithoutAdmin() {
        return userRepository.findAllByOrderByUserName()
                .stream().filter(user -> !administratorRepository.existsById(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw  new UsernameNotFoundException("Correo electrónico o contraseña incorrecto");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(),user.getPassword(),
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
