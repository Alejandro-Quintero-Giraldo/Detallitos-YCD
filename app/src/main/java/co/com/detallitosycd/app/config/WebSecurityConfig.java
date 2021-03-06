package co.com.detallitosycd.app.config;

import co.com.detallitosycd.app.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)  {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws  Exception {

        http.authorizeRequests().antMatchers(
                "/", "/register","/saveUser",
                "/product/{id}","/product/","/catalogue/","/validate",
                "/catalogue/{id}",
                "/bill/available", "/bill/create", "/bill/close", "/bill/deleteProduct",
                "/assets/**",
                "/styles/**",
                "/js/**",
                "/util/**",
                "/image_products/**"
                ).permitAll()
                .antMatchers("/product/create/action", "/product/save",
                        "/product/update/{id}", "/product/put","/withoutAdmin",
                        "/administrator/save", "/catalogue/create", "/catalogue/save",
                        "/catalogue/update/{id}", "/catalogue/put", "/catalogue/delete/{id}")
                .hasRole("ADMINISTRATOR")
                .antMatchers("/login").anonymous()
                .anyRequest().denyAll()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }

}