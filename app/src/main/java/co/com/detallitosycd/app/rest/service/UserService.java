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

//Clase que implementa lógica de negocio de usuario
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    //método para crear el usuario
    @Override
    public User save(User userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //Se crea el usuario con el encriptador de contraseña
        User user = new User(userDTO.getUserId(),userDTO.getUserName(), userDTO.getCellphone(), userDTO.getAddress(),
                userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
        //Se guarda
        return userRepository.save(user);
    }
    //metodo para buscar usuario por id
    @Override
    public User findUserByUserId(String id) {
        return userRepository.findUserByUserId(id);
    }
    //metodo para buscar usuario por email
    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    //metodo para buscar usuarios no administradores
    @Override
    public List<User> findUserWithoutAdmin() {
        return userRepository.findAllByOrderByUserName()
                .stream().filter(user -> !administratorRepository.existsById(user.getUserId()))
                .collect(Collectors.toList());
    }

    //metodo para loguearte
    @Override
    public UserDetails loadUserByUsername(String username) {
        //Se busca el usuario por el email
        User user = userRepository.findUserByEmail(username);
        //Si no existe arroja error
        if (user == null) {
            throw  new UsernameNotFoundException("Correo electrónico o contraseña incorrecto");
        }
        //Si existe, instancia la clase usuario del spring security, que permite la authenticacion
        return new org.springframework.security.core.userdetails.User(user.getUserId(),user.getPassword(),
                mapAuthorities(user.getEmail()));
    }

    //metodo para definir el rol del usuario
    public Collection<GrantedAuthority> mapAuthorities(String email){
        //Si no se encuentra ese email en los registros del administrador
        Administrator administrator = administratorRepository.findByEmail(email);
        if (administrator == null) {
            //Obtendrá el rol usuario
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            //Si se encuentra el email, obtendrá rol administrador
            return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
        }
    }


}
