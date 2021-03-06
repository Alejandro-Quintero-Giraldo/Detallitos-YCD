package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Catalogue;
import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.entity.ProductCatalogue;
import co.com.detallitosycd.app.model.CatalogueModel;
import co.com.detallitosycd.app.model.ProductCatalogueModel;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.core.CollectionFactory;
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

    @ModelAttribute("catalogueAction")
    public Catalogue attributeController(){
        return new Catalogue();
    }

    @GetMapping("/create")
    public String createPage(Model model){
        model.addAttribute("action", "create");
        return "updateCatalogues";
    }

    @PostMapping("/save")
    public String createCatalogue(Catalogue catalogue) throws SQLException {
        catalogueModel = new CatalogueModel();
        catalogue.setCatalogueId(UUID.randomUUID().toString());
        catalogueModel.createCatalogue(catalogue);
        //TODO: RETORNAR HTML
        return "redirect:/catalogue/create?success";
    }

    @GetMapping("/{id}")
    public String findCatalogueById(@PathVariable("id") String id, Model model) throws SQLException {
        catalogueModel = new CatalogueModel();
        productCatalogueModel = new ProductCatalogueModel();
        Catalogue catalogue = catalogueModel.findCatalogueById(id);
        if(catalogue != null){
            List<ProductCatalogue> productCatalogueList = productCatalogueModel.findProductCatalogueByCatalogueId(catalogue.getCatalogueId());
            List<Product> productList = getProductList(productCatalogueList);
            List<Product> productsNoCatalogues = productModel.findProductsVisibles();
            productsNoCatalogues.forEach(product -> System.out.println(product.toString()));
            model.addAttribute("catalogue", catalogue);
            model.addAttribute("productCatalogueList", productCatalogueList);
            model.addAttribute("productList", productList);
            model.addAttribute("productsNoCatalogue", productsNoCatalogues);
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
            model.addAttribute("catalogueUpdate", catalogue);
            model.addAttribute("action", "update");
            return "updateCatalogues";
        } else  {
            return "redirect:/catalogue/?error";
        }
    }

    @PostMapping("/put")
    public String updateCatalogue(@RequestParam("catalogueId") String catalogueId,
                                  @RequestParam("catalogueName") String catalogueName,
                                  @RequestParam("description") String description) throws SQLException {
        catalogueModel = new CatalogueModel();
        Catalogue catalogueUpdate = new Catalogue(catalogueId, catalogueName, description);
        catalogueModel.updateCatalogue(catalogueUpdate);
        return "redirect:/catalogue/update/"+catalogueUpdate.getCatalogueId()+"?updated";
    }

    @GetMapping("/delete/{id}")
    public String deleteCatalogue(@PathVariable("id") String id) throws SQLException {
        catalogueModel = new CatalogueModel();
        productCatalogueModel = new ProductCatalogueModel();
        Catalogue catalogue = catalogueModel.findCatalogueById(id);
        if(catalogue == null){
            return "redirect:/catalogue/?error";
        }
        List<ProductCatalogue> productCatalogueList = productCatalogueModel.findProductCatalogueByCatalogueId(id);
        List<Boolean> resultList = deleteProductCatalogues(productCatalogueList);
        if(resultList.size() != productCatalogueList.size() || resultList.contains(false)) {
            throw new IllegalArgumentException("Ha ocurrido un error al eliminar algunos productos del cat??logo. Intentelo nuevamente");
        }
        catalogueModel.deleteCatalogue(id);
        return "redirect:/catalogue/?deleted";
    }

    @PostMapping("/addProduct")
    public String addProductInCatalogue(@RequestParam("catalogueId") String catalogueId,
                                        @RequestParam("productId") String productId) throws SQLException {
        productCatalogueModel = new ProductCatalogueModel();
        boolean result = productCatalogueModel.createProductCatalogue(new ProductCatalogue(productId,catalogueId));
        if(!result){
            return "redirect:/catalogue/"+catalogueId+"?errorAdded";
        }
        return  "redirect:/catalogue/"+catalogueId+"?productAdded";
    }

    @PostMapping("/deleteProduct")
    public String deleteProductInCatalogue(@RequestParam("catalogueId") String catalogueId,
                                           @RequestParam("productId") String productId) throws SQLException {
        productCatalogueModel = new ProductCatalogueModel();
        boolean result = productCatalogueModel.deleteProductCatalogue(new ProductCatalogue(productId, catalogueId));
        if(!result){
            return "redirect:/catalogue/"+catalogueId+"?error";
        }
        return "redirect:/catalogue/"+catalogueId+"?deleteSuccess";
    }

    private List<Boolean> deleteProductCatalogues(List<ProductCatalogue> productCatalogueList) {
        productCatalogueModel = new ProductCatalogueModel();
        return productCatalogueList.stream().map(productCatalogue -> {
            try {
                return productCatalogueModel.deleteProductCatalogue(productCatalogue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
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

   /* private List<Product> getProductNoCatalogues(List<Product> productList) throws SQLException {
        productModel = new ProductModel();
        List<Product> productNoCatalogues = productModel.findProductsVisibles();
        removeProductsInCatalogue(productList, productNoCatalogues);
        return productNoCatalogues;
    }*/

    /*private List<Product> removeProductsInCatalogue(List<Product> productList, List<Product> productNoCatalogues) {
        List<String> productIds =  productList.stream().map(Product::getProductId).collect(Collectors.toList());
        for (:
             ) {
            
        }
         IntStream.range(0, productList.size())
                .mapToObj(index -> productNoCatalogues.remove(productList.get(index)));
    }*/

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
