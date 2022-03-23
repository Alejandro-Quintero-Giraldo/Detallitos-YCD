package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/create")
    public String createPage(){
        return "createProduct";
    }

    @PostMapping("/save")
    public String createProduct(Product productCreate) throws SQLException {
        productModel = new ProductModel();
        productCreate.setProductId(UUID.randomUUID().toString());
        productModel.createProduct(productCreate);

        return "redirect:/product/create?success";

    }
}
