package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Company;
import co.com.detallitosycd.app.model.CompanyModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private CompanyModel companyModel;

    @GetMapping("/{nit}")
    public String getCompany(@PathVariable("nit") String nit, Model model) throws SQLException {
        Company company = companyModel.findCompanyByNit(nit);
        model.addAttribute("company", company);
        //TODO: AGREGAR HTML DE RETORNO
        return "";
    }

}
