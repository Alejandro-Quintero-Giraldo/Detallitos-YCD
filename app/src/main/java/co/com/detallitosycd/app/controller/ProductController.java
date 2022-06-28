package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.Product;
import co.com.detallitosycd.app.model.ProductModel;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger LOGGER = Logger.getLogger("co.com.detallitosycd.app.controller.product");
    private ProductModel productModel;

    @ModelAttribute("productAction")
    public Product attributeController(){
        return new Product();
    }

    @ModelAttribute("productActionUpdate")
    public Product attributeUpdateController() {
        return new Product();
    }

    @GetMapping("/create/action")
    public String createPage(Model model){
        model.addAttribute("action", "create");
        return "updateProducts";
    }

    @PostMapping("/save")
    public String createProduct(Product productCreate, @RequestParam(name = "file") MultipartFile file)
            throws SQLException, IOException {
        productCreate.setProductId(UUID.randomUUID().toString());
        validateFileExtension(file);
        String resultUpload = uploadFile(file);
        if(resultUpload != null){
            productCreate.setImage(resultUpload);
        }
        productModel = new ProductModel();
        productModel.createProduct(productCreate);

        return "redirect:/product/create/action?success";

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
        List<Product> productList = productModel.findProductsVisibles();
        Collections.shuffle(productList);
        List<Product> suggestProductList  = filterRecentProduct(product, productList);
        model.addAttribute("suggestProductList", suggestProductList);
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
                                @RequestParam("idProductType") String productType,
                                @RequestParam("productPrice") Integer productPrice,
                                @RequestParam("description") String description,
                                @RequestParam("isVisible") String isVisible,
                                @RequestParam("actualImage") String actualImage,
                                @RequestParam(value = "fileUpdate") MultipartFile file) throws SQLException, IOException {

        Product productUpdate = new Product(productId, productName, productType, productPrice, description, isVisible, "");
        if (file != null && !file.isEmpty()) {
            validateFileExtension(file);
            boolean resultDelete = deleteFile(actualImage);
            if (resultDelete) {
                String resultUpload = uploadFile(file);
                if (resultUpload != null) {
                    productUpdate.setImage(resultUpload);
                }
            }
        } else {
            productUpdate.setImage(actualImage);
        }
        productModel = new ProductModel();
        productModel.updateProduct(productUpdate);
        return "redirect:/product/update/" + productUpdate.getProductId() + "?updated";
    }
    private List<Product> filterRecentProduct(Product product, List<Product> suggestProductList) {
        return suggestProductList.stream()
                .filter(product1 -> !product1.getProductId().equals(product.getProductId()))
                .limit(4)
                .collect(Collectors.toList());
    }


    private void validateFileExtension(MultipartFile file) throws FileSystemException {
        String extensionFile =  FileNameUtils.getExtension(file.getOriginalFilename());
        System.out.println("extension "+extensionFile);
        assert extensionFile != null;
        if(!extensionFile.equals("png") && !extensionFile.equals("jpg") && !extensionFile.equals("jpeg")){
            throw new FileSystemException("El archivo ingresado no tiene una extension aceptada");
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) {
            return null;
        }

        String builder = System.getProperty("user.home") +
                File.separator +
                "Documents" + File.separator +
                "image_products";
        File directory = new File(builder);
        verifyIfDirectoryExists(directory);
        builder += File.separator + file.getOriginalFilename();

        Path path = Paths.get(builder);
        String fileName = file.getOriginalFilename();
        int counter = 0;
        while(Files.exists(path)){
            counter += 1;
            builder = renameFileIfExist(builder);
            path = Paths.get(builder);
            fileName = builder.substring(builder.length() - (file.getOriginalFilename().length()+counter));
        }
        Files.write(path, file.getBytes());
        return fileName;

    }

    private void verifyIfDirectoryExists(File directory) {
        if(!directory.exists()){
            boolean result = directory.mkdir();
            if(result){
                LOGGER.log(Level.INFO,"directory created successfully in Documents - image_products");
            }
        }
    }

    private String renameFileIfExist(String builder)  {
        String newFileName = builder.substring(0, builder.length()-4)+"1";
        String extension = builder.substring(builder.length()-4);
        builder = newFileName + extension;
        return builder;
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
