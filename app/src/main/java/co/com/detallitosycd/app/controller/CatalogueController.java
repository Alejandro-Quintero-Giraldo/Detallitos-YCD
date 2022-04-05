package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Catalogue;
import co.com.detallitosycd.app.model.CatalogueModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/catalogue")
public class CatalogueController {

    private CatalogueModel catalogueModel;

    @GetMapping("/create")
    public String createPage(){
        return "";
    }

    @PostMapping("/save")
    public String createCatalogue(Catalogue catalogue) throws SQLException {
        catalogueModel = new CatalogueModel();
        catalogueModel.createCatalogue(catalogue);
        //TODO: RETORNAR HTML
        return "";
    }

    @GetMapping("/{id}")
    public String findCatalogueById(@PathVariable("id") String id, Model model) throws SQLException {
        catalogueModel = new CatalogueModel();
        Catalogue catalogue = catalogueModel.findCatalogueById(id);
        if(catalogue != null){
            model.addAttribute("catalogue", catalogue);
            return "";
        } else {
            return "redirect:?error";
        }
    }

    @GetMapping("/")
    public String findAllCatalogues(Model model) throws SQLException {
        catalogueModel = new CatalogueModel();
        List<Catalogue> catalogueList = catalogueModel.findAllCatalogues();
        model.addAttribute("catalogueList", catalogueList);
        return "";
    }

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") String id, Model model) throws SQLException {
        catalogueModel = new CatalogueModel();
        Catalogue catalogue = catalogueModel.findCatalogueById(id);
        if(catalogue != null){
            model.addAttribute("catalogue", catalogue);
            return "";
        } else  {
            return "redirect:?error";
        }
    }

    @PutMapping("/put")
    public String updateCatalogue(Catalogue catalogue) throws SQLException {
        catalogueModel = new CatalogueModel();
        catalogueModel.updateCatalogue(catalogue);
        return "?updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCatalogue(@PathVariable("id") String id) throws SQLException {
        catalogueModel = new CatalogueModel();
        catalogueModel.deleteCatalogue(id);
        return "?deleted";
    }
}
