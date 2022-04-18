package co.com.detallitosycd.app;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User("Basic User", "randomPass", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        User administratorUser = new User("Administrator User","randomPass",
                List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")));

        return new InMemoryUserDetailsManager(Arrays.asList(basicUser, administratorUser));
    }
}
