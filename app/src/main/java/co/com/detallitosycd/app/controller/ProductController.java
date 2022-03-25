package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductModel productModel;

    @ModelAttribute("productAction")
    public Product attributeController(){
        return new Product();
    }

    @ModelAttribute("productActionUpdate")
    public Product attributeUpdateController() {
        return new Product();
    }

    @GetMapping("/create")
    public String createPage(Model model){
        model.addAttribute("action", "create");
        return "updateProducts";
    }

    @PostMapping("/save")
    public String createProduct(Product productCreate, @RequestParam("image") MultipartFile image)
            throws SQLException, IOException {
        productCreate.setProductId(UUID.randomUUID().toString());
        if(!image.isEmpty()){
            productCreate.setImage(image.getBytes());
        }
        productModel = new ProductModel();
        productModel.createProduct(productCreate);

        return "redirect:/product/create?success";

    }

    @GetMapping("/")
    public String findProductsVisibles(Model model) throws SQLException {
        productModel = new ProductModel();
        List<Product> productList = productModel.findProductsVisibles();
        model.addAttribute("productList", productList);
        return "products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) throws SQLException{
        productModel = new ProductModel();
        Product product = productModel.findById(id);
        model.addAttribute("oneProduct", product);
        return "viewProduct";
    }

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") String id, Model model) throws SQLException {
        productModel = new ProductModel();
        Product product = productModel.findById(id);
        model.addAttribute("productUpdate", product);
        model.addAttribute("action", "update");

        return "updateProducts";
    }

    @PostMapping("/put")
    public String updateProduct(@RequestParam("productId") String productId,
                                @RequestParam("productName") String productName,
                                @RequestParam("productType") String productType,
                                @RequestParam("productPrice") Integer productPrice,
                                @RequestParam("amountStock") Integer amountStock,
                                @RequestParam("description") String description,
                                @RequestParam("isVisible") String isVisible,
                                @RequestParam("image") byte[] image) throws SQLException {
        Product productUpdate = new Product(productId,productName,productType,productPrice,amountStock,description,isVisible,image);
        productModel = new ProductModel();
        productModel.updateProduct(productUpdate);
        return "redirect:/product/update/"+productUpdate.getProductId()+"?updated";

    }
}
