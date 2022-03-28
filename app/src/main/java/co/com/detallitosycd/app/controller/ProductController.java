package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            productCreate.setImage(resultUpload);
        }
        productModel = new ProductModel();
        productModel.createProduct(productCreate);

        return "redirect:/product/create?success";

    }

    public List<GrantedAuthority> getAuthorities(UserDetails userDetails){
        return  userDetails != null ? userDetails
                .getAuthorities()
                .stream().collect(Collectors.toList()) : new ArrayList<>();
    }

    @GetMapping("/")
    public String findProductsVisibles(Model model) throws SQLException {
        productModel = new ProductModel();
        List<Product> productList = new ArrayList<>();
        List<GrantedAuthority> grantedAuthorityList = getAuthorities(checkSession());
        if(grantedAuthorityList.size() == 0) {
            productList = productModel.findProductsVisibles();
        } else if(grantedAuthorityList.get(0).getAuthority().equals("ROLE_USER")){
            productList = productModel.findProductsVisibles();
        } else if(grantedAuthorityList.get(0).getAuthority().equals("ROLE_ADMINISTRATOR")){
            productList = productModel.findAllProducts();
        }
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
                productUpdate.setImage(resultUpload);
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
                "image_products" + File.separator +
                file.getOriginalFilename();

        Path path = Paths.get(builder);
        if(Files.exists(path)){
            return renameFileIfExist(file, builder);
        } else {
            Files.write(path, file.getBytes());
            return file.getOriginalFilename();
        }
    }

    private String renameFileIfExist(MultipartFile file, String builder) throws IOException {
        Path path;
        List<String> list = new ArrayList<>();
        String newFileName = builder.substring(0, builder.length()-4)+"1";
        String extension = builder.substring(builder.length()-4);
        list.add(newFileName);
        list.add(extension);
        builder = list.get(0) + list.get(1);
        path = Paths.get(builder);
        Files.write(path, file.getBytes());
        return file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-4)+"1"+extension;
    }

    public boolean deleteFile(String originalFileName){

        String builder = System.getProperty("user.home") +
                File.separator +
                "Documents" + File.separator +
                "image_products" + File.separator +
                originalFileName;
        Path path = Paths.get(builder);
        try {
            Files.deleteIfExists(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }

}
