package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductModel productModel;

    @ModelAttribute("productCreate")
    public Product attributeController(){
        return new Product();
    }

    @ModelAttribute("productUpdate")
    public Product attributeUpdateController(){
        return new Product();
    }

    @GetMapping("/create")
    public String createPage(){
        return "createProduct";
    }

    @PostMapping("/save")
    public String createProduct(Product productCreate) throws SQLException {
        productCreate.setProductId(UUID.randomUUID().toString());

        productModel = new ProductModel();
        productModel.createProduct(productCreate);

        return "redirect:/product/create?success";

    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) throws SQLException{
        productModel = new ProductModel();
        Product product = productModel.findById(id);
        model.addAttribute("oneProduct", product);
        return "viewProduct";
    }

    @GetMapping("/update")
    public String updatePage() {
        return "";
    }

    @PutMapping("/put")
    public String updateProduct(Product productUpdate) throws SQLException {
        productModel = new ProductModel();
        productModel.updateProduct(productUpdate);
        return "";

    }
}
