package co.com.detallitosycd.app.controller;

import co.com.detallitosycd.app.entity.*;
import co.com.detallitosycd.app.model.*;
import co.com.detallitosycd.app.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.util.annotation.Nullable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/bill")
//Esta es la clase controladora donde se maneja la información y las páginas a mostrar
public class BillController {

    private BillModel billModel;
    private BillProductModel billProductModel;
    private ProductModel productModel;
    private StateModel stateModel;

    @Autowired
    private UserService userService;

    //Este método nos sirve para crear una factura o añadir un producto a una factura activa
    @PostMapping("create")
    public String createBillOrPutProductInBill(@RequestParam("amountPurchased") Integer amountPurchased,
                             @RequestParam("especifications") String especifications,
                             @RequestParam("productId") String productId) throws SQLException {
        billModel = new BillModel();
        //Se verifica si hay un usuario logueado
        if(checkSession() == null) {
            return "redirect:/login";
        }
        //Se busca una factura abierta del usuario logueado
        Bill existsBill = billModel.findAvailableBill(checkSession().getUsername());
        //Si existe una factura abierta
        if(existsBill != null){
            //Se añade el producto a la factura activa
            boolean result = billModel.putProductInAnAvailableBill(existsBill.getBillId(),productId,especifications,amountPurchased);
            //Si el producto ya existe en la factura
            if(!result){
                //Saca un modal de error donde avisa al usuario que ya existe ese producto en la factura
                return "redirect:/bill/available?productExist";
            }
          //Si no hay factura activa
        } else {
            CompanyModel companyModel = new CompanyModel();
            //Se crea una factura con la información necesaria para activarla
            Bill bill = new Bill(checkSession() != null
                    ? checkSession().getUsername() : null,
                    companyModel.findCompanyByNit("123").getNIT());
            //Activa la factura
            billModel.createBill(bill, amountPurchased,especifications,productId);
        }
        //Saca un modal donde confirma el producto añadido a la factura activa
        return "redirect:/bill/available?productAdded";
    }

    //Método que nos muestra la factura activa (carrito de compras) con los productos que tiene
    @GetMapping("available")
    public String getAvailableBill(Model model) throws SQLException {
        billModel = new BillModel();
        BillProductModel billProductModel = new BillProductModel();
        //Se busca una factura activa del usuario logueado
        Bill existBill = billModel.findAvailableBill(checkSession() != null
                ? checkSession().getUsername() : null);
        //Si existe una factura activa
        if(existBill != null){
            //Se setea el precio final
            existBill.setFinalPrice(calculateFinalPrice(existBill.getBillId()));
            //Se muestra la factura activa en la interfaz con el nombre "activeBill"
            model.addAttribute("activeBill", existBill);
            //se busca al usuario logueado
            User user = userService.findUserByUserId(checkSession().getUsername());
            //Se muestra al usuario en la interfaz con el nombre "user"
            model.addAttribute("user", user);
            //Se trae la lista de producto-factura a través del id de la factura
            List<BillProduct> billProductList =  billProductModel.findBillProductsByBillId(existBill.getBillId());
            //Se obtiene la lista de productos según la lista de productos-factura
            List<Product> productList = getProductList(billProductList);
            //Se muestra la lista de productos-factura en la interfaz con el nombre "billProducts"
            model.addAttribute("billProducts", billProductList);
            //Se muestra la lista de productos en la interfaz con el nombre "products"
            model.addAttribute("products", productList);
        //Si no existe una factura activa
        } else {
            //Se manda nulo la factura activa para que saque un modal donde se especifique que no hay facturas activas
            model.addAttribute("activeBill", null);
        }
        //Se muestra el archivo html llamado "shoppingCart"
        return "shoppingCart";
    }
    //Metodo para cerrar la factura cuando se realiza una compra
    @PostMapping("close")
    public String closeBill(@RequestParam("billId") String billId,
                            @RequestParam("deliverType") String deliverType,
                            @Nullable @RequestParam("addressDomicile") String  addressDomicile) throws SQLException {
        billModel = new BillModel();
        stateModel = new StateModel();
        //Se busca la factura por el id
        Bill bill = billModel.findBillById(billId);
        //Se busca el estado de la factura
        State state = stateModel.findStateById(bill.getStateId());
        //Si el estado de la factura es igual a disponible
        if(state.getStateName().equals("DISPONIBLE")){
            //Si el tipo de entrega es domicilio, se crea el domicilio. Si no, no hace nada
            String idDomicile = deliverType.equalsIgnoreCase("domicilio")
                    ?  createDomicile(new Domicile(UUID.randomUUID().toString(),addressDomicile, null,null)).getDomicileId()
                    : null;
            //Se crea la entrega la entrega y se guarda el id.
            String idDelivery = createDelivery(new Delivery(UUID.randomUUID().toString(),deliverType, idDomicile)).getDeliveryId();
            //Se trae el estado de cerrada
            State stateClose = getStateClose(stateModel);

            billProductModel = new BillProductModel();
            //Se calcula el precio final de la factura
            Integer finalPrice = calculateFinalPrice(billId);
            //Se setea el estado de cerrada
            bill.setStateId(stateClose.getStateId());
            //Se setea el precio final
            bill.setFinalPrice(finalPrice);
            //Se setea el id de la entrega
            bill.setDeliverId(idDelivery);
            //Se setea la fecha de cierre
            bill.setDateBill(LocalDateTime.now());
            //Se actualiza la factura
            billModel.updateBill(bill);
        }
        return "redirect:/bill/available";
    }


    //metodo para borrar producto de la factura
    @PostMapping("deleteProduct")
    public String deleteProductInBill(@RequestParam("billId") String billId,
                                      @RequestParam("productId") String productId) throws SQLException {
        billProductModel = new BillProductModel();
        //Se intenta borrar el producto de la factura
        boolean result = billProductModel.deleteBillProduct(billId, productId);
        //Si hay un error
        if(!result){
            //Muestra alerta de error
            return "redirect:/bill/available?error";
        }
        //Si salio bien muestra alerta de exito
        return "redirect:/bill/available?productDeleted";
    }

    //Metodo para ver facturas cerradas
    @GetMapping("showClosed")
    public String showBillsClosed(Model model) throws SQLException {
        billModel = new BillModel();
        stateModel = new StateModel();
        //Se busca una lista de facturas cerradas del usuario logueado
        List<Bill> billList = billModel.findBillsClosed(checkSession().getUsername(),getStateClose(stateModel).getStateId());
        //Si la lista no está vacía
        if(!billList.isEmpty()){
            //Se añade la lista a la interfaz con el nombre "billClosedList"
            model.addAttribute("billClosedList", billList);
        }
        return "";
    }
    //Obtiene la lista de productos dependiendo de la lista de productos-factura
    private List<Product> getProductList(List<BillProduct> billProductList) {
        productModel = new ProductModel();
        List<Product> productList = new ArrayList<>();
        billProductList.forEach(billProduct -> {
            try {
                Product product = productModel.findById(billProduct.getIdProduct());
                productList.add(product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return productList;
    }

    //Obtiene el estado de cerrada
    private State getStateClose(StateModel stateModel) throws SQLException {
        return stateModel.findAllState()
                .stream().filter(state1 -> state1.getStateName().equals("CERRADO-NO-ENTREGADO"))
                .findAny().get();
    }
    //Retorna la información del usuario logueado
    private UserDetails checkSession(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        return userDetails;
    }
    //Crea el domicilio
    private Domicile createDomicile(Domicile domicile) throws SQLException {
        DomicileModel domicileModel = new DomicileModel();
        return domicileModel.createDomicile(domicile);
    }
    //Crea la entrega
    private Delivery createDelivery(Delivery delivery) throws SQLException {
        DeliveryModel deliveryModel = new DeliveryModel();
        return deliveryModel.createDelivery(delivery);
    }
    //Calcula el precio final
    private Integer calculateFinalPrice(String billId) throws SQLException {
        List<Integer> subtotalList = new ArrayList<>();
        billProductModel = new BillProductModel();
        billProductModel.findBillProductsByBillId(billId)
                .forEach(billProduct -> subtotalList.add(billProduct.getSubTotal()));
        Integer finalPrice = 0;
        for (Integer subtotal : subtotalList) {
            finalPrice += subtotal;
        }
        return finalPrice;
    }
}
