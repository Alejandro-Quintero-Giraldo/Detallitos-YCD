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
    //Se agrega una instancia del producto a la interfaz con el nombre "productAction"
    @ModelAttribute("productAction")
    public Product attributeController(){
        return new Product();
    }
    //Se agrega una instancia del producto a la interfaz con el nombre "productActionupdate"
    @ModelAttribute("productActionUpdate")
    public Product attributeUpdateController() {
        return new Product();
    }
    //Se muestra pagina para crear producto
    @GetMapping("/create/action")
    public String createPage(Model model){
        model.addAttribute("action", "create");
        return "updateProducts";
    }
    //Metodo para crear producto
    @PostMapping("/save")
    public String createProduct(Product productCreate, @RequestParam(name = "file") MultipartFile file)
            throws SQLException, IOException {
        //Se crea el id del producto
        productCreate.setProductId(UUID.randomUUID().toString());
        //Se valida la extension de la imagen
        validateFileExtension(file);
        //Se guarda la imagen o se renombra si ya existe
        String resultUpload = uploadFile(file);
        //Si es diferente de nulo
        if(resultUpload != null){
            //Se setea la imagen
            productCreate.setImage(resultUpload);
        }
        productModel = new ProductModel();
        //Se crea el producto
        productModel.createProduct(productCreate);
        //Se muestra alerta de exito
        return "redirect:/product/create/action?success";

    }
    //Mapea el rol del usuario
    public List<GrantedAuthority> getAuthorities(UserDetails userDetails){
        return  userDetails != null ? userDetails
                .getAuthorities()
                .stream().collect(Collectors.toList()) : new ArrayList<>();
    }
    //Muestra los productos visibles
    @GetMapping("/")
    public String findProductsVisibles(Model model) throws SQLException {
        productModel = new ProductModel();
        List<Product> productList = new ArrayList<>();
        //Se obtiene el rol actual del usuario
        List<GrantedAuthority> grantedAuthorityList = getAuthorities(checkSession());
        //Si no hay rol
        if(grantedAuthorityList.size() == 0) {
            //Se setea la lista de productos con la consulta de los productos visibles
            productList = productModel.findProductsVisibles();
        //Si el rol es usuario
        } else if(grantedAuthorityList.get(0).getAuthority().equals("ROLE_USER")){
            //Se setea la lista de productos con la consulta de los productos visibles
            productList = productModel.findProductsVisibles();
        //Si el rol es administrador
        } else if(grantedAuthorityList.get(0).getAuthority().equals("ROLE_ADMINISTRATOR")){
            //Se setea la lista de productos con la consulta con todos los productos
            productList = productModel.findAllProducts();
        }
        //Se añade la lista a la interfaz con el nombre "productList"
        model.addAttribute("productList", productList);
        //Se muestra el products.html
        return "products";
    }

    //metodo para mostrar un producto por id
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) throws SQLException{
        productModel = new ProductModel();
        //Se busca el producto
        Product product = productModel.findById(id);
        //Se agrega el producto a la interfaz con el nombre "oneProduct"
        model.addAttribute("oneProduct", product);
        //Se trae los productos visibles
        List<Product> productList = productModel.findProductsVisibles();
        //Se organiza de forma aleatoria
        Collections.shuffle(productList);
        //Elimina el producto que se encuentra guardado
        List<Product> suggestProductList  = filterRecentProduct(product, productList);
        //se agrega la lista a la interfaz con el nombre suggestProductList
        model.addAttribute("suggestProductList", suggestProductList);
        //Se muestra el  viewProducts.html
        return "viewProduct";
    }
    //Metodo que muestra la página para actualizar producto
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") String id, Model model) throws SQLException {
        productModel = new ProductModel();
        Product product = productModel.findById(id);
        model.addAttribute("productUpdate", product);
        model.addAttribute("action", "update");

        return "updateProducts";
    }
    //Metodo para actualizar el producto
    @PostMapping("/put")
    public String updateProduct(@RequestParam("productId") String productId,
                                @RequestParam("productName") String productName,
                                @RequestParam("idProductType") String productType,
                                @RequestParam("productPrice") Integer productPrice,
                                @RequestParam("description") String description,
                                @RequestParam("isVisible") String isVisible,
                                @RequestParam("actualImage") String actualImage,
                                @RequestParam(value = "fileUpdate") MultipartFile file) throws SQLException, IOException {
        //Se crea el producto
        Product productUpdate = new Product(productId, productName, productType, productPrice, description, isVisible, "");
        //Se verifica que exista una imagen
        if (file != null && !file.isEmpty()) {
            //Se valida su extension
            validateFileExtension(file);
            //Se borra la antigua imagen
            boolean resultDelete = deleteFile(actualImage);
            //Si el borrado es exitoso
            if (resultDelete) {
                //Se guarda la nueva imagen o se renombra
                String resultUpload = uploadFile(file);
                //Si el guardado es exitoso
                if (resultUpload != null) {
                    //Se setea la nueva imagen en el producto
                    productUpdate.setImage(resultUpload);
                }
            }
            //Si no existe una imagen, se setea la que tiene actualmente
        } else {
            productUpdate.setImage(actualImage);
        }
        productModel = new ProductModel();
        //Se actualiza el producto
        productModel.updateProduct(productUpdate);
        //Se saca alerta de actualizacion exitosa
        return "redirect:/product/update/" + productUpdate.getProductId() + "?updated";
    }
    //metodo para filtrar el producto que se encuentra en una lista
    private List<Product> filterRecentProduct(Product product, List<Product> suggestProductList) {
        return suggestProductList.stream()
                .filter(product1 -> !product1.getProductId().equals(product.getProductId()))
                .limit(4)
                .collect(Collectors.toList());
    }

    //metodo para validar la extension de un archivo imagen
    private void validateFileExtension(MultipartFile file) throws FileSystemException {
        String extensionFile =  FileNameUtils.getExtension(file.getOriginalFilename());
        System.out.println("extension "+extensionFile);
        assert extensionFile != null;
        if(!extensionFile.equals("png") && !extensionFile.equals("jpg") && !extensionFile.equals("jpeg")){
            throw new FileSystemException("El archivo ingresado no tiene una extension aceptada");
        }
    }
    //metodo para guardar una imagen
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
    //Metodo para verificar si la carpeta donde se guardan las imagenes existe
    private void verifyIfDirectoryExists(File directory) {
        if(!directory.exists()){
            boolean result = directory.mkdir();
            if(result){
                LOGGER.log(Level.INFO,"directory created successfully in Documents - image_products");
            }
        }
    }
    //metodo para renombrar imagen
    private String renameFileIfExist(String builder)  {
        String newFileName = builder.substring(0, builder.length()-4)+"1";
        String extension = builder.substring(builder.length()-4);
        builder = newFileName + extension;
        return builder;
    }
    //metodo para borrar una imagen antigua
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
    //metodo para retornar la informacion del usuario logueado
    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }

}
