package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Catalogue;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.ProductCatalogue;
import co.com.detallitosycd.app.model.CatalogueModel;
import co.com.detallitosycd.app.model.ProductCatalogueModel;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        productCatalogueModel = new ProductCatalogueModel();
        Catalogue catalogue = catalogueModel.findCatalogueById(id);
        if(catalogue != null){
            List<ProductCatalogue> productCatalogueList = productCatalogueModel.findProductCatalogueByCatalogueId(catalogue.getCatalogueId());
            List<Product> productList = getProductList(productCatalogueList);
            model.addAttribute("catalogue", catalogue);
            model.addAttribute("productCatalogueList", productCatalogueList);
            model.addAttribute("productList", productList);
            return "showCatalogue";
        } else {
            return "redirect:?error";
        }
    }

    @GetMapping("/")
    public String findAllCatalogues(Model model) throws SQLException {
        catalogueModel = new CatalogueModel();
        productModel = new ProductModel();
        List<Catalogue> catalogueList = catalogueModel.findAllCataloguesVisibles();
        //if(catalogueList.size() == 0){
            List<Product> productList = productModel.findProductsVisibles();
            List<ProductCatalogue> productCatalogueList;
            //Catalogue catalogue = new Catalogue(UUID.randomUUID().toString(),"Productos destacados", "Este es el catalogo de productos destacados");
            //productCatalogueList = createFeaturedProducts(productList, catalogue);
            //model.addAttribute("productList", productList);
            //model.addAttribute("productCatalogueList", productCatalogueList);
            model.addAttribute("catalogueList", catalogueList);
          //  return "";
        //}
        return "catalogues";
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
        Catalogue catalogue = catalogueModel.findCatalogueById(id);
        if(catalogue == null){
            return "?error";
        }
        catalogueModel.deleteCatalogue(id);
        return "?deleted";
    }

    @PostMapping("/addProduct")
    public String addProductInCatalogue(@RequestParam("catalogueId") String catalogueId,
                                        @RequestParam("productId") String productId) throws SQLException {
        productCatalogueModel = new ProductCatalogueModel();
        boolean result = productCatalogueModel.createProductCatalogue(new ProductCatalogue(productId,catalogueId));
        if(!result){
            return "?error";
        }
        return  "";
    }

    @DeleteMapping("/deleteProduct")
    public String deleteProductInCatalogue(@RequestParam("catalogueId") String catalogueId,
                                           @RequestParam("productId") String productId) throws SQLException {
        productCatalogueModel = new ProductCatalogueModel();
        boolean result = productCatalogueModel.deleteProductCatalogue(new ProductCatalogue(productId, catalogueId));
        if(!result){
            return "?error";
        }
        return "?deleteSuccess";
    }

    private List<ProductCatalogue> createFeaturedProducts(List<Product> productList, Catalogue catalogue) throws SQLException {
        List<ProductCatalogue> productCatalogueList;
        if (productList.size() >= 9){
            productCatalogueList = createFeaturedProductsCatalogue(productList, catalogue, 9);
        } else {
            productCatalogueList = createFeaturedProductsCatalogue(productList, catalogue, productList.size());
        }
        return productCatalogueList;
    }

    private List<ProductCatalogue> createFeaturedProductsCatalogue(List<Product> productList, Catalogue catalogue, Integer numberProducts) throws SQLException {
        catalogueModel.createCatalogue(catalogue);
        return IntStream.range(0, numberProducts)
                .mapToObj(index -> {
                    try {
                        ProductCatalogue productCatalogue = new ProductCatalogue(productList.get(index).getProductId(), catalogue.getCatalogueId());
                        boolean result = productCatalogueModel.createProductCatalogue(productCatalogue);
                        if(result){
                            return productCatalogue;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<Product> getProductList(List<ProductCatalogue> productCatalogueList) {
        productModel = new ProductModel();
        List<Product> productList = new ArrayList<>();
        productCatalogueList.forEach(billProduct -> {
            try {
                Product product = productModel.findById(billProduct.getIdProduct());
                productList.add(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return productList;
    }
}
