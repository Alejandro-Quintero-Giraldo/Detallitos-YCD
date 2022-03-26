package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String createProduct(Product productCreate, @RequestParam(name = "file") MultipartFile file)
            throws SQLException, IOException {
        productCreate.setProductId(UUID.randomUUID().toString());
        String resultUpload = uploadFile(file);
        if(resultUpload != null){
            productCreate.setImage(file.getOriginalFilename());
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
                                @RequestParam("actualImage") String actualImage,
                                @RequestParam("fileUpdate") MultipartFile file) throws SQLException, IOException {

        Product productUpdate = new Product(productId,productName,productType,productPrice,amountStock,description,isVisible,"");
        boolean resultDelete = deleteFile(actualImage);
        if(resultDelete){
            String resultUpload = uploadFile(file);
            if(resultUpload != null){
                productUpdate.setImage(file.getOriginalFilename());
            }
        }

        productModel = new ProductModel();
        productModel.updateProduct(productUpdate);
        return "redirect:/product/update/"+productUpdate.getProductId()+"?updated";

    }

    public String uploadFile(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) {
            return null;
        }

        String builder = System.getProperty("user.home") +
                File.separator +
                "Documents" + File.separator +
                "Detallitos-YCD" + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "static" + File.separator +
                "assets" + File.separator +
                "products" + File.separator +
                file.getOriginalFilename();

        Path path = Paths.get(builder);
        if(Files.exists(path)){
            builder += "1";
            path = Paths.get(builder);
            Files.write(path, file.getBytes());
            return file.getOriginalFilename()+"1";
        }
        Files.write(path, file.getBytes());

        return file.getOriginalFilename();
    }

    public boolean deleteFile(String originalFileName){

        String builder = System.getProperty("user.home") +
                File.separator +
                "Documents" + File.separator +
                "Detallitos-YCD" + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "static" + File.separator +
                "assets" + File.separator +
                "products" + File.separator +
                originalFileName;
        Path path = Paths.get(builder);
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
