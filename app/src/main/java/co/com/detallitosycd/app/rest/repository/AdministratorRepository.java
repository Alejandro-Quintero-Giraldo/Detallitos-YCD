package co.com.detallitosycd.app.rest.repository;

import co.com.detallitosycd.app.entity.Administrator;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@EntityScan
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {

    Administrator findByEmail(String email);
}
