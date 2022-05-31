package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Catalogue;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.CatalogueModel;
import co.com.detallitosycd.app.model.ProductCatalogueModel;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/catalogue")
public class CatalogueController {

    private CatalogueModel catalogueModel;
    private ProductCatalogueModel productCatalogueModel;
    private ProductModel productModel;

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
        if(catalogueList.size() == 0){
            List<Product> productList = productModel.findProductsVisibles();
            if (productList.size() >= 9){
            }
            catalogueModel.createCatalogue(new Catalogue(UUID.randomUUID().toString(),"Productos destacados", "Este es el catalogo de productos destacados"));
        }
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

    @PostMapping("/addProduct")
    public void addProductInCatalogue(@RequestParam("catalogueId") String catalogueId,
                                        @RequestParam("productId") String productId){
        productCatalogueModel = new ProductCatalogueModel();
    }
}
