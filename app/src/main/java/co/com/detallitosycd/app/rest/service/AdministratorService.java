package co.com.detallitosycd.app.rest.service;

import co.com.detallitosycd.app.entity.Administrator;
import co.com.detallitosycd.app.rest.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator saveAdmin(Administrator administrator){
        return administratorRepository.save(administrator);
    }
}
