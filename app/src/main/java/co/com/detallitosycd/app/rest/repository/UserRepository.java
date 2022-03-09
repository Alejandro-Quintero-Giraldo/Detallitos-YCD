package co.com.detallitosycd.app.rest.repository;

import co.com.detallitosycd.app.entity.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@EntityScan
@Repository
public interface UserRepository  extends JpaRepository<User, String> {

    User findUserByEmail(String email);

    User findUserByUserId(String id);

}
